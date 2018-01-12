(function () {
        var Device = {
            api: "http://127.0.0.1:5010/drivers",
            wsApi: "ws://127.0.0.1:5011"
        };
        var socket;
        var callbacks = {};

        function createCallback(call) {
            if (!call) {
                return null;
            }
            if (call.success) {
                return function (e) {
                    if (e.status === 1) {
                        call.success(e.result);
                    } else {
                        if (call.error)
                            call.error(e);
                    }
                }
            }
            if (typeof call !== 'function') {
                console.warn(call + " is not function")
                return;
            }
            return function (e) {
                call(e.result);
            }
        }

        if (window.WebSocket) {
            socket = new WebSocket(Device.wsApi);
            socket.onmessage = function (ev) {
                var res = JSON.parse(ev.data);
                if (res.code && callbacks[res.code]) {
                    callbacks[res.code](res);
                }
                delete callbacks[res.code];
            }
        }

        Device.call = function (device, provider, action, data, call) {
            var code = Math.round(Math.random() * 10000000);
            var d = JSON.stringify({code: code, device: device, provider: provider, action: action, data: typeof data === 'string' ? data : JSON.stringify(data)});
            if (socket && socket.readyState === 1) {
                callbacks[code] = call;
                socket.send(d)
            } else {
                $.ajax({
                    type: "POST",
                    contentType: "application/json",
                    dataType: "json",
                    url: Device.api,
                    async: typeof call !== 'undefined',
                    data: d, success: function (e) {
                        if (call) {
                            call(e);
                        }
                    }
                });
            }
        };

        {
            Device.getPrinter = function (provider) {
                return {
                    preview: function (data, call) {
                        Device.call("printer", provider, "preview", (typeof data === 'object' ? JSON.stringify(data) : data), createCallback(call));
                    },
                    print: function (data, call) {
                        Device.call("printer", provider, "print", (typeof data === 'object' ? JSON.stringify(data) : data), createCallback(call));
                    }
                }
            };
            Device.getCamera = function (provider) {
                return {
                    record: function (call) {
                        return Device.call("camera", provider, "record", {}, createCallback(call));
                    },
                    getImageBase64: function (call) {
                        return Device.call("camera", provider, "getImageBase64", {}, createCallback(call));
                    },
                    close: function (call) {
                        return Device.call("camera", provider, "close", {}, createCallback(call));
                    },
                    open: function (call) {
                        return Device.call("camera", provider, "open", {}, createCallback(call));
                    },
                    isOpen: function (call) {
                        return Device.call("camera", provider, "isOpen", {}, createCallback(call));
                    }
                }
            };

            Device.printer = Device.getPrinter();
            Device.camera = Device.getCamera();

        }
        if (window.define) {
            define([], function () {
                return Device;
            })
        } else {
            window.devices = Device;
        }

    }
)();
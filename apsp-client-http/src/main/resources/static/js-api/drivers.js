(function () {
        var Device = {
            api: "http://127.0.0.1:5010/drivers"
        };

        function createCallback(call) {
            if (!call) {
                return null;
            }
            return function (e) {
                call(e.result);
            }
        }

        Device.call = function (device, provider, action, data, call) {
            var r = $.ajax({
                type: "POST",
                contentType: "application/json",
                dataType: "json",
                url: Device.api,
                async: typeof call !== 'undefined',
                data: JSON.stringify({device: device, provider: provider, action: action, data: typeof data === 'string' ? data : JSON.stringify(data)})
                , success: function (e) {
                    if (call) {
                        call(e);
                    }
                }
            });
            if (r) {
                return r.responseJSON;
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
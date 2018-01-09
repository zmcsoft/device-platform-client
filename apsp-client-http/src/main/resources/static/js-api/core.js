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

        Device.createCallback = createCallback;
        Device.call = function (device, action, data, call) {
            var r = $.ajax({
                type: "POST",
                contentType: "application/json",
                dataType: "json",
                url: Device.api,
                async: typeof call !== 'undefined',
                data: JSON.stringify({device: device, action: action, data: typeof data === 'string' ? data : JSON.stringify(data)})
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
        window.device = Device;
    }
)();
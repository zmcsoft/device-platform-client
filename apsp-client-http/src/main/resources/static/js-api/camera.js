(function () {

    var Camera = {
        getImageBase64: function (call) {
            return device.call("camera", "getImageBase64", {}, device.createCallback(call));
        },
        close: function (call) {
            return device.call("camera", "close", {}, device.createCallback(call));
        },
        open: function (call) {
            return device.call("camera", "open", {}, device.createCallback(call));
        },
        isOpen: function (call) {
            return device.call("camera", "isOpen", {}, device.createCallback(call));
        }
    };

    if (window.define) {
        define([], function () {
            return Camera;
        })
    } else {
        window.camera = Camera;
    }
})();
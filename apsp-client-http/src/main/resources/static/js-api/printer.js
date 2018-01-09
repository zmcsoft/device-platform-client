(function () {

    var Printer = {
        preview: function (data, call) {
            device.call("printer", "preview", (typeof data === 'object' ? JSON.stringify(data) : data), device.createCallback(call));
        },
        print: function (data, call) {
            device.call("printer", "print", (typeof data === 'object' ? JSON.stringify(data) : data), device.createCallback(call));
        }
    };

    if (window.define) {
        define([], function () {
            return Printer;
        })
    } else {
        window.printer = Printer;
    }
})();
# apsp-client

# 设备驱动JAVA API
模块: `apsp-client-sdk`,调用方式:
```java
// 摄像头
CameraDriver camera = DeviceDriverManager.drivers().camera();

// 打印机
PrinterDriver printer = DeviceDriverManager.drivers().printer();
```

# 设备驱动JS API
约定: 所有的驱动调用的返回都使用回调方式,如:
```js
camera.open(data,function(success){
    console.log(success);
});
```
模块: 'apsp-client-http',首先启动:`HttpApiServer`,默认端口:5010.

在html中引入js: "http://localhost:5010/drivers.js"

```js
  var camera= drivers.camera;
  var printer= drivers.printer;
```
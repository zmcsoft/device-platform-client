package com.zmcsoft.apsp.client;

import gnu.io.CommPortIdentifier;
import org.usb4java.*;

import java.util.Enumeration;

/**
 * @author zhouhao
 * @since
 */
public class LodopTest {
    public static void main(String[] args) {
        Context context = new Context();
        int result = LibUsb.init(context);
        DeviceList list = new DeviceList();
        LibUsb.getDeviceList(context, list);
        for (Device device : list) {
            DeviceDescriptor descriptor = new DeviceDescriptor();
            result = LibUsb.getDeviceDescriptor(device, descriptor);
            System.out.println(descriptor);
        }
        LibUsb.freeDeviceList(list, true);
    }
}

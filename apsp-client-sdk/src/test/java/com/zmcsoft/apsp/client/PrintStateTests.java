package com.zmcsoft.apsp.client;


import com.sun.jna.Native;
import com.zmcsoft.apsp.client.sdk.drivers.printer.PrinterState;

/**
 * TODO 完成注释
 *
 * @author zhouhao
 * @since
 */
public class PrintStateTests {
    public static void main(String[] args) {
        PrinterState state = Native.loadLibrary("/PrinterStatusDll.dll", PrinterState.class);

        System.out.println(state.GetPrtStatus());
    }
}

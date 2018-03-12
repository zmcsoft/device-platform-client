package com.zmcsoft.apsp.client.sdk.drivers.softkeyboard;

import org.hswebframework.expands.shell.Result;
import org.hswebframework.expands.shell.Shell;

public class WinConsoleDriver implements SoftKeyBoardDriver{

    @Override
    public void open() {
        try {
            Result result= Shell.build("cmd /c \"osk\"")
                    .encode("gbk")
                    .onProcess((line, helper) -> {
                        System.out.println(line);
                    }).exec();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

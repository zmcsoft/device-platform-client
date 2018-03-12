package com.zmcsoft.apsp.client.sdk.drivers.voice;

import org.hswebframework.expands.shell.Shell;

import java.io.IOException;

public class WinVoiceDriver implements VoiceDriver{
    @Override
    public void speak(String content) {
        try {
            Shell.build("cmd /c \"mshta vbscript:createobject(\"sapi.spvoice\").speak(\"" + content +"\")(window.close) \"")
                    .encode("gbk")
                    .onProcess(((line, helper) -> {
                        System.out.println(line);
                    })).exec();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

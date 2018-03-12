package com.zmcsoft.apsp.client.sdk.drivers.voice;

import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriver;

public interface VoiceDriver extends DeviceDriver{
    @Override
    default String getName(){
        return "voice";
    }
    
    void speak(String content);
}

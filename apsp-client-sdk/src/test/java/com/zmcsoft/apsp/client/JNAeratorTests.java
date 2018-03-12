package com.zmcsoft.apsp.client;

import com.ochafik.lang.jnaerator.JNAerator;

/**
 * @author zhouhao
 * @since 1.0
 */
public class JNAeratorTests {
    public static void main(String[] args) {
        JNAerator.main(new String[]{
             //   "./drivers/card/K2616_Dll.dll","./drivers/card/K2616_Dll.h", "-o", "target", "-mode", "Directory", "-runtime", "JNA"
              //  "./drivers/identity/mtx_32.dll","./drivers/identity/mtx_32.h", "-o", "target", "-mode", "Directory", "-runtime", "JNA"
                "./drivers/iccrad/56iccdll.dll","./drivers/iccrad/56iccdll.h", "-o", "target", "-mode", "Directory", "-runtime", "JNA"
        });
    }
}

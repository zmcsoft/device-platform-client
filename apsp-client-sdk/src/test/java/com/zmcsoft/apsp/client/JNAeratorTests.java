package com.zmcsoft.apsp.client;

import com.ochafik.lang.jnaerator.JNAerator;

/**
 * @author zhouhao
 * @since 1.0
 */
public class JNAeratorTests {
    public static void main(String[] args) {
        JNAerator.main(new String[]{
                "./drivers/samcoins.dll", "-com", "-o", "target", "-mode", "Maven", "-runtime", "JNA"
        });
    }
}

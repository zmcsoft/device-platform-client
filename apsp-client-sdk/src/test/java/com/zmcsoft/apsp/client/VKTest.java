package com.zmcsoft.apsp.client;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * TODO 完成注释
 *
 * @author zhouhao
 * @since
 */
public class VKTest {
    public static void main(String[] args) throws AWTException {
        Robot r=new Robot();
        r.keyPress(KeyEvent.VK_A);
        r.keyRelease(KeyEvent.VK_A);
    }
}

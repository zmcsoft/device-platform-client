package com.zmcsoft.apsp.client.sdk.drivers.camera;

import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriver;

import java.awt.image.BufferedImage;
import java.util.function.Consumer;

/**
 * 摄像头驱动
 *
 * @author zhouhao
 * @since 1.0
 */
public interface CameraDriver extends DeviceDriver {
    @Override
    default String getName() {
        return "camera";
    }

    /**
     * 摄像头是否已经打开
     *
     * @return boolean
     */
    boolean isOpen();

    /**
     * 打开摄像头
     *
     * @return 是否打开成功
     */
    boolean open();

    /**
     * 关闭摄像头
     *
     * @return 是否关闭成功
     */
    boolean close();

    /**
     * 录制
     *
     * @param recordListener 录制
     */
    void record(Consumer<BufferedImage> recordListener);

    /**
     * 停止录制
     */
    void stopRecord();

    /**
     * 拍照
     *
     * @return 拍照的图片对象, 如果失败返回null
     * @see BufferedImage
     */
    BufferedImage photographImage();

    /**
     * 拍照,并返回照片的base64,图片编码为png
     *
     * @return png格式图片的base64, 如果失败返回空字符串
     */
    String photographBase64();

    /**
     * 拍照,并返回照片的byte数据,图片编码为png
     *
     * @return png格式图片的数据, 如果失败返回 new byte[0]
     */
    byte[] photographBytes();
}

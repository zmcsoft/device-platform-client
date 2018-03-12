package com.zmcsoft.apsp.client.sdk.drivers.multiple.idcard;

import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriver;
import com.zmcsoft.apsp.client.sdk.drivers.multiple.IDCardEntity;

import java.io.IOException;

public interface IdentityDriver extends DeviceDriver {
    @Override
    default String getName() {
        return "identity";
    }

    /**
     * 获取身份证全部信息
     *
     * @return
     */
    IDCardEntity getIDCradInfo() throws Exception;

    /**
     * 查看身份证是否过期
     *
     * @return
     */
    //boolean idCradIfOverdue() throws IOException;

    /**
     * 获取照片base64码
     * @return
     */
    //IDCardEntity getIdCradPhotoBase64() throws IOException, Exception;
    /**
     * 删除所有身份证照片文件
     *
     * @return
     */
    //boolean deleteAllPhotofile();
}

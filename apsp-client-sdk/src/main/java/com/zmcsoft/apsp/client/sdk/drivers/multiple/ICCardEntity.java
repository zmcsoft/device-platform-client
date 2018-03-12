package com.zmcsoft.apsp.client.sdk.drivers.multiple;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Builder
@Setter
@Getter
@ToString
public class ICCardEntity implements Serializable {

    /**
     * IC卡号
     */
    private String cardNo;

    /**
     * IC卡户名
     */
    private String cardName;

    /**
     * IC卡序列号
     */
    private String serNo;

    /**
     * IC卡55域数据
     */
    private String gef55;
}

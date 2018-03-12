package com.zmcsoft.apsp.client.sdk.drivers.multiple;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IDCardEntity implements Serializable{
    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 民族
     */
    private String notion;

    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 住址
     */
    private String address;

    /**
     * 身份证号码
     */
    private String idNumber;

    /**
     * 签发机关
     */
    private String issueDepartment;

    /**
     * 有效期起始日期
     */
    private String validFormDate;

    /**
     * 有效期截止日期
     */
    private String validExpiryDate;

    /**
     * 头像base64字符串
     */
    private String portraitBase64;


}

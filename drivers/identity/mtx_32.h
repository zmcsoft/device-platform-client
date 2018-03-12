
extern "C"
{
#ifndef  __MAGCARD__
#define __MAGCARD__
	struct MagCard {
		unsigned char track1_len;
		unsigned char track2_len;
		unsigned char track3_len;
		unsigned char track1_data[80];
		unsigned char track2_data[41];
		unsigned char track3_data[170];
	};
#endif
	//*************************** 设备操作信息 ********************************************
	//打开设备
	HANDLE  __stdcall device_open(__int16 port,unsigned long baud);
	__int16 __stdcall ReSetupComm(HANDLE idComDev);
	//关闭设备
	__int16 __stdcall device_close(HANDLE icdev);
	//获取设备类型
	__int16 __stdcall device_gettype(unsigned char* devicetype);
	//设置串口通讯波特率
	__int16 __stdcall device_setbaud(HANDLE icdev,unsigned char module,unsigned long baud);
	//获取读写器版本信息
	__int16 __stdcall device_version(HANDLE icdev,unsigned char module,unsigned char* verlen,unsigned char* verdata);
	//读写器软复位
	__int16 __stdcall device_reset(HANDLE icdev,unsigned char module);
	//读写器蜂鸣器控制
	__int16 __stdcall device_beep(HANDLE icdev, unsigned short delaytime,unsigned char times);
	//LED灯控制
	__int16 __stdcall device_ledctrl(HANDLE icdev,unsigned char ledctrl);
	//读产品序列号
	__int16 __stdcall device_readsnr(HANDLE icdev,unsigned char nSnrLen,char* sSnrData);
	//写产品序列号
	__int16 __stdcall dev_writesnr(HANDLE icdev,unsigned char nSnrLen,char* sSnrData);

	//读EEPROM
	__int16 __stdcall dev_readeeprom(HANDLE icdev,unsigned short nAddr,unsigned short nDLen,unsigned char* sReadData);
	//写EEPROM
	__int16 __stdcall dev_writeeeprom(HANDLE icdev,unsigned short nAddr,unsigned short nDLen,unsigned char* sWriteData);
	
	//*************************接触式卡片*******************************
	//判断接触式卡片状态
	__int16 __stdcall sam_slt_getstate(HANDLE icdev,unsigned char cardno,unsigned short* cardstate);
	//接触式卡设置复位波特率
	__int16 __stdcall sam_slt_reset_baud(HANDLE icdev,unsigned char cardno, unsigned char resetBaud, unsigned char* rlen,unsigned char* resetdata);
	//接触式卡片上电复位
	__int16 __stdcall sam_slt_reset(HANDLE icdev,unsigned short delaytime, unsigned char cardno,unsigned char* rlen,unsigned char* resetdata);
	//接触式卡片下电
	__int16 __stdcall sam_slt_powerdown(HANDLE icdev,unsigned char cardno);

	//************************非接触式卡片*******************************
	//激活非接触式卡
	__int16 __stdcall open_card(HANDLE icdev,unsigned short delaytime,unsigned char* cardtype,unsigned char* snrlen,unsigned char* snr,unsigned char* rlen,unsigned char* recdata);
	//设置非接触式卡片为halt状态
	__int16 __stdcall rf_halt(HANDLE icdev,unsigned short delaytime);
	//应用层命令传输
	__int16 __stdcall card_APDU(HANDLE icdev,unsigned char cardno,int slen,unsigned char *datasend,int* rlen,unsigned char* datareceive);

	//*************************非接触式存储卡片******************************
	//激活非接触式存储卡
	__int16 __stdcall rf_card(HANDLE icdev,unsigned short delaytime,unsigned char* cardtype,unsigned char* cardID);
	//非接触式存储卡认证扇区
	__int16 __stdcall rf_authentication(HANDLE icdev,unsigned char mode,unsigned char addr, unsigned char *key);
	//非接触式存储卡读块
	__int16 __stdcall rf_read(HANDLE icdev,unsigned char addr,unsigned char* readdata);
	//非接触式存储卡写块
	__int16 __stdcall rf_write(HANDLE icdev,unsigned char addr,unsigned char* writedata);
	//非接触式存储卡读值块
	__int16 __stdcall rf_readval(HANDLE icdev,unsigned char addr,unsigned long* readvalue);
	//非接触式存储卡写值块
	__int16 __stdcall rf_initval(HANDLE icdev,unsigned char addr,unsigned long writevalue);
	//非接触式存储卡加值
	__int16 __stdcall rf_increment(HANDLE icdev,unsigned char addr,unsigned long incvalue);
	//非接触式存储卡减值
	__int16 __stdcall rf_decrement(HANDLE icdev,unsigned char addr,unsigned long decvalue);
    //非接触式存储卡传送
	__int16 __stdcall rf_transfer(HANDLE icdev,unsigned char nAdr);
	
	//****************************身份证读卡操作指令**************************
	__int16 __stdcall CLCard_Open( HANDLE icdev,unsigned short delaytime,unsigned char* cardtype,unsigned char* snrlen,unsigned char* snr,unsigned char* rlen,unsigned char* recdata );
	//身份证读卡
	__int16 __stdcall IDCard_Read(HANDLE icdev,int* rlen,unsigned char* receivedata);
	//身份证读卡
	__int16 __stdcall IDCard_ReadCard(HANDLE icdev,char* message);

	//身份证读卡, 增加设置照片保存路径
	int __stdcall IDCard_ReadCard_Extra(HANDLE icdev, char* lpInPhoneSaveFile, char* message);
	//根据索引获取身份证数据
	__int16 __stdcall IDCard_GetCardInfo(HANDLE icdev,int index,char* infodata);
	//获取二代证模块ID
	__int16 __stdcall IDCard_GetModeID(HANDLE icdev,unsigned char* nIDLen,unsigned char* sIDData);
	
	//卡ID号
	__int16 __stdcall IDCard_Read_IDNUM(HANDLE icdev,int* rlen,unsigned char* receivedata);
	
	//设置照片保存路径
	__int16 __stdcall IDCard_SetPhotoPath( char* photoPath );

	/*======================================================================================
	*新增加几个二代证接口---方案三														   *
	======================================================================================*/
	__int16 __stdcall IDCard_ReadCard_Ex(
									 HANDLE icdev, 
									 char* sName, 
									 char* sSex, 
									 char* sNation, 
									 char* sBirthday, 
									 char* sAddress,
									 char* sIDNumber, 
									 char* sIssueDepartment, 
									 char* sValidFromDate, 
									 char* sValidExpiryDate,
									 char* sReserve, 
									 int iSavePhoto, 
									 char* sPhotoBase64, 
									 int* iPhotoBase64Len, 
									 char* message);

	__int16 __stdcall IDCard_SetPhotoName( char* photoName );
    __int16 __stdcall IDCard_PhotoSave_Ex(unsigned char* cPhotoData,int nDataLen,char* strBmpFile);

	__int16 __stdcall IDCard_Name(char* cUnicodeStr, char* cName);
    __int16 __stdcall IDCard_Sex(char* cUnicodeStr, char* cSex);
    __int16 __stdcall IDCard_Nation(char* cANSIStr,char* cNation);
    __int16 __stdcall IDCard_Birthday(char* cUnicodeStr, char* cBirthday);
    __int16 __stdcall IDCard_Address(char* cUnicodeStr, char* cAddress);
    __int16 __stdcall IDCard_IDNumber(char* cUnicodeStr, char* cIDNumber);
    __int16 __stdcall IDCard_IssueDepartment(char* cUnicodeStr, char* cIssueDepartment);
    __int16 __stdcall IDCard_ValidFromDate(char* cUnicodeStr, char* cValidFromDate);
	__int16 __stdcall IDCard_ValidExpiryDate(char* cUnicodeStr, char* cValidExpiryDate);
	__int16 __stdcall IDCard_Reserve(char* cUnicodeStr, char* cReserve);
    void __stdcall IDCard_GetPhotoFile(char* cfileName);
	////////////////////////////////////////////////////////////////////////////////////////

	//获取照片文件Base64格式数据
	int GetFileBase64Buffer( char *filename, char *WideString );
    //十六进制串转Base64格式串
	long hex_base64(unsigned char *hex, unsigned char *base64,unsigned long hexlen);
    //Base64格式串转十六进制串
    long base64_hex(unsigned char *base64, unsigned char *hex,unsigned long base64len);

	//工具函数
	//加密
	__int16 __stdcall rf_encrypt(unsigned char *key,unsigned char *ptrSource, unsigned __int16 msgLen,unsigned char *ptrDest);
	
	//解密
	__int16 __stdcall rf_decrypt(unsigned char *key,unsigned char *ptrSource, unsigned __int16 msgLen,unsigned char *ptrDest);

	//hex转asc
	__int16 __stdcall hex_asc(unsigned char *hex,unsigned char *asc,unsigned long length);

	//asc转hex
	__int16 __stdcall asc_hex(unsigned char *asc,unsigned char *hex,unsigned long length);

    //asc转dec
	__int16 __stdcall asc_dec(unsigned char *asc,unsigned char *dec,unsigned long length);

	//****************************接触式存储卡操作指令**************************
	//设置接触式存储卡种类
	__int16 __stdcall contact_settype(HANDLE icdev,unsigned char cardno,unsigned char cardtype);
	//识别接触式存储卡种类
	__int16 __stdcall contact_identifytype(HANDLE icdev,unsigned char cardno,unsigned char* cardtype);
	//接触式存储卡密钥初始化(更改密码)
	__int16 __stdcall contact_passwordinit(HANDLE icdev,unsigned char cardno,unsigned char pinlen,unsigned char* pinstr);
	//接触式存储卡密码校验
	__int16 __stdcall contact_passwordcheck(HANDLE icdev,unsigned char cardno,unsigned char pinlen,unsigned char* pinstr);
	//接触式存储卡读数据
	__int16 __stdcall contact_read(HANDLE icdev,unsigned char cardno,unsigned short address,unsigned short rlen,unsigned char* readdata);
	//接触式存储卡写数据
	__int16 __stdcall contact_write(HANDLE icdev,unsigned char cardno,unsigned short address,unsigned short wlen,unsigned char* writedata);
    
     __int16 __stdcall device_writesnr(HANDLE icdev,unsigned char nSnrLen,char* sSnrData);
	//***************************** 磁条卡操作指令***************************************
	//读取磁条卡数据
	__int16 __stdcall magnetic_read(HANDLE icdev, unsigned char timeout,MagCard* mag_card);
	__int16 __stdcall set_magnetic_mode(HANDLE icdev,unsigned char nmode);
	
	//***************************** 密码键盘***************************************
	
	//DES加密函数
	__int16 __stdcall des_encrypt(unsigned char *key,unsigned char *ptrSource, unsigned char msgLen,unsigned char *ptrDest);
	//DES解密函数
	__int16 __stdcall des_decrypt(unsigned char *key,unsigned char *ptrSource, unsigned char msgLen,unsigned char *ptrDest);
	//3DES加密函数
	__int16 __stdcall des3_encrypt(unsigned char *key,unsigned char *ptrSource, unsigned char msgLen,unsigned char *ptrDest);
	//3Des解密函数
	__int16 __stdcall des3_decrypt(unsigned char *key,unsigned char *ptrSource, unsigned char msgLen,unsigned char *ptrDest);
	
	//密码键盘数据解密
	__int16 __stdcall pwd_decrypt(unsigned char *ptrSource, unsigned char nDataLen,unsigned char *ptrDest);
	//密码键盘下载主密钥
	__int16 __stdcall mw_kb_downloadmainkey(HANDLE icdev,int destype,int mainkeyno,unsigned char *mainkey);
	//密码键盘下载工作密钥
	__int16 __stdcall mw_kb_downloaduserkey(HANDLE icdev, int destype, int mainkeyno, int userkeyno, unsigned char *userkey);
	//密码键盘激活主密钥和工作密钥
	__int16 __stdcall mw_kb_activekey(HANDLE icdev, int mainkeyno,int userkeyno);
	//密码键盘设置输入密码长度
	__int16 __stdcall mw_kb_setpasslen(HANDLE icdev, int passlen);
	//密码键盘设置超时时间
	__int16 __stdcall mw_kb_settimeout(HANDLE icdev, int timeout);
	//密码键盘获取明文密码
	__int16 __stdcall mw_kb_getpin(HANDLE icdev, int type, unsigned char *planpin);
	//密码键盘获取密文密码
	__int16 __stdcall mw_kb_getenpin(HANDLE icdev, int type, unsigned char *cardno, unsigned char *planpin);

	//fc 20130806 获取设备状态
	__int16 __stdcall get_device_status(HANDLE icdev,unsigned char *ndev_status);
	
	//2014-06-10add
	//删除所有照片信息文件
	__int16 __stdcall delete_all_photofile();

	//2014-06-27-add
	__int16 __stdcall IDCard_ReadCard_ExTwo(HANDLE icdev, bool bIsNeedBmpFile, char* lpInFilePath, char* message);
	__int16 __stdcall iWlttoBmp(const char* lpWltFilePath, char* message);

	/*==================================================================================
	*Auther:yangyj																	   *
	*Date:2014-06-10																   *
	*新增1604卡操作函数																   *
	*注意在操作1604卡之前，必须先去识别卡类型，然后再去设置卡类型.					   *
	*识别卡类型：contact_identifytype												   *
	*设置卡类型:contact_settype														   *
	==================================================================================*/

	//读数据
	int __stdcall srd_1604(HANDLE icdev,int zone,int offset,int len,unsigned char *data_buffer);
	//写数据
	int __stdcall swr_1604(HANDLE icdev,int zone,int offset,int len,unsigned char *data_buffer);
	//写密码
	int __stdcall wsc_1604(HANDLE icdev,int zone,int len,unsigned char *data_buffer);
	//擦除数据
	int __stdcall ser_1604(HANDLE icdev,int zone,int offset,int len);
	//校验密码
	int __stdcall csc_1604(HANDLE icdev,int zone,int len, unsigned char *data_buffer);
	//校验擦除密码
	int __stdcall cesc_1604(HANDLE icdev,int zone,int len,unsigned char *data_buffer);
	//伪个人化
	int __stdcall fakefus_1604(HANDLE icdev,int mode);
	//个人化
	int __stdcall psnl_1604(HANDLE icdev);
	
	/*********************PBOC 接口********************************/
	int __stdcall iReadICCardNoAndName(HANDLE icdev, int nCardType, unsigned char* ICCardNo, unsigned char* ICCardName, char* lpErrMsg);
	int __stdcall GenF55(HANDLE icdev,char *aryInput,char *AIDList,char *serno,char *svalue,int *svauelen);
}
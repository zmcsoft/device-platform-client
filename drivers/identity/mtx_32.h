
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
	//*************************** �豸������Ϣ ********************************************
	//���豸
	HANDLE  __stdcall device_open(__int16 port,unsigned long baud);
	__int16 __stdcall ReSetupComm(HANDLE idComDev);
	//�ر��豸
	__int16 __stdcall device_close(HANDLE icdev);
	//��ȡ�豸����
	__int16 __stdcall device_gettype(unsigned char* devicetype);
	//���ô���ͨѶ������
	__int16 __stdcall device_setbaud(HANDLE icdev,unsigned char module,unsigned long baud);
	//��ȡ��д���汾��Ϣ
	__int16 __stdcall device_version(HANDLE icdev,unsigned char module,unsigned char* verlen,unsigned char* verdata);
	//��д����λ
	__int16 __stdcall device_reset(HANDLE icdev,unsigned char module);
	//��д������������
	__int16 __stdcall device_beep(HANDLE icdev, unsigned short delaytime,unsigned char times);
	//LED�ƿ���
	__int16 __stdcall device_ledctrl(HANDLE icdev,unsigned char ledctrl);
	//����Ʒ���к�
	__int16 __stdcall device_readsnr(HANDLE icdev,unsigned char nSnrLen,char* sSnrData);
	//д��Ʒ���к�
	__int16 __stdcall dev_writesnr(HANDLE icdev,unsigned char nSnrLen,char* sSnrData);

	//��EEPROM
	__int16 __stdcall dev_readeeprom(HANDLE icdev,unsigned short nAddr,unsigned short nDLen,unsigned char* sReadData);
	//дEEPROM
	__int16 __stdcall dev_writeeeprom(HANDLE icdev,unsigned short nAddr,unsigned short nDLen,unsigned char* sWriteData);
	
	//*************************�Ӵ�ʽ��Ƭ*******************************
	//�жϽӴ�ʽ��Ƭ״̬
	__int16 __stdcall sam_slt_getstate(HANDLE icdev,unsigned char cardno,unsigned short* cardstate);
	//�Ӵ�ʽ�����ø�λ������
	__int16 __stdcall sam_slt_reset_baud(HANDLE icdev,unsigned char cardno, unsigned char resetBaud, unsigned char* rlen,unsigned char* resetdata);
	//�Ӵ�ʽ��Ƭ�ϵ縴λ
	__int16 __stdcall sam_slt_reset(HANDLE icdev,unsigned short delaytime, unsigned char cardno,unsigned char* rlen,unsigned char* resetdata);
	//�Ӵ�ʽ��Ƭ�µ�
	__int16 __stdcall sam_slt_powerdown(HANDLE icdev,unsigned char cardno);

	//************************�ǽӴ�ʽ��Ƭ*******************************
	//����ǽӴ�ʽ��
	__int16 __stdcall open_card(HANDLE icdev,unsigned short delaytime,unsigned char* cardtype,unsigned char* snrlen,unsigned char* snr,unsigned char* rlen,unsigned char* recdata);
	//���÷ǽӴ�ʽ��ƬΪhalt״̬
	__int16 __stdcall rf_halt(HANDLE icdev,unsigned short delaytime);
	//Ӧ�ò������
	__int16 __stdcall card_APDU(HANDLE icdev,unsigned char cardno,int slen,unsigned char *datasend,int* rlen,unsigned char* datareceive);

	//*************************�ǽӴ�ʽ�洢��Ƭ******************************
	//����ǽӴ�ʽ�洢��
	__int16 __stdcall rf_card(HANDLE icdev,unsigned short delaytime,unsigned char* cardtype,unsigned char* cardID);
	//�ǽӴ�ʽ�洢����֤����
	__int16 __stdcall rf_authentication(HANDLE icdev,unsigned char mode,unsigned char addr, unsigned char *key);
	//�ǽӴ�ʽ�洢������
	__int16 __stdcall rf_read(HANDLE icdev,unsigned char addr,unsigned char* readdata);
	//�ǽӴ�ʽ�洢��д��
	__int16 __stdcall rf_write(HANDLE icdev,unsigned char addr,unsigned char* writedata);
	//�ǽӴ�ʽ�洢����ֵ��
	__int16 __stdcall rf_readval(HANDLE icdev,unsigned char addr,unsigned long* readvalue);
	//�ǽӴ�ʽ�洢��дֵ��
	__int16 __stdcall rf_initval(HANDLE icdev,unsigned char addr,unsigned long writevalue);
	//�ǽӴ�ʽ�洢����ֵ
	__int16 __stdcall rf_increment(HANDLE icdev,unsigned char addr,unsigned long incvalue);
	//�ǽӴ�ʽ�洢����ֵ
	__int16 __stdcall rf_decrement(HANDLE icdev,unsigned char addr,unsigned long decvalue);
    //�ǽӴ�ʽ�洢������
	__int16 __stdcall rf_transfer(HANDLE icdev,unsigned char nAdr);
	
	//****************************���֤��������ָ��**************************
	__int16 __stdcall CLCard_Open( HANDLE icdev,unsigned short delaytime,unsigned char* cardtype,unsigned char* snrlen,unsigned char* snr,unsigned char* rlen,unsigned char* recdata );
	//���֤����
	__int16 __stdcall IDCard_Read(HANDLE icdev,int* rlen,unsigned char* receivedata);
	//���֤����
	__int16 __stdcall IDCard_ReadCard(HANDLE icdev,char* message);

	//���֤����, ����������Ƭ����·��
	int __stdcall IDCard_ReadCard_Extra(HANDLE icdev, char* lpInPhoneSaveFile, char* message);
	//����������ȡ���֤����
	__int16 __stdcall IDCard_GetCardInfo(HANDLE icdev,int index,char* infodata);
	//��ȡ����֤ģ��ID
	__int16 __stdcall IDCard_GetModeID(HANDLE icdev,unsigned char* nIDLen,unsigned char* sIDData);
	
	//��ID��
	__int16 __stdcall IDCard_Read_IDNUM(HANDLE icdev,int* rlen,unsigned char* receivedata);
	
	//������Ƭ����·��
	__int16 __stdcall IDCard_SetPhotoPath( char* photoPath );

	/*======================================================================================
	*�����Ӽ�������֤�ӿ�---������														   *
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

	//��ȡ��Ƭ�ļ�Base64��ʽ����
	int GetFileBase64Buffer( char *filename, char *WideString );
    //ʮ�����ƴ�תBase64��ʽ��
	long hex_base64(unsigned char *hex, unsigned char *base64,unsigned long hexlen);
    //Base64��ʽ��תʮ�����ƴ�
    long base64_hex(unsigned char *base64, unsigned char *hex,unsigned long base64len);

	//���ߺ���
	//����
	__int16 __stdcall rf_encrypt(unsigned char *key,unsigned char *ptrSource, unsigned __int16 msgLen,unsigned char *ptrDest);
	
	//����
	__int16 __stdcall rf_decrypt(unsigned char *key,unsigned char *ptrSource, unsigned __int16 msgLen,unsigned char *ptrDest);

	//hexתasc
	__int16 __stdcall hex_asc(unsigned char *hex,unsigned char *asc,unsigned long length);

	//ascתhex
	__int16 __stdcall asc_hex(unsigned char *asc,unsigned char *hex,unsigned long length);

    //ascתdec
	__int16 __stdcall asc_dec(unsigned char *asc,unsigned char *dec,unsigned long length);

	//****************************�Ӵ�ʽ�洢������ָ��**************************
	//���ýӴ�ʽ�洢������
	__int16 __stdcall contact_settype(HANDLE icdev,unsigned char cardno,unsigned char cardtype);
	//ʶ��Ӵ�ʽ�洢������
	__int16 __stdcall contact_identifytype(HANDLE icdev,unsigned char cardno,unsigned char* cardtype);
	//�Ӵ�ʽ�洢����Կ��ʼ��(��������)
	__int16 __stdcall contact_passwordinit(HANDLE icdev,unsigned char cardno,unsigned char pinlen,unsigned char* pinstr);
	//�Ӵ�ʽ�洢������У��
	__int16 __stdcall contact_passwordcheck(HANDLE icdev,unsigned char cardno,unsigned char pinlen,unsigned char* pinstr);
	//�Ӵ�ʽ�洢��������
	__int16 __stdcall contact_read(HANDLE icdev,unsigned char cardno,unsigned short address,unsigned short rlen,unsigned char* readdata);
	//�Ӵ�ʽ�洢��д����
	__int16 __stdcall contact_write(HANDLE icdev,unsigned char cardno,unsigned short address,unsigned short wlen,unsigned char* writedata);
    
     __int16 __stdcall device_writesnr(HANDLE icdev,unsigned char nSnrLen,char* sSnrData);
	//***************************** ����������ָ��***************************************
	//��ȡ����������
	__int16 __stdcall magnetic_read(HANDLE icdev, unsigned char timeout,MagCard* mag_card);
	__int16 __stdcall set_magnetic_mode(HANDLE icdev,unsigned char nmode);
	
	//***************************** �������***************************************
	
	//DES���ܺ���
	__int16 __stdcall des_encrypt(unsigned char *key,unsigned char *ptrSource, unsigned char msgLen,unsigned char *ptrDest);
	//DES���ܺ���
	__int16 __stdcall des_decrypt(unsigned char *key,unsigned char *ptrSource, unsigned char msgLen,unsigned char *ptrDest);
	//3DES���ܺ���
	__int16 __stdcall des3_encrypt(unsigned char *key,unsigned char *ptrSource, unsigned char msgLen,unsigned char *ptrDest);
	//3Des���ܺ���
	__int16 __stdcall des3_decrypt(unsigned char *key,unsigned char *ptrSource, unsigned char msgLen,unsigned char *ptrDest);
	
	//����������ݽ���
	__int16 __stdcall pwd_decrypt(unsigned char *ptrSource, unsigned char nDataLen,unsigned char *ptrDest);
	//���������������Կ
	__int16 __stdcall mw_kb_downloadmainkey(HANDLE icdev,int destype,int mainkeyno,unsigned char *mainkey);
	//����������ع�����Կ
	__int16 __stdcall mw_kb_downloaduserkey(HANDLE icdev, int destype, int mainkeyno, int userkeyno, unsigned char *userkey);
	//������̼�������Կ�͹�����Կ
	__int16 __stdcall mw_kb_activekey(HANDLE icdev, int mainkeyno,int userkeyno);
	//������������������볤��
	__int16 __stdcall mw_kb_setpasslen(HANDLE icdev, int passlen);
	//����������ó�ʱʱ��
	__int16 __stdcall mw_kb_settimeout(HANDLE icdev, int timeout);
	//������̻�ȡ��������
	__int16 __stdcall mw_kb_getpin(HANDLE icdev, int type, unsigned char *planpin);
	//������̻�ȡ��������
	__int16 __stdcall mw_kb_getenpin(HANDLE icdev, int type, unsigned char *cardno, unsigned char *planpin);

	//fc 20130806 ��ȡ�豸״̬
	__int16 __stdcall get_device_status(HANDLE icdev,unsigned char *ndev_status);
	
	//2014-06-10add
	//ɾ��������Ƭ��Ϣ�ļ�
	__int16 __stdcall delete_all_photofile();

	//2014-06-27-add
	__int16 __stdcall IDCard_ReadCard_ExTwo(HANDLE icdev, bool bIsNeedBmpFile, char* lpInFilePath, char* message);
	__int16 __stdcall iWlttoBmp(const char* lpWltFilePath, char* message);

	/*==================================================================================
	*Auther:yangyj																	   *
	*Date:2014-06-10																   *
	*����1604����������																   *
	*ע���ڲ���1604��֮ǰ��������ȥʶ�����ͣ�Ȼ����ȥ���ÿ�����.					   *
	*ʶ�����ͣ�contact_identifytype												   *
	*���ÿ�����:contact_settype														   *
	==================================================================================*/

	//������
	int __stdcall srd_1604(HANDLE icdev,int zone,int offset,int len,unsigned char *data_buffer);
	//д����
	int __stdcall swr_1604(HANDLE icdev,int zone,int offset,int len,unsigned char *data_buffer);
	//д����
	int __stdcall wsc_1604(HANDLE icdev,int zone,int len,unsigned char *data_buffer);
	//��������
	int __stdcall ser_1604(HANDLE icdev,int zone,int offset,int len);
	//У������
	int __stdcall csc_1604(HANDLE icdev,int zone,int len, unsigned char *data_buffer);
	//У���������
	int __stdcall cesc_1604(HANDLE icdev,int zone,int len,unsigned char *data_buffer);
	//α���˻�
	int __stdcall fakefus_1604(HANDLE icdev,int mode);
	//���˻�
	int __stdcall psnl_1604(HANDLE icdev);
	
	/*********************PBOC �ӿ�********************************/
	int __stdcall iReadICCardNoAndName(HANDLE icdev, int nCardType, unsigned char* ICCardNo, unsigned char* ICCardName, char* lpErrMsg);
	int __stdcall GenF55(HANDLE icdev,char *aryInput,char *AIDList,char *serno,char *svalue,int *svauelen);
}
/////////////////////////////////////////////////////////////////
//////////////////// DLL???????????/////////////////////////////
//CommOpen(101-104)
#define Bad_CommOpen -101//?????.
#define Bad_CommSet -102//???????????,(102)??????CommSet??????

#define Bad_CommTimeouts -999
#define Bad_CommQueue -998
//CommClose(105)
#define Bad_CommClose -105//??????.

//Send(106)
#define Bad_SendByte -106
#define Bad_SendData_Len -107

//Receive(106-107)
#define Bad_ReceiveData_ReadFile -103
#define Bad_ReceiveData_Len -104

// ????????
#define Bad_Parameter -200//???????????(???????)


/**********???????*********/
#define CardBoxEmpty				0xA0//????
#define CardJam						0xA2//???????
#define RecycleBoxFull				0xA1//?????俨??
#define RecycleBoxNotInPosition		0xA3//?????俨??
#define CardBoxNotInPosition		0xA4//????????λ
#define HookMoveBox1NotEmpty		0xA7//????1 2???п???????????????2???,??????????????豸????и?λ????
#define HookMoveTimeOut				0xA8//??????λ???????????????????
#define MoverMoveError				0xAE//???????????
#define PlatformWaitCard			0xAF//???????????
#define PlatformFlipCard			0xB0//?????????
#define Hook1MoveFail        		0xB1//???????1???
#define Hook2MoveFail        		0xB2//???????2???
#define CardIsInPlatform			0xB3//?????????,??????д??????????????л??????
#define SendCardToPlatformFail		0xB4//???????????
#define CardIsNotInPlatform			0xB5//???????????????л?????????????
#define SendCardFailed				0xC6//???????
#define RecyclePlatformCard			0xC7//????????????????
#define RecycleCardFailed			0xC8//????????????????????????
#define HaveCardAndSendCardFailed	0xC9//??????????п??????????
#define HaveNoCardRecycleCardFailed	0xCA//????????????????????????????

//######################################################//
//==================???????===========================//
//+++++++++++++++++++++++++++++++++++++++++++++++++++++//
HANDLE __stdcall K2616_CommOpen(char *Port);
HANDLE __stdcall K2616_CommOpenWithBaud(char *Port, unsigned int _data);
int __stdcall K2616_CommClose(HANDLE ComHandle);
int __stdcall K2616_GetSysVerion(HANDLE ComHandle, char *strVerion);
//+++++++++++++++++++++++++++++++++++++++++++++++++++++//
int __stdcall K2616_SetCommBaud(HANDLE ComHandle, unsigned int _Baud);
int __stdcall K2616_Reset(HANDLE ComHandle, BYTE _PM,BYTE _VerCode[50]);
int __stdcall K2616_CheckCardPosition(HANDLE ComHandle, BYTE _Position[]);
int __stdcall K2616_CheckSensorStates(HANDLE ComHandle, BYTE _States[]);
int __stdcall K2616_CheckSensorVoltage(HANDLE ComHandle, BYTE _Valtage[]);
int __stdcall K2616_EnterCard(HANDLE ComHandle,BYTE EnterType,DWORD WaitTime);
int __stdcall K2616_EnterCardUntime(HANDLE ComHandle, BYTE EnterType);//;;;????????????
int __stdcall K2616_MoveCard(HANDLE ComHandle, BYTE _PM);
int __stdcall K2616_AutoTestICCard(HANDLE ComHandle, BYTE *_IcCardType);
int __stdcall K2616_AutoTestRFIDCard(HANDLE ComHandle, BYTE *_RFIDCardType);
int __stdcall K2616_PowerOn(HANDLE ComHandle);
int __stdcall K2616_PowerOff(HANDLE ComHandle);
int __stdcall K2616_Led1Control(HANDLE ComHandle, BYTE _PM);
int __stdcall K2616_SetLedFlickerRate(HANDLE ComHandle, BYTE _PM, BYTE _Rate);
int __stdcall K2616_Led2Control(HANDLE ComHandle, BYTE _PM);
int __stdcall K2616_ReadBarcode(HANDLE ComHandle, BYTE *szData);
int __stdcall K2616_Eot(HANDLE ComHandle);
//++++++++++++++++++++++++++++++++++++++++++++++++++++//
int __stdcall K2616_ReadMagcardDecode(HANDLE ComHandle, BYTE _track, DWORD *_DataLen,BYTE _BlockData[]);
int __stdcall K2616_ReadMagcardUNDecode(HANDLE ComHandle,BYTE _track, DWORD *_DataLen,BYTE _BlockData[]);
int __stdcall K2616_ClearMagCardData(HANDLE ComHandle);
//++++++++++++++++++++++++++++++++++++++++++++++++++++//
int __stdcall K2616_S50DetectCard(HANDLE ComHandle);
int __stdcall K2616_S50GetCardID(HANDLE ComHandle, BYTE _CardID[4]);
int __stdcall K2616_S50LoadSecKey(HANDLE ComHandle, BYTE _BlockAddr, BYTE _KEYType, BYTE _KEY[6]);
int __stdcall K2616_S50ReadBlock(HANDLE ComHandle, BYTE _Block, BYTE _BlockData[16]);
int __stdcall K2616_S50WriteBlock(HANDLE ComHandle, BYTE _Block, BYTE _BlockData[16]);
int __stdcall K2616_S50InitValue(HANDLE ComHandle, BYTE _Block, BYTE _Data[16]);
int __stdcall K2616_S50Increment(HANDLE ComHandle, BYTE _Block, BYTE _Data[4]);
int __stdcall K2616_S50Decrement(HANDLE ComHandle, BYTE _Block, BYTE _Data[4]);
int __stdcall K2616_S50Halt(HANDLE ComHandle);
int __stdcall K2616_S70DetectCard(HANDLE ComHandle);
int __stdcall K2616_S70GetCardID(HANDLE ComHandle, BYTE _CardID[4]);
int __stdcall K2616_S70LoadSecKey(HANDLE ComHandle, BYTE _BlockAddr, BYTE _KEYType, BYTE _KEY[6]);
int __stdcall K2616_S70ReadBlock(HANDLE ComHandle, BYTE _Block, BYTE _BlockData[16]);
int __stdcall K2616_S70WriteBlock(HANDLE ComHandle, BYTE _Block, BYTE _BlockData[16]);
int __stdcall K2616_S70InitValue(HANDLE ComHandle, BYTE _Block, BYTE _Data[16]);
int __stdcall K2616_S70Increment(HANDLE ComHandle, BYTE _Block, BYTE _Data[4]);
int __stdcall K2616_S70Decrement(HANDLE ComHandle, BYTE _Block, BYTE _Data[4]);
int __stdcall K2616_S70Halt(HANDLE ComHandle);
int __stdcall K2616_ULDetectCard(HANDLE ComHandle);
int __stdcall K2616_ULGetCardID(HANDLE ComHandle, BYTE _CardID[7]);
int __stdcall K2616_ULReadBlock(HANDLE ComHandle, BYTE _Block, BYTE _BlockData[16]);
int __stdcall K2616_ULWriteBlock(HANDLE ComHandle, BYTE _Block, BYTE _BlockData[16]);
int __stdcall K2616_ULHalt(HANDLE ComHandle);
int __stdcall K2616_15693GetUid(HANDLE ComHandle,BYTE UID[8]);
int __stdcall K2616_15693ReadData(HANDLE ComHandle,bool bUid,BYTE Uid[8],BYTE BlockAddr, BYTE BlockLen, BYTE _BlockData[],BYTE* ReadBlockLen);
int __stdcall K2616_15693WriteData(HANDLE ComHandle,bool bUid,BYTE Uid[8],BYTE BlockAddr, BYTE BlockLen, BYTE _BlockData[],BYTE *WriteBlockLen);
int __stdcall K2616_15693ChooseCard(HANDLE ComHandle,bool bUid,BYTE Uid[8]);
int __stdcall K2616_15693GetMessage(HANDLE ComHandle,bool bUid,BYTE Uid[8],BYTE Message[15]);
int __stdcall K2616_15693ReadSafeBit(HANDLE ComHandle,bool bUid,BYTE Uid[8],BYTE BlockAddr, BYTE BlockLen,BYTE* ReadBlockLen,BYTE BlockLockStatus[]);
int __stdcall K2616_15693WriteDSFID(HANDLE ComHandle,bool bUid,BYTE Uid[8],BYTE WriteBit);
int __stdcall K2616_15693WriteAFI(HANDLE ComHandle,bool bUid,BYTE Uid[8],BYTE WriteBit);
int __stdcall K2616_15693LockBlock(HANDLE ComHandle,bool bUid,BYTE Uid[8],BYTE LockAddress);
int __stdcall K2616_15693LockAFI(HANDLE ComHandle,bool bUid,BYTE Uid[8]);
int __stdcall K2616_15693LockDSFID(HANDLE ComHandle,bool bUid,BYTE Uid[8]);

int __stdcall K2616_GetIdCardNo(HANDLE ComHandle, char Id[10]);  


//+++++++++++++++++++++++++++++++++++++++++++++++++++//
int __stdcall K2616_IcCardPowerOn(HANDLE ComHandle);
int __stdcall K2616_IcCardPowerOff(HANDLE ComHandle);
//+++++++++++++++++++++++++++++++++++++++++++++++++++//
int __stdcall K2616_CpuCardColdReset(HANDLE ComHandle, BYTE *_CPUType,BYTE _RstData[], BYTE *_RstdataLen);
int __stdcall K2616_CpuCardPowerOff(HANDLE ComHandle);
int __stdcall K2616_CpuCardWarmReset(HANDLE ComHandle, BYTE _VOLTAGE,BYTE *_CPUType, BYTE _RstData[], BYTE *_RstdataLen);
int __stdcall K2616_CPUT0APDU(HANDLE ComHandle, int _dataLen, BYTE _APDUData[], BYTE _exData[], int *_exdataLen);
int __stdcall K2616_CPUT1APDU(HANDLE ComHandle, int _dataLen, BYTE _APDUData[], BYTE _exData[], int *_exdataLen);
int __stdcall K2616_ReadInfoPOP(HANDLE ComHandle, BYTE _exData[], int *_exdataLen);
//+++++++++++++++++++++++++++++++++++++++++++++++++++//
int __stdcall K2616_SimColdReset(HANDLE ComHandle, BYTE *_SIMTYPE,BYTE _exData[], int *_exdataLen);
int __stdcall K2616_SimCardPowerOff(HANDLE ComHandle);
int __stdcall K2616_SimT0APDU(HANDLE ComHandle,  int _dataLen, BYTE _APDUData[], BYTE _exData[], int *_exdataLen);
int __stdcall K2616_SimT1APDU(HANDLE ComHandle,  int _dataLen, BYTE _APDUData[], BYTE _exData[], int *_exdataLen);
int __stdcall K2616_SimSelect(HANDLE ComHandle, BYTE SIMNo);
int __stdcall K2616_SimWarmReset(HANDLE ComHandle,BYTE _VOLTAGE, BYTE *_SIMTYPE,BYTE _exData[], int *_exdataLen);
//+++++++++++++++++++++++++++++++++++++++++++++++++++//
int __stdcall K2616_AT24CXXRead(HANDLE ComHandle, BYTE _CardType, int _Address, BYTE _dataLen, BYTE _BlockData[]);
int __stdcall K2616_AT24CXXWrite(HANDLE ComHandle, BYTE _CardType, int _Address, BYTE _dataLen, BYTE _BlockData[]);
//+++++++++++++++++++++++++++++++++++++++++++++++++++//
int __stdcall K2616_AT45DB041Reset(HANDLE ComHandle);
int __stdcall K2616_AT45DB041Read(HANDLE ComHandle, int  _Address, BYTE _BlockData[]);
int __stdcall K2616_AT45DB041Write(HANDLE ComHandle, int  _Address, BYTE _BlockData[264]);
//+++++++++++++++++++++++++++++++++++++++++++++++++++//
int __stdcall K2616_AT88SC102Reset(HANDLE ComHandle);
int __stdcall K2616_AT88SC102VerifyPWD(HANDLE ComHandle, BYTE _PWData[2]);
int __stdcall K2616_AT88SC102Read(HANDLE ComHandle, BYTE _Address, BYTE _dataLen, BYTE _BlockData[]);
int __stdcall K2616_AT88SC102Security1Erasure(HANDLE ComHandle, BYTE _Address, BYTE _dataLen);
int __stdcall K2616_AT88SC102Security2ErasureApp1(HANDLE ComHandle,BYTE _Security2App1PW[6]);
int __stdcall K2616_AT88SC102Security2ErasureApp2(HANDLE ComHandle,BYTE _EC2, BYTE _Security2App2PW[4]);
int __stdcall K2616_AT88SC102Write(HANDLE ComHandle, BYTE _Address, BYTE _dataLen, BYTE _BlockData[]);
int __stdcall K2616_AT88SC102WritePWD(HANDLE ComHandle, BYTE _PWType,BYTE _PWData[]);
int __stdcall K2616_AT88SC102SetMode(HANDLE ComHandle, BYTE _CtrlMode);
int __stdcall K2616_AT88SC102DisableEC2(HANDLE ComHandle);
//+++++++++++++++++++++++++++++++++++++++++++++++++++//
int __stdcall K2616_AT88SC1604Reset(HANDLE ComHandle);
int __stdcall K2616_AT88SC1604VerifyPWD(HANDLE ComHandle, BYTE _PWType, BYTE _PWData[]);
int __stdcall K2616_AT88SC1604Read(HANDLE ComHandle,  int _Address, BYTE _dataLen, BYTE _BlockData[]);
int __stdcall K2616_AT88SC1604Erasure(HANDLE ComHandle, int _Address, BYTE _dataLen);
int __stdcall K2616_AT88SC1604Write(HANDLE ComHandle, int _Address, BYTE _dataLen, BYTE _BlockData[]);
int __stdcall K2616_AT88SC1604WritePWD(HANDLE ComHandle, BYTE _PWType,BYTE _PWData[]);
int __stdcall K2616_AT88SC1604Personalization(HANDLE ComHandle,BYTE _PM);
//+++++++++++++++++++++++++++++++++++++++++++++++++++//
int __stdcall K2616_AT88SC1608Reset(HANDLE ComHandle);
int __stdcall K2616_AT88SC1608VerifyPWD(HANDLE ComHandle, BYTE _PWType, BYTE _PWData[]);
int __stdcall K2616_AT88SC1608Read(HANDLE ComHandle, BYTE _Index, BYTE _Address, BYTE _dataLen, BYTE _BlockData[]);
int __stdcall K2616_AT88SC1608Write(HANDLE ComHandle, BYTE _Index, BYTE _Address, BYTE _dataLen, BYTE _BlockData[]);
int __stdcall K2616_AT88SC1608ReadFUSE(HANDLE ComHandle, BYTE * _FAB, BYTE * _CMA, BYTE * _PER);
int __stdcall K2616_AT88SC1608WriteFUSE(HANDLE ComHandle);
int __stdcall K2616_AT88SC1608InitAuth(HANDLE ComHandle, BYTE _DATA[8]);
int __stdcall K2616_AT88SC1608VerifyAuth(HANDLE ComHandle, BYTE _DATA[8]);
int __stdcall K2616_AT88SC1608WritePWD(HANDLE ComHandle, BYTE _PWType,BYTE _PWData[]);
//+++++++++++++++++++++++++++++++++++++++++++++++++++//
int __stdcall K2616_SLE4442Reset(HANDLE ComHandle);
int __stdcall K2616_SLE4442VerifyPWD(HANDLE ComHandle, BYTE _PWData[3]);
int __stdcall K2616_SLE4442Read(HANDLE ComHandle, BYTE _Address, BYTE _dataLen, BYTE _BlockData[]);
int __stdcall K2616_SLE4442ReadProtectBit(HANDLE ComHandle, BYTE _BlockData[32]);
int __stdcall K2616_SLE4442ReadSafety(HANDLE ComHandle, BYTE _BlockData[4]);
int __stdcall K2616_SLE4442Write(HANDLE ComHandle, BYTE _Address, BYTE _dataLen, BYTE _BlockData[]);
int __stdcall K2616_SLE4442WriteProtectBit(HANDLE ComHandle, BYTE _Address,BYTE _DataLen,BYTE _BlockData[32]);
int __stdcall K2616_SLE4442ChangePWD(HANDLE ComHandle, BYTE _PWData[3]);
//+++++++++++++++++++++++++++++++++++++++++++++++++++//
int __stdcall K2616_Sle4428Reset(HANDLE ComHandle);
int __stdcall K2616_Sle4428VerifyPWD(HANDLE ComHandle, BYTE _PWData[2]);
int __stdcall K2616_Sle4428Read(HANDLE ComHandle, int _Address, BYTE _dataLen, BYTE _BlockData[]);
int __stdcall K2616_Sle4428ReadProtectBit(HANDLE ComHandle, int _Address, BYTE _dataLen, BYTE _BlockData[]);
int __stdcall K2616_Sle4428Write(HANDLE ComHandle, int _Address, BYTE _dataLen, BYTE _BlockData[]);
int __stdcall K2616_Sle4428WriteP(HANDLE ComHandle, int _Address, BYTE _dataLen, BYTE _BlockData[]);
int __stdcall K2616_Sle4428ChangePWD(HANDLE ComHandle, BYTE _PWData[2]);
//++++++++++++++++++++++++++++++++++++++++++++++++++//

int __stdcall K2616_CPUTypeACardPowerOn(HANDLE ComHandle, BYTE szATR[16]);
int __stdcall K2616_CPUTypeAAPDU(HANDLE ComHandle, BYTE SCH, int _dataLen, BYTE _APDUData[], BYTE* RCH,BYTE _exData[], int *_exdataLen);
int __stdcall K2616_GetMachineIDNum(HANDLE ComHandle, BYTE *length, BYTE IDNum[]);
int __stdcall K2616_SetMachineIDNum(HANDLE ComHandle, BYTE length, BYTE IDNum[]);
int __stdcall K2616_TestCmd(HANDLE ComHandle);
int __stdcall K2616_SendCard(HANDLE ComHandle, BYTE _PM);
int __stdcall K2616_RecycleCard(HANDLE ComHandle, BYTE _PM);

int __stdcall K2616_CPUTypeACardPowerOn(HANDLE ComHandle, BYTE szATR[16]);
int __stdcall K2616_CPUTypeAAPDU(HANDLE ComHandle, BYTE SCH, int _dataLen, BYTE _APDUData[], BYTE* RCH,BYTE _exData[], int *_exdataLen);
int __stdcall K2616_GetMachineIDNum(HANDLE ComHandle, BYTE *length, BYTE IDNum[]);
int __stdcall K2616_SetMachineIDNum(HANDLE ComHandle, BYTE length, BYTE IDNum[]);
int __stdcall K2616_TestCmd(HANDLE ComHandle);


int __stdcall K2616_SelectPSE(HANDLE ComHandle);
int __stdcall K2616_ReadPSEDirFile(HANDLE ComHandle);
int __stdcall K2616_SelectADFFile(HANDLE ComHandle);
int __stdcall K2616_GPOCMD(HANDLE ComHandle);
int __stdcall K2616_ReadRecordFile(HANDLE ComHandle,int *len,BYTE *CardID);
int __stdcall K2616_ReadICCardNum(HANDLE ComHandle,int *len,BYTE *CardID);

int __stdcall K2616_CPUTypeASelectPSE(HANDLE ComHandle);
int __stdcall K2616_CPUTypeAReadPSEDirFile(HANDLE ComHandle);
int __stdcall K2616_CPUTypeASelectADFFile(HANDLE ComHandle);
int __stdcall K2616_CPUTypeAGPOCMD(HANDLE ComHandle);
int __stdcall K2616_CPUTypeAReadRecordFile(HANDLE ComHandle,int *len,BYTE *CardID);
int __stdcall K2616_CPUTypeAReadICCardNum(HANDLE ComHandle,int *len,BYTE *CardID);
int __stdcall K2616_CPUTypeAReadICCardNum(HANDLE ComHandle,int *len,BYTE *CardID);
int __stdcall K2616_ReadRetainCount(HANDLE ComHandle, int *Count);
int __stdcall K2616_ClearRetainCount(HANDLE ComHandle);

int __stdcall K2616_SelectSocialSecurityApp(HANDLE ComHandle);
int __stdcall K2616_SelectSocialSecurityBasicFile(HANDLE ComHandle);
//int __stdcall K2616_LNSelectSocialSecurityBasicFile(HANDLE ComHandle);
//int __stdcall K2616_LNSelectSocialSecurityDataFile(HANDLE ComHandle);
int __stdcall K2616_ReadSocialSecurityCardID(HANDLE ComHandle,BYTE IDNum[9]);
int __stdcall K2616_ReadSocialSecurityIDNum(HANDLE ComHandle,BYTE IDNum[18]);
int __stdcall K2616_ReadSocialSecurityName(HANDLE ComHandle,BYTE Name[30]);
int __stdcall K2616_ReadSocialSecurityGender(HANDLE ComHandle,BYTE *Sex);
int __stdcall K2616_ReadSocialSecurityNation(HANDLE ComHandle,BYTE *Nation);
int __stdcall K2616_ReadSocialSecurityRegional(HANDLE ComHandle,BYTE Regional[3]);
int __stdcall K2616_ReadSocialSecurityBirthDay(HANDLE ComHandle,BYTE BirthDay[4]);
int __stdcall K2616_ReadSocialSecurityInfo(HANDLE ComHandle,BYTE IDNum[18],BYTE Name[30],BYTE *Sex,BYTE *Nation,BYTE Regional[3],BYTE BirthDay[4]);
int __stdcall K2616_AutoReadSocialSecurityInfo(HANDLE ComHandle,BYTE IDNum[18],BYTE Name[30],BYTE *Sex,BYTE *Nation,BYTE Regional[3],BYTE BirthDay[4]);
int __stdcall K2616_AutoReadSocialSecurityCardID(HANDLE ComHandle,BYTE CardID[9]);
int __stdcall K2616_AutoReadSocialSecurityInfoAndCardID(HANDLE ComHandle,BYTE IDNum[18],BYTE Name[30],BYTE *Sex,BYTE *Nation,BYTE Regional[3],BYTE BirthDay[4],BYTE CardID[9]);
int __stdcall K2616_CPUTypeBCardPowerOn(HANDLE ComHandle, BYTE szATR[]);
int __stdcall K2616_CPUTypeBAPDU(HANDLE ComHandle, BYTE SCH, int _dataLen, BYTE _APDUData[], BYTE* RCH,BYTE _exData[], int *_exdataLen);
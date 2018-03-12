#include <windows.h>

#ifndef ICCDLL_H
#define ICCDLL_H

int OpenPort(char *szDevName, char cExternPort,int nBautrate,int nTimeout);
void ClosePort() ;
int getIsCom();
int readChar(unsigned char *pucChar, int nTimeout);
int ReadStr(unsigned char *uszData,int nReadLen,int nTimeout);
int DeviceRead(unsigned char *pBuffer, DWORD *pdwSize,int nTimeout);
int Msr_WriteStr(unsigned char *uszData, int nDatalen);
int Msr_DeviceWrite(unsigned char *lpBudder, DWORD dwSize); 
unsigned char ascToBcd(
					   unsigned char *psBcd,
					   unsigned char *psAsc,
					   unsigned int nLen,
					   unsigned char byMode);

void bcdToAsc(
			  unsigned char *psAsc,
			  unsigned char *psBcd,
			  unsigned int nLen,
			  unsigned char byMode);
int Icc_EXApdu(int nSlot, unsigned char * uszApdu,int nApduLen,unsigned char *uszResponse,int * pnResplen);
int star_CloseIcc(int nSlot);
int star_OpenIcc_Time2(int nIcSlot, unsigned int nICReadMode, unsigned char * uszResponse, unsigned int *pnResponseLen, int nTimeout,int *ICTypeback);
int star_OpenIcc_Time(int nIcSlot, unsigned int nICReadMode, unsigned char * uszResponse, unsigned int *pnResponseLen, int nTimeout);
int Star_OpenIcc(int nIcSlot, unsigned int nICReadMode, unsigned char * uszResponse, unsigned int *pnResponseLen, int nTimeout);
int  HexToAsciibuf(unsigned char ucNumber,unsigned char *uszAsciibuf);
unsigned char  TwoAsciiToHex(unsigned char ucAscii0,unsigned char ucAscii1);

int WINAPI Icc_OpenPort(int nPort, char cExternPort, int nBautrate);
int WINAPI Icc_PowerOn(int nICReadMode,unsigned char* uszOutdata, int *pnOutDataLen);
int WINAPI Icc_SendCommand(unsigned char* uszApdu, int nApdulen, unsigned char* uszResp, int *pnRespLen);
int WINAPI Icc_GetCardStatus();
int WINAPI Icc_PowerOff();
int WINAPI Icc_ClosePort();
int WINAPI Icc_ReadVersion(char *uszVersion, int *pnVerlen, int nTimeout);

#endif
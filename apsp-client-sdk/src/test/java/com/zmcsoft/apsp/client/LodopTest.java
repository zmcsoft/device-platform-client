package com.zmcsoft.apsp.client;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;

/**
 * @author zhouhao
 * @since
 */
public class LodopTest {
    public static void main(String[] args) {
        ComThread.InitSTA();// 启动线程
        // 注册表中取得注册MyOcx.dll的ProgId，或clsid。
        ActiveXComponent com = new ActiveXComponent("2105C259-1E0C-4534-8141-A753534CB4CA");//在MyOcx中搜索ProgID = s 'MyOcx.MyDialog.1'
        // Dispatch对象看成是对Activex控件的一个操作
        Dispatch disp = com.getObject();
        // 假设MsgBox是MyOcx.dll中的一个方法
        Dispatch.call(disp, "MsgBox", "HelloWorld_Windows弹窗!");
        Dispatch.call(disp, "PRINT_INIT", "打印控件功能演示_Lodop功能_获得打印状态1");

        Dispatch.call(disp, "PRINT_INIT", "打印控件功能演示_Lodop功能_获得打印状态1");
        Dispatch.call(disp, "ADD_PRINT_TEXT", 50, 231, 260, 39, "打印的第一页内容");
        Dispatch.call(disp, "NEWPAGEA");
        Dispatch.call(disp, "ADD_PRINT_TEXT", 50, 231, 260, 39, "打印的第二页内容");
        Dispatch.call(disp, "SET_PRINT_MODE", "CATCH_PRINT_STATUS", true);
        Dispatch.call(disp, "PRINTA");
        ComThread.Release();// 结束进程
    }
}

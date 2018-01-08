package com.zmcsoft.apsp.client.sdk.drivers.printer.builder;

import com.zmcsoft.apsp.client.sdk.drivers.printer.Pager;
import com.zmcsoft.apsp.client.sdk.drivers.printer.Paper;
import com.zmcsoft.apsp.client.sdk.drivers.printer.PixelPaper;
import com.zmcsoft.apsp.client.sdk.drivers.printer.PrintCommand;
import com.zmcsoft.apsp.client.sdk.drivers.printer.executor.DefaultPrintable;
import junit.framework.TestCase;

import javax.print.*;
import javax.print.attribute.Attribute;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.*;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import java.util.Arrays;
import java.util.Locale;

/**
 * TODO 完成注释
 *
 * @author zhouhao
 * @since
 */
public class JsonPageBuilderTest {

    public static void main(String[] args) throws PrintException {
        String json = "{\"layers\":[{\"type\":\"rect\",\"rp\":\"rp72\",\"x\":83,\"y\":218,\"width\":440,\"height\":440.00001525878906,\"fill\":\"rgba(0,0,0,0)\",\"color\":\"#ff0000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\"},{\"type\":\"line\",\"rp\":\"rp72\",\"x1\":87,\"y1\":414,\"x2\":523,\"y2\":414,\"color\":\"#ff0000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\"},{\"type\":\"line\",\"rp\":\"rp72\",\"x1\":306,\"y1\":220,\"x2\":306,\"y2\":654,\"color\":\"#ff0000\",\"strokeWidth\":\"1\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\"},{\"type\":\"img\",\"rp\":\"rp72\",\"x\":325,\"y\":241,\"width\":176,\"height\":148,\"fill\":\"rgba(184,184,184,1)\",\"imgData\":\"erweima\",\"imgType\":\"qrCode\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\"},{\"type\":\"img\",\"rp\":\"rp72\",\"x\":98,\"y\":436,\"width\":170,\"height\":177,\"fill\":\"rgba(184,184,184,1)\",\"imgData\":\"file:D:/test.jpg\",\"imgType\":\"static\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\"},{\"type\":\"img\",\"rp\":\"rp72\",\"x\":99,\"y\":247,\"width\":159,\"height\":141,\"fill\":\"rgba(184,184,184,1)\",\"imgData\":\"tiaoxingma\",\"imgType\":\"barCode\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\"},{\"type\":\"img\",\"rp\":\"rp72\",\"x\":323,\"y\":445,\"width\":182,\"height\":188,\"fill\":\"rgba(184,184,184,1)\",\"imgData\":\"\",\"imgType\":\"static\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\"},{\"type\":\"img\",\"rp\":\"rp72\",\"x\":191,\"y\":48,\"width\":231,\"height\":135,\"fill\":\"rgba(184,184,184,1)\",\"imgData\":\"\",\"imgType\":\"code\",\"loopDirection\":\"\",\"loopSpacing\":0,\"loopData\":\"\",\"loopNum\":\"\",\"visible\":\"\"}]}";
        Pager pager = new JsonPageBuilder().build(json);
        PrintCommand command = new PrintCommand();
        command.setPagers(Arrays.asList(pager));

        command.setPaper(new PixelPaper(72, Paper.A4));

        DefaultPrintable defaultPrintable = new DefaultPrintable(command);
        defaultPrintable.setPageDoneEventPrinterListener(event -> {
            System.out.println(event);
        });
        //构建打印请求属性集
        HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        //设置打印格式，因为未确定类型，所以选择autosense
//        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
        //查找所有的可用的打印服务
        PrintService printServices[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
        PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
        PrintService printService = ServiceUI.printDialog(null, 200, 200, printServices,
                defaultService, flavor, pras);
        if (printService == null) return;

        //获取打印服务对象
        DocPrintJob job = printService.createPrintJob();
       // pras.add(new PrinterName("test", Locale.getDefault()));
        pras.add(PrintQuality.HIGH);
        pras.add(MediaSizeName.ISO_A4);
        //pras.add(OrientationRequested.LANDSCAPE);

        pras.add(new MediaPrintableArea(20, 20, Paper.A4.getWidth(), Paper.A4.getHeight(), MediaPrintableArea.MM));
        Doc doc = new SimpleDoc(defaultPrintable, flavor, null);

        job.print(doc, pras);
    }

}
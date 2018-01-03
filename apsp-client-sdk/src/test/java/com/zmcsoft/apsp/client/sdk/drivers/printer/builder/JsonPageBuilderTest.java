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
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrintQuality;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import java.util.Arrays;

/**
 * TODO 完成注释
 *
 * @author zhouhao
 * @since
 */
public class JsonPageBuilderTest {

    public static void main(String[] args) throws PrintException {
        String json = "{\"layers\":[{\"fontSize\":\"89\",\"type\":\"text\",\"x\":100,\"y\":238,\"originalY\":198.3125,\"text\":\"输入内容\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":\"38\",\"type\":\"text\",\"x\":0,\"y\":173,\"originalY\":156.609375,\"text\":\"输入内容\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":\"18\",\"type\":\"text\",\"x\":161,\"y\":173,\"originalY\":164.609375,\"text\":\"输入内容\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":\"18\",\"type\":\"text\",\"x\":369,\"y\":299,\"originalY\":290.609375,\"text\":\"输入内容\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"}]}";
        Pager pager = new JsonPageBuilder().build(json);
        PrintCommand command = new PrintCommand();
        command.setPagers(Arrays.asList(pager));

        command.setPaper(new PixelPaper(72, Paper.A4));

        DefaultPrintable defaultPrintable = new DefaultPrintable(command);

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

        pras.add(PrintQuality.HIGH);
        pras.add(MediaSizeName.ISO_A4);

        pras.add(new MediaPrintableArea(0, 0, Paper.A4.getWidth(), Paper.A4.getHeight(), MediaPrintableArea.INCH));
        Doc doc = new SimpleDoc(defaultPrintable, flavor, null);

        job.print(doc, pras);
    }

}
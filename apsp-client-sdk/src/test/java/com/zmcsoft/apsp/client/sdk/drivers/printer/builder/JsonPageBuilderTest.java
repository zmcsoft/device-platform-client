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
        String json = "{\"layers\":[{\"type\":\"rect\",\"x\":20,\"y\":57,\"width\":561,\"height\":165,\"fill\":\"#ffffff\",\"color\":\"#808000\",\"strokeWidth\":\"1\"},{\"type\":\"line\",\"x1\":22,\"y1\":88,\"x2\":576,\"y2\":88,\"color\":\"#808000\",\"strokeWidth\":\"3\"},{\"type\":\"line\",\"x1\":24,\"y1\":122,\"x2\":578,\"y2\":122,\"color\":\"#808000\",\"strokeWidth\":\"3\"},{\"fontSize\":\"18\",\"type\":\"text\",\"x\":23,\"y\":234,\"originalY\":225.609375,\"text\":\"打印机构：江阳支行\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":\"18\",\"type\":\"text\",\"x\":203,\"y\":303,\"originalY\":294.609375,\"text\":\"打印次数：0次\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":\"18\",\"type\":\"text\",\"x\":327,\"y\":305,\"originalY\":296.609375,\"text\":\"打印时间：207-09-11 12:00:12\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":\"18\",\"type\":\"text\",\"x\":26,\"y\":29,\"originalY\":20.609375,\"text\":\"入账日期：20470831\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":\"28\",\"type\":\"text\",\"x\":207,\"y\":2,\"originalY\":-10.390625,\"text\":\"网上支付电子回单\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":\"18\",\"type\":\"text\",\"x\":41,\"y\":66,\"originalY\":57.609375,\"text\":\"输入内容\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":\"18\",\"type\":\"text\",\"x\":41,\"y\":98,\"originalY\":89.609375,\"text\":\"输入内容\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"}]}";
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
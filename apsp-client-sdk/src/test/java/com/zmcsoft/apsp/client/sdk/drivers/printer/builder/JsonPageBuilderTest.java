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
        String json = "{\"layers\":[{\"type\":\"rect\",\"x\":\"264\",\"y\":\"252\",\"width\":\"438\",\"height\":\"72\",\"fill\":\"#ffffff\",\"color\":\"#808000\",\"strokeWidth\":\"1\"},{\"type\":\"rect\",\"x\":\"163\",\"y\":\"252\",\"width\":\"150\",\"height\":\"375\",\"fill\":\"#ffffff\",\"color\":\"#808000\",\"strokeWidth\":\"1\"},{\"type\":\"line\",\"x1\":\"414\",\"y1\":\"252\",\"x2\":\"414\",\"y2\":\"323\",\"color\":\"#808000\",\"strokeWidth\":\"3\"},{\"type\":\"line\",\"x1\":\"574\",\"y1\":\"254\",\"x2\":\"574\",\"y2\":\"323\",\"color\":\"#808000\",\"strokeWidth\":\"3\"},{\"type\":\"line\",\"x1\":\"164\",\"y1\":\"327\",\"x2\":\"310\",\"y2\":\"327\",\"color\":\"#808000\",\"strokeWidth\":\"3\"},{\"type\":\"text\",\"x\":\"199\",\"y\":\"388\",\"text\":\"输入内容\",\"fontFamily\":\"Menlo, sans-serif\",\"fontSize\":\"18\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"type\":\"text\",\"x\":\"464\",\"y\":\"293\",\"text\":\"表格123\",\"fontFamily\":\"Menlo, sans-serif\",\"fontSize\":\"18\",\"fill\":\"#0080ff\",\"color\":\"#0080c0\",\"strokeWidth\":\"1\"}]}";
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

        pras.add(new MediaPrintableArea(20, 20, Paper.A4.getWidth(), Paper.A4.getHeight(), MediaPrintableArea.INCH));
        Doc doc = new SimpleDoc(defaultPrintable, flavor, null);

        job.print(doc, pras);
    }

}
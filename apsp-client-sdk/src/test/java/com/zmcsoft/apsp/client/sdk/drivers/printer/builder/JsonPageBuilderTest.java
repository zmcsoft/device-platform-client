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
        String json = "{\"layers\":[{\"x\":\"216\",\"type\":\"rect\",\"y\":\"157\",\"width\":\"319\",\"height\":\"230\",\"fill\":\"#ffffff\",\"stroke\":\"red\",\"strokeWidth\":\"1\"},{\"type\":\"rect\",\"x\":\"216\",\"y\":\"103\",\"width\":\"319\",\"height\":\"54\",\"fill\":\"#ffffff\",\"stroke\":\"red\",\"strokeWidth\":\"1\"},{\"type\":\"line\",\"x1\":\"215\",\"y1\":\"268\",\"x2\":\"535\",\"y2\":\"268\",\"stroke\":\"#000000\",\"strokeWidth\":\"3\"},{\"type\":\"line\",\"x1\":\"217\",\"y1\":\"229\",\"x2\":\"534\",\"y2\":\"229\",\"stroke\":\"#000000\",\"strokeWidth\":\"3\"},{\"type\":\"line\",\"x1\":\"288\",\"y1\":\"230\",\"x2\":\"288\",\"y2\":\"386\",\"stroke\":\"#000000\",\"strokeWidth\":\"3\"},{\"type\":\"text\",\"x\":\"355\",\"y\":\"212\",\"fontFamily\":\"Menlo, sans-serif\",\"fontSize\":\"18\",\"fill\":\"#808000\",\"stroke\":\"#808000\",\"strokeWidth\":\"1\"},{\"type\":\"text\",\"x\":\"357\",\"y\":\"335\",\"fontFamily\":\"Menlo, sans-serif\",\"fontSize\":\"18\",\"fill\":\"#000000\",\"stroke\":\"#000000\",\"strokeWidth\":\"1\"},{\"type\":\"text\",\"x\":\"352\",\"y\":\"130\",\"text\":\"123ABC\",\"fontFamily\":\"Menlo, sans-serif\",\"fontSize\":\"18\",\"fill\":\"#000000\",\"stroke\":\"#000000\",\"strokeWidth\":\"1\"}]}";
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

        pras.add(new MediaPrintableArea(20, 20, Paper.A4.getWidth(), Paper.A4.getHeight(), MediaPrintableArea.MM));
        Doc doc = new SimpleDoc(defaultPrintable, flavor, null);

        job.print(doc, pras);
    }

}
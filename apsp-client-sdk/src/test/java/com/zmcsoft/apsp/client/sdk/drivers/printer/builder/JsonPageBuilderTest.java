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
        String json = "{\"layers\":[{\"type\":\"rect\",\"rp\":\"rp96\",\"x\":18.75,\"y\":39.75,\"width\":558,\"height\":222,\"fill\":\"rgba(0,0,0,0)\",\"color\":\"#ff0000\",\"strokeWidth\":\"1\"},{\"type\":\"line\",\"rp\":\"rp96\",\"x1\":18.75,\"y1\":61.5,\"x2\":577.5,\"y2\":61.5,\"color\":\"#ff0000\",\"strokeWidth\":\"1\"},{\"type\":\"line\",\"rp\":\"rp96\",\"x1\":18.75,\"y1\":83.25,\"x2\":576.75,\"y2\":83.25,\"color\":\"#ff0000\",\"strokeWidth\":\"1\"},{\"type\":\"line\",\"rp\":\"rp96\",\"x1\":90.75,\"y1\":105,\"x2\":297,\"y2\":105,\"color\":\"#ff0000\",\"strokeWidth\":\"1\"},{\"type\":\"line\",\"rp\":\"rp96\",\"x1\":90,\"y1\":126.75,\"x2\":297.75,\"y2\":126.75,\"color\":\"#ff0000\",\"strokeWidth\":\"1\"},{\"type\":\"line\",\"rp\":\"rp96\",\"x1\":19.5,\"y1\":148.5,\"x2\":576.75,\"y2\":148.5,\"color\":\"#ff0000\",\"strokeWidth\":\"1\"},{\"type\":\"line\",\"rp\":\"rp96\",\"x1\":19.5,\"y1\":170.25,\"x2\":576,\"y2\":170.25,\"color\":\"#ff0000\",\"strokeWidth\":\"1\"},{\"type\":\"line\",\"rp\":\"rp96\",\"x1\":371.25,\"y1\":105,\"x2\":576,\"y2\":105,\"color\":\"#ff0000\",\"strokeWidth\":\"1\"},{\"type\":\"line\",\"rp\":\"rp96\",\"x1\":370.5,\"y1\":126.75,\"x2\":576,\"y2\":126.75,\"color\":\"#ff0000\",\"strokeWidth\":\"1\"},{\"type\":\"line\",\"rp\":\"rp96\",\"x1\":297.75,\"y1\":39.75,\"x2\":297.75,\"y2\":169.5,\"color\":\"#ff0000\",\"strokeWidth\":\"1\"},{\"type\":\"line\",\"rp\":\"rp96\",\"x1\":297.75,\"y1\":169.5,\"x2\":297.75,\"y2\":261,\"color\":\"#ff0000\",\"strokeWidth\":\"1\"},{\"type\":\"line\",\"rp\":\"rp96\",\"x1\":158.25,\"y1\":39.75,\"x2\":158.25,\"y2\":169.5,\"color\":\"#ff0000\",\"strokeWidth\":\"1\"},{\"type\":\"line\",\"rp\":\"rp96\",\"x1\":439.5,\"y1\":39.75,\"x2\":439.5,\"y2\":147.75,\"color\":\"#ff0000\",\"strokeWidth\":\"1\"},{\"type\":\"line\",\"rp\":\"rp96\",\"x1\":90.75,\"y1\":83.25,\"x2\":90.75,\"y2\":147.75,\"color\":\"#ff0000\",\"strokeWidth\":\"1\"},{\"type\":\"line\",\"rp\":\"rp96\",\"x1\":371.25,\"y1\":83.25,\"x2\":371.25,\"y2\":147.75,\"color\":\"#ff0000\",\"strokeWidth\":\"1\"},{\"fontSize\":13.5,\"type\":\"text\",\"rp\":\"rp96\",\"x\":18.75,\"y\":263.25,\"originalY\":257.70703125,\"text\":\"打印机构：江阳支行\",\"fontFamily\":\"Helvetica, Arial, sans-serif\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":13.5,\"type\":\"text\",\"rp\":\"rp96\",\"x\":240,\"y\":263.25,\"originalY\":257.70703125,\"text\":\"打印次数：0次\",\"fontFamily\":\"Helvetica, Arial, sans-serif\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":13.5,\"type\":\"text\",\"rp\":\"rp96\",\"x\":374.25,\"y\":264,\"originalY\":258.45703125,\"text\":\"打印时间：2017-09-28 16:37:00\",\"fontFamily\":\"Helvetica, Arial, sans-serif\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":13.5,\"type\":\"text\",\"rp\":\"rp96\",\"x\":18.75,\"y\":23.25,\"originalY\":17.70703125,\"text\":\"入账日期：20470831\",\"fontFamily\":\"Helvetica, Arial, sans-serif\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":28.5,\"type\":\"text\",\"rp\":\"rp96\",\"x\":192,\"y\":2.23828125,\"originalY\":-9.3046875,\"text\":\"网上支付电子回单\",\"fontFamily\":\"Helvetica, Arial, sans-serif\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":13.5,\"type\":\"text\",\"rp\":\"rp96\",\"x\":55.5,\"y\":44.25,\"originalY\":37.95703125,\"text\":\"电子回单号\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":13.5,\"type\":\"text\",\"rp\":\"rp96\",\"x\":164.25,\"y\":44.25,\"originalY\":37.95703125,\"text\":\"20170922CS000381150\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":13.5,\"type\":\"text\",\"rp\":\"rp96\",\"x\":344.25,\"y\":44.25,\"originalY\":37.95703125,\"text\":\"交易类型\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":13.5,\"type\":\"text\",\"rp\":\"rp96\",\"x\":492.75,\"y\":43.5,\"originalY\":37.20703125,\"text\":\"转入\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":13.5,\"type\":\"text\",\"rp\":\"rp96\",\"x\":65.25,\"y\":66,\"originalY\":59.70703125,\"text\":\"交易流水\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":13.5,\"type\":\"text\",\"rp\":\"rp96\",\"x\":182.25,\"y\":66.75,\"originalY\":60.45703125,\"text\":\"0000000386542\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":13.5,\"type\":\"text\",\"rp\":\"rp96\",\"x\":345,\"y\":66.75,\"originalY\":60.45703125,\"text\":\"交易渠道\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":13.5,\"type\":\"text\",\"rp\":\"rp96\",\"x\":492.75,\"y\":66,\"originalY\":59.70703125,\"text\":\"柜面\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":21.75,\"type\":\"text\",\"rp\":\"rp96\",\"x\":23.25,\"y\":104.25,\"originalY\":94.734375,\"text\":\"付款人\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":20.25,\"type\":\"text\",\"rp\":\"rp96\",\"x\":305.25,\"y\":105.75,\"originalY\":96.6796875,\"text\":\"收款人\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":13.5,\"type\":\"text\",\"rp\":\"rp96\",\"x\":93,\"y\":88.5,\"originalY\":82.20703125,\"text\":\"全称\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":13.5,\"type\":\"text\",\"rp\":\"rp96\",\"x\":93,\"y\":109.5,\"originalY\":103.20703125,\"text\":\"账号\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":13.5,\"type\":\"text\",\"rp\":\"rp96\",\"x\":93,\"y\":131.25,\"originalY\":124.95703125,\"text\":\"开户行\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":13.5,\"type\":\"text\",\"rp\":\"rp96\",\"x\":162,\"y\":87,\"originalY\":80.70703125,\"text\":\"马云\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":13.5,\"type\":\"text\",\"rp\":\"rp96\",\"x\":161.25,\"y\":109.5,\"originalY\":103.20703125,\"text\":\"6230851001001648752\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":13.5,\"type\":\"text\",\"rp\":\"rp96\",\"x\":375.75,\"y\":87.75,\"originalY\":81.45703125,\"text\":\"全称\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":13.5,\"type\":\"text\",\"rp\":\"rp96\",\"x\":375,\"y\":109.5,\"originalY\":103.20703125,\"text\":\"账号\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":13.5,\"type\":\"text\",\"rp\":\"rp96\",\"x\":374.25,\"y\":131.25,\"originalY\":124.95703125,\"text\":\"开户行\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":13.5,\"type\":\"text\",\"rp\":\"rp96\",\"x\":442.5,\"y\":87.75,\"originalY\":81.45703125,\"text\":\"支付宝\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":13.5,\"type\":\"text\",\"rp\":\"rp96\",\"x\":442.5,\"y\":129.75,\"originalY\":123.45703125,\"text\":\"313657092617\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":13.5,\"type\":\"text\",\"rp\":\"rp96\",\"x\":56.25,\"y\":153,\"originalY\":146.70703125,\"text\":\"交易金额\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":13.5,\"type\":\"text\",\"rp\":\"rp96\",\"x\":159.75,\"y\":153.75,\"originalY\":147.45703125,\"text\":\"（大写）：叁拾亿元整\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":13.5,\"type\":\"text\",\"rp\":\"rp96\",\"x\":342,\"y\":153,\"originalY\":146.70703125,\"text\":\"（小写）：38000000000.00\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":13.5,\"type\":\"text\",\"rp\":\"rp96\",\"x\":23.25,\"y\":203.25,\"originalY\":196.95703125,\"text\":\"备注：\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"},{\"fontSize\":13.5,\"type\":\"text\",\"rp\":\"rp96\",\"x\":306.75,\"y\":204.75,\"originalY\":198.45703125,\"text\":\"银行盖章：\",\"fontFamily\":\"宋体\",\"fill\":\"#000000\",\"color\":\"#000000\",\"strokeWidth\":\"1\"}]}";
        Pager pager = new JsonPageBuilder().build(json);
        PrintCommand command = new PrintCommand();
        command.setPagers(Arrays.asList(pager, pager));

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
        pras.add(new PrinterName("test", Locale.getDefault()));
        pras.add(PrintQuality.HIGH);
        pras.add(MediaSizeName.ISO_A4);
        pras.add(OrientationRequested.LANDSCAPE);

        pras.add(new MediaPrintableArea(20, 20, Paper.A4.getWidth(), Paper.A4.getHeight(), MediaPrintableArea.MM));
        Doc doc = new SimpleDoc(defaultPrintable, flavor, null);

        job.print(doc, pras);
    }

}
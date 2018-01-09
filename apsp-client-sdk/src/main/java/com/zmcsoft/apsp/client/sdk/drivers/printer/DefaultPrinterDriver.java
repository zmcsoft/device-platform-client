package com.zmcsoft.apsp.client.sdk.drivers.printer;

import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriverRegister;
import com.zmcsoft.apsp.client.sdk.drivers.DeviceDriverManager;
import com.zmcsoft.apsp.client.sdk.drivers.printer.executor.DefaultPrintable;
import org.hswebframework.utils.RandomUtil;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrintQuality;
import java.util.List;
import java.util.Locale;
import java.util.function.BiConsumer;

/**
 * @author zhouhao
 * @since 1.0
 */
public class DefaultPrinterDriver implements PrinterDriver {

    private PrintService printService;

    public DefaultPrinterDriver(PrintService printService) {
        this.printService = printService;
    }

    static {
        HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
        DeviceDriverManager.register(register -> {
            PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
            register.register(PrinterDriver.class, new DefaultPrinterDriver(defaultPrintService), DeviceDriverRegister.DEFAULT_DRIVER_PROVIDER);
            for (PrintService printService : PrintServiceLookup.lookupPrintServices(flavor, pras)) {
                register.register(PrinterDriver.class, new DefaultPrinterDriver(printService), printService.getName());
            }
        });
    }

    @Override
    public PrintJob print(List<Pager> pagers) {
        PrintCommand command = new PrintCommand();
        command.setPagers(pagers);

        command.setPaper(new PixelPaper(72, Paper.A4));
        DefaultPrintable defaultPrintable = new DefaultPrintable(command);
        //构建打印请求属性集
        HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
        //获取打印服务对象
        DocPrintJob job = printService.createPrintJob();
        String id = RandomUtil.randomChar(16);

        pras.add(new JobName(id, Locale.getDefault()));
        pras.add(PrintQuality.HIGH);
        pras.add(MediaSizeName.ISO_A4);
        //pras.add(OrientationRequested.LANDSCAPE);

        pras.add(new MediaPrintableArea(20, 20, Paper.A4.getWidth(), Paper.A4.getHeight(), MediaPrintableArea.MM));
        Doc doc = new SimpleDoc(defaultPrintable, flavor, null);
        try {
            job.print(doc, pras);
        } catch (PrintException e) {
            e.printStackTrace();
        }
        return new PrintJob() {
            @Override
            public String getPrintJobId() {
                return id;
            }

            @Override
            public void onPrintDone(BiConsumer<Integer, Pager> onPagerPrintDone) {
                // TODO: 18-1-9 增加打印完成事件实现
                for (int i = 0; i < pagers.size(); i++) {
                    onPagerPrintDone.accept(i, pagers.get(i));
                }
            }
        };
    }

    @Override
    public PrintState getState() {
        return PrintState.ok;
    }
}

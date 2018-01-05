package com.zmcsoft.apsp.client.javascript.drivers;

import com.zmcsoft.apsp.client.core.ApplicationConfig;
import com.zmcsoft.apsp.client.core.Global;
import com.zmcsoft.apsp.client.javascript.AbstractJavaScriptObject;
import com.zmcsoft.apsp.client.sdk.drivers.printer.Pager;
import com.zmcsoft.apsp.client.sdk.drivers.printer.Paper;
import com.zmcsoft.apsp.client.sdk.drivers.printer.PixelPaper;
import com.zmcsoft.apsp.client.sdk.drivers.printer.PrintCommand;
import com.zmcsoft.apsp.client.sdk.drivers.printer.builder.JsonPageBuilder;
import com.zmcsoft.apsp.client.sdk.drivers.printer.executor.DefaultPrintable;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrintQuality;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author zhouhao
 * @since 1.0
 */
@Slf4j
public class PrintDriver extends AbstractJavaScriptObject {

    private PrintService printService = PrintServiceLookup.lookupDefaultPrintService();

    private JsonPageBuilder builder = new JsonPageBuilder();

    @Override
    public String getName() {
        return "printer";
    }

    public PrintService getPrintService() {
        if (ApplicationConfig.getConfig("debug", "true").equals("true")) {
            DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
            HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
            PrintService printServices[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
            return ServiceUI.printDialog(null, 200, 200, printServices,
                    this.printService, flavor, pras);
        }
        return printService;
    }

    public void print(String json, Object callback) {
        Global.executorService.execute(() -> {
            Platform.runLater(() -> {
                try {
                    PrintService printService = getPrintService();
                    if (printService == null) {
                        return;
                    }
                    log.info("开始准备打印:{}", json);
                    Pager pager = builder.build(json);
                    PrintCommand command = new PrintCommand();
                    command.setPagers(Arrays.asList(pager));
                    command.setPaper(new PixelPaper(72, Paper.A4));
                    DefaultPrintable defaultPrintable = new DefaultPrintable(command);
                    defaultPrintable.setPageDoneEventPrinterListener(event -> {
                        log.info("打印完成:{}", event.getPageIndex());
                        Platform.runLater(() -> {
                            call(callback, event.getPageIndex());
                        });
                    });
                    //构建打印请求属性集
                    HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

                    DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
                    //获取打印服务对象
                    DocPrintJob job = printService.createPrintJob();

                    pras.add(PrintQuality.HIGH);
                    pras.add(MediaSizeName.ISO_A4);
                    OrientationRequested requested;
                    switch (pager.getOrientation()) {
                        case 3:
                            requested = OrientationRequested.PORTRAIT;
                            break;
                        case 4:
                            requested = OrientationRequested.LANDSCAPE;
                            break;
                        case 5:
                            requested = OrientationRequested.REVERSE_LANDSCAPE;
                            break;
                        case 6:
                            requested = OrientationRequested.REVERSE_PORTRAIT;
                            break;
                        default:
                            requested = null;
                    }
                    if (null != requested) {
                        pras.add(requested);
                    }

                    pras.add(new MediaPrintableArea(20, 20, Paper.A4.getWidth(), Paper.A4.getHeight(), MediaPrintableArea.MM));
                    Doc doc = new SimpleDoc(defaultPrintable, flavor, null);
                    job.print(doc, pras);
                } catch (Exception e) {
                    log.error("打印失败", e);
                }
            });

        });
    }

}

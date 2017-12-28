package com.zmcsoft.apsp.client;

import com.alibaba.fastjson.JSON;
import com.zmcsoft.apsp.client.sdk.drivers.printer.*;
import com.zmcsoft.apsp.client.sdk.drivers.printer.executor.DefaultPrintable;
import com.zmcsoft.apsp.client.sdk.drivers.printer.layer.LineLayer;
import com.zmcsoft.apsp.client.sdk.drivers.printer.layer.RectLayer;
import com.zmcsoft.apsp.client.sdk.drivers.printer.layer.TextLayer;

import javax.print.*;
import javax.print.attribute.Attribute;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.*;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobAttributeEvent;
import javax.print.event.PrintJobAttributeListener;
import javax.print.event.PrintJobEvent;
import java.awt.*;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhouhao
 * @since
 */
public class PrinterTest {

    static List<Layer> createTable(int x, int y, int columnNumber, int rowNumber, int width, int height) {
        int everyHeight = height / rowNumber;
        int everyWidth = width / columnNumber;
        List<Layer> layers = new ArrayList<>();
        for (int columnIndex = 0; columnIndex < columnNumber; columnIndex++) {
            int nowX = x + (columnIndex * everyWidth);
            LineLayer column = new LineLayer();
            column.setX(nowX);
            column.setEndX(nowX);
            column.setY(y);
            column.setEndY(y + everyHeight * rowNumber);
            layers.add(column);
        }
        {
            LineLayer column = new LineLayer();
            column.setX(width);
            column.setEndX(width);
            column.setY(y);
            column.setEndY(y + everyHeight * rowNumber);
            layers.add(column);
        }
        for (int rowIndex = 0; rowIndex < rowNumber; rowIndex++) {
            int nowY = y + (rowIndex * everyHeight);
            LineLayer row = new LineLayer();
            row.setX(x);
            row.setEndX(x + everyWidth * columnNumber);
            row.setY(nowY);
            row.setEndY(nowY);
            layers.add(row);

            TextLayer textLayer = new TextLayer();
            textLayer.setX(x + 5);
            textLayer.setY(nowY + everyHeight / 2);
            textLayer.setText("abc中文：" + (rowIndex + 1));
            layers.add(textLayer);
        }
        {
            LineLayer row = new LineLayer();
            row.setX(x);
            row.setEndX(x + everyWidth * columnNumber);
            row.setY(y + everyHeight * rowNumber);
            row.setEndY(y + everyHeight * rowNumber);
            layers.add(row);
        }
//        for (int rowIndex = 0; rowIndex < rowNumber; rowIndex++) {
//            int nowY = y + (rowIndex * everyHeight);
//            for (int columnIndex = 0; columnIndex < columnNumber; columnIndex++) {
//                int nowX = x + (columnIndex * everyWidth);
//                RectLayer layer = new RectLayer();
//                layer.setX(nowX);
//                layer.setY(nowY);
//                layer.setWidth(everyWidth);
//                layer.setHeight(everyHeight);
//                layers.add(layer);
//            }
//        }

        return layers;
    }

    public static void main(String[] args) throws PrinterException, PrintException, InterruptedException, IOException {

        TextLayer text = new TextLayer();
        text.setX(100);
        text.setY(100);
        text.setColor(Color.RED);
        text.setFont(new Font("YaHei Consolas Hybrid", Font.PLAIN, 25));
        text.setText("测试");

        LineLayer lineLayer = new LineLayer();
        lineLayer.setX(80);
        lineLayer.setY(80);
        lineLayer.setEndX(100);
        lineLayer.setEndY(80);
        Pager pager = new Pager();
        //72 dpi
        PixelPaper paper = new PixelPaper(72, Paper.A4);

        pager.setLayers(createTable(10, 70, 10, 30, paper.getWidth(), paper.getHeight() - 70));
        PrintCommand command = new PrintCommand();
        command.setPagers(Arrays.asList(pager));

        command.setPaper(paper);

        DefaultPrintable defaultPrintable = new DefaultPrintable(command);

        //构建打印请求属性集
        HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        //设置打印格式，因为未确定类型，所以选择autosense
//        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
        //查找所有的可用的打印服务
        PrintService printServices[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
        for (PrintService printService : printServices) {
            System.out.println(printService);
        }
        PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();

        PrintService printService = ServiceUI.printDialog(null, 200, 200, printServices,
                defaultService, flavor, pras);
        //获取打印服务对象
        DocPrintJob job = printService.createPrintJob();

        job.addPrintJobAttributeListener(System.out::println, null);
        job.addPrintJobListener(new PrintJobAdapter() {
            @Override
            public void printDataTransferCompleted(PrintJobEvent pje) {
                System.out.println("printDataTransferCompleted" + pje);
            }

            @Override
            public void printJobFailed(PrintJobEvent pje) {
                System.out.println("printJobFailed" + pje);
            }

            @Override
            public void printJobCompleted(PrintJobEvent pje) {
                System.out.println("printJobCompleted" + pje);
            }

            @Override
            public void printJobNoMoreEvents(PrintJobEvent pje) {
                System.out.println("printJobNoMoreEvents" + pje);
            }

            @Override
            public void printJobCanceled(PrintJobEvent pje) {
                System.out.println("printJobCanceled" + pje);
            }

            @Override
            public void printJobRequiresAttention(PrintJobEvent pje) {
                System.out.println("printJobRequiresAttention" + pje);
            }
        });

        pras.add(PrintQuality.HIGH);
        pras.add(MediaSizeName.ISO_A4);

        pras.add(new MediaPrintableArea(20, 20, Paper.A4.getWidth(), Paper.A4.getHeight(), MediaPrintableArea.MM));
        Doc doc = new SimpleDoc(defaultPrintable, flavor, null);

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("--------------------------------");
                for (Attribute attribute : printService.getAttributes().toArray()) {
                    System.out.println(attribute.getName()+"\t\t"+attribute);
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(1000);
        job.print(doc, pras);
        System.in.read();
    }


}

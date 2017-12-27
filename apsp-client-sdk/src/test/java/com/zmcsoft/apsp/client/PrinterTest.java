package com.zmcsoft.apsp.client;

import com.zmcsoft.apsp.client.sdk.drivers.printer.*;
import com.zmcsoft.apsp.client.sdk.drivers.printer.executor.DefaultPrintable;
import com.zmcsoft.apsp.client.sdk.drivers.printer.layer.LineLayer;
import com.zmcsoft.apsp.client.sdk.drivers.printer.layer.RectLayer;
import com.zmcsoft.apsp.client.sdk.drivers.printer.layer.TextLayer;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.awt.*;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
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
            textLayer.setText("" + (rowIndex + 1));
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

    public static void main(String[] args) throws PrinterException {

        TextLayer text = new TextLayer();
        text.setX(100);
        text.setY(100);
        text.setColor(Color.RED);
        text.setFont(new Font("YaHei Consolas Hybrid", 1, 25));
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

        PrintService printService = PrintServiceLookup.lookupDefaultPrintService();

        //获取打印服务对象
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("test");
        // 设置打印类
        job.setPageable(defaultPrintable.getBook());

        //可以用printDialog显示打印对话框，在用户确认后打印；也可以直接打印
        boolean a = job.printDialog();

        job.setPrintService(printService);
        job.print();
    }


}

/*
 *  Copyright (c) 2015.  meicanyun.com Corporation Limited.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of
 *  meicanyun Company. ("Confidential Information").  You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into
 *  with meicanyun.com.
 */

package com.zmcsoft.apsp.client.sdk.drivers.printer.executor;


import com.zmcsoft.apsp.client.sdk.drivers.printer.Layer;
import com.zmcsoft.apsp.client.sdk.drivers.printer.Pager;
import com.zmcsoft.apsp.client.sdk.drivers.printer.PrintCommand;

import java.awt.*;
import java.awt.print.*;
import java.util.List;

/**
 * @author zhouhao
 * @author xiongchuang
 */
public class DefaultPrintable implements Printable {

    private double width;

    private double height;

    private PrintCommand printCommand;

    private Pager noPrintPager;

    private int xPadding = 0;

    private int yPadding = 0;

    public DefaultPrintable(PrintCommand printCommand) {
        this.printCommand = printCommand;
        this.width = printCommand.getPaper().getWidth();
        this.height = printCommand.getPaper().getHeight();
        noPrintPager = printCommand.getPagers().get(0);
    }


    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        List<Pager> pagerList = printCommand.getPagers();
        if (pagerList.size() <= pageIndex) {
            return NO_SUCH_PAGE;
        }
        noPrintPager = pagerList.get(pageIndex);
        noPrintPager.getLayers().forEach(layer -> layer.draw(((Graphics2D) graphics)));
        return PAGE_EXISTS;
    }

    /**
     * 获取打印页
     *
     * @return
     */
    public Paper getPaper() {
        //通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
        Paper paper = new Paper();
        //纸张大小
        paper.setSize(getWidth(), getHeight());
        paper.setImageableArea(getXPadding(), getYPadding(), getWidth(), getHeight());
        return paper;
    }

    /**
     * 获取打印页样式
     *
     * @return
     */
    public PageFormat getPageFormat() {
        Paper paper = getPaper();
        //    设置成竖打
        PageFormat format = new PageFormat();
        format.setOrientation(noPrintPager.getOrientation());
        format.setPaper(paper);
        return format;
    }

    /**
     * 获取打印文档
     *
     * @return
     */
    public Book getBook() {
        PageFormat pageFormat = getPageFormat();
        //    通俗理解就是书、文档
        Book book = new Book();
        //    把 PageFormat 和 Printable 添加到书中，组成一个页面
        int pageSize = printCommand.getPagers().size();

        for (int i = 0; i < pageSize; i++) {
            book.append(this, pageFormat);
        }
        return book;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * 纸张左边距
     *
     * @return
     */
    double getXPadding() {
        return xPadding;
    }

    /**
     * 纸张上边距
     *
     * @return
     */
    double getYPadding() {
        return yPadding;
    }

}

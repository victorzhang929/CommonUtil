package com.victorzhang.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.commons.lang3.StringUtils;

import static com.victorzhang.common.constant.EncryptConstant.GBK;
import static com.victorzhang.common.constant.EncryptConstant.ISO_8859_1;

/**
 * Excel导出工具类
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-05-21 14:16:01
 */
public class ExcelExportUtil {

    private static final int TITLE_HIGH = 500;
    private static final int COL_TITLE_HIGH = 420;
    private static final int COL_DATA_HIGH = 400;

    private final WritableFont titleFont = new WritableFont(WritableFont.TIMES, 14, WritableFont.BOLD);
    private final WritableCellFormat titleFormat = new WritableCellFormat(titleFont);

    private final WritableFont colTitleFont = new WritableFont(WritableFont.TIMES, 10);
    private final WritableCellFormat colTitleFormat = new WritableCellFormat(colTitleFont);

    private final WritableFont colDataFont = new WritableFont(WritableFont.TIMES, 10);
    private final WritableCellFormat colDataFormat = new WritableCellFormat(colDataFont);

    private final NumberFormat integerFormat = new NumberFormat("#");
    private final WritableCellFormat integerFormatCell = new WritableCellFormat(integerFormat);

    private final NumberFormat floatFormat = new NumberFormat("#0.00");
    private final WritableCellFormat floatFormatCell = new WritableCellFormat(floatFormat);

    private final NumberFormat doubleFormat = new NumberFormat("#0.000000");
    private final WritableCellFormat doubleFormatCell = new WritableCellFormat(doubleFormat);

    private void initWritableCellFormat(WritableCellFormat cellFormat, Colour colour, boolean isWrap) throws WriteException {
        initWritableCellFormat(cellFormat);
        cellFormat.setBackground(colour);
        cellFormat.setWrap(isWrap);
    }

    private void initWritableCellFormat(WritableCellFormat cellFormat, boolean isWrap) throws WriteException {
        initWritableCellFormat(cellFormat);
        cellFormat.setWrap(isWrap);
    }

    private void initWritableCellFormat(WritableCellFormat cellFormat, Colour colour) throws WriteException {
        initWritableCellFormat(cellFormat);
        cellFormat.setBackground(colour);
    }

    private void initWritableCellFormat(WritableCellFormat cellFormat) throws WriteException {
        cellFormat.setAlignment(Alignment.CENTRE);
        cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
        cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
    }

    public ExcelExportUtil() {
        try {
            initWritableCellFormat(titleFormat, Colour.LIGHT_GREEN);
            colTitleFont.setColour(Colour.LIGHT_BLUE);
            initWritableCellFormat(colTitleFormat, Colour.LIGHT_GREEN, true);
            initWritableCellFormat(colDataFormat, true);
            initWritableCellFormat(integerFormatCell, true);
            initWritableCellFormat(floatFormatCell, true);
            initWritableCellFormat(doubleFormatCell, true);
        } catch (WriteException e) {
            throw new RuntimeException("ExcelExportUtil init error");
        }
    }

    /**
     * 获取文件下载流
     *
     * @param response
     * @param fileName
     * @return
     */
    private static HttpServletResponse fileStream(HttpServletResponse response, String fileName) {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename="
                + fileNameHandler(fileName) + DateUtil.getTimestamp() + ".xls");
        return response;
    }

    /**
     * 文件名编码处理，否则下载容易出现乱码情况
     *
     * @param fileName
     * @return
     */
    private static String fileNameHandler(String fileName) {
        try {
            return new String(fileName.getBytes(GBK), ISO_8859_1);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * 导出Excel文件
     *
     * @param response
     * @param title    文件名称
     * @param colTitle 列名
     * @param datas    数据
     */
    public static void export(HttpServletResponse response, String title, Map<String, String> colTitle, List<Map<String, Object>> datas) {
        try {
            response = fileStream(response, title);
            WritableWorkbook book = Workbook.createWorkbook(response.getOutputStream());

            book = download(book, title, colTitle, datas);
            book.write();
            book.close();
        } catch (WriteException | IOException e) {
            throw new RuntimeException("ExcelExportUtil common error");
        }
    }

    /**
     * 获取上传Excel所在服务器中名称
     * @param title 标题
     * @param colTitle 列名
     * @param datas 数据
     * @param serverPath 服务器地址
     * @return
     */
    public static String getUploadExcelServerName(String title, Map<String, String> colTitle, List<Map<String, Object>> datas, String serverPath) {
        OutputStream output = null;
        //Excel在服务器中名称
        String serverFileName = title + DateUtil.getTimestamp() + ".xls";
        try {
            File file = new File(serverPath + serverFileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            output = new FileOutputStream(file);
            WritableWorkbook book = Workbook.createWorkbook(output);
            book = download(book, title, colTitle, datas);
            book.write();
            book.close();
        } catch (WriteException | IOException e) {
            throw new RuntimeException("ExcelExportUtil common error");
        } finally {
            StreamUtil.closeStream(output);
        }

        return serverFileName;
    }

    private static WritableWorkbook download(WritableWorkbook book, String title, Map<String, String> colTitle, List<Map<String, Object>> datas) {
        WritableSheet sheet = book.createSheet(title, 0);
        SheetSettings settings = sheet.getSettings();
        settings.setVerticalFreeze(2);

        try {
            ExcelExportUtil exportUtil = new ExcelExportUtil();
            //initial all col weight
            int colSize = colTitle.size();
            for (int i = 0; i < colSize; i++) {
                sheet.setColumnView(i, 20);
            }
            //title setting
            sheet.setRowView(0, TITLE_HIGH);
            sheet.mergeCells(0, 0, colSize - 1, 0);
            sheet.addCell(new Label(0, 0, title, exportUtil.titleFormat));
            //col title setting
            int colIndex = 0;
            sheet.setRowView(1, COL_TITLE_HIGH);
            for (Map.Entry<String, String> entry : colTitle.entrySet()) {
                sheet.addCell(new Label(colIndex, 1, entry.getValue(), exportUtil.colTitleFormat));
                colIndex++;
            }
            //col data setting
            int rowIndex = 2;
            for(Map<String, Object> data : datas) {
                sheet.setRowView(rowIndex, COL_DATA_HIGH);
                colIndex = 0;
                for(String key : colTitle.keySet()) {
                    sheet.addCell(new Label(colIndex, rowIndex, getColValue(data, key), exportUtil.colDataFormat));
                    colIndex++;
                }
                rowIndex++;
            }
        } catch (WriteException e) {
            throw new RuntimeException("ExcelExportUtil product table error!");
        }

        return book;
    }

    /**
     * 获取列值，如果不存在设置空字符串
     * @param data
     * @param key
     * @return
     */
    private static String getColValue(Map<String, Object> data, String key) {
        String result = "";
        for(Map.Entry<String, Object> entry : data.entrySet()) {
            if (StringUtils.equals(key, entry.getKey()) && null != entry.getValue()) {
                result = String.valueOf(entry.getValue());
                break;
            }
        }
        return result;
    }
}

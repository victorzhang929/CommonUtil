package com.victorzhang.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import static com.victorzhang.common.constant.EncryptConstant.UTF_8;

/**
 * PDF导出工具类
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-05-21 11:00:40
 */
public class PdfExportUtil {

    private static final int MARGIN_LEFT = 20;
    private static final int MARGIN_RIGHT = 20;
    private static final int MARGIN_TOP = 30;
    private static final int MARGIN_BOTTOM = 30;

    private static Font fontChineseColData;
    private static Font fontChineseColTitle;
    private static Font fontChineseTitle;

    static {
        try {
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            fontChineseColData = new Font(bfChinese, 10, Font.NORMAL);
            fontChineseColTitle = new Font(bfChinese, 12, Font.BOLD);
            fontChineseTitle = new Font(bfChinese, 14, Font.BOLD);
        } catch (DocumentException | IOException e) {
            System.err.println("PDF中文编码失败");
        }
    }

    /**
     * 导出PDF（TABLE形式）
     *
     * @param response
     * @param title    输出文件名
     * @param colTitle 列名
     * @param datas    数据
     */
    public static void export(HttpServletResponse response, String title, Map<String, String> colTitle, List<Map<String, Object>> datas) {
        ByteArrayOutputStream baos = null;
        try {
            Document document = new Document(PageSize.A4, MARGIN_LEFT, MARGIN_RIGHT, MARGIN_TOP, MARGIN_BOTTOM);
            baos = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, baos);

            document.open();
            document.add(createTable(title, colTitle, datas));
            response = fileStream(response, title);
            baos.writeTo(response.getOutputStream());

            writer.flush();
            writer.close();
            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.closeStream(baos);
        }
    }

    /**
     * 获取文件下载流
     *
     * @param response
     * @param fileName 文件名
     * @return
     */
    private static HttpServletResponse fileStream(HttpServletResponse response, String fileName) {
        response.setContentType("application/pdf;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="
                + fileNameHandler(fileName) + DateUtil.getTimestamp() + ".pdf");
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
            return URLEncoder.encode(fileName, UTF_8);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * 创建表格信息
     *
     * @param title    标题
     * @param colTitle 列标题
     * @param datas    数据
     * @return
     */
    private static PdfPTable createTable(String title, Map<String, String> colTitle, List<Map<String, Object>> datas) {
        //设置列数
        int size = colTitle.size();
        PdfPTable table = new PdfPTable(size);
        //设置标题信息
        PdfPCell cellTitle = new PdfPCell();
        cellTitle.setFixedHeight(40);
        cellTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellTitle.setVerticalAlignment(Element.ALIGN_CENTER);
        cellTitle.setPhrase(new Paragraph(title, fontChineseTitle));
        cellTitle.setColspan(size);
        table.addCell(cellTitle);
        //设置列名信息
        PdfPCell cell = new PdfPCell();
        cell.setFixedHeight(20);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        for (Map.Entry<String, String> entry : colTitle.entrySet()) {
            cell.setPhrase(new Paragraph(entry.getValue(), fontChineseColTitle));
            table.addCell(cell);
        }
        //填充数据
        for (Map<String, Object> data : datas) {
            for (String key : colTitle.keySet()) {
                //默认填充为空字符串
                String result = "";
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    if (null != entry.getValue()) {
                        result = String.valueOf(entry.getValue());
                    }
                    cell.setPhrase(new Paragraph(result, fontChineseColData));
                    table.addCell(cell);
                    data.remove(key);
                    break;
                }
            }
        }
        return table;
    }
}

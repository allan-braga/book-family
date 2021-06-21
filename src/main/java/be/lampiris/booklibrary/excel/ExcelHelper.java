package be.lampiris.booklibrary.excel;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;

import java.util.HashMap;
import java.util.Map;

import static org.apache.poi.ss.usermodel.HorizontalAlignment.LEFT;

public class ExcelHelper {

    protected static final int DEFAULT_COLUMN_WIDTH = 25;
    private static final String COLOR_BACKGROUND_WHITE = "FFFFFF";
    private static final String COLOR_BACKGROUND_GREY = "F5F5F5";
    private static final String COLOR_BACKGROUND_GREY_HEADER = "DDDDDD";
    private static final String COLOR_BORDER = "CCCCCC";
    private final Map<String, XSSFCellStyle> styleMap = new HashMap<>();

    public XSSFCellStyle makeHeaderStyle(final XSSFSheet sheet, final HorizontalAlignment align) {
        XSSFCellStyle style = styleMap.get(align.name() + "-styleHeaderCell");
        if (style == null) {
            style = sheet.getWorkbook().createCellStyle();
            final XSSFFont defaultFont = sheet.getWorkbook().createFont();
            defaultFont.setBold(true);
            style.setFont(defaultFont);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setAlignment(align);
            style.setFillForegroundColor(getColor(COLOR_BACKGROUND_GREY_HEADER));
            applyBorder(style);
            styleMap.put(align.name() + "-styleHeaderCell", style);
        }
        return style;
    }


    public void writeTextHeaderCell(final XSSFRow row,
                                    final int position,
                                    final String header) {
        writeHeaderCell(row, position, header, LEFT);
    }

    public void writeTextCell(final XSSFRow row,
                              final int position,
                              final String text) {
        final XSSFCell cell = row.createCell(position);
        cell.setCellStyle(makeTextCellStyle(row.getSheet(), row.getRowNum()));

        printText(cell, text);
    }


    private void writeHeaderCell(final XSSFRow row,
                                 final int position,
                                 final String header,
                                 final HorizontalAlignment alignment) {
        final XSSFCell cell = row.createCell(position);
        cell.setCellStyle(makeHeaderStyle(row.getSheet(), alignment));
        cell.setCellValue(header);
    }

    private XSSFCellStyle makeTextCellStyle(final XSSFSheet sheet, final int index) {
        XSSFCellStyle style = styleMap.get("styleTextCell-" + backgroundStr(index));
        if (style == null) {
            style = sheet.getWorkbook().createCellStyle();
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setWrapText(true);
            applyBorder(style);
            applyCellColorBackground(index, style);
            styleMap.put("styleTextCell-" + backgroundStr(index), style);
        }
        return style;
    }

    private void printText(final XSSFCell cell, final String text) {
        if (text != null) {
            cell.setCellValue(text);
        }
    }

    private void applyCellColorBackground(final int index, final XSSFCellStyle style) {
        if (index % 2 == 0) {
            style.setFillForegroundColor(getColor(COLOR_BACKGROUND_GREY));
        } else {
            style.setFillForegroundColor(getColor(COLOR_BACKGROUND_WHITE));
        }
    }

    private void applyBorder(final XSSFCellStyle style) {
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setTopBorderColor(getColor(COLOR_BORDER));
        style.setRightBorderColor(getColor(COLOR_BORDER));
        style.setBottomBorderColor(getColor(COLOR_BORDER));
        style.setLeftBorderColor(getColor(COLOR_BORDER));
    }

    XSSFColor getColor(final String hexa) {
        try {
            return new XSSFColor(Hex.decodeHex(hexa), null);
        } catch (DecoderException eParam) {
            return null;
        }
    }

    private String backgroundStr(final int index) {
        return index % 2 == 0 ? "white" : "grey";
    }
}

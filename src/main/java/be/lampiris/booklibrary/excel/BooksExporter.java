package be.lampiris.booklibrary.excel;

import be.lampiris.booklibrary.model.Book;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public class BooksExporter extends ExcelHelper {

    private static final int COL_ID = 0;
    private static final int COL_NAME = 1;
    private static final int COL_DESCRIPTION = 2;
    private static final int COL_AUTHOR = 3;
    private static final int COL_PAGES = 4;
    private static final int COL_BOOK_FAMILY = 5;


    private XSSFWorkbook workbook;
    private List<Book> books;

    public BooksExporter(final List<Book> books) {
        this.books = books;
        this.workbook = new XSSFWorkbook();
    }

    public XSSFWorkbook export() {
        this.createReportSheet();
        return workbook;
    }

    private void createReportSheet() {
        final XSSFSheet sheet = workbook.createSheet();
        sheet.setDefaultColumnWidth(DEFAULT_COLUMN_WIDTH);

        writeReportSheetHeader(sheet);
        int i = 1;
        for (final Book book : books) {
            writeReportRow(sheet, i++, book);
        }
    }

    private void writeReportSheetHeader(XSSFSheet sheet) {
        final XSSFRow row = sheet.createRow(0);
        writeTextHeaderCell(row, COL_ID, "ID");
        writeTextHeaderCell(row, COL_NAME, "NAME");
        writeTextHeaderCell(row, COL_DESCRIPTION, "DESCRIPTION");
        writeTextHeaderCell(row, COL_AUTHOR, "AUTHOR");
        writeTextHeaderCell(row, COL_PAGES, "PAGES");
        writeTextHeaderCell(row, COL_BOOK_FAMILY, "BOOK_FAMILY");
    }

    private void writeReportRow(XSSFSheet sheet, int rowIndex, Book book) {
        final XSSFRow row = sheet.createRow(rowIndex);
        writeTextCell(row, COL_ID, book.getId().toString());
        writeTextCell(row, COL_NAME, book.getName());
        writeTextCell(row, COL_DESCRIPTION, book.getDescription());
        writeTextCell(row, COL_AUTHOR, book.getAuthor());
        writeTextCell(row, COL_PAGES, String.valueOf(book.getPages()));
        writeTextCell(row, COL_BOOK_FAMILY, book.getBookFamily().getName());
    }
}

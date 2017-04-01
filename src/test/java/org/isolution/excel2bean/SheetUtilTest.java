package org.isolution.excel2bean;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class SheetUtilTest {

    @Test
    public void retrieves_object_field_names_from_first_row_in_sheet() {
        final XSSFWorkbook workbook = new XSSFWorkbook();
        final XSSFSheet sheet = workbook.createSheet();
        final XSSFRow row1 = sheet.createRow(0);
        row1.createCell(0).setCellValue("StudentId");
        row1.createCell(1).setCellValue("Firstname");
        row1.createCell(2).setCellValue("Surname");

        final XSSFRow row2 = sheet.createRow(1);
        row2.createCell(0).setCellValue(1);
        row2.createCell(1).setCellValue("Alex");
        row2.createCell(2).setCellValue("Wibowo");

        final Set<String> fieldNames = SheetUtil.readFieldNames(sheet);
        assertThat(fieldNames, containsInAnyOrder("StudentId", "Firstname", "Surname"));
    }

}
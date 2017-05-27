package org.isolution.excel2bean;

import com.google.common.collect.ImmutableMap;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import static java.util.stream.StreamSupport.stream;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@SuppressWarnings("unchecked")
public class SheetUtilTest {

    private XSSFWorkbook workbook;

    @Before
    public void setup() {
        workbook = new XSSFWorkbook();
        final XSSFSheet sheet = workbook.createSheet();
        TestHelper.addRow(sheet, new String[]{"StudentId", "Firstname", "Surname"});
        TestHelper.addRow(sheet, new String[]{"1", "Alex", "Wibowo"});
        TestHelper.addRow(sheet, new String[]{"2", "Randy", "Wong"});
    }

    @Test
    public void retrieves_object_field_names_from_first_row_in_sheet() {
        final LinkedHashSet<String> fieldNames = SheetUtil.readFieldNames(workbook.getSheetAt(0));
        assertThat(fieldNames, contains("StudentId", "Firstname", "Surname"));
    }

    @Test
    public void retrieves_data_with_all_the_fields() {
        final LinkedHashSet<String> fieldNames = new LinkedHashSet<>();
        fieldNames.add("StudentId");
        fieldNames.add("Firstname");
        fieldNames.add("Surname");

        final List<Map<String, String>> values = SheetUtil.readData(fieldNames, workbook.getSheetAt(0));
        assertThat(values.size(), equalTo(2));

        final Map<String, String> data1 = ImmutableMap.<String, String>builder()
                .put("StudentId", "1")
                .put("Firstname", "Alex")
                .put("Surname", "Wibowo")
                .build();

        final Map<String, String> data2 = ImmutableMap.<String, String>builder()
                .put("StudentId", "2")
                .put("Firstname", "Randy")
                .put("Surname", "Wong")
                .build();

        assertThat(values, contains(data1, data2));
    }

}
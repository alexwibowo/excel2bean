package org.isolution.excel2bean;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;
import static org.isolution.excel2bean.TestHelper.addRow;

public class MetadataSheetReaderTest {

    private MetadataSheetReader metadataSheetReader;
    private XSSFWorkbook workbook;

    @Before
    public void setUp() throws Exception {
        metadataSheetReader = new MetadataSheetReader();
        workbook = new XSSFWorkbook();
    }

    @Test
    public void metadata_sheet_has_METADATA_as_the_name() {
        final Throwable throwable = catchThrowable(() -> {
            final XSSFSheet sheet = workbook.createSheet("FOO");
            metadataSheetReader.process(sheet);
        });
        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Metadata sheet must be named METADATA");
    }

    @Test
    public void first_row_has_SheetName_and_ClassName_as_values() throws Exception {
        final XSSFSheet sheet = workbook.createSheet("METADATA");
        addRow(sheet, new String[]{"SheetName", "ClassName"});
        assertThatCode(() -> {
            metadataSheetReader.process(sheet);
        }).doesNotThrowAnyException();
    }

    @Test
    public void throws_error_when_first_row_has_wrong_column_names() {
        final XSSFSheet sheet = workbook.createSheet("METADATA");
        addRow(sheet, new String[]{"SomeArbitrary", "ColumnName"});
        assertThatThrownBy(() -> metadataSheetReader.process(sheet))
                .hasMessage(WorkbookValidationException.wrongMetadataSheetFormat().getMessage());
    }

    @Test
    public void throws_error_when_first_row_doesnt_have_all_required_column_names() {
        final XSSFSheet sheet = workbook.createSheet("METADATA");
        addRow(sheet, new String[]{"SheetName"});
        assertThatThrownBy(() -> metadataSheetReader.process(sheet))
                .hasMessage(WorkbookValidationException.wrongMetadataSheetFormat().getMessage());
    }

    @Test
    public void second_row_and_onwards_contains_sheet_names_and_class_names() throws Exception {


    }
}
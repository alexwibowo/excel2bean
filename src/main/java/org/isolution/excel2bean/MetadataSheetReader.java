package org.isolution.excel2bean;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public final class MetadataSheetReader {

    public static final String SHEET_NAME_COLUMN_NAME = "SheetName";
    public static final String CLASS_NAME_COLUMN_NAME = "ClassName";

    public Map<String, Class> process(final @NotNull XSSFSheet metadataSheet) {
        Objects.requireNonNull(metadataSheet);
        if (!Objects.equals(metadataSheet.getSheetName(), WorkbookProcessor.METADATA_SHEET_NAME)) {
            throw new IllegalArgumentException("Metadata sheet must be named " + WorkbookProcessor.METADATA_SHEET_NAME);
        }

        final Iterator<Row> rowIterator = metadataSheet.iterator();
        if (rowIterator.hasNext()) {
            final Row headerRow = rowIterator.next();
            final Cell firstCell = headerRow.getCell(0);
            final Cell secondCell = headerRow.getCell(1);
            if (firstCell == null || secondCell == null ||
                    !Objects.equals(firstCell.getStringCellValue(), SHEET_NAME_COLUMN_NAME) ||
                    !Objects.equals(secondCell.getStringCellValue(), CLASS_NAME_COLUMN_NAME)) {
                throw WorkbookValidationException.wrongMetadataSheetFormat();
            }
        }
        return null;
    }
}

package org.isolution.excel2bean;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toSet;

public final class SheetUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SheetUtil.class);

    /**
     * @param sheet sheet to read from
     * @return all field names in a given sheet
     */
    @NotNull
    public static Set<String> readFieldNames(final @NotNull Sheet sheet) {
        LOGGER.info("Reading field names for {}", sheet.getSheetName());
        final Row headerRow = sheet.getRow(0);
        final Set<String> fieldNames = StreamSupport.stream(headerRow.spliterator(), false)
                .map(Cell::getStringCellValue)
                .collect(toSet());
        LOGGER.debug("Field names for {} are: {}", sheet.getSheetName(), fieldNames);
        return fieldNames;
    }
}

package org.isolution.excel2bean;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

public final class SheetUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SheetUtil.class);

    /**
     * @param sheet sheet to read field names from
     * @return all field names in a given sheet, in the order the columns are defined
     */
    @NotNull
    public static LinkedHashSet<String> readFieldNames(final @NotNull Sheet sheet) {
        LOGGER.info("Reading field names from {}", sheet.getSheetName());
        final Row headerRow = sheet.getRow(0);
        final LinkedHashSet<String> fieldNames = stream(headerRow.spliterator(), false)
                .map(Cell::getStringCellValue)
                .collect(toCollection(LinkedHashSet::new));
        LOGGER.debug("Field names for {} are: {}", sheet.getSheetName(), fieldNames);
        return fieldNames;
    }

    /**
     * @param sheet sheet to read data from
     * @return List of data
     */
    @NotNull
    public static List<Map<String, String>> readData(final @NotNull LinkedHashSet<String> fieldNames,
                                                     final @NotNull Sheet sheet) {
        LOGGER.info("Reading data from {}, expecting field {}", sheet.getSheetName(), fieldNames);
        return stream(sheet.spliterator(), false)
                .skip(1) // first row in sheet is the field names
                .map(worksheetRow -> {
                    final Map<String, String> row = new HashMap<>();
                    stream(fieldNames.spliterator(), false)
                            .forEach(fieldName -> {
                                final Cell column = worksheetRow.getCell(row.size(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                                row.put(fieldName, column.getStringCellValue());
                            });
                    return row;
                }).collect(toList());
    }
}

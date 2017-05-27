package org.isolution.excel2bean;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public final class WorkbookProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkbookProcessor.class);
    public static final String METADATA_SHEET_NAME = "METADATA";

    private final WorkbookValidator workbookValidator = new WorkbookValidator();

    public void read(final File file) {
        LOGGER.info("Opening file: {}", file);
        try (final OPCPackage pkg = OPCPackage.open(file)){
            final List<Sheet> sheets = getSheets(pkg);

            final Object metadata = processMetadataSheet(sheets.get(0));

            if (sheets.size() > 1) {
                sheets.subList(1, sheets.size()).stream()
                        .map(sheet -> processDataSheet(metadata, sheet))
                        .collect(toList());
            }


        } catch (final Exception exception) {
            LOGGER.error("");
        }
    }

    private List<Object> processDataSheet(final Object metadata, final Sheet sheet) {
        final Set<String> fieldNames = SheetUtil.readFieldNames(sheet);

        return null;
    }

    private Object processMetadataSheet(final Sheet metadataSheet) {
        LOGGER.info("Processing metadata sheet");
        return null;
    }

    private List<Sheet> getSheets(final OPCPackage pkg) throws IOException {
        LOGGER.info("Reading workbook");
        final XSSFWorkbook workbook = new XSSFWorkbook(pkg);

        workbookValidator.validate(workbook);

        LOGGER.info("Reading sheets");
        final List<Sheet> sheets = new ArrayList<>();
        for (final Sheet sheet : workbook) {
            sheets.add(sheet);
        }
        return sheets;
    }

}

package org.isolution.excel2bean;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.isolution.excel2bean.WorkbookProcessor.METADATA_SHEET_NAME;

public final class WorkbookValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkbookValidator.class);

    public void validate(final @NotNull XSSFWorkbook workbook) {
        LOGGER.info("Validating workbook");
        validateNonEmptyWorkbook(workbook);
        validateHasMetadataSheet(workbook);
        validateHasDataSheet(workbook);
        validateMetadataConsistency(workbook);
    }

    private void validateHasMetadataSheet(final @NotNull XSSFWorkbook workbook) {
        LOGGER.debug("Validating workbook has metadata sheet");
        final XSSFSheet metadataSheet = workbook.getSheet(METADATA_SHEET_NAME);
        if (metadataSheet == null) {
            throw WorkbookValidationException.missingMetadataSheet(METADATA_SHEET_NAME);
        }
    }

    private void validateNonEmptyWorkbook(final @NotNull XSSFWorkbook workbook) {
        LOGGER.debug("Validating workbook is not empty");
        final int numberOfSheets = workbook.getNumberOfSheets();
        if (numberOfSheets == 0) {
            throw WorkbookValidationException.emptyWorkbook();
        }
    }

    private void validateHasDataSheet(final @NotNull XSSFWorkbook workbook) {
        LOGGER.debug("Validating workbook has at least one data sheet");
        final int numberOfSheets = workbook.getNumberOfSheets();
        if (numberOfSheets < 1) {
            throw WorkbookValidationException.noDataSheet();
        }
    }

    private void validateMetadataConsistency(final @NotNull XSSFWorkbook workbook) {

    }
}

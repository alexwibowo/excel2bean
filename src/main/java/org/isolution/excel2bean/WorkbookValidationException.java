package org.isolution.excel2bean;

public class WorkbookValidationException extends Excel2BeanException {

    public WorkbookValidationException(final String message) {
        super(message);
    }

    public static WorkbookValidationException emptyWorkbook() {
        return new WorkbookValidationException("Workbook must contain at least one sheet.");
    }

    public static WorkbookValidationException missingMetadataSheet(final String metadataSheetName) {
        return new WorkbookValidationException("Workbook must contain at least one sheet, the metadata sheet named '" + metadataSheetName +"'");
    }

    public static WorkbookValidationException noDataSheet() {
        return new WorkbookValidationException("Workbook must contain at least one data sheet.");
    }

    public static WorkbookValidationException wrongMetadataSheetFormat() {
        return new WorkbookValidationException("Workbook metadata sheet must have 'SheetName' as first column and 'ClassName' as second column.");
    }
}

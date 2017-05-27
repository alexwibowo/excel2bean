package org.isolution.excel2bean;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.StreamSupport.stream;

@SuppressWarnings("ConstantConditions")
public final class TestHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestHelper.class);

    public static File getFile(final String fileName) throws URISyntaxException {
        return new File(AcceptanceTest.class.getClassLoader().getResource(fileName).toURI());
    }

    /**
     * Append values to the given sheet.
     *
     * @return newly added row
     * @throws UnsupportedOperationException when no more rows can be added to the sheet
     */
    static @NotNull XSSFRow addRow(final @NotNull XSSFSheet sheet,
                                   final @NotNull String[] values) {
        Objects.requireNonNull(sheet);
        Objects.requireNonNull(values);
        final long rowCount = stream(sheet.spliterator(), false).count();

        if (rowCount > Integer.MAX_VALUE) {
            // This is because the API on XSSFSheet.createRow() only accepts int. I suppose the maximum number of row supported is Integer.MAX_VALUE
            final String message = String.format("Unable to add more rows to sheet %s. It already has %d rows.", sheet.getSheetName(), rowCount);
            LOGGER.error(message);
            throw new UnsupportedOperationException(message);
        }

        // zero based length
        LOGGER.debug("Creating new row {}", rowCount);
        final XSSFRow newRow = sheet.createRow((int) rowCount);
        for (int i = 0; i < values.length; i++) {
            newRow.createCell(i).setCellValue(values[i]);
        }
        return newRow;
    }
}

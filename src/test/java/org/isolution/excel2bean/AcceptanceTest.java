package org.isolution.excel2bean;

import org.junit.Test;

import java.net.URISyntaxException;

public class AcceptanceTest {

    @Test
    public void readOneSheet() throws URISyntaxException {
        final WorkbookProcessor workbookProcessor = new WorkbookProcessor();

        workbookProcessor.read(TestHelper.getFile("oneSheet.xlsx"));

    }


}

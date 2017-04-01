package org.isolution.excel2bean;

import java.io.File;
import java.net.URISyntaxException;

@SuppressWarnings("ConstantConditions")
public final class TestHelper {

    public static File getFile(final String fileName) throws URISyntaxException {
        return new File(AcceptanceTest.class.getClassLoader().getResource(fileName).toURI());
    }
}

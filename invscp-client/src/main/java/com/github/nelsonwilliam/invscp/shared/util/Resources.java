package com.github.nelsonwilliam.invscp.shared.util;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Resources {

    public static String readResource(final String path)
            throws FileNotFoundException {
        final ClassLoader classLoader =
                Thread.currentThread().getContextClassLoader();
        final InputStream stream = classLoader.getResourceAsStream(path);
        if (stream == null) {
            throw new FileNotFoundException();
        }
        final Scanner scanner = new Scanner(stream);
        scanner.useDelimiter("\\A");
        final String string = scanner.next();
        scanner.close();
        return string;
    }
}

package com.github.nelsonwilliam.invscp.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Relatorios {

    public static File salvar(final String path, final String text)
            throws IOException {

        final File file = new File(path);
        file.getParentFile().mkdirs();

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file, "UTF-8");
            writer.print(text);
        } finally {
            writer.close();
        }

        return file;
    }

    public static String escapeToHtml(final Object s) {
        // Fonte: https://stackoverflow.com/a/5135149

        if (s == null || s.toString().isEmpty()) {
            return "";
        }
        final StringBuilder builder = new StringBuilder();
        boolean previousWasASpace = false;
        for (final char c : s.toString().toCharArray()) {
            if (c == ' ') {
                if (previousWasASpace) {
                    builder.append("&nbsp;");
                    previousWasASpace = false;
                    continue;
                }
                previousWasASpace = true;
            } else {
                previousWasASpace = false;
            }
            switch (c) {
                case '<':
                    builder.append("&lt;");
                    break;
                case '>':
                    builder.append("&gt;");
                    break;
                case '&':
                    builder.append("&amp;");
                    break;
                case '"':
                    builder.append("&quot;");
                    break;
                case '\n':
                    builder.append("<br>");
                    break;
                case '\t':
                    builder.append("&nbsp; &nbsp; &nbsp; &nbsp;");
                    break;
                default:
                    if (c < 128) {
                        builder.append(c);
                    } else {
                        builder.append("&#").append((int) c).append(";");
                    }
            }
        }
        return builder.toString();
    }

}

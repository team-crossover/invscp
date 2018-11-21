package com.github.nelsonwilliam.invscp.util;

import java.io.IOException;
import java.io.PrintWriter;

public class Relatorios {

    public static void salvar(final String path, final String text)
            throws IOException {

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(path, "UTF-8");
            writer.print(text);
        } finally {
            writer.close();
        }
    }

    public static String escapeToHtml(final Object s) {
        // Fonte: https://stackoverflow.com/a/5135149

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
                // We need Tab support here, because we print StackTraces as
                // HTML
                case '\t':
                    builder.append("&nbsp; &nbsp; &nbsp;");
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

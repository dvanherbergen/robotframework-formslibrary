package org.robotframework.formslibrary.util;

public class TextUtil {

    public static String getFirstSegment(String input, String divider) {

        if (input == null || input.trim().length() == 0) {
            return null;
        }

        if (input.indexOf(divider) != -1) {
            return input.split(divider)[0].trim();
        } else {
            return input.trim();
        }

    }

    public static String getNextSegments(String input, String divider) {

        if (input.indexOf(divider) != -1) {
            return input.substring(input.indexOf(divider) + 1).trim();
        } else {
            return null;
        }
    }

    public static String removeNewline(String input) {

        return input.replaceAll("\\r?\\n", " ");
    }

    public static boolean matches(String actual, String expected) {

        if (actual == null) {
            actual = "";
        }
        if (expected == null) {
            expected = "";
        }

        actual = actual.toLowerCase().trim();
        expected = expected.toLowerCase().trim();

        if (expected.endsWith("*")) {
            return actual.startsWith(expected.substring(0, expected.length() - 2));
        }

        return actual.equals(expected);
    }

    public static String formatArray(String[] values) {

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                builder.append(" - ");
            }
            builder.append(values[i]);
        }
        return builder.toString();
    }
}

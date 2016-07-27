package org.robotframework.formslibrary.util;

import java.util.Set;

/**
 * Utility class for various string text operations.
 */
public class TextUtil {

    /**
     * Extract the first segment of the input. For example for "Menu1 > Menu2",
     * it will return "Menu1".
     */
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

    /**
     * Return the input text with the first segment removed.
     */
    public static String getNextSegments(String input, String divider) {

        if (input.indexOf(divider) != -1) {
            return input.substring(input.indexOf(divider) + 1).trim();
        } else {
            return null;
        }
    }

    /**
     * Strip new line characters from text.
     */
    public static String removeNewline(String input) {
        return input.replaceAll("\\r?\\n", " ");
    }

    /**
     * Check if a given string matches the expected string pattern.
     * 
     * The expected pattern can contain wildcards.
     * 
     * @param actual
     *            text found in the appliction
     * @param expected
     *            text described in the test case
     * @return true if it matches
     */
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
            return actual.startsWith(expected.substring(0, expected.length() - 1));
        }

        return actual.equals(expected);
    }

    /**
     * Concatenate array values with a separator.
     */
    public static String concatenateArrayElements(String[] values) {

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                builder.append(" - ");
            }
            builder.append(values[i]);
        }
        return builder.toString();
    }

    /**
     * Concatenate set values.
     */
    public static String concatenateSetValues(Set<String> options) {
        StringBuilder builder = new StringBuilder();
        for (String s : options) {
            builder.append(" '");
            builder.append(s);
            builder.append("'");
        }
        return builder.toString();
    }
}

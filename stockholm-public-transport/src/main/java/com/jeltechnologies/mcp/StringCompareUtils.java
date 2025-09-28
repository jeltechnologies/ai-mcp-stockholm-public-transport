package com.jeltechnologies.mcp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for fuzzy string comparison.
 */
public final class StringCompareUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringCompareUtils.class);
    
    private record ComparedString(String compared, int similarityScore) implements Comparable<ComparedString> {
        @Override
        public int compareTo(ComparedString o) {
            return (int) Math.round(similarityScore - o.similarityScore);
        }
    };

    /**
     * Returns true if the two strings are at least {@code threshold} similar.
     *
     * @param s1        first string (may be null)
     * @param s2        second string (may be null)
     * @param threshold similarity threshold between 0.0 (never match) and 1.0 (exact match)
     * @return true if similarity ≥ threshold
     * @throws IllegalArgumentException if threshold is outside 0–1 range
     */
    public static boolean approximatelyEquals(String s1, String s2, double threshold) {
        if (threshold < 0.0 || threshold > 1.0) {
            throw new IllegalArgumentException("threshold must be between 0.0 and 1.0");
        }

        // treat nulls as empty strings
        s1 = Objects.toString(s1, "");
        s2 = Objects.toString(s2, "");

        double similarity = similarityScore(s1, s2);
        return similarity >= threshold;
    }

    /**
     * Computes a similarity score between 0.0 (completely different) and 1.0 (identical).
     *
     * @param s1 first string
     * @param s2 second string
     * @return similarity score
     */
    public static double similarityScore(String s1, String s2) {
        // quick path for identical strings
        if (s1.equals(s2)) {
            return 1.0;
        }

        int maxLen = Math.max(s1.length(), s2.length());
        if (maxLen == 0) { // both empty
            return 1.0;
        }

        int distance = levenshteinDistance(s1, s2);
        // normalise to a value in [0,1]
        return 1.0 - ((double) distance / maxLen);
    }

    /**
     * Classic Levenshtein distance implementation (edit distance).
     *
     * @param s1 first string
     * @param s2 second string
     * @return the number of insert/delete/substitute operations needed to turn s1 into s2
     */
    public static int levenshteinDistance(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();

        // a very small optimisation: use a 1‑dimensional array
        int[] prev = new int[len2 + 1];
        int[] cur = new int[len2 + 1];

        // initial row (distance from empty s1)
        for (int j = 0; j <= len2; j++) {
            prev[j] = j;
        }

        for (int i = 1; i <= len1; i++) {
            cur[0] = i; // distance from empty s2
            char c1 = s1.charAt(i - 1);

            for (int j = 1; j <= len2; j++) {
                char c2 = s2.charAt(j - 1);
                int cost = (c1 == c2) ? 0 : 1;

                cur[j] = Math.min(
                        Math.min(cur[j - 1] + 1, // insertion
                                prev[j] + 1), // deletion
                        prev[j - 1] + cost); // substitution
            }

            // swap arrays
            int[] tmp = prev;
            prev = cur;
            cur = tmp;
        }

        int result = prev[len2];
        // System.out.println(s1 + " <==> " + s2 + " = " + result);
        return result;
    }

    /**
     * Normalises a string: – lower‑cases it – replaces å, ä with a – replaces ö with o
     *
     * @param s source string (may be null – returns null)
     * @return normalised string (never null)
     */
    private static String normalize(String s) {
        if (s == null) {
            return "";
        }
        String lower = s.toLowerCase();

        return lower
                .replace('å', 'a')
                .replace('ä', 'a')
                .replace('ö', 'o')
                .replace('é', 'e')
                .replace('ë', 'e')
                .replace('ê', 'e')
                .replace('è', 'e')
                .replace('ï', 'i')
                .replace('à', 'a')
                .replace('á', 'a');
    }

    public static List<String> findBestMatches(String search, List<String> items) {
        String normalizedSearch = normalize(search);
        List<ComparedString> comparedStrings = new ArrayList<ComparedString>(items.size());
        for (String item : items) {
            String normalizedItem = normalize(item);
            if (normalizedItem.contains(normalizedSearch)) {
                int simScore = levenshteinDistance(normalizedItem, normalizedSearch);
                comparedStrings.add(new ComparedString(item, simScore));
            }
        }
        Collections.sort(comparedStrings);

        if (LOGGER.isTraceEnabled()) {
            for (int i = 0; i < comparedStrings.size(); i++) {
                ComparedString cs = comparedStrings.get(i);
                LOGGER.trace(" " + (i + 1) + ": " + cs.compared + " => " + cs.similarityScore);
            }
        }

        List<String> result = new ArrayList<String>(items.size());
        for (ComparedString comparedString : comparedStrings) {
            result.add(comparedString.compared());
        }
        return result;
    }

}

package ch.zhaw.ads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class FuzzySearchServer implements CommandExecutor {
    // List of all (original) names, as they appear in the ranking list
    public static List<String> names = new ArrayList<>();

    // Trigram index:
    //   key   = trigram (always lower case, without spaces)
    //   value = list of indices into {@link #names} where this trigram occurs
    public static Map<String, List<Integer>> trigrams = new HashMap<>();

    // Helper map used during a search:
    //   key   = index into {@link #names}
    //   value = number of matching trigrams with the search string
    public static Map<Integer, Integer> counts = new HashMap<>();

    /**
     * Load all names from the given ranking-list string.
     * Every line has the form "Lastname Firstname;time".
     * The time-part is ignored; only the name before ';' is stored.
     *
     * Each (normalised) name is stored at most once – duplicates are skipped.
     */
    public static void loadNames(String nameString) {
        names.clear();
        trigrams.clear();
        counts.clear();

        if (nameString == null) {
            return;
        }

        // Use a set of normalised names to avoid duplicates
        HashSet<String> seen = new HashSet<>();

        String[] lines = nameString.split("\\r?\\n");
        for (String line : lines) {
            if (line == null) continue;
            line = line.trim();
            if (line.isEmpty()) continue;

            // Split "Name;time"
            String[] parts = line.split(";");
            if (parts.length == 0) continue;

            String name = parts[0].trim();
            if (name.isEmpty()) continue;

            String normalized = normalize(name);
            if (seen.add(normalized)) {
                // Keep the original form in the list
                names.add(name);
            }
        }
    }

    /**
     * Build the trigram index for all names.
     * The method fills the {@link #trigrams} map.
     */
    public static void constructTrigramIndex(List<String> nameList) {
        trigrams.clear();

        for (int index = 0; index < nameList.size(); index++) {
            String originalName = nameList.get(index);
            String normalized = normalize(originalName);

            // Collect all trigrams of this (normalised) name.
            // We use a set to ensure that the same index is stored
            // at most once per trigram.
            HashSet<String> localTris = new HashSet<>(buildTrigrams(normalized));

            for (String tri : localTris) {
                List<Integer> indices = trigrams.get(tri);
                if (indices == null) {
                    indices = new ArrayList<>();
                    trigrams.put(tri, indices);
                }
                indices.add(index);
            }
        }
    }

    /**
     * Find the best matching name for the given search string.
     *
     * @param searchString the (possibly misspelled) search text
     * @param minPercent   minimum similarity (in %) required for a match
     * @return the best matching name from {@link #names},
     *         or {@code null} if no name reaches the required percentage
     */
    public static String find(String searchString, int minPercent) {
        if (searchString == null || searchString.trim().isEmpty()) {
            return null;
        }

        String normalizedSearch = normalize(searchString);
        List<String> searchTris = buildTrigrams(normalizedSearch);

        counts.clear();

        // Count how many trigrams of the search string each name shares
        for (String tri : searchTris) {
            List<Integer> indices = trigrams.get(tri);
            if (indices == null) continue;

            for (Integer idx : indices) {
                counts.put(idx, counts.getOrDefault(idx, 0) + 1);
            }
        }

        if (counts.isEmpty()) {
            return null;
        }

        // Determine the name with the highest count
        int bestIndex = -1;
        int bestCount = -1;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            int idx = entry.getKey();
            int c = entry.getValue();
            if (c > bestCount || (c == bestCount && (bestIndex == -1 || idx < bestIndex))) {
                bestCount = c;
                bestIndex = idx;
            }
        }

        if (bestIndex < 0) {
            return null;
        }

        // Similarity is measured as:
        //   (#commonTrigrams / #trigrams(searchString)) * 100
        int totalTris = searchTris.size();
        int similarity = (totalTris == 0) ? 0 : (int) Math.round((100.0 * bestCount) / totalTris);

        if (similarity < minPercent) {
            return null;
        }

        return names.get(bestIndex);
    }

    // ---------------------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------------------

    /** Normalise a name: lower-case and remove all whitespace. */
    private static String normalize(String s) {
        if (s == null) return "";
        // lower-case, then remove all white-space characters
        return s.toLowerCase().replaceAll("\\s+", "");
    }

    /** Build the list of overlapping trigrams of the given (already normalised) string. */
    private static List<String> buildTrigrams(String s) {
        List<String> res = new ArrayList<>();
        if (s == null) return res;
        int len = s.length();
        if (len < 3) return res;

        for (int i = 0; i <= len - 3; i++) {
            res.add(s.substring(i, i + 3));
        }
        return res;
    }

    public static List<String> trigramForName(String name) {
        List<String> result = new ArrayList<>();
        if (name == null) return result;

        String s = normalize(name);
        int len = s.length();

        if (len < 2) return result;

        // 1) 2-letter prefix
        result.add(s.substring(0, 2));

        // 2) all 3-letter trigrams
        if (len >= 3) {
            for (int i = 0; i <= len - 3; i++) {
                result.add(s.substring(i, i + 3));
            }
        }

        // 3) 2-letter suffix (only add if different from prefix)
        if (len > 2) {
            String suffix = s.substring(len - 2);
            if (!suffix.equals(result.get(0))) {
                result.add(suffix);
            }
        }

        return result;
    }


    public static void addToTrigrams(int index, String trigram) {
        if (trigram == null) return;

        // Normalise the trigram like everywhere else
        String tri = trigram.toLowerCase();

        // Get or create the list for this trigram
        List<Integer> indices = trigrams.get(tri);
        if (indices == null) {
            indices = new ArrayList<>();
            trigrams.put(tri, indices);
        }

        indices.add(index);
    }



    // ---------------------------------------------------------------------
    // CommandExecutor interface
    // ---------------------------------------------------------------------

    /**
     * Simple protocol for the ADS server framework.
     * For this exercise we only need a very small subset:
     *
     *  - The argument is interpreted as the complete ranking list.
     *  - We build the trigram index and return a short confirmation.
     *
     * Actual searching is done via the static {@link #find} method which
     * is used directly in the JUnit tests.
     */
    @Override
    public String execute(String arg) {
        loadNames(arg);
        constructTrigramIndex(names);
        return "OK";
    }

    // Small manual test (not used by JUnit, but handy if you run it yourself)
    public static void main(String[] args) {
        String rangliste = "Mueller Stefan;02:31:14\n" +
                "Marti Adrian;02:30:09\n" +
                "Kiptum Daniel;02:11:31\n" +
                "Ancay Tarcis;02:20:02\n" +
                "Kreibuhl Christian;02:21:47\n" +
                "Ott Michael;02:33:48\n" +
                "Menzi Christoph;02:27:26\n" +
                "Oliver Ruben;02:32:12\n" +
                "Elmer Beat;02:33:53\n" +
                "Kuehni Martin;02:33:36\n";

        loadNames(rangliste);
        constructTrigramIndex(names);

        System.out.println(find("Kiptum Daniel", 80));
        System.out.println(find("Daniel Kiptum", 80));
        System.out.println(find("Kip Dan", 30));
        System.out.println(find("Dan Kip", 30));
    }
}

import java.util.*;

public class BoyerMoore {

    public static int search(String text, String pattern) {
        int n = text.length(),m = pattern.length();
        if (m == 0) return 0;
        if (m > n) return -1;
        int[] badChar = new int[256];
        Arrays.fill(badChar, -1);

        for (int i = 0; i < m; i++) {
            badChar[pattern.charAt(i) % 256] = i;
        }

        int shift = 0;

        while (shift <= n - m) {
            int j = m - 1;

            while (j >= 0 &&
                   pattern.charAt(j) == text.charAt(shift + j)) {
                j--;
            }

            if (j < 0) {
                return shift;
            } else {
                shift += Math.max(
                    1,
                    j - badChar[text.charAt(shift + j) % 256]
                );
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        String text = "ABAAABCD";
        String pattern = "ABC";

        int index = search(text, pattern);

        System.out.println("Pattern found at index: " + index);
    }
}
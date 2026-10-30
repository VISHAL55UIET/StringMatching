public class KMPALGO {

    public static int[] buildLPS(String pattern) {
        int n = pattern.length();
        int[] lps =new int[n];
        int len =0;
        int i = 1;
        while(i < n) {
            if (pattern.charAt(i) ==pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            }else{
                if(len != 0){
                    len = lps[len - 1];  
                }else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }
    public static void search(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();
        if (m == 0 || m > n) {
            return;
        }
        int[] lps = buildLPS(pattern);
        int i = 0,j = 0;
        while (i < n) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }
            if (j == m) {
                System.out.println("Pattern found at index " + (i- j));
                j = lps[j - 1];
            } else if (i < n && text.charAt(i) != pattern.charAt(j)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
    }
    public static void main(String[] args) {
        String text = "ABABDABACDABABCABAB";
        String pattern = "ABABCABAB";
        search(text, pattern);
    }
}
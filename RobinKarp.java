public class RobinKarp {

    private static final int PRIME = 101;
    public static long calculateHash(String str) {
        long hash = 0;
        for (int i = 0; i< str.length(); i++) {
            hash+= str.charAt(i)*Math.pow(PRIME, i);               
        }
        return hash;
    }
    public static long updateHash(long prevHash, char oldChar,char newChar, int patternLength) {
        long newHash = (prevHash - oldChar) / PRIME;
        newHash += newChar* Math.pow(PRIME, patternLength - 1);
        return newHash;      // return hash in the last           

    }
    public static void search(String text, String pattern) { // calculate the    search method       
        int patternLength = pattern.length();     
        // return the shortest length  
        if (patternLength == 0||patternLength > text.length()) {               
            return;
        }
        long patternHash = calculateHash(pattern);
        long textHash = calculateHash(text.substring(0, patternLength));
        for (int i = 0; i <= text.length() - patternLength; i++) {
            if (textHash == patternHash) {
                if (text.substring(i, i + patternLength).equals(pattern)) {
                    System.out.println("Pattern found at index " + i);
                }
            }
            if (i < text.length() - patternLength) {
                textHash = updateHash(textHash,text.charAt(i),text.charAt(i + patternLength),patternLength);
            }
        }
    }
    public static void main(String[] args) {
        search("ApprovKunalRahul", "Rahul");
    }
}
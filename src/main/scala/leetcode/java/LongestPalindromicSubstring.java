package leetcode.java;

/**
 * Given a string s, return the longest palindromic substring in s.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "babad"
 * Output: "bab"
 * Note: "aba" is also a valid answer.
 * Example 2:
 *
 * Input: s = "cbbd"
 * Output: "bb"
 * Example 3:
 *
 * Input: s = "a"
 * Output: "a"
 * Example 4:
 *
 * Input: s = "ac"
 * Output: "a"
 *
 *
 * Constraints:
 *
 * 1 <= s.length <= 1000
 * s consist of only digits and English letters (lower-case and/or upper-case),
 */
public class LongestPalindromicSubstring {
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) return "";
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    private int expandAroundCenter(String s, int left, int right) {
        int L = left, R = right;
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--;
            R++;
        }
        return R - L - 1;
    }

    public String longestPalindromeMine(String s) {
        if(s == null || s.isEmpty())
            return null;

        char[] chars = s.toCharArray();
        int longestLength = 1;
        int[] longestPalindromeIndices = {0,0};

        //check for even length
        for(int i = 0; i < s.length()-1; i++) {
            if(chars[i] == chars[i+1]) {
                int[] indices = extendAndGetPalindrome(chars,i,i+1);
                if((indices[1] - indices[0] + 1) > longestLength) {
                    longestPalindromeIndices = indices;
                    longestLength = indices[1] - indices[0] + 1;
                }
            }
        }

        if(longestLength == s.length()){
            return s;
        }

        //check for odd length
        for(int i = 0; i < s.length()-2; i++) {
            if(chars[i] == chars[i+2]) {
                int[] indices = extendAndGetPalindrome(chars,i,i+2);
                if((indices[1] - indices[0] + 1) > longestLength) {
                    longestPalindromeIndices = indices;
                    longestLength = indices[1] - indices[0] + 1;
                }
            }
        }

        if(longestLength == s.length()){
            return s;
        }

        return s.substring(longestPalindromeIndices[0],longestPalindromeIndices[1]+1);
    }

    private int[] extendAndGetPalindrome(char[] s, int startIdx, int endIdx){
        int[] indices = new int[2];
        if(startIdx < 0 || startIdx >= s.length || endIdx < 0 || endIdx >= s.length){
            indices[0] = indices[1] = -1;
            return indices;
        }

        while(startIdx >= 0 && endIdx < s.length && s[startIdx] == s[endIdx]){
            indices[0] = startIdx;
            indices[1] = endIdx;
            startIdx--;
            endIdx++;
        }
        return indices;
    }
}

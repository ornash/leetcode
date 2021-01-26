package leetcode.java;

/**
 * Given a string s, find the length of the longest substring without repeating characters.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "abcabcbb"
 * Output: 3
 * Explanation: The answer is "abc", with the length of 3.
 * Example 2:
 *
 * Input: s = "bbbbb"
 * Output: 1
 * Explanation: The answer is "b", with the length of 1.
 * Example 3:
 *
 * Input: s = "pwwkew"
 * Output: 3
 * Explanation: The answer is "wke", with the length of 3.
 * Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
 * Example 4:
 *
 * Input: s = ""
 * Output: 0
 *
 *
 * Constraints:
 *
 * 0 <= s.length <= 5 * 104
 * s consists of English letters, digits, symbols and spaces.
 */
public class LongestSubstringWithoutRepeatingChars {
    //https://leetcode.com/articles/longest-substring-without-repeating-characters/
    public int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        int[] index = new int[256]; // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            i = Math.max(index[s.charAt(j)], i);
            ans = Math.max(ans, j - i + 1);
            index[s.charAt(j)] = j + 1;
        }
        return ans;
    }
/*    public int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>(); // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);
            }
            ans = Math.max(ans, j - i + 1);
            map.put(s.charAt(j), j + 1);
        }
        return ans;
    }*/
/*
//most easy to understand
public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        Set<Character> set = new HashSet<>();
        int ans = 0, i = 0, j = 0;
        while (i < n && j < n) {
            // try to extend the range [i, j]
            if (!set.contains(s.charAt(j))){
                set.add(s.charAt(j++));
                ans = Math.max(ans, j - i);
            }
            else {
                set.remove(s.charAt(i++));
            }
        }
        return ans;
    }*/

    //My code
/*    public int lengthOfLongestSubstring(String s) {
        int start = 0;
        int longestStart = 0;
        int length = 0;
        int longestLength = -1;

        Map<Character,Integer> charMap = new HashMap<>();

        for(int i = 0; i < s.length(); i++) {
            if(!charMap.containsKey(s.charAt(i))) {
                charMap.put(s.charAt(i), i);
                length++;
            } else {
                if(length > longestLength) {
                    longestStart = start;
                    longestLength = length;
                }

                int lastIdx = charMap.get(s.charAt(i));
                int oldStart = start;
                start = lastIdx + 1;
                length = i - start + 1;

                Set<Character> keySet = charMap.keySet();

                for(int j = oldStart; j < start; j++) {
                    keySet.remove(s.charAt(j));
                }
                charMap.put(s.charAt(i), i);
            }
        }

        return length > longestLength ? length : longestLength;
    }*/

    /*public int lengthOfLongestSubstring(String s) {
        int start = 0;
        int longestStart = 0;
        int length = 0;
        int longestLength = 0;

        s = s.toLowerCase();

        int[] charMap = new int[26];
        Arrays.fill(charMap, -1);

        for(int i = 0; i < s.length(); i++) {
            int lastIdx = charMap[s.charAt(i) - 'a'];
            if(lastIdx == -1) {
                charMap[s.charAt(i) - 'a'] = i;
                length++;
            } else {
                if(length > longestLength) {
                    longestStart = start;
                    longestLength = length;
                }

                start = lastIdx + 1;
                length = i - start + 1;
                charMap[s.charAt(i) - 'a'] = i;
            }
        }

        return longestLength;
    }*/
}

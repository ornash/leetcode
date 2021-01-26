package leetcode.java;

import java.util.*;

/**
 * Given a pattern and a string s, return true if s matches the pattern.
 *
 * A string s matches a pattern if there is some bijective mapping of single characters to strings such that if each character in pattern is replaced by the string it maps to, then the resulting string is s. A bijective mapping means that no two characters map to the same string, and no character maps to two different strings.
 *
 *
 *
 * Example 1:
 *
 * Input: pattern = "abab", s = "redblueredblue"
 * Output: true
 * Explanation: One possible mapping is as follows:
 * 'a' -> "red"
 * 'b' -> "blue"
 * Example 2:
 *
 * Input: pattern = "aaaa", s = "asdasdasdasd"
 * Output: true
 * Explanation: One possible mapping is as follows:
 * 'a' -> "asd"
 * Example 3:
 *
 * Input: pattern = "abab", s = "asdasdasdasd"
 * Output: true
 * Explanation: One possible mapping is as follows:
 * 'a' -> "a"
 * 'b' -> "sdasd"
 * Note that 'a' and 'b' cannot both map to "asd" since the mapping is a bijection.
 * Example 4:
 *
 * Input: pattern = "aabb", s = "xyzabcxzyabc"
 * Output: false
 *
 *
 * Constraints:
 *
 * 0 <= pattern.length <= 20
 * 0 <= s.length <= 50
 * pattern and s consist of only lower-case English letters.
 */
public class WordPatter2 {
    public boolean wordPatternMatchOld(String pattern, String str) {
        if(pattern == null || str == null || (pattern.isEmpty() && !str.isEmpty())){
            return false;
        }

        if(pattern.length() > str.length()){
            return false;
        }

        if(pattern.isEmpty() && str.isEmpty()) {
            return true;
        }

        Map<String, String> patternMap =  new HashMap<String,String>();
        return discoverPattern(pattern, str, patternMap, new HashSet<String>());
    }

    private boolean discoverPattern(String pattern, String str, Map<String,String> patternMap, Set<String> words) {

        //Both should be empty or both should be non-empty
        if(pattern.isEmpty()) return str.isEmpty();
        if(str.isEmpty()) return pattern.isEmpty();

        String firstChar = pattern.substring(0,1);

        if(patternMap.containsKey(firstChar)) {
            String lastStrMatch = patternMap.get(firstChar);
            //Str must start with last matching pattern.
            if(str.startsWith(lastStrMatch)) {
                return
                    discoverPattern(pattern.substring(1), str.substring(lastStrMatch.length()), patternMap, words);
            } else {
                return false;
            }
        } else {
            for(int i = 1; i <= str.length(); i++) {
                String newWord = str.substring(0, i);
                if(words.contains(newWord)) {
                    continue;
                }

                patternMap.put(firstChar, newWord);
                words.add(newWord);
                boolean discovered =
                    discoverPattern(pattern.substring(1), str.substring(i), patternMap, words);
                if(!discovered) {
                    //attemp failed
                    patternMap.remove(firstChar);
                    words.remove(newWord);
                } else {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean wordPatternMatch(String pattern, String str) {
        if(pattern == null || str == null || (pattern.isEmpty() && !str.isEmpty())){
            return false;
        }

        if(pattern.length() > str.length()){
            return false;
        }

        if(pattern.isEmpty() && str.isEmpty()) {
            return true;
        }

        char[] chars = pattern.toCharArray();
        String[] charToWord = new String[26];

        return findPattern(chars, 0, str, charToWord, new HashSet<String>());
    }

    private boolean findPattern(char[] chars, int startAt, String suffix, String[] charToWord, Set<String> words) {
        if(startAt == chars.length - 1) { //if this is the last char in pattern
            String word = charToWord[chars[startAt] - 'a'];
            //suffix must match previous mapping or be empty.
            if(word != null && !word.equals(suffix)) {
                return false;
            }
            charToWord[chars[startAt] - 'a'] = suffix;
            return true;
        }

        String word = charToWord[chars[startAt] - 'a'];
        if(word != null) {
            //if a mpping exists, it must match prefix and must be shorter.
            if(suffix.length() - word.length() <= 0 || !suffix.startsWith(word)) {
                return false;
            }

            return findPattern(chars, startAt+1, suffix.substring(word.length()), charToWord, words);
        } else {
            //if mapping doesnt exist, check each prefix and suffix.
            for(int prefixLen = 1; prefixLen < suffix.length(); prefixLen++) {
                String prefix = suffix.substring(0,prefixLen);
                if(words.contains(prefix)) {
                    continue;
                }

                charToWord[chars[startAt] - 'a'] = prefix;
                words.add(prefix);
                boolean found = findPattern(chars, startAt+1, suffix.substring(prefix.length()), charToWord, words);
                if(!found) {
                    charToWord[chars[startAt] - 'a'] = null; //revert mapping
                    words.remove(prefix);
                } else
                    return true;
            }
        }
        return false;
    }

    /*
    //Divide and conquer binary search
    //does not handle
    "aba"
    "aaaa"
    i.e. when each matched word in str does not have same length
    public boolean wordPatternMatch(String pattern, String str) {
        if(pattern == null || str == null || (pattern.isEmpty() && !str.isEmpty())){
            return false;
        }

        if(pattern.length() > str.length()){
            return false;
        }

        if(pattern.isEmpty() && str.isEmpty()) {
            return true;
        }

        return discoverPattern(pattern, str, new HashMap<String,String>(), new HashMap<String,String>());
    }

    private boolean discoverPattern(String pattern, String str, Map<String,String> patternMap,
        Map<String,String> reverseMap) {
        if(pattern.length() == 1) {
            String expectedStr = patternMap.get(pattern);
            String expectedPattern = reverseMap.get(str);
            if(expectedStr == null && expectedPattern == null) {
                patternMap.put(pattern, str);
                reverseMap.put(str, pattern);
                return true;
            } else if(expectedStr == null || expectedPattern == null) {
                return false;
            }

            return expectedStr.equals(str) && expectedPattern.equals(pattern);
        }

        int mid = pattern.length() / 2;
        String leftPattern = pattern.substring(0, mid);
        String rightPattern = pattern.substring(mid);

        mid = str.length() / 2;
        String leftStr = str.substring(0, mid);
        String rightStr = str.substring(mid);

        return discoverPattern(leftPattern, leftStr, patternMap, reverseMap)
        && discoverPattern(rightPattern, rightStr, patternMap, reverseMap);
    }*/

}

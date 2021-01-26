package leetcode.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A message containing letters from A-Z can be encoded into numbers using the following mapping:
 *
 * 'A' -> "1"
 * 'B' -> "2"
 * ...
 * 'Z' -> "26"
 * To decode an encoded message, all the digits must be mapped back into letters using the reverse of the mapping above (there may be multiple ways). For example, "111" can have each of its "1"s be mapped into 'A's to make "AAA", or it could be mapped to "11" and "1" ('K' and 'A' respectively) to make "KA". Note that "06" cannot be mapped into 'F' since "6" is different from "06".
 *
 * Given a non-empty string num containing only digits, return the number of ways to decode it.
 *
 * The answer is guaranteed to fit in a 32-bit integer.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "12"
 * Output: 2
 * Explanation: "12" could be decoded as "AB" (1 2) or "L" (12).
 * Example 2:
 *
 * Input: s = "226"
 * Output: 3
 * Explanation: "226" could be decoded as "BZ" (2 26), "VF" (22 6), or "BBF" (2 2 6).
 * Example 3:
 *
 * Input: s = "0"
 * Output: 0
 * Explanation: There is no character that is mapped to a number starting with 0. The only valid mappings with 0 are 'J' -> "10" and 'T' -> "20".
 * Since there is no character, there are no valid ways to decode this since all digits need to be mapped.
 * Example 4:
 *
 * Input: s = "1"
 * Output: 1
 *
 *
 * Constraints:
 *
 * 1 <= s.length <= 100
 * s contains only digits and may contain leading zero(s).
 */
public class DecodeWays {

        //visualize the problem as a binary tree
        //we are just counting the total number of paths from root to leaf
        //which is given as root.#paths = right.#paths + left.#paths
        //and then handle a lot of special cases
        public int numDecodings(String s) {
            if(s.length() == 0)
                return 0;

            for(int j = 0; j < s.length(); j++) {
                if(s.charAt(j) < '0' || s.charAt(j) > '9')
                    return 0;
                if(j > 0 && s.charAt(j) == '0'
                    && !(s.charAt(j-1) == '1' || s.charAt(j-1) == '2'))
                    return 0;
            }

            if(s.length() == 1)
                return s.charAt(0) == '0' ? 0 : 1;

            if(s.startsWith("0") || s.endsWith("00"))
                return  0;

            if(s.endsWith("0") && !(s.endsWith("20") || s.endsWith("10")))
                return 0;

            int[] counts = new int[s.length()];
            char[] encoding = new char[27];

            encoding[0] = '$';
            int i = 1;
            for(Character c : "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()) {
                encoding[i++] = c;
            }

            counts[0] = s.charAt(0) == '0' ? 0 : 1;
            int index = Integer.valueOf(s.substring(0,2));
            if(index == 10 || index == 20) {
                counts[1] = 1;
            }else if(index > 0 && index <= encoding.length -1) {
                counts[1] = counts[0] + 1;
            } else {
                counts[1] = counts[0];
            }

            for(i = 2; i < s.length(); i++) {
                if(s.charAt(i-1) == '0') {
                    counts[i] = counts[i-1];
                    continue;
                }

                index = Integer.valueOf(s.substring(i-1, i+1));
                //root.#paths = right.#paths + left.#paths
                if(index == 10 || index == 20) {
                    counts[i] = counts[i-2];
                }else if(index <= encoding.length -1) {
                    counts[i] = counts[i-1] + counts[i-2];
                } else {
                    counts[i] = counts[i-1];
                }
            }

            return counts[s.length() -1];
        }


        public int numDecodingsDPLeetCode(String s) {

            if(s == null || s.length() == 0) {
                return 0;
            }

            // DP array to store the subproblem results
            int[] dp = new int[s.length() + 1];
            dp[0] = 1;
            // Ways to decode a string of size 1 is 1. Unless the string is '0'.
            // '0' doesn't have a single digit decode.
            dp[1] = s.charAt(0) == '0' ? 0 : 1;

            for(int i = 2; i < dp.length; i += 1) {

                // Check if successful single digit decode is possible.
                if(s.charAt(i-1) != '0') {
                    dp[i] += dp[i-1];
                }

                // Check if successful two digit decode is possible.
                int twoDigit = Integer.valueOf(s.substring(i-2, i));
                if(twoDigit >= 10 && twoDigit <= 26) {
                    dp[i] += dp[i-2];
                }
            }
            return dp[s.length()];

        }

        HashMap<Integer, Integer> memo = new HashMap<>();

        private int recursiveWithMemo(int index, String str) {

            // If you reach the end of the string
            // Return 1 for success.
            if (index == str.length()) {
                return 1;
            }

            // If the string starts with a zero, it can't be decoded
            if (str.charAt(index) == '0') {
                return 0;
            }

            if (index == str.length()-1) {
                return 1;
            }

            // Memoization is needed since we might encounter the same sub-string.
            if (memo.containsKey(index)) {
                return memo.get(index);
            }

            int ans = recursiveWithMemo(index+1, str);
            if (Integer.parseInt(str.substring(index, index+2)) <= 26) {
                ans += recursiveWithMemo(index+2, str);
            }

            // Save for memoization
            memo.put(index, ans);

            return ans;
        }

        public int numDecodingsMemoLeetCode(String s) {
            if (s == null || s.length() == 0) {
                return 0;
            }
            return recursiveWithMemo(0, s);
        }

        public int numDecodingsNewFails(String s) {
            char[] chars = s.toCharArray();

            Map<String, List<char[]>> decodings = new HashMap<>();
            Map<String, List<char[]>> inputMap = new HashMap<>();

            for (int i = 0; i < 26; i++) {
                char[] decoding = new char[1];
                decoding[0] = (char) ('a' + i);

                List<char[]> newDecodings = new ArrayList<>();
                newDecodings.add(decoding);

                inputMap.put(String.valueOf(i + 1), newDecodings);
            }

            decodingHelper(inputMap, chars, 0, decodings);

            if(decodings.get(s) != null) {
                return decodings.get(s).size();
            }
            return 0;
        }

        public void decodingHelper(Map<String, List<char[]>> inputMap, char[] chars, int startIdx, Map<String, List<char[]>> decodings) {
            if(startIdx >= chars.length ) {
                return;
            }

            String suffix = new String(chars, startIdx, chars.length - startIdx);
            if (decodings.get(suffix) != null) {
                return;
            }

            decodingHelper(inputMap, chars, startIdx + 1, decodings);
            decodingHelper(inputMap, chars, startIdx + 2, decodings);

            List<char[]> newDecodings = decodings.getOrDefault(suffix, new ArrayList<>());
            if (newDecodings.isEmpty()) {
                newDecodings = inputMap.getOrDefault(suffix, new ArrayList<>());
            }
            decodings.put(suffix, newDecodings);

            if(chars.length - startIdx < 2) {
                return;
            }
            String singleDigitPrefix = new String(chars, startIdx, 1);
            List<char[]> decodingsForPrefix = decodings.getOrDefault(singleDigitPrefix,  Collections.emptyList());
            if (decodingsForPrefix.isEmpty()) {
                decodingsForPrefix = inputMap.getOrDefault(singleDigitPrefix, Collections.emptyList());
            }

            String tempSuffix = new String(chars, startIdx + 1, chars.length - (startIdx + 1));
            List<char[]> decodingsForSuffix = decodings.getOrDefault(tempSuffix, Collections.emptyList());
            if (decodingsForSuffix.isEmpty()) {
                decodingsForSuffix = inputMap.getOrDefault(tempSuffix, Collections.emptyList());
            }

            for(char[] prefixDecoding : decodingsForPrefix) {
                for(char[] suffixDecoding : decodingsForSuffix) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(prefixDecoding).append(suffixDecoding);
                    newDecodings.add(builder.toString().toCharArray());
                }
            }

            if(chars.length - startIdx < 3) {
                return;
            }
            String doubleDigitPrefix = new String(chars, startIdx, 2);
            decodingsForPrefix = decodings.getOrDefault(doubleDigitPrefix,  Collections.emptyList());
            if (decodingsForPrefix.isEmpty()) {
                decodingsForPrefix = inputMap.getOrDefault(doubleDigitPrefix, Collections.emptyList());
            }

            tempSuffix = new String(chars, startIdx + 2, chars.length - (startIdx + 2));
            decodingsForSuffix = decodings.getOrDefault(tempSuffix, Collections.emptyList());
            if (decodingsForSuffix.isEmpty()) {
                decodingsForSuffix = inputMap.getOrDefault(tempSuffix, Collections.emptyList());
            }

            for(char[] prefixDecoding : decodingsForPrefix) {
                for(char[] suffixDecoding : decodingsForSuffix) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(prefixDecoding).append(suffixDecoding);
                    newDecodings.add(builder.toString().toCharArray());
                }
            }
        }
}

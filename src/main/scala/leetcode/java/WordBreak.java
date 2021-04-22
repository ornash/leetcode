package leetcode.java;

import java.util.*;

/**
 * Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, determine if s can be segmented into a space-separated sequence of one or more dictionary words.
 *
 * Note:
 *
 * The same word in the dictionary may be reused multiple times in the segmentation.
 * You may assume the dictionary does not contain duplicate words.
 * Example 1:
 *
 * Input: s = "leetcode", wordDict = ["leet", "code"]
 * Output: true
 * Explanation: Return true because "leetcode" can be segmented as "leet code".
 *
 * Example 2:
 *
 * Input: s = "applepenapple", wordDict = ["apple", "pen"]
 * Output: true
 * Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
 *              Note that you are allowed to reuse a dictionary word.
 * Example 3:
 *
 * Input: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
 * Output: false
 */
public class WordBreak {
    private Map<String, Boolean> dp = new HashMap<String, Boolean>();

/*    public boolean wordBreak(String s, Set<String> wordDict) {
        if(s == null)
            return false;
        if(s.length() == 0)
            return false;
        return wordBreakHelp(s, wordDict);
    }

    private boolean wordBreakHelp(String s, Set<String> wordDict) {
       if(wordDict.contains(s))
            return true;

       for(int i = 0; i < s.length(); i++) {
            if(wordDict.contains(s.substring(0, i+1))) {
                if(wordBreakHelp(s.substring(i+1), wordDict)) {
                    return true;
                }
            }
        }
        return false;
    }*/

/*    public boolean wordBreak(String s, Set<String> wordDict) {
        if(s == null)
            return false;
        if(s.length() == 0)
            return false;
        return wordBreakHelp(s, 0, 1, wordDict);
    }

    private boolean wordBreakHelp(String s, int start, int cutAt, Set<String> wordDict) {
    	//System.out.println("\n" + start + " " + cutAt);

    	String leftOver = s.substring(start);
    	if(leftOver.length() == 0 ) {
    		return true;
    	}

    	//System.out.println(leftOver);
    	Boolean oldResult = dp.get(leftOver);
    	if(oldResult != null) {
    		return oldResult;
    	}

    	String prefix = s.substring(start, cutAt);
    	//System.out.println(prefix);

    	if(wordDict.contains(leftOver)) {
    		dp.put(leftOver, true);
    		return true;
    	} else if(wordDict.contains(prefix) && cutAt < s.length()
    			&& wordBreakHelp(s, cutAt, cutAt+1, wordDict)) {
    		dp.put(prefix, true);
    		String suffix = s.substring(cutAt);
    		//System.out.println(suffix);
    		dp.put(suffix, true);
    		dp.put(leftOver, true);
    		return true;
    	} else if(cutAt < s.length() && wordBreakHelp(s, start, cutAt+1, wordDict)) {
    		dp.put(leftOver, true);
    		return true;
    	}

    	dp.put(leftOver, false);
        return false;
    }*/

    public boolean wordBreak(String s, Set<String> wordDict) {
        if(s == null)
            return false;
        if(s.length() == 0)
            return false;

        boolean[] f = new boolean[s.length() + 1];
        f[0] = true;

        for(int i=1; i <= s.length(); i++){
            for(int j=0; j < i; j++){
                if(f[j] && wordDict.contains(s.substring(j, i))){
                    f[i] = true;
                    break;
                }
            }
        }

        return f[s.length()];
    }
}

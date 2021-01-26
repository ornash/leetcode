package leetcode.java;

import java.util.*;

/**
 * A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence of words such that:
 *
 * The first word in the sequence is beginWord.
 * The last word in the sequence is endWord.
 * Only one letter is different between each adjacent pair of words in the sequence.
 * Every word in the sequence is in wordList.
 * Given two words, beginWord and endWord, and a dictionary wordList, return the number of words in the shortest transformation sequence from beginWord to endWord, or 0 if no such sequence exists.
 *
 *
 *
 * Example 1:
 *
 * Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
 * Output: 5
 * Explanation: One shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog" with 5 words.
 * Example 2:
 *
 * Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
 * Output: 0
 * Explanation: The endWord "cog" is not in wordList, therefore there is no possible transformation.
 *
 *
 * Constraints:
 *
 * 1 <= beginWord.length <= 10
 * endWord.length == beginWord.length
 * 1 <= wordList.length <= 5000
 * wordList[i].length == beginWord.length
 * beginWord, endWord, and wordList[i] consist of lowercase English letters.
 * beginWord != endWord
 * All the strings in wordList are unique.
 */
public class WordLadder {

    public int ladderLength(String beginWord, String endWord, Set<String> wordList) {
        Set<String> set1 = new HashSet<String>();
        set1.add(beginWord);

        Set<String> set2 = new HashSet<String>();
        set2.add(endWord);

        wordList.remove(beginWord);
        wordList.remove(endWord);

        return minLengthBidirectionalSearch(set1, set2, wordList, 2);
    }

    public int minLengthBidirectionalSearch(Set<String> set1, Set<String> set2, Set<String> wordList, int length) {
        if(set1.size() == 0) return 0;

        Set<String> newSet = new HashSet<String>();
        Map<String, String> ff = new HashMap<>();


        for(String s : set1) {
            char[] str = s.toCharArray();
            for(int j = 0; j < str.length; j++) {
                char og = str[j];
                for(char c = 'a'; c <= 'z'; c++) {
                    str[j] = c;
                    String newStr = String.valueOf(str);
                    if(set2.contains(newStr)) return length;
                    if(wordList.contains(newStr)) {
                        newSet.add(newStr);
                        wordList.remove(newStr);
                    }
                }
                str[j] = og;
            }
        }

        // This part is KEY to bringing your run-time down. Otherwise sets with more neighbours
        // will skew the benefit that can be obtained from searching outward from two nodes.
        if(newSet.size() < set2.size()) {
            return minLengthBidirectionalSearch(newSet, set2, wordList, length+1);
        } else {
            return minLengthBidirectionalSearch(set2, newSet, wordList, length+1);
        }
    }
/*    public int ladderLength(String beginWord, String endWord, Set<String> wordDict) {
    Set<String> reached = new HashSet<String>();
    reached.add(beginWord);
    wordDict.add(endWord);
	int distance = 1;
	while(!reached.contains(endWord)) {
		Set<String> toAdd = new HashSet<String>();
		for(String each : reached) {
			for (int i = 0; i < each.length(); i++) {
                char[] chars = each.toCharArray();
                for (char ch = 'a'; ch <= 'z'; ch++) {
                	chars[i] = ch;
                    String word = new String(chars);
                    if(wordDict.contains(word)) {
                    	toAdd.add(word);
                    	wordDict.remove(word);
                    }
                }
			}
		}
		distance ++;
		if(toAdd.size() == 0) return 0;
		reached = toAdd;
	}
	return distance;
}*/
/*	    public int ladderLength(String beginWord, String endWord, Set<String> wordList) {
	        if(beginWord.length() == 0 || endWord.length() == 0) {
	            return 0;
	        }

	        return findLadderLength(beginWord, endWord, wordList, 1);
	    }

	    private static int findLadderLength(String currentWord, String endWord, Set<String> wordList, int length) {
	        int changeDist = getChangeDistance(currentWord, endWord);
	        if(changeDist == 0)
	            return length;
	        if(changeDist == 1)
	            return length + 1;
	        if(wordList.size() == 0)
	            return 0;

	        Set<String> nearestWords = getWordsWithinOneChange(currentWord, wordList);
	        Set<String> sortedWords = new TreeSet<>(new Comparator<String> () {
	                @Override
				    public int compare(String arg0, String arg1) {
					    return getChangeDistance(endWord, arg0) - getChangeDistance(endWord, arg1);
				    }
	            });
	        sortedWords.addAll(nearestWords);
	        int shortestLength = 0;
	        wordList.remove(currentWord);
	        for(String neighbor : sortedWords) {
	            int newLength = findLadderLength(neighbor, endWord, wordList, length + 1);
	            if(newLength != 0) {
	                return newLength;
	            }
	        }
	        wordList.add(currentWord);
	        return shortestLength;
	    }

	    private static Set<String> getWordsWithinOneChange(String word, Set<String> wordList) {
	        Set<String> wordsWithinOneChange = new HashSet<>();
	        for(String dictWord : wordList) {
	            if(getChangeDistance(word, dictWord) == 1) {
	                wordsWithinOneChange.add(dictWord);
	            }
	        }
	        return wordsWithinOneChange;
	    }

	    private static int getChangeDistance(String begin, String end) {
	        int dist = 0;
	        for(int i = 0; i < begin.length(); i++) {
	            if(begin.charAt(i) != end.charAt(i))
	                dist++;
	        }
	        return dist;
	    }*/
/*    public int ladderLength(String beginWord, String endWord, Set<String> wordList) {
        if(beginWord.length() == 0 || endWord.length() == 0) {
            return 0;
        }

        return findLadderLength(beginWord, endWord, wordList, 1);
    }

    private static int findLadderLength(String currentWord, String endWord, Set<String> wordList, int length) {
        int changeDist = getChangeDistance(currentWord, endWord);
        if(changeDist == 0)
            return length;
        if(changeDist == 1)
            return length + 1;
        if(wordList.size() == 0)
            return 0;

        Set<String> nearestWords = getWordsWithinOneChange(currentWord, wordList);
        int shortestLength = 0;
        wordList.remove(currentWord);
        for(String neighbor : nearestWords) {
            int newLength = findLadderLength(neighbor, endWord, wordList, length + 1);
            if(newLength > 0) {
                shortestLength = shortestLength == 0 || newLength < shortestLength ? newLength : shortestLength;
            }
        }
        wordList.add(currentWord);
        return shortestLength;
    }

    private static Set<String> getWordsWithinOneChange(String word, Set<String> wordList) {
        Set<String> wordsWithinOneChange = new HashSet<>();
        for(String dictWord : wordList) {
            if(getChangeDistance(word, dictWord) == 1) {
                wordsWithinOneChange.add(dictWord);
            }
        }
        return wordsWithinOneChange;
    }

    private static int getChangeDistance(String begin, String end) {
        int dist = 0;
        for(int i = 0; i < begin.length(); i++) {
            if(begin.charAt(i) != end.charAt(i))
                dist++;
        }
        return dist;
    }*/
}

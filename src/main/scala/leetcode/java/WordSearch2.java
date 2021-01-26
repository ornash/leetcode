package leetcode.java;

import java.util.*;

/**
 * Given an m x n board of characters and a list of strings words, return all words on the board.
 *
 * Each word must be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: board = [["o","a","a","n"],["e","t","a","e"],["i","h","k","r"],["i","f","l","v"]], words = ["oath","pea","eat","rain"]
 * Output: ["eat","oath"]
 * Example 2:
 *
 *
 * Input: board = [["a","b"],["c","d"]], words = ["abcb"]
 * Output: []
 *
 *
 * Constraints:
 *
 * m == board.length
 * n == board[i].length
 * 1 <= m, n <= 12
 * board[i][j] is a lowercase English letter.
 * 1 <= words.length <= 3 * 104
 * 1 <= words[i].length <= 10
 * words[i] consists of lowercase English letters.
 * All the strings of words are unique.
 */
public class WordSearch2 {
    public List<String> findWordsBest(char[][] board, String[] words) {
        List<String> res = new ArrayList<>();
        TrieNode root = buildTrie(words);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs (board, i, j, root, res);
            }
        }
        return res;
    }

    public void dfs(char[][] board, int i, int j, TrieNode p, List<String> res) {
        char c = board[i][j];
        if (c == '#' || p.next[c - 'a'] == null) return;
        p = p.next[c - 'a'];
        if (p.word != null) {   // found one
            res.add(p.word);
            p.word = null;     // de-duplicate
        }

        board[i][j] = '#';
        if (i > 0) dfs(board, i - 1, j ,p, res);
        if (j > 0) dfs(board, i, j - 1, p, res);
        if (i < board.length - 1) dfs(board, i + 1, j, p, res);
        if (j < board[0].length - 1) dfs(board, i, j + 1, p, res);
        board[i][j] = c;
    }

    public TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        for (String w : words) {
            TrieNode p = root;
            for (char c : w.toCharArray()) {
                int i = c - 'a';
                if (p.next[i] == null) p.next[i] = new TrieNode();
                p = p.next[i];
            }
            p.word = w;
        }
        return root;
    }

    class TrieNode {
        TrieNode[] next = new TrieNode[26];
        String word;
    }

    public List<String> findWords(char[][] board, String[] words) {
        Map<Character, Set<String>> wordsByFirstChar = new HashMap<>();

        for(String word : words) {
            Set<String> sameFirstCharWords = wordsByFirstChar.getOrDefault(word.charAt(0), new HashSet<>());
            sameFirstCharWords.add(word);
            wordsByFirstChar.put(word.charAt(0), sameFirstCharWords);
        }

        List<String> result = new ArrayList<>();

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                Set<String> sameFirstCharWords = (Set<String>) wordsByFirstChar.getOrDefault(board[i][j], Collections.emptySet());
                Set<String> removeWords = new HashSet<>();
                for(String searchWord : sameFirstCharWords) {
                    if(dfs(board,i,j,searchWord.toCharArray(),0)) {
                        result.add(searchWord);
                        removeWords.add(searchWord);
                    }
                }
                sameFirstCharWords.removeAll(removeWords);
            }
        }

        return result;
    }

    private boolean dfs(char[][] board, int i, int j, char[] searchWord, int sIdx) {
        if(sIdx >= searchWord.length)
            return false;
        if(i >= board.length || i < 0)
            return false;
        if(j >= board[0].length || j < 0)
            return false;

        if(board[i][j] != searchWord[sIdx]){
            return false;
        }

        if (searchWord.length - 1 == sIdx) {
            return true;
        }

        board[i][j] = '#';
        boolean found = dfs(board,i+1,j,searchWord,sIdx+1)
            || dfs(board,i-1,j,searchWord,sIdx+1)
            || dfs(board,i,j+1,searchWord,sIdx+1)
            || dfs(board,i,j-1,searchWord,sIdx+1);
        board[i][j] = searchWord[sIdx];
        return found;
    }

    /* takes too long
    public List<String> findWords(char[][] board, String[] words) {
        List<String> found = new ArrayList<>();
        Set<String> dict = new HashSet<>();

        for(String word : words) {
            dict.add(word);
        }
        for(String word : dict) {
            if(exist(board, word)){
                found.add(word);
            }
        }

        return found;
    }

    public boolean exist(char[][] board, String word) {
        if(board == null || word == null || word.isEmpty())
            return false;

        char[] letters = word.toCharArray();
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if(letters[0] == board[i][j]
                    && dfs(board, i, j, letters, 0)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean dfs(char[][] board, int row, int col, char[] letters, int nextIdx) {
        if(nextIdx >= letters.length)
            return true;
        if(row < 0 || row >= board.length || col < 0 || col >= board[0].length)
            return false;
        if(board[row][col] == '#' || board[row][col] != letters[nextIdx])
            return false;

        board[row][col] = '#';
        boolean result = dfs(board, row+1, col, letters, nextIdx+1)
                        || dfs(board, row-1, col, letters, nextIdx+1)
                        || dfs(board, row, col+1, letters, nextIdx+1)
                        || dfs(board, row, col-1, letters, nextIdx+1);
        board[row][col] = letters[nextIdx];
        return result;
    }*/
}

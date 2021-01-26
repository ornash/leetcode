package leetcode.java;

/**
 * Given an m x n board and a word, find if the word exists in the grid.
 *
 * The word can be constructed from letters of sequentially adjacent cells, where "adjacent" cells are horizontally or vertically neighboring. The same letter cell may not be used more than once.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
 * Output: true
 * Example 2:
 *
 *
 * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
 * Output: true
 * Example 3:
 *
 *
 * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"
 * Output: false
 *
 *
 * Constraints:
 *
 * m == board.length
 * n = board[i].length
 * 1 <= m, n <= 200
 * 1 <= word.length <= 103
 * board and word consists only of lowercase and uppercase English letters.
 */
public class WordSearch {
    public boolean existOld(char[][] board, String word) {
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
    }

    public boolean exist(char[][] board, String word) {
        if(word == null || word.isEmpty())
            return false;

        char[] letters = word.toCharArray();
        for(int row = 0; row < board.length; row++) {
            for(int col = 0; col < board[0].length; col++) {
                if(letters[0] == board[row][col] && recurse(board, letters, 0, row, col)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean recurse(char[][] board, char[] letters, int idx, int row, int col) {
        if(idx >= letters.length)
            return true;
        if(row < 0 || col < 0 || row >= board.length || col >= board[0].length)
            return false;
        if('#' == board[row][col] || letters[idx] != board[row][col])
            return false;

        //mark visited
        board[row][col] = '#';

        boolean result = recurse(board, letters, idx + 1 , row, col +1) ||
            recurse(board, letters, idx + 1, row+1,col) ||
            recurse(board, letters, idx + 1, row, col-1) ||
            recurse(board, letters, idx + 1, row-1, col);

        //restore
        board[row][col] = letters[idx];
        return result;
    }
}

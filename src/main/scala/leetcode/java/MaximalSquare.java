package leetcode.java;

public class MaximalSquare {
    public int maximalSquareBest(char[][] matrix) {
        if(matrix == null)
            return 0;
        if(matrix.length == 0 || matrix[0].length == 0)
            return 0;

        int[][] dp = new int[matrix.length][matrix[0].length];

        int maxSide = 0;
        for(int i = 0; i < matrix[0].length; i++) {
            dp[0][i] = matrix[0][i] - '0';
            maxSide = dp[0][i] > maxSide ? dp[0][i] : maxSide;
        }

        for(int i = 1; i < matrix.length; i++) {
            dp[i][0] = matrix[i][0] - '0';
            maxSide = dp[i][0] > maxSide ? dp[i][0] : maxSide;
        }

        for(int i = 1; i < matrix.length; i++) {
            for(int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][j] == '1') {
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
                    maxSide = Math.max(maxSide, dp[i][j]);
                }
            }
        }
        return maxSide * maxSide;
    }

    public int maximalSquareEasyToUnderstand(char[][] matrix) {
        int rows = matrix.length, cols = rows > 0 ? matrix[0].length : 0;
        int[][] dp = new int[rows + 1][cols + 1];
        int maxsqlen = 0;
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                if (matrix[i-1][j-1] == '1'){
                    dp[i][j] = Math.min(Math.min(dp[i][j - 1], dp[i - 1][j]), dp[i - 1][j - 1]) + 1;
                    maxsqlen = Math.max(maxsqlen, dp[i][j]);
                }
            }
        }
        return maxsqlen * maxsqlen;
    }

    public int maximalSquare(char[][] matrix) {
        if(matrix == null)
            return 0;
        if(matrix.length == 0 || matrix[0].length == 0)
            return 0;

        int max = 0;

        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                int maxPossibleLen = Math.min(matrix.length-i, matrix[0].length-j);
                for(int len = maxPossibleLen; len > max; len--) {
                    if(isSquare(matrix,i,j,len)){
                        max = Math.max(max, len);
                        break;
                    }
                }
            }
        }

        return max*max;
    }

    private boolean isSquare(char[][] matrix, int startRow, int startCol, int len) {
        if(startRow + len > matrix.length) {
            return false;
        }
        if(startCol + len > matrix[0].length){
            return false;
        }

        for(int i = startRow; i < startRow + len; i++) {
            for(int j = startCol; j < startCol + len; j++) {
                if(matrix[i][j] == '0')
                    return false;
            }
        }
        return true;
    }
/*
//my solution
public int maximalSquare(char[][] matrix) {
        if(matrix == null)
            return 0;
        if(matrix.length == 0 || matrix[0].length == 0)
            return 0;

        int[][] dp = new int[matrix.length][matrix[0].length];

        int maxSide = 0;
        for(int i = 0; i < matrix[0].length; i++) {
            dp[0][i] = matrix[0][i] - '0';
            maxSide = dp[0][i] > maxSide ? dp[0][i] : maxSide;
        }

        for(int i = 1; i < matrix.length; i++) {
            dp[i][0] = matrix[i][0] - '0';
            maxSide = dp[i][0] > maxSide ? dp[i][0] : maxSide;
        }

        for(int i = 1; i < matrix.length; i++) {
            for(int j = 1; j < matrix[0].length; j++) {
                switch(matrix[i][j]) {
                    case '0': dp[i][j] = 0; break;
                    case '1' :
                        if(matrix[i-1][j-1] == '1') {
                            int lowest = dp[i-1][j] <= dp[i][j-1] ? dp[i-1][j] : dp[i][j-1];
                            if(matrix[i-lowest][j-lowest] == '1') {
                                dp[i][j] = lowest + 1;
                                maxSide = dp[i][j] > maxSide ? dp[i][j] : maxSide;
                            } else {
                                dp[i][j] = lowest;
                            }
                        } else {
                            dp[i][j] = 1;
                        }
                        break;
                    default:
                        return 0;
                }
            }
        }

        return maxSide * maxSide;
    }*/
}

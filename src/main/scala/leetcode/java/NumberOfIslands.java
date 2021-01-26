package leetcode.java;

/**
 * Given an m x n 2d grid map of '1's (land) and '0's (water), return the number of islands.
 *
 * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.
 *
 *
 *
 * Example 1:
 *
 * Input: grid = [
 *   ["1","1","1","1","0"],
 *   ["1","1","0","1","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","0","0","0"]
 * ]
 * Output: 1
 * Example 2:
 *
 * Input: grid = [
 *   ["1","1","0","0","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","1","0","0"],
 *   ["0","0","0","1","1"]
 * ]
 * Output: 3
 *
 *
 * Constraints:
 *
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 300
 * grid[i][j] is '0' or '1'.
 */
public class NumberOfIslands {
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;
        int result = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '0') continue;
                dfs(grid, i, j);
                result++;
            }
        }
        return result;
    }

    private void dfs(char[][] grid, int x, int y) {
        grid[x][y] = '0';
        if (x > 0 && grid[x - 1][y] == '1') dfs(grid, x - 1, y);
        if (x < grid.length - 1 && grid[x + 1][y] == '1') dfs(grid, x + 1, y);
        if (y > 0 && grid[x][y - 1] == '1') dfs(grid, x, y - 1);
        if (y < grid[x].length - 1 && grid[x][y + 1] == '1') dfs(grid, x, y + 1);
    }
    /*
    //wrong answer
    public int numIslands(char[][] grid) {
        if(grid == null)
            return 0;

        boolean waterAbove = false;
        boolean waterLeft = false;

        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == '1') {
                    waterAbove = (i == 0 ? true : grid[i-1][j] == '0');
                    waterLeft = (j == 0 ? true : grid[i][j-1] == '0');
                    if(waterAbove && waterLeft) {
                        grid[i][j] = '2';
                    } else if (!waterAbove && !waterLeft) {
                        int row = i -1;
                        boolean merged = false;
                        while(row >= 0) {
                            if(grid[row][j] == '2') {
                                merged = true;
                                grid[row][j] = '1';
                                break;
                            }
                            row--;
                        }

                        if(!merged) {
                            row = i;
                            int col = j -1;
                            while(col >= 0) {
                                if(grid[row][col] == '2') {
                                    grid[row][col] = '1';
                                    break;
                                }
                                col--;
                            }
                        }
                    }
                }
            }
        }

        int count = 0;
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == '2') {
                    count++;
                }
            }
        }

        return count;
    }*/


/*    public int numIslands(char[][] grid) {
        if(grid == null)
            return 0;

        Set<Island> islands = new HashSet<Island>();

        boolean waterAbove = false;
        boolean waterLeft = false;

        Set<SubIsland> islandsAbove = new HashSet<SubIsland>();
        Set<SubIsland> current = new HashSet<SubIsland>();
        for(int i = 0; i < grid.length; i++) {
            SubIsland currentIsland = null;
            for(int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == '1') {
//                    System.out.println(i + " " + j + " " + grid[i][j]);
                    waterLeft = (j == 0 ? true : grid[i][j-1] == '0');
                    if(waterLeft) {
                        currentIsland = new SubIsland(i);
                        currentIsland.columns.add(j);
                        current.add(currentIsland);
                    } else {
                        currentIsland.columns.add(j);
                    }
                }
            }

            if(islandsAbove.isEmpty()) {
                for(SubIsland child : current) {
                    Island newIsland = new Island();
                    newIsland.add(child);
                    child.parentIsland = newIsland;
                    islands.add(newIsland);
                }
            } else {
                for(SubIsland curr : current) {
                    boolean overlap = false;
                    for(SubIsland prev : islandsAbove) {
                        if(prev.hasOverlap(curr)) {
                            overlap = true;
                            if(curr.parentIsland == null) {
                                prev.parentIsland.add(curr);
                                curr.parentIsland = prev.parentIsland;
                            } else if(prev.parentIsland != curr.parentIsland){
                                prev.parentIsland.union(curr.parentIsland);
                                islands.remove(curr.parentIsland);
                                islands.add(prev.parentIsland);
                                curr.parentIsland = prev.parentIsland;
                            }
                        }
                    }

                    if(!overlap) {
                        Island newIsland = new Island();
                        newIsland.add(curr);
                        curr.parentIsland = newIsland;
                        islands.add(newIsland);
                    }
                }
            }

            islandsAbove.clear();
            islandsAbove.addAll(current);
            current.clear();
        }
        return islands.size();
    }

    private static class SubIsland {
        int row;
        Set<Integer> columns = new HashSet<Integer>();
        Island parentIsland;

        public SubIsland (int row) {
            this.row = row;
        }

        public boolean hasOverlap(SubIsland other) {
            int rowDiff = Math.abs(row - other.row);
            if(rowDiff == 1) {
                for(Integer myColumn : columns) {
                    if(other.columns.contains(myColumn))
                        return true;
                }
            }
            return false;
        }

        public String toString() {
            return row + " " +  columns.toString();
        }
    }

    private static class Island {
        Map<Integer, SubIsland> subIslands = new HashMap<Integer,SubIsland>();

        public void add(SubIsland child) {
            SubIsland lastChild = subIslands.get(child.row);
            if(lastChild == null)
                subIslands.put(child.row, child);
            else
                lastChild.columns.addAll(child.columns);
        }

        public void union(Island other) {
            for(SubIsland sub : other.subIslands.values())
                add(sub);
        }

        public String toString() {
           StringBuilder sb = new StringBuilder();
           for(SubIsland sub : subIslands.values())
                sb.append("\n").append(sub.toString());

            return sb.toString();
        }
    }
*/
    /*
    public int numIslands(char[][] grid) {
        if(grid == null)
            return 0;

        boolean waterAbove = false;
        boolean waterLeft = false;

        int numberOfIslands = 0;

            boolean newIslandStart = false;
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == '1') {
                    waterAbove = (i == 0 ? true : grid[i-1][j] == '0');
                    waterLeft = (j == 0 ? true : grid[i][j-1] == '0');
                    if(waterAbove && waterLeft) {
                        newIslandStart = true;
                        numberOfIslands++;
                    } else if (!waterAbove && !waterLeft  && newIslandStart && numberOfIslands != 1) {
                        numberOfIslands--;
                        newIslandStart = false;
                    }
                }
            }
        }

        return numberOfIslands;
    }*/
}

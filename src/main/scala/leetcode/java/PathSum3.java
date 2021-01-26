package leetcode.java;

/**
 * You are given a binary tree in which each node contains an integer value.
 *
 * Find the number of paths that sum to a given value.
 *
 * The path does not need to start or end at the root or a leaf, but it must go downwards (traveling only from parent nodes to child nodes).
 *
 * The tree has no more than 1,000 nodes and the values are in the range -1,000,000 to 1,000,000.
 *
 * Example:
 *
 * root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8
 *
 *       10
 *      /  \
 *     5   -3
 *    / \    \
 *   3   2   11
 *  / \   \
 * 3  -2   1
 *
 * Return 3. The paths that sum to 8 are:
 *
 * 1.  5 -> 3
 * 2.  5 -> 2 -> 1
 * 3. -3 -> 11
 */
public class PathSum3 {
    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }

    //bruteforce, treat every node as root and find pathSum
    public int pathSum(TreeNode root, int sum) {
        if (root == null) return 0;
        return pathSumFrom(root, sum) + pathSum(root.left, sum) + pathSum(root.right, sum);
    }

    private int pathSumFrom(TreeNode node, int sum) {
        if (node == null) return 0;
        return (node.val == sum ? 1 : 0)
            + pathSumFrom(node.left, sum - node.val) + pathSumFrom(node.right, sum - node.val);
    }

    /*
    //best solution obtained by optimizing bruteforce solution
    //note that at a time you are dealing with only one path from root to node
    //you can also use a hashmap<node, integer>, add node during preOp and remove node during postOp
    //but maitaing sum using levels is much easier.
    int count=0;
    public int pathSum(TreeNode root, int sum) {
        int n=findDepth(root);
        int[]path= new int[n];
        findSum(root,sum,path,0);
        return count;
    }
    int findDepth(TreeNode root){
        if(root==null)return 0;
        return Math.max(findDepth(root.left),findDepth(root.right))+1;
    }
    void findSum(TreeNode root,int sum,int [] path,int level){
        if(root==null)return;
        path[level]=root.val;
        int total=0;
        for(int i=level;i>=0;i--){
           total+=path[i];
           if(total==sum){
               count=count+1;
           }
         }
       findSum(root.left,sum,path,level+1);
       findSum(root.right,sum,path,level+1);
        path[level]=Integer.MIN_VALUE;
    }*/
}

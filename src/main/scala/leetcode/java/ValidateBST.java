package leetcode.java;

/**
 * Given the root of a binary tree, determine if it is a valid binary search tree (BST).
 *
 * A valid BST is defined as follows:
 *
 * The left subtree of a node contains only nodes with keys less than the node's key.
 * The right subtree of a node contains only nodes with keys greater than the node's key.
 * Both the left and right subtrees must also be binary search trees.
 *
 *
 * Example 1:
 *
 *
 * Input: root = [2,1,3]
 * Output: true
 * Example 2:
 *
 *
 * Input: root = [5,1,4,null,null,3,6]
 * Output: false
 * Explanation: The root node's value is 5 but its right child's value is 4.
 */
public class ValidateBST {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    TreeNode INVALID = new TreeNode(0);

    public boolean isValidBST(TreeNode root) {
        return validateBST(root, null) != INVALID;
    }

    /**
     * does validation using inorder traversal same as the non-recursive solution.
     **/
    public TreeNode validateBST(TreeNode root, TreeNode lastVisited) {
        if(root == null)
            return lastVisited;

        lastVisited = validateBST(root.left, lastVisited);

        if(lastVisited == INVALID)
            return lastVisited;
        else if(lastVisited == null || lastVisited.val < root.val)
            lastVisited = root;
        else
            return INVALID;

        return validateBST(root.right, lastVisited);
    }

/*        public boolean isValidBST(TreeNode root) {
		   Stack<TreeNode> stack = new Stack<TreeNode> ();
		   TreeNode cur = root ;
		   TreeNode pre = null ;
		   while (!stack.isEmpty() || cur != null) {
			   while (cur != null) {
				   stack.push(cur);
				   cur = cur.left ;
			   }
			   cur = stack.pop() ;
			   if (pre != null && cur.val <= pre.val) {
			        return false ;
			    }
			    pre = cur;
				cur = cur.right;
		   }
		   return true;
    }*/
/*    public boolean isValidBST(TreeNode root) {
		   Stack<TreeNode> stack = new Stack<TreeNode> ();
		   TreeNode cur = root ;
		   TreeNode pre = null ;
		   while (!stack.isEmpty() || cur != null) {
			   if (cur != null) {
				   stack.push(cur);
				   cur = cur.left ;
			   } else {
				   TreeNode p = stack.pop() ;
				   if (pre != null && p.val <= pre.val) {
					   return false ;
				   }
				   pre = p ;
				   cur = p.right ;
			   }
		   }
		   return true ;
    }*/

/*    private boolean validateBST(TreeNode node, long min, long max) {
        if(node == null)
            return true;

        if(node.val <= min || node.val >= max) {
            return false;
        }

        return validateBST(node.left, min, node.val)
            && validateBST(node.right, node.val, max);
    }*/
/*    public boolean isValidBST(TreeNode root) {
        if(root == null)
            return true;
        return validateBST(root.left, root, null)
            && validateBST(root.right, root, null);
    }

    //incorrect solution
    private boolean validateBST(TreeNode root, TreeNode parent, TreeNode grandParent) {
        if(root == null)
            return true;

        if(grandParent == null) {
            if(root == parent.left && root.val >= parent.val)
                return false;
            if(root == parent.right && root.val <= parent.val)
                return false;
        } else {
            if(parent == grandParent.left) {
                if(root == parent.left &&
                    ( root.val >= parent.val || root.val >= grandParent.val))
                    return false;
                if(root == parent.right &&
                    (root.val <= parent.val || root.val >= grandParent.val))
                    return false;
            } else if (parent == grandParent.right) {
                if(root == parent.left &&
                    (root.val >= parent.val || root.val <= grandParent.val))
                    return false;
                if(root == parent.right &&
                    (root.val <= parent.val || root.val <= grandParent.val))
                    return false;
            }
        }

        grandParent = parent;
        parent = root;

        return validateBST(root.left, parent, grandParent)
        && validateBST(root.right, parent, grandParent);
    }*/
    /*public boolean isValidBST(TreeNode root) {
        return validateBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean validateBST(TreeNode node, int min, int max) {
        if(node == null)
            return true;

        if(node.val == Integer.MIN_VALUE) {
            return node.val == min &&
                   node.left == null &&
                ( (node.right == null) ||
                    (node.right.val > node.val &&
                        validateBST(node.right, node.val, max)
                    )
                );
        } else if (node.val == Integer.MAX_VALUE) {
            return node.val == max &&
                node.right == null &&
                ( (node.left == null) ||
                    (node.left.val < node.val &&
                        validateBST(node.left, min, node.val)
                    )
                );
        } else if(node.val <= min || node.val >= max) {
            return false;
        }

        return validateBST(node.left, min, node.val)
            && validateBST(node.right, node.val, max);
    }*/

/*    public boolean isValidBST(TreeNode root) {
        if(root == null)
            return true;
        return validateBST(root) != null;
    }

    private MaxMin validateBST(TreeNode node) {
        if(node == null)
            return null;

        if(node.left == null && node.right == null) {
            return new MaxMin(node.val, node.val);
        }

        MaxMin leftMaxMin = null;
        if(node.left != null) {
            leftMaxMin = validateBST(node.left);
            if(leftMaxMin == null || node.val <= leftMaxMin.max)
                return null;
        } else {
            leftMaxMin = new MaxMin(node.val, node.val);
        }

        MaxMin rightMaxMin = null;
        if(node.right != null) {
            rightMaxMin = validateBST(node.right);
            if(rightMaxMin == null || node.val >= rightMaxMin.min)
                return null;
        } else {
            rightMaxMin = new MaxMin(node.val, node.val);
        }

        return new MaxMin(rightMaxMin.max, leftMaxMin.min);
    }

    private static class MaxMin {
        int max;
        int min;

        MaxMin(int max, int min) {
            this.max = max;
            this.min = min;
        }
    }*/
}

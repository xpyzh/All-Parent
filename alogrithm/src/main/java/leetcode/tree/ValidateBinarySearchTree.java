package leetcode.tree;

/**
 *
 * 98. Validate Binary Search Tree
 *
 * Given a binary tree, determine if it is a valid binary search tree (BST).
 *
 * Assume a BST is defined as follows:
 *
 * The left subtree of a node contains only nodes with keys less than the node's key.
 * The right subtree of a node contains only nodes with keys greater than the node's key.
 * Both the left and right subtrees must also be binary search trees.
 * Example 1:
 *
 * Input:
 *     2
 *    / \
 *   1   3
 * Output: true
 * Example 2:
 *
 *     5
 *    / \
 *   1   4
 *      / \
 *     3   6
 * Output: false
 * Explanation: The input is: [5,1,4,null,null,3,6]. The root node's value
 *              is 5 but its right child's value is 4.
 * Created by youzhihao on 4/4/19.
 */
public class ValidateBinarySearchTree {


    //使用递归进行，设置一个min和max,每一个子节点，不论左右，都需要在这个min和max范围之内才符合二分查找树的定义
//    public static boolean isValidBST1(TreeNode root) {
//
//    }
//
//    public static boolean compare(TreeNode root, Integer min, Integer max) {
//        //如果节点不在最小和最大的范围内，直接返回false
//        if (min != null && root.val < min) return false;
//        if (max != null && root.val > max) return false;
//        boolean left = root.left == null || compare(root.left, min, root.val);
//        if (left) {
//            boolean right = root.right == null || compare(root.right, root.val, max);
//        }
//    }


    // Definition for a binary tree node.
    public static class TreeNode {

        int val;

        TreeNode left;

        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}

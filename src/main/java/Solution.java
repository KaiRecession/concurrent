import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;

class Solution {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1, 'A');
        build(root, new int[]{1, 2, 2, 1, 4}, "ABCCAD");
        int[] result = new int[6];
        find(root, new HashMap<Character, Integer>(), result);
        for (int i : result) {
            System.out.println(i);
        }
        System.out.println(result);

    }

    public static TreeNode build(TreeNode root, int[] nums, String s) {
        HashMap<Integer, TreeNode> trees = new HashMap<>();
        trees.put(1, root);
        for (int i = 0; i < nums.length; i++) {
            TreeNode subTree = new TreeNode(i + 2, s.charAt(i + 1));
            trees.put(i + 2, subTree);
            TreeNode treeNode = trees.get(nums[i]);
            if (treeNode.left == null)
                treeNode.left = subTree;
            else
                treeNode.right = subTree;
        }
        return root;
    }

    public static void find(TreeNode root, HashMap<Character, Integer> note, int[] result) {
        if (root == null) {
            return;
        }
        HashMap<Character, Integer> note1 = new HashMap<>(note);
        HashMap<Character, Integer> note2 = new HashMap<>(note);
        if (root.right != null) {
            find(root.right, note1, result);
        }
        if (root.left != null)
            find(root.left, note2, result);
        note.putAll(note1);
        note.putAll(note2);
        note.put(root.c, note.getOrDefault(root.c, 0) + 1);
        result[root.val - 1] = note.size();

    }



}

class TreeNode {
      int val;
      char c;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
    TreeNode(int val, char c) { this.val = val; this.c = c;}
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
  }
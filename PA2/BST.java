// Bongki Moon (bkmoon@snu.ac.kr), Mar/27/2017

import com.sun.source.tree.Tree;

public class BST { // Binary Search Tree implementation

  private TreeNode root;
  public BST() { root = null; }

  public void insert(String key) {
    if(root==null) root = new TreeNode(key);
    else root.insertKey(key);
  }
  public boolean find(String key) {
    if(root==null || root.key == null) return false;
    else return root.findKey(key);
  }
  public int size() {
    int count = 0;
    if(root==null || root.key == null) return 0;
    else count = count + root.size();
    return count;
  }

  public int sumFreq() {
    int sum = 0;
    if(root == null || root.key == null) return 0;
    else sum = sum + root.sumFreq();
    return sum;
  }
  public int sumProbes() {
    int sum = 0;
    if(root == null || root.key == null) return 0;
    else sum = sum + root.sumProbes();
    return sum;
  }
  public void resetCounters() {
    if(root == null || root.key == null) return;
    root.clear();
  }

  public void print() {
    if(root == null || root.key == null) return;
    root.print();
  }

  public static class TreeNode{
    protected final String key;
    protected int frequency;
    protected int access_count;
    protected TreeNode right;
    protected TreeNode left;

    public TreeNode(String newKey){
      key = newKey;
      frequency = 0;
      access_count = 0;
      right = null;
      left = null;
    }

    public void insertKey(String key){
      if(this.key.equals(key)) this.frequency++;
      else if(this.key.compareTo(key)<0) {
        if(this.right == null || this.right.key == null) this.right = new TreeNode(key);
        else this.right.insertKey(key);
      }
      else {
        if(this.left == null || this.left.key == null) this.left = new TreeNode(key);
        else this.left.insertKey(key);
      }
    }

    public boolean findKey(String key){
      this.access_count++;
      if(this.key.equals(key)) return true;
      else if(this.key.compareTo(key)<0) {
        if(this.right == null || this.right.key == null) return false;
        else return this.right.findKey(key);
      }
      else {
        if(this.left == null || this.left.key == null) return false;
        else return this.left.findKey(key);
      }
    }

    public int size(){
      int count = 0;
      if(right!=null && right.key != null) {
        count++;
        count = count + right.size();
      }
      if(left!=null && left.key != null) {
        count++;
        count = count + left.size();
      }
      return count;
    }

    public int sumFreq(){
      int count = 0;
      if(right!=null && right.key != null) {
        count = count + frequency;
        count = count + right.sumFreq();
      }
      if(left!=null && left.key != null) {
        count = count + frequency;
        count = count + left.sumFreq();
      }
      return count;
    }

    public int sumProbes(){
      int count = 0;
      if(right!=null && right.key != null) {
        count = count + access_count;
        count = count + right.sumProbes();
      }
      if(left!=null && left.key != null) {
        count = count + access_count;
        count = count + left.sumProbes();
      }
      return count;
    }

    public void clear(){
      frequency = 0;
      access_count = 0;
      if(right!=null && right.key != null) right.clear();
      if(left!=null && left.key != null) left.clear();
    }

    public void print(){
      if(left!=null && left.key != null) left.print();
      System.out.println("[" + key + ":" + frequency + ":" + access_count + "]");
      if(right!=null && right.key != null) right.print();
    }
  }

}

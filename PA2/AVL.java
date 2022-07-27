import static java.lang.Math.max;

public class AVL extends BST
{
  private AVLNode root;
  public AVL() { root = new AVLNode(null); }
  
  public void insert(String key) {
    if(root==null) root = new AVLNode(key);
    else root.insertKey(key);
    controlBalance(root);
  }

  public AVLNode controlBalance(AVLNode tnode){
    if(tnode.key == null || tnode.height < 3) return tnode;
    tnode.left = controlBalance(tnode.left);
    tnode.right = controlBalance(tnode.right);
    if(tnode.left.height - tnode.right.height > 1 || tnode.left.height - tnode.right.height < -1){
      if(tnode.left.height > tnode.right.height){
        if(tnode.left.left.height >= tnode.left.right.height) return turnLL(tnode);
        else return turnLR(tnode);
      }
      else {
        if(tnode.right.left.height >= tnode.right.right.height) return turnRL(tnode);
        else return turnRR(tnode);
      }
    }
    else return tnode;
  }

  public AVLNode turnLL(AVLNode tnode){
    AVLNode tmp = tnode.left;
    tnode.left = tmp.right;
    tmp.right = tnode;
    //height 수정하기
    tnode.height = max(tnode.right.height, tnode.left.height) + 1;
    tmp.height = max(tmp.right.height, tmp.left.height) + 1;
    return tmp;
  }

  public AVLNode turnLR(AVLNode tnode){
    tnode.left = turnRR(tnode.left);
    return turnLL(tnode);
  }

  public AVLNode turnRL(AVLNode tnode){
    tnode.right = turnLL(tnode.right);
    return turnRR(tnode);
  }

  public AVLNode turnRR(AVLNode tnode){
    AVLNode tmp = tnode.right;
    tnode.right = tmp.left;
    tmp.left = tnode;
    //height 수정하기
    tnode.height = max(tnode.right.height, tnode.left.height) + 1;
    tmp.height = max(tmp.right.height, tmp.left.height) + 1;
    return tmp;
  }

  public static class AVLNode extends TreeNode {
    private int height;
    private AVLNode right;
    private AVLNode left;

    public AVLNode(String newKey){
      super(newKey);
      if(newKey==null) height = 0;
      else {
        height = 1;
        right = new AVLNode(null);
        left = new AVLNode(null);
      }
    }

    public void insertKey(String key){
      if(this.key.equals(key)) this.frequency++;
      else if(this.key.compareTo(key)<0) {
        if(this.right.key == null) {
          this.right = new AVLNode(key);
          this.right.height++;
        }
        else this.right.insertKey(key);
      }
      else {
        if(this.left.key == null) {
          this.left = new AVLNode(key);
          this.left.height++;
        }
        else this.left.insertKey(key);
      }
      height = max(right.height, left.height) + 1;
    }
  }

}


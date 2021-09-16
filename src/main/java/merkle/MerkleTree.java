package merkle;

import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Getter
public class MerkleTree {

    private final MessageDigest md;
    private String root;
    private MerkleTree leftTree;
    private MerkleTree rightTree;
    private Leaf leftLeaf;
    private Leaf rightLeaf;


    // specify used digest
    public MerkleTree(MessageDigest md) {
        this.md = md;
        this.root = hash("0");
        this.leftTree = null;
        this.rightTree = null;
        this.leftLeaf = null;
        this.rightLeaf = null;
    }

    public void add(MerkleTree leftTree, MerkleTree rightTree) {
        this.leftTree = leftTree;
        this.rightTree = rightTree;
        this.root = (hash(leftTree.getRoot() + rightTree.getRoot()));
    }

    public void add(Leaf leftLeaf, Leaf rightLeaf) {
        this.leftLeaf = leftLeaf;
        this.rightLeaf = rightLeaf;
        this.leftLeaf.setHash(hash(leftLeaf.getHash()));
        this.rightLeaf.setHash(hash(rightLeaf.getHash()));
        this.root = (hash(leftLeaf.getHash() + rightLeaf.getHash()));
    }


    // Return the root node digest
    public byte[] digest() {
        return root.getBytes(StandardCharsets.UTF_8);
    }

    // Prints the tree from the root node
    public void printTree() {
        System.out.println("Root hash: " + root);

        if (leftLeaf != null && rightLeaf != null) {
            System.out.println("Left leaf: " + leftLeaf.getHash() + "\t" + "Right leaf: " + rightLeaf.getHash());
        } else if (leftTree != null && rightTree != null) {
            leftTree.printTree();
            rightTree.printTree();
        }
    }

    private String hash(String msg) {
        byte[] digest = md.digest(msg.getBytes(StandardCharsets.UTF_8));
        return HashUtil.bytesToHex(digest);
    }
}
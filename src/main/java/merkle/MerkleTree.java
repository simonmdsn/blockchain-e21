package merkle;

import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Getter
public class MerkleTree  {

    private final MessageDigest md;
    private final Leaf root;
    private MerkleTree leftTree;
    private MerkleTree rightTree;
    private Leaf leftLeaf;
    private Leaf rightLeaf;


    // specify used digest
    public MerkleTree(MessageDigest md) {
        this.md = md;
        this.root = new Leaf("");
        this.leftTree = null;
        this.rightTree = null;
        this.leftLeaf = null;
        this.rightLeaf = null;
    }

    public void add(MerkleTree leftTree, MerkleTree rightTree) {
        this.leftTree = leftTree;
        this.rightTree = rightTree;
        this.root.setHash(hash(leftTree.getRoot().getHash() + rightTree.getRoot().getHash()));
    }

    public void add(Leaf leftLeaf, Leaf rightLeaf) {
        this.leftLeaf = leftLeaf;
        this.rightLeaf = rightLeaf;
        this.leftLeaf.setHash(hash(leftLeaf.getHash()));
        this.rightLeaf.setHash(hash(rightLeaf.getHash()));
        this.root.setHash(hash(leftLeaf.getHash() + rightLeaf.getHash()));
    }


    // Return the root node digest
    public byte[] digest() {
        return root.getHash().getBytes(StandardCharsets.UTF_8);
    }

    // Prints the tree from the root node
    public void printTree() {
        System.out.println("Root hash: " + root.getHash());

        if (leftLeaf != null && rightLeaf != null) {
            System.out.println("Left leaf: " + leftLeaf.getHash() + "\t" + "Right leaf: " + rightLeaf.getHash());
        } else if (leftTree != null && rightTree != null) {
            leftTree.printTree();
            rightTree.printTree();
        }
    }

    private String hash(String msg) {
        return bytesToHex(md.digest(msg.getBytes(StandardCharsets.UTF_8)));
    }

    private String bytesToHex(byte[] bytes) {
        byte[] hexBytes = "0123456789ABCDEF".getBytes(StandardCharsets.UTF_8);
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexBytes[v >>> 4];
            hexChars[j * 2 + 1] = hexBytes[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }
}
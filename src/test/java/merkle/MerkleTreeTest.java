package merkle;

import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;

public class MerkleTreeTest {

    @Test
    public void createSimpleMerkleTree() {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            MerkleTree merkleTree = new MerkleTree(instance);
            assertEquals(merkleTree.getMd().getAlgorithm(), "SHA-256");

            Leaf leftLeaf = new Leaf("left");
            Leaf rightLeaf = new Leaf("right");

            Leaf left2 = new Leaf("left");
            Leaf right2 = new Leaf("right");


            MerkleTree leftTree = new MerkleTree(instance);
            MerkleTree rightTree = new MerkleTree(instance);
            leftTree.add(leftLeaf, rightLeaf);
            rightTree.add(left2, right2);

            merkleTree.add(leftTree, rightTree);

            merkleTree.printTree();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}

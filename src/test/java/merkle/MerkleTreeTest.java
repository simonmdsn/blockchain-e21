package merkle;

import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.assertEquals;

public class MerkleTreeTest {

    @Test
    public void createSimpleMerkleTree() {
        try {
            String algorithm = "SHA-256";
            MessageDigest instance = MessageDigest.getInstance(algorithm);
            MerkleTree merkleTree = new MerkleTree(instance);
            assertEquals(merkleTree.getMd().getAlgorithm(), algorithm);

            // Manual creation
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

            System.out.println("");

            // With a builder method
            Queue<String> queue = new LinkedList<>(Arrays.asList("123", "123", "123", "123", "123", "123", "123", "123", "123"));

            MerkleTreeHelper merkleTreeHelper = new MerkleTreeHelper(instance);
            MerkleTree merkleTree1 = merkleTreeHelper.create(queue);
            merkleTree1.printTree();


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}

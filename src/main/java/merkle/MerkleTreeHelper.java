package merkle;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class MerkleTreeHelper {

    private final MessageDigest md;

    public MerkleTreeHelper(MessageDigest md) {
        this.md = md;
    }

    public MerkleTree create(Queue<String> transaction) {
        List<Leaf> collect = transaction.stream().map(Leaf::new).collect(Collectors.toList());
        List<MerkleTree> trees = new ArrayList<>();
        for (int i = 0; i < collect.size(); i += 2) {
            MerkleTree merkleTree = new MerkleTree(md);
            merkleTree.add(collect.get(i), i == collect.size() - 1 ? new Leaf("0") : collect.get(i + 1));
            trees.add(merkleTree);
        }
        return buildTree(trees);
    }

    private MerkleTree buildTree(List<MerkleTree> merkleTrees) {
        if (merkleTrees.size() == 1) {
            return merkleTrees.get(0);
        }
        List<MerkleTree> newTrees = new ArrayList<>();
        for (int i = 0; i < merkleTrees.size(); i += 2) {
            MerkleTree merkleTree1 = new MerkleTree(md);
            merkleTree1.add(merkleTrees.get(i), i == merkleTrees.size() - 1 ? new MerkleTree(md) : merkleTrees.get(i + 1));
            newTrees.add(merkleTree1);
        }
        return buildTree(newTrees);
    }

}

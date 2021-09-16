package hashcollision;

import merkle.HashUtil;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import java.util.stream.Collectors;

public class HashCollision implements Runnable {

    private final int numberOfBits;
    private List<String> strings;
    private final MessageDigest md;

    public HashCollision(int numberOfBits, List<String> strings, MessageDigest md) {
        this.numberOfBits = numberOfBits;
        this.strings = strings;
        this.md = md;
    }


    @Override
    public void run() {

        List<String> collect = strings.stream().map(s -> HashUtil.bytesToHex(md.digest(s.getBytes(StandardCharsets.UTF_8)))).collect(Collectors.toList());
        Set<String> foundCollisions = new HashSet<>();
        for (String hash : collect) {
            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < 5000) {
                UUID uuid = UUID.randomUUID();
                String randomString = HashUtil.bytesToHex(md.digest(uuid.toString().getBytes(StandardCharsets.UTF_8)));
                int counter = 0;
                for (int bits = numberOfBits; bits < hash.length(); bits++) {
                    if (hash.substring(counter, bits - 1).equals(randomString.substring(counter, bits - 1))) {
                        foundCollisions.add(randomString);
                        System.out.println(hash + "\t" + randomString);
                    }
                    counter++;
                }
            }
        }
        System.out.println("Found following collisions: " + foundCollisions);
    }

}

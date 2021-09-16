package hashcollision;

import lombok.SneakyThrows;
import org.junit.Test;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;

public class HashCollisionTest {

    @SneakyThrows
    @Test
    public void hashCollisionTest() {
        MessageDigest instance = MessageDigest.getInstance("SHA-256");
        int bits = 8;
        List<String> strings = Arrays.asList("119223", "99123", "123kajsdsa", "mas123", "ioasdo123", "9+13123", "mx123", "oaoAO123");
        HashCollision hashCollision = new HashCollision(bits, strings, instance);
        hashCollision.run();
    }
}

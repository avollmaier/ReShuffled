import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FirstClassTest {
    @Test
    void testListisEmpty() {
        final List<String> names=new ArrayList<>();
    }

    @Test
    void TestNull() {
        assertNotNull(null);
    }
}
package interpreter.model.adt;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class MyDictionaryTest {

    @Test
    void testPutAndGet() {
        IDictionary<String, Integer> dict = new MyDictionary<>();

        dict.put("x", 10);
        dict.put("y", 20);

        assertEquals(10, dict.get("x"));
        assertEquals(20, dict.get("y"));
    }

    @Test
    void testUpdateValue() {
        IDictionary<String, Integer> dict = new MyDictionary<>();

        dict.put("x", 10);
        dict.put("x", 50);

        assertEquals(50, dict.get("x"));
    }

    @Test
    void testContainsKey() {
        IDictionary<String, Integer> dict = new MyDictionary<>();

        assertFalse(dict.containsKey("x"));

        dict.put("x", 10);

        assertTrue(dict.containsKey("x"));
    }

    @Test
    void testRemove() {
        IDictionary<String, Integer> dict = new MyDictionary<>();

        dict.put("x", 10);
        dict.remove("x");

        assertFalse(dict.containsKey("x"));
    }

    @Test
    void testMissingKey() {
        IDictionary<String, Integer> dict = new MyDictionary<>();

        assertThrows(
                NoSuchElementException.class,
                () -> dict.get("missing")
        );
    }
}

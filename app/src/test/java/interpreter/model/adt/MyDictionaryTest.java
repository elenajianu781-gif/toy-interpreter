package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyDictionaryTest {

    @Test
    void testPutAndLookup() throws ADTException {
        IDictionary<String, Integer> dict = new MyDictionary<>();

        dict.put("x", 10);
        dict.put("y", 20);

        assertEquals(10, dict.lookup("x"));
        assertEquals(20, dict.lookup("y"));
    }

    @Test
    void testUpdate() throws ADTException {
        IDictionary<String, Integer> dict = new MyDictionary<>();

        dict.put("x", 10);
        dict.update("x", 50);

        assertEquals(50, dict.lookup("x"));
    }

    @Test
    void testIsDefined() {
        IDictionary<String, Integer> dict = new MyDictionary<>();

        assertFalse(dict.isDefined("x"));

        dict.put("x", 10);

        assertTrue(dict.isDefined("x"));
    }

    @Test
    void testMissingKey() {
        IDictionary<String, Integer> dict = new MyDictionary<>();

        assertThrows(
                ADTException.class,
                () -> dict.lookup("missing")
        );
    }

    @Test
    void testUpdateMissingKey() {
        IDictionary<String, Integer> dict = new MyDictionary<>();

        assertThrows(
                ADTException.class,
                () -> dict.update("missing", 20)
        );
    }

    @Test
    void testRemove() {
        IDictionary<String, Integer> dict = new MyDictionary<>();

        dict.put("x", 10);
        dict.remove("x");

        assertFalse(dict.isDefined("x"));
    }
}

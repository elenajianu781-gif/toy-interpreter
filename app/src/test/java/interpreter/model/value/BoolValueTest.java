package interpreter.model.value;

import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoolValueTest {
    @Test
    void testBoolValue() {
        BoolValue value = new BoolValue(true);

        assertTrue(value.val());
        assertEquals(new BoolType(), value.getType());
    }
}

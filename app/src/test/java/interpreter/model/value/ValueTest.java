
package interpreter.model.value;

import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValueTest {

    @Test
    void testIntValue() {
        IntValue value = new IntValue(10);

        assertEquals(10, value.getValue());
        assertEquals(new IntType(), value.getType());
    }

    @Test
    void testBoolValue() {
        BoolValue value = new BoolValue(true);

        assertTrue(value.getValue());
        assertEquals(new BoolType(), value.getType());
    }
}


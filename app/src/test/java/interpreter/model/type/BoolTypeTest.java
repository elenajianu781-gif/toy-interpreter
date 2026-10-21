package interpreter.model.type;

import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.IValue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoolTypeTest {
    @Test
    void testBoolTypeDefaultValue() {
        IType type = new BoolType();
        IValue value = type.defaultValue();

        assertInstanceOf(BoolValue.class, value);
        assertFalse(((BoolValue) value).getValue());
    }
    @Test
    void testBoolTypeEquality() {
        assertEquals(new BoolType(), new BoolType());
        assertNotEquals(new BoolType(), new IntType());
    }
}

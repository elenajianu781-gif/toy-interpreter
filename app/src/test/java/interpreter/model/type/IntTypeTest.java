package interpreter.model.type;

import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.IValue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IntTypeTest {
    @Test
    void testIntTypeDefaultValue() {
        IType type = new IntType();
        IValue value = type.defaultValue();

        assertInstanceOf(IntValue.class, value);
        assertEquals(0, ((IntValue) value). val());
    }
    @Test
    void testIntTypeEquality() {
        assertEquals(new IntType(), new IntType());
        assertNotEquals(new IntType(), new BoolType());
    }
}

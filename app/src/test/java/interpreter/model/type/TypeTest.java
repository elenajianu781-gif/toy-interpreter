
package interpreter.model.type;

import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.IValue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TypeTest {

    @Test
    void testIntTypeDefaultValue() {
        IType type = new IntType();
        IValue value = type.defaultValue();

        assertInstanceOf(IntValue.class, value);
        assertEquals(0, ((IntValue) value).getValue());
    }

    @Test
    void testBoolTypeDefaultValue() {
        IType type = new BoolType();
        IValue value = type.defaultValue();

        assertInstanceOf(BoolValue.class, value);
        assertFalse(((BoolValue) value).getValue());
    }

    @Test
    void testTypeEquality() {
        assertEquals(new IntType(), new IntType());
        assertEquals(new BoolType(), new BoolType());
        assertNotEquals(new IntType(), new BoolType());
    }
}

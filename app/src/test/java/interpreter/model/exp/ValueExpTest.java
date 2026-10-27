
package interpreter.model.exp;

import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValueExpTest {

    @Test
    void testIntValue() {
        ValueExp exp = new ValueExp(new IntValue(10));

        assertEquals(
                new IntValue(10),
                exp.eval(new MyDictionary<>())
        );
    }

    @Test
    void testBoolValue() {
        ValueExp exp = new ValueExp(new BoolValue(true));

        assertEquals(
                new BoolValue(true),
                exp.eval(new MyDictionary<>())
        );
    }
}

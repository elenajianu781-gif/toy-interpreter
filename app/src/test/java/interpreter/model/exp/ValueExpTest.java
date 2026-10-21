
package interpreter.model.exp;

import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.IntValue;
import interpreter.model.value.IValue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValueExpTest {

    @Test
    void evaluatesIntegerConstant() {
        IDictionary<String, IValue> table = new MyDictionary<>();

        IExp exp = new ValueExp(new IntValue(10));

        IValue result = exp.eval(table);

        assertEquals(10, ((IntValue) result).getValue());
    }
}

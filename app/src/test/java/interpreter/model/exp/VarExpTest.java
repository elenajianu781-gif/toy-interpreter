package interpreter.model.exp;

import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.IValue;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class VarExpTest {

    @Test
    void testVarExp() {
        IDictionary<String, IValue> table = new MyDictionary<>();
        table.put("x", new IntValue(25));

        IExp exp = new VarExp("x");

        IntValue result = (IntValue) exp.eval(table);
        assertEquals(25, result.getValue());
    }

    @Test
    void testMissingVariable() {
        IDictionary<String, IValue> table = new MyDictionary<>();

        IExp exp = new VarExp("missing");

        assertThrows(NoSuchElementException.class,
                () -> exp.eval(table));
    }
}

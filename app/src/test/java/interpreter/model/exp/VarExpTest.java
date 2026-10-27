package interpreter.model.exp;
import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.IValue;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import interpreter.exceptions.MyException;
public class VarExpTest {

    @Test
    void testVarExp() throws MyException {
        IDictionary<String, IValue> table = new MyDictionary<>();
        table.put("x", new IntValue(25));

        IExp exp = new VarExp("x");

        IntValue result = (IntValue) exp.eval(table);
        assertEquals(25, result.val());
    }

    @Test
    void testMissingVariable() {
        IDictionary<String, IValue> table = new MyDictionary<>();

        IExp exp = new VarExp("missing");


        assertThrows(
                ExpressionException.class,
                () -> exp.eval(table)
        );

    }
}

package interpreter.model.exp;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.IValue;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class LogicExpTest {
    @Test
    void testLogicalOperations() {
        IDictionary<String, IValue> table = new MyDictionary<>();

        IExp a = new ValueExp(new BoolValue(true));
        IExp b = new ValueExp(new BoolValue(false));

        assertFalse(((BoolValue)
                new LogicExp(a, b, 1).eval(table)).getValue());

        assertTrue(((BoolValue)
                new LogicExp(a, b, 2).eval(table)).getValue());
    }

    @Test
    void testInvalidLogicalType() {
        IDictionary<String, IValue> table = new MyDictionary<>();

        IExp exp = new LogicExp(
                new ValueExp(new BoolValue(true)),
                new ValueExp(new IntValue(5)),
                1
        );

        assertThrows(RuntimeException.class,
                () -> exp.eval(table));
    }
}

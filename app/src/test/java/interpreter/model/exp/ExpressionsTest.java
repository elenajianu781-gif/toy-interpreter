package interpreter.model.exp;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.IValue;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class ExpressionsTest {

    @Test
    void testValueExp() {
        IDictionary<String, IValue> table = new MyDictionary<>();

        IExp exp = new ValueExp(new IntValue(10));

        IntValue result = (IntValue) exp.eval(table);
        assertEquals(10, result.getValue());
    }

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

    @Test
    void testArithmeticOperations() {
        IDictionary<String, IValue> table = new MyDictionary<>();

        IExp a = new ValueExp(new IntValue(10));
        IExp b = new ValueExp(new IntValue(5));

        assertEquals(15, ((IntValue)
                new ArithExp(a, b, 1).eval(table)).getValue());

        assertEquals(5, ((IntValue)
                new ArithExp(a, b, 2).eval(table)).getValue());

        assertEquals(50, ((IntValue)
                new ArithExp(a, b, 3).eval(table)).getValue());

        assertEquals(2, ((IntValue)
                new ArithExp(a, b, 4).eval(table)).getValue());
    }

    @Test
    void testDivisionByZero() {
        IDictionary<String, IValue> table = new MyDictionary<>();

        IExp exp = new ArithExp(
                new ValueExp(new IntValue(10)),
                new ValueExp(new IntValue(0)),
                4
        );

        assertThrows(ArithmeticException.class,
                () -> exp.eval(table));
    }

    @Test
    void testInvalidArithmeticType() {
        IDictionary<String, IValue> table = new MyDictionary<>();

        IExp exp = new ArithExp(
                new ValueExp(new IntValue(10)),
                new ValueExp(new BoolValue(true)),
                1
        );

        assertThrows(RuntimeException.class,
                () -> exp.eval(table));
    }

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















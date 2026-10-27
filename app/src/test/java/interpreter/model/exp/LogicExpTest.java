package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.IValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogicExpTest {

    private final IDictionary<String, IValue> symTable =
            new MyDictionary<>();

    private static IExp bool(boolean value) {
        return new ValueExp(new BoolValue(value));
    }

    @Test
    void testAndTrue() throws MyException {
        IExp exp = new LogicExp(
                LogicOp.AND, bool(true), bool(true)
        );

        assertEquals(new BoolValue(true), exp.eval(symTable));
    }

    @Test
    void testAndFalse() throws MyException {
        IExp exp = new LogicExp(
                LogicOp.AND, bool(true), bool(false)
        );

        assertEquals(new BoolValue(false), exp.eval(symTable));
    }

    @Test
    void testOrTrue() throws MyException {
        IExp exp = new LogicExp(
                LogicOp.OR, bool(false), bool(true)
        );

        assertEquals(new BoolValue(true), exp.eval(symTable));
    }

    @Test
    void testOrFalse() throws MyException {
        IExp exp = new LogicExp(
                LogicOp.OR, bool(false), bool(false)
        );

        assertEquals(new BoolValue(false), exp.eval(symTable));
    }

    @Test
    void testFirstOperandWrongType() {
        IExp exp = new LogicExp(
                LogicOp.AND,
                new ValueExp(new IntValue(5)),
                bool(true)
        );

        assertThrows(
                ExpressionException.class,
                () -> exp.eval(symTable)
        );
    }

    @Test
    void testSecondOperandWrongType() {
        IExp exp = new LogicExp(
                LogicOp.OR,
                bool(false),
                new ValueExp(new IntValue(5))
        );

        assertThrows(
                ExpressionException.class,
                () -> exp.eval(symTable)
        );
    }
}

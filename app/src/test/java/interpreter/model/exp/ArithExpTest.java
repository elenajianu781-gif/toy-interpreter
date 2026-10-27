package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArithExpTest {

    private final IDictionary<String, IValue> symTable =
            new MyDictionary<>();

    private static IExp num(int n) {
        return new ValueExp(new IntValue(n));
    }

    @Test
    void testAddition() throws MyException {
        IExp exp = new ArithExp(ArithOp.ADD, num(2), num(3));
        assertEquals(new IntValue(5), exp.eval(symTable));
    }

    @Test
    void testSubtraction() throws MyException {
        IExp exp = new ArithExp(ArithOp.SUB, num(8), num(3));
        assertEquals(new IntValue(5), exp.eval(symTable));
    }

    @Test
    void testMultiplication() throws MyException {
        IExp exp = new ArithExp(ArithOp.MUL, num(4), num(5));
        assertEquals(new IntValue(20), exp.eval(symTable));
    }

    @Test
    void testDivision() throws MyException {
        IExp exp = new ArithExp(ArithOp.DIV, num(10), num(2));
        assertEquals(new IntValue(5), exp.eval(symTable));
    }

    @Test
    void testDivisionByZero() {
        IExp exp = new ArithExp(ArithOp.DIV, num(10), num(0));

        ExpressionException exception = assertThrows(
                ExpressionException.class,
                () -> exp.eval(symTable)
        );

        assertEquals("division by zero", exception.getMessage());
    }

    @Test
    void testNonIntegerOperand() {
        IExp exp = new ArithExp(
                ArithOp.ADD,
                new ValueExp(new BoolValue(true)),
                num(3)
        );

        assertThrows(
                ExpressionException.class,
                () -> exp.eval(symTable)
        );
    }

    @Test
    void testSecondNonIntegerOperand() {
        IExp exp = new ArithExp(
                ArithOp.ADD,
                num(3),
                new ValueExp(new BoolValue(false))
        );

        assertThrows(
                ExpressionException.class,
                () -> exp.eval(symTable)
        );
    }

    @Test
    void testOperatorPrecedence() throws MyException {
        IExp exp = new ArithExp(
                ArithOp.ADD,
                num(2),
                new ArithExp(ArithOp.MUL, num(3), num(5))
        );

        assertEquals(new IntValue(17), exp.eval(symTable));
    }
}

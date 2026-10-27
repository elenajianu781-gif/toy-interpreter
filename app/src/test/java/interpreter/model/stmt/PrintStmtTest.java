
package interpreter.model.stmt;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ArithOp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrintStmtTest {

    private PrgState createState() {
        return new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new NopStmt()
        );
    }

    @Test
    void testPrintValue() throws MyException {
        PrgState state = createState();

        PrintStmt stmt = new PrintStmt(
                new ValueExp(new IntValue(10))
        );

        stmt.execute(state);

        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(10), state.getOut().get(0));
    }

    @Test
    void testPrintVariable() throws MyException {
        PrgState state = createState();

        state.getSymTable().put("x", new IntValue(20));

        PrintStmt stmt = new PrintStmt(new VarExp("x"));

        stmt.execute(state);

        assertEquals(new IntValue(20), state.getOut().get(0));
    }

    @Test
    void testPrintArithmeticExpression() throws MyException {
        PrgState state = createState();

        PrintStmt stmt = new PrintStmt(
                new ArithExp(
                        ArithOp.ADD,
                        new ValueExp(new IntValue(2)),
                        new ValueExp(new IntValue(3))
                )
        );

        stmt.execute(state);

        assertEquals(new IntValue(5), state.getOut().get(0));
    }

    @Test
    void testPrintUndefinedVariable() {
        PrgState state = createState();

        PrintStmt stmt = new PrintStmt(new VarExp("missing"));

        assertThrows(
                ExpressionException.class,
                () -> stmt.execute(state)
        );

        assertTrue(state.getOut().isEmpty());
    }
}

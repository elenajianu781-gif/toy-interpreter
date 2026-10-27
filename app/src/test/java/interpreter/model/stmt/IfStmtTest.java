
package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IfStmtTest {

    private PrgState createState() {
        return new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new NopStmt()
        );
    }

    @Test
    void testTrueCondition() throws MyException {
        PrgState state = createState();

        IStmt thenStmt = new PrintStmt(
                new ValueExp(new IntValue(10))
        );

        IStmt elseStmt = new PrintStmt(
                new ValueExp(new IntValue(20))
        );

        IfStmt stmt = new IfStmt(
                new ValueExp(new BoolValue(true)),
                thenStmt,
                elseStmt
        );

        stmt.execute(state);

        assertSame(thenStmt, state.getExeStack().pop());
    }

    @Test
    void testFalseCondition() throws MyException {
        PrgState state = createState();

        IStmt thenStmt = new NopStmt();
        IStmt elseStmt = new PrintStmt(
                new ValueExp(new IntValue(20))
        );

        IfStmt stmt = new IfStmt(
                new ValueExp(new BoolValue(false)),
                thenStmt,
                elseStmt
        );

        stmt.execute(state);

        assertSame(elseStmt, state.getExeStack().pop());
    }

    @Test
    void testInvalidCondition() {
        PrgState state = createState();

        IfStmt stmt = new IfStmt(
                new ValueExp(new IntValue(10)),
                new NopStmt(),
                new NopStmt()
        );

        assertThrows(
                StatementException.class,
                () -> stmt.execute(state)
        );
    }
}

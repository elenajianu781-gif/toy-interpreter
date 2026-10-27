package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompStmtTest {

    @Test
    void testExecutionOrder() throws MyException {

        PrgState state = new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new NopStmt()
        );

        IStmt first = new NopStmt();
        IStmt second = new NopStmt();

        CompStmt stmt = new CompStmt(first, second);

        stmt.execute(state);

        assertSame(first, state.getExeStack().pop());
        assertSame(second, state.getExeStack().pop());
    }

    @Test
    void testReturnsSameState() {

        PrgState state = new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new NopStmt()
        );

        CompStmt stmt = new CompStmt(
                new NopStmt(),
                new NopStmt()
        );

        assertSame(state, stmt.execute(state));
    }
}


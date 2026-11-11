
package interpreter.model.stmt;

import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NopStmtTest {

    @Test
    void testExecute() {
        PrgState state = PrgState.of(new NopStmt());

        NopStmt stmt = new NopStmt();

        PrgState result = stmt.execute(state);

        assertSame(state, result);
        assertTrue(state.getSymTable().isDefined("x") == false);
        assertTrue(state.getOut().isEmpty());
    }
}

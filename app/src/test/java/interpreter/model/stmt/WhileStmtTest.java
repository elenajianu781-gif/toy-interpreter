
package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.*;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WhileStmtTest {

    // Helper method to execute a while loop
    private int runLoop(int initialValue) throws MyException {

        PrgState state = PrgState.of(new NopStmt());
        state.getExeStack().pop();

        // int v; v = initialValue;
        new VarDeclStmt("v", new IntType()).execute(state);
        new AssignStmt("v", new ValueExp(new IntValue(initialValue)))
                .execute(state);

        // Body: v = v - 1
        IStmt body = new AssignStmt(
                "v",
                new ArithExp(
                        ArithOp.SUB,
                        new VarExp("v"),
                        new ValueExp(new IntValue(1))
                )
        );

        // while (v > 0) body
        WhileStmt loop = new WhileStmt(
                new RelationalExp(
                        RelOp.GT,
                        new VarExp("v"),
                        new ValueExp(new IntValue(0))
                ),
                body
        );

        state.getExeStack().push(loop);

        int iterations = 0;
        int steps = 0;

        // Execute statements from the stack
        while (!state.getExeStack().isEmpty()) {
            assertTrue(steps++ < 100, "Loop did not finish");

            IStmt current = state.getExeStack().pop();

            if (current == body) {
                iterations++;
            }

            current.execute(state);
        }

        // The final value must be 0
        assertEquals(new IntValue(0),
                state.getSymTable().lookup("v"));

        return iterations;
    }

    @Test
    void whileWithZeroIterations() throws MyException {
        assertEquals(0, runLoop(0));
    }

    @Test
    void whileWithOneIteration() throws MyException {
        assertEquals(1, runLoop(1));
    }

    @Test
    void whileWithSeveralIterations() throws MyException {
        assertEquals(4, runLoop(4));
    }

    @Test
    void whileWithNonBooleanConditionThrows() {
        PrgState state = PrgState.of(new NopStmt());

        WhileStmt loop = new WhileStmt(
                new ValueExp(new IntValue(1)),
                new NopStmt()
        );

        assertThrows(StatementException.class,
                () -> loop.execute(state));
    }
}

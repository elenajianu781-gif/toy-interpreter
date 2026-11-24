
package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WriteHeapStmtTest {

    private PrgState state;

    @BeforeEach
    void setUp() throws MyException {
        state = PrgState.of(new NopStmt());

        new VarDeclStmt("v", new RefType(new IntType()))
                .execute(state);

        new NewStmt("v", new ValueExp(new IntValue(20)))
                .execute(state);
    }

    @Test
    void writeHeapUpdatesTheValue() throws MyException {
        new WriteHeapStmt("v", new ValueExp(new IntValue(30)))
                .execute(state);

        assertEquals(new IntValue(30), state.getHeap().get(1));

        assertEquals(
                new RefValue(1, new IntType()),
                state.getSymTable().lookup("v")
        );

        assertEquals(1, state.getHeap().getContent().size());
    }

    @Test
    void writeHeapWithUndeclaredVariableThrows() {
        assertThrows(StatementException.class, () ->
                new WriteHeapStmt(
                        "x", new ValueExp(new IntValue(30))
                ).execute(state)
        );
    }

    @Test
    void writeHeapWithNonReferenceVariableThrows() throws MyException {
        new VarDeclStmt("x", new IntType()).execute(state);

        assertThrows(StatementException.class, () ->
                new WriteHeapStmt(
                        "x", new ValueExp(new IntValue(30))
                ).execute(state)
        );
    }

    @Test
    void writeHeapWithInvalidAddressThrows() throws MyException {
        state.getSymTable().update(
                "v", new RefValue(99, new IntType())
        );

        assertThrows(StatementException.class, () ->
                new WriteHeapStmt(
                        "v", new ValueExp(new IntValue(30))
                ).execute(state)
        );
    }

    @Test
    void writeHeapWithWrongTypeThrows() {
        assertThrows(StatementException.class, () ->
                new WriteHeapStmt(
                        "v", new ValueExp(new BoolValue(true))
                ).execute(state)
        );
    }
}

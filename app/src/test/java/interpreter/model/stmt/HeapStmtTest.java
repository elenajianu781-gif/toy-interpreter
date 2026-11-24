package interpreter.model.stmt;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IntValue;
import interpreter.model.value.BoolValue;
import interpreter.model.value.RefValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Import StatementException from your exception package.

class HeapStmtTest {

    private PrgState state;

    @BeforeEach
    void setUp() throws Exception{
        state=PrgState.of(new NopStmt());

        new VarDeclStmt(
                "v",new RefType(new IntType())
        ).execute(state);
    }

    @Test
    void newAllocatesAtAddressOne() throws Exception{
        new NewStmt(
                "v",new ValueExp(new IntValue(20))
        ).execute(state);

        assertEquals(new RefValue(1,new IntType()),
                state.getSymTable().lookup("v"));
        assertEquals(
                new IntValue(20),
                state.getHeap().get(1)
        );
    }

    @Test
    void newWithUndeclaredVariableThrows() {
        assertThrows(StatementException.class, () ->
                new NewStmt(
                        "x", new ValueExp(new IntValue(20))
                ).execute(state)
        );
    }

    @Test
    void newWithNonReferenceVariableThrows() throws Exception {
        new VarDeclStmt("x", new IntType()).execute(state);

        assertThrows(StatementException.class, () ->
                new NewStmt(
                        "x", new ValueExp(new IntValue(20))
                ).execute(state)
        );
    }

    @Test
    void newWithWrongTypeThrows() {
        assertThrows(StatementException.class, () ->
                new NewStmt(
                        "v", new ValueExp(new BoolValue(true))
                ).execute(state)
        );
    }


}
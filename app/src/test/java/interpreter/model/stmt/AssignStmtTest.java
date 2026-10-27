
package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ArithOp;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssignStmtTest {

    private PrgState createState() {
        return new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new NopStmt()
        );
    }

    @Test
    void testAssignment() throws MyException {
        PrgState state = createState();

        new VarDeclStmt("x", new IntType()).execute(state);

        AssignStmt stmt = new AssignStmt(
                "x",
                new ValueExp(new IntValue(10))
        );

        stmt.execute(state);

        assertEquals(
                new IntValue(10),
                state.getSymTable().lookup("x")
        );
    }

    @Test
    void testArithmeticAssignment() throws MyException {
        PrgState state = createState();

        new VarDeclStmt("x", new IntType()).execute(state);

        AssignStmt stmt = new AssignStmt(
                "x",
                new ArithExp(
                        ArithOp.ADD,
                        new ValueExp(new IntValue(2)),
                        new ValueExp(new IntValue(3))
                )
        );

        stmt.execute(state);

        assertEquals(
                new IntValue(5),
                state.getSymTable().lookup("x")
        );
    }

    @Test
    void testUndefinedVariable() {
        PrgState state = createState();

        AssignStmt stmt = new AssignStmt(
                "x",
                new ValueExp(new IntValue(10))
        );

        assertThrows(
                StatementException.class,
                () -> stmt.execute(state)
        );
    }

    @Test
    void testTypeMismatch() throws MyException {
        PrgState state = createState();

        new VarDeclStmt("x", new IntType()).execute(state);

        AssignStmt stmt = new AssignStmt(
                "x",
                new ValueExp(new BoolValue(true))
        );

        assertThrows(
                StatementException.class,
                () -> stmt.execute(state)
        );

        // Valoarea lui x tr să rămână neschimbată
        assertEquals(
                new IntValue(0),
                state.getSymTable().lookup("x")
        );
    }
}

package interpreter.model.stmt;
import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.type.StringType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class VarDeclStmtTest {
    private PrgState createState() {
        return PrgState.of(new NopStmt());

    }
    @Test
    void testIntDeclaration() throws MyException{
        PrgState state=createState();

        VarDeclStmt stmt=new VarDeclStmt("x", new IntType());

        stmt.execute(state);

        assertTrue(state.getSymTable().isDefined("x"));
        assertEquals( new IntValue(0),state.getSymTable().lookup("x"));
    }
    @Test
    void testBoolDeclaration() throws MyException {
        PrgState state = createState();

        VarDeclStmt stmt =
                new VarDeclStmt("ok", new BoolType());

        stmt.execute(state);

        assertEquals(
                new BoolValue(false),
                state.getSymTable().lookup("ok")
        );
    }
    @Test
    void testDuplicateDeclaration() throws MyException{
        PrgState state=createState();

        VarDeclStmt stmt=new VarDeclStmt("x", new IntType());

        stmt.execute(state);

        assertThrows(StatementException.class,
                ()->stmt.execute(state));
    }



    @Test
    void stringVariableGetsDefaultValue() throws MyException {
        PrgState state = PrgState.of(new NopStmt());

        new VarDeclStmt("s", new StringType()).execute(state);

        assertEquals(
                new StringValue(""),
                state.getSymTable().lookup("s")
        );
    }


}

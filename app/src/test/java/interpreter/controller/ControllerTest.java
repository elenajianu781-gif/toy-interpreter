package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.stmt.*;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import org.junit.jupiter.api.Test;
import interpreter.model.stmt.IStmt;
import interpreter.view.Examples;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {


    private PrgState runProgram(IStmt program) throws MyException {

        PrgState state = PrgState.of(program);

        Repository repository = new Repository(state,"log-test.txt");
        Controller controller = new Controller(repository);

        controller.setDisplayFlag(false);
        controller.allStep();

        return state;
    }


    @Test
    void testOneStep() throws MyException {

        PrgState state = PrgState.of(
                new PrintStmt(new ValueExp(new IntValue(10)))
        );

        Repository repository = new Repository(state,"log-test.txt");
        Controller controller = new Controller(repository);

        controller.oneStep(state);

        assertEquals(1, state.getOut().size());
        assertEquals(
                new IntValue(10),
                state.getOut().get(0)
        );

        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void testEmptyStack() throws MyException {

        PrgState state = PrgState.of(new NopStmt());

        Repository repository = new Repository(state,"log-test.txt");
        Controller controller = new Controller(repository);

        controller.oneStep(state);

        assertThrows(
                MyException.class,
                () -> controller.oneStep(state)
        );
    }


    @Test
    void testAllStep() throws MyException {

        IStmt program = new CompStmt(
                new VarDeclStmt("x", new IntType()),
                new CompStmt(
                        new AssignStmt(
                                "x",
                                new ValueExp(new IntValue(10))
                        ),
                        new PrintStmt(new VarExp("x"))
                )
        );

        PrgState state = PrgState.of(program);

        Repository repository = new Repository(state,"log-test.txt");
        Controller controller = new Controller(repository);

        controller.allStep();

        PrgState result = repository.getCrtPrg();

        assertTrue(result.getExeStack().isEmpty());

        assertEquals(
                new IntValue(10),
                result.getSymTable().lookup("x")
        );

        assertEquals(1, result.getOut().size());

        assertEquals(
                new IntValue(10),
                result.getOut().get(0)
        );
    }


    @Test
    void testEmptyStackThrowsStatementException() throws MyException {
        PrgState state = PrgState.of(new NopStmt());

        Controller controller = new Controller(new Repository(state,"log-test.txt"));

        controller.oneStep(state);

        assertThrows(
                StatementException.class,
                () -> controller.oneStep(state)
        );
    }

    @Test
    void testExample1() throws MyException {

        PrgState state = runProgram(
                Examples.getPrograms().get(0)
        );

        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());

        assertEquals(
                new IntValue(2),
                state.getOut().get(0)
        );
    }

    @Test
    void testExample2() throws MyException {

        PrgState state = runProgram(
                Examples.getPrograms().get(1)
        );

        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());

        assertEquals(
                new IntValue(18),
                state.getOut().get(0)
        );
    }

    @Test
    void testExample3() throws MyException {

        PrgState state = runProgram(
                Examples.getPrograms().get(2)
        );

        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());

        assertEquals(
                new IntValue(2),
                state.getOut().get(0)
        );
    }


    @TempDir
    Path dir;

    @Test
    void logFileContainsFourSectionsInOrder() throws Exception {
        PrgState state = PrgState.of(
                new PrintStmt(new ValueExp(new IntValue(10)))
        );

        Path logPath = dir.resolve("log.txt");

        Repository repo = new Repository(
                state,
                logPath.toString()
        );

        Controller controller = new Controller(repo);
        controller.setDisplayFlag(false);
        controller.allStep();

        String log = Files.readString(logPath);

        int a = log.indexOf("ExeStack:");
        int b = log.indexOf("SymTable:");
        int c = log.indexOf("Out:");
        int d = log.indexOf("FileTable:");

        assertTrue(0 <= a && a < b && b < c && c < d);
    }




}

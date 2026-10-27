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

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @Test
    void testOneStep() throws MyException {

        PrgState state = new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new PrintStmt(
                        new ValueExp(new IntValue(10))
                )
        );

        Repository repository = new Repository(state);
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

        PrgState state = new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new NopStmt()
        );

        Repository repository = new Repository(state);
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

        PrgState state = new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                program
        );

        Repository repository = new Repository(state);
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
        PrgState state = new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new NopStmt()
        );

        Controller controller = new Controller(new Repository(state));

        controller.oneStep(state);

        assertThrows(
                StatementException.class,
                () -> controller.oneStep(state)
        );
    }


}

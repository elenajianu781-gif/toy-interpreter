package interpreter.repository;

import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.NopStmt;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {

    private PrgState createState() {
        return new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new NopStmt()
        );
    }

    @Test
    void testGetCurrentProgram() {
        PrgState state = createState();

        Repository repository = new Repository(state);

        assertSame(state, repository.getCurrentProgram());
    }

    @Test
    void testSetCurrentProgram() {
        PrgState firstState = createState();
        PrgState secondState = createState();

        Repository repository = new Repository(firstState);

        repository.setCurrentProgram(secondState);

        assertSame(secondState, repository.getCurrentProgram());
    }
}

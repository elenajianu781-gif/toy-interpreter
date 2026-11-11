
package interpreter.repository;

import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.NopStmt;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {

    @Test
    void testGetCurrentProgram() {

        PrgState state = PrgState.of(new NopStmt());

        Repository repository = new Repository(state,"log-test.txt");

        assertSame(state, repository.getCrtPrg());
    }
}

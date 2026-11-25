
package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;

public class Controller {

    private final IRepository repo;
    private boolean displayFlag;


    public Controller(IRepository repo) {
        this(repo, false);
    }

    public Controller(IRepository repo, boolean displayFlag) {
        this.repo = repo;
        this.displayFlag = displayFlag;
    }


    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag = displayFlag;
    }

    public PrgState oneStep(PrgState state) throws MyException {
        var stack = state.getExeStack();

        if (stack.isEmpty()) {
            throw new StatementException(
                    "program state stack is empty"
            );
        }

        IStmt crtStmt = stack.pop();
        return crtStmt.execute(state);
    }

    public void allStep() throws MyException {
        PrgState prg = repo.getCrtPrg();

        repo.logPrgStateExec();
        display(prg);

        while (!prg.getExeStack().isEmpty()) {
            oneStep(prg);

            // Log the state before garbage collection
            repo.logPrgStateExec();

            // Remove unreachable heap locations
            prg.getHeap().setContent(
                    GarbageCollector.safeGarbageCollector(
                            GarbageCollector.getAddrFromSymTable(
                                    prg.getSymTable().toMap().values()
                            ),
                            prg.getHeap().getContent()
                    )
            );

            // Log the state after garbage collection
            repo.logPrgStateExec();

            display(prg);
        }
    }

    private void display(PrgState prg) {
        if (displayFlag) {
            System.out.println(prg);
        }
    }
}

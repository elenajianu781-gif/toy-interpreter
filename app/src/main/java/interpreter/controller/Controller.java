package interpreter.controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.IStack;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;

public class Controller {

    private final IRepository repository;

    public Controller(IRepository repository){
        this.repository=repository;
    }

    public PrgState oneStep(PrgState state) throws MyException{
        IStack<IStmt> stack= state.getExeStack();

        if(stack.isEmpty()){
            throw new MyException("Execution stack is empty");
        }

        IStmt currentStatement= stack.pop();

        return currentStatement.execute(state);
    }
    public void allStep() throws MyException{

        PrgState state=repository.getCurrentProgram();

        while(!state.getExeStack().isEmpty()){
            state=oneStep(state);
        }
        repository.setCurrentProgram(state);
    }
}

package interpreter.repository;
import interpreter.model.PrgState;

public class Repository implements  IRepository {
    private PrgState currentProgram;

    public Repository(PrgState program){
        this.currentProgram=program;
    }

    @Override
    public PrgState getCurrentProgram(){
        return currentProgram;
    }
    @Override
    public void setCurrentProgram(PrgState program) {
        this.currentProgram = program;
    }
}

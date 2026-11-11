
package interpreter.model.stmt;

import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.StringValue;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;

import java.io.IOException;

public record CloseRFileStmt(IExp exp) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {

        if (!(exp.eval(state.getSymTable())
                instanceof StringValue fileName)) {
            throw new StatementException(
                    "closeRFile: expression is not a string"
            );
        }

        var fileTable = state.getFileTable();

        if (!fileTable.isDefined(fileName)) {
            throw new StatementException(
                    "closeRFile: file " + fileName + " is not open"
            );
        }

        try {
            fileTable.lookup(fileName).close();
            fileTable.remove(fileName);

        } catch (IOException e) {
            throw new StatementException(
                    "closeRFile: " + e.getMessage()
            );
        }

        return state;
    }

    @Override
    public String toString() {
        return "closeRFile(" + exp + ")";
    }
}

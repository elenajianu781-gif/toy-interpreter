
package interpreter.model.stmt;

import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import interpreter.model.type.IntType;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;

import java.io.IOException;

public record ReadFileStmt(IExp exp, String varName) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {

        var symTable = state.getSymTable();
        var fileTable = state.getFileTable();

        if (!symTable.isDefined(varName)) {
            throw new StatementException(
                    "readFile: variable " + varName + " is not declared"
            );
        }

        if (!symTable.lookup(varName).getType()
                .equals(new IntType())) {
            throw new StatementException(
                    "readFile: variable must be of type int"
            );
        }

        if (!(exp.eval(symTable) instanceof StringValue fileName)) {
            throw new StatementException(
                    "readFile: expression is not a string"
            );
        }

        if (!fileTable.isDefined(fileName)) {
            throw new StatementException(
                    "readFile: file " + fileName + " is not open"
            );
        }

        try {
            var reader = fileTable.lookup(fileName);
            String line = reader.readLine();

            int value = (line == null)
                    ? 0
                    : Integer.parseInt(line.strip());

            symTable.update(varName, new IntValue(value));

        } catch (IOException | NumberFormatException e) {
            throw new StatementException(
                    "readFile: " + e.getMessage()
            );
        }

        return state;
    }

    @Override
    public String toString() {
        return "readFile(" + exp + ", " + varName + ")";
    }
}

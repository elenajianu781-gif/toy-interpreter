package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public record IfStmt(
        IExp condition,
        IStmt thenStmt,
        IStmt elseStmt
) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {

        IValue value = condition.eval(state.getSymTable());

        if (!(value instanceof BoolValue boolValue)) {
            throw new StatementException(
                    "If condition must be boolean"
            );
        }

        if (boolValue.val()) {
            state.getExeStack().push(thenStmt);
        } else {
            state.getExeStack().push(elseStmt);
        }

        return state;
    }

    @Override
    public String toString() {
        return "if (" + condition + ") then ("
                + thenStmt + ") else (" + elseStmt + ")";
    }
}

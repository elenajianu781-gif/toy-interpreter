
package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

public record WriteHeapStmt(String varName, IExp exp) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        var heap = state.getHeap();

        // Check if the variable exists
        if (!symTable.isDefined(varName)) {
            throw new StatementException(
                    "wH: " + varName + " is not declared"
            );
        }

        // Check if the variable contains a reference
        if (!(symTable.lookup(varName) instanceof RefValue ref)) {
            throw new StatementException(
                    "wH: " + varName + " is not a reference"
            );
        }

        // Check if the address exists
        if (!heap.isDefined(ref.address())) {
            throw new StatementException(
                    "wH: address " + ref.address() + " is not in the heap"
            );
        }

        // Evaluate the expression
        IValue value = exp.eval(symTable, heap);

        // Check if the types match
        if (!value.getType().equals(ref.locationType())) {
            throw new StatementException(
                    "wH: type mismatch, expected " + ref.locationType()
                            + ", got " + value.getType()
            );
        }

        // Update the value at the existing address
        heap.update(ref.address(), value);

        return state;
    }

    @Override
    public String toString() {
        return "wH(" + varName + ", " + exp + ")";
    }
}

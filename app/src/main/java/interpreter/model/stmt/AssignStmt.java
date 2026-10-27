package interpreter.model.stmt;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.IDictionary;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;

public record AssignStmt(
        String name,
        IExp expression
) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException{

        IDictionary<String, IValue> symTable =
                state.getSymTable();

        if(!symTable.isDefined(name)){
            throw new StatementException(
                    "Variable " + name + " is not defined"
            );
        }

        IValue value = expression.eval(symTable);
        IValue oldValue = symTable.lookup(name);

        if(!value.getType().equals(oldValue.getType())){
            throw new StatementException("Type mismatch for variable " + name);
        }

        symTable.update(name,value);
        return state;
    }
    @Override
    public String toString() {
        return name + " = " + expression;
    }

}

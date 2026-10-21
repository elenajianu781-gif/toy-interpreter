
package interpreter.model.exp;

import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;

public class VarExp implements IExp {

    private final String id;

    public VarExp(String id) {
        this.id = id;
    }

    @Override
    public IValue eval(IDictionary<String, IValue> symTable) {
        return symTable.get(id);
    }

    @Override
    public String toString() {
        return id;
    }
}

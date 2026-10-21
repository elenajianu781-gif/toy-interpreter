package interpreter.model.exp;
import interpreter.model.adt.IDictionary;
import interpreter.model.type.BoolType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public class LogicExp implements IExp {
    private final IExp left;
    private final IExp right;
    private final int op;

    public LogicExp(IExp left, IExp right, int op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    @Override
    public IValue eval(IDictionary<String, IValue> symTable) {
        IValue v1 = left.eval(symTable);
        IValue v2 = right.eval(symTable);


        if (!v1.getType().equals(new BoolType()) ||
                !v2.getType().equals(new BoolType())) {
            throw new RuntimeException(
                    "Logical operands must be boolean"
            );
        }
        boolean b1 = ((BoolValue) v1).getValue();
        boolean b2 = ((BoolValue) v2).getValue();

        return switch (op) {
            case 1 -> new BoolValue(b1 && b2);
            case 2 -> new BoolValue(b1 || b2);
            default -> throw new IllegalArgumentException(
                    "Invalid logical operator: " + op
            );
        };

    }

    @Override
    public String toString() {
        String operator = switch (op) {
            case 1 -> "&&";
            case 2 -> "||";
            default -> "?";
        };

        return "(" + left + " " + operator + " " + right + ")";
    }

}

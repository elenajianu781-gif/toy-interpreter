package interpreter.model.exp;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.type.IntType;

public class ArithExp implements IExp {

    private final IExp left;
    private final IExp right;
    private final int op;

    public ArithExp(IExp left, IExp right, int op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }
    @Override
    public IValue eval(IDictionary<String, IValue> symTable) {
        IValue v1 = left.eval(symTable);
        IValue v2 = right.eval(symTable);

        if (!v1.getType().equals(new IntType()) ||
                !v2.getType().equals(new IntType())) {
            throw new RuntimeException(
                    "Arithmetic operands must be integers"
            );
        }
        int n1 = ((IntValue) v1).getValue();
        int n2 = ((IntValue) v2).getValue();

        return switch (op) {
            case 1 -> new IntValue(n1 + n2);
            case 2 -> new IntValue(n1 - n2);
            case 3 -> new IntValue(n1 * n2);
            case 4 -> {
                if (n2 == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                yield new IntValue(n1 / n2);
            }
            default -> throw new IllegalArgumentException(
                    "Invalid arithmetic operator: " + op
            );
        };

    }
        @Override
        public String toString () {
            String operator = switch (op) {
                case 1 -> "+";
                case 2 -> "-";
                case 3 -> "*";
                case 4 -> "/";
                default -> "?";
            };

            return "(" + left + " " + operator + " " + right + ")";
        }
    }

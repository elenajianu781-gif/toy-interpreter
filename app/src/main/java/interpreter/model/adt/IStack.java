package interpreter.model.adt;

public interface IStack<T> {
    void push(T value);
    T pop();
    boolean isEmpty();
}

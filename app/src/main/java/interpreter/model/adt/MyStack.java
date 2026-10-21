package interpreter.model.adt;

import java.util.Stack;
import java.util.EmptyStackException;

public class MyStack<T> implements IStack<T> {
    private final Stack<T> stack;

    public MyStack() {
        stack = new Stack<>();
    }

    @Override
    public void push(T value) {
        stack.push(value);
    }

    @Override
    public T pop() {
        if (stack.isEmpty()) {
            throw new EmptyStackException();
        }
        return stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}

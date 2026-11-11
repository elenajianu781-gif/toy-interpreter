
package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.*;

public class MyStack<T> implements IStack<T> {

    private final Deque<T> stack = new ArrayDeque<>();

    @Override
    public void push(T value) {
        stack.push(value);
    }

    @Override
    public T pop() throws ADTException {
        if (stack.isEmpty()) {
            throw new ADTException("pop from an empty stack");
        }

        return stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public List<T> toList() {
        List<T> elements = new ArrayList<>(stack);
        Collections.reverse(elements);
        return elements;
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}

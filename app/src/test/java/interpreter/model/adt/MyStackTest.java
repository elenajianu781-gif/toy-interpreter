
package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyStackTest {

    @Test
    void testLIFO() throws ADTException {
        IStack<Integer> stack = new MyStack<>();

        stack.push(10);
        stack.push(20);
        stack.push(30);

        assertEquals(30, stack.pop());
        assertEquals(20, stack.pop());
        assertEquals(10, stack.pop());
    }

    @Test
    void testIsEmpty() throws ADTException {
        IStack<Integer> stack = new MyStack<>();

        assertTrue(stack.isEmpty());

        stack.push(10);
        assertFalse(stack.isEmpty());

        stack.pop();
        assertTrue(stack.isEmpty());
    }

    @Test
    void testPopEmptyStack() {
        IStack<Integer> stack = new MyStack<>();

        ADTException exception = assertThrows(
                ADTException.class,
                stack::pop
        );

        assertEquals(
                "pop from an empty stack",
                exception.getMessage()
        );
    }
}

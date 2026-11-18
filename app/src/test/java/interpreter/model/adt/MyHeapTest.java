package interpreter.model.adt;
import interpreter.exceptions.ADTException;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyHeapTest {
    @Test
    void allocateReturnsConsecutiveAddress(){
        IHeap heap= new MyHeap();

        assertEquals(1,heap.allocate(new IntValue(10)));
        assertEquals(2,heap.allocate(new IntValue(20)));
        assertEquals(3,heap.allocate(new IntValue(30)));
    }
    @Test
    void getReturnsStoredValue() throws ADTException{
        IHeap heap =new MyHeap();
        heap.allocate(new IntValue(20));
        assertEquals(new IntValue(20),heap.get(1));
    }

    @Test
    void updateChangeStoredValue() throws ADTException{
        IHeap heap =new MyHeap();
        heap.allocate(new IntValue(20));
        heap.update(1,new IntValue(30));

        assertEquals(new IntValue(30),heap.get(1));
    }

    @Test
    void missingAddressThrowsException() {
        IHeap heap = new MyHeap();

        assertThrows(ADTException.class, () -> heap.get(10));

        assertThrows(
                ADTException.class,
                () -> heap.update(10, new IntValue(5))
        );
    }
}

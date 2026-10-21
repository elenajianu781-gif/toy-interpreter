package interpreter.model.adt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyListTest {
    @Test
    void testAddAndGet(){
        IList<Integer> list=new MyList<>();
        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(10,list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    void testSize(){
        IList<String> list=new MyList<>();

        assertEquals(0,list.size());

        list.add("hello");
        list.add("world");

        assertEquals(2,list.size());
    }
    @Test
    void testIsEmpty(){
        IList<Integer> list=new MyList<>();

        assertTrue(list.isEmpty());

        list.add(5);

        assertFalse(list.isEmpty());
    }
    @Test
    void testInvalidIndex(){
        IList<Integer> list=new MyList<>();

        list.add(10);
        list.add(20);

        assertThrows(
                IndexOutOfBoundsException.class, () -> list.get(5)
        );
    }
}

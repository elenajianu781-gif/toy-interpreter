package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.adt.MyHeap;
import interpreter.model.type.IntType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class ReadHeadExpTest {

    private IDictionary<String,IValue> symTable;
    private IHeap heap;

    @BeforeEach
    void setUp(){
        symTable=new MyDictionary<>();
        heap=new MyHeap();
    }

    @Test
    void readHeapReturnsStoredValue() throws Exception {
       int address= heap.allocate(new IntValue(20));

       IExp exp=new ReadHeapExp(
               new ValueExp(new RefValue(address,new IntType()))
       );

        assertEquals(new IntValue(20),
                exp.eval(symTable, heap));
    }

    @Test
    void readHeapWitNonReferenceThrows(){
        IExp exp=new ReadHeapExp( new ValueExp(new IntValue(20)));


        assertThrows(ExpressionException.class,
                () -> exp.eval(symTable, heap));
    }

    @Test
    void readHeapWithMissingAddressThrows() {
        IExp exp = new ReadHeapExp(
                new ValueExp(new RefValue(10, new IntType()))
        );

        assertThrows(ExpressionException.class,
                () -> exp.eval(symTable, heap));
    }
}

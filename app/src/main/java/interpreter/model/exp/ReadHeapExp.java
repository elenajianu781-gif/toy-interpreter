package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IHeap;
import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

public record ReadHeapExp(IExp exp) implements IExp {
     @Override
     public IValue eval(IDictionary<String, IValue> symTable,
                        IHeap heap) throws MyException {
         // Evaluate the expression
         IValue value=exp.eval(symTable,heap);

         // Check if the result is a reference
         if (!(value instanceof RefValue ref)) {
             throw new ExpressionException(
                     "rH: expression is not a reference"
             );
         }

         // Check if the address exists in the heap
         if (!heap.isDefined(ref.address())) {
             throw new ExpressionException(
                     "rH: address " + ref.address()
                             + " is not in the heap"
             );
         }
         // Return the value stored at that address
         return heap.get(ref.address());
     }

     @Override
     public String toString() {
         return "rH(" + exp + ")";
     }
}
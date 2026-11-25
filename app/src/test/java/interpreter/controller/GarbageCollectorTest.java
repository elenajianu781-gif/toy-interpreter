package interpreter.controller;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
public class GarbageCollectorTest {
    // State of Ex9 after new(v, 30)
    private final List<IValue> symTable = List.of(
            new RefValue(3, new IntType()),
            new RefValue(2, new RefType(new IntType()))
    );

    private final Map<Integer, IValue> heap = Map.of(
            1, new IntValue(20),
            2, new RefValue(1, new IntType()),
            3, new IntValue(30),
            4, new IntValue(7)
    );

    @Test
    void getsAddressesFromSymTable() {
        Set<Integer> roots =
                GarbageCollector.getAddrFromSymTable(symTable);

        assertEquals(Set.of(2, 3), roots);
    }

    @Test
    void unsafeCollectorRemovesIndirectReference() {
        Set<Integer> roots =
                GarbageCollector.getAddrFromSymTable(symTable);

        Map<Integer, IValue> result =
                GarbageCollector.unsafeGarbageCollector(
                        roots, heap
                );

        assertEquals(Set.of(2, 3), result.keySet());
    }

    @Test
    void safeCollectorKeepsIndirectReferences() {
        Set<Integer> roots =
                GarbageCollector.getAddrFromSymTable(symTable);

        Map<Integer, IValue> result =
                GarbageCollector.safeGarbageCollector(
                        roots, heap
                );

        assertEquals(Set.of(1, 2, 3), result.keySet());
        assertEquals(new IntValue(20), result.get(1));
    }

    @Test
    void reachableAddressesFindsIndirectLocations() {
        Set<Integer> result =
                GarbageCollector.reachableAddresses(
                        Set.of(2, 3), heap
                );

        assertEquals(Set.of(1, 2, 3), result);
    }

    @Test
    void safeCollectorKeepsOnlyReachableLocations() {

        // Exercise 2 from Lab 8
        Map<Integer, IValue> testHeap = Map.of(
                1, new IntValue(7),
                2, new IntValue(8),
                3, new RefValue(2, new IntType()),
                4, new RefValue(1, new IntType()),
                5, new RefValue(4, new RefType(new IntType()))
        );

        Map<Integer, IValue> result =
                GarbageCollector.safeGarbageCollector(
                        Set.of(3), testHeap
                );

        assertEquals(Set.of(2, 3), result.keySet());
    }

}

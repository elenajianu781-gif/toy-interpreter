package interpreter.controller;

import interpreter.model.value.IValue;
import interpreter.model.value.RefValue;

import java.util.*;
import java.util.stream.Collectors;

public final class GarbageCollector {
    private GarbageCollector() {}

    /** The addresses that occur in the RefValues of the SymTable. */
    public static Set<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).address())
                .collect(Collectors.toSet());
    }

    /** UNSAFE: keeps only the heap entries referred to directly from the SymTable. */
    public static Map<Integer, IValue> unsafeGarbageCollector(Set<Integer> symTableAddr,
                                                              Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    //Find all the addresses reachable directly or inidrectly
    public static Set<Integer> reachableAddresses(Set<Integer> roots,Map<Integer,IValue> heap){
        Set<Integer> visited=new HashSet<>();
        Deque<Integer> worklist=new ArrayDeque<>(roots);

        while(!worklist.isEmpty()){
            int address= worklist.removeFirst();

            if(!heap.containsKey(address) || !visited.add(address)){
                continue;
            }

            IValue value=heap.get(address);

            if(value instanceof  RefValue ref){
                worklist.addLast(ref.address());
            }
        }
        return visited;
    }

    // Safe: keeps all reachable heap locations
    public static Map<Integer,IValue> safeGarbageCollector(
            Set<Integer> symTableAddr,Map<Integer,IValue> heap
    ){
        Set<Integer> reachable=reachableAddresses(symTableAddr,heap);

        return heap.entrySet().stream()
                .filter(e->reachable.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }
}
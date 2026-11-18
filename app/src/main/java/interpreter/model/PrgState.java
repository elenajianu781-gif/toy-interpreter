package interpreter.model;
import interpreter.model.adt.*;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;
import java.io.BufferedReader;
import java.util.TreeMap;

import interpreter.model.adt.MyStack;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyHeap;

import interpreter.model.value.StringValue;

public class PrgState {

    private final IStack<IStmt> exeStack;
    private final IDictionary<String, IValue> symTable;
    private final IList<IValue> out;
    private final IStmt originalProgram;
    private IDictionary<StringValue, BufferedReader> fileTable;
    private final IHeap heap;

    public PrgState(
            IStack<IStmt> exeStack,
            IDictionary<String, IValue> symTable,
            IList<IValue> out,
            IDictionary<StringValue, BufferedReader> fileTable,
            IHeap heap,
            IStmt originalProgram

    ) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.originalProgram = originalProgram;

        exeStack.push(originalProgram);
    }
    public IStack<IStmt> getExeStack(){
        return exeStack;
    }
    public IDictionary<String, IValue> getSymTable() {
        return symTable;
    }
    public IDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public IList<IValue> getOut() {
        return out;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }



    public static PrgState of(IStmt program) {
        return new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new MyDictionary<>(),
                new MyHeap(),
                program
        );
    }


    @Override
    public String toString() {
        return "ExeStack: " + exeStack
                + "\nSymTable: " + symTable
                + "\nOut: " + out
                + "\nFileTable: " + fileTable;
    }


    public String toLogString() {
        var sb = new StringBuilder();

        sb.append("ExeStack:\n");
        exeStack.toList().forEach(s ->
                sb.append(s).append('\n')
        );

        sb.append("SymTable:\n");
        symTable.toMap().forEach((k, v) ->
                sb.append(k).append(" --> ").append(v).append('\n')
        );

        sb.append("Out:\n");
        out.toList().forEach(v ->
                sb.append(v).append('\n')
        );

        sb.append("FileTable:\n");
        fileTable.toMap().keySet().forEach(f ->
                sb.append(f.val()).append('\n')
        );

        sb.append("Heap:\n");

        new TreeMap<>(heap.getContent()).forEach((address, value) ->
                sb.append(address)
                        .append(" --> ")
                        .append(value)
                        .append("\n")
        );

        return sb.toString();
    }
    public IHeap getHeap() {
        return heap;
    }


}


package interpreter.model.stmt;

import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.StringType;
import interpreter.model.value.*;
import interpreter.model.type.IntType;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileStmtTest {

    @TempDir
    Path dir;

    private PrgState state;
    private ValueExp file;

    @BeforeEach
    void setUp() throws Exception {
        Path path = dir.resolve("numbers.in");
        Files.writeString(path, "7\n");

        file = new ValueExp(
                new StringValue(path.toString())
        );

        state = PrgState.of(new NopStmt());

        new VarDeclStmt("x", new IntType()).execute(state);
    }

    @Test
    void readGivesNextNumberThenZeroAtEnd()
            throws MyException {

        new OpenRFileStmt(file).execute(state);

        new ReadFileStmt(file, "x").execute(state);
        assertEquals(
                new IntValue(7),
                state.getSymTable().lookup("x")
        );

        new ReadFileStmt(file, "x").execute(state);
        assertEquals(
                new IntValue(0),
                state.getSymTable().lookup("x")
        );

        new CloseRFileStmt(file).execute(state);
    }

    @Test
    void openingSameFileTwiceThrows()
            throws MyException {

        new OpenRFileStmt(file).execute(state);

        assertThrows(
                StatementException.class,
                () -> new OpenRFileStmt(file).execute(state)
        );

        new CloseRFileStmt(file).execute(state);
    }


    @Test
    void openingFileAddsItToFileTable() throws MyException {
        new OpenRFileStmt(file).execute(state);

        assertTrue(state.getFileTable().isDefined(
                (StringValue) file.eval(state.getSymTable())
        ));

        new CloseRFileStmt(file).execute(state);
    }

    @Test
    void openingNonStringThrows() {
        var invalidFile = new ValueExp(new IntValue(10));

        assertThrows(
                StatementException.class,
                () -> new OpenRFileStmt(invalidFile).execute(state)
        );
    }

    @Test
    void openingMissingFileThrows() {
        var missingFile = new ValueExp(
                new StringValue(dir.resolve("missing.in").toString())
        );

        assertThrows(
                StatementException.class,
                () -> new OpenRFileStmt(missingFile).execute(state)
        );
    }


    @Test
    void readingUndeclaredVariableThrows() throws MyException {
        new OpenRFileStmt(file).execute(state);

        assertThrows(
                StatementException.class,
                () -> new ReadFileStmt(file, "y").execute(state)
        );

        new CloseRFileStmt(file).execute(state);
    }

    @Test
    void readingNonIntegerVariableThrows() throws MyException {
        new VarDeclStmt("s", new StringType()).execute(state);
        new OpenRFileStmt(file).execute(state);

        assertThrows(
                StatementException.class,
                () -> new ReadFileStmt(file, "s").execute(state)
        );

        new CloseRFileStmt(file).execute(state);
    }

    @Test
    void readingUnopenedFileThrows() {
        assertThrows(
                StatementException.class,
                () -> new ReadFileStmt(file, "x").execute(state)
        );
    }

    @Test
    void readingNonIntegerLineThrows() throws Exception {
        Path invalidPath = dir.resolve("invalid.in");
        Files.writeString(invalidPath, "hello\n");

        var invalidFile = new ValueExp(
                new StringValue(invalidPath.toString())
        );

        new OpenRFileStmt(invalidFile).execute(state);

        assertThrows(
                StatementException.class,
                () -> new ReadFileStmt(invalidFile, "x").execute(state)
        );

        new CloseRFileStmt(invalidFile).execute(state);
    }



    @Test
    void closingUnopenedFileThrows() {
        assertThrows(
                StatementException.class,
                () -> new CloseRFileStmt(file).execute(state)
        );
    }


    @Test
    void closingFileRemovesItFromFileTable() throws MyException {
        new OpenRFileStmt(file).execute(state);

        StringValue fileName =
                (StringValue) file.eval(state.getSymTable());

        assertTrue(state.getFileTable().isDefined(fileName));

        new CloseRFileStmt(file).execute(state);

        assertFalse(state.getFileTable().isDefined(fileName));
    }

}

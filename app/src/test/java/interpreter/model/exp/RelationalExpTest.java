package interpreter.model.exp;

import interpreter.model.adt.MyDictionary;
import interpreter.model.value.*;
import interpreter.exceptions.ExpressionException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RelationalExpTest {

    @ParameterizedTest
    @CsvSource({
            "LT, 3, 5, true",
            "LT, 5, 3, false",
            "LE, 3, 3, true",
            "LE, 5, 3, false",
            "EQ, 3, 3, true",
            "EQ, 3, 5, false",
            "NE, 3, 5, true",
            "NE, 3, 3, false",
            "GT, 5, 3, true",
            "GT, 3, 5, false",
            "GE, 5, 5, true",
            "GE, 3, 5, false"
    })
    void testRelationalOperators(
            RelOp op,int a,int b,boolean expected

    )throws Exception{

        var exp=new RelationalExp(
                op,
                new ValueExp(new IntValue(a)),
                new ValueExp(new IntValue(b))
        );

        assertEquals(
                new BoolValue(expected),
                exp.eval(new MyDictionary<>())
        );
    }

    @Test
    void testNonIntegerOperand() {
        var exp = new RelationalExp(
                RelOp.LT,
                new ValueExp(new StringValue("hello")),
                new ValueExp(new IntValue(5))
        );

        assertThrows(
                ExpressionException.class,
                () -> exp.eval(new MyDictionary<>())
        );
    }


}

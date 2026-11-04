package interpreter.model.type;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class StringTypeTest {

    @Test
    void defaultValueIsEmptyString(){
        StringType type=new StringType();

        assertEquals(new StringValue(""),type.defaultValue());
    }

    @Test
    void equalStringValuesHaveSameHashCode(){
        StringValue first=new StringValue("test.in");
        StringValue second=new StringValue("test.in");

        assertEquals(first,second);
        assertEquals(first.hashCode(),second.hashCode());
    }

    @Test
    void differentStringValuesAreNotEqual() {
        StringValue first = new StringValue("test.in");
        StringValue second = new StringValue("other.in");

        assertNotEquals(first, second);
    }

}

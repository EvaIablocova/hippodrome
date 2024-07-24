import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mockStatic;

public class HorseTest {

    @Test
    public void nullNameException(){
        assertThrows(IllegalArgumentException.class,() -> new Horse (null, 1, 1));
    }

    @Test
    public void nullNameMessage(){
        try {
            new Horse(null, 1, 1);
            fail();
        }
        catch(IllegalArgumentException e){
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource (strings = {"", "  ", "\t\t","\n\n\n\n\n"})
    public void blankNameException(String name){

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,() -> new Horse (name, 1, 1));;

        assertEquals("Name cannot be blank.", e.getMessage());
    }

    @Test
    public void getName() throws NoSuchFieldException, IllegalAccessException {
        String stringName = "qwer";
        Horse horse = new Horse(stringName, 1, 1);

        Field name = Horse.class.getDeclaredField("name");
        name.setAccessible(true);
        String nameValue = (String) name.get(horse);
        assertEquals(stringName, nameValue);
    }

    @Test
    public void getSpeed() throws NoSuchFieldException, IllegalAccessException {
        double expectedSpeed = 443;
        Horse horse = new Horse("qwer", expectedSpeed, 1);

        assertEquals(expectedSpeed, horse.getSpeed());
    }

    @Test
    public void getDistance(){
        Horse horse = new Horse("qwer", 1, 228);

        assertEquals(228, horse.getDistance());
    }

    @Test
    public void zeroDistanceByDefault(){
        Horse horse = new Horse("qwer", 1);

        assertEquals(0, horse.getDistance());
    }

    @Test
    public void moveUsesGetRandom(){
        try(MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)){
            new Horse("qwer", 31, 283).move();

            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
            //can not mix matcher with double - 0.2 and anyDouble():
//            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, anyDouble()));
            mockedStatic.verify(() -> Horse.getRandomDouble(anyDouble(), anyDouble()));

        }
    }

    @ParameterizedTest
    @ValueSource (doubles = {0.1, 0.2, 0.5, 0.9, 1.0, 999.999, 0.0})
    void move(double random){
        try(var mokedStatic = mockStatic(Horse.class)){
            Horse horse = new Horse("qwerty", 31, 283);
            mokedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);

            horse.move();

            assertEquals(283+31*random, horse.getDistance());

        }
    }

}

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HippodromeTest {

    @Test
    public void emptyHorsesException(){
        assertThrows(IllegalArgumentException.class, ()-> new Hippodrome(new ArrayList<>()));
    }

    @Test
    public void getHorses(){
        List<Horse> horses = new ArrayList<>();
        for (int i = 1; i < 30; i++) {
            horses.add(new Horse(""+ i, i, i));
        }

        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals (horses, hippodrome.getHorses());
    }

//    @Test
//    public void move(){
//        //AAA
//        //arrange
//        List<Horse> horses = new ArrayList<>();
//
//        for (int i = 1; i < 50; i++) {
//            horses.add(mock(Horse.class));
//        }
//
//        //act
//        new Hippodrome(horses).move();
//
//        //assert
//        for(Horse horse:horses){
//            verify(horse).move();
//        }
//    }

    @Test
    public void getWinner(){
        Horse horse1 = new Horse("qwe1", 1, 2.9999);
        Horse horse2 = new Horse("qwe2", 1, 2);
        Horse horse3 = new Horse("qwe3", 1, 3);
        Horse horse4 = new Horse("qwe4", 1, 1);
        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3, horse4));

        assertSame(horse3, hippodrome.getWinner());
    }



}

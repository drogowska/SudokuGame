package pl.first.firstjava;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoxTest {

    @Test
    public void verifyBoxPositiveTest() {
        List<SudokuField> fields = Arrays.asList(new SudokuField[81]);
        int x = 0;
        for(int i = 0; i < 81; i++) {
            fields.set(i, new SudokuField(0));
        }
        int value = 1;
        for(int i = 0; i < 3; i++) {
            for(int j = 0;j < 3; j++){
                fields.set(x, new SudokuField(value));
                x++;
                value++;
            }
            x += 6;
        }
        System.out.print(fields);
        SudokuBox box = new SudokuBox(fields,0);
        assertTrue(box.verify());
    }

    @Test
    public void verifyBoxNegativeTest() {
        List<SudokuField> fields = Arrays.asList(new SudokuField[81]);
        for(int i = 0; i < 81; i++) {
            fields.set(i, new SudokuField(0));
        }
        SudokuBox box = new SudokuBox(fields,0);
        assertFalse(box.verify());
    }

    @Test
    public void hashCodeTest() {
        List<SudokuField> fields = Arrays.asList(new SudokuField[81]);
        for(int i = 0; i < 81; i++) {
            fields.set(i, new SudokuField(0));
        }
        SudokuBox box = new SudokuBox(fields,0);
        SudokuBox box1 =new SudokuBox(fields,0);
        assertEquals(box.hashCode(),box1.hashCode());
    }

    @Test
    public void equalsTest() {
        List<SudokuField> fields = Arrays.asList(new SudokuField[81]);
        for(int i = 0; i < 81; i++) {
            fields.set(i, new SudokuField(0));
        }
        SudokuBox box = new SudokuBox(fields,0);
        SudokuBox box1 = (SudokuBox) box.clone();
        assertTrue(box.equals(box));
        assertFalse(box.equals(null));
        assertTrue(box.equals(box1));
    }

    @Test
    public void cloneTest()  {
        List<SudokuField> fields = Arrays.asList(new SudokuField[81]);
        for(int i = 0; i < 81; i++) {
            fields.set(i, new SudokuField(0));
        }
        SudokuBox box = new SudokuBox(fields,0);
        SudokuBox box1 = (SudokuBox) box.clone();
        assertTrue(box1.equals(box));
    }
}
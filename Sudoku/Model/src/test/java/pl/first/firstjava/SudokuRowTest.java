package pl.first.firstjava;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuRowTest {

    @Test
    public void verifyRowPositiveTest() {
        List<SudokuField> fields = Arrays.asList(new SudokuField[81]);
        for( int i = 0; i < 81; i++) {
            fields.set(i, new SudokuField(i+1));
        }
        SudokuRow row = new SudokuRow(fields,0);
        assertTrue(row.verify());
    }
    @Test
    public void verifyRowNegativeTest() {
        List<SudokuField> fields = Arrays.asList(new SudokuField[81]);
        for( int i = 0; i < 81; i++) {
            fields.set(i, new SudokuField(0));
        }
        SudokuRow row = new SudokuRow(fields,0);
        assertFalse(row.verify());
    }

    @Test
    public void equalsTest() {
        List<SudokuField> fields = Arrays.asList(new SudokuField[81]);
        List<SudokuField> fields1 = Arrays.asList(new SudokuField[81]);
        for( int i = 0; i < 81; i++) {
            fields.set(i, new SudokuField(0));
            fields1.set(i,new SudokuField(i));
        }
        SudokuRow row = new SudokuRow(fields,0);
        SudokuRow row1 = (SudokuRow) row.clone();
        assertTrue(row.equals(row));
        assertFalse(row.equals(null));
        assertTrue(row.equals(row1));
        assertFalse(row.equals(new SudokuField()));
        assertTrue(row.equals(row1));


    }

    @Test
    public void hashCodeTest() {
        List<SudokuField> fields = Arrays.asList(new SudokuField[81]);
        for( int i = 0; i < 81; i++) {
            fields.set(i, new SudokuField(0));
        }
        SudokuRow row = new SudokuRow(fields,0);
        SudokuRow row1 = (SudokuRow) row.clone();
        assertEquals(row.hashCode(),row1.hashCode());

    }

    @Test
    public void toStringTest() {
        List<SudokuField> fields = Arrays.asList(new SudokuField[81]);
        for( int i = 0; i < 81; i++) {
            fields.set(i, new SudokuField(0));
        }
        SudokuRow row = new SudokuRow(fields,0);
        System.out.println(row.toString());
        String test = "SudokuRow{fields=[SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, " +
                "SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, " +
                "SudokuField{value=0}, SudokuField{value=0}]}";
        assertEquals(row.toString(), test);
    }

    @Test
    public void CloneTest()  {
        List<SudokuField> fields = Arrays.asList(new SudokuField[81]);
        for( int i = 0; i < 81; i++) {
            fields.set(i, new SudokuField(0));
        }
        SudokuRow row = new SudokuRow(fields,0);
        SudokuRow row1 = (SudokuRow) row.clone();
        assertTrue(row.equals(row1));
    }
}
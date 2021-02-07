package pl.first.firstjava;

import exceptions.BadFieldValueException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuColumnTest {

    @Test
    public void verifyColumnPositiveTest() {
        List<SudokuField> fields = Arrays.asList(new SudokuField[81]);
        int x = 0;
        for( int i = 0; i < 9; i++) {
           fields.set(x, new SudokuField(i+1));
           x+=9;
        }
        SudokuColumn column = new SudokuColumn(fields,0);
        assertTrue(column.verify());
    }

    @Test
    public void verifyColumnNegativeTest() {
        List<SudokuField> fields = Arrays.asList(new SudokuField[81]);
        for( int i = 0; i < 81; i++) {
            fields.set(i, new SudokuField(0));
        }
        SudokuColumn column = new SudokuColumn(fields,0);
        assertFalse(column.verify());
    }
    @Test
    public void hashCodeTest() {
        List<SudokuField> fields = Arrays.asList(new SudokuField[81]);
        for( int i = 0; i < 81; i++) {
            fields.set(i, new SudokuField(0));
        }
        SudokuColumn col = new SudokuColumn(fields,0);
        SudokuColumn col1 =new SudokuColumn(fields,0);
        assertEquals(col.hashCode(),col1.hashCode());
    }
    @Test
    public void equalsTest() {
        List<SudokuField> fields = Arrays.asList(new SudokuField[81]);
        for( int i = 0; i < 81; i++) {
            fields.set(i, new SudokuField(0));
        }
        SudokuColumn col = new SudokuColumn(fields,0);
        SudokuColumn col1 = (SudokuColumn) col.clone();
        assertTrue(col.equals(col));
        assertFalse(col.equals(null));
        assertTrue(col.equals(col1));
    }

    @Test
    public void cloneTest() {
        List<SudokuField> fields = Arrays.asList(new SudokuField[81]);
        for( int i = 0; i < 81; i++) {
            fields.set(i, new SudokuField(5));
        }
        SudokuColumn col = new SudokuColumn(fields,0);
        SudokuColumn col1 = (SudokuColumn) col.clone();
        assertTrue(col.equals(col1));
        fields.set(0,new SudokuField(-10));
        assertThrows(BadFieldValueException.class, ()-> new SudokuColumn(fields,0));

    }

}
package pl.first.firstjava;

import exceptions.BadFieldValueException;
import exceptions.WrongObjectException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuFieldTest {

    @Test
    public void getSetCorrectFieldValueTest() {
        SudokuField field = new SudokuField();
        assertTrue(field.setFieldValue(9));
        assertEquals(9, field.getFieldValue());
    }

    @Test
    public void setIncorrectNumberTest() {
        SudokuField field = new SudokuField(5);
        assertThrows(BadFieldValueException.class,()-> field.setFieldValue(-1) );
        assertThrows(BadFieldValueException.class,()-> field.setFieldValue(10) );

    }

    @Test
    public void setTheSameNumberTest() {
        SudokuField field = new SudokuField(5);
        assertFalse(field.setFieldValue(5));
    }

    @Test
    public void equalsTest() {
        SudokuField field = new SudokuField(5);
        SudokuField field2 = null;
        try {
            field2 = (SudokuField) field.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(field.equals(field2));
        SudokuField field1 = new SudokuField(2);
        assertFalse(field.equals(null));
        assertFalse(field.equals(new SudokuBoard(new BacktrackingSudokuSolver())));
        assertTrue(field.equals(field));
        assertFalse(field.equals(field1));
    }

    @Test
    public void hashCodeTest() {
        SudokuField field = new SudokuField(5);
        SudokuField field1 = null;
        try {
            field1 = (SudokuField) field.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertEquals(field.hashCode(), field1.hashCode());

    }

    @Test
    public void toStringTest() {
        String test = "SudokuField{value=2}";
        SudokuField field = new SudokuField(2);
        assertEquals(field.toString(), test);
    }

    @Test
    public void CloneTest() {
        SudokuField field = new SudokuField(2);
        SudokuField field1 = null;
        try {
            field1 = (SudokuField) field.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(field.equals(field1));
        field.setFieldValue(6);
        assertFalse(field.equals(field1));
    }

    @Test
    public void CompareToTest() {
        SudokuField field = new SudokuField(2);
        SudokuField field1 = null;
        try {
            field1 = (SudokuField) field.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertEquals(field.compareTo(field1),0 );
        field.setFieldValue(5);
        assertEquals(field.compareTo(field1),1);
        field.setFieldValue(0);
        assertEquals(field.compareTo(field1),-1);
        assertThrows(WrongObjectException.class,()-> field.compareTo(null) );
    }

    @Test
    public void getChangeableTest(){
        SudokuField field = new SudokuField(2);
        SudokuField field1 = new SudokuField();
        assertEquals(field.getChangeable(), false);
        assertEquals(field1.getChangeable(), true);
    }
}
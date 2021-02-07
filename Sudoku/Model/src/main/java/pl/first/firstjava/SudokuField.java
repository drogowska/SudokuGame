package pl.first.firstjava;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import exceptions.BadFieldValueException;
import exceptions.WrongObjectException;
import java.io.Serializable;
import java.util.ResourceBundle;


//@Entity
public class SudokuField implements Cloneable, Comparable<SudokuField>, Serializable {


    private Long id;

    private int value;

    private boolean isChangeable;

    private String position;

    //@Transient
    private ResourceBundle listBundle = ResourceBundle.getBundle("modelBundle");

    //@ManyToOne
    //@JoinColumn(name = "board_id", referencedColumnName = "id")
    private SudokuBoard sudokuBoard;

    public boolean getChangeable() {
        return isChangeable;
    }

    public void setChangeable() {
        isChangeable = true;
    }

    public SudokuField(int value) {
        this.value = value;
        if (value == 0) {
            this.isChangeable = true;
        } else {
            this.isChangeable = false;
        }
    }

    public SudokuField() {
        value = 0;
        this.isChangeable = true;
    }

    public int getFieldValue() {
        return value;
    }

    public boolean setFieldValue(int value) throws BadFieldValueException {
        if (value <= 9 && value >= 0) {
            if (this.value == value) {
                return false;
            }
            this.value = value;
            isChangeable = value == 0;
            return true;
        }
        throw new BadFieldValueException(listBundle.getString("_wrongFieldValue"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuField field = (SudokuField) o;
        return Objects.equal(value, field.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", value)
                .toString();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int compareTo(SudokuField o) throws  WrongObjectException {
        if (o == null) {
            throw new WrongObjectException(listBundle.getString("_Nptr"));
        }

        if (this.value == o.value) {
            return 0;
        } else if (value > o.value) {
            return 1;
        } else {
            return -1;
        }
    }
}

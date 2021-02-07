package pl.first.firstjava;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public abstract class SudokuElement implements Cloneable, Serializable {

    protected  final List<SudokuField> fields = Arrays.asList(new SudokuField[9]);
    //protected final SudokuField[] fields = new SudokuField[9];

    public SudokuElement() {
        for (int i = 0; i < 9; i++) {
            fields.set(i, new SudokuField());
            //fields[i] = new SudokuField();
        }
    }

    public boolean verify() {

        boolean [] nine = new boolean [10];
        for (int i = 0; i < 9; i++) {
            nine[fields.get(i).getFieldValue()] = true;
        }
        if (nine[0]) {
            return false;
        }
        for (int i = 1; i < 10; i++) {
            if (!nine[i]) {
                return false;
            }
        }
        return true;
        }

    protected int get(int i) {
        return fields.get(i).getFieldValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuElement that = (SudokuElement) o;
        return Objects.equal(fields, that.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(fields);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("fields", fields)
                .toString();
    }

}

package helpers;

import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
//import javafx.scene.layout.Pane;


public class FieldPane extends TextField {

    private int valueX;                      // x is row position
    private int valueY;                      // y is column position
    private boolean isChangeable;       // flag that holds information if field is changeable

    public StringProperty getProperty() {
        return this.textProperty();
    }

    public int converter() {
        if (getText().equals("")) {
            return 0;
        }
        return Integer.parseInt(getText());
    }

    public int getX() {
        return valueX;
    }

    public int getY() {
        return valueY;
    }

    public boolean isChangeable() {
        return isChangeable;
    }

    public FieldPane(final int x, final int y, final String text, final boolean isChangeable) {
        super();
        this.valueX = x;
        this.valueY = y;
        this.setPrefSize(50,50);
        this.getStyleClass().add("text-field");
        /**this.label = new Label();
        *this.getChildren().add(label);
        *this.label.layoutXProperty().bind(this.widthProperty()
          *     .subtract(this.label.widthProperty()).divide(2));
        *this.label.layoutYProperty().bind(this.heightProperty()
         *       .subtract(this.label.heightProperty()).divide(2));
        *this.label.setFont(new Font("Arial", 10));**/
        this.isChangeable = isChangeable;
        this.setDisable(!isChangeable);
        if (!text.equals("0")) {
            this.setPromptText(text);
        }
    }

    public void setLabelText(final String text) {
        String content = text;
        if (content.equals("0")) {
            content = "";
        }
        this.setPromptText(content);
    }
}

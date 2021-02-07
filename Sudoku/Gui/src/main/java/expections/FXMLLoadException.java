package expections;

import pl.first.firstjava.WindowManager;

import java.io.IOException;
import java.util.ResourceBundle;

public class FXMLLoadException extends IOException {

    ResourceBundle labels;

    public FXMLLoadException(final String message) {
        super(message);
        labels = ResourceBundle.getBundle("bundle", WindowManager.getInstance().getCurrentLocale());
    }

    public FXMLLoadException(String message, Throwable cause) {
        super(message, cause);
        labels = ResourceBundle.getBundle("bundle", WindowManager.getInstance().getCurrentLocale());
    }

    public String getLocalizedMessage() {
        return labels.getString(getMessage());
    }
}

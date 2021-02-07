package expections;

import pl.first.firstjava.WindowManager;

import java.util.ResourceBundle;

public class BindingFailedException extends NoSuchMethodException {
    ResourceBundle labels;

    public BindingFailedException(String message) {
        super(message);
        labels = ResourceBundle.getBundle("bundle", WindowManager.getInstance().getCurrentLocale());
    }

    public String getLocalizedMessage() {
        return labels.getString(getMessage());
    }
}

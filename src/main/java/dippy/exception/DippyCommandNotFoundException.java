package dippy.exception;

import dippy.ui.Ui;

public class DippyCommandNotFoundException extends DippyException {
    public static final String MESSAGE = "Oops! I don't know what that means :(\n"
        + Ui.printInstructionsGui();
    public DippyCommandNotFoundException() {
        super(MESSAGE);
    }
}

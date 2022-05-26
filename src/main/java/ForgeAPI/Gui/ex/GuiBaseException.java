package ForgeAPI.Gui.ex;

public class GuiBaseException extends RuntimeException{
    public GuiBaseException() {
        super();
    }
    public GuiBaseException(String message) {
        super(message);
    }

    public GuiBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public GuiBaseException(Throwable cause) {
        super(cause);
    }

    protected GuiBaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

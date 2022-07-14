package io.gitee.zhangsisiyao.ForgeAPI.Annotation.Exception;

public class IllegalClassException extends Exception{
    public IllegalClassException() {
        super();
    }

    public IllegalClassException(String message) {
        super(message);
    }

    public IllegalClassException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalClassException(Throwable cause) {
        super(cause);
    }

    protected IllegalClassException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

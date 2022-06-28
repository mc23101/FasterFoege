package io.gitee.zhangsisiyao.ForgeAPI.Gui.ex;

public class ParamErrorException extends Error{
    public ParamErrorException() {
    }

    public ParamErrorException(String message) {
        super(message);
    }

    public ParamErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamErrorException(Throwable cause) {
        super(cause);
    }

    public ParamErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

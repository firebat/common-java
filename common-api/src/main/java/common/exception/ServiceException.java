package common.exception;

import common.api.json.CodeMessage;

public class ServiceException extends RuntimeException implements CodeMessage {

    private final int code;

    private Object data;

    public ServiceException(String message) {
        this(ERROR, message);
    }

   public ServiceException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}

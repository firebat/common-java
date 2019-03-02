package common.api.json;

public interface CodeMessage {

    int OK = 0;
    int ERROR = -1;

    int getCode();

    String getMessage();

    Object getData();
}

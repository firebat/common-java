package common.rpc.http;

import okhttp3.Call;

public interface Callback<T> {

    default void onFailure(Call call, Exception e) {
    }

    void onResponse(Call call, T object);
}

package io.advantageous.qbit.example.recommendationengine;

import io.advantageous.qbit.reactive.Callback;

public interface UserDataServiceClient {

    void loadUser(Callback<User> callBack, String userId);
}
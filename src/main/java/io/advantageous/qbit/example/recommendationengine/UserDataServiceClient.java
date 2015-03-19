package io.advantageous.qbit.example.recommendationengine;

import io.advantageous.qbit.service.Callback;

public interface UserDataServiceClient {

    void loadUser(Callback<User> callBack, String userId);
}
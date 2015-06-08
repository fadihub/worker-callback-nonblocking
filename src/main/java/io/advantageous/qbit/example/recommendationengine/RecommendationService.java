package io.advantageous.qbit.example.recommendationengine;


import io.advantageous.boon.core.Lists;
import io.advantageous.boon.primitive.SimpleLRUCache;
import io.advantageous.qbit.annotation.QueueCallback;
import io.advantageous.qbit.annotation.QueueCallbackType;
import io.advantageous.qbit.reactive.Callback;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static io.advantageous.qbit.service.ServiceProxyUtils.flushServiceProxy;

public class RecommendationService {


    private final SimpleLRUCache<String, User> users =
            new SimpleLRUCache<>(10_000);


    private BlockingQueue<Runnable> callbacks = new ArrayBlockingQueue<Runnable>(10_000);
    private UserDataServiceClient userDataService;


    public RecommendationService(UserDataServiceClient userDataService) {
        this.userDataService = userDataService;
    }


    public void recommend(final Callback<List<Recommendation>> recommendationsCallback,
                          final String userName) {


        System.out.println("recommend called");

        User user = users.get(userName);

        if (user == null) {
            userDataService.loadUser(
                    loadedUser -> {
                        handleLoadFromUserDataService(loadedUser, recommendationsCallback);
                    }, userName);
        } else {
            recommendationsCallback.accept(runRulesEngineAgainstUser(user));
        }

    }

    /**
     * Handle defered recommendations based on user loads.
     */
    private void handleLoadFromUserDataService(final User loadedUser,
                                               final Callback<List<Recommendation>> recommendationsCallback) {

        /** Add a runnable to the callbacks list. */
        callbacks.add(() -> {
            List<Recommendation> recommendations = runRulesEngineAgainstUser(loadedUser);
            recommendationsCallback.accept(recommendations);
        });
    }


    @QueueCallback({
            QueueCallbackType.EMPTY,
            QueueCallbackType.START_BATCH,
            QueueCallbackType.LIMIT})
    private void handleCallbacks() {

        flushServiceProxy(userDataService);
        Runnable runnable = callbacks.poll();

        while (runnable != null) {
            runnable.run();
            runnable = callbacks.poll();
        }
    }

    /* Fake CPU intensive operation. */
    private List<Recommendation> runRulesEngineAgainstUser(final User user) {
        return Lists.list(new Recommendation("Take a walk"), new Recommendation("Read a book"),
                new Recommendation("Love more, complain less"));
    }

}
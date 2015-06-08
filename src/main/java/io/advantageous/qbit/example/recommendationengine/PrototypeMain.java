package io.advantageous.qbit.example.recommendationengine;

import io.advantageous.boon.core.Sys;
import io.advantageous.qbit.service.ServiceQueue;

import java.util.List;

import static io.advantageous.boon.core.Lists.list;
import static io.advantageous.qbit.service.ServiceBuilder.serviceBuilder;
import static io.advantageous.qbit.service.ServiceProxyUtils.flushServiceProxy;

/**
 * Created by rhightower on 2/20/15.
 */
public class PrototypeMain {

    public static void main(String... args) {



        /* Create user data service and client proxy. */
        ServiceQueue userDataService = serviceBuilder()
                .setServiceObject(new UserDataService())
                .build().startServiceQueue();
        userDataService.startCallBackHandler();
        UserDataServiceClient userDataServiceClient = userDataService
                .createProxy(UserDataServiceClient.class);



        /* Create recommendation service and client proxy. */
        RecommendationService recommendationServiceImpl =
                new RecommendationService(userDataServiceClient);
        ServiceQueue recommendationServiceQueue = serviceBuilder()
                .setServiceObject(recommendationServiceImpl)
                .build().startServiceQueue().startCallBackHandler();

        RecommendationServiceClient recommendationServiceClient =
                recommendationServiceQueue.createProxy(RecommendationServiceClient.class);


        /* Use recommendationServiceClient for 4 recommendations for
          Bob, Joe, Scott and William. */
        List<String> userNames = list("Bob", "Joe", "Scott", "William");

        userNames.forEach( userName->
                        recommendationServiceClient.recommend(recommendations -> {
                            System.out.println("Recommendations for: " + userName);
                            recommendations.forEach(recommendation->
                                    System.out.println("\t" + recommendation));
                        }, userName)
        );

        flushServiceProxy(recommendationServiceClient);
        Sys.sleep(1000);

    }
}
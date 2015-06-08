package io.advantageous.qbit.example.recommendationengine;


import io.advantageous.boon.core.Lists;
import io.advantageous.boon.core.Sys;
import io.advantageous.boon.core.reflection.FastStringUtils;
import io.advantageous.qbit.annotation.QueueCallback;
import io.advantageous.qbit.annotation.QueueCallbackType;
import io.advantageous.qbit.example.recommendationengine.Terminal.Escape;
import io.advantageous.qbit.reactive.Callback;

import java.util.ArrayList;
import java.util.List;


public class UserDataService {

    public static void println(Object message) {
        print((Object)message);
        println();
    }

    public static void println() {
        Sys.println("");
    }

    public static void print(String message) {
        Sys.print(message);
    }

    public static void print(Object message) {
        if(message == null) {
            print((String)"<NULL>");
        } else if(message instanceof char[]) {
            print((String) FastStringUtils.noCopyStringFromChars((char[]) ((char[]) message)));
        } else if(message.getClass().isArray()) {
            print((String) Lists.toListOrSingletonList(message).toString());
        } else {
            print((String)message.toString());
        }

    }

    public static void puts(Object... messages) {
        Object[] var1 = messages;
        int var2 = messages.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Object message = var1[var3];
            print((Object)message);
            if(!(message instanceof Escape)) {
                print((Object)Character.valueOf(' '));
            }
        }

        println();
    }



    private final List<Runnable> userLoadCallBacks = new ArrayList<>(1_000);

    public void loadUser(final Callback<User> callBack, final String userId) {

        puts("UserDataService :: loadUser called", userId);
        userLoadCallBacks.add(
                new Runnable() {
                    @Override
                    public void run() {
                        callBack.accept(new User(userId));
                    }
                });

    }


    @QueueCallback({QueueCallbackType.EMPTY, QueueCallbackType.LIMIT})
    public void pretendToDoIO() {
        Sys.sleep(100);

        if (userLoadCallBacks.size()==0) {
            return;
        }
        for (Runnable runnable : userLoadCallBacks) {
            runnable.run();
        }
        userLoadCallBacks.clear();

    }




}
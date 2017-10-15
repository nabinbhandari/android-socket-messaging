package com.nabinbhandari.android.socketmessaging;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created at 2:37 PM on 10/15/2017.
 *
 * @author Nabin Bhandari
 */

class Utils {

    static final int PORT = 2222;

    static void closeSilently(final Object closeable) {
        if (closeable != null && closeable instanceof Closeable) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ((Closeable) closeable).close();
                    } catch (IOException ignored) {
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

}

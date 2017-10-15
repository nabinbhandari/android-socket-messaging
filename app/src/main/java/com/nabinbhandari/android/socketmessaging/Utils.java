package com.nabinbhandari.android.socketmessaging;

import java.io.Closeable;
import java.io.IOException;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Created at 2:37 PM on 10/15/2017.
 *
 * @author Nabin Bhandari
 */

class Utils {

    static final int PORT = 2222;

    static String getIPAddress() {
        String ipAddress = "localhost";
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (networkInterface.isUp()) {
                    String name = networkInterface.getName();
                    if (name.toLowerCase().equals("wlan0") || name.toLowerCase().equals("rmnet0")) {
                        ipAddress = networkInterface.getInetAddresses().nextElement().getHostAddress();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ipAddress;
    }

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

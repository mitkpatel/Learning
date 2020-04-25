package com.example.equipments.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Class to handel all network state related methods
 * <p>
 * Created by sander on 11-4-2016.
 */
public class NetworkService {

    /**
     * Method to determine the status of the internet network
     *
     * @param context the activity context
     * @return True if the network is available | False if the network is unavailable
     */
    static public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }
}

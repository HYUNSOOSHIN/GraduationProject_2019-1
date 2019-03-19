package com.example.graduationproject_2019_1.Manager;

import android.content.ContentValues;
import android.os.AsyncTask;

public class AsyncManager {

    private static AsyncManager instance = null;

    private AsyncManager() {
    }

    public static AsyncManager getInstance() {
        return (instance == null ? instance = new AsyncManager() : instance);
    }

    public String make(String url, ContentValues values) {
        String result = "";
        AsyncRequestTask task = new AsyncRequestTask(url, values);

        try {
            result = task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            return result;
        }
    }

}


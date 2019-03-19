package com.example.graduationproject_2019_1.Manager;

import android.content.ContentValues;
import android.os.AsyncTask;

public class AsyncRequestTask extends AsyncTask<String, String, String> {

    private String url;
    private ContentValues values;

    public AsyncRequestTask(String url, ContentValues values) {
        this.url = url;
        this.values = values;
    }

    @Override
    protected String doInBackground(String... params) {
        String result;
        HttpManager httpManager = HttpManager.getInstance();

        url += values.get("msrrgn") + "/" + values.get("msrste");

        result = httpManager.request(url);
        if (result == null || result.equals("")) {
            result = "error";
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}

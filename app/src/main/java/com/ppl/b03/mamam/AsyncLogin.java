package com.ppl.b03.mamam;

import android.os.AsyncTask;
import android.util.Log;

import com.ppl.b03.mamam.models.Review;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Think Pad on 12/04/2015.
 */
public class AsyncLogin extends AsyncTask<ArrayList<String>, Void, String> {
    private MainActivity context;
    public AsyncLogin(MainActivity context) {
        this.context = context;
    }
    @Override
    protected String doInBackground(ArrayList<String>... params) {
        String dataReview = null;
        try {
            ArrayList<String> input = params[0];
            String idGoogle = input.get(0);
            String nick = input.get(1);
            HttpGet httppost = new HttpGet("http://ppl-b03.cs.ui.ac.id/login.php?idGoogle="+idGoogle+"&nick="+nick);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httppost);

            // StatusLine stat = response.getStatusLine();
            int status = response.getStatusLine().getStatusCode();
            Log.i("Review","sent");
            if (status == 200) {
                Log.i("Msg","Success");
                HttpEntity entity = response.getEntity();
                dataReview = EntityUtils.toString(entity);
                Log.i("Data",dataReview);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataReview;
    }
    @Override
    protected void onPostExecute(String v){
        context.finishLogin(v);
    }
}

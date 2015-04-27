package com.ppl.b03.mamam;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Think Pad on 12/04/2015.
 */
public class AsyncRateFetch extends AsyncTask<Integer, Void, Double> {
    private DeskripsiKantin context;

    public AsyncRateFetch(DeskripsiKantin context) {
        this.context = context;
    }

    @Override
    protected Double doInBackground(Integer... params) {

        Log.i("IdKantin",params[0]+"");
        Double ret = 0.0;
        try {
            HttpGet httppost = new HttpGet("http://ppl-b03.cs.ui.ac.id/getRating.php?id="+params[0]);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httppost);

            // StatusLine stat = response.getStatusLine();
            int status = response.getStatusLine().getStatusCode();

            if (status == 200) {
                HttpEntity entity = response.getEntity();
                JSONObject jsono = new JSONObject(EntityUtils.toString(entity));
                ret = jsono.getDouble("rating");
                Log.i("Rating",""+ret);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }
    @Override
    protected void onPostExecute(Double d){
        Log.i("RateKantin",d+"");
        context.changeRate(d);
    }
}

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Think Pad on 12/04/2015.
 */
public class AsyncReviewPost extends AsyncTask<Review, Void, Void> {
    private WriteReviewActivity context;
    public AsyncReviewPost(WriteReviewActivity context) {
        this.context = context;
    }
    @Override
    protected Void doInBackground(Review... params) {
        String dataReview = null;
        try {
            HttpGet httppost = new HttpGet("http://ppl-b03.cs.ui.ac.id/writeReview.php?idUser="+params[0].getIdUser()+"&idKantin="+params[0].getIdKantin()+"&nilai="+params[0].getNilai()+"&judul="+params[0].getJudul()+"&tanggapan="+params[0].getTanggapan());
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
        return null;
    }
    @Override
    protected void onPostExecute(Void v){
        context.finishReview();
    }
}

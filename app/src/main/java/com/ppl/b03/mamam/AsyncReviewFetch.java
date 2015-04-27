package com.ppl.b03.mamam;

import android.os.AsyncTask;
import android.util.Log;

import com.ppl.b03.mamam.models.Faculty;
import com.ppl.b03.mamam.models.Kantin;
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Think Pad on 12/04/2015.
 */
public class AsyncReviewFetch extends AsyncTask<Integer, Void, List<Review>> {
    private ReviewListActivity context;
    public AsyncReviewFetch(ReviewListActivity context) {
        this.context = context;
    }
    @Override
    protected List<Review> doInBackground(Integer... params) {
        String dataReview = null;
        try {
            HttpGet httppost = new HttpGet("http://ppl-b03.cs.ui.ac.id/getReview.php?id="+params[0]);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httppost);

            // StatusLine stat = response.getStatusLine();
            int status = response.getStatusLine().getStatusCode();

            if (status == 200) {
                Log.i("Msg","Success");
                HttpEntity entity = response.getEntity();
                dataReview = EntityUtils.toString(entity);
                Log.i("Data",dataReview);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Review> listOfReview = new ArrayList<Review>();
        try {
            JSONObject objectReview = new JSONObject(dataReview);
            JSONArray daftar = objectReview.getJSONArray("daftar");
            for(int i=0; i<daftar.length();i++) {
                JSONObject f = daftar.getJSONObject(i);
                JSONObject item = f.getJSONObject("penilaian");
                int idUser = Integer.parseInt(item.getString("id"));
                double nilai = Double.parseDouble(item.getString("nilai"));
                String judul = item.getString("judul");
                String tanggapan = item.getString("tanggapan");
                listOfReview.add(new Review(judul, tanggapan, idUser, nilai, params[0]));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


//        for(int i=10; i>=0; i--) {
//            listOfFaculty.add(new Faculty(i, "Fakultas "+i));
//        }
        return listOfReview;
    }
    @Override
    protected void onPostExecute(List<Review> list){
        Log.i("Post","Ecexute");
        context.renderList(list);
    }
}

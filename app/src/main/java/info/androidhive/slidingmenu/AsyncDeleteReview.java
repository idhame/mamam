package info.androidhive.slidingmenu;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Think Pad on 12/04/2015.
 */
public class AsyncDeleteReview extends AsyncTask<ArrayList<String>, Void, Void> {
    private MainActivity context;
    public AsyncDeleteReview(MainActivity context) {
        this.context = context;
    }
    @Override
    protected Void doInBackground(ArrayList<String>... params) {
        String dataReview = null;
        try {
            ArrayList<String> input = params[0];
            String id = "(";
            for(int i=0; i<input.size();i++) {
                if(i!=input.size()-1) {
                    id+=input.get(i)+",";
                } else if(i==input.size()-1) {
                    id+=input.get(i);
                }
            }
            id += ")";
            JSONObject json = new JSONObject();
            json.put("id",id);
            Log.i("idYangMauDelete", id);
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams,10000);
            HttpConnectionParams.setSoTimeout(httpParams,10000);
            HttpClient httpclient = new DefaultHttpClient(httpParams);
            String url = "http://ppl-b03.cs.ui.ac.id/deleteReview.php";
            HttpPost request = new HttpPost(url);
            request.setEntity(new ByteArrayEntity(json.toString().getBytes("UTF8")));
            request.setHeader("json",json.toString());
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if(entity!=null) {
                InputStream inputStream = entity.getContent();
                String result = inputStream.toString();

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void v){

        context.finishDeleteReview();
    }
}

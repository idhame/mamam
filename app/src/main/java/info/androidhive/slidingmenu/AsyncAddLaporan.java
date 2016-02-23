package info.androidhive.slidingmenu;

import android.os.AsyncTask;

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
public class AsyncAddLaporan extends AsyncTask<ArrayList<String>, Void, Void> {
    private MainActivity context;
    public AsyncAddLaporan(MainActivity context) {
        this.context = context;
    }
    @Override
    protected Void doInBackground(ArrayList<String>... params) {
        String dataReview = null;
        try {
            ArrayList<String> input = params[0];
            String idUser = input.get(0);
            String idPenilaian = input.get(1);
            JSONObject json = new JSONObject();
            json.put("idUser",idUser);
            json.put("idPenilaian",idPenilaian);
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams,10000);
            HttpConnectionParams.setSoTimeout(httpParams,10000);
            HttpClient httpclient = new DefaultHttpClient(httpParams);
            String url = "http://ppl-b03.cs.ui.ac.id/addLaporan.php";
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
        context.finishAddLaporan();
    }
}

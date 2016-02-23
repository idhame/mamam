package info.androidhive.slidingmenu;

import android.os.AsyncTask;
import android.util.Log;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import info.androidhive.slidingmenu.model.Review;

/**
 * Created by Think Pad on 12/04/2015.
 */
public class AsyncFeedbackPost extends AsyncTask<Review, Void, Void> {
    private MainActivity context;
    public AsyncFeedbackPost(MainActivity context) {
        this.context = context;
    }
    @Override
    protected Void doInBackground(Review... params) {
        String dataReview = null;
        try {
            HttpGet httppost = new HttpGet("http://ppl-b03.cs.ui.ac.id/sendFeedback.php?idUser="+params[0].getIdUser()+"&feedback="+params[0].getTanggapan());
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

//        context.finishReview();
    }
}

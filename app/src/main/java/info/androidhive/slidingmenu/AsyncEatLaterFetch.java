package info.androidhive.slidingmenu;

import android.os.AsyncTask;
import android.util.Log;

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

import info.androidhive.slidingmenu.model.Faculty;
import info.androidhive.slidingmenu.model.Kantin;
import info.androidhive.slidingmenu.model.Review;

/**
 * Created by Think Pad on 12/04/2015.
 */
public class AsyncEatLaterFetch extends AsyncTask<Integer, Void, List<Kantin>> {
    private MainActivity context;
    public AsyncEatLaterFetch(MainActivity context) {
        this.context = context;
    }
    @Override
    protected List<Kantin> doInBackground(Integer... params) {
        String dataKantin = null;
        try {
            HttpGet httppost = new HttpGet("http://ppl-b03.cs.ui.ac.id/getEatLater.php?idUser="+params[0]);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httppost);

            // StatusLine stat = response.getStatusLine();
            int status = response.getStatusLine().getStatusCode();

            if (status == 200) {
                Log.i("Msg","Success");
                HttpEntity entity = response.getEntity();
                dataKantin = EntityUtils.toString(entity);
                Log.i("Data",dataKantin);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Kantin> listOfKantin = new ArrayList<Kantin>();
        try {

            JSONObject objectKantin = new JSONObject(dataKantin);
            JSONArray daftarKantin = objectKantin.getJSONArray("daftar");

            for(int i=0; i<daftarKantin.length();i++){
                JSONObject f = daftarKantin.getJSONObject(i);
                Log.i("Kantinnya",f.toString());
                JSONObject item = f.getJSONObject("kantin");
                int id = Integer.parseInt(item.getString("id"));
                String nama = item.getString("nama");
                String deskripsi = item.getString("deskripsi");
                String menu = item.getString("menu");
                String namapenjual = item.getString("namapenjual");
                int pricerange = item.getInt("pricerange");
                String foto = item.getString("foto");
                int kategori = item.getInt("idkategori");
                int fakultas = item.getInt("idfakultas");
                Kantin kantin = new Kantin(nama, namapenjual, menu, deskripsi, pricerange,kategori,id,foto);
                Log.i("Kantin", nama);
                listOfKantin.add(kantin);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


//        for(int i=10; i>=0; i--) {
//            listOfFaculty.add(new Faculty(i, "Fakultas "+i));
//        }
        return listOfKantin;
    }
    @Override
    protected void onPostExecute(List<Kantin> list){
        Log.i("Post","Ecexute");
        context.renderListEatLater(list);
    }
}

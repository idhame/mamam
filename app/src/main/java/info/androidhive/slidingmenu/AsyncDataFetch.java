package info.androidhive.slidingmenu;

import android.os.AsyncTask;
import android.util.Log;
import info.androidhive.slidingmenu.model.Faculty;
import info.androidhive.slidingmenu.model.Kantin;

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
public class AsyncDataFetch extends AsyncTask<Void, Void, Void> {
    private MainActivity context;
    private static final String FACULTY_CACHE_FILE = "faculty_cache.ser";
    private static final String CANTEEN_CACHE_FILE = "canteen_cache.ser";
    public AsyncDataFetch(MainActivity context) {
        this.context = context;
    }
    @Override
    protected Void doInBackground(Void... params) {

        String dataFakultas = null;
        String dataKantin = null;
        try {
            HttpGet httppost = new HttpGet("http://ppl-b03.cs.ui.ac.id/getAllFakultas.php");
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httppost);

            // StatusLine stat = response.getStatusLine();
            int status = response.getStatusLine().getStatusCode();

            if (status == 200) {
                Log.i("Msg","Success");
                HttpEntity entity = response.getEntity();
                dataFakultas = EntityUtils.toString(entity);
//                Log.i("Data",data);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            HttpGet httppost = new HttpGet("http://ppl-b03.cs.ui.ac.id/getAllKantin.php");
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httppost);
            // StatusLine stat = response.getStatusLine();
            int status = response.getStatusLine().getStatusCode();

            if (status == 200) {
                HttpEntity entity = response.getEntity();
                dataKantin = EntityUtils.toString(entity);
                Log.i("Data",dataKantin);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Faculty> listOfFaculty = new ArrayList<Faculty>();
        List<Kantin> listOfKantin = new ArrayList<Kantin>();
        try {
            JSONObject objectFaculty = new JSONObject(dataFakultas);
            JSONArray daftar = objectFaculty.getJSONArray("daftar");

            for(int i=0; i<daftar.length();i++) {
                JSONObject f = daftar.getJSONObject(i);
                JSONObject item = f.getJSONObject("fakultas");
                listOfFaculty.add(new Faculty(item.getInt("id"), item.getString("fakultas"), item.getDouble("long"), item.getDouble("lat")));
            }

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
                listOfFaculty.get(fakultas-1).addKantin(kantin);
                Log.i("Fakultas", listOfFaculty.get(fakultas-1).getNama());
                listOfKantin.add(kantin);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


//        for(int i=10; i>=0; i--) {
//            listOfFaculty.add(new Faculty(i, "Fakultas "+i));
//        }
        try {
            FileOutputStream fos = context.openFileOutput(FACULTY_CACHE_FILE, context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(listOfFaculty);
            oos.close();
            fos.close();
            fos = context.openFileOutput(CANTEEN_CACHE_FILE, context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(listOfKantin);
            oos.close();
            fos.close();
        } catch (Exception e) {

        }
        return null;
    }
    @Override
    protected void onPostExecute(Void v){
        context.renderFacultyList();
    }
}

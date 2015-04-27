package com.ppl.b03.mamam;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.ppl.b03.mamam.models.Faculty;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CategoryListActivity extends ActionBarActivity {
    private ListView categoryListView;
    private ArrayAdapter categoryItemArrayAdapter;
    private static final String FACULTY_CACHE_FILE = "faculty_cache.ser";
    private List<Faculty> listFacultyRead;
    private GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Test", "test");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        List<Faculty> listFaculty = new ArrayList<Faculty>();
        //read from cache
        renderFacultyList();
//        create dummy
//        for(int i=0;i<=10;i++) {
//            listFaculty.add(new Faculty(i, "Fakultas " + i));
//        }
////        write dummy to cache
//        try {
//            FileOutputStream fos = openFileOutput(FACULTY_CACHE_FILE, MODE_PRIVATE);
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//            oos.writeObject(listFaculty);
//            oos.close();
//            fos.close();
//        } catch (Exception e) {
//
//        }
        new AsyncDataFetch(this).execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_feedback:
                Intent intent = new Intent(CategoryListActivity.this, FeedbackActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_about:
                Intent intent2 = new Intent(CategoryListActivity.this, AboutActivity.class);
                startActivity(intent2);
                return true;
            case R.id.action_logout:
                signOutFromGplus();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void signOutFromGplus() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
        }
        SharedPreferences prefs = getSharedPreferences("codelearn_twitter", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("usernameKey");
        editor.commit();
        Intent intent2 = new Intent(CategoryListActivity.this, MainActivity.class);
        startActivity(intent2);
    }

    public void renderFacultyList() {
        try {
            FileInputStream fis = openFileInput(FACULTY_CACHE_FILE);
            ObjectInputStream ois = new ObjectInputStream(fis);
            listFacultyRead = ( List<Faculty> ) ois.readObject();
            fis.close();
            ois.close();
            categoryItemArrayAdapter = new FacultyAdapter(this, listFacultyRead);
            categoryListView = (ListView) findViewById(R.id.categoryList);
            categoryListView.setAdapter(categoryItemArrayAdapter);
            Log.i("Message","Success");
            categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //TextView t = (TextView) view.findViewById(R.id.facultyTitle);
                    //t.setText("Tweet Clicked");
                    Intent intent = new Intent(CategoryListActivity.this, KantinListActivity.class);
                    Faculty fakultas = listFacultyRead.get(position);
                    intent.putExtra("fakultas", fakultas);
                    Log.i("intent",fakultas.getListKantin().toString());
                    startActivity(intent);
                }
            });
        } catch(Exception e){

        }

    }
}

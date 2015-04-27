package com.ppl.b03.mamam;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ppl.b03.mamam.models.Faculty;
import com.ppl.b03.mamam.models.Kantin;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;


public class KantinListActivity extends ActionBarActivity {
    private List<Kantin> listKantin;
    private ListView categoryListView;
    private ArrayAdapter categoryItemArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Faculty faculty = (Faculty) getIntent().getSerializableExtra("fakultas");
        listKantin = faculty.getListKantin();
        setContentView(R.layout.activity_kantin_list);
        renderFacultyList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kantin_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void renderFacultyList() {
        try {
            categoryItemArrayAdapter = new KantinAdapter(this, listKantin);
            String[] stringArray = new String[10];
            for(int i=0; i < stringArray.length; i++){
                stringArray[i] = "String " + i;
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, stringArray);

            categoryListView = (ListView) findViewById(R.id.kantinList);
            Log.i("Message","Success");
            categoryListView.setAdapter(categoryItemArrayAdapter);
            Log.i("Message","Success");
            categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //TextView t = (TextView) view.findViewById(R.id.facultyTitle);
                    //t.setText("Tweet Clicked");
                    Intent intent = new Intent(KantinListActivity.this, DeskripsiKantin.class);
                    Kantin kantin = listKantin.get(position);
                    intent.putExtra("kantin", kantin);
                    startActivity(intent);
                }
            });
        } catch(Exception e){
            Log.i("Error", "Adapter Error");
        }

    }
}
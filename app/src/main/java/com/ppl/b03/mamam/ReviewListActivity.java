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
import android.widget.TextView;

import com.ppl.b03.mamam.models.Kantin;
import com.ppl.b03.mamam.models.Review;

import java.util.List;


public class ReviewListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);
        final int id = getIntent().getIntExtra("id",0);
        new AsyncReviewFetch(this).execute(id);

        TextView t4 = (TextView) findViewById(R.id.textReview);
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewListActivity.this, WriteReviewActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_review_list, menu);
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

    public void renderList(List<Review> list) {
        try {
            ArrayAdapter categoryItemArrayAdapter = new ReviewAdapter(this, list);
            Log.i("Error","Step1");
            ListView categoryListView = (ListView) findViewById(R.id.kantinList);
            Log.i("Error","Step2");
            categoryListView.setAdapter(categoryItemArrayAdapter);
            Log.i("Error","Step3");
        } catch(Exception e){
            Log.i("Error", "Adapter Error");
        }
    }
}

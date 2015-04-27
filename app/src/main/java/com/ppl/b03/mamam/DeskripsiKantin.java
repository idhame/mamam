package com.ppl.b03.mamam;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.ShareActionProvider;
import android.widget.TextView;

import com.ppl.b03.mamam.models.Faculty;
import com.ppl.b03.mamam.models.Kantin;
import com.ppl.b03.mamam.models.Review;

import java.util.List;


public class DeskripsiKantin extends ActionBarActivity {
    private Intent mShareIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Kantin kantin = (Kantin) getIntent().getSerializableExtra("kantin");
        setContentView(R.layout.activity_deskripsi_kantin);

        new AsyncRateFetch(this).execute(kantin.getId());
        TextView t = (TextView) findViewById(R.id.namaKantin);
        t.setText(kantin.getNama());

        TextView t2 = (TextView) findViewById(R.id.textDeskripsi);
        t2.setText(kantin.getDeskripsi());

        TextView t3 = (TextView) findViewById(R.id.textMenu);
        t3.setText(kantin.getMenu());

        TextView t4 = (TextView) findViewById(R.id.textReview);
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeskripsiKantin.this, ReviewListActivity.class);
                intent.putExtra("id", kantin.getId());
                startActivity(intent);
            }
        });

        mShareIntent = new Intent();
        mShareIntent.setAction(Intent.ACTION_SEND);
        mShareIntent.setType("text/plain");
        mShareIntent.putExtra(Intent.EXTRA_TEXT, "Mamam di "+kantin.getNama()+" yuk");
    }

    public void changeRate(Double rate) {
        TextView t = (TextView) findViewById(R.id.rating);
        t.setText(rate+"");
    }

    public void giveReview() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_deskripsi_kantin, menu);
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Get its ShareActionProvider
        ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        // Connect the dots: give the ShareActionProvider its Share Intent
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(mShareIntent);
        }
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

}

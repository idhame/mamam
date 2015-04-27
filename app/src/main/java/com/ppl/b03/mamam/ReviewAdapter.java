package com.ppl.b03.mamam;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ppl.b03.mamam.models.Kantin;
import com.ppl.b03.mamam.models.Review;

import java.util.List;

/**
 * Created by Think Pad on 11/04/2015.
 */
public class ReviewAdapter extends ArrayAdapter<Review> {
    private LayoutInflater inflater;
    private List<Review> listOfReview;
    public ReviewAdapter(Activity activity, List<Review> items){
        super(activity, R.layout.row_kantin, items);
        listOfReview = items;
        inflater = activity.getWindow().getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Log.i("view","GetView");
        View ret = inflater.inflate(R.layout.row_kantin, parent, false);
        TextView t = (TextView) ret.findViewById(R.id.kantinTitle);
        //t.setText("hai");
        t.setText(listOfReview.get(position).getJudul());

        TextView t2 = (TextView) ret.findViewById(R.id.kantinDeskripsi);
        t2.setText(listOfReview.get(position).getTanggapan());
        return ret;
    }
}

package info.androidhive.slidingmenu;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.List;
import info.androidhive.slidingmenu.model.Review;

/**
 * Created by Think Pad on 11/04/2015.
 */
public class ReviewAdapter extends ArrayAdapter<Review> {
    private LayoutInflater inflater;
    private List<Review> listOfReview;
    private final Activity parentActivity;
    public ReviewAdapter(Activity activity, List<Review> items){
        super(activity, R.layout.row_review, items);
        listOfReview = items;
        inflater = activity.getWindow().getLayoutInflater();
        parentActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent){
        Log.i("view","GetView");
        View ret = inflater.inflate(R.layout.row_review, parent, false);
        TextView t = (TextView) ret.findViewById(R.id.kantinTitle);
        //t.setText("hai");
        t.setText(listOfReview.get(position).getJudul());

        TextView t2 = (TextView) ret.findViewById(R.id.kantinDeskripsi);
        t2.setText(listOfReview.get(position).getTanggapan());
        final int pos = position;
        TextView t3 = (TextView) ret.findViewById(R.id.laporReview);
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                press(listOfReview.get(pos).getId());
            }
        });
        return ret;
    }

    public void press(int idPenilaian) {
        ((MainActivity) parentActivity).addLaporan(idPenilaian);
    }

}

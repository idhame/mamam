package info.androidhive.slidingmenu;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.slidingmenu.model.Review;

/**
 * Created by Think Pad on 11/04/2015.
 */
public class ReviewCheckboxAdapter extends ArrayAdapter<Review> implements CompoundButton.OnCheckedChangeListener {
    private LayoutInflater inflater;
    private List<Review> listOfReview;
    private Activity parentActivity;
    public ReviewCheckboxAdapter(Activity activity, List<Review> items){
        super(activity, R.layout.row_review_checkbox, items);
        this.parentActivity = activity;
        listOfReview = items;
        inflater = activity.getWindow().getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Log.i("view","GetView");
        View ret = inflater.inflate(R.layout.row_review_checkbox, parent, false);
        TextView t = (TextView) ret.findViewById(R.id.kantinTitle);
        //t.setText("hai");
        t.setText(listOfReview.get(position).getJudul());

        TextView t2 = (TextView) ret.findViewById(R.id.kantinDeskripsi);
        t2.setText(listOfReview.get(position).getTanggapan());
        CheckBox cb = (CheckBox) ret.findViewById(R.id.checkBox1);
        cb.setTag(position);
        cb.setChecked(listOfReview.get(position).getChecked());
        cb.setOnCheckedChangeListener(this);
        return ret;
    }

    public boolean isChecked(int position) {
        return listOfReview.get(position).getChecked();
    }

    public void setChecked(int position, boolean isChecked) {
        listOfReview.get(position).setChecked(isChecked);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        listOfReview.get((Integer)buttonView.getTag()).setChecked(isChecked);
        Log.i("Check",listOfReview.get((Integer)buttonView.getTag()).getJudul());
    }

    public void delete() {
        ArrayList<String> delete = new ArrayList<String>();
        for(int i=0; i<listOfReview.size();i++) {
            if(listOfReview.get(i).getChecked()) {
                delete.add(listOfReview.get(i).getId()+"");
            }
        }
        ((MainActivity)parentActivity).deleteReview(delete);
    }

}

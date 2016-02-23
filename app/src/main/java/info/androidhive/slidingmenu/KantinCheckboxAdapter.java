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

import info.androidhive.slidingmenu.model.Kantin;

/**
 * Created by Think Pad on 11/04/2015.
 */
public class KantinCheckboxAdapter extends ArrayAdapter<Kantin> implements CompoundButton.OnCheckedChangeListener {
    private LayoutInflater inflater;
    private List<Kantin> listOfKantin;
    private Activity parentActivity;
    public KantinCheckboxAdapter(Activity activity, List<Kantin> items){
        super(activity, R.layout.row_kantin_checkbox, items);
        listOfKantin = items;
        inflater = activity.getWindow().getLayoutInflater();
        this.parentActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Log.i("view","GetView");
        View ret = inflater.inflate(R.layout.row_kantin_checkbox, parent, false);
        TextView t = (TextView) ret.findViewById(R.id.kantinTitle);
        //t.setText("hai");
        t.setText(listOfKantin.get(position).getNama());

        TextView t2 = (TextView) ret.findViewById(R.id.kantinDeskripsi);
        t2.setText(listOfKantin.get(position).getDeskripsi());

        CheckBox cb = (CheckBox) ret.findViewById(R.id.checkBox1);
        cb.setTag(position);
        cb.setChecked(listOfKantin.get(position).getChecked());
        cb.setOnCheckedChangeListener(this);
        return ret;
    }

    public boolean isChecked(int position) {
        return listOfKantin.get(position).getChecked();
    }

    public void setChecked(int position, boolean isChecked) {
        listOfKantin.get(position).setChecked(isChecked);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        listOfKantin.get((Integer)buttonView.getTag()).setChecked(isChecked);
        Log.i("Check",listOfKantin.get((Integer)buttonView.getTag()).getNama());
    }

    public void delete() {
        ArrayList<String> delete = new ArrayList<String>();
        for(int i=0; i<listOfKantin.size();i++) {
            if(listOfKantin.get(i).getChecked()) {
                delete.add(listOfKantin.get(i).getId()+"");
            }
        }
        ((MainActivity)parentActivity).deleteEatLater(delete);
    }
}

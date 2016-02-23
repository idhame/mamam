package info.androidhive.slidingmenu;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import info.androidhive.slidingmenu.model.Faculty;

import java.util.List;

/**
 * Created by Think Pad on 11/04/2015.
 */
public class FacultyAdapter extends ArrayAdapter<Faculty> {
    private LayoutInflater inflater;
    private List<Faculty> listofFaculty;
    public FacultyAdapter(Activity activity, List<Faculty> items){
        super(activity, R.layout.row_faculty, items);
        listofFaculty = items;
        inflater = activity.getWindow().getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View ret = inflater.inflate(R.layout.row_faculty, parent, false);
        TextView t = (TextView) ret.findViewById(R.id.facultyTitle);
        //t.setText("hai");
        t.setText(listofFaculty.get(position).getNama());
        Log.i("view", listofFaculty.get(position).getNama());
        return ret;
    }

    public List<Faculty> getListofFaculty() {
        return listofFaculty;
    }
}

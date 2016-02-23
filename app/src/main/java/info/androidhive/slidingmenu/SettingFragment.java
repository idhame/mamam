package info.androidhive.slidingmenu;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.List;

import info.androidhive.slidingmenu.model.Faculty;
import info.androidhive.slidingmenu.model.Kantin;

public class SettingFragment extends Fragment {
    private ListView categoryListView;
	public SettingFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        return rootView;
    }

    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        final MainActivity par = (MainActivity)getActivity();
        String[] stringArray = new String[3];
        stringArray[0] = "Tutorial";
        stringArray[1] = "About";
        stringArray[2] = "Send Feedback";
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, stringArray);
        categoryListView = (ListView) getView().findViewById(R.id.settingList);
        categoryListView.setAdapter(arrayAdapter);
        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==1) {
                    ((MainActivity)getActivity()).viewAbout();
                }
                else if(position==0) {
                    par.viewTutorial();
                }
            }
        });
    }

}

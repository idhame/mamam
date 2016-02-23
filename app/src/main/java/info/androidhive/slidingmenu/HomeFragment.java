package info.androidhive.slidingmenu;

import info.androidhive.slidingmenu.model.Faculty;
import info.androidhive.slidingmenu.model.Kantin;

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

import java.util.List;

public class HomeFragment extends Fragment {
    private List<Kantin> listKantin;
    private ListView categoryListView;
    private ArrayAdapter categoryItemArrayAdapter;
	public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }

    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        ((MainActivity)getActivity()).renderFacultyList();
    }

    public void renderList() {
        final List<Faculty> listFacultyRead = ((MainActivity)getActivity()).getListFacultyRead();
        categoryItemArrayAdapter = new FacultyAdapter(getActivity(), listFacultyRead);
        categoryListView = (ListView) getView().findViewById(R.id.categoryList);
        categoryListView.setAdapter(categoryItemArrayAdapter);
        Log.i("Message", "Render2");
        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TextView t = (TextView) view.findViewById(R.id.facultyTitle);
                //t.setText("Tweet Clicked");
//                Intent intent = new Intent(CategoryListActivity.this, KantinListActivity.class);
                Faculty fakultas = listFacultyRead.get(position);
                ((MainActivity)getActivity()).setFacultyKantinView(fakultas);
                ((MainActivity)getActivity()).viewFaculty(fakultas.getNama());
//                intent.putExtra("fakultas", fakultas);
//                Log.i("intent",fakultas.getListKantin().toString());
//                startActivity(intent);
            }
        });
    }
}

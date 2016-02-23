package info.androidhive.slidingmenu;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import info.androidhive.slidingmenu.model.Faculty;
import info.androidhive.slidingmenu.model.Kantin;

public class ListKantinFragment extends Fragment {
    private List<Kantin> listKantin;
    private ListView categoryListView;
    private ArrayAdapter categoryItemArrayAdapter;

	public ListKantinFragment(){}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_kantin, container, false);
        return rootView;
    }

    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        renderList();
    }


    public void renderList() {

        final List<Kantin> listKantin = ((MainActivity)getActivity()).getFacultyKantinView().getListKantin();
        categoryItemArrayAdapter = new KantinAdapter(getActivity(), listKantin);
        categoryListView = (ListView) getView().findViewById(R.id.kantinList);
        categoryListView.setAdapter(categoryItemArrayAdapter);
        Log.i("Message", "Render2");
        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TextView t = (TextView) view.findViewById(R.id.facultyTitle);
                //t.setText("Tweet Clicked");
//                Intent intent = new Intent(CategoryListActivity.this, KantinListActivity.class);
//                Faculty fakultas = listFacultyRead.get(position);
//                intent.putExtra("fakultas", fakultas);
//                Log.i("intent",fakultas.getListKantin().toString());
//                startActivity(intent);
                ((MainActivity)getActivity()).setKantinView(listKantin.get(position));
                ((MainActivity)getActivity()).viewKantin(listKantin.get(position).getNama());

            }
        });
    }
}

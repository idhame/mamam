package info.androidhive.slidingmenu;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;


import java.util.List;

import info.androidhive.slidingmenu.model.Kantin;

public class SearchKantinFragment extends Fragment {
    private List<Kantin> listKantin;
    private ListView categoryListView;
    private ArrayAdapter categoryItemArrayAdapter;

    public SearchKantinFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_kantin, container, false);
        return rootView;
    }

    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        Button btn = (Button) getView().findViewById(R.id.btnSearch);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText keyword = (EditText) getView().findViewById(R.id.fld_keyword);
                ((MainActivity)getActivity()).searchKantin(keyword.getText().toString());
            }
        });
    }

    public void renderList(List<Kantin> list) {

        final List<Kantin> listKantin = list;
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

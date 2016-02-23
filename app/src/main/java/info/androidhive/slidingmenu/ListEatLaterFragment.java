package info.androidhive.slidingmenu;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import info.androidhive.slidingmenu.model.Kantin;

public class ListEatLaterFragment extends Fragment {
    private List<Kantin> listKantin;
    private ListView categoryListView;
    private ArrayAdapter categoryItemArrayAdapter;

	public ListEatLaterFragment(){}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_kantin, container, false);
        ((MainActivity)getActivity()).fetchEatLater();
        setHasOptionsMenu(true);
        return rootView;
    }

    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_eat_later, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_editeatlater:
                ((MainActivity)getActivity()).viewDeleteEatLater(listKantin);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void renderList(List<Kantin> list) {
        this.listKantin = list;
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

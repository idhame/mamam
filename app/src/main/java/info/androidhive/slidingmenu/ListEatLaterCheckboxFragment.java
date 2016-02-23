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

public class ListEatLaterCheckboxFragment extends Fragment {
    private List<Kantin> listKantin;
    private ListView categoryListView;
    private ArrayAdapter categoryItemArrayAdapter;

	public ListEatLaterCheckboxFragment(){}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_kantin, container, false);
        setHasOptionsMenu(true);
        return rootView;
    }

    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        renderList();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_eat_later, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_deleteeatlater:
                delete();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void renderList() {
        final List<Kantin> listKantin = ((MainActivity)getActivity()).getListEatLater();
        categoryItemArrayAdapter = new KantinCheckboxAdapter(getActivity(), listKantin);
        categoryListView = (ListView) getView().findViewById(R.id.kantinList);
        categoryListView.setAdapter(categoryItemArrayAdapter);
        Log.i("Message", "Render2");
        categoryListView.setOnItemClickListener(null);
    }

    public void delete() {
        ((KantinCheckboxAdapter)categoryItemArrayAdapter).delete();
    }
}

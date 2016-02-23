package info.androidhive.slidingmenu;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import info.androidhive.slidingmenu.model.Kantin;
import info.androidhive.slidingmenu.model.Review;

public class ListUserReviewFragment extends Fragment {
    private List<Review> listReview;
    private ListView categoryListView;
    private ArrayAdapter categoryItemArrayAdapter;
    public ListUserReviewFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_user_review, container, false);
        setHasOptionsMenu(true);
        return rootView;
    }



    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        ((MainActivity)getActivity()).fetchUserReview();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_user_review, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_editreview:
                ((MainActivity)getActivity()).viewDeleteReview(listReview);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void renderList(List<Review> list) {
        listReview = list;
        categoryItemArrayAdapter = new SelfReviewAdapter(getActivity(), list);
        categoryListView = (ListView) getView().findViewById(R.id.reviewList);
        categoryListView.setAdapter(categoryItemArrayAdapter);
    }
}

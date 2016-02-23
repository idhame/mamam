package info.androidhive.slidingmenu;

import info.androidhive.slidingmenu.model.Faculty;
import info.androidhive.slidingmenu.model.Kantin;
import info.androidhive.slidingmenu.model.Review;

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
import android.widget.TextView;

import java.util.List;

public class ListReviewFragment extends Fragment {
    private List<Kantin> listKantin;
    private ListView categoryListView;
    private ArrayAdapter categoryItemArrayAdapter;
    public ListReviewFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_review, container, false);
        return rootView;
    }

    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        ((MainActivity)getActivity()).fetchKantinReview();
        TextView t4 = (TextView) getView().findViewById(R.id.textReview);
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).viewWriteReview();
            }
        });
    }

    public void renderList(List<Review> list) {
        categoryItemArrayAdapter = new ReviewAdapter(getActivity(), list);
        categoryListView = (ListView) getView().findViewById(R.id.kantinList);
        categoryListView.setAdapter(categoryItemArrayAdapter);
        categoryListView.setOnItemClickListener(null);
    }
}

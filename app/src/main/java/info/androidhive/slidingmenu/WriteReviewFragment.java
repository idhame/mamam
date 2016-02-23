package info.androidhive.slidingmenu;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import info.androidhive.slidingmenu.model.Kantin;

public class WriteReviewFragment extends Fragment {


	public WriteReviewFragment(){}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_write_review, container, false);
        return rootView;
    }

    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        Button btn = (Button) getView().findViewById(R.id.btnRate);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RatingBar ratingBar = (RatingBar) getView().findViewById(R.id.ratingBar);
                EditText ratingText = (EditText) getView().findViewById(R.id.fld_review);
                EditText titleText = (EditText) getView().findViewById(R.id.fld_title);

                ((MainActivity)getActivity()).postReview(titleText.getText().toString(), ratingText.getText().toString(),ratingBar.getRating());

            }
        });
    }

}

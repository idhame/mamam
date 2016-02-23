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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import info.androidhive.slidingmenu.model.Kantin;

public class DeskripsiKantinFragment extends Fragment {
    private List<Kantin> listKantin;
    private ListView categoryListView;
    private ImageView imgView;
    private ImageLoader imgLoader;
    private ArrayAdapter categoryItemArrayAdapter;

	public DeskripsiKantinFragment(){}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_deskripsi_kantin, container, false);
        setHasOptionsMenu(true);
        return rootView;
    }

    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        Kantin k = ((MainActivity)getActivity()).getKantinView();
        ((MainActivity)getActivity()).getRate(k.getId());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_deskripsi_kantin, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_eatlater:
                ((MainActivity)getActivity()).addToEatLater();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void renderView(double rate) {
        Kantin k = ((MainActivity)getActivity()).getKantinView();

        TextView t = (TextView) getView().findViewById(R.id.namaKantin);
        t.setText(k.getNama());

        TextView t2 = (TextView) getView().findViewById(R.id.textDeskripsi);
        t2.setText(k.getDeskripsi());

        TextView t3 = (TextView) getView().findViewById(R.id.textMenu);
        t3.setText(k.getMenu());

        TextView t4 = (TextView) getView().findViewById(R.id.rating);
        t4.setText("Rate : "+rate+"/5");

        String strURL = "http://ppl-b03.cs.ui.ac.id/images/"+k.getFoto();
        imgView = (ImageView) getView().findViewById(R.id.imageView);
        imgLoader = new ImageLoader(this.getActivity());
        imgLoader.DisplayImage(strURL, imgView);

        TextView t5 = (TextView) getView().findViewById(R.id.textReview);
        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).viewReviewKantin();
            }
        });
    }
}

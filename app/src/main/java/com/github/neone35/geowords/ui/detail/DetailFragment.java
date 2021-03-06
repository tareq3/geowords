package com.github.neone35.geowords.ui.detail;


import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.neone35.geowords.R;
import com.github.neone35.geowords.data.models.remote.ResultsItem;
import com.github.neone35.geowords.data.models.remote.WordResponse;
import com.google.common.base.Joiner;

import org.parceler.Parcels;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    private static final String ARG_PARCEL = "parcel";
    private Parcelable mWordResponseParcel;

    @BindView(R.id.tv_detail_word)
    TextView tvDetailWord;
    @BindView(R.id.tv_detail_part_of_speech)
    TextView tvDetailPartOfSpeech;
    @BindView(R.id.tv_detail_synonyms)
    TextView tvDetailSynonyms;
    @BindView(R.id.tv_detail_type_of)
    TextView tvDetailTypeOf;
    @BindView(R.id.tv_detail_synonyms_label)
    TextView tvDetailSynonymsLabel;
    @BindView(R.id.tv_detail_type_of_label)
    TextView tvDetailTypeOfLabel;

    @BindColor(R.color.colorDivider)
    int veryLightGray;

    public DetailFragment() {
        // Required empty public constructor
    }

    static DetailFragment newInstance(Parcelable wordResponseParcel) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARCEL, wordResponseParcel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mWordResponseParcel = getArguments().getParcelable(ARG_PARCEL);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, rootView);

        WordResponse wordResponse = Parcels.unwrap(mWordResponseParcel);

        tvDetailWord.setText(wordResponse.getWord());
        ResultsItem firstResult = wordResponse.getResults().get(0);
        tvDetailPartOfSpeech.setText(firstResult.getPartOfSpeech());
        if (firstResult.getSynonyms() != null) {
            String synonyms = Joiner.on(", ").skipNulls().join(firstResult.getSynonyms());
            tvDetailSynonyms.setText(synonyms);
        } else {
            tvDetailSynonymsLabel.setVisibility(View.GONE);
            tvDetailSynonyms.setText(R.string.no_synonyms);
            tvDetailSynonyms.setTextColor(veryLightGray);
        }
        if (firstResult.getTypeOf() != null) {
            String typeOfs = Joiner.on(", ").skipNulls().join(firstResult.getTypeOf());
            tvDetailTypeOf.setText(typeOfs);
        } else {
            tvDetailTypeOfLabel.setVisibility(View.GONE);
            tvDetailTypeOf.setText(R.string.no_known_as);
            tvDetailTypeOf.setTextColor(veryLightGray);
        }

        return rootView;
    }

}

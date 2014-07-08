package com.ma.activities;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.ma.lavasa.R;

public class WriteReviewDialogFragment extends DialogFragment {

	Button btn_AddReview, btn_Cancel;
	EditText et_ReviewTitle, et_ReviewDescription;
	RatingBar ratingBar;
	ReviewData reviewData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		return inflater.inflate(R.layout.write_review_fragment, null);
	}

	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);

		btn_AddReview = (Button) getView().findViewById(R.id.placereview_btn_add); 
		btn_Cancel = (Button) getView().findViewById(R.id.placereview_btn_cancel);
		et_ReviewTitle = (EditText) getView().findViewById(R.id.placereview_et_review_title);
		et_ReviewDescription = (EditText) getView().findViewById(R.id.placereview_et_review_desc);
		ratingBar = (RatingBar) getView().findViewById(R.id.placereview_ratingbar);

		reviewData = (PlaceReviewFragment) getTargetFragment();


		btn_Cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getDialog().dismiss();
			}
		});

		btn_AddReview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if(et_ReviewTitle.getText().toString().trim().length()==0 || et_ReviewDescription.getText().toString().trim().length()==0){
					
					Toast.makeText(getActivity(), "Please add Review/Description", Toast.LENGTH_SHORT).show();
				} else {

					reviewData.saveReviewData(et_ReviewTitle.getText().toString().trim(), et_ReviewDescription.getText().toString().trim(), ratingBar.getRating());
					getDialog().dismiss();
				}
			}
		});
	}

}

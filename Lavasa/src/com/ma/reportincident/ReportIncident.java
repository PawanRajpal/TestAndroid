package com.ma.reportincident;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.ma.bean.Incident;
import com.ma.database.LavasaDatabase;
import com.ma.lavasa.R;
import com.ma.location.GPSTracker;

public class ReportIncident extends Fragment {

	int heightDP = 200, WidthDP = 200;
	private Button buttonCamera;
	private Button buttonGallery;
	private Button buttonSumbit;
	private Button buttonClear;
	private Button buttonClearDesc;
	private Button buttonClearLocation;
	private Spinner spinner;
	private EditText etDesc;
	private EditText etLocation;
	private String category;
	ArrayList<String> selectImages;
	LinearLayout linearLayout;
	public static final int MEDIA_TYPE_IMAGE = 1;
	private static final int SELECT_PICTURE = 1;
	GPSTracker gpsTracker;
	Context context;

	private ImageView imageView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.reportincident, null);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		buttonCamera = (Button)getView().findViewById(R.id.btn_camera);
		buttonGallery = (Button)getView().findViewById(R.id.btn_gallery);
		buttonSumbit = (Button)getView().findViewById(R.id.btn_submit);
		buttonClear = (Button)getView().findViewById(R.id.btn_clear);
		buttonClearDesc = (Button)getView().findViewById(R.id.btn_cleardesc);
		buttonClearLocation = (Button)getView().findViewById(R.id.btn_clearlocation);
		spinner = (Spinner)getView().findViewById(R.id.spinner_category);
		etDesc = (EditText)getView().findViewById(R.id.eT_description);
		etLocation = (EditText)getView().findViewById(R.id.eT_location);
		imageView = (ImageView) getView().findViewById(R.id.lV_subcat);

		// Get Location
		gpsTracker = new GPSTracker(getActivity());

		String location = gpsTracker.getAddress().toString();

		if(gpsTracker.getLocation()!=null){

			etLocation.setText(location);
		}

		// Clear button
		buttonClear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				etDesc.setText("");
				etLocation.setText("");

			}
		});

		buttonClearDesc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				etDesc.setText("");

			}
		});

		buttonClearLocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				etLocation.setText("");
			}
		});

		// Spinner
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1, int arg2, long arg3) {

				category = parent.getItemAtPosition(arg2).toString();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {


			}
		});

		// Get Image from Camera
		buttonCamera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				captureImage();

			}
		});

		// Get Image from Gallery
		buttonGallery.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

			}
		});

		// Submit button to save
		buttonSumbit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if(etDesc.getText().length()==0){
					Toast.makeText(getActivity(), "Please add description", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getActivity(), "Saved", Toast.LENGTH_LONG).show();
					getFragmentManager().popBackStack();
				}
			}
		});
	}


	/*
	 * Capturing Camera Image will lauch camera app requrest image capture
	 */
	private void captureImage() {
		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
		startActivityForResult(cameraIntent,4);
	}

	// onActivityResult for camera/gallery
	public void onActivityResult(int requestCode, int resultCode, Intent data)  
	{  
		super.onActivityResult(requestCode, resultCode, data);  
		// check if the request code is same as what is passed  here it is 2  


		if (requestCode == 4 && resultCode == Activity.RESULT_OK) {  
			Bitmap photo = (Bitmap) data.getExtras().get("data"); 
			// CALL THIS METHOD TO GET THE URI FROM THE BITMAP
			Uri tempUri = getImageUri(getActivity(), photo);

			// CALL THIS METHOD TO GET THE ACTUAL PATH
			File file = new File(getRealPathFromURI(tempUri));

			imageView.refreshDrawableState();
			Bitmap bmp;
			try {
				bmp=Bitmap.createScaledBitmap(BitmapFactory.decodeFile(file.getPath().toString()), WidthDP, heightDP, true);
				imageView.setImageBitmap(bmp);
			} catch (OutOfMemoryError e) {
				e.printStackTrace();
			}

			//imageView.setBackgroundResource(finalFile.getPath().toString());
			//LavasaDatabase.getInstance().insertIncidentInfo(category, "", desEditText.getText().toString(), finalFile.getPath().toString(), "no");
			//ShowImagesFromGalleryDb showImagesFromGalleryDb = new ShowImagesFromGalleryDb();
			//showImagesFromGalleryDb.execute();
		}  

		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				Uri selectedImageUri = data.getData();
				String selectedImagePath = getPath(selectedImageUri);

				selectImages=new ArrayList<String>();
				selectImages.add(selectedImagePath);
				Toast.makeText(getActivity(), "" + selectedImagePath, Toast.LENGTH_LONG).show();

				Bitmap bmp;
				try {
					bmp=Bitmap.createScaledBitmap(BitmapFactory.decodeFile(selectedImagePath), WidthDP, heightDP, true);
					imageView.setImageBitmap(bmp);
				} catch (OutOfMemoryError e) {
					e.printStackTrace();
				}

				//LavasaDatabase.getInstance().insertIncidentInfo(category, "", desEditText.getText().toString(), selectedImagePath, "no");
				//ShowImagesFromGallery showImagesFromGallery=new ShowImagesFromGallery();
				//showImagesFromGallery.execute();
			}
		}
	}  

	// Get Image path
	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}


	private class showImageThumbnail extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {

			return null;
		}

	}



	/** Class to show sub category thumbnails and description  */
	private class ShowImagesFromGallery extends AsyncTask<Void, Void, Void>{
		ImageView thumbImageView;
		LinearLayout.LayoutParams layoutParams;
		Incident setImages;
		@Override
		protected void onPreExecute() {
			try {
				linearLayout = (LinearLayout)getView().findViewById(R.id.lV_subcat);
				heightDP = (int) (100 * getResources().getDisplayMetrics().density);
				WidthDP = (int) (100 * getResources().getDisplayMetrics().density);
			} catch (Exception e) {
				e.printStackTrace();
			}
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {

			setImages = null;
			setImages= LavasaDatabase.getInstance().getIncident();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			try {
				linearLayout.removeAllViews();
				if(thumbImageView!=null)
				{
					thumbImageView =null;
				}
				if(layoutParams!=null){
					layoutParams = null;
				}

				if(!setImages.getImgURL().equalsIgnoreCase("")&& setImages!=null){
					thumbImageView = new ImageView(getActivity());
					layoutParams = new LinearLayout.LayoutParams(heightDP, WidthDP);
					layoutParams.setMargins(10, 5, 10, 5);
					thumbImageView.setLayoutParams(layoutParams);
					thumbImageView.setScaleType(ScaleType.CENTER_CROP);
					thumbImageView.setPadding(10, 2, 10, 4);
					thumbImageView.setMaxHeight(80);
					thumbImageView.setMaxWidth(80);
					//thumbImageView.setBackgroundResource(R.color.com_facebook_blue);
					Bitmap bmp;
					try {
						bmp=Bitmap.createScaledBitmap(BitmapFactory.decodeFile(setImages.getImgURL()), WidthDP, heightDP, true);
						thumbImageView.setImageBitmap(bmp);
					} catch (OutOfMemoryError e) {
						e.printStackTrace();
					}
					linearLayout.addView(thumbImageView);
					//thumbImageView.setOnClickListener(new ImagesClickListener(i));
					//thumbImageView.setOnLongClickListener(new ImagesLongListener(i,setImages.get(i)));
					System.out.println(setImages.getImgURL());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}		

			super.onPostExecute(result);
		}
	}

	/** Class to show sub category thumbnails and description  */
	private class ShowImagesFromGalleryDb extends AsyncTask<Void, Void, Void>{
		ImageView thumbImageView ;
		LinearLayout.LayoutParams layoutParams ;
		Incident setImages;
		@Override
		protected void onPreExecute() {
			try {
				linearLayout = (LinearLayout)getView().findViewById(R.id.lV_subcat);

				heightDP = (int) (150 * getResources().getDisplayMetrics().density);
				WidthDP = (int) (150 * getResources().getDisplayMetrics().density);

			} catch (Exception e) {
				e.printStackTrace();
			}
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			setImages = null;
			setImages = LavasaDatabase.getInstance().getIncident();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			try {
				linearLayout.removeAllViews();
				if(thumbImageView!=null)
				{
					thumbImageView =null;
				}
				if(layoutParams!=null){
					layoutParams = null;

				}
				if(!setImages.getImgURL().equalsIgnoreCase("")&& setImages!=null){
					thumbImageView = new ImageView(getActivity());
					layoutParams = new LinearLayout.LayoutParams(heightDP, WidthDP);
					layoutParams.setMargins(10, 5, 10, 5);
					thumbImageView.setLayoutParams(layoutParams);
					thumbImageView.setScaleType(ScaleType.CENTER_CROP);
					thumbImageView.setPadding(10, 2, 10, 4);
					thumbImageView.setMaxHeight(80);
					thumbImageView.setMaxWidth(80);
					//thumbImageView.setBackgroundResource(R.color.com_facebook_blue);
					Bitmap bmp;
					try {
						bmp=Bitmap.createScaledBitmap(BitmapFactory.decodeFile(setImages.getImgURL()), WidthDP, heightDP, true);
						thumbImageView.setImageBitmap(bmp);
					} catch (OutOfMemoryError e) {
						e.printStackTrace();
					}

					linearLayout.addView(thumbImageView);
					//thumbImageView.setOnClickListener(new ImagesClickListener(i));
					//thumbImageView.setOnLongClickListener(new ImagesLongListener(i,setImages.get(i)));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}		
			super.onPostExecute(result);
		}
	}

	public Uri getImageUri(Context inContext, Bitmap inImage) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		String path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
		return Uri.parse(path);
	}

	public String getRealPathFromURI(Uri uri) {
		Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null); 
		cursor.moveToFirst(); 
		int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA); 
		return cursor.getString(idx); 
	}


	@Override
	public void onResume() {
	//	ShowImagesFromGalleryDb showImagesFromGalleryDb=new ShowImagesFromGalleryDb();
		//showImagesFromGalleryDb.execute();
		super.onResume();
	}

}

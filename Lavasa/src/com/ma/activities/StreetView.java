package com.ma.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.ma.lavasa.R;
public class StreetView extends FragmentActivity {

	//private static final String PLACE = "18.405009,73.505553";

	private static final LatLng SAN_FRAN = new LatLng(18.405563, 73.505413);
	private StreetViewPanorama svp;
	private CheckBox mStreetNameCheckbox;
	private CheckBox mNavigationCheckbox;
	private CheckBox mZoomCheckbox;
	private CheckBox mPanningCheckbox;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.streetviewpanaroma);

		setUpStreetViewPanoramaIfNeeded(savedInstanceState);

		mStreetNameCheckbox = (CheckBox) findViewById(R.id.streetnames);
		mNavigationCheckbox = (CheckBox) findViewById(R.id.navigation);
		mZoomCheckbox = (CheckBox) findViewById(R.id.zoom);
		mPanningCheckbox = (CheckBox) findViewById(R.id.panning);

		/*		// Add code to print out the key hash
	    try {
	        PackageInfo info = getPackageManager().getPackageInfo("com.ma.mapssdksample", PackageManager.GET_SIGNATURES);
	        for (Signature signature : info.signatures) {
	            MessageDigest md = MessageDigest.getInstance("SHA");
	            md.update(signature.toByteArray());
	            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
	            }
	    } catch (NameNotFoundException e) {

	    } catch (NoSuchAlgorithmException e) {

	    }

		 Uri streetViewUri = Uri.parse("google.streetview:cbll=" + PLACE + "&cbp=3a,75y,265.29h,90t");
         Intent streetViewIntent = new Intent(Intent.ACTION_VIEW, streetViewUri);
         streetViewIntent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
         startActivity(streetViewIntent);*/

	}

	private void setUpStreetViewPanoramaIfNeeded(Bundle savedInstanceState) {
		if (svp == null) {
			svp = ((SupportStreetViewPanoramaFragment)
					getSupportFragmentManager().findFragmentById(R.id.streetviewpanorama))
					.getStreetViewPanorama();
			if (svp != null) {
				if (savedInstanceState == null) {
					svp.setPosition(SAN_FRAN);
				}
			}
		}
	}

	private boolean checkReady() {
		if (svp == null) {
			Toast.makeText(this, R.string.map_not_ready, Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	public void onStreetNamesToggled(View view) {
		if (!checkReady()) {
			return;
		}
		svp.setStreetNamesEnabled(mStreetNameCheckbox.isChecked());
	}

	public void onNavigationToggled(View view) {
		if (!checkReady()) {
			return;
		}
		svp.setUserNavigationEnabled(mNavigationCheckbox.isChecked());
	}

	public void onZoomToggled(View view) {
		if (!checkReady()) {
			return;
		}
		svp.setZoomGesturesEnabled(mZoomCheckbox.isChecked());
	}

	public void onPanningToggled(View view) {
		if (!checkReady()) {
			return;
		}
		svp.setPanningGesturesEnabled(mPanningCheckbox.isChecked());
	}

}

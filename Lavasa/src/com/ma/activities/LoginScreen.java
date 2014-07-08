package com.ma.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ma.lavasa.R;
import com.ma.socialmedia.FBLogin;
import com.ma.socialmedia.GPlusLogin;

public class LoginScreen  extends Activity{

	Button btn_FBLogin;
	Button btn_GPlusLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		btn_FBLogin = (Button) findViewById(R.id.login_btn_fb);
		btn_GPlusLogin = (Button) findViewById(R.id.login_btn_gplus);
		
		btn_FBLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(getApplicationContext(), FBLogin.class);
				startActivityForResult(intent, 1);
			}
		});
		
		btn_GPlusLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(getApplicationContext(), GPlusLogin.class);
				startActivityForResult(intent, 2);
			}
		});
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode==1){
			finish();
		}
		
		if(resultCode==2){
			finish();
		}
	}

}

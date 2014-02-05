package com.org.cch_android;

import java.util.ArrayList;
import android.widget.TextView;

import model.User;



import com.example.cch_android.R;
import com.org.cch_android.application.UIUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class CCHLogin extends Activity{
	
	public static final String TAG = CCHLogin.class.getSimpleName();
	private boolean IS_LOGGEDIN = false;
	
	private EditText staffIdField;
	private EditText passwordField;	
	private ProgressDialog pDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
				
		//prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		staffIdField = (EditText) findViewById(R.id.staff_id_fields);
        passwordField = (EditText) findViewById(R.id.login_password_field);	
		
	}
	
	public void onLoginClick(View view){
		int staffid = Integer.valueOf(staffIdField.getText().toString());
    	//check valid email address format
    	if(staffIdField.getText().toString().length() == 0){
    	    UIUtils.showAlert(this,R.string.error,R.string.error_no_username);
    		return;
    	}
    	
    	String password = passwordField.getText().toString();
    	
    	// show progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setTitle(R.string.title_login);
        pDialog.setMessage(this.getString(R.string.login_process));
        pDialog.setCancelable(true);
        pDialog.show();       
    	
    	User u = new User();
    	u.setStaffId(staffid);
    	u.setPassword(password);
    	
    	
    	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

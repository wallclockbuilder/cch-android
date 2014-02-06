package com.org.cch_android;

import java.util.ArrayList;
import android.widget.TextView;

import model.User;



import com.example.cch_android.R;
import com.org.cch_android.application.CCHDbHelper;
import com.org.cch_android.application.UIUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
/*
 * This activity shows the login page 
 * it has a login and a sign up button
 * it has 2 text fields, one for taking the Staff Id and another for password entry
 */
public class CCHLogin extends Activity{
	
	public static final String TAG = CCHLogin.class.getSimpleName();
	private boolean IS_LOGGEDIN = false;
	//private final Context ctx;
	private EditText staffIdField;
	private EditText passwordField;	
	private ProgressDialog pDialog;
	
	/*public CCHLogin(Context ctx){
		this.ctx = ctx;
	}*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);	
		staffIdField = (EditText) findViewById(R.id.staff_id_fields);
        passwordField = (EditText) findViewById(R.id.login_password_field);			
	}
	
	/*
	 * Methods needed
	 * 
	 */
	
	public void onLoginClick(View view){
		int staffid = Integer.valueOf(staffIdField.getText().toString());
		
    	if(staffIdField.getText().toString().length() == 0){
    	    UIUtils.showAlert(this,R.string.error,R.string.error_no_username);
    		return;
    	}
    	String password = passwordField.getText().toString();
    	
    	User u = new User();
    	u.setStaffId(staffid);
    	u.setPassword(password);    
    	// show progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setTitle(R.string.title_login);
        pDialog.setMessage(this.getString(R.string.login_process));
        pDialog.setCancelable(true);
        pDialog.show();       
    	
    		
    	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

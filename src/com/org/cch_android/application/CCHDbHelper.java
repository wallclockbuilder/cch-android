package com.org.cch_android.application;

import java.util.ArrayList;

import model.User;

import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.digitalcampus.oppia.model.Activity;
import org.digitalcampus.oppia.model.ActivitySchedule;
import org.digitalcampus.oppia.model.Course;
import org.digitalcampus.oppia.model.TrackerLog;
import org.digitalcampus.oppia.task.Payload;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;


public class CCHDbHelper extends SQLiteOpenHelper{
	
	static final String TAG = CCHDbHelper.class.getSimpleName();
	static final String DB_NAME = "mobilelearning.db";
	static final int DB_VERSION = 15;

	private SQLiteDatabase db;
	
	private static final String USER_TABLE = "user";
	private static final String USER_ID = BaseColumns._ID;
	private static final String STAFF_ID = "staff_id";
	private static final String PASSWORD = "password";
	
	private static final String PERSONAL_INFO_TABLE = "personal_info";
	private static final String PI_ID = "user_id";
	private static final String FIRST_NAME = "first_name";
	private static final String LAST_NAME = "last_name";	
	
	
	// Constructor
	public CCHDbHelper(Context ctx) { //
		super(ctx, DB_NAME, null, DB_VERSION);
		db = this.getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createUserTable(db);
		createPersonalInfoTable(db);
		
	}
	
	public void createUserTable(SQLiteDatabase db){
		String m_sql = "create table IF NOT EXISTS " + USER_TABLE + " (" + USER_ID + " integer primary key autoincrement, "
				+ FIRST_NAME + " text, " + LAST_NAME + " text)";
		try{
			
			db.execSQL(m_sql);
			}catch(Exception e){
				Log.v(TAG, "Error occurred while creating " + USER_TABLE);

			}
	}
	

	public void createPersonalInfoTable(SQLiteDatabase db){
		String m_sql = "create table IF NOT EXISTS " + PERSONAL_INFO_TABLE + " (" + PI_ID + " integer, "
				+ STAFF_ID + " int, " + PASSWORD + " text" +"foreign key " +"("+PI_ID+")"+ " REFERENCES " + USER_TABLE +
				"(" +USER_ID+"))";
		try{
			
			db.execSQL(m_sql);
			}catch(Exception e){
				Log.v(TAG, "Error occurred while creating " + PERSONAL_INFO_TABLE);

			}
	}	
	
	public void addToUserTable(User u){
		ContentValues values = new ContentValues();
		values.put(STAFF_ID, u.getStaffId());
		values.put(PASSWORD, u.getPassword());
		Log.v(TAG, "Record added " + u.getStaffId() + " and " + u.getPassword() );
		db.insertOrThrow(USER_TABLE, null, values);
	}
	
	public void addToPersonalInfoTable(User u){
		ContentValues values = new ContentValues();
		values.put(STAFF_ID, u.getFirstname());
		values.put(LAST_NAME, u.getLastname());
		
		Log.v(TAG, "Record added " + u.getFirstname() + " and " + u.getLastname() );
		db.insertOrThrow(PERSONAL_INFO_TABLE, null, values);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	/*public void updateUserTable(User u){
		ContentValues values = new ContentValues();
		values.put(COURSE_C_SCHEDULE, scheduleVersion);
		db.update(COURSE_TABLE, values, COURSE_C_ID + "=" + modId, null);
	}
	public void updarePersonalInfoTable(){}
	public void deleteFromPersonalInfoTable (User u){}
	public void deleteFromUserTable(User u){}*/
	
	
	/*public void updateScheduleVersion(long modId, long scheduleVersion){
		ContentValues values = new ContentValues();
		values.put(COURSE_C_SCHEDULE, scheduleVersion);
		db.update(COURSE_TABLE, values, COURSE_C_ID + "=" + modId, null);
	}
	
	public void insertActivities(ArrayList<Activity> acts) {
		// acts.listIterator();
		for (Activity a : acts) {
			ContentValues values = new ContentValues();
			values.put(ACTIVITY_C_COURSEID, a.getModId());
			values.put(ACTIVITY_C_SECTIONID, a.getSectionId());
			values.put(ACTIVITY_C_ACTID, a.getActId());
			values.put(ACTIVITY_C_ACTTYPE, a.getActType());
			values.put(ACTIVITY_C_ACTIVITYDIGEST, a.getDigest());
			values.put(ACTIVITY_C_TITLE, a.getTitleJSONString());
			db.insertOrThrow(ACTIVITY_TABLE, null, values);
		}
	}

	
	
	
	
	public void resetSchedule(int modId){
		ContentValues values = new ContentValues();
		values.put(ACTIVITY_C_STARTDATE,"");
		values.put(ACTIVITY_C_ENDDATE,"");
		db.update(ACTIVITY_TABLE, values, ACTIVITY_C_COURSEID + "=" + modId, null);
	}	
	
	public void deleteCourse(int modId){
		// delete log
		resetCourse(modId);
		
		// delete activities
		String s = ACTIVITY_C_COURSEID + "=?";
		String[] args = new String[] { String.valueOf(modId) };
		db.delete(ACTIVITY_TABLE, s, args);
		
		// delete course
		s = COURSE_C_ID + "=?";
		args = new String[] { String.valueOf(modId) };
		db.delete(COURSE_TABLE, s, args);
		
		// delete any quiz attempts
		this.deleteQuizResults(modId);
	}
	
	public boolean isInstalled(String shortname){
		String s = COURSE_C_SHORTNAME + "=?";
		String[] args = new String[] { shortname };
		Cursor c = db.query(COURSE_TABLE, null, s, args, null, null, null);
		if(c.getCount() == 0){
			c.close();USER_ID
			return false;
		} else {
			c.close();
			return true;
		}
	}
	
	public boolean toUpdate(String shortname, Double version){
		String s = COURSE_C_SHORTNAME + "=? AND "+ COURSE_C_VERSIONID + "< ?";
		String[] args = new String[] { shortname, String.format("%.0f", version) };
		Cursor c = db.query(COURSE_TABLE, null, s, args, null, null, null);
		if(c.getCount() == 0){
			c.close();
			return false;
		} else {
			c.close();
			return true;
		}
	}
	
	public boolean toUpdateSchedule(String shortname, Double scheduleVersion){
		String s = COURSE_C_SHORTNAME + "=? AND "+ COURSE_C_SCHEDULE + "< ?";
		String[] args = new String[] { shortname, String.format("%.0f", scheduleVersion) };
		Cursor c = db.query(COURSE_TABLE, null, s, args, null, null, null);
		if(c.getCount() == 0){
			c.close();
			return false;
		} else {
			c.close();
			return true;
		}
	}
	

	
	
	public Payload getUnsentQuizResults(){
		String s = QUIZRESULTS_C_SENT + "=? ";
		String[] args = new String[] { "0" };
		Cursor c = db.query(QUIZRESULTS_TABLE, null, s, args, null, null, null);
		c.moveToFirst();
		ArrayList<Object> sl = new ArrayList<Object>();
		while (c.isAfterLast() == false) {
			TrackerLog so = new TrackerLog();
			so.setId(c.getLong(c.getColumnIndex(QUIZRESULTS_C_ID)));
			so.setContent(c.getString(c.getColumnIndex(QUIZRESULTS_C_DATA)));
			sl.add(so);
			c.moveToNext();
		}
		Payload p = new Payload(sl);
		c.close();
		
		return p;
	}
	
	
	
	public void deleteQuizResults(int modId){
		// delete any quiz attempts
		String s = QUIZRESULTS_C_COURSEID + "=?";
		String[] args = new String[] { String.valueOf(modId) };
		db.delete(QUIZRESULTS_TABLE, s, args);
	}*/
	
	
	
	
}

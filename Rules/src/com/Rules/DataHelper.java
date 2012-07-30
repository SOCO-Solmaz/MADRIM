package com.Rules;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataHelper {
	
	public static final String PATIENT_TABLE_NAME = "PATIENT";
	public static final String PHQ9_TABLE_NAME="PHQ9_QUESTIONNAIRE";
	public static final String MESSAGE_TABLE_NAME="MESSAGE_TABLE";
	public static final String RESULT_TABLE_NAME="RESULTS";
	public static final String SYSTEM_MESSAGES_TABLE_NAME="SYSTEM_MESSAGES";
	public static final String SENSORS_TABLE_NAME="SENSORS";
	
	//PATIENT table columns
	public static final String PATIENT_ID = "_id";
	public static final String PATIENT_MADEP_ID = "id_madep_user";
	public static final String PATIENT_NAME = "name";
	public static final String PATIENT_SURNAME_1 = "surname_1";
	public static final String PATIENT_SURNAME_2 = "surname_2";
	public static final String PATIENT_ALIAS = "alias";
	public static final String PATIENT_BIRTHDAY = "birth_date";
	public static final String PATIENT_MAIN_CAUSE = "main_cause";
	public static final String PATIENT_HAS_COUPLE = "has_couple";
	public static final String PATIENT_HAS_EMPLOYMENT = "has_employment";
	//These three fields has been added...
	public static final String PATIENT_RECURRENCE = "recurrence_number";
	public static final String PATIENT_REOCURANCE = "reocurance_number";
	public static final String PATIENT_SUICIDE_HIST = "suicide_history";
	//the fields we need to add in case of remission
	public static final String PATIENT_SEMANA_MEDICACION="sem_med";
	public static final String PATIENT_SEMANA_REMISSION="sem_rem";
	public static final String PATIENT_RELAPSE="relap";
	public static final String PATIENT_REMISSION="remision";
		
	//....
	public static final String PATIENT_TIME_EXPORTED = "time_exported";
	public static final String PATIENT_EXPORTED = "exported";
	
	
	//PHQ9_QUESTIONNAIRE table columns
	public static final String PHQ9_ID = "_id";
	public static final String PHQ9_MADEP_ID = "madep_id";
	public static final String PHQ9_ANSWER_1 = "answer_1";
	public static final String PHQ9_ANSWER_2 = "answer_2";
	public static final String PHQ9_ANSWER_3 = "answer_3";
	public static final String PHQ9_ANSWER_4 = "answer_4";
	public static final String PHQ9_ANSWER_5 = "answer_5";
	public static final String PHQ9_ANSWER_6 = "answer_6";
	public static final String PHQ9_ANSWER_7 = "answer_7";
	public static final String PHQ9_ANSWER_8 = "answer_8";
	public static final String PHQ9_ANSWER_9 = "answer_9";
	public static final String PHQ9_TOTAL_OPTION_1 = "total_option_1";
	public static final String PHQ9_TOTAL_OPTION_2 = "total_option_2";
	public static final String PHQ9_TOTAL_OPTION_3 = "total_option_3";
	public static final String PHQ9_TOTAL_OPTION_4 = "total_option_String4";
	public static final String PHQ9_TOTAL = "total";
	//These fields have been added...
	public static final String PHQ9_WEEK ="week";
	public static final String PHQ9_DATE = "date";
	//These fields are temporary ...
	public static final String PHQ9_BRUGHA_CHANGE = "brugha_change";
	public static final String PHQ9_CAPTURED_SENSORS = "capture_sensors";
	
	public static final String PHQ9_TIME_EXPORTED = "time_exported";
	public static final String PHQ9_EXPORTED = "exported";
		
	
	private static final String PATIENT_TABLE_CREATE =
		"CREATE TABLE " + PATIENT_TABLE_NAME + " (" +
			PATIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  "+
			PATIENT_MADEP_ID + " TEXT, "+
			PATIENT_NAME + " TEXT, "+
			PATIENT_SURNAME_1 + " TEXT, "+
			PATIENT_SURNAME_2 + " TEXT, "+
			PATIENT_ALIAS + " TEXT, "+
			PATIENT_BIRTHDAY + " DATE, "+					
			PATIENT_MAIN_CAUSE +" TEXT, "+
			PATIENT_HAS_COUPLE +" BOOLEAN, "+
			PATIENT_HAS_EMPLOYMENT + " NUMERIC, "+
			PATIENT_RECURRENCE + " NUMERIC, "+
			PATIENT_REOCURANCE + " NUMERIC, "+
			PATIENT_SUICIDE_HIST + " NUMERIC, "+
			PATIENT_SEMANA_MEDICACION + " NUMERIC, "+
			PATIENT_SEMANA_REMISSION + " NUMERIC, "+
			PATIENT_RELAPSE + " NUMERIC, "+
			PATIENT_REMISSION + " NUMERIC, "+
			PATIENT_TIME_EXPORTED +" NUMERIC,  "+
			PATIENT_EXPORTED +" NUMERIC)";
	
	private static final String PHQ9_QUESTIONNAIRE_TABLE_CREATE =
        "CREATE TABLE " + PHQ9_TABLE_NAME + " (" +
        		PHQ9_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,  "+
        		PHQ9_MADEP_ID + " TEXT, "+
        		PHQ9_ANSWER_1 +" NUMERIC, "+
        		PHQ9_ANSWER_2 +" NUMERIC, "+
        		PHQ9_ANSWER_3 +" NUMERIC, "+
        		PHQ9_ANSWER_4 +" NUMERIC, "+
        		PHQ9_ANSWER_5 +" NUMERIC, "+
        		PHQ9_ANSWER_6 +" NUMERIC, "+
        		PHQ9_ANSWER_7 +" NUMERIC, "+
        		PHQ9_ANSWER_8 +" NUMERIC, "+
        		PHQ9_ANSWER_9 +" NUMERIC, "+
        		PHQ9_TOTAL_OPTION_1 +" NUMERIC, "+
        		PHQ9_TOTAL_OPTION_2 +" NUMERIC, "+
        		PHQ9_TOTAL_OPTION_3 +" NUMERIC, "+
        		PHQ9_TOTAL_OPTION_4 +" NUMERIC, "+
        		PHQ9_TOTAL +" NUMERIC, "+
        		PHQ9_WEEK +" NUMERIC, "+
        		PHQ9_BRUGHA_CHANGE +" NUMERIC, "+
        		PHQ9_CAPTURED_SENSORS+" NUMERIC, "+
        		PHQ9_DATE +" DATE, "+        		
        		PHQ9_TIME_EXPORTED +" NUMERIC, "+
        		PHQ9_EXPORTED +" NUMERIC)";
	
	//MESSAGE table columns
	public static final String MESSAGE_ID = "_id";
	public static final String MESSAGE_HOSPITAL = "message_hospital";
	public static final String MESSAGE_PATIENT = "message_patient";
	
	private static final String MESSAGE_TABLE_CREATE =
        "CREATE TABLE " +  MESSAGE_TABLE_NAME + " (" +
        		MESSAGE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,  "+
        		MESSAGE_HOSPITAL +" TEXT, "+
        		MESSAGE_PATIENT +" TEXT)";
	

	//RESULT table columns
	public static final String RESULT_ID="_id";
	public static final String RESULT_MADEP_ID = "result_madep_id";
	public static final String RESULT_MESSAGE_G0="result_message_g0";
	public static final String RESULT_MESSAGE_G1="result_message_g1";
	public static final String RESULT_MESSAGE_G234="result_message_g234";
	public static final String RESULT_MESSAGE_G5="result_message_g5";
	public static final String RESULT_MESSAGE_G6="result_message_g6";
	public static final String RESULT_MESSAGE_G7="result_message_g7";
	public static final String RESULT_MESSAGE_G8="result_message_g8";
	public static final String RESULT_MESSAGE_G9="result_message_g9";
	public static final String RESULT_MESSAGE_G10="result_message_g10";
	public static final String RESULT_MESSAGE_G11="result_message_g11";
	public static final String RESULT_MESSAGE_G12="result_message_g12";
	public static final String RESULT_MESSAGE_G13="result_message_g13";
	public static final String RESULT_MESSAGE_MESSAGE="result_message_message";
	public static final String RESULT_SEMAFOR="result_semafor";
	// added for having access to previous etapa
	public static final String RESULT_ETAPA="result_etapa";
	public static final String RESULT_ALARM="result_alarm";
	public static final String RESULT_WEEK="result_week";
	public static final String RESULT_DATE="result_date";
	

			
	private static final String RESULT_TABLE_CREATE =
        "CREATE TABLE " + RESULT_TABLE_NAME + " (" +
        		RESULT_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,  "+
        		RESULT_MADEP_ID + " TEXT, "+
        		RESULT_MESSAGE_G0 +" TEXT, "+
        		RESULT_MESSAGE_G1 +" TEXT, "+
        		RESULT_MESSAGE_G234 + " TEXT, "+
        		RESULT_MESSAGE_G5 +" TEXT, "+
        		RESULT_MESSAGE_G6 +" TEXT, "+
        		RESULT_MESSAGE_G7 +" TEXT, "+
        		RESULT_MESSAGE_G8 +" TEXT, "+
        		RESULT_MESSAGE_G9 +" TEXT, "+
        		RESULT_MESSAGE_G10 +" TEXT, "+
        		RESULT_MESSAGE_G11 +" TEXT, "+
        		RESULT_MESSAGE_G12 +" TEXT, "+
        		RESULT_MESSAGE_G13 +" TEXT, "+
        		RESULT_MESSAGE_MESSAGE + " TEXT, "+
        		RESULT_SEMAFOR +" INTEGER, "+
        		RESULT_ETAPA + " NUMERIC, "+
        		RESULT_ALARM + " NUMERIC, "+
        		RESULT_WEEK + " INTEGER, "+
        		RESULT_DATE +" DATE)";
	
	//SYSTEM_MESSAGES table columns
	public static final String SYSTEM_MESSAGE_ID = "_id";
	public static final String SYSTEM_MESSAGE_CODE="message_code";
	public static final String SYSTEM_MESSAGE_TEXT= "message_text";
	
	private static final String SYSTEM_MESSAGES_TABLE_CREATE=
		"CREATE TABLE " + SYSTEM_MESSAGES_TABLE_NAME + " (" +
			SYSTEM_MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  "+
			SYSTEM_MESSAGE_CODE + " TEXT, "+
			SYSTEM_MESSAGE_TEXT+ " TEXT)";
	
	//SENSORS_TABLE table columns
	public static final String SENSORS_SENSOR_ID ="_id";
	public static final String SENSORS_SENSOR_MADEP_ID = "sensor_madep_id";
	public static final String SENSORS_SENSOR_WEIGHT = "sensor_weight";
	public static final String SENSORS_SENSOR_ACTIVITY = "sensor_activity";
	public static final String SENSORS_SENSOR_SLEEP = "sensor_sleep";
	public static final String SENSORS_WEEK = "sensor_week";
	
	private static final String SENSORS_TABLE_CREATE =
	        "CREATE TABLE " + SENSORS_TABLE_NAME + " (" +
	        		SENSORS_SENSOR_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,  "+
	        		SENSORS_SENSOR_MADEP_ID +" TEXT, "+
	        		SENSORS_SENSOR_WEIGHT +" NUMERIC, "+
	        		SENSORS_SENSOR_ACTIVITY +" NUMERIC, "+
	        		SENSORS_SENSOR_SLEEP +" NUMERIC, "+
	        		SENSORS_WEEK +" NUMERIC)";
	
	
	public ContentValues values;
	public ContentValues resultvalues;
	public ContentValues patientvalue;
	
	private static final String DATABASE_NAME = "DatabaseRule.db";
	private static final int DATABASE_VERSION = 1;
//	private static final String PHQ9_TABLE_NAME = "PHQ9";
	 
    private final Context context;
	private SQLiteDatabase db;
	
	

//	public static final String DELETE_RESULTS="DELETE FROM " + RESULT_TABLE_NAME ;
	
	public DataHelper(Context context) throws SQLException {
		 this.context = context;
		 OpenHelper openHelper = new OpenHelper(this.context);
		 
		 this.db = openHelper.getWritableDatabase();
		 }
	   
		 
	public void deleteAll() {
		this.db.delete(PATIENT_TABLE_NAME, null, null);
		this.db.delete(PHQ9_TABLE_NAME, null, null);
		this.db.delete(MESSAGE_TABLE_NAME, null, null);
		this.db.delete(RESULT_TABLE_NAME, null, null);
		this.db.delete(SYSTEM_MESSAGES_TABLE_NAME, null, null);
		this.db.delete(SENSORS_TABLE_NAME, null, null);
		}
		 
	public void close() {
		db.close();
		}
			
			
			public long insertPHQ9(String patient_id,int answer_1,int answer_2,int answer_3,int answer_4,
					int answer_5,int answer_6,int answer_7,int answer_8,int answer_9,int total_option_1,
					int total_option_2,int total_option_3,int total_option_4,int total,int week, String date,int brugha_change,int captured_sensor,
					String timeExported, String exported) {
				
				ContentValues values = new ContentValues();
				values.put(PHQ9_MADEP_ID, patient_id);
				values.put(PHQ9_ANSWER_1, answer_1);
				values.put(PHQ9_ANSWER_2, answer_2);
				values.put(PHQ9_ANSWER_3, answer_3);
				values.put(PHQ9_ANSWER_4, answer_4);
				values.put(PHQ9_ANSWER_5, answer_5);
				values.put(PHQ9_ANSWER_6, answer_6);
				values.put(PHQ9_ANSWER_7, answer_7);
				values.put(PHQ9_ANSWER_8, answer_8);
				values.put(PHQ9_ANSWER_9, answer_9);
				values.put(PHQ9_TOTAL_OPTION_1, total_option_1);
				values.put(PHQ9_TOTAL_OPTION_2, total_option_2);
				values.put(PHQ9_TOTAL_OPTION_3, total_option_3);
				values.put(PHQ9_TOTAL_OPTION_4, total_option_4);
				values.put(PHQ9_TOTAL, total);
				values.put(PHQ9_WEEK, week);
				values.put(PHQ9_DATE, date );
				values.put(PHQ9_BRUGHA_CHANGE, brugha_change);
				values.put(PHQ9_CAPTURED_SENSORS, captured_sensor);
				values.put(PHQ9_TIME_EXPORTED, timeExported);
				values.put(PHQ9_EXPORTED, exported);
				
				return db.insert(PHQ9_TABLE_NAME, null, values);
			} 

			
			public long insertResult(String patient_id,String result_message_g0, String result_message_g1,String result_message_g234, String result_message_g5,String result_message_g6, String result_message_g7, String result_message_g8, String result_message_g9,String result_message_g10, String result_message_g11,String result_message_g12, String result_message_g13, String result_message_message,int result_semafor,int result_etapa,boolean result_alarm,int result_week, String result_date){
				ContentValues resultvalues = new ContentValues();
				
				resultvalues.put(RESULT_MADEP_ID, patient_id);
				resultvalues.put(RESULT_MESSAGE_G0, result_message_g0);
				resultvalues.put(RESULT_MESSAGE_G1, result_message_g1);
				resultvalues.put(RESULT_MESSAGE_G234, result_message_g234);
				resultvalues.put(RESULT_MESSAGE_G5, result_message_g5);
				resultvalues.put(RESULT_MESSAGE_G6, result_message_g6);
				resultvalues.put(RESULT_MESSAGE_G7, result_message_g7);
				resultvalues.put(RESULT_MESSAGE_G8, result_message_g8);
				resultvalues.put(RESULT_MESSAGE_G9, result_message_g9);
				resultvalues.put(RESULT_MESSAGE_G10, result_message_g10);
				resultvalues.put(RESULT_MESSAGE_G11, result_message_g11);
				resultvalues.put(RESULT_MESSAGE_G12, result_message_g12);
				resultvalues.put(RESULT_MESSAGE_G13, result_message_g13);
				resultvalues.put(RESULT_MESSAGE_MESSAGE, result_message_message);
				resultvalues.put(RESULT_SEMAFOR, result_semafor);
				resultvalues.put(RESULT_ETAPA, result_etapa);
				resultvalues.put(RESULT_ALARM, result_alarm);
				resultvalues.put(RESULT_WEEK, result_week);
				resultvalues.put(RESULT_DATE, result_date);
				
				return db.insert(RESULT_TABLE_NAME, null, resultvalues);
				
			}
			
				
	
			
		/*   public List<String> selectAll() {
		      List<String> list = new ArrayList<String>();
		      Cursor cursor = this.db.query(PHQ9_TABLE_NAME, new String[] { "name" },
		        null, null, null, null, "name desc");
		      if (cursor.moveToFirst()) {
		         do {
		            list.add(cursor.getString(0));
		         } while (cursor.moveToNext());
		      }
		      if (cursor != null && !cursor.isClosed()) {
		         cursor.close();
		      }
		      return list;
		   } */
		 
			public Cursor getAllPHQ9(String patient_id){
				return db.query(PHQ9_TABLE_NAME, new String[] {
						PHQ9_ID,
						PHQ9_MADEP_ID,
						PHQ9_ANSWER_1,
						PHQ9_ANSWER_2,
						PHQ9_ANSWER_3,
						PHQ9_ANSWER_4,
						PHQ9_ANSWER_5,
						PHQ9_ANSWER_6,
						PHQ9_ANSWER_7, 
						PHQ9_ANSWER_8,
						PHQ9_ANSWER_9,
						PHQ9_TOTAL_OPTION_1,
						PHQ9_TOTAL_OPTION_2,
						PHQ9_TOTAL_OPTION_3,
						PHQ9_TOTAL_OPTION_4,
						PHQ9_TOTAL,
						PHQ9_WEEK,
						PHQ9_BRUGHA_CHANGE,
						PHQ9_CAPTURED_SENSORS,
						PHQ9_TIME_EXPORTED,
						PHQ9_EXPORTED,
						}, " madep_id like \'"+ patient_id + "\'", null, null, null, null);
			}
			
			
			public Cursor getPHQ9(int PHQ9ID){
				return db.query(PHQ9_TABLE_NAME, new String[] {
						PHQ9_ID,
						PHQ9_MADEP_ID,
						PHQ9_ANSWER_1,
						PHQ9_ANSWER_2,
						PHQ9_ANSWER_3,
						PHQ9_ANSWER_4,
						PHQ9_ANSWER_5,
						PHQ9_ANSWER_6,
						PHQ9_ANSWER_7, 
						PHQ9_ANSWER_8,
						PHQ9_ANSWER_9,
						PHQ9_TOTAL_OPTION_1,
						PHQ9_TOTAL_OPTION_2,
						PHQ9_TOTAL_OPTION_3,
						PHQ9_TOTAL_OPTION_4,
						PHQ9_TOTAL,
						PHQ9_WEEK,
						PHQ9_BRUGHA_CHANGE,
						PHQ9_CAPTURED_SENSORS,
						PHQ9_TIME_EXPORTED,
						PHQ9_EXPORTED,
						}, " _id like \'"+ PHQ9ID + "\'", null, null, null,null,null);
			}
			
			public Cursor PHQ9ID(String patient_id,int PHQweek){
				return db.query(PHQ9_TABLE_NAME, new String[]{PHQ9_ID}, "week like \'"+ PHQweek + "\'" + "AND madep_id like \'" + patient_id + "\'", null, null, null, null,null);
			}
			
			public Cursor ReadSensor(String patient_id,int sensor_week){
				return db.query(SENSORS_TABLE_NAME, new String[] {
						SENSORS_SENSOR_ID,
						SENSORS_SENSOR_MADEP_ID,
						SENSORS_SENSOR_WEIGHT,
						SENSORS_SENSOR_ACTIVITY,
						SENSORS_SENSOR_SLEEP,
						SENSORS_WEEK,
				}, " sensor_week like \'" + sensor_week + "\'" + "AND sensor_madep_id like \'" + patient_id + "\'",null, null, null, null, null);
				
			}
			
			
			public void DeleteAllTheResults(String patient_id){
				db.delete(RESULT_TABLE_NAME, null, null);
				
			}
			
			public long UpdatePetient(String patient_id, int semana_med, int semana_rem, boolean relapse, boolean remision){
				ContentValues patientvalue= new ContentValues();
				
				patientvalue.put(PATIENT_MADEP_ID, patient_id);
				patientvalue.put(PATIENT_SEMANA_MEDICACION, semana_med);
				patientvalue.put(PATIENT_SEMANA_REMISSION, semana_rem);
				patientvalue.put(PATIENT_RELAPSE, relapse);
				patientvalue.put(PATIENT_REMISSION, remision);
				
				return db.update(PATIENT_TABLE_NAME,patientvalue , PATIENT_MADEP_ID + " like \'" + patient_id + "\'", null);
				
			}
			
			public long UpdatePHQ9(String PHQ9_madepid, int week,int PHQ){
				ContentValues PHQ9=new ContentValues();
				PHQ9.put(PHQ9_TOTAL, PHQ);
				return db.update(PHQ9_TABLE_NAME, PHQ9, "week like \'"+ week + "\'" + "AND madep_id like \'"+ PHQ9_madepid + "\'", null);
			}

			
		//	public Cursor getThreeLastPHQ(){
				//later I should use select top 3,now I cant
				//String SqlStringThreeLastPHQ9=
			//eturn db.rawQuery(sql, selectionArgs);
		//	}
			
			public Cursor getAllRESULTS(String patient_id){
				return db.query(RESULT_TABLE_NAME, new String[]{
						RESULT_ID,
						RESULT_MADEP_ID,
						RESULT_MESSAGE_G0,
						RESULT_MESSAGE_G1,
						RESULT_MESSAGE_G234,
						RESULT_MESSAGE_G5,
						RESULT_MESSAGE_G6,
						RESULT_MESSAGE_G7,
						RESULT_MESSAGE_G8,
						RESULT_MESSAGE_G9,
						RESULT_MESSAGE_G10,
						RESULT_MESSAGE_G11,
						RESULT_MESSAGE_G12,
						RESULT_MESSAGE_G13,
						RESULT_MESSAGE_MESSAGE,
						RESULT_SEMAFOR,
						RESULT_ETAPA,
						RESULT_ALARM,
						RESULT_WEEK,
						RESULT_DATE,
				}, "result_madep_id like " + "'" + patient_id + "'", null, null, null, null, null);
			}
			
			public Cursor getResultList(String patient_id){
				return db.query(RESULT_TABLE_NAME, new String[]{
						RESULT_ID,	
						RESULT_MESSAGE_G0,
						RESULT_MESSAGE_MESSAGE,
						RESULT_WEEK

				}, RESULT_MADEP_ID + " like \'"+ patient_id + "\'", null, null, null, null, null);				
			}
			
			public Cursor getPatient(String patient_id){
				return db.query(PATIENT_TABLE_NAME, new String[]{
								PATIENT_ID,
								PATIENT_MADEP_ID,
								PATIENT_NAME,
								PATIENT_SURNAME_1,
								PATIENT_SURNAME_2,
								PATIENT_ALIAS,
								PATIENT_BIRTHDAY,					
								PATIENT_MAIN_CAUSE,
								PATIENT_HAS_COUPLE,
								PATIENT_HAS_EMPLOYMENT,
								PATIENT_RECURRENCE,
								PATIENT_REOCURANCE,
								PATIENT_SUICIDE_HIST,
								PATIENT_SEMANA_MEDICACION,
								PATIENT_SEMANA_REMISSION,
								PATIENT_RELAPSE,
								PATIENT_REMISSION,
								PATIENT_TIME_EXPORTED,
								PATIENT_EXPORTED,						
				}, "id_madep_user like " + "'" + patient_id + "'", null, null, null, null);
			}
			
			public Cursor getSensors(String patient_id){
				return db.query(SENSORS_TABLE_NAME, new String[]{
						SENSORS_SENSOR_ID,
						SENSORS_SENSOR_MADEP_ID,
						SENSORS_SENSOR_WEIGHT,
						SENSORS_SENSOR_ACTIVITY,
						SENSORS_SENSOR_SLEEP,
						SENSORS_WEEK,						
				}, "sensor_madep_id like " + "'" + patient_id + "'", null, null, null, null );
			}
			
			public Cursor getMESSAGE(String MessageCode){
				return db.query(SYSTEM_MESSAGES_TABLE_NAME, new String[]{SYSTEM_MESSAGE_ID,SYSTEM_MESSAGE_CODE,SYSTEM_MESSAGE_TEXT}, "message_code like \'"+ MessageCode + "\'", null, null, null, null);
				//return db.query(SYSTEM_MESSAGES_TABLE_NAME, new String[]{SYSTEM_MESSAGE_CODE} , "message_code like \'"+ MessageCode + "\'" , null, null, null, null);
			}
			
		   public static class OpenHelper extends SQLiteOpenHelper {
		 
		      OpenHelper(Context context) {
		         super(context, DATABASE_NAME, null, DATABASE_VERSION);
		      }
		 
		      @Override
		      public void onCreate(SQLiteDatabase db) {
		    	 db.execSQL(PATIENT_TABLE_CREATE);
		         db.execSQL(PHQ9_QUESTIONNAIRE_TABLE_CREATE);
		         db.execSQL(MESSAGE_TABLE_CREATE);
		         db.execSQL(RESULT_TABLE_CREATE);
		         db.execSQL(SYSTEM_MESSAGES_TABLE_CREATE);
		         db.execSQL(SENSORS_TABLE_CREATE);
		      }
		 
		      @Override
		      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		         Log.w("Example", "Upgrading database, this will drop tables and recreate.");
		         db.execSQL("DROP TABLE IF EXISTS "+ PATIENT_TABLE_NAME);
		         db.execSQL("DROP TABLE IF EXISTS " + PHQ9_TABLE_NAME);
		         db.execSQL("DROP TABLE IF EXISTS "+ MESSAGE_TABLE_NAME);
		         db.execSQL("DROP TABLE IF EXISTS "+ RESULT_TABLE_NAME);
		         db.execSQL("DROP TABLE IF EXISTS "+ SYSTEM_MESSAGES_TABLE_NAME);
		         db.execSQL("DROP TABLE IF EXISTS "+ SENSORS_TABLE_NAME);
		         onCreate(db);
		      }
		   }
}

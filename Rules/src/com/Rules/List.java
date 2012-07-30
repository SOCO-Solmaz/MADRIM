package com.Rules;


import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class List extends ListActivity {
	
	public DataHelper dh1;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_activity_view);
		
		Bundle bundle = getIntent().getExtras();
		String patient_id=bundle.getString("patient_id");
		
		
		this.dh1= new DataHelper(this);
	//	final Button btn_back = (Button) findViewById(R.id.btnBack);
		ListView lv = this.getListView();
		
		final Button btn_back = (Button) findViewById(R.id.back);
	//	String[] items = new String[] {"Item 1", "Item 2", "Item 3"};
	//    ArrayAdapter<String> adapter =
	 //   	      new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		Cursor cursorResultList = dh1.getResultList(patient_id);
	//	String t= new Integer(cursorResultList.getCount()).toString();
	//	Toast.makeText(getApplicationContext(), t, Toast.LENGTH_SHORT).show();
		//cursorResultList.moveToFirst();
		startManagingCursor(cursorResultList);
		ListAdapter adapter = new SimpleCursorAdapter(this,R.layout.custom_row_view ,cursorResultList,new String[] {cursorResultList.getColumnName(3), cursorResultList.getColumnName(2), cursorResultList.getColumnName(1) },new int[]{R.id.text1,R.id.text2,R.id.text3});
	    lv.setAdapter(adapter);

		
		
		
		
		
	//	
		
		
		
	//	setListAdapter(adapter);
	    btn_back.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		    	
	    });
		
	}

	
	
	
	private Object Integer(int columnCount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		dh1.close();
	}

	
	

}

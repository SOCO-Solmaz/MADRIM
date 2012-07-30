package com.Rules;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.Toast;



public class RulesActivity extends Activity {
	
	public DataHelper dh;	
	// az emrooz svn estefade mikonam
	
	//public PHQTotals phqtotals;
		
	//The objects 
	Rule RuleEngine = new Rule();
	PUNTOS puntosfilled= new PUNTOS();
	Historia histfilled=new Historia();
	PEM_M pem_m = new PEM_M();
	MESSAGES Mes = new MESSAGES();
	int PHQ0,PHQ1,PHQ2,pos,i;
	int week,PHQ;
	String patient_id;
	
	private CursorAdapter datasource;
	

	String Regla;

	
	//public PUNTOS FillPuntos(int PHQ9ID){
	//	PUNTOS puntosfilled;
	//	Cursor cursor=dh.getPHQ9(PHQ9ID);
		
		//later I should work with lastPHQ9ID and change the order to the oposit dirrection
		
		
	//	return puntosfilled;
	//}
	
//	@SuppressWarnings("null")
	 
	 
	public void ProduceResults(String patient_id){

		

		String Etapa_string="";
		int Etapa;
		boolean Alarm=false;

		
		Cursor cursorPHQ9=dh.getAllPHQ9(patient_id);
		//Cursor cursorRESULT=dh.getAllRESULTS();
		Cursor cursorSENSORS=dh.getSensors(patient_id);
		Cursor cursorPATIENT=dh.getPatient(patient_id);
		
		cursorPHQ9.moveToFirst();
		int CC=cursorPHQ9.getCount();

			cursorPATIENT.moveToFirst();
			
			pos=0;
			cursorPHQ9.moveToPosition(pos);
			
			puntosfilled.P0 = cursorPHQ9.getInt(15);
			puntosfilled.T0 = cursorPHQ9.getInt(16);
			
			puntosfilled.PI = puntosfilled.P0;//Cursor cur=dh.PHQ9ID(week);

			puntosfilled.TI = puntosfilled.P0;
			
			//sensors 0
			cursorSENSORS=dh.ReadSensor(patient_id,puntosfilled.T0);
			
	
			//if we assume the first 2 weeks are 0-2
			histfilled.sem_med=0;
			histfilled.sem_rem=0;
			histfilled.brugha_change = (cursorPHQ9.getInt(11)==1);
			histfilled.capture_PHQ9=true;
			histfilled.anterior_alarm=false;
			histfilled.remision=false;
			histfilled.relap=false;
			

			//if Histfilled.capturasensor = true ...
			cursorSENSORS=dh.ReadSensor(patient_id,puntosfilled.T0);
			if (cursorSENSORS.moveToFirst()) {
				cursorSENSORS.moveToFirst();
				puntosfilled.Peso0=cursorSENSORS.getInt(2);
				puntosfilled.Mov0=cursorSENSORS.getInt(3);
				puntosfilled.Dorm0=cursorSENSORS.getInt(4);

				histfilled.capture_sensor=true;}
			else histfilled.capture_sensor=false;
			cursorSENSORS.close();
			
				cursorPHQ9.moveToPosition(pos+1);

				puntosfilled.P2 = cursorPHQ9.getInt(15);
				puntosfilled.T2 = cursorPHQ9.getInt(16);
				histfilled.sem_med=puntosfilled.T2;
				
				
				//sensor 2
				if (histfilled.capture_sensor=true){
				cursorSENSORS=dh.ReadSensor(patient_id,puntosfilled.T2);
			if (cursorSENSORS.moveToFirst()) {
				cursorSENSORS.moveToFirst();
				puntosfilled.Peso2=cursorSENSORS.getInt(2);
				puntosfilled.Mov2=cursorSENSORS.getInt(3);
				puntosfilled.Dorm2=cursorSENSORS.getInt(4);}	
				else histfilled.capture_sensor=false;
				cursorSENSORS.close();}
				//Cursor cur=dh.PHQ9ID(week);

			pem_m = RuleEngine.PEM(puntosfilled, puntosfilled.T2-puntosfilled.T0);
			
			Mes.txt1[2]=pem_m.Regla;
			Mes.txt1[3]=pem_m.Tcal + pem_m.Tdir +pem_m.Tvel;
			
			Etapa=RuleEngine.Etapa(pem_m.Regla, puntosfilled.P2,puntosfilled.P1, puntosfilled.PI, histfilled.sem_med, histfilled.sem_rem, histfilled.relap, 0);
		
			RuleEngine.EvaluaEtapa(Etapa, puntosfilled, pem_m, histfilled.sem_med,histfilled.sem_rem,Mes,histfilled.remision,histfilled.relap,histfilled.brugha_change,histfilled.capture_PHQ9,histfilled.capture_sensor, histfilled.anterior_alarm);
			if (histfilled.capture_sensor) RuleEngine.EvaluaSensor(puntosfilled,Mes);

	 		if (Mes.Semafor==RuleEngine.Rojo) {Alarm=true;}
	 		else Alarm=false;
	 		
			dh.insertResult(patient_id,Mes.txt1[0], Mes.txt1[1],Mes.txt1[2]+Mes.txt1[3]+Mes.txt1[4], Mes.txt1[5], Mes.txt1[6], Mes.txt1[7], Mes.txt1[8], Mes.txt1[9],Mes.txt1[10], Mes.txt1[11],Mes.txt1[12],Mes.txt1[13] , Mes.txt1[3]+" "+Mes.txt1[6]+Mes.txt1[7]+Mes.txt1[8]+Mes.txt1[9]+"Semana Remission: "+Mes.txt1[10]+Mes.txt1[11]+Mes.txt1[12]+Mes.txt1[13],Mes.Semafor, Etapa, Alarm, puntosfilled.T2 ,"" );
			dh.UpdatePetient(patient_id,histfilled.sem_med, histfilled.sem_rem, histfilled.relap,histfilled.remision);
			

			
			for (int i=0; i< (CC-2); i++){
				//The objects must be  created again

				Mes.Clean();
				histfilled.clean();
				puntosfilled.clean();
				pem_m.clean();
				

				Cursor cursorRESULT=dh.getAllRESULTS(patient_id);
				cursorRESULT.moveToLast();
				
				cursorPATIENT=dh.getPatient(patient_id);
				cursorPATIENT.moveToFirst();
				
				histfilled.etapa_anterior=cursorRESULT.getInt(16);
				histfilled.Semafor_Anterior=cursorRESULT.getInt(15);
				histfilled.anterior_alarm = (cursorRESULT.getInt(17)==1)?true:false;
				
				//histfilled.sem_med=cursorPATIENT.getInt(13);
				//histfilled.sem_rem=cursorPATIENT.getInt(14);
				histfilled.remision=false;
				histfilled.relap=(cursorPATIENT.getInt(16)==1);
				
			pos=0;	
			cursorPHQ9.moveToPosition(pos);
			
			puntosfilled.PI = cursorPHQ9.getInt(15);
			puntosfilled.TI = cursorPHQ9.getInt(16);
			
			cursorPHQ9.moveToPosition(pos+i);
			puntosfilled.P0 = cursorPHQ9.getInt(15);
			puntosfilled.T0 = cursorPHQ9.getInt(16);
			//Sensors0
			
			cursorSENSORS=dh.ReadSensor(patient_id,puntosfilled.T0);
			if (cursorSENSORS.moveToFirst()){
			cursorSENSORS.moveToFirst();
			puntosfilled.Peso0=cursorSENSORS.getInt(2);
			puntosfilled.Mov0=cursorSENSORS.getInt(3);
			puntosfilled.Dorm0=cursorSENSORS.getInt(4);
			histfilled.capture_sensor=true;}
			else histfilled.capture_sensor=false;
			cursorSENSORS.close();
			
			pos++;
			cursorPHQ9.moveToPosition(pos+i);
			puntosfilled.P1= cursorPHQ9.getInt(15);
			puntosfilled.T1= cursorPHQ9.getInt(16);
	
			
			pos++;
			cursorPHQ9.moveToPosition(pos+i);
			puntosfilled.P2= cursorPHQ9.getInt(15);
			puntosfilled.T2= cursorPHQ9.getInt(16);
			
			//sensor 2
			if (histfilled.capture_sensor) {
			cursorSENSORS=dh.ReadSensor(patient_id,puntosfilled.T2);
			if (cursorSENSORS.moveToFirst()) {
			cursorSENSORS.moveToFirst();
			puntosfilled.Peso2=cursorSENSORS.getInt(2);
			puntosfilled.Mov2=cursorSENSORS.getInt(3);
			puntosfilled.Dorm2=cursorSENSORS.getInt(4);}
			else histfilled.capture_sensor=false;
			cursorSENSORS.close();}
			
			
			histfilled.brugha_change = (cursorPHQ9.getInt(17)==1);
			//histfilled.capture_sensor = (cursorPHQ9.getInt(17)==1);		
			histfilled.sem_med = cursorPHQ9.getInt(16);
			histfilled.sem_rem = cursorPATIENT.getInt(14);
			histfilled.relap = (cursorPATIENT.getInt(15)==1);
		//	histfilled.remision = (cursorPATIENT.getInt(16)==1);
	          if(puntosfilled.P2<=RuleEngine.K_Normal && !histfilled.remision){
	               histfilled.remision=true;
	               histfilled.sem_rem = histfilled.sem_rem+(puntosfilled.T2-puntosfilled.T1);                   
	               }  
	        //Cursor cur=dh.PHQ9ID(week);

	
			if ((puntosfilled.T2 - puntosfilled.T1 == 2) & (puntosfilled.T1 - puntosfilled.T0 ==2)) 
				pem_m = RuleEngine.PEM(puntosfilled, puntosfilled.T2-puntosfilled.T0);
			else if (puntosfilled.T2 - puntosfilled.T1 > 2)	{	
				pem_m = RuleEngine.PEM(puntosfilled, puntosfilled.T2 - puntosfilled.T1);
				histfilled.capture_PHQ9=false;}			
			else if ((puntosfilled.T2 - puntosfilled.T1 ==2 )&&(puntosfilled.T1 - puntosfilled.T0 > 2)){
				pem_m=RuleEngine.PEM(puntosfilled, 2);
			}
							
			Mes.txt1[2]=pem_m.Regla;
			Mes.txt1[3]=pem_m.Tcal+ pem_m.Tdir + pem_m.Tvel;
			
			Etapa=RuleEngine.Etapa(pem_m.Regla, puntosfilled.P2,puntosfilled.P1,puntosfilled.PI, histfilled.sem_med, histfilled.sem_rem, histfilled.relap, histfilled.etapa_anterior);
			
		//	if (Etapa==4 ||Etapa==5 || Etapa==6) {histfilled.remision=true;
		//	histfilled.sem_rem=histfilled.sem_rem + ((puntosfilled.T2-puntosfilled.T1) + (puntosfilled.T1-puntosfilled.T0))-4;
		//	}
		//	else if (Etapa==1 || Etapa==2 || Etapa==3 || Etapa==7 || Etapa==0) {histfilled.remision=false;
		//			histfilled.sem_rem=0;}
			if (Etapa==7) {histfilled.relap=true;
			histfilled.remision=false;//Cursor cur=dh.PHQ9ID(week);

			histfilled.sem_rem=0;}
			
			if ((Etapa==1) && (histfilled.etapa_anterior==7))
			{histfilled.relap=true;
			histfilled.sem_rem=histfilled.sem_med;
			Mes.txt1[1]="Restart";}
			
			
			RuleEngine.EvaluaEtapa(Etapa, puntosfilled, pem_m, histfilled.sem_med,histfilled.sem_rem,Mes,histfilled.remision,histfilled.relap,histfilled.brugha_change,histfilled.capture_PHQ9,histfilled.capture_sensor, histfilled.anterior_alarm);


			
		//	if (histfilled.remision) histfilled.sem_rem=histfilled.sem_rem + (puntosfilled.T2-puntosfilled.T1) + (puntosfilled.T1-puntosfilled.T0)-4;
			//Cursor cur=dh.PHQ9ID(week);

	        
			Mes.txt1[10]=Integer.toString(histfilled.sem_rem);
			
			if (histfilled.capture_sensor) RuleEngine.EvaluaSensor(puntosfilled,Mes);

			
			if (Mes.Semafor==RuleEngine.Rojo) {Alarm=true;}
			else Alarm=false;
	
			dh.insertResult(patient_id,Mes.txt1[0], Mes.txt1[1],Mes.txt1[2]+ Mes.txt1[3]+Mes.txt1[4], Mes.txt1[5], Mes.txt1[6], Mes.txt1[7], Mes.txt1[8], Mes.txt1[9],Mes.txt1[10], Mes.txt1[11],Mes.txt1[12],Mes.txt1[13] ,  Mes.txt1[3]+" "+Mes.txt1[6]+Mes.txt1[7]+Mes.txt1[8]+Mes.txt1[9]+"Semana Remission: "+Mes.txt1[10]+Mes.txt1[11]+Mes.txt1[12]+Mes.txt1[13], Mes.Semafor, Etapa, Alarm, puntosfilled.T2 ,"" );
				
			dh.UpdatePetient(patient_id,histfilled.sem_med, histfilled.sem_rem, histfilled.relap, histfilled.remision);

			cursorRESULT.close();
			cursorSENSORS.close();
			}
			
			cursorPATIENT.close();
			cursorPHQ9.close();
	

	}

	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        this.dh= new DataHelper(this);
       
      //  dh.deleteAll();
        
             
       final EditText edittext_week = (EditText) findViewById(R.id.et_week);
       final EditText edittext_phq = (EditText) findViewById(R.id.et_phq);
       final EditText edittext_patientid = (EditText) findViewById(R.id.et_patientID);

       final Button btn_PEM = (Button) findViewById(R.id.btn_PEM);
       final Button btn_del_results = (Button) findViewById(R.id.btn_del_results);
       final Button btn_edit = (Button) findViewById(R.id.btn_Edit);
        
        edittext_week.setOnKeyListener(new OnKeyListener() {
			
			public boolean onKey(View v, int keyCode, KeyEvent event) {
		        // If the event is a key-down event on the "enter" button
		        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
		            (keyCode == KeyEvent.KEYCODE_ENTER)) {
		          // Perform action on key press
		        //  Toast.makeText(getApplicationContext(), edittext_puntos.getText(), Toast.LENGTH_SHORT).show();
		 	    //   puntos=Integer.parseInt(edittext_puntos.getText().toString());
			    //   if (puntos == 2) {
			    //	   Toast.makeText(getApplicationContext(), "Doroste", Toast.LENGTH_SHORT).show();		    	   
			    //   }
		          return true;
		        }
				return false;
			}
			    	
        });
  
        btn_PEM.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				patient_id = edittext_patientid.getText().toString();
							
				//first to delete all the results
				dh.DeleteAllTheResults(patient_id);
				dh.UpdatePetient(patient_id,0, 0, false, false);
				
				
				ProduceResults(patient_id);
			//	dh.close();
			//	Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
			//	Cursor cursorResultList = dh.getResultList();
			//	String t= new Integer(cursorResultList.getCount()).toString();
			//	Toast.makeText(getApplicationContext(), t, Toast.LENGTH_SHORT).show();
				
				Intent i= new Intent(RulesActivity.this,List.class);
				Bundle bundle = new Bundle();
				bundle.putString("patient_id",patient_id);
				i.putExtras(bundle);
				startActivityForResult(i,0);
			}
        	
        });
        
        btn_del_results.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(), "Doroste", Toast.LENGTH_SHORT).show();
				patient_id=edittext_patientid.getText().toString();
				dh.DeleteAllTheResults(patient_id);
				dh.UpdatePetient(patient_id,0, 0, false, false);
				
				Toast.makeText(getApplicationContext(), "All the results deleted and PATIENT Table RESET!", Toast.LENGTH_SHORT).show();
			}
        	
        });
        
       btn_edit.setOnClickListener(new OnClickListener(){

		public void onClick(View arg0) {
			
			// TODO Auto-generated method stub
			patient_id=edittext_patientid.getText().toString();
			week=Integer.parseInt(edittext_week.getText().toString());
			PHQ=Integer.parseInt(edittext_phq.getText().toString());
			dh.UpdatePHQ9(patient_id,week, PHQ);
			//Cursor cur=dh.PHQ9ID(week);
			//cur.moveToFirst();
			//String p=new Integer(cur.getInt(0)).toString();
			Toast.makeText(getApplicationContext(),"Updated" , Toast.LENGTH_SHORT).show();
			//cur.close();
		}
    	   
       });
    }
    public void OnDestroy(){
 	   super.onDestroy();
 	   dh.close();
    }
    
    
}

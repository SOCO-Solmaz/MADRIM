package com.Rules;

public class Historia {
	public int sem_med;
	public int sem_rem;
	public boolean relap;
	public boolean remision;
	public boolean brugha_change;
	public boolean capture_sensor;
	public boolean capture_PHQ9;
	public boolean anterior_alarm;
	public int etapa_anterior;
	public int Semafor_Anterior;
	
	public void clean(){
		sem_med=0;
		sem_rem=0;
		relap=false;
		remision=false;
		brugha_change=false;
		capture_sensor=true;
		capture_PHQ9=true;
		anterior_alarm=false;
		etapa_anterior=0;
		Semafor_Anterior=0;
	}
	
}

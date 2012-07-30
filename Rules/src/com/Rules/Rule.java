package com.Rules;

public class Rule {
	
	final int Npac=10; 
	//Calibracion de constantes en numero de semanas

	 String MRegla;	
	 int MEtapa;
	 int Puntos,Dir, Vel, Cal, KK2, KK3;
	 int D1,D2,D;
	 float DeltaT,PP1;
	public final int Empeora=1,SinCambio=2,Mejora=3;
	public final int Lento=1,Normal=2,Rapido=3,Cero=4;
	public final int Advertida=1,Sostenida=2,Oscilante=3,SinValor=4;
	public final int Verde=1, Amarillo=2, Rojo=3;
	MESSAGES messages= new MESSAGES(); 
	public int SemanaRemision;
	public boolean Remision;
	 
	 final int K0=0;       //
//	       Mejorar
	 final int  K1=1;       // Significativo - Lento_Inf.
	 //*here in K2 we changed 4->5 on 17/04 based on ce_manual
	 final int  K2=5;       // Lento_Sup-Normal_Inf  
	 final int  K3=9;       // Normal_Sup-Rapido_Inf
	 //*here we set a new constant for cases that are hard to beleive if they happen 17/04 based on ce_manual
	 final int  K31=16;     // currar en un mes de un depression mayor es poco creible
	 
	 
//	       Empeora
	 final int  K4=1;       // Lento
	 final int  K5=2;       // Normal
	 final int  K6=4;       // Rapido 
	 final int  Ksemanas=4; // Calibraci��n de constantes en n��mero de semanas
	 
	 
	     
	 final int  K_Normal=5;    //      K <  5
	 final int    K_SintomasLevesM  =7;    //  5<= K <= 7
	 final int    K_SintomasLevesH  =9;    //  5<= K <= 9
	 final int    K_DepresionMenor  =14;   //  9 < K <=14
	 final int    K_DepresionMayor  =19;   // 14 < K >=19
	 final int    K_DepresionSevera =20;   // 19 < K >=27
//	        Otras constantes
	 final int     K_Ini = 5;  // Cambio para abandonar estado inicial
	 final int     TmaxSR=4; 
	 final int     TcontMin = 26;int TcontMax = 52;  // Semanas de mantenimiento despues de remision
	 final int     Tmantenimiento = 52;     // Semanas de mantenimiento despues de recaidad y remisi��n
	 
	 final int     KSuicidio_0 = 4;             // Contribuci��n a la suma por antecedentes
	 final int     KSuicidio_1 = 3;             // Indicio de suicidio (PHQ9_9 )
	 final int     KSuicidio_leve = 1;          // Limite inf. riesgo de suicidio leve (suma MiniC )
	 final int     KSuicidio_moderado = 6;      // L��mite inf, riesgo de suicidio moderado (suma MiniC )
	 final int     KSuicidio_alto = 10;         // Limite inf. riesgo de suicidio alto (suma MiniC )
	        
	 int  KPESO = 5;
	 int  KMOV = 30;
	 int  KDORM = 20;
	 int KPESOFIX=2;
	 int KMOVFIX=10;
	 int KDORMFIX=5;

	                	                
	     public class TIME{
	            int dia, mes, anyo;
	            }
	                
	    
	     PUNTOS P;

	     String caso, Regla;
	    public int  Etapa=0, EtapaAnt;
	     int idioma=0, actor=0, intervalo;
	 //
	 //  Eventos extraordinarios
	 //
	     boolean CapturaSensor=true, CapturaPHQ9=true, BrughaChange = false;        
	 //
	 //  Variables temporales, historiales y evolutivas
	 //
	    boolean HistSuicidio = false, Recurrence = false,
	         CambioEtapa=false, PrimeraVez=true;
	    int  TsinCambio=0;
	    int TBrugha=26 , TCapturaSensor=40;
	
	 public int Etapa (String Regla,int P2,int P1,int PI, int SemMed, int SemRem, boolean Relap, int EtapaAnt) {
		 Boolean mal, MuyBien,mejora;
		 	 	
		    int Etapa=0;
		    int Det=P2-PI;		    
		    mal = Regla==system_messages.getString("RE10") || Regla==system_messages.getString("RE11")  || Regla==system_messages.getString("RE12") || Regla==system_messages.getString("RE13") ||  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		          Regla==system_messages.getString("RE14") || Regla==system_messages.getString("RE15")  || Regla==system_messages.getString("RE16")| Regla==system_messages.getString("RE17") || Regla==system_messages.getString("RE18"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		    
		    MuyBien =Regla==system_messages.getString("RE1") || Regla==system_messages.getString("RE3") || Regla==system_messages.getString("RE4"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		    mejora = Regla==system_messages.getString("RE1") || Regla==system_messages.getString("RE2") || Regla==system_messages.getString("RE3") || Regla==system_messages.getString("RE4")||
		    		Regla==system_messages.getString("RE6") || Regla==system_messages.getString("RE7") || Regla==system_messages.getString("RE8") || Regla==system_messages.getString("RE9");
	    
		    if ((P2 <= K_Normal ) &&  ((SemRem>=TcontMin && !Relap) || (SemRem>=TcontMax && Relap)) ) Etapa=8;    //Recuperado
		    else if (((EtapaAnt==3 || EtapaAnt==4 || EtapaAnt==5 || EtapaAnt==6) & mal) || (EtapaAnt==7 & !mejora)) Etapa=7;                // Reca��da
		    
		    else if ((P2 <= K_Normal) && (SemRem<TcontMax) && Relap) Etapa=6;        // Mantenimiento	    
		    else if ((P2 <= K_Normal) && (SemRem<TcontMin))  Etapa=5;     // Continuacion
		    
		    else if (P2 <= K_Normal && !mal) Etapa=4;                                      // Remision
		    else if (P2 < 0.5*PI) Etapa=3;                                              // Respuesta Consolidada
		    //-----Error---- Muy bien y Muy mal en las reglas de 2 puntos son diferente!
		    else if (Det > -(K_Ini) && !MuyBien ) Etapa=1;                                     // Estado inicial
		    else Etapa=2;                                                                    // Respuesta Parcial
		    
		    return Etapa;
	 };
	 
	//  EVALUACION DE REGLAS  ----    MADRIM
//	
	 public void EvaluaEtapa (int Etapa, PUNTOS P,PEM_M Mess,int SemanaMedicacion,int SemanaRemision,MESSAGES messages,boolean Remision,boolean Relapse,boolean BrughaChange,boolean CapturaPHQ9, boolean CapturaSensor, boolean AlarmPreExistance){
		 
   if (Etapa==1) EvaluaEtapa1(P,Mess.Regla,SemanaMedicacion,messages,Remision,SemanaRemision);
   else if (Etapa==2) EvaluaEtapa2(Mess.Regla,SemanaMedicacion,messages,Remision,SemanaRemision);
   else if (Etapa==3) EvaluaEtapa3(Mess.Regla,SemanaMedicacion,messages,Remision,SemanaRemision);
   else if (Etapa==4) EvaluaEtapa4(Mess.Regla,SemanaMedicacion,messages,Remision,SemanaRemision);
   else if (Etapa==5) EvaluaEtapa5(Mess.Regla,SemanaMedicacion,messages,Remision,SemanaRemision);
   else if (Etapa==6) EvaluaEtapa6(Mess.Regla,SemanaMedicacion,messages,Remision,SemanaRemision);
   else if (Etapa==7) EvaluaEtapa7(Mess.Regla,SemanaMedicacion,messages,Relapse,Remision,SemanaRemision);
   else if (Etapa==8) EvaluaEtapa8(Mess.Regla,SemanaMedicacion,messages,Remision,SemanaRemision);
   
   //we put today to detect very big changes! 07/12
	if (P.P2 - P.P0 >= K31) {
 		messages.txt1[5]=system_messages.getString("E18");
 		messages.Semafor=Amarillo;
 	} 
   
   if(BrughaChange) {
      messages.txt1[6]= system_messages.getString("BR13"); //$NON-NLS-1$
      if(messages.Semafor!=Rojo) messages.Semafor=Amarillo;
      }
   if (!CapturaPHQ9) {
	     messages.txt1[7]= system_messages.getString("NC1")+ (P.T2-P.T1)  +system_messages.getString("SEM"); //$NON-NLS-1$ //$NON-NLS-2$
      if(messages.Semafor!=Rojo) messages.Semafor=Amarillo;
      } 				
   if (!CapturaSensor) {
	     messages.txt1[8] = system_messages.getString("NC2"); //$NON-NLS-1$
      if(messages.Semafor!=Rojo) messages.Semafor=Amarillo;
      }

   if (AlarmPreExistance && messages.Semafor==Verde) {
	     messages.txt1[9] = system_messages.getString("TR13"); //$NON-NLS-1$
      messages.Semafor=Amarillo;
      }
  // AlarmPreExistance=(messages.Semafor==Rojo);	
   switch (messages.Semafor){
          case Verde:    messages.txt1[0]= system_messages.getString("SE1"); break; //$NON-NLS-1$
          case Amarillo: messages.txt1[0]= system_messages.getString("SE2"); break; //$NON-NLS-1$
          case Rojo:     messages.txt1[0]= system_messages.getString("SE3"); break; //$NON-NLS-1$
          default:       messages.txt1[0]= system_messages.getString("SE4"); //$NON-NLS-1$
          }   
	 };
   
   //
// ***********  Sensor Evaluator    **********************************
//
	 
	 public void EvaluaSensor(PUNTOS P, MESSAGES messages){
		      int PP;
		      PP = 100*(P.Peso2-P.Peso0)/P.Peso0;
		      if (PP>=KPESO){
			     messages.txt1[8] = system_messages.getString("S11"); //$NON-NLS-1$
		         if(messages.Semafor!=Rojo) messages.Semafor=Amarillo;
		         }
		      else if (PP<KPESO & PP!=0){
			     messages.txt1[8] = system_messages.getString("S13"); //$NON-NLS-1$
		         if(messages.Semafor!=Rojo) messages.Semafor=Amarillo;
		         }
		      else  messages.txt1[8] = system_messages.getString("S12");		       //$NON-NLS-1$
		//
		// Sensor Sue��o
		//
		      int SS;
		      SS = 100*(P.Dorm2-P.Dorm0)/P.Dorm0;
		      if (SS>=KDORM){
			     messages.txt1[12] = system_messages.getString("S21"); //$NON-NLS-1$
		         if(messages.Semafor!=Rojo) messages.Semafor=Amarillo;
		         }
		      else if (SS<KDORM & SS!=0){
			     messages.txt1[12] = system_messages.getString("S23"); //$NON-NLS-1$
		         if(messages.Semafor!=Rojo) messages.Semafor=Amarillo;
		         }
		      else  messages.txt1[12] = system_messages.getString("S22");		       //$NON-NLS-1$
		//
		// Sensor Movimiento
		//
		      int MM;
		      MM = 100*(P.Mov2-P.Mov0)/P.Mov0;
		      if (MM>=KMOV){
			     messages.txt1[13] = system_messages.getString("S31"); //$NON-NLS-1$
		         if(messages.Semafor!=Rojo) messages.Semafor=Amarillo;
		         }
		      else if (MM<KMOV & MM!=0){
			     messages.txt1[13] = system_messages.getString("S33"); //$NON-NLS-1$
		         if(messages.Semafor!=Rojo) messages.Semafor=Amarillo;
		         }
		      else  messages.txt1[13] = system_messages.getString("S32"); //$NON-NLS-1$
		   }	 
	 	 
	// ***********  Patient Evaluation   **********************************	 
	 
 public PEM_M PEM(PUNTOS P, int semanas){
   
   int Dir=0, Vel=0, Cal=0, KK2, KK3;
   float D1, D2, D, Det;
   float DeltaT,PP1;
   PEM_M M = new PEM_M();
      
//
// Tres puntos (Las constantes asumen 4 semanas de cambio con Deltat=1;
// Dos puntos se asumen DeltaT semanas de cambio
//
   DeltaT = (float)semanas / Ksemanas;
  // if(semanas !=4) PP1 =(float) (P.P2+P.P0)/2; // Estado intermedio interpolado
   if(semanas !=Ksemanas) PP1 = P.P0;              // Estado intermedio copiado
   else PP1 =  P.P1;  
   
   D1 =(float) (PP1-P.P0)/DeltaT;  
   D2 =(float) (P.P2-PP1)/DeltaT;  
   D  =(float) (P.P2-P.P0)/DeltaT;  
   Det=(float) P.P2-P.PI;
/*
   cout<< "\t\t\t\t P0="<< P.P0 <<"   PP1="<< PP1 <<"   P2="<< P.P2 
       << "   D1="<< D1 << "   D2="<< D2 <<"   D="<< D 
       << "   Det="<< Det << "   DetltaT="<< DeltaT <<endl;
*/
//
//Direccion del cambio			
//
//  if (D < -K1)  Dir=Mejora; 
  if (D < -K1)  Dir=Mejora;  // he quitado este && (D2!=0)
  else if (D > K1 ) Dir=Empeora;
       else Dir=SinCambio;			
//
//Velocidad del cambio
//
  if(Dir==SinCambio) Vel=Cero;
  else{
      if(Dir==Empeora){KK2=K5; KK3=K6;}
      else {KK2=K2; KK3=K3;}
      if (Math.abs(D) < KK2) Vel=Lento;
      else if((Math.abs(D)>= KK2) && (Math.abs(D)< KK3)) Vel=Normal;
      else if (Math.abs(D)>= KK3) Vel=Rapido;
      }
//						
//Calidad del cambio	
//			
     if ((D1*D2 > 0)|(Math.abs(D1)<= K1 && Math.abs(D2) <= K1)) Cal=Sostenida;			
     else if (D1*D2 < 0) Cal=Oscilante;
          else if ((Math.abs(D1)<= K1 && !(Math.abs(D2)<=K1)) || !(Math.abs(D1) <= K1 && Math.abs(D2) <= K1)){
		  		   Cal=Advertida;}
//
//Evaluaci��n PEM
//
	if (Dir==Mejora)
		if(Vel==Rapido)
			if(Cal==Sostenida) M.Regla=system_messages.getString("RE1"); //$NON-NLS-1$
			else if(Cal==Oscilante) M.Regla=system_messages.getString("RE2"); //$NON-NLS-1$
			        else if(Cal==Advertida) M.Regla=system_messages.getString("RE3"); //$NON-NLS-1$
                        else M.Regla=system_messages.getString("ERR"); //$NON-NLS-1$
		else if(Vel==Normal)
			if(Cal==Sostenida) M.Regla=system_messages.getString("RE4"); //$NON-NLS-1$
			else if(Cal==Oscilante) M.Regla=system_messages.getString("RE5"); //$NON-NLS-1$
	  			else if(Cal==Advertida) M.Regla=system_messages.getString("RE6"); //$NON-NLS-1$
                    else M.Regla=system_messages.getString("ERR"); //$NON-NLS-1$
		else if(Vel==Lento)
			if(Cal==Sostenida) M.Regla=system_messages.getString("RE7"); //$NON-NLS-1$
			else if(Cal==Oscilante) M.Regla=system_messages.getString("RE8"); //$NON-NLS-1$
	 			else if(Cal==Advertida) M.Regla=system_messages.getString("RE9"); //$NON-NLS-1$
					 else M.Regla=system_messages.getString("ERR"); //$NON-NLS-1$
			else M.Regla=system_messages.getString("ERR"); //$NON-NLS-1$
	else if  (Dir==Empeora)
		if(Vel==Rapido)
			if(Cal==Sostenida) M.Regla=system_messages.getString("RE10"); //$NON-NLS-1$
			else if(Cal==Oscilante) M.Regla=system_messages.getString("RE11"); //$NON-NLS-1$
			        else if(Cal==Advertida) M.Regla=system_messages.getString("RE12"); //$NON-NLS-1$
                        else M.Regla=system_messages.getString("ERR"); //$NON-NLS-1$
		else if(Vel==Normal)
			if(Cal==Sostenida) M.Regla=system_messages.getString("RE13"); //$NON-NLS-1$
			else if(Cal==Oscilante) M.Regla=system_messages.getString("RE14"); //$NON-NLS-1$
	  			else if(Cal==Advertida) M.Regla=system_messages.getString("RE15"); //$NON-NLS-1$
                    else M.Regla=system_messages.getString("ERR"); //$NON-NLS-1$
		else if(Vel==Lento)
			if(Cal==Sostenida) M.Regla=system_messages.getString("RE16"); //$NON-NLS-1$
			else if(Cal==Oscilante) M.Regla=system_messages.getString("RE17"); //$NON-NLS-1$
	 			else if(Cal==Advertida) M.Regla=system_messages.getString("RE18"); //$NON-NLS-1$
					else M.Regla=system_messages.getString("ERR"); //$NON-NLS-1$
			else M.Regla=system_messages.getString("ERR"); //$NON-NLS-1$
	else if (Dir==SinCambio)
		if(Cal==Sostenida) M.Regla=system_messages.getString("RE19"); //$NON-NLS-1$
		else  if(Cal==Oscilante) M.Regla=system_messages.getString("RE20"); //$NON-NLS-1$
			  else M.Regla=system_messages.getString("ERR"); //$NON-NLS-1$
	else M.Regla=system_messages.getString("ERR"); //$NON-NLS-1$

   switch (Dir){
         case Empeora:   M.Tdir=system_messages.getString("DIR1"); break; //$NON-NLS-1$
         case SinCambio: M.Tdir=system_messages.getString("DIR2"); break; //$NON-NLS-1$
         case Mejora:    M.Tdir=system_messages.getString("DIR3"); break; //$NON-NLS-1$
         default:        M.Tdir=system_messages.getString("DIR4"); //$NON-NLS-1$
         }
   switch (Vel){
         case Lento:  M.Tvel=system_messages.getString("VEL1"); break; //$NON-NLS-1$
         case Normal: M.Tvel=system_messages.getString("VEL2"); break; //$NON-NLS-1$
         case Rapido: M.Tvel=system_messages.getString("VEL3"); break;           //$NON-NLS-1$
         default:     M.Tvel=system_messages.getString("VEL4"); //$NON-NLS-1$
         }
   switch (Cal){
         case Advertida: M.Tcal=system_messages.getString("CAL1"); break; //$NON-NLS-1$
         case Sostenida: M.Tcal=system_messages.getString("CAL2"); break; //$NON-NLS-1$
         case Oscilante: M.Tcal=system_messages.getString("CAL3"); break; //$NON-NLS-1$
         default:        M.Tcal=system_messages.getString("CAL4"); //$NON-NLS-1$
         }    
   return M;
    }
//

public String PEM_Rule(){
	String pem_rule=""; //$NON-NLS-1$
	
	return pem_rule;
}


//
//***************************   Etapa Inicial   *******************************
// 
public void EvaluaEtapa1(PUNTOS P, String Regla, int SemanaMedicacion, MESSAGES messages,boolean Remision,int SemanaRemision){

	String c=" "; //$NON-NLS-1$
	messages.txt1[1]= system_messages.getString("ET1"); //$NON-NLS-1$
	int D=P.PI-P.P2;

	
	if (Regla==system_messages.getString("RE1") || Regla==system_messages.getString("RE2") || Regla==system_messages.getString("RE3") ) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
     messages.txt1[5]=system_messages.getString("E11");  //$NON-NLS-1$
     messages.Semafor=Verde;
     	}
	else if (Regla==system_messages.getString("RE4") || Regla==system_messages.getString("RE5") || Regla==system_messages.getString("RE6")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			  messages.txt1[5] =system_messages.getString("E12");  //$NON-NLS-1$
           messages.Semafor=Verde;
           }	
	else if (Regla==system_messages.getString("RE7") || Regla==system_messages.getString("RE8") || Regla==system_messages.getString("RE9")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			  messages.txt1[5] =system_messages.getString("E13");  //$NON-NLS-1$
           messages.Semafor=Verde;
           }	
	else if (Regla==system_messages.getString("RE19") || Regla==system_messages.getString("RE20") ) {                         //$NON-NLS-1$ //$NON-NLS-2$
           messages.txt1[5]= system_messages.getString("E14");  //$NON-NLS-1$
           if(SemanaMedicacion < TmaxSR) messages.Semafor=Verde;
           else messages.Semafor=Amarillo;
           } 
	else if (Regla==system_messages.getString("RE16") || Regla==system_messages.getString("RE17") || Regla==system_messages.getString("RE18") ){  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	          messages.txt1[5]= system_messages.getString("E15"); //$NON-NLS-1$
           messages.Semafor=Amarillo;
           }
	else if (Regla==system_messages.getString("RE10") || Regla==system_messages.getString("RE11") || Regla==system_messages.getString("RE12") ) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			  messages.txt1[5]= system_messages.getString("E16");  //$NON-NLS-1$
           messages.Semafor=Rojo;
           } 				
	else if (Regla==system_messages.getString("RE13") || Regla==system_messages.getString("RE14") || Regla==system_messages.getString("RE15") ) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	          messages.txt1[5]= system_messages.getString("E17"); //$NON-NLS-1$
           messages.Semafor=Rojo;
           }                                 
 if (SemanaMedicacion >= TmaxSR){
	 String S=new Integer(SemanaMedicacion).toString();
     if(D<=K1){                               
               messages.txt1[9] = system_messages.getString("TR11"); //$NON-NLS-1$
               messages.txt1[9] += c + S;
               messages.txt1[11]= system_messages.getString("AL1");  //$NON-NLS-1$
               if(SemanaMedicacion > TmaxSR) messages.Semafor=Rojo;
               else if(messages.Semafor!=Rojo)messages.Semafor=Amarillo; 
               }
     else if(D>K1 & D<K_Ini){   
               messages.txt1[9] = system_messages.getString("TR12");  //$NON-NLS-1$
               messages.txt1[9] += c + S;
               messages.txt1[11]= system_messages.getString("AL2"); //$NON-NLS-1$
               if(SemanaMedicacion > TmaxSR+2) messages.Semafor=Rojo;
               else if(messages.Semafor!=Rojo)messages.Semafor=Amarillo; 
               }
          else {
               messages.txt1[9] = system_messages.getString("TR14");                    //$NON-NLS-1$
               messages.txt1[11]= system_messages.getString("AL3"); //$NON-NLS-1$
               }

     }
	};
//
//**********************     Respuesta Parcial     ****************************
//
void EvaluaEtapa2(String Regla , int SemanaMedicacion, MESSAGES messages,boolean Remision,int SemanaRemision){
	
	messages.txt1[1]= system_messages.getString("ET2");				  //$NON-NLS-1$
	
	if (Regla==system_messages.getString("RE1") || Regla==system_messages.getString("RE2") || Regla==system_messages.getString("RE3")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 	messages.txt1[5]=system_messages.getString("E21"); //$NON-NLS-1$
     messages.Semafor=Verde;
	} 
	else if (Regla==system_messages.getString("RE4") || Regla==system_messages.getString("RE5") || Regla==system_messages.getString("RE6") || Regla==system_messages.getString("RE7") ||  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
          Regla==system_messages.getString("RE8") || Regla==system_messages.getString("RE9")) { //$NON-NLS-1$ //$NON-NLS-2$
	         messages.txt1[5] = system_messages.getString("E22"); //$NON-NLS-1$
          messages.Semafor=Verde;
          }
 else if (Regla==system_messages.getString("RE19") || Regla==system_messages.getString("RE20") ) { //$NON-NLS-1$ //$NON-NLS-2$
          messages.txt1[5] = system_messages.getString("E23"); //$NON-NLS-1$
          messages.Semafor=Amarillo;
          } 
 else if (Regla==system_messages.getString("RE11") || Regla==system_messages.getString("RE14") || Regla==system_messages.getString("RE15") || Regla==system_messages.getString("RE17") | //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
          Regla==system_messages.getString("RE18") ) { //$NON-NLS-1$
          messages.txt1[5] = system_messages.getString("E24"); //$NON-NLS-1$
          messages.Semafor=Amarillo;
          }
	else if (Regla==system_messages.getString("RE10") || Regla==system_messages.getString("RE12") ) { //$NON-NLS-1$ //$NON-NLS-2$
			 messages.txt1[5] =  system_messages.getString("E25"); //$NON-NLS-1$
          messages.Semafor=Rojo;
          } 
 else if (Regla==system_messages.getString("RE13") || Regla==system_messages.getString("RE16") ) { //$NON-NLS-1$ //$NON-NLS-2$
          messages.txt1[5] =  system_messages.getString("E26"); //$NON-NLS-1$
          messages.Semafor=Rojo;
          }                      
 };
//
//**********************   Respuesta consolidada     **********************     
//			 
void EvaluaEtapa3(String Regla , int SemanaMedicacion, MESSAGES messages,boolean Remision,int SemanaRemision){
                  messages.txt1[1]= system_messages.getString("ET3");				 				  //$NON-NLS-1$
                  
				 if (Regla==system_messages.getString("RE1") || Regla==system_messages.getString("RE2") || Regla==system_messages.getString("RE3")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 					messages.txt1[5]=system_messages.getString("E31"); //$NON-NLS-1$
                     messages.Semafor=Verde;
				 } 
				 
				     else if (Regla==system_messages.getString("RE4") || Regla==system_messages.getString("RE5") || Regla==system_messages.getString("RE6") || Regla==system_messages.getString("RE7") || Regla==system_messages.getString("RE8") || Regla==system_messages.getString("RE9")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
      				messages.txt1[5] = system_messages.getString("E32"); //$NON-NLS-1$
                     messages.Semafor=Verde;
                     }
                  else if (Regla==system_messages.getString("RE19") || Regla==system_messages.getString("RE20") ) { //$NON-NLS-1$ //$NON-NLS-2$
                     messages.txt1[5] = system_messages.getString("E33"); //$NON-NLS-1$
                     messages.Semafor=Amarillo;
                     }
                  else if (Regla==system_messages.getString("RE16") || Regla==system_messages.getString("RE17") || Regla==system_messages.getString("RE18") ) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			                  messages.txt1[5] = system_messages.getString("E34"); //$NON-NLS-1$
                           messages.Semafor=Amarillo;
                           } 					
				     else if (Regla==system_messages.getString("RE10") || Regla==system_messages.getString("RE11") || Regla==system_messages.getString("RE12")| Regla==system_messages.getString("RE13")  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                            || Regla==system_messages.getString("RE14") || Regla==system_messages.getString("RE15") ) { //$NON-NLS-1$ //$NON-NLS-2$
					          messages.txt1[5] = system_messages.getString("E35"); //$NON-NLS-1$
                           messages.Semafor=Amarillo;
                           }
			         else if (Regla==system_messages.getString("RE10") || Regla==system_messages.getString("RE12") ) { //$NON-NLS-1$ //$NON-NLS-2$
					          messages.txt1[5] = system_messages.getString("E36"); //$NON-NLS-1$
					          //habia alarma anterior???
                           messages.Semafor=Rojo;
                           } 					
				     else if (Regla==system_messages.getString("RE13") || Regla==system_messages.getString("RE16") ) { //$NON-NLS-1$ //$NON-NLS-2$
					          messages.txt1[5] = system_messages.getString("E37"); //$NON-NLS-1$
                           messages.Semafor=Rojo;
                           } 
                  };
//
//***************************     Remisi��n     *********************************     
//
void EvaluaEtapa4(String Regla , int SemanaMedicacion, MESSAGES messages,boolean Remision,int SemanaRemision){

                  messages.txt1[1]= system_messages.getString("ET4");				 				  //$NON-NLS-1$
                  
                 
				     if (Regla==system_messages.getString("RE1") || Regla==system_messages.getString("RE2") || Regla==system_messages.getString("RE3")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 					messages.txt1[5]=system_messages.getString("E41"); //$NON-NLS-1$
                     messages.Semafor=Verde;
				     } 
				     else if (Regla==system_messages.getString("RE4") || Regla==system_messages.getString("RE5") || Regla==system_messages.getString("RE6") || Regla==system_messages.getString("RE7") || Regla==system_messages.getString("RE8") || Regla==system_messages.getString("RE9")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
      				messages.txt1[5] = system_messages.getString("E42"); //$NON-NLS-1$
                     messages.Semafor=Verde;
                     }
                  else if (Regla==system_messages.getString("RE19") || Regla==system_messages.getString("RE20") ) { //$NON-NLS-1$ //$NON-NLS-2$
                     messages.txt1[5] = system_messages.getString("E43"); //$NON-NLS-1$
                     messages.Semafor=Verde;
                     }                        
                  else if (Regla==system_messages.getString("RE16") || Regla==system_messages.getString("RE17") || Regla==system_messages.getString("RE18") ) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			                  messages.txt1[5] = system_messages.getString("E44"); //$NON-NLS-1$
                           messages.Semafor=Amarillo;
                           } 					
				     else if (Regla==system_messages.getString("RE10") || Regla==system_messages.getString("RE11") || Regla==system_messages.getString("RE12")| Regla==system_messages.getString("RE13")  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                            || Regla==system_messages.getString("RE14") || Regla==system_messages.getString("RE15") ) { //$NON-NLS-1$ //$NON-NLS-2$
					          messages.txt1[5] = system_messages.getString("E45"); //$NON-NLS-1$
                           messages.Semafor=Amarillo;
                           }
			         else if (Regla==system_messages.getString("RE10") || Regla==system_messages.getString("RE12") ) { //$NON-NLS-1$ //$NON-NLS-2$
					          messages.txt1[5] = system_messages.getString("E46"); //$NON-NLS-1$
                           messages.Semafor=Rojo;
                           } 					
				     else if (Regla==system_messages.getString("RE13") || Regla==system_messages.getString("RE16") ) { //$NON-NLS-1$ //$NON-NLS-2$
					          messages.txt1[5] = system_messages.getString("E47"); //$NON-NLS-1$
                           messages.Semafor=Rojo;
                           } 
                  };
//
//***************************     Continuaci��n     *********************************     
//
void EvaluaEtapa5(String Regla , int SemanaMedicacion,MESSAGES messages,boolean Remision,int SemanaRemision){

				
                  messages.txt1[1]= system_messages.getString("ET6");				 				  //$NON-NLS-1$
                  

               	
				     if (Regla==system_messages.getString("RE1") || Regla==system_messages.getString("RE2") || Regla==system_messages.getString("RE3")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 					messages.txt1[5]=system_messages.getString("E51"); //$NON-NLS-1$
                     messages.Semafor=Verde;
				     } 
				     else if (Regla==system_messages.getString("RE4") || Regla==system_messages.getString("RE5") || Regla==system_messages.getString("RE6") || Regla==system_messages.getString("RE7") || Regla==system_messages.getString("RE8") || Regla==system_messages.getString("RE9")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
      				messages.txt1[5] = system_messages.getString("E52"); //$NON-NLS-1$
                     messages.Semafor=Verde;
                     }
                  else if (Regla==system_messages.getString("RE19") || Regla==system_messages.getString("RE20") ) { //$NON-NLS-1$ //$NON-NLS-2$
                     messages.txt1[5] = system_messages.getString("E53"); //$NON-NLS-1$
                     messages.Semafor=Verde;
                     }                        
                  else if (Regla==system_messages.getString("RE16") || Regla==system_messages.getString("RE17") || Regla==system_messages.getString("RE18") ) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			                  messages.txt1[5] = system_messages.getString("E54"); //$NON-NLS-1$
                           messages.Semafor=Amarillo;
                           } 					
				     else if (Regla==system_messages.getString("RE10") || Regla==system_messages.getString("RE11") || Regla==system_messages.getString("RE12")| Regla==system_messages.getString("RE13")  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                            || Regla==system_messages.getString("RE14") || Regla==system_messages.getString("RE15") ) { //$NON-NLS-1$ //$NON-NLS-2$
					          messages.txt1[5] = system_messages.getString("E55"); //$NON-NLS-1$
                           messages.Semafor=Amarillo;
                           }
			         else if (Regla==system_messages.getString("RE10") || Regla==system_messages.getString("RE12") ) { //$NON-NLS-1$ //$NON-NLS-2$
					          messages.txt1[5] = system_messages.getString("E56"); //$NON-NLS-1$
                           messages.Semafor=Rojo;
                           } 					
				     else if (Regla==system_messages.getString("RE13") || Regla==system_messages.getString("RE16") ) { //$NON-NLS-1$ //$NON-NLS-2$
					          messages.txt1[5] = system_messages.getString("E57"); //$NON-NLS-1$
                           messages.Semafor=Rojo;
                           } 
                  };
//
//***************************     Mantenimiento     *********************************     
//
void EvaluaEtapa6(String Regla , int SemanaMedicacion, MESSAGES messages,boolean Remision,int SemanaRemision){

                  messages.txt1[1]= system_messages.getString("ET5");				 				  //$NON-NLS-1$
                  
                 
                  
				     if (Regla==system_messages.getString("RE1") || Regla==system_messages.getString("RE2") || Regla==system_messages.getString("RE3")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 					messages.txt1[5]=system_messages.getString("E61"); //$NON-NLS-1$
                     messages.Semafor=Verde;
				     } 
				     else if (Regla==system_messages.getString("RE4") || Regla==system_messages.getString("RE5") || Regla==system_messages.getString("RE6") || Regla==system_messages.getString("RE7") || Regla==system_messages.getString("RE8") || Regla==system_messages.getString("RE9")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
      				messages.txt1[5] = system_messages.getString("E62"); //$NON-NLS-1$
                     messages.Semafor=Verde;
                     }
                  else if (Regla==system_messages.getString("RE19") || Regla==system_messages.getString("RE20") ) { //$NON-NLS-1$ //$NON-NLS-2$
                     messages.txt1[5] = system_messages.getString("E63"); //$NON-NLS-1$
                     messages.Semafor=Verde;
                     }                        
                  else if (Regla==system_messages.getString("RE16") || Regla==system_messages.getString("RE17") || Regla==system_messages.getString("RE18") ) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			                  messages.txt1[5] = system_messages.getString("E64"); //$NON-NLS-1$
                           messages.Semafor=Amarillo;
                           } 					
				     else if (Regla==system_messages.getString("RE10") || Regla==system_messages.getString("RE11") || Regla==system_messages.getString("RE12")| Regla==system_messages.getString("RE13")  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                            || Regla==system_messages.getString("RE14") || Regla==system_messages.getString("RE15") ) { //$NON-NLS-1$ //$NON-NLS-2$
					          messages.txt1[5] = system_messages.getString("E65"); //$NON-NLS-1$
                           messages.Semafor=Amarillo;
                           }
			         else if (Regla==system_messages.getString("RE10") || Regla==system_messages.getString("RE12") ) { //$NON-NLS-1$ //$NON-NLS-2$
					          messages.txt1[5] = system_messages.getString("E66"); //$NON-NLS-1$
                           messages.Semafor=Rojo;
                           } 					
				     else if (Regla==system_messages.getString("RE13") || Regla==system_messages.getString("RE16") ) { //$NON-NLS-1$ //$NON-NLS-2$
					          messages.txt1[5] = system_messages.getString("E67"); //$NON-NLS-1$
                           messages.Semafor=Rojo;
                           } 
                  };
//
//***************************     Reca��da     *********************************     
//
void EvaluaEtapa7(String Regla , int SemanaMedicacion, MESSAGES messages, boolean  Relapse,boolean Remision,int SemanaRemision){

            messages.txt1[1]= system_messages.getString("ET7");				 				  //$NON-NLS-1$
            messages.txt1[11]= system_messages.getString("AL4");				 				  //$NON-NLS-1$
            messages.Semafor=Rojo;
            if (Regla==system_messages.getString("RE19") || Regla==system_messages.getString("RE20") ) { //$NON-NLS-1$ //$NON-NLS-2$
                     messages.txt1[5] = system_messages.getString("ET71"); //$NON-NLS-1$
                     messages.txt1[9] = system_messages.getString("TR15"); //$NON-NLS-1$
                     }                        
            else if (Regla==system_messages.getString("RE16") || Regla==system_messages.getString("RE17") || Regla==system_messages.getString("RE18") ) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                     messages.txt1[5] = system_messages.getString("ET72"); //$NON-NLS-1$
                     messages.txt1[9] = system_messages.getString("TR16"); //$NON-NLS-1$
                           } 					
			  else if (Regla==system_messages.getString("RE10") || Regla==system_messages.getString("RE11") || Regla==system_messages.getString("RE12") || Regla==system_messages.getString("RE13")  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                            || Regla==system_messages.getString("RE14") || Regla==system_messages.getString("RE15") ) { //$NON-NLS-1$ //$NON-NLS-2$
		                messages.txt1[5] = system_messages.getString("ET73"); //$NON-NLS-1$
                     messages.txt1[9] = system_messages.getString("TR17"); //$NON-NLS-1$
                           }
            }  

void EvaluaEtapa8(String Regla , int SemanaMedicacion, MESSAGES messages,boolean Remision,int SemanaRemision){
	messages.txt1[1]=system_messages.getString("ET8");
	
}

void SuicidioMensaje(int MiniSum){
	
    if(MiniSum>=KSuicidio_alto) {
        messages.txt1[13]="Riesgo Suicidio Alto"; 
        messages.txt1[12]="Riesgo Suicidio Alto";
        messages.Semafor=Rojo;
        }   
     else if(MiniSum>=KSuicidio_moderado){
        messages.txt1[13]="Riesgo Suicidio Moderado"; 
        messages.txt1[12]="Riesgo Suicidio Moderado";
        if(messages.Semafor!=Rojo) messages.Semafor=Amarillo;
        }
     else if(MiniSum>=KSuicidio_leve) 
        messages.txt1[13]="Riesgo Suicidio Bajo";
     else messages.txt1[12]="Sin riesgo de suicidio ";
     }
}


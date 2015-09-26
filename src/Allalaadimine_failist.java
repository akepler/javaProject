/**
 * Klass, mille meetodite abil leiame, mitu rida on failis, kust andmeid loeme;
 * leiame kıigist faili salvestatud ridadest temperatuurid ja ajad ning loome neist maatriksi;
 * leiame soovitud ajavahemiku j‰rgi temperatuuri v‰‰rtused ja loome neist maatriksi;
 * moodustame soovitud arvu viimaste temperatuuri v‰‰rtuste pıhjal maatriksi
 * 
 * @author Aivo Toots, Andres Kepler
 *
 */

import java.io.*;
import java.text.*;
import java.util.*;

public class Allalaadimine_failist {
	
// Klassimuutujad:	
	
	static String failinimi; // Andmete faili nimi
	static int ridu_failis; // Ridade arv failis
	static String [][] v22rtused; // Failist loetud v‰‰rtused

// Konstruktor:
	
	Allalaadimine_failist (String fail) throws Exception{
		
		failinimi = fail;
		
		// M‰‰rame ridade arvu failis
	      
		Ridu_failis();
		
		// Loeme faili rida-haaval ja vıtame sealt v‰lja aja ja temperatuuri v‰‰rtused ning moodustame neist maatriksi
	      
		BufferedReader reader = new BufferedReader(new FileReader(failinimi));
			
		int i=0;

		String[][] a = new String[ridu_failis][2];

		while(reader.ready()) {
				String rida = reader.readLine();
				String[] tykid = rida.split(",");
				a[i][0] = tykid[1];
				a[i][1] = tykid[2];
				i++;

		}
	      
		v22rtused = a;

		reader.close();

	      
	}
	
// Meetodid:

	// Meetod, millega leiame j‰rgmise kahe meetodi tarvis ridade arvu failis
	
	public static int Ridu_failis() throws Exception {
		
		int lines = 0;
		
		try {
		
			BufferedReader reader = new BufferedReader(new FileReader(failinimi));
		
			while (reader.readLine() != null) lines++;
		
			reader.close();
		
		} catch (Exception e) {
			
			System.out.println(e);
			
		}
		
		return ridu_failis = lines;
		
	}

	// Meetod, mille abil moodustame kıigist faili sisestatud andmetest maatriksi
	
	public static String[][] K6ik_andmed_failist() throws Exception {
		
        return v22rtused;
    }

	// Meetod, mille abil moodustame soovitud arvu viimaste ridade pıhjal maatriksi	
	
	public static String[][] Andmed_failist(int ridu) throws Exception {
		
		String[][] a = new String[ridu][2];
	
		for (int i = 0; i < ridu; i++) {
		
			a[i][0]=v22rtused[ridu_failis-ridu+i][0];
			a[i][1]=v22rtused[ridu_failis-ridu+i][1];
		
		}
	
		return a;
    }
	
	// Meetod, mille abil moodustame soovitud kuup‰evade pıhjal maatriksi	

	/*
	 * @param akp1 alguskuup‰ev
	 * @param lkp2 lıpukuup‰ev
	 */
	
	public static String[][] Andmed_failist_kp(String akp1, String lkp2) throws Exception {

		// Kuna me ei tea, kui pikk vıib array olla, siis kasutame ArrayList klassi
		
	    ArrayList<String> kuupaevad = new ArrayList<String>();
	    ArrayList<String> temperatuurid = new ArrayList<String>();
	    	
		try {
				 
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        
	    	Date alguskp = sdf.parse(akp1); // Alguskuup‰ev
	        Date loppkp = sdf.parse(lkp2); // Lıppkuup‰ev
	 
	        // Moodustame kolm kalendrit, et neid hiljem vırrelda
	        
	        Calendar cal1 = Calendar.getInstance();
	        Calendar cal2 = Calendar.getInstance();
	        Calendar cal3 = Calendar.getInstance();
	        	
	        // Seadistame kalendritele kuup‰evad
	        	
	        cal1.setTime(alguskp);
	        cal2.setTime(loppkp);
	        	
	        for (int i=0;i<ridu_failis;i++) {
	    			
	    		StringBuilder StrkpFailist = new StringBuilder(); 
	    		
	    		// Lammutame kuup‰eva juppideks T j‰rgi
	    		
	    		String [] kp1 = v22rtused[i][0].split("T");
	    		
	    		// Lammutame kellaaja juppideks . j‰rgi
	    		
	    		String [] time = kp1[1].split("\\.");
	    		
	    		// Lihtsamaks kasutamiseks seadistame kuup‰eva ja kellaaja v‰‰rtused
	    		
	    		String kuupaev = kp1[0];
	    		String kell = time[0];
	    		
	    		// Moodustame leitud juppidest stringi
	    		
	    		StrkpFailist.append(kuupaev);
	    		StrkpFailist.append(" ");
	    		StrkpFailist.append(kell);
	    			
	    		Date kpFailist = sdf.parse(StrkpFailist.toString());
	    		
	    		// M‰‰rame kolmandas kalendris kuup‰evaks leitud kuup‰eva
	    		
	    		cal3.setTime(kpFailist);
	    		
	    		// Vırdleme, kas kolmas kalender on cal1 ja cal2 vahemikus
	    		
	    		if (cal1.before(cal3) && cal2.after(cal3)) {
	    				
	    			String temp = v22rtused[i][1];
	    			kuupaevad.add(StrkpFailist.toString());
	    			temperatuurid.add(temp);

	    		}
	   
	    	}
	    		
	    	// Kui kuup‰evade ja temperatuuride arrayd on ¸hepikkused, moodustame nendest v‰ljundmassiivi
	    	
	        if (kuupaevad.size() == temperatuurid.size()){
	    		
	        	String [][] a = new String[kuupaevad.size()][2];
	    			
	        	for (int i=0;i<a.length;i++){
	        		
	        		a[i][0]=kuupaevad.get(i);
	    	        a[i][1]=temperatuurid.get(i);
	    	    		
	    		} 
	    		
	        	return a;
	    		
	        } else {
	    		
	        	return null;
	    	
	        }
	    		
	    } catch(ParseException ex) {
	    	
	    	System.out.println("Viga");
			ex.printStackTrace();

	    }
		
		return null;
				
	}

// Getterid ja setterid:
	
	public static String getFailinimi() {
		return failinimi;
	}

	public static void setFailinimi(String failinimi) {
		Allalaadimine_failist.failinimi = failinimi;
	}

	public static int getRidu_failis() {
		return ridu_failis;
	}

	public static void setRidu_failis(int ridu_failis) {
		Allalaadimine_failist.ridu_failis = ridu_failis;
	}

	public static String[][] getV22rtused() {
		return v22rtused;
	}

	public static void setV22rtused(String[][] v22rtused) {
		Allalaadimine_failist.v22rtused = v22rtused;
	}
	
}

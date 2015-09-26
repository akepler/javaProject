/**
 * Klass, mis moodustab sisendina saadud andmetest temperatuuride massiivi, leiab neist eksed ja eemaldab need 
 * ning võimaldab leida temperatuuride aritmeetilist kesmist, dispersiooni ja standardhälvet
 * 
 * @author Aivo Toots, Andres Kepler
 *
 */

import java.util.*;


public class Statistika2 {
	
// Klassimuutujad:
	
	static double [] temperatuurid_raw; // Eksetega temperatuuri väärtused
	static double [] temperatuurid; // Temperatuuride väärtused

	static int temperatuuri_v22rtusi; // Temperatuuri väärtuste arv
	
	static double aritm_keskmine_raw; // Eksetega temperatuuri väärtuste aritmeeetiline keskmine
	static double aritm_keskmine; // Temperatuuride aritmeetiline keskmine
	
	static double dispersioon_raw; // Eksetega temperatuuri väärtuste dispersioon
	static double dispersioon; // Temperatuuride dispersiooni
	
	static double standardh2lve_raw; // Eksetega temperatuuri väärtuste standardhälve
	
// Konstruktor:
		
	Statistika2 (String[][] v22rtused) {
		
		// Võtame saadud maatriksist temperatuuri väärtused ja teisendame need double-tüüpi väärtusteks
		
		double temp_v22rtused[] = new double [v22rtused.length];
			
		for (int j = 0; j < v22rtused.length; j++) {
	        	
        	temp_v22rtused[j] = Double.parseDouble(v22rtused[j][1]);
	        	
        }
		
		// Loeme eksetega temperatuurid massiivi
		
		temperatuurid_raw = temp_v22rtused;
		
		// Määrame temperatuuri väärtuste arvu
		
		temperatuuri_v22rtusi = temperatuurid_raw.length;
		
		// Leiame eksetega temperatuuride aritmeetilise keskmise
		
		Aritm_keskmine_raw();
		
		// Leiame eksetega temperatuuride dispersiooni
		
		Dispersioon_raw();
		
		// Leiame eksetega temperatuuride standardhälbe
		
		standardh2lve_raw = Math.sqrt(dispersioon_raw);
		
		// Eemaldame eksed
		
		Eksete_eemaldamine();
		
	}
	
// Meetodid:
	
	// Meetod eksetega temperatuuride aritmeetilise keskmise leidmiseks
	
	public static void Aritm_keskmine_raw() {
			
		double summa = 0;
			
		for (double element : temperatuurid_raw){
					
			summa+= element;
		       	
	    }
			
		aritm_keskmine_raw = summa/temperatuuri_v22rtusi;
			
	}
	
	// Meetod eksetega temperatuuride dispersiooni leidmiseks
	
	public static void Dispersioon_raw() {
		
		double h2lvete_ruutude_summa = 0;
		
		for (int j = 0; j < temperatuuri_v22rtusi; j++) {
	        	
	        h2lvete_ruutude_summa+= Math.pow((temperatuurid_raw[j] - aritm_keskmine_raw),2);
	        	
	    }
			
		dispersioon_raw = h2lvete_ruutude_summa/(temperatuuri_v22rtusi-1);
		
	}
	
	// Meetod eksetega temperatuuride standardhälbe leidmiseks

	public static void Standardh2lve_raw() {
			
		standardh2lve_raw = Math.sqrt(dispersioon_raw);
			
	}
	
	// Meetod eksede leidmiseks ja eemaldamiseks
	
	public static void Eksete_eemaldamine() {
		
		temperatuurid = new double[temperatuuri_v22rtusi];

		// Ekse leidmise piirid (vasakpoolne ja parempoolne kvantiil)		
		
		double v_kvantiil = aritm_keskmine_raw - (3*standardh2lve_raw);
		double p_kvantiil = aritm_keskmine_raw + (3*standardh2lve_raw);
		
		for (int j = 0; j < temperatuuri_v22rtusi; j++) {
				
			if ( temperatuurid_raw[j] < v_kvantiil || temperatuurid_raw[j] > p_kvantiil ) {

				// Asendame ekse väärtusega, mis on valimi aritmeetilise keskmise ja juhusliku arvu (vahemikus 0.0-1.0) summa

				Random rand = new Random(); 
			
				temperatuurid[j] = aritm_keskmine_raw + rand.nextDouble();
				
			} else {
					
				temperatuurid[j] = temperatuurid_raw[j];
				
			}
	        	
        }
		
	}
	
	// Meetod temperatuuride aritmeetilise keskmise leidmiseks
	
	public double Aritm_keskmine() {
		
		double summa = 0;
		
		for (double element : temperatuurid){
				
			summa+= element;
	       	
	    }
			
		return aritm_keskmine = summa/temperatuuri_v22rtusi;
		
	}
	
	// Meetod temperatuuride dispersiooni leidmiseks
	
	public double Dispersioon() {
		
		double h2lvete_ruutude_summa = 0;
		
		for (int j = 0; j < temperatuuri_v22rtusi; j++) {
	        	
	        h2lvete_ruutude_summa+= Math.pow(temperatuurid[j] - aritm_keskmine,2);
	        	
	    }
			
		return dispersioon = h2lvete_ruutude_summa/(temperatuuri_v22rtusi-1);
		
	}
	
	// Meetod temperatuuride standardhälbe leidmiseks
	
	public double Standardh2lve() {
		
		return Math.sqrt(dispersioon);
		
	}
	
// Getterid ja setterid:

	public static double[] getTemperatuurid_raw() {
		return temperatuurid_raw;
	}

	public static void setTemperatuurid_raw(double[] temperatuurid_raw) {
		Statistika2.temperatuurid_raw = temperatuurid_raw;
	}

	public static double[] getTemperatuurid() {
		return temperatuurid;
	}

	public static void setTemperatuurid(double[] temperatuurid) {
		Statistika2.temperatuurid = temperatuurid;
	}

	public static int getTemperatuuri_v22rtusi() {
		return temperatuuri_v22rtusi;
	}

	public static void setTemperatuuri_v22rtusi(int temperatuuri_v22rtusi) {
		Statistika2.temperatuuri_v22rtusi = temperatuuri_v22rtusi;
	}

	public static double getAritm_keskmine_raw() {
		return aritm_keskmine_raw;
	}

	public static void setAritm_keskmine_raw(double aritm_keskmine_raw) {
		Statistika2.aritm_keskmine_raw = aritm_keskmine_raw;
	}

	public static double getAritm_keskmine() {
		return aritm_keskmine;
	}

	public static void setAritm_keskmine(double aritm_keskmine) {
		Statistika2.aritm_keskmine = aritm_keskmine;
	}

	public static double getDispersioon_raw() {
		return dispersioon_raw;
	}

	public static void setDispersioon_raw(double dispersioon_raw) {
		Statistika2.dispersioon_raw = dispersioon_raw;
	}

	public static double getDispersioon() {
		return dispersioon;
	}

	public static void setDispersioon(double dispersioon) {
		Statistika2.dispersioon = dispersioon;
	}

	public static double getStandardh2lve_raw() {
		return standardh2lve_raw;
	}

	public static void setStandardh2lve_raw(double standardh2lve_raw) {
		Statistika2.standardh2lve_raw = standardh2lve_raw;
	}
			
}

/**
 * Klass, mille meetodite abil loeme internetist temperatuuride salvestamise ajad ja temperatuuri väärtused ning
 * kirjutame need sobival kujul faili
 * 
 * @author Aivo Toots, Andres Kepler
 *
 */

import java.io.*;
import Pachube.*;


public class Allalaadimine_internetist {

	private static String[] v22rtused; // Siin hoiame failist loetud ridu
	private static int datastream;

// Konstruktor:

	Allalaadimine_internetist(String key, int feed, int datastream)  {
		
		try {
			
			// Loome ühenduse Pachube klassiga, mille kaudu suhtleme kodulehega, kust saame temeperatuuri muutuste andmed
			
			Pachube p = new Pachube(key);
			Feed f = p.getFeed(feed);
								
			// Kõik andmed stringina
			
			v22rtused = f.getDatastreamArchive(datastream);
			
		} catch (PachubeException e) {
			
	        System.out.println(e.errorMessage);
		
		} 

	}

// Meetod, mille abil loeme Pachube klassi kaudu internetist temperatuuri muutuste andmed ning kirjutame need faili	

	public void kirjutaToorandmedFaili(String fail) {
		
		String [] temp = v22rtused;
			
		try {
				
			FileOutputStream out = new FileOutputStream(fail,false);
				
			String rida = null;
			
			// Moodustame sobival kujul sõne, mis sisaldab vajalikke andmeid (salvestamisaeg ja temepratuur) edasiseks töötlemiseks ning kirjutame need faili
				 
			for (int i = 0; i < temp.length; i++) {
			
				StringBuilder sisu = new StringBuilder();
				
				sisu.append(datastream);
				sisu.append(",");
				sisu.append(temp[i]);
				sisu.append(System.getProperty("line.separator"));
				
				rida = sisu.toString();
				
				byte b[] = rida.getBytes();
				
				out.write(b);
				
			}
			
			out.close();

		} catch (FileNotFoundException e) {
				
			e.printStackTrace();
				
		} catch (IOException e) {
				
			e.printStackTrace();
				
		}
				   		
	}

// Getterid ja setterid:
		
	public static String[] getV22rtused() {
		return v22rtused;
	}

	public static void setV22rtused(String[] v22rtused) {
		Allalaadimine_internetist.v22rtused = v22rtused;
	}

	public static int getDatastream() {
		return datastream;
	}

	public static void setDatastream(int datastream) {
		Allalaadimine_internetist.datastream = datastream;
	}

}

	

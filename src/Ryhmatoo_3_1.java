/**
 * Programmi peaklass, mis kuvab graafiliselt kolme tab-iga akna ning arvutab nendes soovitud
 * tingimustel etteantud andmete pıhjal temperatuuride aritmeetilise keskmise, dispersiooni
 * ja standardh‰lbe
 * 
 * @author Aivo Toots, Andres Kepler
 *
 */

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.event.*;


public class Ryhmatoo_3_1 extends JPanel {
	
	public static String failinimi; // Kasutatava failinime v‰li
	private static String algus_kp = "0000-00-00 00:00:00"; // Failist loetud esimese temperatuuri salvestusaeg
    private static String l6pp_kp = "0000-00-00 00:00:00";// Failist loetud viimase temperatuuri salvestusaeg
	public static int ridu_failis = 0;
	private static final long serialVersionUID = 1L;
	
// Konstruktor:
	
	public Ryhmatoo_3_1(String fail) throws Exception {
    	
		this.failinimi = fail;
		
		// Rakendame sobivad meetodeid ja anname sellega klassimuutujatele v‰‰rtused 
		
		riduFailis();
		ajadFailis();
		
	}
	
	public static void main(String[] args) throws Exception {
		
		// M‰‰rame vaikimisi faili, millega tˆˆtame
		
		try {
			
			// Loome ¸henduse konstruktoriga ning kasutame seda omakorda kontrollina, kas failiga saab ¸hendust
			
			Ryhmatoo_3_1 k2ivita = new Ryhmatoo_3_1("andmed.txt");
					
		} catch (Exception e1) {
			
			try {
			
			// Kui faili ei leitud, loome selle ja t‰idame internetist alla laetud andmetega			
			
			Allalaadimine_internetist inet = new Allalaadimine_internetist("JinXOsCxi1KJ3dp9Cn42azXiV3GSAKxDOHM0N0doYjgrdz0g",117899,0);
						
			inet.kirjutaToorandmedFaili("andmed.txt");
						
			Ryhmatoo_3_1 k2ivita = new Ryhmatoo_3_1("andmed.txt");
			
			} catch (Exception e2) {
				
				// Kui ei ınnestu faili leida ega luua, v‰ljastame veateate ja sulgeme seej‰rel programmi
				
				JOptionPane.showConfirmDialog(null, "Programmi tˆˆks vajalikku faili ei ınnestu avada/leida vıi internetist alla laadida!\nProovige programmi uuesti k‰ivitada!", "Tırge!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		
		}		
		
        // Loome esimese tab-i        
        
        JPanel tabpaneel1 = new JPanel(false);
        
        tabpaneel1.setLayout(new GridLayout(17, 1));

        // Vajalikud tekstiv‰ljad tulemuste kuvamiseks ja "Arvuta" nupp
        
        JLabel tab1failiandmed = new JLabel("<html><font size=\"4\"><u>Faili \"" + failinimi + "\" andmed:</u></font></html>");
        final JLabel tab1ridufailis = new JLabel("<html>Temperatuuri v‰‰rtuseid: <font color=\"blue\">" + ridu_failis + "</font></html>");
        final JLabel tab1alguskp = new JLabel("<html>Esimese salvestuse aeg: <font color=\"blue\">" + algus_kp + "</font></html>");
        final JLabel tab1l6ppkp = new JLabel("<html>Viimase salvestuse aeg: <font color=\"blue\">" + l6pp_kp + "</font></html>");
         
        JLabel tab1kirjeldus = new JLabel("<html><font size=\"4\"><u>Arvuta temperatuuri aritmeetiline keskmine, dispersioon ja standardh‰lve:</u></font></html>");
        final JRadioButton tab1allikavalik1 = new JRadioButton("Kasuta faili \"" + failinimi + "\" salvestatud andmeid", true);
        final JRadioButton tab1allikavalik2 = new JRadioButton("Lae internetist uued andmed faili \"" + failinimi + "\" ja kasuta andmeid uuendatud failist");
        ButtonGroup tab1nupugrupp = new ButtonGroup();
        tab1nupugrupp.add(tab1allikavalik1);
        tab1nupugrupp.add(tab1allikavalik2);
        JButton tab1arvuta = new JButton("Arvuta");
        
        final JLabel tab1keskmine = new JLabel();
	    final JLabel tab1dispersioon = new JLabel();
	    final JLabel tab1stdev = new JLabel();
	    
	    // Esimese tab-i ActionListener, mis leiab soovitud karakteristikud, kui on vajutatud nuppu "Arvuta"
	    
	    ActionListener Tab1Listener = new ActionListener() {
	    	
	        public void actionPerformed(ActionEvent actionEvent) {
	        	
	        	if (actionEvent.getActionCommand() == "Arvuta_k6ik") {
	        	
	        		try {
	        			
	        			// Kasutame failist loetud andmeid
	        			
	        			if (tab1allikavalik1.isSelected()) {
	        				
	        				Allalaadimine_failist fail = new Allalaadimine_failist(failinimi);
	        				
	        				Statistika2 st1 = new Statistika2(Allalaadimine_failist.K6ik_andmed_failist());
	        				
	        				// Uuendame faili andmeid
	        				
	        				riduFailis();
		        			ajadFailis();
		        			
		        			// Arvutame aritmeetilise keskmise, dispersiooni ja standardh‰lbe ning v‰ljastame need ekraanile
		        			
		        			tab1keskmine.setText("<html><font size=\"4\">Aritmeetiline keskmine: <font color=\"red\">"+st1.Aritm_keskmine() + "</font></font></html>");
		        			tab1dispersioon.setText("<html><font size=\"4\">Dispersioon: <font color=\"red\">"+st1.Dispersioon() + "</font></font></html>");
		        			tab1stdev.setText("<html><font size=\"4\">Standardh‰lve: <font color=\"red\">"+st1.Standardh2lve() + "</font></font></html>");
	        				
	        			// Kasutame internetist loetud andmeid
	        			
	        			} else if (tab1allikavalik2.isSelected()) {
	        				
	        				Allalaadimine_failist fail;
        					
	        				Statistika2 st1;
	        				
	        				// Kui andmete fail puudub, siis loeme kıik andmed internetist ja loome faili
	        				
	        				if (ridu_failis == -999 || ridu_failis == 0) {
	        				
	        					Allalaadimine_internetist inet = new Allalaadimine_internetist("JinXOsCxi1KJ3dp9Cn42azXiV3GSAKxDOHM0N0doYjgrdz0g",117899,0);
	        				
	        					inet.kirjutaToorandmedFaili(failinimi);
	        					
	        					fail = new Allalaadimine_failist(failinimi);
	        					
	        					st1 = new Statistika2(Allalaadimine_failist.K6ik_andmed_failist());
	        					
	        					// Uuendame faili andmeid
	        					
	        					riduFailis();
			        			ajadFailis();
	        					
	        				} else {
	        					
	        				// Kui andmete fail on olemas, siis leiame vajalikud karakteristikud
	        					
	        					Allalaadimine_internetist inet = new Allalaadimine_internetist("JinXOsCxi1KJ3dp9Cn42azXiV3GSAKxDOHM0N0doYjgrdz0g",117899,0);
		        				
	        					inet.kirjutaToorandmedFaili(failinimi);
	        					
	        					fail = new Allalaadimine_failist(failinimi);
	        					
	        					st1 = new Statistika2(Allalaadimine_failist.K6ik_andmed_failist());
	        					
	        				}
	        				
	        				// Uuendame faili andmeid
	        				
	        				riduFailis();
		        			ajadFailis();
		        			
		        			// V‰ljastame ekraanile faili andmed
		        			
		        			tab1ridufailis.setText("<html>Temperatuuri v‰‰rtuseid: <font color=\"blue\">" + ridu_failis + "</font></html>");
		        	        tab1alguskp.setText("<html>Esimese salvestuse aeg: <font color=\"blue\">" + algus_kp + "</font></html>");
		        	        tab1l6ppkp.setText("<html>Viimase salvestuse aeg: <font color=\"blue\">" + l6pp_kp + "</font></html>");
		        			
		        	        // Arvutame aritmeetilise keskmise, dispersiooni ja standardh‰lbe ning v‰ljastame need ekraanile
		        	        
		        	        tab1keskmine.setText("<html><font size=\"4\">Aritmeetiline keskmine: <font color=\"red\">"+st1.Aritm_keskmine() + "</font></font></html>");
		        			tab1dispersioon.setText("<html><font size=\"4\">Dispersioon: <font color=\"red\">"+st1.Dispersioon() + "</font></font></html>");
		        			tab1stdev.setText("<html><font size=\"4\">Standardh‰lve: <font color=\"red\">"+st1.Standardh2lve() + "</font></font></html>");
	        				
	        			}
      			
					} catch (Exception e) {
					
						JOptionPane.showConfirmDialog(null, "Probleem andmete lugemisel ja/vıi nende tˆˆtlemisel!\nKui valisite \"Kasuta faili \"" + failinimi + "\" salvestatud andmeid\", vıib tırke pıhjuseks olla andmete puudumine failis -\nprobleemi lahendamiseks laadige andmed kıigepealt internetist!", "Tırge!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
												
					}
	        	
	        	}
	        	
	        }
	        
	    };
	    
	    tab1arvuta.addActionListener(Tab1Listener);
	    tab1arvuta.setActionCommand("Arvuta_k6ik");
	    
	    tabpaneel1.add(tab1failiandmed);
        tabpaneel1.add(tab1ridufailis);
        tabpaneel1.add(tab1alguskp);
        tabpaneel1.add(tab1l6ppkp);
        tabpaneel1.add(new JLabel());
	    tabpaneel1.add(tab1kirjeldus);
        tabpaneel1.add(tab1allikavalik1);
	    tabpaneel1.add(tab1allikavalik2);
	    tabpaneel1.add(tab1arvuta);
	    tabpaneel1.add(new JLabel());
	    tabpaneel1.add(tab1keskmine);
	    tabpaneel1.add(tab1dispersioon);
	    tabpaneel1.add(tab1stdev);
	    
	    // Loome teise tab-i
	    
	    final JPanel tabpaneel2 = new JPanel(false);
        tabpaneel2.setLayout(new GridLayout(17, 1));
        
        // Vajalikud tekstiv‰ljad andmete sisestamiseks ja tulemuste kuvamiseks ning "Arvuta" nupp
        
        JLabel tab2failiandmed = new JLabel("<html><font size=\"4\"><u>Faili \"" + failinimi + "\" andmed:</u></font></html>");
        final JLabel tab2ridufailis = new JLabel("<html>Temperatuuri v‰‰rtuseid: <font color=\"blue\">" + ridu_failis + "</font></html>");
        final JLabel tab2alguskp = new JLabel("<html>Esimese salvestuse aeg: <font color=\"blue\">" + algus_kp + "</font></html>");
        final JLabel tab2l6ppkp = new JLabel("<html>Viimase salvestuse aeg: <font color=\"blue\">" + l6pp_kp + "</font></html>");
            	
        JLabel tab2kirjeldus = new JLabel("<html><font size=\"4\"><u>Arvuta temperatuuri aritmeetiline keskmine, dispersioon ja standardh‰lve:</u></font></html>");
        JLabel tab2ridadearvkirjeldus = new JLabel("Sisesta positiivne t‰isarv, mitut viimast temperatuuri v‰‰rtust soovid kasutada: ");
        JLabel tab2NB = new JLabel("<html><font color=\"blue\">NB! M‰‰ra internetist laadimisel v‰‰rtuste arvuks 0, et kasutada kıiki v‰‰rtuseid!</font><html>");
        final JTextField tab2ridadearv = new JTextField("" + ridu_failis);
        tab2ridadearv.setToolTipText("Maksimaalselt vıib olla "+ ridu_failis + " rida");        
        
        final JRadioButton tab2allikavalik1 = new JRadioButton("Kasuta faili \"" + failinimi + "\" salvestatud andmeid", true);
        final JRadioButton tab2allikavalik2 = new JRadioButton("Lae internetist uued andmed faili \"" + failinimi + "\" ja kasuta andmeid uuendatud failist");
        ButtonGroup tab2nupugrupp = new ButtonGroup();
        tab2nupugrupp.add(tab2allikavalik1); 
        tab2nupugrupp.add(tab2allikavalik2);		
        JButton tab2arvuta = new JButton("Arvuta");
        
        final JLabel tab2keskmine = new JLabel();
	    final JLabel tab2dispersioon = new JLabel();
	    final JLabel tab2stdev = new JLabel();
	    
	    // Teise tab-i ActionListener, mis leiab soovitud karakteristikud, kui on vajutatud nuppu "Arvuta"
	    
	    ActionListener Tab2Listener = new ActionListener() {
	    	
	        public void actionPerformed(ActionEvent actionEvent) {
	        	
	        	if (actionEvent.getActionCommand() == "Arvuta_read") {
	        	
	        		try {
	        			
	        			// Loeme kasutaja poolt sisestatud temperatuuri v‰‰rtuste arvu
	        			
	        			int ridu = Integer.parseInt(tab2ridadearv.getText());
	        			
	        			// Kasutame failist loetud andmeid
	        			
	        			if (tab2allikavalik1.isSelected()) {
	        				
	        				Allalaadimine_failist fail = new Allalaadimine_failist(failinimi);
	        				
	        				Statistika2 st2 = new Statistika2(Allalaadimine_failist.Andmed_failist(ridu));
	        				
	        				// Arvutame aritmeetilise keskmise, dispersiooni ja standardh‰lbe ning v‰ljastame need ekraanile
	   			      	 
	        				tab2keskmine.setText("<html><font size=\"4\">Aritmeetiline keskmine: <font color=\"red\">"+st2.Aritm_keskmine() + "</font></font></html>");
		        			tab2dispersioon.setText("<html><font size=\"4\">Dispersioon: <font color=\"red\">"+st2.Dispersioon() + "</font></font></html>");
		        			tab2stdev.setText("<html><font size=\"4\">Standardh‰lve: <font color=\"red\">"+st2.Standardh2lve() + "</font></font></html>");
	        				
		        		// Kasutame internetist loetud andmeid
	        			
	        			} else if (tab2allikavalik2.isSelected()) {
	        				
	        				Statistika2 st2;
	        				Allalaadimine_failist fail;
	        				
	        				// Kui andmete fail puudub, siis loeme kıik andmed internetist ja loome faili
	        				
	        				if (ridu_failis == -999 || ridu == 0) {
	        					
	        					Allalaadimine_internetist inet = new Allalaadimine_internetist("JinXOsCxi1KJ3dp9Cn42azXiV3GSAKxDOHM0N0doYjgrdz0g",117899,0);
		        				
		        				inet.kirjutaToorandmedFaili(failinimi);
		        				
		        				fail = new Allalaadimine_failist(failinimi);
	        					
	        					st2 = new Statistika2(Allalaadimine_failist.K6ik_andmed_failist());
	        					
	        					// Uuendame faili andmeid
	        					
	        					riduFailis();
	        					ajadFailis();
	        					
	        					// V‰ljastame ekraanile maksimaalse vıimaliku temperatuuri v‰‰rtuste arvu
	        					
		                    	tab2ridadearv.setText("" + ridu_failis);
		                    	tab2ridadearv.setToolTipText("Maksimaalselt vıib olla "+ ridu_failis + " rida");
		                    	
		                    	// Arvutame aritmeetilise keskmise, dispersiooni ja standardh‰lbe ning v‰ljastame need ekraanile
		                    	
		                    	tab2ridufailis.setText("<html>Temperatuuri v‰‰rtuseid: <font color=\"blue\">" + ridu_failis + "</font></html>");
			        	        tab2alguskp.setText("<html>Esimese salvestuse aeg: <font color=\"blue\">" + algus_kp + "</font></html>");
			        	        tab2l6ppkp.setText("<html>Viimase salvestuse aeg: <font color=\"blue\">" + l6pp_kp + "</font></html>");
	        					
			        	    // Kui andmete fail on olemas ja sisestatud temperatuuri v‰‰rtuste arv on sobival kujul, siis leiame vajalikud karakteristikud
	        				
	        				} else if ( ridu_failis != -999 && ridu > 0) {
	        					
	        					Allalaadimine_internetist inet = new Allalaadimine_internetist("JinXOsCxi1KJ3dp9Cn42azXiV3GSAKxDOHM0N0doYjgrdz0g",117899,0);
		        				
		        				inet.kirjutaToorandmedFaili(failinimi);
		        				
		        				fail = new Allalaadimine_failist(failinimi);
		        				
		        				st2 = new Statistika2(Allalaadimine_failist.Andmed_failist(ridu));
		        				
		        				tab2ridadearv.setText("" + ridu_failis);
		                    	tab2ridadearv.setToolTipText("Maksimaalselt vıib olla "+ ridu_failis + " rida");
	        					
	        				// Kui eelnevad tingimused ei ole t‰idetud, siis loome ¸henduse karakteristikute arvutamise klassiga, et tekiks veateade
	        				
	        				} else {
	        					
	        					fail = new Allalaadimine_failist(failinimi);
		        				
		        				st2 = new Statistika2(Allalaadimine_failist.Andmed_failist(ridu));
	        					
	        				}
	        				
	        				// Uuendame faili andmeid
	        				
	        				riduFailis();
		        			ajadFailis();
		        			
		        			// V‰ljastame ekraanile faili andmed
		        			
		        			tab2ridufailis.setText("<html>Temperatuuri v‰‰rtuseid: <font color=\"blue\">" + ridu_failis + "</font></html>");
		        	        tab2alguskp.setText("<html>Esimese salvestuse aeg: <font color=\"blue\">" + algus_kp + "</font></html>");
		        	        tab2l6ppkp.setText("<html>Viimase salvestuse aeg: <font color=\"blue\">" + l6pp_kp + "</font></html>");
	        				
		        	        // Arvutame aritmeetilise keskmise, dispersiooni ja standardh‰lbe ning v‰ljastame need ekraanile
		        	        
		        	        tab2keskmine.setText("<html><font size=\"4\">Aritmeetiline keskmine: <font color=\"red\">"+st2.Aritm_keskmine() + "</font></font></html>");
		        			tab2dispersioon.setText("<html><font size=\"4\">Dispersioon: <font color=\"red\">"+st2.Dispersioon() + "</font></font></html>");
		        			tab2stdev.setText("<html><font size=\"4\">Standardh‰lve: <font color=\"red\">"+st2.Standardh2lve() + "</font></font></html>");
	        				
	        			}
		  
					} catch (Exception e) {
						
						JOptionPane.showConfirmDialog(null, "Probleem andmete lugemisel ja/vıi nende tˆˆtlemisel!\nKui valisite \"Kasuta faili \"" + failinimi + "\" salvestatud andmeid\", vıib tırke pıhjuseks olla andmete puudumine failis -\nprobleemi lahendamiseks m‰‰rake soovitavate v‰‰rtuste arvuks 0 ja laadige andmed kıigepealt internetist!\nKontrollige ka, kas ridade arv on sisestatud korrektsel kujul (positiivne t‰isarv)!", "Tırge!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
						
						// Uuendame faili andmeid
						
						riduFailis();
    					ajadFailis();
						
					}
	        	
	        	}
	        	
	        }
	        
	    };
	    
	    tab2arvuta.addActionListener(Tab2Listener);
	    tab2arvuta.setActionCommand("Arvuta_read");
        
	    tabpaneel2.add(tab2failiandmed);
        tabpaneel2.add(tab2ridufailis);
        tabpaneel2.add(tab2alguskp);
        tabpaneel2.add(tab2l6ppkp);
        tabpaneel2.add(new JLabel());
        tabpaneel2.add(tab2kirjeldus);
        tabpaneel2.add(tab2ridadearvkirjeldus);
        tabpaneel2.add(tab2NB);
        tabpaneel2.add(tab2ridadearv);
        tabpaneel2.add(tab2allikavalik1);
        tabpaneel2.add(tab2allikavalik2);
        tabpaneel2.add(tab2arvuta);
        tabpaneel2.add(new JLabel());
        tabpaneel2.add(tab2keskmine);
        tabpaneel2.add(tab2dispersioon);
        tabpaneel2.add(tab2stdev);
        
        // Loome kolmanda tab-i
        
        final JPanel tabpaneel3 = new JPanel(false);
        
        tabpaneel3.setLayout(new GridLayout(17, 1));

        // Vajalikud tekstiv‰ljad tulemuste kuvamiseks ja "Arvuta" nupp
        
        JLabel tab3kirjeldus = new JLabel("<html><font size=\"4\"><u>Arvuta temperatuuri aritmeetiline keskmine, dispersioon ja standardh‰lve:</u></font></html>");
        JLabel tab3failiandmed = new JLabel("<html><font size=\"4\"><u>Faili \"" + failinimi + "\" andmed:</u></font></html>");
        
        final JLabel tab3ridufailis = new JLabel("<html>Temperatuuri v‰‰rtuseid: <font color=\"blue\">" + ridu_failis + "</font></html>");
        final JLabel tab3alguskp = new JLabel("<html>Esimese salvestuse aeg: <font color=\"blue\">" + algus_kp + "</font></html>");
        final JLabel tab3l6ppkp = new JLabel("<html>Viimase salvestuse aeg: <font color=\"blue\">" + l6pp_kp + "</font></html>");
        
        JLabel tab3algus = new JLabel("Sisesta ajavahemiku algusaeg, millest alates soovid temperatuuri v‰‰rtuseid kasutada: ");
        final JTextField tab3KPAlgus = new JTextField(algus_kp);
        tab3KPAlgus.setToolTipText("yyyy-MM-dd HH:mm:ss");
  	    JLabel tab3l6pp = new JLabel("Sisesta ajavahemiku lıppaeg, milleni j‰‰vaid temperatuuri v‰‰rtuseid soovid kasutada: ");
  	    final JTextField tab3KPLopp  = new JTextField(l6pp_kp);
  	    tab3KPLopp.setToolTipText("yyyy-MM-dd HH:mm:ss");
        
        final JRadioButton tab3allikavalik1 = new JRadioButton("Kasuta faili \"" + failinimi + "\" salvestatud andmeid", true);
        final JRadioButton tab3allikavalik2 = new JRadioButton("Lae internetist uued andmed faili \"" + failinimi + "\" ja kasuta andmeid uuendatud failist");
        ButtonGroup tab3nupugrupp = new ButtonGroup();
        tab3nupugrupp.add(tab3allikavalik1);
        tab3nupugrupp.add(tab3allikavalik2);
                
        JButton tab3arvuta = new JButton("Arvuta");
        final JLabel tab3keskmine = new JLabel();
	    final JLabel tab3dispersioon = new JLabel();
	    final JLabel tab3stdev = new JLabel();
	    
	    // Kolmanda tab-i ActionListener, mis leiab soovitud karakteristikud, kui on vajutatud nuppu "Arvuta"
	    
	    ActionListener Tab3Listener = new ActionListener() {
	    	
	        public void actionPerformed(ActionEvent actionEvent) {
	        	
	        	if (actionEvent.getActionCommand() == "Arvuta_kuup2evad") {
	        	
	        		try {
	        			
	        			// Kasutame failist loetud andmeid
	        			
	        			if (tab3allikavalik1.isSelected()) {
	        				
	        				Allalaadimine_failist fail = new Allalaadimine_failist(failinimi);
	        				
	        				Statistika2 st3 = new Statistika2(Allalaadimine_failist.Andmed_failist_kp(tab3KPAlgus.getText(), tab3KPLopp.getText()));
		        			
		        			tab3keskmine.setText("<html><font size=\"4\">Aritmeetiline keskmine: <font color=\"red\">"+st3.Aritm_keskmine() + "</font></font></html>");
		        			tab3dispersioon.setText("<html><font size=\"4\">Dispersioon: <font color=\"red\">"+st3.Dispersioon() + "</font></font></html>");
		        			tab3stdev.setText("<html><font size=\"4\">Standardh‰lve: <font color=\"red\">"+st3.Standardh2lve() + "</font></font></html>");
	        				
		        		// Kasutame internetist loetud andmeid
	        			
	        			} else if (tab3allikavalik2.isSelected()) {
	        				
	        				Allalaadimine_failist fail;
        					
	        				Statistika2 st3;
	        				
	        				// Kui andmete fail puudub, siis loeme kıik andmed internetist ja loome faili
	        				
	        				if (ridu_failis == -999) {
	        				
	        					Allalaadimine_internetist inet = new Allalaadimine_internetist("JinXOsCxi1KJ3dp9Cn42azXiV3GSAKxDOHM0N0doYjgrdz0g",117899,0);
	        				
	        					inet.kirjutaToorandmedFaili(failinimi);
	        					
	        					fail = new Allalaadimine_failist(failinimi);
	        					
	        					st3 = new Statistika2(Allalaadimine_failist.K6ik_andmed_failist());
	        					
	        					// Uuendame faili andmeid
	        					
	        					riduFailis();
			        			ajadFailis();
			        			
			        			// V‰ljastame ekraanile maksimaalse vıimaliku ajavahemiku alguse ja lıpu
	        					
	        					tab3KPAlgus.setText(algus_kp);
	        					tab3KPLopp.setText(algus_kp);
	        					
	        				// Kui andmete fail on olemas, siis leiame vajalikud karakteristikud
	        				
	        				} else if (ridu_failis != -999) {
	        					
	        					Allalaadimine_internetist inet = new Allalaadimine_internetist("JinXOsCxi1KJ3dp9Cn42azXiV3GSAKxDOHM0N0doYjgrdz0g",117899,0);
		        				
	        					inet.kirjutaToorandmedFaili(failinimi);
	        					
	        					fail = new Allalaadimine_failist(failinimi);
	        					
		        				st3 = new Statistika2(Allalaadimine_failist.Andmed_failist_kp(tab3KPAlgus.getText(), tab3KPLopp.getText()));
	        				
		        			// Kui eelnevad tingimused ei ole t‰idetud, siis loome ¸henduse karakteristikute arvutamise klassiga, et tekiks veateade	
		        				
	        				} else {
	        					
	        					fail = new Allalaadimine_failist(failinimi);
	        					
		        				st3 = new Statistika2(Allalaadimine_failist.Andmed_failist_kp(tab3KPAlgus.getText(), tab3KPLopp.getText()));
	        					
	        				}
	        				
	        				// Uuendame faili andmeid
		        			
		        			riduFailis();
		        			ajadFailis();
		        			
		        			// V‰ljastame ekraanile faili andmed
		        			
		        			tab3ridufailis.setText("<html>Temperatuuri v‰‰rtuseid: <font color=\"blue\">" + ridu_failis + "</font></html>");
		        	        tab3alguskp.setText("<html>Esimese salvestuse aeg: <font color=\"blue\">" + algus_kp + "</font></html>");
		        	        tab3l6ppkp.setText("<html>Viimase salvestuse aeg: <font color=\"blue\">" + l6pp_kp + "</font></html>");
		        			
		        	        // Arvutame aritmeetilise keskmise, dispersiooni ja standardh‰lbe ning v‰ljastame need ekraanile
		        	        
		        			tab3keskmine.setText("<html><font size=\"4\">Aritmeetiline keskmine: <font color=\"red\">"+st3.Aritm_keskmine() + "</font></font></html>");
		        			tab3dispersioon.setText("<html><font size=\"4\">Dispersioon: <font color=\"red\">"+st3.Dispersioon() + "</font></font></html>");
		        			tab3stdev.setText("<html><font size=\"4\">Standardh‰lve: <font color=\"red\">"+st3.Standardh2lve() + "</font></font></html>");
		        			
	        			}
      			
					} catch (Exception e) {
					
						JOptionPane.showConfirmDialog(null, "Probleem andmete lugemisel ja/vıi nende tˆˆtlemisel!\nKui valisite esimese valiku, vıib pıhjuseks olla andmete puudumine -\nselle lahendamiseks laadige kıigepealt andmed internetist!", "Tırge!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
												
					}
	        	
	        	}
	        	
	        }
	        
	    };
	    
	    tab3arvuta.addActionListener(Tab3Listener);
	    tab3arvuta.setActionCommand("Arvuta_kuup2evad");
	    
	    tabpaneel3.add(tab3failiandmed);
        tabpaneel3.add(tab3ridufailis);
        tabpaneel3.add(tab3alguskp);
        tabpaneel3.add(tab3l6ppkp);
        tabpaneel3.add(new JLabel());
	    tabpaneel3.add(tab3kirjeldus);
	    tabpaneel3.add(tab3algus);
	    tabpaneel3.add(tab3KPAlgus);
	    tabpaneel3.add(tab3l6pp);
	    tabpaneel3.add(tab3KPLopp);
        tabpaneel3.add(tab3allikavalik1);
	    tabpaneel3.add(tab3allikavalik2);
	    tabpaneel3.add(tab3arvuta);
	    tabpaneel3.add(new JLabel());
	    tabpaneel3.add(tab3keskmine);
	    tabpaneel3.add(tab3dispersioon);
	    tabpaneel3.add(tab3stdev);
	    
	    // Loome neljanda tab-i (positsioon null)
		
	    JPanel tabpaneel4 = new JPanel(false);
        
        tabpaneel4.setLayout(new GridLayout(17, 1));
        
        // Vajalikud tekstiv‰ljad info kuvamiseks
        
        JLabel tab4allikas = new JLabel("<html>Andmete allikas: <font color=\"blue\">https://api.cosm.com/v2/feeds/117899.csv</font></html>");
        JLabel tab4key = new JLabel("<html>Feed: <font color=\"blue\">JinXOsCxi1KJ3dp9Cn42azXiV3GSAKxDOHM0N0doYjgrdz0g</font></html>");
        JLabel tab4feed = new JLabel("<html>Key: <font color=\"blue\">117899</font></html>");
        JLabel tab4datastream = new JLabel("<html>Datastream: <font color=\"blue\">0</font></html>");
        JLabel tab4failinimi = new JLabel("<html>Fail, kus andmeid hoiame: <font color=\"blue\">andmed.txt</font></html>");
        JLabel tab4ajavahemik = new JLabel("<html>Ajavahemik, mille jagu andmeid salvestame: <font color=\"blue\">1 kuu</font></html>");
        JLabel tab4ajaintervall = new JLabel("<html>Salvestuste vaheline ajaintervall: <font color=\"blue\">15 minutit</font></html>");
        final JLabel tab4alguskp = new JLabel("<html>Esimese salvestuse aeg: <font color=\"blue\">" + algus_kp + "</font></html>");
        final JLabel tab4l6ppkp = new JLabel("<html>Viimase salvestuse aeg: <font color=\"blue\">" + l6pp_kp + "</font></html>");
        final JLabel tab4ridufailis = new JLabel("<html>Temperatuuri v‰‰rtuseid: <font color=\"blue\">" + ridu_failis + "</font></html>");
        JButton tab4uuenda = new JButton("Uuenda andmeid");
        JLabel tab4autorid = new JLabel("<html>Programmi autorid: <font color=\"blue\">Aivo Toots, Andres Kepler</font></html>");
                
        
        // Neljanda tab-i ActionListener, mis uuendab faili salvestatud andmeid, kui on vajutatud nuppu "Uuenda andmeid"
	    
	    ActionListener Tab4Listener = new ActionListener() {
	    	
	        public void actionPerformed(ActionEvent actionEvent) {
	        	
	        	if (actionEvent.getActionCommand() == "Uuenda_andmeid") {
	        	
	        		try {
	        			
	        			Allalaadimine_internetist inet = new Allalaadimine_internetist("JinXOsCxi1KJ3dp9Cn42azXiV3GSAKxDOHM0N0doYjgrdz0g",117899,0);
	        			
	        			inet.kirjutaToorandmedFaili(failinimi);
	        					
	        			Allalaadimine_failist fail = new Allalaadimine_failist(failinimi);
	        					
	        			//st1 = new Statistika2(Allalaadimine_failist.K6ik_andmed_failist());
	        					
	        			// Uuendame faili andmeid
	        					
	        			riduFailis();
			        	ajadFailis();
	        					
	        			// V‰ljastame ekraanile faili andmed
		        			
		        		tab4alguskp.setText("<html>Esimese salvestuse aeg: <font color=\"blue\">" + algus_kp + "</font></html>");
		        	    tab4l6ppkp.setText("<html>Viimase salvestuse aeg: <font color=\"blue\">" + l6pp_kp + "</font></html>");
		        	    tab4ridufailis.setText("<html>Temperatuuri v‰‰rtuseid: <font color=\"blue\">" + ridu_failis + "</font></html>");
		        	    		        	    
		        	} catch (Exception e) {
					
						JOptionPane.showConfirmDialog(null, "Probleem internetist andmete allalaadimisel!", "Tırge!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
						
					}
	        	
	        	}
	        	
	        }
	        
	    };        
        
        tab4uuenda.addActionListener(Tab4Listener);
	    tab4uuenda.setActionCommand("Uuenda_andmeid");
	    
        tabpaneel4.add(tab4allikas);
        tabpaneel4.add(tab4key);
	    tabpaneel4.add(tab4feed);
	    tabpaneel4.add(tab4datastream);
	    tabpaneel4.add(new JLabel());
	    tabpaneel4.add(tab4failinimi);
	    tabpaneel4.add(tab4ajavahemik);
	    tabpaneel4.add(tab4ajaintervall);
        tabpaneel4.add(tab4alguskp);
        tabpaneel4.add(tab4l6ppkp);
        tabpaneel4.add(tab4ridufailis);
        tabpaneel4.add(new JLabel());
        tabpaneel4.add(tab4uuenda);
        tabpaneel4.add(new JLabel());
        tabpaneel4.add(new JLabel());
        tabpaneel4.add(new JLabel());
        tabpaneel4.add(tab4autorid);
        
        // Loome tabidega paneeli
    	
        JTabbedPane tabidegaPaneel = new JTabbedPane();
        
        tabidegaPaneel.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        
        tabidegaPaneel.addTab("Tutvustus",  null, tabpaneel4, "Programmi tutvustus ja info");
        tabidegaPaneel.addTab("Kıik andmed", null, tabpaneel1, "Arvuta statistilised karakteristikud kıigi andmete pıhjal");
        tabidegaPaneel.addTab("Valitud hulk andmeid", null, tabpaneel2, "Arvuta statistilised karakteristikud valitud arvu viimaste andmete pıhjal");
        tabidegaPaneel.addTab("Valitud ajavahemiku andmed", null, tabpaneel3, "Arvuta statistilised karakteristikud valitud ajavahemiku andmete pıhjal");

        // ChangeListener, mis j‰lgib tab-ide vahetust ja iga tab-i vahetuse korral uuendab peamist osa nende sisust
        
        tabidegaPaneel.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				
				if (e.getSource() instanceof JTabbedPane) {
                    JTabbedPane pane = (JTabbedPane) e.getSource();
                    
                    // Uuendame faili andmeid
                    
                    riduFailis();
            		ajadFailis();
            		
            		// Uuendame ekraanile v‰ljastatavaid faili andmeid
            		
            		tab1ridufailis.setText("<html>Temperatuuri v‰‰rtuseid: <font color=\"blue\">" + ridu_failis + "</font></html>");
        	        tab1alguskp.setText("<html>Esimese salvestuse aeg: <font color=\"blue\">" + algus_kp + "</font></html>");
        	        tab1l6ppkp.setText("<html>Viimase salvestuse aeg: <font color=\"blue\">" + l6pp_kp + "</font></html>");
        	        
        	        tab2ridufailis.setText("<html>Temperatuuri v‰‰rtuseid: <font color=\"blue\">" + ridu_failis + "</font></html>");
        	        tab2alguskp.setText("<html>Esimese salvestuse aeg: <font color=\"blue\">" + algus_kp + "</font></html>");
        	        tab2l6ppkp.setText("<html>Viimase salvestuse aeg: <font color=\"blue\">" + l6pp_kp + "</font></html>");
        	        
        	        tab3ridufailis.setText("<html>Temperatuuri v‰‰rtuseid: <font color=\"blue\">" + ridu_failis + "</font></html>");
        	        tab3alguskp.setText("<html>Esimese salvestuse aeg: <font color=\"blue\">" + algus_kp + "</font></html>");
        	        tab3l6ppkp.setText("<html>Viimase salvestuse aeg: <font color=\"blue\">" + l6pp_kp + "</font></html>");
        	        
        	        tab4alguskp.setText("<html>Esimese salvestuse aeg: <font color=\"blue\">" + algus_kp + "</font></html>");
        	        tab4l6ppkp.setText("<html>Viimase salvestuse aeg: <font color=\"blue\">" + l6pp_kp + "</font></html>");
        	        tab4ridufailis.setText("<html>Temperatuuri v‰‰rtuseid: <font color=\"blue\">" + ridu_failis + "</font></html>");
        	                	        
                    // System.out.println("Valitud tab: " + pane.getSelectedIndex());

                }
				
			}
			
        });
        
        // Loome pearaami, lisame sellele tab-idega paneeli ja kuvame selle
        
        final JFrame raam = new JFrame("XIVELY JAVA 2013 v3.1 - Temperatuuri statistilised karakteristikud");
        raam.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        raam.setResizable(false);
        raam.add(tabidegaPaneel);
        raam.pack();
        raam.setVisible(true);
        
    }
	
	// Meetod ridade arvu leidmiseks failist
	
	public static void riduFailis() {
			
		// Leiame faili ridade arvu
	        
        try {
	            
           	BufferedReader reader = new BufferedReader(new FileReader(failinimi));
	    		
           	int ridu = 0;
	           	
           	while (reader.readLine() != null) ridu++;
	    		
           	reader.close();
	           	
           	ridu_failis = ridu;
	    		
        } catch (Exception e) {
	            	
           	ridu_failis = -999;
	            	
        }
			
	}
	
	// Meetod esimese ja viimase temperatuuri v‰‰rtuse salvestamise aegade leidmiseks
	
	public static void ajadFailis() {
		
		// Leiame esimese ja viimase temperatuuri salvestamise ajad, et pakkuda need v‰lja ajavahemiku piirideks
       
        try {
        
        	BufferedReader in = new BufferedReader(new FileReader(failinimi));
        
        	String currentLine = in.readLine();
        	String nextLine = in.readLine();
        
        	boolean hasStarted = false;
        
        	while ( null != currentLine ) {
        	
        		// Lammutame kuupaeva juppideks T j2rgi
        		String [] kp1 = currentLine.split("T");
    		
        		// Lammutame kellaaja juppideks . j2rgi
        		String [] time = kp1[1].split("\\.");
			
        		// Moodustame vajalikul kujul kuup2eva ja kellaaja s6ne
        		String kuup2ev = ((kp1[0] + " " + time[0]).split(",")[1]).split("Z")[0];

        		// Leiame esimese salvestuse aja
        		if ( !hasStarted ) {
				
        			algus_kp = kuup2ev;
        			hasStarted = true;
                
        		}
			
        		// Leiame viimase salvestuse aja
        		if ( null == nextLine ) {
            	
        			l6pp_kp = kuup2ev;
            	
        		}
            
        		currentLine = nextLine;
        		nextLine = in.readLine();
            
        	}
        
        	in.close();
        
        } catch (Exception e) {
        	
        	algus_kp = "0000-00-00 00:00:00";
        	l6pp_kp = "0000-00-00 00:00:00";
       	
        }
        
// Getterid ja setterid:
		
	}

	public static String getFailinimi() {
		return failinimi;
	}

	public static void setFailinimi(String failinimi) {
		Ryhmatoo_3_1.failinimi = failinimi;
	}

	public static String getAlgus_kp() {
		return algus_kp;
	}

	public static void setAlgus_kp(String algus_kp) {
		Ryhmatoo_3_1.algus_kp = algus_kp;
	}

	public static String getL6pp_kp() {
		return l6pp_kp;
	}

	public static void setL6pp_kp(String l6pp_kp) {
		Ryhmatoo_3_1.l6pp_kp = l6pp_kp;
	}

	public static int getRidu_failis() {
		return ridu_failis;
	}

	public static void setRidu_failis(int ridu_failis) {
		Ryhmatoo_3_1.ridu_failis = ridu_failis;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
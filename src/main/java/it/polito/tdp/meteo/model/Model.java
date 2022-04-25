package it.polito.tdp.meteo.model;

import java.util.LinkedList;
import java.util.List;
import java.time.*;
import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
    private MeteoDAO dao;
    private List<Rilevamento> rilevamenti;
    
    private List<Citta> cittaRicorsione; 
    private List<Rilevamento> parziale;
    private List<Rilevamento> migliore;
    private double costoMigliore;
    

	public Model() {
       dao = new MeteoDAO();
       rilevamenti=dao.getAllRilevamenti();
       cittaRicorsione = new LinkedList();
       costoMigliore = Integer.MAX_VALUE ;
       migliore = new LinkedList();
	}

	// of course you can change the String output with what you think works best
	public double getUmiditaMedia(int mese,String localita) {
		int count=0;
		double sum=0.0;
		for(Rilevamento r: rilevamenti) {
			if(r.getData().getMonthValue()==mese && r.getLocalita().equals(localita)) {
				count++;
				sum=sum+r.getUmidita();
			}
		}
		return sum/count;
	}
	
	public void initialize() {
		cittaRicorsione.clear();
	}
	
	public void getAllRilevamentiLocalitaMese(int mese, String localita) {
		Citta c = new Citta(localita, dao.getAllRilevamentiLocalitaMese(mese, localita));
		cittaRicorsione.add(c);
	}

	// of course you can change the String output with what you think works best
	public void trovaSequenza(int mese) {
		initialize();
		getAllRilevamentiLocalitaMese(mese, "Torino");
		getAllRilevamentiLocalitaMese(mese, "Milano");
		getAllRilevamentiLocalitaMese(mese, "Genova");
		parziale = new LinkedList();
		cerca_Ricorsiva(0,parziale);
	}
	
	
	
	public void cerca_Ricorsiva(int livello, List<Rilevamento> parziale) {
	if(parziale.size()== NUMERO_GIORNI_TOTALI) {
		for(Citta c: cittaRicorsione) {
			if(c.getCounter()==0) {
				return;
			}
		}
		
		List<Integer> vector =new LinkedList();
		int count=1;
		for(int i=0;i<NUMERO_GIORNI_TOTALI-1;i++) {
			if(!parziale.get(i).getLocalita().equals(parziale.get(i+1).getLocalita())) {
				vector.add(new Integer(count));
				count=1;
			}else
			count++;
			/*if(i==NUMERO_GIORNI_TOTALI-2) {
				if(parziale.get(i+1).getLocalita().equals(parziale.get(i).getLocalita())){
				vector.add(new Integer(count));	
				}else vector.add(1);
			}*/
		}
		for(Integer in:vector) {
			if(in<NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN)
				return;
		}
		
		double c = calcolaCosto(parziale);
		if(c<costoMigliore) {
			costoMigliore = c;
			migliore = new LinkedList(parziale);
			return;
		}
		else return;
	}
	
	
	
	for(Citta c: cittaRicorsione) {
       if(c.getCounter()<NUMERO_GIORNI_CITTA_MAX) {
    	   parziale.add(c.getRilevamenti().get(livello));
    	   c.increaseCounter();
    	   cerca_Ricorsiva(livello+1,parziale);
    	   parziale.remove(parziale.size()-1);
    	   c.decreaseCounter();
       }
	}
	
		
	}
	
	public double calcolaCosto(List<Rilevamento> parziale){
		double c = 0.0;
		for(int i=1;i<NUMERO_GIORNI_TOTALI;i++) {
			if(parziale.get(i).getLocalita().equals(parziale.get(i-1).getLocalita())) {
				 c = c + parziale.get(i-1).getUmidita();
			}
			else c = c + parziale.get(i-1).getUmidita() + COST ;	
		}
		c = c + parziale.get(NUMERO_GIORNI_TOTALI-1).getUmidita();
		return c;
	}

	public double getCostoMigliore() {
		return costoMigliore;
	}

	public List<Rilevamento> getMigliore() {
		return migliore;
	}
	
	
	
	
	

}




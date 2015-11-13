package view;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;

import model.*;

public class VueLivraison extends JPanel implements Observer {

	private Plan plan;
	private JList livraisons;
	private Fenetre fenetre;
	
	public VueLivraison(Plan plan, Fenetre fenetre) {
		super();
		this.plan = plan;
		this.fenetre = fenetre;
		
		this.setBackground(Color.WHITE);
		//Couleur des bordures
        this.setBorder(new CompoundBorder(
        	    BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY), 
        	    BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY)));
		
		this.livraisons = new JList();
		this.livraisons.setCellRenderer(new ColorierItemRenderer(fenetre, plan));
		
		this.add(livraisons);
		fenetre.getContentPane().add(this);
		plan.addObserver(this);
	}
	
	/**
	 * affiche la liste des livraisons dans la vue livraison (droite)
	 */
	private void afficherLivraisons() {

		int indice = 0;
		FenetreLivraison fenetreLivraisonCourante;
		Iterator<FenetreLivraison> itFL = plan.getDemandeLivraisons().getFenetresLivraisons().iterator();
		List<Livraison> livraisonsModel;
		Iterator<Livraison> itL;
		String elementsLivraison;
		//Le tableau a une taille egale au nombre de livraisons plus le nombre de fenetre de livraisons
		Object[] model = new Object[plan.getDemandeLivraisons().getAllLivraisons().size()+plan.getDemandeLivraisons().getFenetresLivraisons().size()];
		
		while (itFL.hasNext()) {
			fenetreLivraisonCourante = itFL.next();
			livraisonsModel = fenetreLivraisonCourante.getLivraisons();
			itL = livraisonsModel.iterator();
			
			String heureDebut = fenetre.secondeToHeure(fenetreLivraisonCourante.getHeureDebut());
			String heureFin = fenetre.secondeToHeure(fenetreLivraisonCourante.getHeureFin());
			model[indice] = plan.getDemandeLivraisons().getFenetresLivraisons().indexOf(fenetreLivraisonCourante)+1+") "+heureDebut+" a "+heureFin;
			indice++;
			TreeMap<Integer, List<String>> mapEtapesFen = new TreeMap<Integer, List<String>>();
			while (itL.hasNext()) {
				Livraison livraisonActuelle = itL.next();
				String resultat = 	"Client : "+livraisonActuelle.getClient();
				Integer key = 0;
				if (plan.getTournee().getEtapes() != null) {
					for (int i = 0; i < plan.getTournee().getEtapes().size(); i++) {
						Etape etapeATester = plan.getTournee().getEtapes().get(i);
						Livraison livraisonATester = etapeATester.getLivraison();
						if (livraisonATester.getAdresse() == livraisonActuelle.getAdresse()) {
							resultat += " Heure prevue : "+fenetre.secondeToHeure((int)etapeATester.getHeureLivraison());
									
							if((int)etapeATester.getRetard() > 0) {
								resultat += " Retard : "+fenetre.secondeToHeure((int)etapeATester.getRetard());
							}
							key = (int)etapeATester.getHeureLivraison();
						}
					}
				}
				if(!mapEtapesFen.containsKey(key)){
					List<String> liste = new ArrayList<String>();
					mapEtapesFen.put(key, liste);
				}
				mapEtapesFen.get(key).add(resultat);
			}
			Set<Entry<Integer,List<String>>> entrySet = mapEtapesFen.entrySet();
			Iterator<Map.Entry<Integer,List<String>>> it = entrySet.iterator();
			while(it.hasNext()) {
				List<String> liste = it.next().getValue();
				for(String res : liste) {
					model[indice] = res;
					indice++;	
				}
			}
		}
		
		this.livraisons.setListData(model);
	}
	
	/**
	 * Cette classe privee sert a la coloration des item de la liste des livraisons
	 */
	private static class ColorierItemRenderer extends DefaultListCellRenderer {
		
		private Fenetre fenetre;
		private Plan plan;
		
		public ColorierItemRenderer(Fenetre fenetre, Plan plan) {
			this.fenetre = fenetre;
			this.plan = plan;
		}
		
        public Component getListCellRendererComponent( JList list, Object value, int index, boolean isSelected, boolean cellHasFocus ) {
        	Component c = super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );

    		String fenetreL = (String)value;
    		
            if ( fenetreL.charAt(0) != 'C' ) {
                c.setBackground( fenetre.getCouleurs().get(fenetreL.charAt(0)-49));
            }
        	return c;
        }
    }
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg != null) {
			
		}
		afficherLivraisons();
	}
}

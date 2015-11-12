package view;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;

import model.FenetreLivraison;
import model.Livraison;
import model.Plan;

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
		this.livraisons.setCellRenderer(new ColorierItemRenderer());
		this.add(livraisons);
		fenetre.getContentPane().add(this);
		plan.addObserver(this);
	}
	
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
			model[indice] = "De "+heureDebut+" à "+heureFin;
			indice++;
			
			while (itL.hasNext()) {
				Livraison livraisonActuelle = itL.next();
				
				model[indice] = livraisonActuelle.getClient();
				indice++;
			}
		}
		
		this.livraisons.setListData(model);
	}
	
	//Cette classe privee sert a la coloration des item de la liste des livraisons
	private static class ColorierItemRenderer extends DefaultListCellRenderer {
        public Component getListCellRendererComponent( JList list, Object value, int index, boolean isSelected, boolean cellHasFocus ) {
        	Component c = super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );
        	System.out.println(value.getClass().getName());
        	if(value.getClass().getName() == "java.lang.String") {
        		String fenetreL = (String)value;
        		
        		//ATTENTION ce test est vraiment pourri mais à 2h du matin j'ai pas trouve mieux
        		//Si la ligne commence par "D" c'est que c'est une entete de fenetre de livraisons a colorier
	            if ( fenetreL.charAt(0) == 'D' ) {
	                c.setBackground( Color.yellow );
	            }
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

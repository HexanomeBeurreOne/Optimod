/**
 * 
 */
package model.factory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * @author Adrien Menella
 *
 */
public interface FactoryBase {
	
	/**
	 * parse un fichier XMl et renvoie son arbre DOM si le fichier est bien formé
	 * retourne null en cas d'erreur
	 * @param pathXml est le chemin d'accès du fichier xml
	 * @return
	 */
	public Document getDomTree(String pathXml);
	
	/**
	 * vérifie que le noeud passé en paramètre est une balise XML est que son nom est égal à celui passé en paramètre
	 * retourne null en cas d'erreur
	 * @param currentNode est le noeud que l'on veut tester
	 * @param correctNodeName est le nom correcte attendu pour ce noeud
	 * @return
	 */
	public boolean checkNodeTypeAndName(Node currentNode, String correctNodeName);

}

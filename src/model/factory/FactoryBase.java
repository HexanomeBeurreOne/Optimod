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
	 * parse un fichier XMl et renvoie son arbre DOM si le fichier est bien form�
	 * retourne null en cas d'erreur
	 * @param pathXml est le chemin d'acc�s du fichier xml
	 * @return
	 */
	public Document getDomTree(String pathXml);
	
	/**
	 * v�rifie que le noeud pass� en param�tre est une balise XML est que son nom est �gal � celui pass� en param�tre
	 * retourne null en cas d'erreur
	 * @param currentNode est le noeud que l'on veut tester
	 * @param correctNodeName est le nom correcte attendu pour ce noeud
	 * @return
	 */
	public boolean checkNodeTypeAndName(Node currentNode, String correctNodeName);

}

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
	
	public Document getDomTree(String uriXml);
	
	public boolean checkNodeTypeAndName(Node currentNode, String correctNodeName);

}

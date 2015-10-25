/**
 * 
 */
package model;

import org.w3c.dom.Document;

/**
 * @author Adrien Menella
 *
 */
public interface FactoryBase {
	
	public Document getDomTree(String uriXml);

}

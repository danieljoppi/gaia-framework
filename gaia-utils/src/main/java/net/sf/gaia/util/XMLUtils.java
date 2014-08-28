package net.sf.gaia.util;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Classe utilitária para maniputação de XML.
 * 
 * @author daniel.joppi
 *
 */
public abstract class XMLUtils extends org.apache.axis.utils.XMLUtils 
{

	/**
	 * Método que copia um Document ou um Element, copiando todos os seus
	 * atributos e nós filhos se o parâmetro deep for true.
	 * 
	 * @param doc instância de Document a qual a nova instancia estara associada.
	 * @param node instancia de Document ou Element.
	 * @param deep se true copia os nós filhos também.
	 * @return nova instância de node.
	 * 
	 *  @author daniel.joppi
	 *  @since 25/11/2009
	 */
	public static Node cloneNode(Document doc, Node node, boolean deep)
	{
		if(node instanceof Document)
		{
			Document docNode = (Document) node;
			Element elem = (Element) docNode.getFirstChild();
			return XMLUtils.cloneElement(doc, elem, deep);
		} 
		else if (node instanceof Element)
		{
			return XMLUtils.cloneElement(doc, (Element) node, deep);
		} 
		else 
		{
			return null;
		}
	}
	
	/**
	 * Método que copia um Element, copiando todos os seus
	 * atributos e nós filhos se o parâmetro deep for true.
	 * 
	 * @param doc instância de Document a qual a nova instancia estara associada.
	 * @param node instância de Element.
	 * @param deep se true copia os nós filhos também.
	 * @return nova instância de Element.
	 * 
	 *  @author daniel.joppi
	 *  @since 25/11/2009
	 */
	public static Element cloneElement(Document doc, Element elem, boolean deep)
	{
		if(elem == null)
		{
			return null;
		}

		// clona o elemento
		Element newElem = doc.createElement(elem.getNodeName());
		
		// clona os atributos
		NamedNodeMap attributes = elem.getAttributes();
		for (int i = 0; i < attributes.getLength(); i++) 
		{
			Attr attr = (Attr) attributes.item(i);
			newElem.setAttribute(attr.getName(), attr.getValue());
		}
		
		if(deep)
		{
			if(elem.hasChildNodes())
			{
				// clona os nos filhos
				NodeList nodeList = elem.getChildNodes();
				for (int i = 0; i < nodeList.getLength(); i++) 
				{
					Element child = (Element) nodeList.item(i);
					Element newChild = XMLUtils.cloneElement(doc, child, deep);
					if (newChild != null)
					{
						newElem.appendChild(newChild);
					}
				}
			}
		}
		
		return newElem;
	}
}

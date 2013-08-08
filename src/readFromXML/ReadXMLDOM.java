package readFromXML;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ReadXMLDOM {

	String xmlfile;
	private ArrayList<Monier> mList = new ArrayList<Monier>();
	private Monier temp;

	public ReadXMLDOM(String xml) {
		this.xmlfile = xml;
		parseDocument();
	}

	public void parseDocument() {

		try {

			File fxml = new File(xmlfile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fxml);

			// doc.getDocumentElement().normalize();

			System.out.println("Root element :"
					+ doc.getDocumentElement().getNodeName());

			//NodeList nList = doc.getElementsByTagName("H3B");
			 NodeList nList = doc.getDocumentElement().getChildNodes();

			for (int i = 0; i < nList.getLength(); i++) {

				Node nNode = nList.item(i); //System.out.println(nList.getLength());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					temp = new Monier();
					temp.setHtype(nNode.getNodeName());

					/******************** Extract Lex Node **********************/
					NodeList list = nNode.getChildNodes();

					for (int j = 0; j < list.getLength(); j++) {

						Node node = list.item(j);

						if ("body".equals(node.getNodeName())) {

							NodeList bodylist = node.getChildNodes();

							for (int k = 0; k < bodylist.getLength(); k++) {

								Node cnode = bodylist.item(k);
							//	System.out.println(k+" "+cnode.getNodeName());
								
								if (cnode.getNodeName().equals("lex")) {
									temp.setLex(cnode.getTextContent());
									node.removeChild(cnode); 
								}
															
								// remove ls, ab, etym, as0, as1, p
								else if ("ls".equals(cnode.getNodeName())) {
									//System.out.println("ls "+cnode.getNodeValue());
									node.removeChild(cnode); 
								} else if ("ab".equals(cnode.getNodeName())) {
									//System.out.println("ab "+cnode.getNodeValue());
									node.removeChild(cnode); 
								} else if ("etym".equals(cnode.getNodeName())){
									//System.out.println("etym");
								
									node.removeChild(cnode);
								}else if ("as0".equals(cnode.getNodeName())){
									//System.out.println("as0");
									node.removeChild(cnode); 
								}else if ("as1".equals(cnode.getNodeName())){
									//System.out.println("as1");									
									node.removeChild(cnode); 
								}
								/* else if("p".equals(cnode.getNodeName())){
								 System.out.println("p");
								 node.removeChild(cnode); 
							 	}*/

							}// end of k for
						}

					} // end of j for

					/******************** Add to monier list **********************/

					temp.setKey1(eElement.getElementsByTagName("key1").item(0)
							.getTextContent()
							+ "(" + temp.getLex() + ")");
					temp.setC(eElement.getElementsByTagName("body").item(0)
							.getTextContent());
					mList.add(temp);

				}// end of if

			}// end of i for
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void displayList() {
		System.out.println("No of words '" + mList.size() + "'.");
		Iterator<Monier> it = mList.iterator();
		while (it.hasNext()) {
			// if(!(it.next().getEtym().equals("null")))
			System.out.println(it.next().toString());

		}
	}

	public ArrayList<Monier> getList() {
		return mList;
	}

	public void displayWord(String word) {
		Iterator<Monier> it = mList.iterator();
		while (it.hasNext()) {
			Monier wordPointer = it.next();
			if (wordPointer.getKey1().equals(word))
				System.out.println(wordPointer.toString());

		}
	}

	public void writeToFile() {

		try {
			FileWriter f1 = new FileWriter("dictionary.txt");
			PrintWriter pw = new PrintWriter(f1);
			Iterator<Monier> it = mList.iterator();
			while (it.hasNext()) {

				pw.println(it.next().toString());

			}

			pw.close();
			f1.close();
		} catch (Exception e) {
			e.toString();
		}

	}

}

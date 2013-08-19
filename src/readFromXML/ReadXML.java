package readFromXML;
// SAX Parser

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ReadXML extends DefaultHandler {

    private Monier mo;
    private String temp;
    String xmlfile;
    private ArrayList<Monier> mList = new ArrayList<Monier>();
    
    public ReadXML(String xmlfile){
        this.xmlfile=xmlfile;
        parseDocument();
    }

    private void parseDocument() {
        //parse
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(xmlfile, this);
        }
        catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        }
        catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        }
        catch (IOException e) {
            System.out.println("IO error");
        	//e.printStackTrace();
        }
    }


    /*
     * When the parser encounters plain text (not XML elements),
     * it calls(this method, which accumulates them in a string buffer
     */
    public void characters(char[] buffer, int start, int length) {
        temp = new String(buffer, start, length);
    }

    
    
    /*
     * Every time the parser encounters the beginning of a new element,
     * it calls this method, which resets the string buffer
     */
    @Override
    public void startElement(String uri, String localName,
            String qName, Attributes attributes) throws SAXException {
        temp = "";
        if (qName.equalsIgnoreCase("H1") || qName.equalsIgnoreCase("H2") || qName.equalsIgnoreCase("H3")||
qName.equalsIgnoreCase("H4") || qName.equalsIgnoreCase("H1A") || qName.equalsIgnoreCase("H2A") || 
qName.equalsIgnoreCase("H3A") || qName.equalsIgnoreCase("H4A") ||  qName.equalsIgnoreCase("H1B") || 
qName.equalsIgnoreCase("H2B") || qName.equalsIgnoreCase("H3B")|| qName.equalsIgnoreCase("H4B") ||
qName.equalsIgnoreCase("HPW")) {
            mo = new Monier();
            mo.setHtype(qName);
          
        }
    }

    /*
     * When the parser encounters the end of an element, it calls this method
     */
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

    	 if (qName.equalsIgnoreCase("H1") || qName.equalsIgnoreCase("H2") || qName.equalsIgnoreCase("H3")||
    			 qName.equalsIgnoreCase("H4") || qName.equalsIgnoreCase("H1A") || qName.equalsIgnoreCase("H2A") || 
    			 qName.equalsIgnoreCase("H3A") || qName.equalsIgnoreCase("H4A") ||  qName.equalsIgnoreCase("H1B") || 
    			 qName.equalsIgnoreCase("H2B") || qName.equalsIgnoreCase("H3B")|| qName.equalsIgnoreCase("H4B") ||
    			 qName.equalsIgnoreCase("HPW")) {
            // add it to the list
            mList.add(mo);
        } else if (qName.equalsIgnoreCase("root")) {
            mo.setH_root(temp);
        } else if (qName.equalsIgnoreCase("key1")) {
            mo.setKey1((temp));
        } else if (qName.equalsIgnoreCase("key2")) {
            mo.setKey2((temp));
        } else if (qName.equalsIgnoreCase("lex")) {
            mo.setLex((temp));          
        } else if (qName.equalsIgnoreCase("vlex")) {
            mo.setVlex((temp));          
        } else if (qName.equalsIgnoreCase("b")) {
            mo.setC((temp));
        } else if (qName.equalsIgnoreCase("b1")) {
            mo.setC((temp));
        } else if (qName.equalsIgnoreCase("p")) {
            mo.setC((temp));
        } else if (qName.equalsIgnoreCase("p1")) {
            mo.setC((temp));
        } else if (qName.equalsIgnoreCase("c")) {
            mo.setC((temp));
        } else if (qName.equalsIgnoreCase("c1")) {
            mo.setC((temp));
        } else if (qName.equalsIgnoreCase("c2")) {
            mo.setC((temp));
        } else if (qName.equalsIgnoreCase("c3")) {
            mo.setC((temp));
        } else if (qName.equalsIgnoreCase("s")) {
            mo.setSynonym((temp));
        } else if (qName.equalsIgnoreCase("etym")) {
            mo.setEtym((temp));
          
        }

    }

    public void displayList() {
        System.out.println("No of words '" + mList.size() + "'.");
        Iterator<Monier> it = mList.iterator();
        while (it.hasNext()) {
        	        		//if(!(it.next().getEtym().equals("null")))
        		     System.out.println(it.next().toString());
        	      
        }
    }
    
    
    public ArrayList<Monier> getList() {
       return mList;
    }

	public void displayWord(String word) {
		Iterator<Monier> it = mList.iterator();
        while (it.hasNext()) {
        	Monier wordPointer= it.next();
        	      if(wordPointer.getKey1().equals(word))
        		     System.out.println(wordPointer.toString());
        	      
        }
	}
	
	public void writeToFile() {
       
		try{
			FileWriter f1= new FileWriter("dictionary.txt");
			PrintWriter pw= new PrintWriter(f1);
			 Iterator<Monier> it = mList.iterator();
		        while (it.hasNext()) {
		        	    		  
		        		     pw.println(it.next().toString());
		        		     
		        }
			
			pw.close();
			f1.close();
		}
		catch(Exception e){
			e.toString();
		}
       
    }
    
}


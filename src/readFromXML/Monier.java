package readFromXML;


public class  Monier {

	   private String Htype;
	   //eg: H1, H1A etc.
       private String h_srs, h_root, key1, key2; 
       //single replacement sandhi, root for verbs, word, additional info on word
       private String c,lex,vlex;
       // p,p1,b,b1,c,c1,c2,c3 to be combined and saved in c as meaning 
       // lex contains pos information
       private String synonym, etym;
       // synonym from <eq/> tag
       String temp1,temp2;
       
       
       public Monier() {
    	   Htype = null;
   		this.h_srs = null;
   		this.h_root = null;
   		this.key1 = null;
   		this.key2 = null;
   		this.c = "";
   		this.lex = null;
   		this.vlex=null;
   		this.synonym = null;
   		this.etym = null;

       }
       

	public String getLex() {
		return lex;
	}

	public void setLex(String lex) {
	   
		temp1=lex.replaceAll("/", "_");
		temp2 =temp1.replaceAll("-", "_"); 
		this.lex=temp2.replaceAll(" ","_");
		
	}

	public String getVlex() {
		
		return vlex;
	}

	public void setVlex(String vlex) {
		temp1=vlex.replaceAll("/", "_");
		temp2=temp1.replaceAll("-", "_");
		this.vlex = temp2.replaceAll(" ", "_");
	}

	public String getHtype() {
		
		return Htype;
	}

	public void setHtype(String htype) {
		
		Htype = htype.replaceAll("-", "_");
		this.Htype=htype.replaceAll(" ","_");
	}

	public String getH_srs() {
		return h_srs;
	}

	public void setH_srs(String h_srs) {
		
		//h_srs.replace(arg0, arg1);
		this.h_srs = h_srs.replaceAll("-", "_");
		this.h_srs=h_srs.replaceAll(" ","_");
	}

	public String getH_root() {
		return h_root;
	}

	public void setH_root(String h_root) {
		
		this.h_root = h_root.replaceAll("-", "_");;
		this.h_root=h_root.replaceAll(" ","_");
	}

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		
		temp1=key1.replaceAll(" ","_");
		temp2=temp1.replaceAll("/","_");
		this.key1=temp2.replaceAll("-", "_");
	}

	public String getKey2() {
		return key2;
	}

	public void setKey2(String key2) {
		
		this.key2 = key2.replaceAll(" ", "_");
		this.key2 = key2.replaceAll("-", "_");
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		
		c=c.replaceAll("_", " ");
		c=c.replaceAll("-", " ");
		c=c.replaceAll("~", " ");
		this.c= new StringBuilder(getC()).append(c).toString();
	}

	public String getSynonym() {
		return synonym;
	}

	public void setSynonym(String synonym) {
		
		temp1=synonym.replaceAll("-", "_");
		temp2= temp1.replaceAll(" ", "_");
		this.synonym = temp2.replaceAll("/", "_");
		
		temp2= " "+temp2+" ";
		setC(temp2);
	}

	public String getEtym() {
		return etym;
	}

	public void setEtym(String etym) {
		
		this.etym = etym.replaceAll("-", "_");
		this.etym = etym.replaceAll(" ", "_");
	}

	
	
	public String toString() {
              StringBuffer sb = new StringBuffer();
              sb.append("H type:" + getHtype());
              sb.append(",");
              if(getLex()==null&&getVlex()!=null){
            	  sb.append("POS:" + "v.");
            	  sb.append(",");
              }
              else{            	 
            	  sb.append("POS:" + getLex());
            	  sb.append(",");
              }
              sb.append("Word:" + getKey1());
              sb.append(",");
              sb.append("Meaning:" + getC());
              sb.append(",");
              sb.append("h_root:" + getH_root());
              sb.append(",");
              sb.append("synonym:" + getSynonym());
              sb.append(",");
              sb.append("Etym:" + getEtym());
              sb.append(".");
              

              return sb.toString();
       }
}



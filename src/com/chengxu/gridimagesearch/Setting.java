package com.chengxu.gridimagesearch;

import java.io.Serializable;

import android.util.Log;

public class Setting implements Serializable {

	private static final long serialVersionUID = -2898173596189304069L;
	private String size;
	private String color;
	private String type;
	private String site;
	private String query;
	
	Setting(String mySize, String myColor, String myType, String mySite){
		size= mySize.toLowerCase(); 
		color= myColor.toLowerCase() ; 
		type= myType.toLowerCase();
		site= mySite;
		
		
	}
	Setting(){
		size= ""; 
		color= "" ; 
		type= "";
		site= "";
	}
	
	
	public String getSize(){
		return size;
	}
	
	public String getColor(){
		return color;
	}
	
	public String getType(){
		return type; 
	}
	
	public String getSite(){
		return site;
	}
	
	public String getQuery(){
		 query="";
		 if (!color.equals("any") && !color.equals("")  ){
			 query += "&imgcolor="+ color;
	     }
	     if (!size.equals("any") && !size.equals("")  ){
//	    	 Log.d("DEBUG","size "+ size);
	    	 query += "&imgsz="+ size;
	     }
	     if (!type.equals("any") && !type.equals("")  ){
	    	 query += "&imgtype="+ type;
	     }
	     if (site.equals("")){
	    	 query += site;
	     }
	     return query;
	}
	
}
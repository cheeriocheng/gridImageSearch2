package com.chengxu.gridimagesearch;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//Refer to google image api with java 
//https://developers.google.com/image-search/v1/jsondevguide?hl=ja

//Serializable allows the Extra to encode and decode the imageResult class and pass it in intent
public class ImageResult implements Serializable { 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7073596205120958111L;
	private String fullUrl;
	private String thumbUrl; 
	
	ImageResult(JSONObject json){
		try{
			this.fullUrl = json.getString("url"); 
			this.thumbUrl= json.getString("tbUrl");		
		}catch(JSONException e){
			this.fullUrl = null; 
			this.thumbUrl= null;		
		}
	}
	
	public String getFullUrl (){
		return fullUrl; 
	}
	
	public String getThumbUrl (){
		return thumbUrl; 
	}
	
	public String toString(){
		return this.thumbUrl;
	}
	
	//static method; access directly from ImageResult
	//go through JsonArray add each objects
	public static ArrayList<ImageResult> fromJSONArray(
			JSONArray array) {
		ArrayList<ImageResult> results = new ArrayList<ImageResult>();
		for (int x=0; x<array.length();x++){
			try{
				results.add(new ImageResult(array.getJSONObject(x)));
			}catch (JSONException e){
				e.printStackTrace();
			}
		}
		
		return results;
	}
	
}

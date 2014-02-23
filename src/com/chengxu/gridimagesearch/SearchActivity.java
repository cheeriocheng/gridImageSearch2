package com.chengxu.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {
	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
	int startCount= 0 ;
	String searchCriteria = "";
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultsArrayAdapter imageAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		Log.v("system", "onCreate was just called!");
		
		setupViews();
		imageAdapter = new ImageResultsArrayAdapter (this, imageResults);
		//attach adapter to the grid view here 
		gvResults.setAdapter(imageAdapter);
		
		//click on thumb nails  
		gvResults.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> adapter, View parent,int position, long rowId){
				Intent i = new Intent (getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				//parameter to pass to the activity 
				//i.putExtra("url", imageResult.getfullUrl());
				
				//alternatively, pass a serializable 
				i.putExtra("result", imageResult ) ; 
				//start with parameter 
				startActivity (i); 
				
			}
			
		});

	}
	
	protected void onResume() {
		super.onResume();
		Log.v("system", "onResume was just called!");
	}

	protected void onPause() {
		super.onPause();
		Log.v("system", "onPause was just called!");
	}

	//inflate action bar 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;

	}
	
	private final int REQUEST_CODE = 20;
	Setting currentSetting ;
	//call this when action bar icon is clicked
	public void onSettings(MenuItem mi){
		Intent i = new Intent (this, SettingActivity.class);
		//if currentSetting exists , send settings over to setting activity  
		if (currentSetting!=null){
			i.putExtra("settings",currentSetting);
		}else {
			Log.d("DEBUG","no settings exist yet ");
		}
		startActivityForResult(i, REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//	  Log.d("DEBUG", "back in main search page" + requestCode);
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
	     currentSetting = (Setting) data.getSerializableExtra("settings");
	     searchCriteria = currentSetting.getQuery();
	     //TODO update results 
//	     onImageSearch();
	  }
	} 
	
	//push button to load more image 
	public void onLoadMore(View v){
		startCount += 8 ;
		onImageSearch(v);
	}
	
	
	public void onImageSearch(View v) {
		String query = etQuery.getText().toString();
		if (startCount == 0){
			Toast.makeText(this, "Searching for " + query, Toast.LENGTH_LONG)
					.show();
			View b = findViewById(R.id.bt_more);
			b.setVisibility(View.VISIBLE);
		}
		Log.d("DEBUG", "seach parameter: " + searchCriteria);
		
		// load async http client
		// see example http://loopj.com/android-async-http/
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(
				"https://ajax.googleapis.com/ajax/services/search/images?rsz=8&"
						+ "start=" + Integer.toString(startCount) + searchCriteria +"&v=1.0&q=" + Uri.encode(query),
				// client.get(Uri.encode("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&q=android"),
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject response) {
						JSONArray imageJsonResutls = null;
						Log.d("DEBUG", "in http handler");

						try {
							imageJsonResutls = response.getJSONObject(
									"responseData").getJSONArray("results");
							imageResults.clear();
							// imageResults.addAll(ImageResult.fromJSONArray(imageJsonResutls));
							// mutate for adapter
							imageAdapter.addAll(ImageResult
									.fromJSONArray(imageJsonResutls));
							Log.v("DEBUG", imageResults.toString());
							//

						} catch (JSONException e) {
							Log.d("DEBUG", "http parse fail");
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(java.lang.Throwable e,
							org.json.JSONObject errorResponse) {
						Log.d("DEBUG", "JsonHttpResponseHandler fail");
					}
				});
		

	}

	public void setupViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		btnSearch = (Button) findViewById(R.id.btnSearch);
	}

}
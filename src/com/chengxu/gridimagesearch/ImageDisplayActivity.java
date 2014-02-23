package com.chengxu.gridimagesearch;

import com.loopj.android.image.SmartImageView;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ImageDisplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		//retrieve parameter in bundle from intent 
		// String url = getIntent ().getStringExtra("url");
		//or, retrieve the object 
		ImageResult result = (ImageResult) getIntent().getSerializableExtra("result"); 
		
		SmartImageView ivImage = (SmartImageView) findViewById (R.id.ivResult);

		// ivImage.setImageUrl(url);
		ivImage.setImageUrl(result.getFullUrl());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_display, menu);
		return true;
	}

}

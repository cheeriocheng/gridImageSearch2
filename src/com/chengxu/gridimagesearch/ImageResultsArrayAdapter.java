package com.chengxu.gridimagesearch;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.loopj.android.image.SmartImageView;

public class ImageResultsArrayAdapter extends ArrayAdapter<ImageResult> {
	public ImageResultsArrayAdapter(Context context, List<ImageResult> images) {
		// this will show url results as text
		// super(context, android.R.layout.simple_list_item_1,images);
		super(context, R.layout.item_image_result, images);
	}

	@Override
	// take an item and turn it into a view
	public View getView(int position, View convertView, ViewGroup parent) {
		//?
		ImageResult imageInfo = this.getItem(position);
		SmartImageView ivImage;

		if (convertView == null) {
			// create the view for the first time
			LayoutInflater inflator = LayoutInflater.from(getContext());
			ivImage = (SmartImageView) inflator.inflate(
					R.layout.item_image_result, parent, false);

		} else {
			// recycle a view to conserve memory
			ivImage = (SmartImageView)convertView; 
			ivImage.setImageResource(android.R.color.transparent);
		}
		ivImage.setImageUrl(imageInfo.getThumbUrl());
		
		return ivImage;

	}
}

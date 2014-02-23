package com.chengxu.gridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;


public class SettingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		//read settings from serach activity, if exists. 
		Setting s = (Setting) getIntent().getSerializableExtra("settings");
		//if no setting is given 
		if (s==null) {
			Log.d("DEBUG","no setting received in setting activity");
		}
		//otherwise retrieve previous setting   
		else {
			Log.d("DEBUG","set choices to previous");
			setSpinnerToValue((Spinner)findViewById(R.id.sp_image_size), s.getSize()); 
			setSpinnerToValue((Spinner)findViewById(R.id.sp_image_color), s.getColor());
			setSpinnerToValue((Spinner)findViewById(R.id.sp_image_type), s.getType());
			((EditText)findViewById(R.id.et_site)).setText(s.getSite());
		}

		// respond to save button click
		final Button button = (Button) findViewById(R.id.btn_save);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// get setting info
				String sizeValue = ((Spinner) findViewById(R.id.sp_image_size))
						.getSelectedItem().toString();
				String colorValue = ((Spinner) findViewById(R.id.sp_image_color))
						.getSelectedItem().toString();
				String typeValue = ((Spinner) findViewById(R.id.sp_image_type))
						.getSelectedItem().toString();
				String siteValue = ((EditText) findViewById(R.id.et_site))
						.getText().toString();
				
				Setting s = new Setting(sizeValue, colorValue, typeValue,
						siteValue);
				Log.d("DEBUG","color filter: "+ colorValue );
				Log.d("DEBUG","site filter: "+ siteValue );

				Intent i = new Intent(getApplicationContext(),SearchActivity.class);
				
				// alternatively, pass a serializable
				i.putExtra("settings", s);
				// start with parameter
				setResult(RESULT_OK, i);
				finish();

			}
		});

	}
	

	public void setSpinnerToValue(Spinner spinner, String value) {
		int index = 0;
		SpinnerAdapter adapter = spinner.getAdapter();
		for (int i = 0; i < adapter.getCount(); i++) {
			if (adapter.getItem(i).equals(value)) {
				index = i;
			}
		}
		spinner.setSelection(index);
	}


}

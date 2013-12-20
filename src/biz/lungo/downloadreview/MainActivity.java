package biz.lungo.downloadreview;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	private static final int REQUEST_CODE = 1;
	EditText etUrl;
	TextView tvDestination;
	Button buttonWhere, buttonSave;
	static Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		setContentView(R.layout.activity_main);
		etUrl = (EditText) findViewById(R.id.editTextUrl);
		tvDestination = (TextView) findViewById(R.id.textViewUrl);
		buttonWhere = (Button) findViewById(R.id.buttonWhere);
		buttonSave = (Button) findViewById(R.id.buttonSave);		
		buttonWhere.setOnClickListener(this);
		buttonSave.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.buttonWhere:
			Intent i = new Intent(this, FileManager.class);
			startActivityForResult(i, REQUEST_CODE);
			break;
		case R.id.buttonSave:
			//TODO
			break;
		}		
	}	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		tvDestination.setText(data.getStringExtra("file_path"));
		super.onActivityResult(requestCode, resultCode, data);
	}
}
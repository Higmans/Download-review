package biz.lungo.downloadreview;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private static final int REQUEST_CODE = 1;
	boolean isFileDownloading = false;
	EditText etUrl;
	TextView tvDestination, tvProgress;
	Button buttonWhere, buttonSave;
	ProgressBar pb;
	static Activity activity;
	DownloadTask dt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		setContentView(R.layout.activity_main);
		etUrl = (EditText) findViewById(R.id.editTextUrl);
		tvDestination = (TextView) findViewById(R.id.textViewUrl);
		tvProgress = (TextView) findViewById(R.id.textViewProgress);
		buttonWhere = (Button) findViewById(R.id.buttonWhere);
		buttonSave = (Button) findViewById(R.id.buttonSave);		
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		buttonWhere.setOnClickListener(this);
		buttonSave.setOnClickListener(this);
		changeButton();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.buttonWhere:
			Intent i = new Intent(this, FileManager.class);
			startActivityForResult(i, REQUEST_CODE);
			break;
		case R.id.buttonSave:
			if (!isFileDownloading){
				stopDownload();
			}
			else{
				startDownload();
			}
			changeButton();
			break;
		}		
	}	
	private void startDownload() {	
		dt = new DownloadTask();	
		dt.execute(etUrl.getText().toString(), tvDestination.getText().toString());
	}

	private void stopDownload() {
		dt.cancel(true);
	}

	private void changeButton() {
		if (isFileDownloading){
			buttonSave.setText("Стоп");
			isFileDownloading = false;
		}
		else{
			buttonSave.setText("Сохранить");
			isFileDownloading = true;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		tvDestination.setText(data.getStringExtra("file_path"));
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	private class DownloadTask extends AsyncTask<String, Integer, Long>{
		@Override
		protected Long doInBackground(String... data) {
			Long counter = 0l;
			for (int i = 1; i <= 100; i++){
				publishProgress(i);
				counter += 3;
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (isCancelled()){
					break;
				}
			}
			return counter;
		}
		@Override
		protected void onProgressUpdate(Integer... values) {
			tvProgress.setText(values[0] + " %");
			pb.setProgress(values[0]);
			super.onProgressUpdate(values);
		}
		@Override
		protected void onPostExecute(Long result) {
			Toast.makeText(activity, "Finished! " + result, Toast.LENGTH_LONG).show();
			super.onPostExecute(result);
		}
		@Override
		protected void onCancelled(Long result) {
			Toast.makeText(activity, "Облом! " + result, Toast.LENGTH_LONG).show();
			super.onCancelled(result);
		}
	}
}
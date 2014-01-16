package biz.lungo.downloadreview;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
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
	
	
	private class DownloadTask extends AsyncTask<String, Integer, Long> {
		@Override
		protected Long doInBackground(String... data) {
			String urlString = data[0];
			String destinationString = data[1];
			int count;
			try {
				URL url = new URL(urlString);
				URLConnection conection = url.openConnection();
				conection.connect();

				// this will be useful so that you can show a typical 0-100%
				// progress bar
				int lenghtOfFile = conection.getContentLength();
				//Map<String, List<String>> nameOfFile = conection.getHeaderFields();

				// download the file
				InputStream input = new BufferedInputStream(url.openStream(), 8192);

				// Output stream
				OutputStream output = new FileOutputStream(destinationString + "/myFile.pdf");

				byte byteData[] = new byte[1024];

				long total = 0;

				while ((count = input.read(byteData)) != -1) {
					total += count;
					// publishing the progress....
					// After this onProgressUpdate will be called
					publishProgress((int) ((total * 100) / lenghtOfFile));

					// writing data to file
					output.write(byteData, 0, count);
				}

				// flushing output
				output.flush();

				// closing streams
				output.close();
				input.close();

			} catch (Exception e) {
				Log.e("Error: ", e.getMessage());
			}
			return Long.valueOf(500);
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
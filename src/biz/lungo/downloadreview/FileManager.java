package biz.lungo.downloadreview;

import java.io.File;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FileManager extends Activity implements OnClickListener {
	TextView tv;
	LinearLayout llRoot;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.file_activity);
		llRoot = (LinearLayout) findViewById(R.id.linearLayoutFiles);
		File file = Environment.getExternalStorageDirectory();
		String fileListString[] = file.list();
		File fileList[] = file.listFiles();
		Arrays.sort(fileListString);
		for (int i = 0; i < fileListString.length; i++){
			FileView fv = new FileView(this, fileList[i]);
			fv.setText(fileListString[i]);
			fv.setOnClickListener(this);
			llRoot.addView(fv);
		}		
	}
	@Override
	public void onClick(View v) {
		Intent i = new Intent();
		i.putExtra("file_path", ((FileView)v).getFile().getAbsolutePath());
		setResult(RESULT_OK, i);
		finish();
	}

}

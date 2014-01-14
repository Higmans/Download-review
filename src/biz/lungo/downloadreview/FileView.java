package biz.lungo.downloadreview;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FileView extends LinearLayout implements OnClickListener, OnLongClickListener {
	ImageView image;
	TextView tvName, tvSize, tvDate;
	final int WHITE = 0;
	final int GREY = 1;
	LinearLayout llRoot;
	File file;

	public FileView(Context context, File file, int colorCount) {
		super(context);
		llRoot = (LinearLayout) LinearLayout.inflate(MainActivity.activity, R.layout.file_view, null);
		image = (ImageView) llRoot.findViewById(R.id.imageView);
		tvName = (TextView) llRoot.findViewById(R.id.textViewName);
		tvSize = (TextView) llRoot.findViewById(R.id.textViewSize);
		tvDate = (TextView) llRoot.findViewById(R.id.textViewDate);
		tvSize.setGravity(Gravity.BOTTOM);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		try {
			date = new Date(file.getCanonicalFile().lastModified());
		} catch (IOException e) {
			e.printStackTrace();
		}
		String dateModified = sdf.format(date);
		llRoot.setOnClickListener(this);
		llRoot.setOnLongClickListener(this);
		llRoot.setPadding(0, 10, 0, 10);
		this.file = file;
		if (file.isDirectory()) {
			image.setImageResource(R.drawable.folder);
			tvSize.setText("<DIR>");
		} else {
			image.setImageResource(R.drawable.file);
			String fileSize = getSizeString(file.length());
			tvSize.setText(fileSize + "");
		}
		tvName.setText(file.getName());
		tvDate.setText(dateModified);
		switch (colorCount) {
		case WHITE:
			llRoot.setBackgroundColor(Color.WHITE);
			break;
		case GREY:
			llRoot.setBackgroundColor(Color.LTGRAY);
			break;
		}
	}
	private String getSizeString(long length) {
		String size = "";
		String suffix = "";
		if (length < 1024){
			size = length + "";
			suffix = "B";
		}
		else if (length >= 1024 && length < 1048576){
			int kb = (int) (length) / 1024;
			int b = (int) (length) - (kb * 1024);
			size = kb + "," + ((b > 99)?(b + "").substring(0, 1) : b);
			suffix = "KB";
		}
		else if (length >= 1048576){
			int mb = (int) (length) / 1048576;
			int kb = (int) (length) - (mb * 1048576);
			size = mb + "," + ((kb > 99)?(kb + "").substring(0, 2) : kb);
			suffix = "MB";
		}		
		return size + " " + suffix;
	}
	public File getFile() {
		return file;
	}
	@Override
	public void onClick(View v) {		
		if (file.isDirectory()){
			FileManager.llRoot.removeAllViews();
			FileManager.currentFile = file;
			FileManager.makeFileManager(file.listFiles());
		}
		else{
			Toast.makeText(MainActivity.activity, 
						getResources().getString(R.string.error_not_directory), 
						Toast.LENGTH_LONG).show();
		}
	}
	@Override
	public boolean onLongClick(View v) {
		if (file.isDirectory()){
			FileManager.transferPath(file.getAbsolutePath());
		}
		else{
			Toast.makeText(MainActivity.activity, 
						getResources().getString(R.string.error_not_directory), 
						Toast.LENGTH_LONG).show();
		}
		return false;
	}

}

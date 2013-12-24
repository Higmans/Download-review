package biz.lungo.downloadreview;

import java.io.File;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FileView extends LinearLayout implements OnClickListener {
	ImageView image;
	TextView tvName, tvSize;
	final int WHITE = 0;
	final int GREY = 1;
	LinearLayout llRoot;
	File file;

	public FileView(Context context, File file, int colorCount) {
		super(context);
		/*llRoot = (LinearLayout) LinearLayout.inflate(
				MainActivity.activity, R.layout.file_view, null);*/
		View.inflate(MainActivity.activity, R.layout.file_view, null);
		image = (ImageView) findViewById(R.id.imageView);
		tvName = (TextView) findViewById(R.id.textViewName);
		tvSize = (TextView) findViewById(R.id.textViewSize);
		setOnClickListener(this);
		setPadding(0, 10, 0, 10);
		this.file = file;
		if (file.isDirectory()) {
			image.setImageResource(R.drawable.folder);
		} else {
			image.setImageResource(R.drawable.file);
			long fileSize = file.length()/1024;
			tvSize.setText(fileSize + "");
		}
		tvName.setText(file.getName());
		switch (colorCount) {
		case WHITE:
			setBackgroundColor(Color.WHITE);
			break;
		case GREY:
			setBackgroundColor(Color.LTGRAY);
			break;
		}
	}
	public File getFile() {
		return file;
	}
	@Override
	public void onClick(View v) {
				
	}

}

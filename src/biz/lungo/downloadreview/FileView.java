package biz.lungo.downloadreview;

import java.io.File;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

public class FileView extends TextView {
	File file;

	public File getFile() {
		return file;
	}

	public FileView(Context context, File file) {
		super(context);
		setPadding(0, 0, 0, 10);
		this.file = file;
		if (file.isFile()){
			setBackgroundColor(Color.LTGRAY);
		}
	}
}

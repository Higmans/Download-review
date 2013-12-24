package biz.lungo.downloadreview;

import java.io.File;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

public class FileTextView extends TextView {
	File file;
	final int WHITE = 0;
	final int GREY = 1;

	public File getFile() {
		return file;
	}

	public FileTextView(Context context, File file, int colorCount) {
		super(context);
		setPadding(0, 10, 0, 10);
		this.file = file;
		switch(colorCount){
		case WHITE:
			setBackgroundColor(Color.WHITE);
			break;
		case GREY:
			setBackgroundColor(Color.LTGRAY);
			break;
		}
	}
}

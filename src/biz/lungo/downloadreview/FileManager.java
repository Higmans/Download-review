package biz.lungo.downloadreview;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FileManager extends Activity {
	private static Activity activity;
	static LinearLayout llRoot;
	static int colorCount = 0;
	static File currentFile;
	TextView tv;
	ArrayList<File> fileList;
	String fileArrayString[];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		setContentView(R.layout.file_activity);
		llRoot = (LinearLayout) findViewById(R.id.linearLayoutFiles);
		File file = Environment.getExternalStorageDirectory();
		currentFile = file;
		makeFileManager(file.listFiles());
	}
	static void makeFileManager(File filesArray[]) {
		ArrayList<File> files = sortByName(filesArray);
		for (int i = 0; i < files.size(); i++){
			if (files.get(i).getName().charAt(0) != '.') {
				FileView fv = new FileView(MainActivity.activity, files.get(i), colorCount % 2);
				llRoot.addView(fv.llRoot);
				colorCount++;
			}
		}
	}
	static private ArrayList<File> sortByName(File[] array) {
		ArrayList<File> result = new ArrayList<File>();
		ArrayList<File> folders = new ArrayList<File>();
		ArrayList<File> files = new ArrayList<File>();
		for (int i = 0; i < array.length; i++){
			if (array[i].isDirectory()){
				folders.add(array[i]);
			}
			else{
				files.add(array[i]);
			}
		}
		List<String> folderNamesList = Arrays.asList(extractNames(folders));
		List<String> fileNamesList = Arrays.asList(extractNames(files));
		List<File> sortedFolderList = new ArrayList<File>();
		List<File> sortedFileList = new ArrayList<File>();
		Collections.sort(folderNamesList, String.CASE_INSENSITIVE_ORDER);
		Collections.sort(fileNamesList, String.CASE_INSENSITIVE_ORDER);
		for (int i = 0; i < folderNamesList.size(); i++){
			for (int j = 0; j < folders.size(); j++){
				if (folderNamesList.get(i).equals(folders.get(j).getName())){
					sortedFolderList.add(folders.get(j));
					break;
				}
			}
		}
		for (int i = 0; i < fileNamesList.size(); i++){
			for (int j = 0; j < files.size(); j++){
				if (fileNamesList.get(i).equals(files.get(j).getName())){
					sortedFileList.add(files.get(j));
					break;
				}
			}
		}
		result.addAll(sortedFolderList);
		result.addAll(sortedFileList);
		return result;
	}
	static private String[] extractNames(ArrayList<File> list) {
		String result[] = new String[list.size()];
		for (int i = 0; i < list.size(); i++){
			result[i] = list.get(i).getName();
		}
		return result;
	}
	static void transferPath(String path){
		Intent i = new Intent();
		i.putExtra("file_path", path);
		activity.setResult(RESULT_OK, i);
		activity.finish();
	}
	@Override
	public void onBackPressed() {
		if (!currentFile.getAbsolutePath().equals(Environment.getExternalStorageDirectory().getAbsolutePath())){
			currentFile = currentFile.getParentFile();
			llRoot.removeAllViews();
			makeFileManager(currentFile.listFiles());
		}
		else{
			transferPath("");
		}
	}

}

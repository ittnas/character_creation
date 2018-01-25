package GUI;

import java.io.File;
import java.io.FilenameFilter;

import GUI.MainWindow.FileType;

public class ExportFileNameFilter implements FilenameFilter {
	
	private FileType fileType;
	public ExportFileNameFilter(FileType fileType) {
		this.fileType = fileType;
	}
	public boolean accept(File dir, String name) {
		if(name.endsWith(fileType.getEnding())) {
			return true;
		} else return false;
	}

}

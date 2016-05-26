package gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import utilities.Manager;

//.set - Simple Entities TextFile
//.oef - Object Entities File

public class EFileFilter extends FileFilter {
	
	private Manager manager;

	public EFileFilter(Manager manager){
		
		this.manager = manager;
	}
	
	@Override
	public boolean accept(File file) {

		if(file.isDirectory())
			return true;

		String ext = manager.getExtension(file);
		return ext.equals("set") || ext.equals("oef");
	}

	@Override
	public String getDescription() {

		return "Entities File (*.set, *.oef)"; 
	}
}

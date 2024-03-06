package strategy;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import geometry.Shape;

public class DrawingFile implements File {
	private ArrayList<Shape> shapes;
	
	public DrawingFile(ArrayList<Shape> shapes) {
		this.shapes = shapes;
	}

	@Override
	public void save(){
		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home")));
		jFileChooser.setDialogTitle("Save file");
		jFileChooser.setFileFilter(new FileNameExtensionFilter("Bin File (*.bin)", "bin"));
		jFileChooser.setAcceptAllFileFilterUsed(true);
		int result = jFileChooser.showSaveDialog(null);
		if(result == JFileChooser.APPROVE_OPTION) {
			java.io.File fileToSave = jFileChooser.getSelectedFile();
			try {
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileToSave.getAbsolutePath() + ".bin"));
				
				objectOutputStream.writeObject(shapes);
				objectOutputStream.close();
				JOptionPane.showMessageDialog(null, "File saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "File not saved successfully!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

}

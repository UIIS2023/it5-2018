package strategy;

import java.io.FileWriter;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LogFile implements File {
private DefaultListModel<String> modelLogList;
	
	public LogFile(DefaultListModel<String> modelLogList) {
		this.modelLogList = modelLogList;
	}

	@Override
	public void save() {
		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home")));
		jFileChooser.setDialogTitle("Save file");
		jFileChooser.setFileFilter(new FileNameExtensionFilter("Text File (*.txt)", "txt"));
		jFileChooser.setAcceptAllFileFilterUsed(true);
		int result = jFileChooser.showSaveDialog(null);
		if(result == JFileChooser.APPROVE_OPTION) {
			java.io.File fileToSave = jFileChooser.getSelectedFile();
			try {
				FileWriter fileWriter = new FileWriter(fileToSave.getAbsolutePath() + ".txt");
				for(int i = 0; i < modelLogList.size(); i++) {
					fileWriter.write(modelLogList.getElementAt(i) + "\n");
				}
				fileWriter.close();
				JOptionPane.showMessageDialog(null, "File saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "File not saved successfully!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

}

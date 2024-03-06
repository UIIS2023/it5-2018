package mvc;

import javax.swing.JFrame;

public class Application {

	public static void main(String[] args) {
		//System.out.println("Dobrodošli na vežbe iz predmeta Dizajnerski obrasci.");
		DrawingModel model = new DrawingModel();
		DrawingFrame frame = new DrawingFrame();
		 
		frame.getView().setModel(model);
		DrawingController controller = new DrawingController(model, frame);
		frame.setController(controller);
		
		frame.setSize(1200, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}

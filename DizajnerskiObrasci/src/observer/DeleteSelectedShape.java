package observer;

import mvc.DrawingFrame;
import mvc.DrawingModel;

public class DeleteSelectedShape implements Observer{
	private DrawingFrame frame;
	private DrawingModel model;
	
	public DeleteSelectedShape(DrawingFrame frame){
		this.frame = frame;
	}
	
	@Override
	public void update(DrawingModel model) {
		this.model = model;
		
		if(model.getSelectedShapes().size() > 0){
			this.frame.getTglbtnDelete().setEnabled(true);
		}
		else{
			this.frame.getTglbtnDelete().setEnabled(false);
		}
	}
	
}

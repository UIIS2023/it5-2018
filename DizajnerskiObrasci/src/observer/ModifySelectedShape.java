package observer;

import mvc.DrawingFrame;
import mvc.DrawingModel;

public class ModifySelectedShape implements Observer{
	private DrawingFrame frame;
	private DrawingModel model;
	
	public ModifySelectedShape(DrawingFrame frame){
		this.frame = frame;
	}
	
	@Override
	public void update(DrawingModel model) {
		this.model = model;
		
		if(model.getSelectedShapes().size() == 1){
			this.frame.getTglbtnModify().setEnabled(true);
		}
		else{
			this.frame.getTglbtnModify().setEnabled(false);
		}
	}
	
}

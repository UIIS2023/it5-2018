package command;

import geometry.Rectangle;
import geometry.Shape;

public class UpdateRectangleCmd implements Command {
	private Rectangle oldState;
	private Rectangle newState;
	private Rectangle original = new Rectangle();
	
	public UpdateRectangleCmd(Rectangle oldState, Rectangle newState){
		this.oldState = oldState;
		this.newState = newState;
	}
	
	@Override
	public void execute() {
		/*original.getUpperLeftPoint().setX(oldState.getUpperLeftPoint().getX());
		original.getUpperLeftPoint().setY(oldState.getUpperLeftPoint().getY());
		original.setWidth(oldState.getWidth());
		original.setHeight(oldState.getHeight());
		original.setColor(oldState.getColor());
		original.setInnerColor(oldState.getInnerColor());*/
		original = oldState.clone();
		
		oldState.getUpperLeftPoint().setX(newState.getUpperLeftPoint().getX());
		oldState.getUpperLeftPoint().setY(newState.getUpperLeftPoint().getY());
		oldState.setWidth(newState.getWidth());
		oldState.setHeight(newState.getHeight());
		oldState.setColor(newState.getColor());
		oldState.setInnerColor(newState.getInnerColor());
	}

	@Override
	public void unexecute() {
		oldState.getUpperLeftPoint().setX(original.getUpperLeftPoint().getX());
		oldState.getUpperLeftPoint().setY(original.getUpperLeftPoint().getY());
		oldState.setWidth(original.getWidth());
		oldState.setHeight(original.getHeight());
		oldState.setColor(original.getColor());
		oldState.setInnerColor(original.getInnerColor());
	}

	@Override
	public Shape getShape(){
		return (Shape)oldState;
	}
	
	public String toString() {
		return "MODIFY " + original + " into " + newState;
	}
	
}

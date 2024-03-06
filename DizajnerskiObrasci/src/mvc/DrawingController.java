package mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

//import composite.SremBanatBacka;
import geometry.Shape;
import observer.DeleteSelectedShape;
import observer.ModifySelectedShape;
import observer.SelectedShape;
import geometry.Point;
import geometry.Line;
import geometry.Rectangle;
import geometry.Circle;
import geometry.Donut;
import adapter.HexagonAdapter;
import packages.DlgPoint;
import packages.DlgLine;
import packages.DlgRectangle;
import strategy.FileManager;
import strategy.DrawingFile;
import strategy.File;
import strategy.LogFile;
import packages.DlgCircle;
import packages.DlgDonut;
import packages.DlgHexagon;

import command.Command;
import command.AddPointCmd;
import command.AddLineCmd;
import command.AddCircleCmd;
import command.AddDonutCmd;
import command.AddRectangleCmd;
import command.AddHexagonCmd;
import command.RemovePointCmd;
import command.RemoveLineCmd;
import command.RemoveCircleCmd;
import command.RemoveDonutCmd;
import command.RemoveRectangleCmd;
import command.RemoveShapeCmd;
import command.SelectShapeCmd;
import command.RemoveHexagonCmd;
import command.UpdatePointCmd;
import command.UpdateLineCmd;
import command.UpdateCircleCmd;
import command.UpdateDonutCmd;
import command.UpdateRectangleCmd;
import command.UpdateHexagonCmd;
import command.ToBackCmd;
import command.ToFrontCmd;
import command.BringToBackCmd;
import command.BringToFrontCmd;

public class DrawingController {
	
	private DrawingModel model;
	private DrawingFrame frame;
	private SelectedShape selectedShape;
	private DeleteSelectedShape deleteSelectedShape;
	private ModifySelectedShape modifySelectedShape;
	
	private Stack<Command> undoStack = new Stack<Command>();
	private Stack<Command> redoStack = new Stack<Command>();
	private int ind = 0;//ako se nesto nacrta ili izmeni da ne moze da vrati ono sto je ponisteno preko undo operacije
	//private ArrayList<Shape> pom = new ArrayList<Shape>();
	private BufferedReader bufferedReader;
	
	public DrawingController(DrawingModel model, DrawingFrame frame){
		this.model = model;
		this.frame = frame;
		
		selectedShape = new SelectedShape();
		deleteSelectedShape = new DeleteSelectedShape(this.frame);
		modifySelectedShape = new ModifySelectedShape(this.frame);
		
		selectedShape.addObserver(deleteSelectedShape);
		selectedShape.addObserver(modifySelectedShape);
		selectedShape.setModel(this.model);
	}
 
	public void mouseClick(MouseEvent e) {

		Shape newShape = null;
		if(frame.getTglbtnSelection().isSelected()){
			//frame.getView().getModel().setSelected(null);
			model.setSelected(null);
			int selectedInd = 0;//indikator za slucaj da se klikne na prazan prostor da se deselektuju svi oblici
			Iterator<Shape> it = model.getShapes().iterator();
			while(it.hasNext()){
				Shape shape = it.next();
				//shape.setSelected(false);
				if(shape.contains(e.getX(), e.getY())){
					//model.setSelected(shape);
					if(selectedInd == 1){//ako se klikne na tacku preseka vise oblika da se selektuje onaj koji je kasnije nacrtan oblik
						model.getSelectedShapes().remove(model.getSelectedShapes().size()-1);
					}
					model.getSelectedShapes().add(shape);
					selectedInd = 1;
				}
			}
			if(selectedInd == 0){
				//model.getSelectedShapes().clear();
				
				SelectShapeCmd selectShapeCmd = new SelectShapeCmd(model.getSelectedShapes());
				selectShapeCmd.execute();
				undoStack.push(selectShapeCmd);
				model.getSelectedShapes().clear();
				frame.getModelLogList().addElement(selectShapeCmd.toString());
			}
			/*if(model.getSelected() != null){
				model.getSelected().setSelected(true);
			}*/
			/*Iterator<Shape> itSelected = model.getSelectedShapes().iterator();
			while(itSelected.hasNext()){
				Shape shape = itSelected.next();
				shape.setSelected(true);
			}*/
			else{
				SelectShapeCmd selectShapeCmd = new SelectShapeCmd(model.getSelectedShapes());
				selectShapeCmd.execute();
				undoStack.push(selectShapeCmd);
				frame.getModelLogList().addElement(selectShapeCmd.toString());
			}
			
			selectedShape.setModel(model);
		}
		else if(frame.getTglbtnPoint().isSelected()){
			newShape = new Point(e.getX(), e.getY(),false, frame.getBtnOuterColor().getBackground());
			frame.getTglbtnPoint().setSelected(false);
		}
		else if(frame.getTglbtnLine().isSelected()){
			if(model.getStartPoint() == null){
				model.setStartPoint(new Point(e.getX(), e.getY()));
			}
			else{
				newShape = new Line(frame.getView().getModel().getStartPoint(), new Point(e.getX(), e.getY()), false, frame.getBtnOuterColor().getBackground());
				model.setStartPoint(null);
			}
		}
		else if(frame.getTglbtnRectangle().isSelected()){
			DlgRectangle dlg = new DlgRectangle();
			dlg.setModal(true);
			//dlg.setRect(new Rectangle(new Point(e.getX(), e.getY()), -1, -1));
			dlg.getTxtUpperLeftPointX().setText(Integer.toString(e.getX()));
			dlg.getTxtUpperLeftPointY().setText(Integer.toString(e.getY()));
			dlg.getBtnOuterColor().setBackground(frame.getBtnOuterColor().getBackground());//pocetna boja
			dlg.getBtnInnerColor().setBackground(frame.getBtnInnerColor().getBackground());
			dlg.setVisible(true);
			if(!dlg.isOk){
				return;
			}
			try{
				newShape = dlg.getRect();
			}catch(Exception ex){
				JOptionPane.showMessageDialog(frame, "Wrong data type", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(frame.getTglbtnCircle().isSelected()){
			DlgCircle dlg = new DlgCircle();
			dlg.setModal(true);
			//dlg.setCircle(new Circle(new Point(e.getX(), e.getY()), -1));
			dlg.getTxtCenterX().setText(Integer.toString(e.getX()));
			dlg.getTxtCenterY().setText(Integer.toString(e.getY()));
			dlg.getBtnOuterColor().setBackground(frame.getBtnOuterColor().getBackground());
			dlg.getBtnInnerColor().setBackground(frame.getBtnInnerColor().getBackground());
			dlg.setVisible(true);
			if(!dlg.isOk){
				return;
			}
			try{
				newShape = dlg.getCircle();
			}catch(Exception ex){
				JOptionPane.showMessageDialog(frame, "Wrong data type", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(frame.getTglbtnDonut().isSelected()){
			DlgDonut dlg = new DlgDonut();
			dlg.setModal(true);
			//dlg.setDonut(new Donut(new Point(e.getX(), e.getY()), -1, -1));
			dlg.getTxtCenterX().setText(Integer.toString(e.getX()));
			dlg.getTxtCenterY().setText(Integer.toString(e.getY()));
			dlg.getBtnOuterColor().setBackground(frame.getBtnOuterColor().getBackground());
			dlg.getBtnInnerColor().setBackground(frame.getBtnInnerColor().getBackground());
			dlg.setVisible(true);
			if(!dlg.isOk){
				return;
			}
			try{
				newShape = dlg.getDonut();
			}catch(Exception ex){
				JOptionPane.showMessageDialog(frame, "Wrong data type", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(frame.getTglbtnHexagon().isSelected()){
			DlgHexagon dlg = new DlgHexagon();
			dlg.setModal(true);
			dlg.getTxtCenterPointX().setText(Integer.toString(e.getX()));
			dlg.getTxtCenterPointY().setText(Integer.toString(e.getY()));
			dlg.getBtnOuterColor().setBackground(frame.getBtnOuterColor().getBackground());
			dlg.getBtnInnerColor().setBackground(frame.getBtnInnerColor().getBackground());
			dlg.setVisible(true);
			if(!dlg.isOk){
				return;
			}
			try{
				newShape = dlg.getHexagonAdapt();
			}catch(Exception ex){
				JOptionPane.showMessageDialog(frame, "Wrong data type", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		if(newShape != null){
			//frame.getView().getModel().getShapes().add(newShape);
			//model.add(newShape);
			/*frame.getTglbtnUndo().setVisible(true);
			
			frame.getTglbtnToBack().setVisible(true);
			frame.getTglbtnToFront().setVisible(true);
			frame.getTglbtnBringToBack().setVisible(true);
			frame.getTglbtnBringToFront().setVisible(true);*/
			
			frame.getTglbtnUndo().setEnabled(true);
			
			frame.getTglbtnToBack().setEnabled(true);
			frame.getTglbtnToFront().setEnabled(true);
			frame.getTglbtnBringToBack().setEnabled(true);
			frame.getTglbtnBringToFront().setEnabled(true);
			
			if(newShape instanceof Point) {
				AddPointCmd addPointCmd = new AddPointCmd((Point)newShape, model);
				addPointCmd.execute();
				undoStack.push(addPointCmd);
			}
			else if(newShape instanceof Line) {
				AddLineCmd addLineCmd = new AddLineCmd((Line)newShape, model);
				addLineCmd.execute();
				undoStack.push(addLineCmd);
			}
			else if(newShape instanceof Donut) {
				AddDonutCmd addDonutCmd = new AddDonutCmd((Donut)newShape, model);
				addDonutCmd.execute();
				undoStack.push(addDonutCmd);
			}
			else if(newShape instanceof Circle) {
				AddCircleCmd addCircleCmd = new AddCircleCmd((Circle)newShape, model);
				addCircleCmd.execute();
				undoStack.push(addCircleCmd);
			}
			else if(newShape instanceof Rectangle) {
				AddRectangleCmd addRectangleCmd = new AddRectangleCmd((Rectangle)newShape, model);
				addRectangleCmd.execute();
				undoStack.push(addRectangleCmd);
			}
			else if(newShape instanceof HexagonAdapter) {
				AddHexagonCmd addHexagonCmd = new AddHexagonCmd((HexagonAdapter)newShape, model);
				addHexagonCmd.execute();
				undoStack.push(addHexagonCmd);
			}
			ind = 1;
			
			model.getSelectedShapes().clear();//svaki put kad se doda neki oblik brise se lista selektovanih oblika
			
			Iterator<Shape> itSelected = model.getShapes().iterator();//svaki put kad se doda neki oblik svi oblici koji su prethodno bili selektovani ce se deselektovati
			while(itSelected.hasNext()){
				Shape shape = itSelected.next();
				shape.setSelected(false);
			}
			frame.getModelLogList().addElement("ADD " + newShape.toString());
			
			selectedShape.setModel(model);
		}
		
		frame.repaint();
	}
	
	protected void delete(){
		int option = JOptionPane.showConfirmDialog(null, "Are you sure want to delete?", "Delete", JOptionPane.YES_NO_OPTION);
		//Shape selected = model.getSelected();
		/*Iterator<Shape> itSelected = model.getSelectedShapes().iterator();
		while(itSelected.hasNext()){
			Shape shape = itSelected.next();
		
			if(shape != null){
				//int option = JOptionPane.showConfirmDialog(null, "Are you sure want to delete?", "Delete", JOptionPane.YES_NO_OPTION);
				if(option == 0){
					//model.getShapes().remove(selected);
					if(shape instanceof Point) {
						RemovePointCmd removePointCmd = new RemovePointCmd((Point)shape, model);
						removePointCmd.execute();
						undoStack.push(removePointCmd);
					}
					else if(shape instanceof Line) {
						RemoveLineCmd removeLineCmd = new RemoveLineCmd((Line)shape, model);
						removeLineCmd.execute();
						undoStack.push(removeLineCmd);
					}
					else if(shape instanceof Donut) {
						RemoveDonutCmd removeDonutCmd = new RemoveDonutCmd((Donut)shape, model);
						removeDonutCmd.execute();
						undoStack.push(removeDonutCmd);
					}
					else if(shape instanceof Circle) {
						RemoveCircleCmd removeCircleCmd = new RemoveCircleCmd((Circle)shape, model);
						removeCircleCmd.execute();
						undoStack.push(removeCircleCmd);
					}
					else if(shape instanceof Rectangle) {
						RemoveRectangleCmd removeRectangleCmd = new RemoveRectangleCmd((Rectangle)shape, model);
						removeRectangleCmd.execute();
						undoStack.push(removeRectangleCmd);
					}
					else if(shape instanceof HexagonAdapter) {
						RemoveHexagonCmd removeHexagonCmd = new RemoveHexagonCmd((HexagonAdapter)shape, model);
						removeHexagonCmd.execute();
						undoStack.push(removeHexagonCmd);
					}
				}
				//model.setSelected(null);
				ind = 1;
			}
			else {
				JOptionPane.showMessageDialog(frame, "Select a shape first.", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
		}*/
		
		ArrayList<Shape> selectedShapes = model.getSelectedShapes();
		
		if(option == 0){
			RemoveShapeCmd removeShapeCmd = new RemoveShapeCmd(selectedShapes, model);
			removeShapeCmd.execute();
			undoStack.push(removeShapeCmd);
			frame.getModelLogList().addElement(removeShapeCmd.toString());
			
			ind = 1;
		}
		frame.getView().repaint();
		selectedShape.setModel(model);
	}
	
	protected void modify(){
		//Shape selected = model.getSelected();
		Shape selected = model.getSelectedShapes().get(0);
		if(selected != null){
			if(selected instanceof Point){
				Point point = (Point) selected;
				Point p= new Point(((Point) selected).getX(), ((Point) selected).getY());
				DlgPoint dlg = new DlgPoint();
				//dlg.setPoint(point);
				//dlg.setX(point.getX());
				//dlg.setY(point.getY());
				dlg.getTxtX().setText(Integer.toString(point.getX()));
				dlg.getTxtY().setText(Integer.toString(point.getY()));
				dlg.getBtnColor().setBackground(point.getColor());
				dlg.setModal(true);
				dlg.setVisible(true);
				if (dlg.isOk){
					p.setX(dlg.getP().getX());
					p.setY(dlg.getP().getY());
					p.setColor(dlg.getP().getColor());
					
					UpdatePointCmd updatePointCmd = new UpdatePointCmd(point, p);
					undoStack.push(updatePointCmd);
					frame.getModelLogList().addElement("MODIFY " + point + " into " + p);
					updatePointCmd.execute();
				}
			}
			else if(selected instanceof Line){
				Line line = (Line) selected;
				Point p11 = new Point(((Line) selected).getStartPoint().getX(), ((Line) selected).getStartPoint().getY());
				Point p12 = new Point(((Line) selected).getEndPoint().getX(), ((Line) selected).getEndPoint().getY());
				Line l = new Line(p11, p12);
				l.setColor(((Line) selected).getColor());
				
				DlgLine dlg = new DlgLine();
				//dlg.setLine(line);
				dlg.getTxtStartPointX().setText(Integer.toString(line.getStartPoint().getX()));
				dlg.getTxtStartPointY().setText(Integer.toString(line.getStartPoint().getY()));
				dlg.getTxtEndPointX().setText(Integer.toString(line.getEndPoint().getX()));
				dlg.getTxtEndPointY().setText(Integer.toString(line.getEndPoint().getY()));
				dlg.getBtnColor().setBackground(line.getColor());
				dlg.setModal(true);
				dlg.setVisible(true);
				if (dlg.isOk){
					Point p1 = new Point(dlg.getLine().getStartPoint().getX(), dlg.getLine().getStartPoint().getY());
					Point p2 = new Point(dlg.getLine().getEndPoint().getX(), dlg.getLine().getEndPoint().getY());
					l.setStartPoint(p1);
					l.setEndPoint(p2);
					l.setColor(dlg.getLine().getColor());
					
					UpdateLineCmd updateLineCmd = new UpdateLineCmd(line, l);
					frame.getModelLogList().addElement("MODIFY " + line + " into " + l);
					updateLineCmd.execute();
					undoStack.push(updateLineCmd);
				}
			}
			else if(selected instanceof Rectangle){
				Rectangle rect = (Rectangle) selected;
				Rectangle r = new Rectangle();
				DlgRectangle dlg = new DlgRectangle();
				//dlg.setRectangle(rect);
				dlg.getTxtUpperLeftPointX().setText(Integer.toString(rect.getUpperLeftPoint().getX()));
				dlg.getTxtUpperLeftPointY().setText(Integer.toString(rect.getUpperLeftPoint().getY()));
				dlg.getTxtWidth().setText(Integer.toString(rect.getWidth()));
				dlg.getTxtHeight().setText(Integer.toString(rect.getHeight()));
				dlg.getBtnInnerColor().setBackground(rect.getInnerColor());
				dlg.getBtnOuterColor().setBackground(rect.getColor());
				dlg.setModal(true);
				dlg.setVisible(true);
				if (dlg.isOk){
					r.getUpperLeftPoint().setX(dlg.getRect().getUpperLeftPoint().getX());
					r.getUpperLeftPoint().setY(dlg.getRect().getUpperLeftPoint().getY());
					r.setWidth(dlg.getRect().getWidth());
					r.setHeight(dlg.getRect().getHeight());
					r.setColor(dlg.getRect().getColor());
					r.setInnerColor(dlg.getRect().getInnerColor());
					
					UpdateRectangleCmd updateRectangleCmd = new UpdateRectangleCmd(rect, r);
					frame.getModelLogList().addElement("MODIFY " + rect + " into " + r);
					updateRectangleCmd.execute();
					undoStack.push(updateRectangleCmd);
				}
			}
			else if(selected instanceof Donut){
				Donut donut = (Donut) selected;
				Donut d = new Donut();
				DlgDonut dlg = new DlgDonut();
				//dlg.setDonut(donut);
				dlg.getTxtCenterX().setText(Integer.toString(donut.getCenter().getX()));
				dlg.getTxtCenterY().setText(Integer.toString(donut.getCenter().getY()));
				dlg.getTxtRadius().setText(Integer.toString(donut.getRadius()));
				dlg.getTxtInnerRadius().setText(Integer.toString(donut.getInnerRadius()));
				dlg.getBtnInnerColor().setBackground(donut.getInnerColor());
				dlg.getBtnOuterColor().setBackground(donut.getColor());
				dlg.setModal(true);
				dlg.setVisible(true);
				if (dlg.isOk){
					d.getCenter().setX(dlg.getDonut().getCenter().getX());
					d.getCenter().setY(dlg.getDonut().getCenter().getY());
					try {
						d.setRadius(dlg.getDonut().getRadius());
					} catch (Exception e) {
						e.printStackTrace();
					}
					d.setInnerRadius(dlg.getDonut().getInnerRadius());
					d.setColor(dlg.getDonut().getColor());
					d.setInnerColor(dlg.getDonut().getInnerColor());
					
					UpdateDonutCmd updateDonutCmd = new UpdateDonutCmd(donut, d);
					frame.getModelLogList().addElement("MODIFY " + donut + " into " + d);
					updateDonutCmd.execute();
					undoStack.push(updateDonutCmd);
				}
			}
			else if(selected instanceof Circle){
				Circle circle = (Circle) selected;
				Circle c = new Circle();
				DlgCircle dlg = new DlgCircle();
				//dlg.setCircle(circle);
				dlg.getTxtCenterX().setText(Integer.toString(circle.getCenter().getX()));
				dlg.getTxtCenterY().setText(Integer.toString(circle.getCenter().getY()));
				dlg.getTxtRadius().setText(Integer.toString(circle.getRadius()));
				dlg.getBtnInnerColor().setBackground(circle.getInnerColor());
				dlg.getBtnOuterColor().setBackground(circle.getColor());
				dlg.setModal(true);
				dlg.setVisible(true);
				if (dlg.isOk){
					c.getCenter().setX(dlg.getCircle().getCenter().getX());
					c.getCenter().setY(dlg.getCircle().getCenter().getY());
					try {
						c.setRadius(dlg.getCircle().getRadius());
					} catch (Exception e) {
						e.printStackTrace();
					}
					c.setColor(dlg.getCircle().getColor());
					c.setInnerColor(dlg.getCircle().getInnerColor());
					
					UpdateCircleCmd updateCircleCmd = new UpdateCircleCmd(circle, c);
					frame.getModelLogList().addElement("MODIFY " + circle + " into " + c);
					updateCircleCmd.execute();
					undoStack.push(updateCircleCmd);
				}
			}
			else if(selected instanceof HexagonAdapter){
				HexagonAdapter hexagonAdapt = (HexagonAdapter) selected;
				HexagonAdapter hA = new HexagonAdapter(-1,-1,-1);
				DlgHexagon dlg = new DlgHexagon();
				dlg.getTxtCenterPointX().setText(Integer.toString(hexagonAdapt.getHexagon().getX()));
				dlg.getTxtCenterPointY().setText(Integer.toString(hexagonAdapt.getHexagon().getY()));
				dlg.getTxtRadius().setText(Integer.toString(hexagonAdapt.getHexagon().getR()));
				dlg.getBtnInnerColor().setBackground(hexagonAdapt.getHexagon().getAreaColor());
				dlg.getBtnOuterColor().setBackground(hexagonAdapt.getHexagon().getBorderColor());
				dlg.setModal(true);
				dlg.setVisible(true);
				if (dlg.isOk){
					hA.getHexagon().setX(dlg.getHexagonAdapt().getHexagon().getX());
					hA.getHexagon().setY(dlg.getHexagonAdapt().getHexagon().getY());
					hA.getHexagon().setR(dlg.getHexagonAdapt().getHexagon().getR());
					hA.getHexagon().setBorderColor(dlg.getHexagonAdapt().getHexagon().getBorderColor());
					hA.getHexagon().setAreaColor(dlg.getHexagonAdapt().getHexagon().getAreaColor());
					
					UpdateHexagonCmd updateHexagonCmd = new UpdateHexagonCmd(hexagonAdapt, hA);
					frame.getModelLogList().addElement("MODIFY " + hexagonAdapt + " into " + hA);
					updateHexagonCmd.execute();
					undoStack.push(updateHexagonCmd);
				}
			}
			ind = 1;
		}
		frame.getView().repaint();
	}
	
	protected void undo(){
		frame.getTglbtnRedo().setSelected(false);
		Shape undoShape = null;
		Command undoCommand = null;
		if(ind == 1) {
			redoStack.clear();//ako se nesto nacrta ili izmeni da ne moze da vrati ono sto je ponisteno preko undo operacije
		}
		try{
			undoCommand = undoStack.pop();
			undoShape = undoCommand.getShape();
			//frame.getTglbtnRedo().setVisible(true);
			frame.getTglbtnRedo().setEnabled(true);
			frame.getModelLogList().addElement("UNDO " + undoCommand.toString());
		}catch(Exception ex){
			JOptionPane.showMessageDialog(frame, "The Undo function is not available.", "Error", JOptionPane.ERROR_MESSAGE);
				frame.getTglbtnUndo().setSelected(false);
				//frame.getTglbtnUndo().setVisible(false);
				frame.getTglbtnUndo().setEnabled(false);
		}
		
		if(undoCommand instanceof ToBackCmd) {
			undoCommand.unexecute();
			redoStack.push(undoCommand);
		}
		else if(undoCommand instanceof ToFrontCmd) {
			undoCommand.unexecute();
			redoStack.push(undoCommand);
		}
		else if(undoCommand instanceof BringToBackCmd) {
			undoCommand.unexecute();
			redoStack.push(undoCommand);
		}
		else if(undoCommand instanceof BringToFrontCmd) {
			undoCommand.unexecute();
			redoStack.push(undoCommand);
		}
		else if(undoCommand instanceof RemoveShapeCmd) {
			undoCommand.unexecute();
			redoStack.push(undoCommand);
		}
		else if(undoCommand instanceof SelectShapeCmd) {
			undoCommand.unexecute();
			redoStack.push(undoCommand);
		}
		else if(undoShape instanceof Point) {
			if(undoCommand instanceof AddPointCmd) {
				undoCommand.unexecute();
				redoStack.push(undoCommand);
			}
			/*else if(undoCommand instanceof RemovePointCmd) {
				undoCommand.unexecute();
				redoStack.push(undoCommand);
				
				model.setSelected(undoShape);
			}*/
			else if(undoCommand instanceof UpdatePointCmd) {
				undoCommand.unexecute();
				redoStack.push(undoCommand);
			}
		}
		else if(undoShape instanceof Line) {
			if(undoCommand instanceof AddLineCmd) {
				undoCommand.unexecute();
				redoStack.push(undoCommand);
			}
			/*else if(undoCommand instanceof RemoveLineCmd) {
				undoCommand.unexecute();
				redoStack.push(undoCommand);
				
				model.setSelected(undoShape);
			}*/
			else if(undoCommand instanceof UpdateLineCmd) {
				undoCommand.unexecute();
				redoStack.push(undoCommand);
			}
		}
		else if(undoShape instanceof Donut) {
			if(undoCommand instanceof AddDonutCmd) {
				undoCommand.unexecute();
				redoStack.push(undoCommand);
			}
			/*else if(undoCommand instanceof RemoveDonutCmd) {
				undoCommand.unexecute();
				redoStack.push(undoCommand);
				
				model.setSelected(undoShape);
			}*/
			else if(undoCommand instanceof UpdateDonutCmd) {
				undoCommand.unexecute();
				redoStack.push(undoCommand);
			}
		}
		else if(undoShape instanceof Circle) {
			if(undoCommand instanceof AddCircleCmd) {
				undoCommand.unexecute();
				redoStack.push(undoCommand);
			}
			/*else if(undoCommand instanceof RemoveCircleCmd) {
				undoCommand.unexecute();
				redoStack.push(undoCommand);
				
				model.setSelected(undoShape);
			}*/
			else if(undoCommand instanceof UpdateCircleCmd) {
				undoCommand.unexecute();
				redoStack.push(undoCommand);
			}
		}
		else if(undoShape instanceof Rectangle) {
			if(undoCommand instanceof AddRectangleCmd) {
				undoCommand.unexecute();
				redoStack.push(undoCommand);
			}
			/*else if(undoCommand instanceof RemoveRectangleCmd) {
				undoCommand.unexecute();
				redoStack.push(undoCommand);
				
				model.setSelected(undoShape);
			}*/
			else if(undoCommand instanceof UpdateRectangleCmd) {
				undoCommand.unexecute();
				redoStack.push(undoCommand);
			}
		}
		else if(undoShape instanceof HexagonAdapter) {
			if(undoCommand instanceof AddHexagonCmd) {
				undoCommand.unexecute();
				redoStack.push(undoCommand);
			}
			/*else if(undoCommand instanceof RemoveHexagonCmd) {
				undoCommand.unexecute();
				redoStack.push(undoCommand);
				
				model.setSelected(undoShape);
			}*/
			else if(undoCommand instanceof UpdateHexagonCmd) {
				undoCommand.unexecute();
				redoStack.push(undoCommand);
			}
		}
		
		/*frame.getTglbtnToBack().setVisible(true);
		frame.getTglbtnToFront().setVisible(true);
		frame.getTglbtnBringToBack().setVisible(true);
		frame.getTglbtnBringToFront().setVisible(true);*/
		frame.getTglbtnToBack().setEnabled(true);
		frame.getTglbtnToFront().setEnabled(true);
		frame.getTglbtnBringToBack().setEnabled(true);
		frame.getTglbtnBringToFront().setEnabled(true);
		
		frame.repaint();
		selectedShape.setModel(model);
		
		ind = 0;
	}
	
	protected void redo(){
		frame.getTglbtnUndo().setSelected(false);
		Shape redoShape = null;
		Command redoCommand = null;
		if(ind == 0) {
			try{
				redoCommand = redoStack.pop();
				redoShape = redoCommand.getShape();
				//frame.getTglbtnUndo().setVisible(true);
				frame.getTglbtnUndo().setEnabled(true);
				frame.getModelLogList().addElement("REDO " + redoCommand.toString());
			}catch(Exception ex){
				JOptionPane.showMessageDialog(frame, "The Redo function is not available.", "Error", JOptionPane.ERROR_MESSAGE);
				frame.getTglbtnRedo().setSelected(false);
				//frame.getTglbtnRedo().setVisible(false);
				frame.getTglbtnRedo().setEnabled(false);
			}
			
			if(redoCommand instanceof ToBackCmd) {
				redoCommand.execute();
				undoStack.push(redoCommand);
			}
			else if(redoCommand instanceof ToFrontCmd) {
				redoCommand.execute();
				undoStack.push(redoCommand);
			}
			else if(redoCommand instanceof BringToBackCmd) {
				redoCommand.execute();
				undoStack.push(redoCommand);
			}
			else if(redoCommand instanceof BringToFrontCmd) {
				redoCommand.execute();
				undoStack.push(redoCommand);
			}
			else if(redoCommand instanceof RemoveShapeCmd) {
				redoCommand.execute();
				undoStack.push(redoCommand);
			}
			else if(redoCommand instanceof SelectShapeCmd) {
				redoCommand.execute();
				undoStack.push(redoCommand);
			}
			else if(redoShape instanceof Point) {
				if(redoCommand instanceof AddPointCmd) {
					redoCommand.execute();
					undoStack.push(redoCommand);
				}
				/*else if(redoCommand instanceof RemovePointCmd) {
					redoCommand.execute();
					undoStack.push(redoCommand);
				}*/
				else if(redoCommand instanceof UpdatePointCmd) {
					redoCommand.execute();
					undoStack.push(redoCommand);
				}
			}
			else if(redoShape instanceof Line) {
				if(redoCommand instanceof AddLineCmd) {
					redoCommand.execute();
					undoStack.push(redoCommand);
				}
				/*else if(redoCommand instanceof RemoveLineCmd) {
					redoCommand.execute();
					undoStack.push(redoCommand);
				}*/
				else if(redoCommand instanceof UpdateLineCmd) {
					redoCommand.execute();
					undoStack.push(redoCommand);
				}
			}
			else if(redoShape instanceof Donut) {
				if(redoCommand instanceof AddDonutCmd) {
					redoCommand.execute();
					undoStack.push(redoCommand);
				}
				/*else if(redoCommand instanceof RemoveDonutCmd) {
					redoCommand.execute();
					undoStack.push(redoCommand);
				}*/
				else if(redoCommand instanceof UpdateDonutCmd) {
					redoCommand.execute();
					undoStack.push(redoCommand);
				}
			}
			else if(redoShape instanceof Circle) {
				if(redoCommand instanceof AddCircleCmd) {
					redoCommand.execute();
					undoStack.push(redoCommand);
				}
				/*else if(redoCommand instanceof RemoveCircleCmd) {
					redoCommand.execute();
					undoStack.push(redoCommand);
				}*/
				else if(redoCommand instanceof UpdateCircleCmd) {
					redoCommand.execute();
					undoStack.push(redoCommand);
				}
			}
			else if(redoShape instanceof Rectangle) {
				if(redoCommand instanceof AddRectangleCmd) {
					redoCommand.execute();
					undoStack.push(redoCommand);
				}
				/*else if(redoCommand instanceof RemoveRectangleCmd) {
					redoCommand.execute();
					undoStack.push(redoCommand);
				}*/
				else if(redoCommand instanceof UpdateRectangleCmd) {
					redoCommand.execute();
					undoStack.push(redoCommand);
				}
			}
			else if(redoShape instanceof HexagonAdapter) {
				if(redoCommand instanceof AddHexagonCmd) {
					redoCommand.execute();
					undoStack.push(redoCommand);
				}
				/*else if(redoCommand instanceof RemoveHexagonCmd) {
					redoCommand.execute();
					undoStack.push(redoCommand);
				}*/
				else if(redoCommand instanceof UpdateHexagonCmd) {
					redoCommand.execute();
					undoStack.push(redoCommand);
				}
			}
			
			/*frame.getTglbtnToBack().setVisible(true);
			frame.getTglbtnToFront().setVisible(true);
			frame.getTglbtnBringToBack().setVisible(true);
			frame.getTglbtnBringToFront().setVisible(true);*/
			frame.getTglbtnToBack().setEnabled(true);
			frame.getTglbtnToFront().setEnabled(true);
			frame.getTglbtnBringToBack().setEnabled(true);
			frame.getTglbtnBringToFront().setEnabled(true);
			
			frame.repaint();
			selectedShape.setModel(model);
		}
		else {//ako se nesto nacrta ili izmeni da ne moze da vrati ono sto je ponisteno preko undo operacije
			JOptionPane.showMessageDialog(frame, "The Redo function is not available.", "Error", JOptionPane.ERROR_MESSAGE);
			//frame.getTglbtnRedo().setVisible(false);
			frame.getTglbtnRedo().setEnabled(false);
			redoStack.clear();
		}
	}
	
	protected void toBack(){
		//Shape selected = model.getSelected();
		if(model.getSelectedShapes().size() == 1){
			Shape selected = model.getSelectedShapes().get(0);
			if(selected != null){
				if(model.getShapes().indexOf(selected) > 0 && model.getShapes().size() > 1){
					ToBackCmd toBackCmd = new ToBackCmd(selected, model);
					toBackCmd.execute();
					undoStack.push(toBackCmd);
					
					frame.getModelLogList().addElement("TOBACK " + selected.toString());
					
					ind = 1;
				}
				else if(model.getShapes().size() == 1){
					JOptionPane.showMessageDialog(frame, "Only one shape is drawn. To Back function is not available.", "Error", JOptionPane.ERROR_MESSAGE);
					//frame.getTglbtnToBack().setVisible(false);
					frame.getTglbtnToBack().setEnabled(false);
				}
				else if(model.getShapes().size() == 0){
					JOptionPane.showMessageDialog(frame, "No shape is drawn. To Back function is not available.", "Error", JOptionPane.ERROR_MESSAGE);
					//frame.getTglbtnToBack().setVisible(false);
					frame.getTglbtnToBack().setEnabled(false);
				}
				else{
					JOptionPane.showMessageDialog(frame, "That shape is already behind all shapes.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else{
			JOptionPane.showMessageDialog(frame, "You need to choose exactly one shape.", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
		
		frame.repaint();
	}
	
	protected void toFront(){
		//Shape selected = model.getSelected();
		if(model.getSelectedShapes().size() == 1){
			Shape selected = model.getSelectedShapes().get(0);
			if(selected != null){
				if(model.getShapes().indexOf(selected) < model.getShapes().size()-1 && model.getShapes().size() > 1){
					ToFrontCmd toFrontCmd = new ToFrontCmd(selected, model);
					toFrontCmd.execute();
					undoStack.push(toFrontCmd);
					
					frame.getModelLogList().addElement("TOFRONT " + selected.toString());
					
					ind = 1;
				}
				else if(model.getShapes().size() == 1){
					JOptionPane.showMessageDialog(frame, "Only one shape is drawn. To Front function is not available.", "Error", JOptionPane.ERROR_MESSAGE);
					//frame.getTglbtnToFront().setVisible(false);
					frame.getTglbtnToFront().setEnabled(false);
				}
				else if(model.getShapes().size() == 0){
					JOptionPane.showMessageDialog(frame, "No shape is drawn. To Front function is not available.", "Error", JOptionPane.ERROR_MESSAGE);
					//frame.getTglbtnToFront().setVisible(false);
					frame.getTglbtnToFront().setEnabled(false);
				}
				else{
					JOptionPane.showMessageDialog(frame, "That shape is already in front of all shapes.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else{
			JOptionPane.showMessageDialog(frame, "You need to choose exactly one shape.", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
		
		frame.repaint();
	}
	
	protected void bringToBack(){
		//Shape selected = model.getSelected();
		if(model.getSelectedShapes().size() == 1){
			Shape selected = model.getSelectedShapes().get(0);
			if(selected != null){
				if(model.getShapes().indexOf(selected) > 0 && model.getShapes().size() > 1){
					BringToBackCmd bringToBackCmd = new BringToBackCmd(selected, model);
					bringToBackCmd.execute();
					undoStack.push(bringToBackCmd);
					
					frame.getModelLogList().addElement("BRINGTOBACK " + selected.toString());
					
					ind = 1;
				}
				else if(model.getShapes().size() == 1){
					JOptionPane.showMessageDialog(frame, "Only one shape is drawn. Bring To Back function is not available.", "Error", JOptionPane.ERROR_MESSAGE);
					//frame.getTglbtnBringToBack().setVisible(false);
					frame.getTglbtnBringToBack().setEnabled(false);
				}
				else if(model.getShapes().size() == 0){
					JOptionPane.showMessageDialog(frame, "No shape is drawn. Bring To Back function is not available.", "Error", JOptionPane.ERROR_MESSAGE);
					//frame.getTglbtnBringToBack().setVisible(false);
					frame.getTglbtnBringToBack().setEnabled(false);
				}
				else{
					JOptionPane.showMessageDialog(frame, "That shape is already behind all shapes.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else{
			JOptionPane.showMessageDialog(frame, "You need to choose exactly one shape.", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
		
		frame.repaint();
	}
	
	protected void bringToFront(){
		//Shape selected = model.getSelected();
		if(model.getSelectedShapes().size() == 1){
			Shape selected = model.getSelectedShapes().get(0);
			if(selected != null){
				if(model.getShapes().indexOf(selected) < model.getShapes().size()-1 && model.getShapes().size() > 1){
					BringToFrontCmd bringToFrontCmd = new BringToFrontCmd(selected, model);
					bringToFrontCmd.execute();
					undoStack.push(bringToFrontCmd);
					
					frame.getModelLogList().addElement("BRINGTOFRONT " + selected.toString());
					
					ind = 1;
				}
				else if(model.getShapes().size() == 1){
					JOptionPane.showMessageDialog(frame, "Only one shape is drawn. Bring To Front function is not available.", "Error", JOptionPane.ERROR_MESSAGE);
					//frame.getTglbtnBringToFront().setVisible(false);
					frame.getTglbtnBringToFront().setEnabled(false);
				}
				else if(model.getShapes().size() == 0){
					JOptionPane.showMessageDialog(frame, "No shape is drawn. Bring To Front function is not available.", "Error", JOptionPane.ERROR_MESSAGE);
					//frame.getTglbtnBringToFront().setVisible(false);
					frame.getTglbtnBringToFront().setEnabled(false);
				}
				else{
					JOptionPane.showMessageDialog(frame, "That shape is already in front of all shapes.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else{
			JOptionPane.showMessageDialog(frame, "You need to choose exactly one shape.", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
		
		frame.repaint();
	}
	
	protected void saveLog(){
		File logFile = new LogFile(frame.getModelLogList());
		
		File fileManager = new FileManager(logFile);
		fileManager.save();
	}
	
	protected void saveDrawing(){
		File drawingFile = new DrawingFile(model.getShapes());
		
		File fileManager = new FileManager(drawingFile);
		fileManager.save();
	}
	
	protected void openLog(){
		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home")));
		jFileChooser.setDialogTitle("Open file");
		jFileChooser.setFileFilter(new FileNameExtensionFilter("Text File (*.txt)", "txt"));
		jFileChooser.setAcceptAllFileFilterUsed(true);
		int result = jFileChooser.showOpenDialog(null);
		if(result == JFileChooser.APPROVE_OPTION) {
			java.io.File selectedFile = jFileChooser.getSelectedFile();
			try {
				bufferedReader = new BufferedReader(new FileReader(selectedFile.getAbsolutePath()));
				
				model.getShapes().clear();
				model.getSelectedShapes().clear();
				frame.getModelLogList().clear();
				undoStack.clear();
				redoStack.clear();
				ind = 0;
				
				frame.getBtnNextLogLine().setEnabled(true);
				frame.repaint();
				JOptionPane.showMessageDialog(null, "File opened successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "File not opened successfully!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		//frame.repaint();
	}
	
	protected void openDrawing(){
		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home")));
		jFileChooser.setDialogTitle("Open file");
		jFileChooser.setFileFilter(new FileNameExtensionFilter("Bin File (*.bin)", "bin"));
		jFileChooser.setAcceptAllFileFilterUsed(true);
		int result = jFileChooser.showOpenDialog(null);
		if(result == JFileChooser.APPROVE_OPTION) {
			java.io.File selectedFile = jFileChooser.getSelectedFile();
			try {
				ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(selectedFile));
				
				model.getShapes().clear();
				model.getSelectedShapes().clear();
				frame.getModelLogList().clear();
				undoStack.clear();
				redoStack.clear();
				ind = 0;
				
				ArrayList<Shape> pom = (ArrayList<Shape>) objectInputStream.readObject();
				
				Iterator<Shape> it = pom.iterator();
				while(it.hasNext()){
					Shape shape = it.next();
					model.add(shape);
				}
				
				objectInputStream.close();
				
				frame.repaint();
				JOptionPane.showMessageDialog(null, "File opened successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "File not opened successfully!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		//frame.repaint();
	}
	
	protected void nextLogLine(){
		String nextLogLine;
		try {
			nextLogLine = bufferedReader.readLine();
			
			if(nextLogLine != null) {
				String[] elementLine = nextLogLine.split(" ");
				
				if(elementLine[0].equals("ADD")) {
					if(elementLine[1].equals("POINT:")) {
						Point point = new Point(Integer.parseInt(elementLine[3]), Integer.parseInt(elementLine[5]), Boolean.parseBoolean(elementLine[9]), getColor(elementLine[12]));
						AddPointCmd addPointCmd = new AddPointCmd(point, model);
						addPointCmd.execute();
						undoStack.push(addPointCmd);
					}
					else if(elementLine[1].equals("LINE:")) {
						Line line = new Line(new Point(Integer.parseInt(elementLine[3]), Integer.parseInt(elementLine[5])), new Point(Integer.parseInt(elementLine[9]), Integer.parseInt(elementLine[11])), Boolean.parseBoolean(elementLine[15]), getColor(elementLine[18]));
						AddLineCmd addLineCmd = new AddLineCmd(line, model);
						addLineCmd.execute();
						undoStack.push(addLineCmd);
					}
					else if(elementLine[1].equals("DONUT:")) {
						Donut donut = new Donut(new Point(Integer.parseInt(elementLine[3]), Integer.parseInt(elementLine[5])), Integer.parseInt(elementLine[9]), Integer.parseInt(elementLine[13]), Boolean.parseBoolean(elementLine[16]), getColor(elementLine[20]), getColor(elementLine[25]));
						AddDonutCmd addDonutCmd = new AddDonutCmd(donut, model);
						addDonutCmd.execute();
						undoStack.push(addDonutCmd);
					}
					else if(elementLine[1].equals("CIRCLE:")) {
						Circle circle = new Circle(new Point(Integer.parseInt(elementLine[3]), Integer.parseInt(elementLine[5])), Integer.parseInt(elementLine[9]), Boolean.parseBoolean(elementLine[12]), getColor(elementLine[16]), getColor(elementLine[21]));
						AddCircleCmd addCircleCmd = new AddCircleCmd(circle, model);
						addCircleCmd.execute();
						undoStack.push(addCircleCmd);
					}
					else if(elementLine[1].equals("RECTANGLE:")) {
						Rectangle rectangle = new Rectangle(new Point(Integer.parseInt(elementLine[5]), Integer.parseInt(elementLine[7])), Integer.parseInt(elementLine[11]), Integer.parseInt(elementLine[14]), Boolean.parseBoolean(elementLine[17]), getColor(elementLine[21]), getColor(elementLine[26]));
						AddRectangleCmd addRectangleCmd = new AddRectangleCmd(rectangle, model);
						addRectangleCmd.execute();
						undoStack.push(addRectangleCmd);
					}
					else if(elementLine[1].equals("HEXAGON:")) {
						HexagonAdapter hexagonAdapt = new HexagonAdapter(Integer.parseInt(elementLine[5]), Integer.parseInt(elementLine[10]), Integer.parseInt(elementLine[13]), Boolean.parseBoolean(elementLine[16]), getColor(elementLine[20]), getColor(elementLine[25]));
						AddHexagonCmd addHexagonCmd = new AddHexagonCmd(hexagonAdapt, model);
						addHexagonCmd.execute();
						undoStack.push(addHexagonCmd);
					}
					
					ind = 1;
					
					model.getSelectedShapes().clear();//svaki put kad se doda neki oblik brise se lista selektovanih oblika
					
					Iterator<Shape> itSelected = model.getShapes().iterator();//svaki put kad se doda neki oblik svi oblici koji su prethodno bili selektovani ce se deselektovati
					while(itSelected.hasNext()){
						Shape shape = itSelected.next();
						shape.setSelected(false);
					}
					
					frame.getModelLogList().addElement(nextLogLine);
				}
				else if(elementLine[0].equals("SELECT")) {
					Shape inputShape = null;
					if(elementLine[1].equals("POINT:")) {
						inputShape = new Point(Integer.parseInt(elementLine[3]), Integer.parseInt(elementLine[5]), Boolean.parseBoolean(elementLine[9]), getColor(elementLine[12]));
					}
					else if(elementLine[1].equals("LINE:")) {
						inputShape = new Line(new Point(Integer.parseInt(elementLine[3]), Integer.parseInt(elementLine[5])), new Point(Integer.parseInt(elementLine[9]), Integer.parseInt(elementLine[11])), Boolean.parseBoolean(elementLine[15]), getColor(elementLine[18]));
					}
					else if(elementLine[1].equals("DONUT:")) {
						inputShape = new Donut(new Point(Integer.parseInt(elementLine[3]), Integer.parseInt(elementLine[5])), Integer.parseInt(elementLine[9]), Integer.parseInt(elementLine[13]), Boolean.parseBoolean(elementLine[16]), getColor(elementLine[20]), getColor(elementLine[25]));
					}
					else if(elementLine[1].equals("CIRCLE:")) {
						inputShape = new Circle(new Point(Integer.parseInt(elementLine[3]), Integer.parseInt(elementLine[5])), Integer.parseInt(elementLine[9]), Boolean.parseBoolean(elementLine[12]), getColor(elementLine[16]), getColor(elementLine[21]));
					}
					else if(elementLine[1].equals("RECTANGLE:")) {
						inputShape = new Rectangle(new Point(Integer.parseInt(elementLine[5]), Integer.parseInt(elementLine[7])), Integer.parseInt(elementLine[11]), Integer.parseInt(elementLine[14]), Boolean.parseBoolean(elementLine[17]), getColor(elementLine[21]), getColor(elementLine[26]));
					}
					else if(elementLine[1].equals("HEXAGON:")) {
						inputShape = new HexagonAdapter(Integer.parseInt(elementLine[5]), Integer.parseInt(elementLine[10]), Integer.parseInt(elementLine[13]), Boolean.parseBoolean(elementLine[16]), getColor(elementLine[20]), getColor(elementLine[25]));
					}
					
					Iterator<Shape> it = model.getShapes().iterator();
					while(it.hasNext()){
						Shape shape = it.next();
						if(shape.equals(inputShape)){
							model.getSelectedShapes().add(shape);
						}
					}
					
					SelectShapeCmd selectShapeCmd = new SelectShapeCmd(model.getSelectedShapes());
					selectShapeCmd.execute();
					undoStack.push(selectShapeCmd);
					
					selectedShape.setModel(model);
					
					frame.getModelLogList().addElement(nextLogLine);
				}
				else if(elementLine[0].equals("DESELECT")) {
					
					SelectShapeCmd selectShapeCmd = new SelectShapeCmd(model.getSelectedShapes());
					selectShapeCmd.execute();
					undoStack.push(selectShapeCmd);
					model.getSelectedShapes().clear();
					
					selectedShape.setModel(model);
					
					frame.getModelLogList().addElement(nextLogLine);
				}
				else if(elementLine[0].equals("MODIFY")) {
					Shape selected = model.getSelectedShapes().get(0);
					
					if(elementLine[1].equals("POINT:")) {
						Point point = (Point) selected;
						Point p = new Point(Integer.parseInt(elementLine[17]), Integer.parseInt(elementLine[19]), Boolean.parseBoolean(elementLine[23]), getColor(elementLine[26]));
						UpdatePointCmd updatePointCmd = new UpdatePointCmd(point, p);
						updatePointCmd.execute();
						undoStack.push(updatePointCmd);
					}
					else if(elementLine[1].equals("LINE:")) {
						Line line = (Line) selected;
						Line l = new Line(new Point(Integer.parseInt(elementLine[23]), Integer.parseInt(elementLine[25])), new Point(Integer.parseInt(elementLine[29]), Integer.parseInt(elementLine[31])), Boolean.parseBoolean(elementLine[35]), getColor(elementLine[38]));
						UpdateLineCmd updateLineCmd = new UpdateLineCmd(line, l);
						updateLineCmd.execute();
						undoStack.push(updateLineCmd);
					}
					else if(elementLine[1].equals("DONUT:")) {
						Donut donut = (Donut) selected;
						Donut d = new Donut(new Point(Integer.parseInt(elementLine[30]), Integer.parseInt(elementLine[32])), Integer.parseInt(elementLine[36]), Integer.parseInt(elementLine[40]), Boolean.parseBoolean(elementLine[43]), getColor(elementLine[47]), getColor(elementLine[52]));
						UpdateDonutCmd updateDonutCmd = new UpdateDonutCmd(donut, d);
						updateDonutCmd.execute();
						undoStack.push(updateDonutCmd);
					}
					else if(elementLine[1].equals("CIRCLE:")) {
						Circle circle = (Circle) selected;
						Circle c = new Circle(new Point(Integer.parseInt(elementLine[26]), Integer.parseInt(elementLine[28])), Integer.parseInt(elementLine[32]), Boolean.parseBoolean(elementLine[35]), getColor(elementLine[39]), getColor(elementLine[44]));
						UpdateCircleCmd updateCircleCmd = new UpdateCircleCmd(circle, c);
						updateCircleCmd.execute();
						undoStack.push(updateCircleCmd);
					}
					else if(elementLine[1].equals("RECTANGLE:")) {
						Rectangle rect = (Rectangle) selected;
						Rectangle r = new Rectangle(new Point(Integer.parseInt(elementLine[33]), Integer.parseInt(elementLine[35])), Integer.parseInt(elementLine[39]), Integer.parseInt(elementLine[42]), Boolean.parseBoolean(elementLine[45]), getColor(elementLine[21]), getColor(elementLine[54]));
						UpdateRectangleCmd updateRectangleCmd = new UpdateRectangleCmd(rect, r);
						updateRectangleCmd.execute();
						undoStack.push(updateRectangleCmd);
					}
					else if(elementLine[1].equals("HEXAGON:")) {
						HexagonAdapter hexagonAdapt = (HexagonAdapter) selected;
						HexagonAdapter hA = new HexagonAdapter(Integer.parseInt(elementLine[32]), Integer.parseInt(elementLine[37]), Integer.parseInt(elementLine[40]), Boolean.parseBoolean(elementLine[43]), getColor(elementLine[47]), getColor(elementLine[52]));
						UpdateHexagonCmd updateHexagonCmd = new UpdateHexagonCmd(hexagonAdapt, hA);
						updateHexagonCmd.execute();
						undoStack.push(updateHexagonCmd);
					}
					
					ind = 1;
					
					frame.getModelLogList().addElement(nextLogLine);
				}
				else if(elementLine[0].equals("REMOVE")) {
					ArrayList<Shape> selectedShapes = model.getSelectedShapes();
					
					RemoveShapeCmd removeShapeCmd = new RemoveShapeCmd(selectedShapes, model);
					removeShapeCmd.execute();
					undoStack.push(removeShapeCmd);
						
					ind = 1;
					selectedShape.setModel(model);
					
					frame.getModelLogList().addElement(nextLogLine);
				}
				else if(elementLine[0].equals("UNDO")) {
					undo();
				}
				else if(elementLine[0].equals("REDO")) {
					redo();
				}
				else if(elementLine[0].equals("TOBACK")) {
					if(model.getSelectedShapes().size() == 1){
						Shape selected = model.getSelectedShapes().get(0);
						if(model.getShapes().indexOf(selected) > 0 && model.getShapes().size() > 1){
							ToBackCmd toBackCmd = new ToBackCmd(selected, model);
							toBackCmd.execute();
							undoStack.push(toBackCmd);
									
							frame.getModelLogList().addElement(nextLogLine);
									
							ind = 1;
						}
					}
				}
				else if(elementLine[0].equals("TOFRONT")) {
					if(model.getSelectedShapes().size() == 1){
						Shape selected = model.getSelectedShapes().get(0);
						if(model.getShapes().indexOf(selected) < model.getShapes().size()-1 && model.getShapes().size() > 1){
							ToFrontCmd toFrontCmd = new ToFrontCmd(selected, model);
							toFrontCmd.execute();
							undoStack.push(toFrontCmd);
								
							frame.getModelLogList().addElement(nextLogLine);
								
							ind = 1;
						}
					}
				}
				else if(elementLine[0].equals("BRINGTOBACK")) {
					if(model.getSelectedShapes().size() == 1){
						Shape selected = model.getSelectedShapes().get(0);
						if(model.getShapes().indexOf(selected) > 0 && model.getShapes().size() > 1){
							BringToBackCmd bringToBackCmd = new BringToBackCmd(selected, model);
							bringToBackCmd.execute();
							undoStack.push(bringToBackCmd);
								
							frame.getModelLogList().addElement(nextLogLine);
								
							ind = 1;
						}
					}
				}
				else if(elementLine[0].equals("BRINGTOFRONT")) {
					if(model.getSelectedShapes().size() == 1){
						Shape selected = model.getSelectedShapes().get(0);
						if(model.getShapes().indexOf(selected) < model.getShapes().size()-1 && model.getShapes().size() > 1){
							BringToFrontCmd bringToFrontCmd = new BringToFrontCmd(selected, model);
							bringToFrontCmd.execute();
							undoStack.push(bringToFrontCmd);
								
							frame.getModelLogList().addElement(nextLogLine);
								
							ind = 1;
						}
					}
				}
				
				frame.repaint();
			}
			else{
				bufferedReader.close();
				frame.getBtnNextLogLine().setEnabled(false);
				JOptionPane.showMessageDialog(null, "The file has been read!", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Line is not valid!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private Color getColor(String color) {
		String[] elementColor = color.split(",");
		
		Color returnColor = new Color(Integer.parseInt(elementColor[0]), Integer.parseInt(elementColor[1]), Integer.parseInt(elementColor[2]));
		
		return returnColor;
	}
}

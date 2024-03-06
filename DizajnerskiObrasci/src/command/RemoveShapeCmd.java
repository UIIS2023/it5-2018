package command;

import java.util.ArrayList;
import java.util.Iterator;

import geometry.Shape;
import mvc.DrawingModel;

public class RemoveShapeCmd implements Command {
	private ArrayList<Shape> selectedShapes;
	private ArrayList<Shape> pom = new ArrayList<Shape>();
	private DrawingModel model;
	private StringBuilder sb = new StringBuilder();
	private Integer[] indexSelectedElement;
	
	public RemoveShapeCmd(ArrayList<Shape> selectedShapes,DrawingModel model){
		this.selectedShapes = selectedShapes;
		this.model = model;
		this.indexSelectedElement = new Integer[selectedShapes.size()];
		
		Iterator<Shape> itPom = selectedShapes.iterator();
		
		while(itPom.hasNext()){
			Shape shape = itPom.next();
			
			pom.add(shape);
		}
	}
	
	@Override
	public void execute() {
		int i = 0;
		sb = new StringBuilder();
		Iterator<Shape> itPom = selectedShapes.iterator();
		
		while(itPom.hasNext()){//popunjavanje niza sa indeksima oblika unutar liste oblika koji treba da se obrisu tj. koji su selektovani
			Shape shape = itPom.next();
			
			indexSelectedElement[i] = model.getShapes().indexOf(shape);
			i++;
		}
		
		Iterator<Shape> itSelected = selectedShapes.iterator();
		
		while(itSelected.hasNext()){
			Shape shape = itSelected.next();
			
			model.remove(shape);
		}
		
		sb.append("REMOVE ");
		Iterator<Shape> it = pom.iterator();
		
		while(it.hasNext()){
			Shape shape = it.next();
			
			model.removeSelectedShape(shape);
			sb.append(shape.toString());
			sb.append(" ");
		}
	}

	@Override
	public void unexecute() {
		/*int i = 0;
		int index = -5;
		int trenutniIndex = minIndex(indexSelectedElement);
		
		//if(min(indexSelectedElement) == 1){
		
			Iterator<Shape> itSelected = pom.iterator();
			
			while(itSelected.hasNext()){
				Shape shape = itSelected.next();
				
				//model.add(shape);
				if(indexSelectedElement[i] >= 0 && indexSelectedElement[i] < model.getShapes().size()){
					model.getShapes().add(indexSelectedElement[i], shape);
				}
				else if(indexSelectedElement[i] == index-1) {
					model.getShapes().add(trenutniIndex, shape);
				}
				else {
					model.add(shape);
				}
				model.addSelectedShape(shape);
				
				index = indexSelectedElement[i];
				trenutniIndex = model.getShapes().indexOf(shape);
				i++; 
				/*if(indexSelectedElement[i] < 0 || indexSelectedElement[i] > model.getShapes().size()){
					model.add(shape);
				}
				else {
					model.getShapes().add(indexSelectedElement[i], shape);
				}
				model.addSelectedShape(shape);
				
				index = indexSelectedElement[i]; 
				i++;*/
			//}
		/*}
		else {
			for(int j = pom.size()-1; j>=0;j--){
				model.add(pom.get(j));
				model.addSelectedShape(pom.get(j));
			}
		}*/
		
		ArrayList<Shape> pomShape = new ArrayList<Shape>();
		Integer[] sortIndexSelectedElement = new Integer[indexSelectedElement.length];
		Integer[] positionIndexSelectedElement = new Integer[indexSelectedElement.length];
		int pomIndex;
		int index;
		
		for(int i = 0; i < indexSelectedElement.length; i++) {//kopiranje indeksa selektovanih oblika u novi niz
			sortIndexSelectedElement[i] = indexSelectedElement[i];
		}
		
		for(int i = 0; i < sortIndexSelectedElement.length-1; i++) {//sortiranje indeksa selektovanih oblika u novom nizu
			for(int j = 0; j < sortIndexSelectedElement.length-1-i; j++) {
				if(sortIndexSelectedElement[j] > sortIndexSelectedElement[j+1]) {
					pomIndex = sortIndexSelectedElement[j];
					sortIndexSelectedElement[j] = sortIndexSelectedElement[j+1];
					sortIndexSelectedElement[j+1] = pomIndex;
				}
			}
		}
		
		for(int i = 0; i < sortIndexSelectedElement.length; i++) {//pronalazak pozicije u pocetnom nizu indeksa selektovanih oblika i smestanje tih pozicija u novi niz
			for(int j = 0; j < indexSelectedElement.length; j++) {
				if(indexSelectedElement[j] == sortIndexSelectedElement[i]) {
					positionIndexSelectedElement[i] = j;
				}
			}
		}
		
		for(int i = 0; i < positionIndexSelectedElement.length; i++) {//unosenje oblika u novu listu oblika tako da oni budu poredjani u redosledu njihovog crtanja na panelu
			pomShape.add(pom.get(positionIndexSelectedElement[i]));
		}
		
		int k=0;
		for(int i = 0; i < sortIndexSelectedElement.length; i++) {//vracanje selektovanih oblika u niz oblika na panelu uzimajuci ih pri tome iz liste u kome su oni poredjani u redosledu njihovog crtanja na panelu
			model.getShapes().add(sortIndexSelectedElement[i], pomShape.get(k));
			k++;
		}
		
		Iterator<Shape> itSelected = pom.iterator();
		
		while(itSelected.hasNext()){//vracanje selektovanih oblika u listu selektovanih oblika
			Shape shape = itSelected.next();
			model.addSelectedShape(shape);
		}
	}

	@Override
	public Shape getShape(){
		return null;
	}
	
	public String toString() {
		return sb.toString();
	}
	
	/*private Integer minIndex(Integer[] indexSelectedElement){
		int min = indexSelectedElement[0];
		for(int i = 1; i < indexSelectedElement.length; i++) {
			if(indexSelectedElement[i] < min) {
				min = indexSelectedElement[i];
			}
		}
		
		return min;
	}
	
	private Integer min(Integer[] indexSelectedElement){//provera da li su indeksi poredjani u opadajucem redosledu
		int min = indexSelectedElement[0];
		int ind = 0;
		for(int i = 1; i < indexSelectedElement.length; i++) {
			if(indexSelectedElement[i] < min) {
				min = indexSelectedElement[i];
			}
			else {
				ind = 1;
			}
		}
		
		return ind;
	}*/
}

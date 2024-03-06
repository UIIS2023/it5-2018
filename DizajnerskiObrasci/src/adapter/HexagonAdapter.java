package adapter;

import java.awt.Color;
import java.awt.Graphics;

import geometry.SurfaceShape;
import hexagon.Hexagon;

public class HexagonAdapter extends SurfaceShape {
	private Hexagon hexagon;
	
	public HexagonAdapter() {
		
	}
	
	public HexagonAdapter(Hexagon hexagon) {
		this.hexagon = hexagon;
	}
	
	public HexagonAdapter(int x, int y, int r) {
		this.hexagon = new Hexagon(x, y, r);
	}
	
	public HexagonAdapter(int x, int y, int r, boolean selected) {
		this(x, y, r);
		this.hexagon.setSelected(selected);
	}
	
	public HexagonAdapter(int x, int y, int r, boolean selected, Color color) {
		this(x, y, r, selected);
		//setColor(color);
		this.hexagon.setBorderColor(color);
	}
	
	public HexagonAdapter(int x, int y, int r, boolean selected, Color color, Color innerColor) {
		this(x, y, r, selected, color);
		//setInnerColor(innerColor);
		this.hexagon.setAreaColor(innerColor);
	}

	@Override
	public void moveBy(int byX, int byY) {
		// TODO Auto-generated method stub
		this.hexagon.setX(this.hexagon.getX() + byX);
		this.hexagon.setY(this.hexagon.getY() + byY);
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if (o instanceof Hexagon) {
			return (int) (this.hexagon.getR() - ((Hexagon) o).getR());
		}
		return 0;
	}

	@Override
	public double area() {
		// TODO Auto-generated method stub
		
		return 6*((this.hexagon.getR()*this.hexagon.getR()*Math.sqrt(3))/4);
	}

	@Override
	public void fill(Graphics g) {
		// TODO Auto-generated method stub
		//g.setColor(getInnerColor());
		g.setColor(this.hexagon.getAreaColor());
		int [] xpoints = {this.hexagon.getX()-(this.hexagon.getR()/2),
						  this.hexagon.getX()+(this.hexagon.getR()/2),
						  this.hexagon.getX()+(this.hexagon.getR())-2,
						  this.hexagon.getX()+(this.hexagon.getR()/2),
						  this.hexagon.getX()-(this.hexagon.getR()/2),
						  this.hexagon.getX()-(this.hexagon.getR())+2};
		int [] ypoints = {this.hexagon.getY()+(int)((this.hexagon.getR()*Math.sqrt(3))/2)-1,
						  this.hexagon.getY()+(int)((this.hexagon.getR()*Math.sqrt(3))/2)-1,
						  this.hexagon.getY(),
						  this.hexagon.getY()-(int)((this.hexagon.getR()*Math.sqrt(3))/2)+1,
						  this.hexagon.getY()-(int)((this.hexagon.getR()*Math.sqrt(3))/2)+1,
						  this.hexagon.getY()};
		g.fillPolygon(xpoints, ypoints, 6);
	}
	

	@Override
	public boolean contains(int x, int y) {
		// TODO Auto-generated method stub
		return this.hexagon.doesContain(x, y);
		//return false;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		//g.setColor(getColor());
		g.setColor(this.hexagon.getBorderColor());
		this.hexagon.paint(g);
		this.fill(g);
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.hexagon.getX() - (this.hexagon.getR()/2) - 3, this.hexagon.getY() + (int)((this.hexagon.getR()*Math.sqrt(3))/2) - 3, 6, 6);
			g.drawRect(this.hexagon.getX() + (this.hexagon.getR()/2) - 3, this.hexagon.getY() + (int)((this.hexagon.getR()*Math.sqrt(3))/2) - 3, 6, 6);
			g.drawRect(this.hexagon.getX() + (this.hexagon.getR()) - 3, this.hexagon.getY() - 3, 6, 6);
			g.drawRect(this.hexagon.getX() + (this.hexagon.getR()/2) - 3, this.hexagon.getY() - (int)((this.hexagon.getR()*Math.sqrt(3))/2) - 3, 6, 6);
			g.drawRect(this.hexagon.getX() - (this.hexagon.getR()/2) - 3, this.hexagon.getY() - (int)((this.hexagon.getR()*Math.sqrt(3))/2) - 3, 6, 6);
			g.drawRect(this.hexagon.getX() - (this.hexagon.getR()) - 3, this.hexagon.getY() - 3, 6, 6);
		}
	}

	public boolean equals(Object obj) {
		if (obj instanceof HexagonAdapter) {
			HexagonAdapter pom = (HexagonAdapter) obj;
			/*if (this.hexagon.equals(pom.getHexagon())) {
				return true;
			} else {
				return false;
			}*/
			if (this.hexagon.getX() == pom.getHexagon().getX() && 
					this.hexagon.getY() == pom.getHexagon().getY() && this.hexagon.getR() == pom.getHexagon().getR()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public Hexagon getHexagon() {
		return hexagon;
	}
	public void setHexagon(Hexagon hexagon) {
		this.hexagon = hexagon;
	}
	/*
	public boolean isSelected() {
		return hexagon.isSelected();
	}
	
	public void setSelected(boolean selected) {
		this.hexagon.setSelected(selected);
	}
	
	public int getX() {
		return this.hexagon.getX();
	}
	
	public void setX(int x) {
		this.hexagon.setX(x);
	}
	
	public int getY() {
		return this.hexagon.getY();
	}
	
	public void setY(int y) {
		this.hexagon.setY(y);
	}
	
	public int getR() {
		return this.hexagon.getR();
	}
	
	public void setR(int r) {
		this.hexagon.setR(r);
	}
	
	public Color getBorderColor() {
		return this.hexagon.getBorderColor();
	}
	
	public void setBorderColor(Color borderColor) {
		this.hexagon.setBorderColor(borderColor);;
	}
	
	public Color getAreaColor() {
		return this.hexagon.getAreaColor();
	}
	
	public void setAreaColor(Color areaColor) {
		this.hexagon.setAreaColor(areaColor);;
	}*/
	
	public String toString() {
		return "HEXAGON: x center point= " + this.hexagon.getX() + " , y center point= " + this.hexagon.getY() + " , radius= " + this.hexagon.getR() + " , select= " + isSelected() + " , outer color=[ " + this.hexagon.getBorderColor().getRed() + "," + this.hexagon.getBorderColor().getGreen() + "," + this.hexagon.getBorderColor().getBlue() + " ]" + " , inner color=[ " + this.hexagon.getAreaColor().getRed() + "," + this.hexagon.getAreaColor().getGreen() + "," + this.hexagon.getAreaColor().getBlue() + " ]";
	}
	
	@Override
	public HexagonAdapter clone(){
		HexagonAdapter hexagonAdapt = new HexagonAdapter(-1,-1,-1);
		hexagonAdapt.getHexagon().setX(this.getHexagon().getX());
		hexagonAdapt.getHexagon().setY(this.getHexagon().getY());
		hexagonAdapt.getHexagon().setR(this.getHexagon().getR());
		hexagonAdapt.getHexagon().setBorderColor(this.getHexagon().getBorderColor());
		hexagonAdapt.getHexagon().setAreaColor(this.getHexagon().getAreaColor());
		
		return hexagonAdapt;
	}
}
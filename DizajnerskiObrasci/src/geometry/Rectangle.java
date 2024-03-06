package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends SurfaceShape {

	private Point upperLeftPoint = new Point();
	private int width;
	private int height;
	
	public Rectangle() {

	}
	
	public Rectangle(Point upperLeftPoint, int height, int width) {
		this.upperLeftPoint = upperLeftPoint;
		this.height = height;
		this.width = width;
	}
	
	public Rectangle(Point upperLeftPoint, int height, int width, boolean selected) {
		this(upperLeftPoint, height, width);
		setSelected(selected);
	}
	
	public Rectangle(Point upperLeftPoint, int height, int width, boolean selected, Color color) {
		this(upperLeftPoint, height, width, selected);
		setColor(color);
	}
	
	public Rectangle(Point upperLeftPoint, int height, int width, boolean selected, Color color, Color innerColor) {
		this(upperLeftPoint, height, width, selected, color);
		setInnerColor(innerColor);
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Rectangle) {
			return (int) (this.area() - ((Rectangle) o).area());
		}
		return 0;
	}

	@Override
	public void moveBy(int byX, int byY) {
		this.upperLeftPoint.moveBy(byX, byY);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawRect(this.upperLeftPoint.getX(), this.upperLeftPoint.getY(), this.width, this.height);
		this.fill(g);
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(getUpperLeftPoint().getX() - 3, getUpperLeftPoint().getY() - 3, 6, 6);
			g.drawRect(getUpperLeftPoint().getX() + getWidth() - 3, getUpperLeftPoint().getY() - 3, 6, 6);
			g.drawRect(getUpperLeftPoint().getX() - 3, getUpperLeftPoint().getY() + getHeight() - 3, 6, 6);
			g.drawRect(getUpperLeftPoint().getX() + getWidth() - 3, getUpperLeftPoint().getY() + getHeight() - 3, 6, 6);
		}
		
	}
	
	@Override
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		g.fillRect(this.upperLeftPoint.getX()+1, this.getUpperLeftPoint().getY()+1, width-1, height-1);
	}
	
	public double area() {
		return height * width;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Rectangle) {
			Rectangle prosledjeni = (Rectangle) obj;
			if (this.upperLeftPoint.equals(prosledjeni.getUpperLeftPoint()) && 
					this.width == prosledjeni.getWidth() && this.height == prosledjeni.getHeight()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean contains(int x, int y) {
		if (upperLeftPoint.getX() <= x &&
				upperLeftPoint.getY() <= y &&
				x <= upperLeftPoint.getX() + width &&
				y <= upperLeftPoint.getY() + height) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean contains(Point p) {
		if (upperLeftPoint.getX() <= p.getX() &&
				upperLeftPoint.getY() <= p.getY() &&
				p.getX() <= upperLeftPoint.getX() + width &&
				p.getY() <= upperLeftPoint.getY() + height) {
			return true;
		} else {
			return false;
		}
	}
	
	public Point getUpperLeftPoint() {
		return upperLeftPoint;
	}
	public void setUpperLeftPoint(Point upperLeftPoint) {
		this.upperLeftPoint = upperLeftPoint;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public String toString() {
		return "RECTANGLE: upper left point=" + "( " + upperLeftPoint.getX() + " , " + upperLeftPoint.getY() + " )" + " , height= " + height + " , width= " + width + " , select= " + isSelected() + " , outer color=[ " + getColor().getRed() + "," + getColor().getGreen() + "," + getColor().getBlue() + " ]" + " , inner color=[ " + getInnerColor().getRed() + "," + getInnerColor().getGreen() + "," + getInnerColor().getBlue() + " ]";
	}

	@Override
	public Rectangle clone(){
		Rectangle rectangle = new Rectangle();
		rectangle.getUpperLeftPoint().setX(this.getUpperLeftPoint().getX());
		rectangle.getUpperLeftPoint().setY(this.getUpperLeftPoint().getY());
		rectangle.setWidth(this.getWidth());
		rectangle.setHeight(this.getHeight());
		rectangle.setColor(this.getColor());
		rectangle.setInnerColor(this.getInnerColor());
		
		return rectangle;
	}
}

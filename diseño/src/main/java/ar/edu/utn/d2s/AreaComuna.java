package ar.edu.utn.d2s;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;

import org.uqbar.geodds.Point;
import org.uqbar.geodds.Polygon;

@Embeddable
public class AreaComuna extends Polygon {
	
	@ElementCollection
	@CollectionTable(name="puntoArea", 
	joinColumns=@JoinColumn(name="idCgp"))
	private List<Coordenada> area = new LinkedList<Coordenada>();
	
	public AreaComuna() {
		super(); 
	}
	
	public AreaComuna(List<Point> points) {
		super(points);
		setArea(points);
	}	

	public List<Coordenada> getArea() {
		return area;
	}
	
	@SuppressWarnings("unchecked")
	public void setArea(List<Point> points) {
		this.area = (List<Coordenada>)(List<?>)points;
	}

	public boolean add(Point point) {
		if(super.add(point)) {
			this.area.add((Coordenada) point);
			return true;
		}
		return false;
	}
}

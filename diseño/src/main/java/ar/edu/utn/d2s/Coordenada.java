package ar.edu.utn.d2s;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.uqbar.geodds.Point;

@Embeddable
public class Coordenada extends Point {
	@Column(name="longitud")
	private double x;
	@Column(name="latitud")
	private double y;
	
	public Coordenada(double aX, double aY) {
		super(aX, aY);
		this.setX(aX);
		this.setY(aY);		
	}
	
	public Coordenada() {
		super(0,0);
	}
	
	@Override
	public boolean equals(Object anObject){
		Coordenada other = (Coordenada) anObject;
		return (this.latitude() == other.latitude() && this.longitude() == other.longitude() );
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
}

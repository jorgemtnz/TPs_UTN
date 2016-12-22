package ar.edu.utn.d2s.Mockup_SE_Banco;

import java.util.LinkedList;
import java.util.List;

public class BancoDTO {

	private String banco;
	private double x;
	private double y;
	private String sucursal;
	private String gerente;
	private List<String> servicios = new LinkedList<String>();
	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++constructores++++++++++++++++++++++++++++++++
	public BancoDTO(String banco, double x, double y, String sucursal, String gerente, List<String> servicios) {
		super();
		this.banco = banco;
		this.x = x;
		this.y = y;
		this.sucursal = sucursal;
		this.gerente = gerente;
		this.servicios = servicios;
	}
	
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++accesor++++++++++++++++++++++++++++++++++++++
	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco = banco;
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
	public String getSucursal() {
		return sucursal;
	}
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	public String getGerente() {
		return gerente;
	}
	public void setGerente(String gerente) {
		this.gerente = gerente;
	}
	public List<String> getServicios() {
		return servicios;
	}
	public void setServicios(List<String> servicios) {
		this.servicios = servicios;
	}
		
}

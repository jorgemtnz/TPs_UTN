package ar.edu.utn.d2s.Mockup_SE_Cgp;

public class RangoServicioDTO {

	private int numeroDia;
	private int minutosDesde;
	private int minutosHasta;
	private int horarioDesde;
	private int horarioHasta;

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++constructores++++++++++++++++++++++++++++++++
	public RangoServicioDTO(int numeroDia, int horarioDesde, int minutosDesde, int horarioHasta,  int minutosHasta) {
		super();
		this.numeroDia = numeroDia;
		this.minutosDesde = minutosDesde;
		this.minutosHasta = minutosHasta;
		this.horarioDesde = horarioDesde;
		this.horarioHasta = horarioHasta;
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// +++++++++++++++++accesor++++++++++++++++++++++++++++++++++++++
	public int getNumeroDia() {
		return numeroDia;
	}
	public void setNumeroDia(int numeroDia) {
		this.numeroDia = numeroDia;
	}
	public int getMinutosDesde() {
		return minutosDesde;
	}
	public void setMinutosDesde(int minutosDesde) {
		this.minutosDesde = minutosDesde;
	}
	public int getMinutosHasta() {
		return minutosHasta;
	}
	public void setMinutosHasta(int minutosHasta) {
		this.minutosHasta = minutosHasta;
	}
	public int getHorarioDesde() {
		return horarioDesde;
	}
	public void setHorarioDesde(int horarioDesde) {
		this.horarioDesde = horarioDesde;
	}
	public int getHorarioHasta() {
		return horarioHasta;
	}
	public void setHorarioHasta(int horarioHasta) {
		this.horarioHasta = horarioHasta;
	}	
}

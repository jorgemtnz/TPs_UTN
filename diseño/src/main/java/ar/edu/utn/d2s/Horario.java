package ar.edu.utn.d2s;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "HORARIOS")
public class Horario {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private int id_horario;

	private int numeroDia = new Integer(0);
	private int minutosDesde = new Integer(0);
	private int minutosHasta= new Integer(0);
	private int horarioDesde= new Integer(0);
	private int horarioHasta= new Integer(0);
	
	public Horario() {
		super();
	}

	public Horario(int numeroDia, int horarioDesde, int minutosDesde, int horarioHasta,  int minutosHasta){
	
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
		
		public String getDiaTexto()
		{
			String dia = null;
			switch (this.numeroDia) {
				case 1:
					dia = "Lunes";
				case 2:
					dia = "Martes";
					break;
				case 3:
					dia = "Miercoles";
					break;
				case 4:
					dia = "Jueves";
					break;
				case 5:
					dia = "Viernes";
					break;
				default:
					break;
			}
			return dia;
		}

}

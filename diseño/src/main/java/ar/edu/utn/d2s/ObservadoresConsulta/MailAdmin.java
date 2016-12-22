package ar.edu.utn.d2s.ObservadoresConsulta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.mail.EmailException;

import ar.edu.utn.d2s.InformacionBusqueda;
import ar.edu.utn.d2s.ManejadorDeError;
import ar.edu.utn.d2s.MyMailSender;
import ar.edu.utn.d2s.ProcesoIndividual;

@Entity
@Table(name = "ADMIN_MAILS")
public class MailAdmin extends AbstractObservadorConsulta implements ObservadorConsulta, ManejadorDeError {


	 @Column (name = "TIEMPO")
	private int mailAdminSegundosDemora;
	 @Transient
	private MyMailSender mockMail = new MyMailSender();
	 @Transient
	private boolean mailEnviado = false;

	public MailAdmin(int mailAdminSegundosDemora) {
		super();
		this.mailAdminSegundosDemora = mailAdminSegundosDemora;
	}
	public MailAdmin( ) {
		super();

	}	

	public void actualizar(InformacionBusqueda i) {
		// TODO: obtener de this.subject ultimoTiempoBusquedaMilisegundos, si
		// este valor es mayor a
		// this.mailAdminSegundosDemora evaluar enviar mail a admin

		// TODO: Adaptar sacar datos de parametros recibidos por actualizar

		if ((i.getTiempoBusqueda()) > this.getMailAdminSegundosDemora() * 1000) {
			try {
				this.enviarEmail("TiempoExcedido", "Se ha excedido el tiempo de la busqueda");
			} catch (EmailException e) {
				e.printStackTrace();
			}

		}
	}

	public void enviarEmail(String subject, String body) throws EmailException {
		mailEnviado = this.getMockMail().sendMail("admin@frba.com", "system@frb.com", subject, body);
	}

	public int getMailAdminSegundosDemora() {
		return mailAdminSegundosDemora;
	}

	public void setMailAdminSegundosDemora(int mailAdminSegundosDemora) {
		this.mailAdminSegundosDemora = mailAdminSegundosDemora;
	}

	public MyMailSender getMockMail() {
		return mockMail;
	}

	public void setMockMail(MyMailSender mockMail) {
		this.mockMail = mockMail;
	}

	public boolean getMailEnviado() {
		return mailEnviado;
	}

	public void setMailEnviado(boolean mailEnviado) {
		this.mailEnviado = mailEnviado;
	}

	@Override
	public void manejarError(ProcesoIndividual procesoIndividual) {
		try {
			this.enviarEmail("Error de ejecucion", "Descripcion");
		} catch (Exception e) {

		}
	}

	@Override
	public String toString() {
		return "Enviar mail a Administrador";
	}


	

}

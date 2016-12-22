package ar.edu.utn.d2s;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import ar.edu.utn.d2s.Mockup_SE_Cgp.Mockup_SE_Cgp;
import org.junit.Before; //se ejecuta antes de cada test

public class TestConvertidorCGP {

	private ConvertidorCGP convertidor = new ConvertidorCGP();
	private Mockup_SE_Cgp se_Cgp = new Mockup_SE_Cgp();

	List<Cgp> listCgps = new LinkedList<Cgp>();
	List<Servicio> serCgp = new LinkedList<Servicio>();
	Cgp cgp1 = new Cgp("comuna");

	Servicio serv = new Servicio();
	List<Servicio> listServ = new LinkedList<Servicio>();

	@Before
	public void setUpBefore() throws Exception {
		// convierto los DTOS a CGP

		convertidor.agregaCentroDTOaCgps(se_Cgp.obtenerCgps("funcionaaaaa"));
		listCgps = convertidor.getCgpsTemp().stream().filter(cgp -> cgp.getNumero() == 1).collect(Collectors.toList());
		//para pruebas sobre un solo Cgp
		listServ.add(serv);
		cgp1.setServicios(listServ);
		//debe ser la comuna1
		cgp1 = listCgps.get(0);

	}
	
	@Test
	public void testHorariosatencionmayorACero(){
		assertTrue(cgp1.getServicios().get(0).getRango().getHorariosAtencion().size()>0);
	}

	@Test
	public void testHorarioAtencionNombreRentas(){
		assertTrue(cgp1.getServicios().get(0).getNombre().contains("Rentas"));
	}
	@Test
	public void testHorarioAtencionDia(){
		assertTrue(cgp1.getServicios().get(0).getRango().getHorariosAtencion().get(0).getNumeroDia()==1);
	}
	@Test
	public void testHorarioAtencionHoraDesde(){
		assertTrue(cgp1.getServicios().get(0).getRango().getHorariosAtencion().get(0).getHorarioDesde()==9);
	}
	

	@Test
	public void testestaComuna1() {
		assertTrue(convertidor.getCgpsTemp().stream().anyMatch(unCgp -> unCgp.getNumero() == 1));
	}

	@Test
	public void testListaNoVacia() {
		assertTrue(listCgps.isEmpty() == false);
	}

	@Test
	public void testcgpNoNull() {
		assertNotNull(cgp1);
	}

	@Test
	public void testCgpNumero1() {
		assertTrue(cgp1.getNumero() == 1);
	}

	@Test
	public void testserviciosNoNull() {
		assertNotNull(cgp1.getServicios());
	}

	@Test
	public void testservicioVacia() {		
		assertTrue(cgp1.getServicios().isEmpty() == false);
	}


		


}

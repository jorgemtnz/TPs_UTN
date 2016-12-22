
$(document).ready(function () {
    $("#btnBuscar").click(function () {
             var nombre = $("#user").val();
             var fechaInicio = $("#fechaInicio").val();
             var fechaFin = $("#fechaFin").val();
             
              	
        	if((new Date(fechaInicio).getTime()) >new Date(fechaFin).getTime() ){
        	alert("Error: Ha seleccionado una \"Fecha Inicio\" que es mayor a la \"Fecha Fin\"  ");
        	}else {
    	var url  = "/historialBusqueda?nombreBuscado=" + nombre + "&fechaDesde=" + fechaInicio + "&fechaHasta=" + fechaFin;
   	window.location = url;
    
        	}
    })
    
})
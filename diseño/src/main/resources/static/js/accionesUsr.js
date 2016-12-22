var x
	function agregarAccion(){
		x = document.getElementById("opcionesAcciones").value;
		document.getElementById("nuevaAccion").value = x;
	}

function deleteAccion(id) {
			
			$.ajax({
			    url: '/accionesBusqueda/' + id,
			    type: 'DELETE',
			    success: function(result) {			    	
			        window.location = "/accionesBusqueda"
			    }
			});
		}


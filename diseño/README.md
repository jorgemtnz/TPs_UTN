# Se sube el proyecto al repositorio, pero fue desarrollado en un repositorio privado, donde participaron todos los integrantes en su desarrollo.
Materia: Diseño de Sistemas
Trabajo Práctico Anual
# se eliminan los apellidos de otros integrantes para respetar su identidad.
Integrantes:
            Jorge Martínez Rodríguez
            Walter 
            Cecilia 
            Francisco 
            Javier 

    
   Al levantar la aplicacion, se cargan unos usuarios para las pruebas
   Usuario                 Password
   TerminalEnzo             123
   TerminalJorge            123
   test                     123
   
   Se han inicializado algunos datos de prueba:
   	   Usuarios
   	   Pdis (Incluye Mocks)
	   Algunos de los usuarios tienen ya habilitada la accion de busqueda e incluye 
	   algunas busquedas que han realizado.
   
   Las cuales fueron cargadas a fin de probar funcionalidades.
   
   Obs 1: Se tomo que según lo conversado en la ultima clase el Guardar Busqueda es la accion/observer
   que determina que se puedan guardar las busquedas en el historial. Acorde a entregas anteriores, 
   si se elimina la accion guardar busquedas se eliminan los datos asociados a las busuqedas realizadas.
   
   Obs 2: Se recomienda probar los tests uno por uno.
   
   direccion: localhost:4567/
   
   para cosultar los json de la API-REST se debe usar las direcciones
   	localhost:4567/json/accionesBusqueda
	localhost:4567/json/busqueda
	localhost:4567/json/historialBusqueda
    localhost:4567/json/infoBusqueda/{:id}
    localhost:4567/json/pdi/{:id}
   
         
            
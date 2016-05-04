
laGuardiana:= Obrera crearConRol: Guardiana new.
laPecoreadora:= Obrera crearConRol: Pecoreadora new.
otraPecoreadora:= Obrera crearConRol: Pecoreadora new.
lareina:=Reina new.
elzanga:=Zangano new.

laGuardiana puedesPicar.   true.
elzanga puedesPicar.   false.
lareina puedesPicar. false .
laPecoreadora puedesPicar.  true.


granNectar:= Nectar new.
granColmena:=Colmena crearConNombre:'granColmena' yNectar: granNectar.
laPiquera:= Piquera crearContamanio:20 enLaColmena: granColmena. 
granColmena piquera: laPiquera.


granColmena agregarGuardiana:laGuardiana.
granColmena agregarAbeja:laPecoreadora.
granColmena agregarAbeja:otraPecoreadora.
granColmena agregarAbeja:lareina.
granColmena agregarAbeja:elzanga.

deguerreraGuardiana:= Obrera crearConRol: Guardiana new.
deguerreraGuardianaOtra:=Obrera crearConRol: Guardiana new.
deguerreraGeneral:= Obrera crearConRol:Guardiana new.
deguerreraCapitan:= Obrera crearConRol: Guardiana new.
deguerreraSargento:= Obrera crearConRol:Guardiana new.
deguerreraCabo:= Obrera crearConRol:Guardiana new.
deguerreraPecoreadora:= Obrera crearConRol: Pecoreadora new.
deguerreraPecoreadoraOtra:= Obrera crearConRol: Pecoreadora new.
deguerreralareina:=Reina new.
deguerreraelzanga:=Zangano new.

guerreraNectar:= Nectar new.
guerreraColmena:= Colmena crearConNombre:'Colmena guerrera' yNectar:guerreraNectar .
guerreraPiquera:= Piquera crearContamanio:10 enLaColmena: guerreraColmena.  
guerreraColmena piquera:guerreraPiquera.

guerreraColmena agregarGuardiana:deguerreraGuardiana.
guerreraColmena agregarGuardiana:deguerreraGuardianaOtra.
guerreraColmena agregarGuardiana:deguerreraGeneral.
guerreraColmena agregarGuardiana:deguerreraCapitan.
guerreraColmena agregarGuardiana:deguerreraSargento.
guerreraColmena agregarGuardiana:deguerreraCabo.
guerreraColmena agregarAbeja:deguerreraPecoreadora.
guerreraColmena agregarAbeja:deguerreraPecoreadoraOtra.
guerreraColmena agregarAbeja:deguerreraelzanga.
guerreraColmena agregarAbeja:deguerreralareina.


margarita:=Flor crearFlorConunaDistancia:2 NectarCantidad:2.
rosa:=Flor crearFlorConunaDistancia:5 NectarCantidad:10. 
tulipan:= Flor crearFlorConunaDistancia:1 NectarCantidad:15.
mariposa:= Flor crearFlorConunaDistancia:1 NectarCantidad:15.
romero:= Flor crearFlorConunaDistancia:1 NectarCantidad:6.

granColmena agregarFlor: margarita.
granColmena agregarFlor:rosa.
granColmena agregarFlor:tulipan.
guerreraColmena agregarFlor:mariposa.
guerreraColmena agregarFlor:romero.

superApiario:= Apiario new. 
superApiario agregarColmena:guerreraColmena.
superApiario agregarColmena:granColmena.

superApiario quienEsLaColmenaMaFuerte.   su nombre es: 'Colmena guerrera'.


laPecoreadora hazTuTarea:granColmena.


granColmena haganSuTarea.  
guerreraColmena haganSuTarea.


granColmena comanNectar:1. a Nectar.
granColmena comanNectar:400.  'nectar insuficiente'.


nuevoRol:= Guardiana new. 
laPecoreadora rolObrera: nuevoRol. 


laPiquera quieroEntrar:deguerreraPecoreadora.

"______________________fin primera parte____________________________"

" las otras instancias ya las tengo creadas antes."

pillaje:= Pillaje new.

debilNectar:= Nectar new.
debilColmena:=Colmena crearConNombre:'debilColmena' yNectar:debilNectar .
laPiquera:= Piquera crearContamanio:20 enLaColmena: debilColmena. 
debilColmena piquera: laPiquera.
debilreina:=Reina new.
debilzangano:=Zangano new.
debilGuardiana:= Obrera crearConRol:Guardiana new.
debilColmena agregarAbeja: debilzangano.
debilColmena agregarAbeja: debilreina.
debilColmena agregarGuardiana:debilGuardiana.
girasol:= Flor crearFlorConunaDistancia:1 NectarCantidad:15.
debilColmena agregarFlor:girasol.


laAlmacenadora:= Obrera crearConRol:Almacenadora new.
deguerreraAlmacenadora:=Obrera crearConRol:Almacenadora new.
debilAlmacenadora:= Obrera crearConRol:Almacenadora new.
granColmena agregarAbeja:laAlmacenadora.
guerreraColmena agregarAbeja:deguerreraAlmacenadora.
 debilColmena agregarAbeja:debilAlmacenadora.


granColmena haganSuTarea.  


nuevoRol:= Almacenadora new.
otraPecoreadora rolObrera: nuevoRol.

pillaje colmenaPilladora: guerreraColmena.

granColmena esAtacadaPor: pillaje.
  
granColmena miel.  50.


debilColmena esAtacadaPor: pillaje.

debilColmena  miel.   36. 
guerreraColmena miel.  64. 

elHormiguero:= Hormiguero new.


granColmena esAtacadaPor: elHormiguero.
granColmena miel. 50.

debilColmena esAtacadaPor: elHormiguero.

debilColmena  miel.  34. 
elHormiguero  deposito. 2.


laVarroaDestructora:= VarroaDestructora new.

granColmena esAtacadaPor: laVarroaDestructora.

debilColmena esAtacadaPor: laVarroaDestructora.

debilzangano capacidadDeventilar. 4.0.

debilAlmacenadora rolObrera capacidadCrearMiel.  4.25.

enemigos:=Set new.

enemigos add: laVarroaDestructora.
enemigos  add: pillaje.
enemigos  add: elHormiguero.

debilColmena esAtacadaPorLos:enemigos.


debilColmena  miel.  3.














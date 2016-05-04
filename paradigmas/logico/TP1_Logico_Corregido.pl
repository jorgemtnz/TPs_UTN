/* Base de Conocimiento */

compatible(smartphone(nokia, lumia710), windowsPhone).
compatible(smartphone(nokia, n8), nokiaBelle).

compatible(smartphone(motorola, xT615), android(2.37,[market, flash, multitask])).
compatible(smartphone(motorola, backflip), android(1.6, [market])).
compatible(smartphone(motorola, razr), android(4.12, [market, flash, multitask])).
compatible(smartphone(motorola, vivaz), symbian(s60)).
compatible(smartphone(motorola, vivaz), palmOSGarnet).

compatible(smartphone(samsung, galaxyS4), android(4.22, [market, flash, multitask])).
compatible(smartphone(samsung, galaxySAdvance), android(4.22, [market, flash, multitask])).
compatible(smartphone(samsung, razr), android(4.12, [market, flash, multitask])).

compatible(smartphone(lg, razr), android(4.12, [market, flash, multitask])).
compatible(smartphone(lg, galaxySAdvance), android(4.12, [market, flash, multitask])).

compatible(smartphone(sonyEricsson, vivaz), symbian(s60)).
compatible(smartphone(sonyEricsson, x8), android(2.1, [market, multitask])).

compatible(smartphone(mac, iphone4), iOS([market, multitask, itunes])).
compatible(smartphone(mac, iphone4), nokiaBelle).
compatible(smartphone(mac, iphone5), nokiaBelle).

aplicacion(mapaLoco).
aplicacion(aceleradorDeDescargas).
aplicacion(haskellMobile).

precio(galaxyS4, 3100).
precio(galaxySAdvance,2500).
precio(lumia710,1500).
precio(n8,1600).
precio(xT615,2000).
precio(backflip,900).
precio(vivaz,1100).
precio(x8,1200).
precio(iphone4,3000).
precio(razr, 2500).

tiene(mariano, smartphone(mac, iphone4)). 
tiene(rodrigo, smartphone(samsung, galaxyS4)).

conocidos(hernan,mariano). 
conocidos(juan, hernan). 
conocidos(martina,pepe).

/* Punto 1 */

esInnovadora(Marca) :- compatible(smartphone(Marca, _), UnSistOp), compatible(smartphone(Marca, _), OtroSistOp), UnSistOp \= OtroSistOp.
esInsistente(Marca) :- compatible(smartphone(Marca, UnModelo), UnSistOp), compatible(smartphone(Marca, OtroModelo), UnSistOp), UnModelo \= OtroModelo.
esMarketinera(Marca) :- esInnovadora(Marca), esInsistente(Marca).

/* Punto 2 cumpleRequisito(mapaLoco, nokiaBelle). */

cumpleRequisito(mapaLoco, symbian(s60)).
cumpleRequisito(mapaLoco, iOS(_)).
cumpleRequisito(mapaLoco, nokiaBelle).
cumpleRequisito(aceleradorDeDescargas, windowsPhone).
cumpleRequisito(aceleradorDeDescargas, iOS(Caracteristicas)) :- member(market,Caracteristicas).
cumpleRequisito(aceleradorDeDescargas, android(_,Caracteristicas)) :- member(market,Caracteristicas).
cumpleRequisito(haskellMobile, iOS(Caracteristicas)) :- member(multitask,Caracteristicas).
cumpleRequisito(haskellMobile, android(_,Caracteristicas)) :- member(multitask,Caracteristicas).

/* Punto 3 */

correAplicacion(Smartphone,Aplicacion) :- compatible(Smartphone,SistOp), cumpleRequisito(Aplicacion,SistOp).

/* Punto 4 */

funcionaEn(Aplicacion,Smartphones) :- aplicacion(Aplicacion),findall(Smartphone,correAplicacion(Smartphone,Aplicacion),Smartphones).


/* Punto 5 */

esCopada(Marca) :- esMarketinera(Marca), forall(compatible(smartphone(Marca, Modelo), _), correnDosAplicaciones(Marca,Modelo)).

correnDosAplicaciones(Marca,Modelo) :- correAplicacion(smartphone(Marca, Modelo),Aplicacion),correAplicacion(smartphone(Marca, Modelo),OtraAplicacion),Aplicacion \= OtraAplicacion.

/* Punto 6 */

elDesterrado(SistOp) :-  sistOperativo(SistOp),not((aplicacion(Aplicacion), (cumpleRequisito(Aplicacion,SistOp)))).
sistOperativo(SistOp) :- compatible(_,SistOp).

/* Punto 7 */

mayorCantidadModelos(Marca) :- compatible(smartphone(Marca, _),_), cantidadModelos(Marca,Cantidad), forall(compatible(smartphone(OtraMarca, _),_),(cantidadModelos(OtraMarca,OtraCantidad), Cantidad >= OtraCantidad)).
cantidadModelos(Marca,Cantidad) :- findall(Modelo, compatible(smartphone(Marca, Modelo),_), Modelos), length(Modelos,Cantidad).

mayorCantidadModelos2(UnaMarca) :- findall(Marca, compatible(smartphone(Marca, _), _), Marcas), mayorCantidadModelosR(Marcas,UnaMarca).
mayorCantidadModelosR([Max],Max).
mayorCantidadModelosR([CabMarca,OtraMarca|Cola],Max) :- cantidadModelos(CabMarca,Cantidad), cantidadModelos(OtraMarca,OtraCantidad), Cantidad > OtraCantidad, mayorCantidadModelosR([CabMarca|Cola],Max). 
mayorCantidadModelosR([CabMarca,OtraMarca|Cola],Max) :- cantidadModelos(CabMarca,Cantidad), cantidadModelos(OtraMarca,OtraCantidad), Cantidad =< OtraCantidad, mayorCantidadModelosR([OtraMarca|Cola],Max). 

/* Punto 8 */

nombresSistemasOperativos(NombresSinRepetidos) :- findall(Nombre, (sistOperativo(SistOp),nombre(SistOp,Nombre)),NombresConRepetidos), sinRepetidos(NombresConRepetidos,NombresSinRepetidos).

nombre(android(_,_),android).
nombre(windowsPhone,windowsPhone).
nombre(nokiaBelle,nokiaBelle).
nombre(symbian(_),symbian).
nombre(iOS(_),iOs).

sinRepetidos([],[]).
sinRepetidos([Cab|Cola],NombresSinRepetidos) :- member(Cab,Cola),sinRepetidos(Cola,NombresSinRepetidos).
sinRepetidos([Cab|Cola],[Cab|Cola2]) :- not(member(Cab,Cola)),sinRepetidos(Cola,Cola2).

/* Punto 9 */

correEn(Nombre,MarcasSinRepetidos) :- sistOperativo(SistOp),nombre(SistOp,Nombre),findall(Marca, compatible(smartphone(Marca,_),SistOp), MarcasConRepetidos), sinRepetidos(MarcasConRepetidos,MarcasSinRepetidos).


/* Punto 10 */

compatibleConTodosExceptoUno(Marca) :- compatible(smartphone(Marca,_),SistOp), not(esAndroid(SistOp)), forall((compatible(smartphone(Marca,_),OtroSistOp),OtroSistOp \= SistOp), esAndroid(OtroSistOp)).

esAndroid(android(_,_)).


/* Punto 11 */

puedeConectarse(Persona) :- tiene(Persona, smartphone(_, _)).
puedeConectarse(Persona) :- sonConocidos(Persona,Alguien), tiene(Alguien, smartphone(_, _)).
puedeConectarse(Persona) :- sonConocidos(Persona,Alguien), puedeConectarse(Alguien).

sonConocidos(Persona,Alguien) :- conocidos(Persona,Alguien).
sonConocidos(Persona,Alguien) :- conocidos(Alguien,Persona).

/* Punto 12 */

smartphonesPosibles(Plata,SmartphonesPosibles) :- findall(Smartphone, precio(Smartphone, _), ListaSmartphones), sonPosibles(ListaSmartphones, Plata, SmartphonesPosibles).
sonPosibles([] , _ , []).
sonPosibles([Smartphone|Smartphones], Plata, [Smartphone|PosiblesSmartphones]):- precio(Smartphone, Precio), Plata > Precio, PlataRestante is Plata - Precio, sonPosibles(Smartphones, PlataRestante, PosiblesSmartphones).   
sonPosibles([_|Smartphones], Plata, PosiblesSmartphones) :- sonPosibles(Smartphones, Plata, PosiblesSmartphones).

/* Punto 13: En los predicados correAplicacion y elDesterrado uso polimorfismo ya que utilizo el predicado cumpleRequisito que es polimórfico para
todos los sistemas operativos. Fue útil utilizar el predicado cumpleRequisito con polimorfismo, ya que derivamos en él el tratamiento de los distintos celulares
de acuerdo a sus sistemas operativos. */

%compatible(smartphone(marca, modelo), sistemaOperativo)
compatible(smartphone(nokia, lumia710), windowsPhone).
compatible(smartphone(nokia, n8), nokiaBelle).

%conocimiento agregado.
compatible(smartphone(nokia, n10), nokiaBelle).

compatible(smartphone(motorola, xT615), android(2.37,[market, flash, multitask])).
compatible(smartphone(motorola, backflip), android(1.6, [market])).
compatible(smartphone(motorola, razr), android(4.12, [market, flash, multitask])).

compatible(smartphone(samsung, galaxyS4), android(4.22, [market, flash, multitask])).
compatible(smartphone(samsung, galaxySAdvance), android(4.22, [market, flash, multitask])).
compatible(smartphone(sonyEricsson, vivaz), symbian(s60)).
compatible(smartphone(sonyEricsson, x8), android(2.1, [market, multitask])).
compatible(smartphone(mac, iphone4), iOS([market, multitask, itunes])).

%conocimiento agregado.
compatible(smartphone(mac, iphone4), nokiaBelle ).


%precio(modelo, precio).
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

tiene(mariano,smartphone(mac,iphone4)).
tiene(rodrigo,smartphone(samsung,galaxyS4)).

conocidos(hernan,mariano).
conocidos(juan,hernan).
conocidos(martina,pepe).

%1a).
innovadora(Empresa):-compatible(smartphone(Empresa, _), SistemaOperativo),compatible(smartphone(Empresa, _), OtroSistemaOperativo), SistemaOperativo\= OtroSistemaOperativo.
%1b).
insistente(Empresa):-compatible(smartphone(Empresa, Celular), SistemaOperativo),compatible(smartphone(Empresa, OtroCelular),SistemaOperativo),Celular\=OtroCelular.
%1c).
marketinera(Empresa):-innovadora(Empresa), insistente(Empresa).
%2).
cumpleRequisito(Aplicacion, SistemaOperativo):-compatible(_,SistemaOperativo ), cumpleCondicion(Aplicacion, SistemaOperativo).

cumpleCondicion(mapaLoco, nokiaBelle).
cumpleCondicion(mapaLoco, symbian(_)).
cumpleCondicion(mapaLoco, iOS(_)).

cumpleCondicion(aceleradorDeDescargas,windowsPhone ).
cumpleCondicion(aceleradorDeDescargas,iOS(Lista) ):- busca(market, Lista).
cumpleCondicion(aceleradorDeDescargas,android(_,Lista) ):-busca(market, Lista).

cumpleCondicion(haskellMobile, iOS(Lista)):-busca(multitask, Lista).
cumpleCondicion(haskellMobile,android(_,Lista) ):-busca(multitask, Lista).

busca(Palabra,Lista):- member(Palabra, Lista).

%3).
correAplicacion(Smartphone, Aplicacion):-compatible(Smartphone,SistemaOperativo ), cumpleRequisito(Aplicacion, SistemaOperativo).

%4).
funcionaEn(Aplicacion,Smartphones):-cumpleRequisito(Aplicacion, _), findall(Smartphone, correAplicacion(Smartphone, Aplicacion), Smartphones).

%5).
esCopada(Marca):-marketinera(Marca), forall(compatible(smartphone(Marca,Modelo),_) , correAlMenosDosAplicaciones(smartphone(Marca,Modelo))).

correAlMenosDosAplicaciones(Smartphone) :- correAplicacion(Smartphone,Aplicacion),correAplicacion(Smartphone,OtraAplicacion),Aplicacion \= OtraAplicacion.

%6).
elDesterrado(SistemaOperativo):-compatible(Smartphone,SistemaOperativo),not((correAplicacion(Smartphone, _))).

%7)
cantModelos(Marca,Cant):-compatible(smartphone(Marca,_),_), findall(Modelo,compatible(smartphone(Marca,Modelo),_),Modelos), length(Modelos,Cant).

%sin recursividad.
%mayorCantidadDeModelos(Marca):-cantModelos(Marca,MaxCant), forall(cantModelos(_,OtraCant), MaxCant >= OtraCant).


%con recursividad.
mayorCantidadDeModelos(MarcaSuperior):- findall(Marca, compatible(smartphone(Marca,_),_), Marcas), hallarMaxdeMarcas(Marcas, MarcaSuperior).

hallarMaxdeMarcas([MarcaMax],MarcaMax).
hallarMaxdeMarcas([Marca,OtraMarca|Cola],MarcaMax):-cantModelos(Marca,Cant), cantModelos(OtraMarca,OCant), Cant > OCant,hallarMaxdeMarcas([Marca|Cola],MarcaMax).
hallarMaxdeMarcas([Marca,OtraMarca|Cola],MarcaMax):-cantModelos(Marca,Cant), cantModelos(OtraMarca,OCant), OCant >= Cant,hallarMaxdeMarcas([OtraMarca|Cola],MarcaMax).

%8).
nombresSistemasOperativos(NombresSistemas):-findall(Nombre,(compatible(_,Sistema),nombre(Sistema,Nombre)), Nombres), sinRepetidos(Nombres, NombresSistemas).

sinRepetidos([X],[X]).
sinRepetidos([Cab|Cola],Lista):-member(Cab,Cola),sinRepetidos(Cola,Lista).
sinRepetidos([Cab|Cola],[Cab|Cola2]):- not(member(Cab,Cola)), sinRepetidos(Cola,Cola2).

nombre(windowsPhone,windowsPhone).
nombre(nokiaBelle,nokiaBelle).
nombre(android(_,_),android).
nombre(symbian(_),symbian).
nombre(iOS(_),iOS).
%nombreSistema(sistemaTigre(_,_),sistemaTigre).

%9).
correEn(NombreSistemaOperativo, ListaMarcas):-compatible(smartphone(_,_),SistemaOperativo),nombre(SistemaOperativo,NombreSistemaOperativo), marcasPorNombre(NombreSistemaOperativo,ListaMarcas).

marcasPorNombre(NombreSistemaOperativo,ListaMarcas):-findall(Marca,busca(Marca,NombreSistemaOperativo),Marcas), sinRepetidos(Marcas,ListaMarcas).

busca(Marca,NombreSistemaOperativo):-compatible(smartphone(Marca,_),SistemaOperativo),nombre(SistemaOperativo,NombreSistemaOperativo).

%10).
compatibleConTodosExceptoUno(Marca):-compatible(smartphone(Marca,_),SistemaOperativo),not(nombre(SistemaOperativo,android)),forall((compatible(smartphone(Marca,_),OtroSistemaOperativo) ,SistemaOperativo\=OtroSistemaOperativo),nombre(OtroSistemaOperativo,android)).


%11). 
puedenConectarse(Persona):-tiene(Persona,_).
puedenConectarse(Persona):-sonConocidos(Persona, UnaPersona), tiene(UnaPersona,_).
puedenConectarse(Persona):-sonConocidos(Persona, OtraPersona), puedenConectarse(OtraPersona).

sonConocidos(Persona1,Persona2):-conocidos(Persona1,Persona2).
sonConocidos(Persona1,Persona2):-conocidos(Persona2,Persona1).

%12).
smartphonesPosibles(CantDinero, AlternativasPosiblesSmartphone):- findall(AlternativaSmartphone,precio(AlternativaSmartphone,_),AlternativasSmartphones), alternativasPosibles(AlternativasSmartphones,CantDinero,AlternativasPosiblesSmartphone).

alternativasPosibles([],_,[]).
alternativasPosibles([Cab |Cola] ,Plata, [Cab|Cola2]):- precio(Cab, Costo), Plata > Costo, PlataRestante is Plata - Costo, alternativasPosibles(Cola,PlataRestante,Cola2 ).
alternativasPosibles([_|Cola] ,Plata, Cola2):- alternativasPosibles(Cola ,Plata, Cola2).

%13).
/* Use polimorfismo en el punto2 en el predicado cumpleCondicion y en el punto 8 en el predicado nombre. Es útil usar el polimorfirmo porque no tengo que tener en cuenta, específicamente con que 
estoy trabajando y me permite abstraerme mas,es decir en el predicado  cumpleCondicion no me interesa saber específicamente de que Aplicación o que functor de Sistema Operativo se trata, uso el 
predicado indistintamente para cada uno. El predicado se encarga  de hacer un tratamiento distinto para cada uno de ellos cuando los relaciona .
En el predicado nombre obtengo un beneficio similar, no importa que functor de Sistema Operativo sea , el predicado se encarga de relacionar el correcto con su respectivo nombre y yo me 
despreocupo, entonces puedo usar el mismo predicado para todos.
*/




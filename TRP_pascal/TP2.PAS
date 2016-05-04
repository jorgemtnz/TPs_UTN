CONST
 MAXPREMIOS  = 50;
 MAXCLIENTES = 1500;
type
TSLClientes = ^TNodoSub;
   TNodoSub = Record
     NroTarjeta:word;
     sig:TSLClientes
     end;

ListaPremios=Record
             cod:byte;
             PSLClientes:TSLClientes;
             end;

TListaPremios = ^TNodo;
        TNodo = Record
          info:ListaPremios;
          sig :TListaPremios;
          end;

TSLPremios = ^TNodoSubP;
   TNodoSubP = Record
     cod:byte;
     sig:TSLPremios
     end;

ListaClientes= Record
             NroTarjeta:word;
             PSLPremios:TSLPremios;
             end;

TListaClientes = ^TNodoLP;
        TNodoLP = Record
          info:ListaClientes;
          sig :TListaClientes;
          end;

str20 = string[20];
 trPremio = RECORD
   Codigo      : byte;
   Descripcion : str20;
   Stock,
   Puntos      : word
 end;
 trCliente = RECORD
   NumeroTarjeta : word;
   DNI           : longint;
   Nombre        : str20;
   Puntos        : word
 end;
 tvPremios  = ARRAY [1..MAXPREMIOS] OF trPremio;
 tvClientes = ARRAY [1..MAXCLIENTES] OF trCliente;

procedure MostrarPremios(cod:byte;vecP:tvPremios);
begin
writeln(' Codigo:',cod,' ',' Descripcion:' );
writeln(vecP[cod].Descripcion);
end;

procedure MostrarClientes(NTarje:word;vecC:tvClientes);
begin
writeln('NroTarjeta:',NTarje,' DNI:',vecC[NTarje].DNI,' Nombre:',vecC[NTarje].Nombre);
end;

procedure MostrarSublistaC(lista:TSLClientes;vecC:tvClientes) ;
begin
while (lista <> NIL)
   do  begin
 MostrarClientes(lista^.NroTarjeta,vecC);
 lista:=lista^.sig;
 end;
end;

procedure MostrarLPremios(lista:TListaPremios;vecP:tvPremios;vecC:tvClientes);
begin
   while (lista <> NIL)
    do  begin
        MostrarPremios(lista^.info.cod,vecP);
        MostrarSublistaC(lista^.info.PSLClientes,vecC);
        lista:=lista^.sig;
     end;
end;
procedure MostrarSublistaP(lista:TSLPremios;vecP:tvPremios);
begin
while (lista<>NIL)
 do begin
 MostrarPremios(lista^.cod,vecP);
 lista:=lista^.sig;
 end;
end;
procedure MostrarLClientes(lista:TListaClientes;vecP:tvPremios;vecC:tvClientes);
begin
while (lista<>NIL)
  do begin
  MostrarClientes(lista^.info.NroTarjeta,vecC);
  MostrarSublistaP(lista^.info.PSLPremios,vecP);
  lista:=lista^.sig;
  end;
end;
begin
end.

{amigo que muestre cada campo de lista en una misma linea y cada campo de
sublista en la linea siguiente}
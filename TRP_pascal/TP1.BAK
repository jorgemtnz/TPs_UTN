Program TrabajoPractico;

USES
 dos,
 { newdelay,}
 crt;

CONST
 MAXPREMIOS  = 50;
 MAXCLIENTES = 1500;
 DESCR       = 20;
 PRECIOPUNTO = 7;
 AMARILLO    = Yellow;
 AZUL        = LightBlue;
 CELESTE     = LightCyan;
 GRIS        = DarkGray;
 ROJO        = LightRed;
 VERDE       = LightGreen;

{DEFINICION DE TIPOS}

TYPE
 str20 = string[DESCR];
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
   Puntos        : word {Podria ser otro tipo, mas grande que word}
 end;
 trRetiro = RECORD
   Fecha         : longint;
   NumeroTarjeta : word;
   Codigo        : byte
 end;
 taPremios  = FILE OF trPremio;
 taClientes = FILE OF trCliente;
 taRetiros  = FILE OF trRetiro;
 tvPremios  = ARRAY [1..MAXPREMIOS] OF trPremio;
 tvClientes = ARRAY [1..MAXCLIENTES] OF trCliente;

{BLOQUE DE PROCEDIMIENTOS Y FUNCIONES}

Procedure EscribirColor(Linea:string;Color:byte);
Begin
  TextColor(Color);
  writeln(Linea);
  NormVideo
end;

Function Digitos (num:longint) : byte;
var
  aux : string;
Begin
  str(num,aux);
  Digitos := length(aux)
end;

Function RepeatChar(caracter:char;cantidad:byte) : string;
var
  i:byte;
  aux:string;
Begin
  aux:='';
  for i:=1 to cantidad do
    aux := aux + caracter;
  RepeatChar := aux
end;

Function FechaLarga(d,m:byte;a:word) : longint;
Begin
  FechaLarga := a * 10000 + m * 100 + d
end;

Function Menu : byte;
Var
  opcion : byte;
Begin
  repeat
    clrscr;
    EscribirColor('****Menu****',VERDE);
    writeln;
    writeln('1 - Asignar Tarjeta a Cliente');
    writeln('2 - Agregar Compra a Cliente');
    writeln('3 - Retirar Premio');
    writeln('4 - Salir del Programa');
    readln(opcion)
  until opcion <= 4;
  Menu := opcion
end;

Function PosicionClienteSegunDNI(var vC:tvClientes;CantC:word;DNI:longint) : word;
Var
  Encontrado : boolean;
  pos        : word;
Begin
  Encontrado := FALSE;
  pos := 1;
  while (pos <= CantC) AND NOT(Encontrado) do begin
    if vC[pos].DNI = DNI then
      Encontrado := TRUE
    else
      pos := pos +1
  end;
	PosicionClienteSegunDNI := pos
end;

Function PosicionPremioSegunCodigo(var vP:tvPremios;CantP:word;Codigo:longint) : word;
Var
  Encontrado : boolean;
  pos        : word;
Begin
  Encontrado := FALSE;
  pos := 1;
  while (pos <= CantP) AND NOT(Encontrado) do begin
    if vP[pos].Codigo = Codigo then
      Encontrado := TRUE
    else
      pos := pos +1
  end;
	PosicionPremioSegunCodigo := pos
end;

Procedure TituloMostrarPremios;
Begin
  write('CODIGO');
  write(' ');
  write('DESCRIPCION',RepeatChar(' ',DESCR-11));
  write(' ');
  write('STOCK');
  write(' ');
  writeln('PUNTOS')
end;

Procedure MostrarPremios(var vP:tvPremios;CantP:byte);
Var
  i : byte;
Begin
  TituloMostrarPremios;
  for i := 1 to CantP do begin
    write(RepeatChar(' ',4 - Digitos(vP[i].Codigo)));
    write(vP[i].Codigo);
    write('  ');
    write(' ');
    write(vP[i].Descripcion);
    write(RepeatChar(' ',DESCR - length(vP[i].Descripcion)));
    write(' ');
    write(RepeatChar(' ',4 - Digitos(vP[i].Stock)));
    write(vP[i].Stock);
    write(' ');
    write(' ');
    write(RepeatChar(' ',5 - Digitos(vP[i].Puntos)));
    writeln(vP[i].Puntos);
    if (((i mod 20) = 0) AND (i <> MAXPREMIOS)) then begin
      EscribirColor('Presione ENTER para Continuar',GRIS);
      readkey
    end
  end;
  readkey
end;

Procedure GuardarPremios(var aP:taPremios;var vP:tvPremios;var CantP:byte);
Var
  i : byte;
Begin
  for i := 1 to CantP do
    write(aP,vP[i]);
end;

Procedure GuardarClientes(var aC:taClientes;var vC:tvClientes;var CantC:word);
Var
  i : byte;
Begin
  for i:=1 to CantC do
    write(aC,vC[i]);
end;

Procedure OrdenarPremiosXCodigo(var vP:tvPremios; CantP: byte);
{Ordena el vP por Codigo Ascendente}
Var
  i,j: byte;
  aux : trPremio;
Begin
  For i := 2 to CantP do begin
    aux := vP[i];
    j := i-1;
    While ((j >= 1) AND (vP[j].Codigo > aux.Codigo)) do begin
      vP[j+1] := vP[j];
      j := j - 1;
    end;
    vP[j+1] := aux;
  end;
End;

Procedure OrdenarPremiosXPuntos(var vP:tvPremios; CantP: byte);
{Ordena el vP por Puntos Ascendente}
Var
 i,j: byte;
 aux : trPremio;
Begin
  For i := 2 to CantP do begin
    aux := vP[i];
    j := i - 1;
    While ((j >= 1) AND (vP[j].Puntos > aux.Puntos)) do begin
	vP[j+1] := vP[j];
	j := j - 1;
    end;
  vP[j+1] := aux;
  end
end;

Procedure AbrirArchivos(var archPremios:taPremios;var archClientes:taClientes);
Begin
  {Si no existe el archivo de Premios, lo crea y sino lo abre}
  ASSIGN(archPremios,'Premios.dat');
  {$I-}
  RESET(archPremios);
  if (IOResult <> 0) then
    REWRITE(archPremios);
  {$I+}
  {Si no existe el archivo de Clientes, lo crea y sino lo abre.}
  ASSIGN(archClientes,'Clientes.dat');
  {$I-}
  RESET(archClientes);
  if (IOResult <> 0) then
   rewrite (archClientes);
  {$I+}
end;

Procedure CargarPremios(var aP:taPremios;var vP:tvPremios;var CantP:byte);
Var
  rPremio : trPremio;
Begin;
  CantP := 0;
  while not EOF(aP) do begin
    read(aP,rPremio);
    CantP := CantP + 1;
    vP[CantP] := rPremio
  end
end;

Procedure CargarClientes(var aC:taClientes;var vC:tvClientes;var CantC:word);
Var
  rCliente : trCliente;
Begin;
  CantC := 0;
  while not EOF(aC) do begin
    read(aC,rCliente);
    CantC := CantC + 1;
    vC[CantC] := rCliente
  end
end;

Procedure Cerrar(var aC:taClientes;var aP:taPremios);
Begin
  CLOSE(aC);
  CLOSE(aP)
end;

Procedure Inicializar(var vC:tvClientes;var CantC:word;var vP:tvPremios;var CantP:byte);
Var
  aClientes : taClientes;
  aPremios  : taPremios;
Begin
  AbrirArchivos(aPremios,aClientes);
  CargarClientes(aClientes,vC,CantC);
  CargarPremios(aPremios,vP,CantP);
  Cerrar(aClientes,aPremios)
end;

Procedure Asignar(var vC:tvClientes;var CantClientes:word);
Var
  rCliente   : trCliente;
  posCliente : word;
  linea      : string;
Begin
  clrscr;
  EscribirColor('****Asignar Tarjeta a Cliente****',AMARILLO);
  writeln;
  write('Ingrese el Numero de DNI: ');
  readln(rCliente.DNI);
  posCliente := PosicionClienteSegunDNI(vC,CantClientes,rCliente.DNI);
  if posCliente > CantClientes then begin
    rCliente.NumeroTarjeta := posCliente;
    write('Ingrese el Nombre: ');
    readln(rCliente.Nombre);
    rCliente.Puntos := 0;
    vC[posCliente] := rCliente;
    CantClientes := posCliente;
    str(CantClientes,linea);
    linea := 'Se ha Asignado la Tajerta Numero: ' + linea;
    EscribirColor(linea,CELESTE)
  end
  else begin
	str(rCliente.DNI,linea);
	linea := 'El Cliente con DNI: ' + linea + ' Ya Tiene Una Tarjeta Asignada';
	EscribirColor(linea,ROJO)
  end;
  readkey
end;

Procedure AgregarCompra(var vC:tvClientes;CantC:word);
Var
  NumeroTarjeta,
  MontoCompra    : word;
  linea          : string;
Begin
  clrscr;
  EscribirColor('****Agregar Compra a Cliente****',AZUL);
  writeln;
  write('Ingrese el Numero de Tarjeta: ');
  readln(NumeroTarjeta);
  if NumeroTarjeta <= CantC then begin
    write('Ingrese el Monto de la Compra (Max 65535): ');
    readln(MontoCompra);
    if (MontoCompra <= 65535) then begin
      vC[NumeroTarjeta].Puntos:=vC[NumeroTarjeta].Puntos+(MontoCompra div PRECIOPUNTO);
      str((montocompra div PRECIOPUNTO),linea);
      linea := 'Se le han Agregado ' + linea + ' Puntos';
      EscribirColor(linea,CELESTE)
    end
    else begin
	EscribirColor('La compra maxima es de $65535',ROJO);
        write('hola' )
        end
  end
  else begin
    str(NumeroTarjeta,linea);
    linea := 'El Numero de Tarjeta ' + linea + ' NO fue Asignada a Ningun Cliente';
    EscribirColor(linea,ROJO)
  end;
  readkey
end;

Procedure AgregarPremioOtorgado(nroCliente:longint ; codPremio:byte);
var
  aRetiros : taRetiros;
  rRetiro  : trRetiro;
  fecha    : longint;
  d,m,a,ds : word;
Begin
  ASSIGN(aRetiros,'Retiros.dat');
  {$I-}
  RESET(aRetiros);
  if (IOResult <> 0) then
    REWRITE(aRetiros);
    {$I+}
    GetDate(d,m,a,ds);
    fecha := FechaLarga(d,m,a);
    rRetiro.Fecha := fecha;
    rRetiro.NumeroTarjeta := nroCliente;
    rRetiro.Codigo := codPremio;
    SEEK(aRetiros,FILESIZE(aRetiros));
    write(aRetiros,rRetiro);
    CLOSE(aRetiros)
end;

Procedure RetirarPremio(var vC:tvClientes;CantC:word;var vP:tvPremios;CantP:byte);
var
  codPremio,
  posPremio  : byte;
  nroCliente : word;
Begin
  clrscr;
  EscribirColor('****Retirar Premio****',CELESTE);
  writeln;
  OrdenarPremiosXPuntos(vP,CantP);
  MostrarPremios(vP,CantP);
  write('Ingrese el Codigo de Producto que desea Retirar: ');
  readln(codPremio);
  if codPremio <= MAXPREMIOS then begin
    posPremio := PosicionPremioSegunCodigo(vP,CantP,codPremio);
    if (vP[posPremio].Stock <> 0 ) then  begin
      write('Ingrese su Numero de Tarjeta: ');
      readln(nroCliente);
      if nroCliente <= cantC then
	if (vC[nroCliente].Puntos >= vP[posPremio].Puntos) then  begin
	  vC[nroCliente].Puntos:=vC[nroCliente].Puntos-vP[posPremio].Puntos;
	  DEC(vP[posPremio].Stock);
	  AgregarPremioOtorgado(nroCliente,codPremio);
	  EscribirColor('Premio Retirado Exitosamente',CELESTE)
	end
	else  begin
	  EscribirColor('No tiene Puntos Suficientes para Retirar el Premio',ROJO);
	end
	else begin
	  EscribirColor('Numero de Tarjeta NO Asignado',ROJO);
	end
      end
      else begin
        EscribirColor('No hay Stock del Premio Solicitado',ROJO);
      end;
    end
    else begin
      EscribirColor('Codigo de Premio Incorrecto',ROJO);
    end;
  readkey
end;

Procedure Actualizar(var vC:tvClientes;CantC:word;var vP:tvPremios;CantP:byte);
Var
  aClientes : taClientes;
  aPremios  : taPremios;
Begin
  clrscr;
  abrirArchivos(aPremios,aClientes);
  OrdenarPremiosXCodigo(vP,CantP);
  GuardarPremios(aPremios,vP,CantP);
  GuardarClientes(aClientes,vC,CantC);
  Cerrar(aClientes,aPremios)
end;

{BLOQUE PRINCIPAL}

VAR
  vClientes    : tvClientes;
  CantClientes : word;
  vPremios     : tvPremios;
  CantPremios  : byte;
  opcion       : byte;
Begin
  Inicializar(vClientes,CantClientes,vPremios,CantPremios);
  opcion := Menu;
  while opcion < 4 do begin
    CASE opcion OF
	1:
	Asignar(vClientes,CantClientes);
	2:
	AgregarCompra(vClientes,CantClientes);
	3:
	RetirarPremio(vClientes,CantClientes,vPremios,CantPremios);
    end;
    opcion := Menu;
  end;
  Actualizar(vClientes,CantClientes,vPremios,CantPremios);
End.
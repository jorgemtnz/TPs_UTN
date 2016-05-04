program hola;
uses
 crt;
const
 CANT_STAR=25;
type
 str20=string[20];
 str30=string[30];

 tregStar= record
           nomStar: str20;
           coordX, coordY: word;
           nroOrden: byte;
           end;
 tvStar = array[1..CANT_STAR] of tregStar;
 tregCnstStar= record
             nomCnstlcn: str30;
             cjtoStar: tvStar;
             end;
 tarcCnstStar = file of tregCnstStar;

 tregSeg = record
          ptoOrigen: byte;
          ptoDestino: byte;
          end;
 tvSeg = array[1..CANT_STAR] of tregSeg;
 tregCnstSeg = record
             nomCnstSeg: str30;
             cjtoSeg: tvSeg;
             end;
  tarcCnstSeg = file of tregCnstSeg;

 procedure inicializar(var tCntStar:tarcCnstStar;var tCnSeg:tarcCnstSeg);
 var
  rtCnStar:tregCnstStar;
  rtCnSeg:tregCnstSeg;
  nombinC,nombinS,nomtxt: str30;
  c:byte; archivotxt:text;
 begin
   for c:= 1 to 25 do
   begin
    rtCnStar.cjtoStar[c].coordX:=0;
    rtCnStar.cjtoStar[c].coordY:=0;
    rtCnStar.cjtoStar[c].nroOrden:=0;
    rtCnStar.cjtoStar[c].nomStar:=' ';
    rtCnSeg.cjtoSeg[c].ptoOrigen:=0;
    rtCnSeg.cjtoSeg[c].ptoDestino:=0;
    rtCnStar.nomCnstlcn:=' ';
    rtCnSeg.nomCnstSeg:=' ';
   end;
 end;
 function menuprin:byte;
  var
  op : byte;
  begin
   repeat
    gotoxy(21,20); writeln('Elija una opcion');
    gotoxy(21,11); writeln('1. Crear archivos Binarios de Constelaciones');
    gotoxy(21,13); writeln('2. Agregar Nueva Constelacion');
    gotoxy(21,15); writeln('3. Consultar Archivo de Constelaciones');
    gotoxy(21,17); writeln('4. Salir de la Aplicacion');
    gotoxy(21,21); readln(op)
   until (op<5)and(op>0);
   menuprin := op;
   ClrScr;
  end;

  Procedure ventana;
   var
    i,j,k,l,x1,y1,x2,y2:byte;
   begin
    x1:=20;
    y1:=10;
    x2:=65;
    y2:=25;
    gotoXY (x1,y1);
    write ('É');
    for i:=(x1+1) to (x2-1) do
     write ('Í');
     writeln ('»');
    for j:=(y1+1) to y2 do
     begin
      gotoXY (x2,j);
      writeln ('º');
     end;
    for k:=(y1+1) to y2 do
     begin
      gotoXY (x1,k);
      writeln ('º');
     end;
    {gotoXY (x1,y2+1);}
    gotoxy(20,25);
    write ('È');

    for l:=(x1+1) to (x2-1) do
    begin
     write ('Í');
     end;
     write ('¼');
     gotoXY (x1+1,y1+1);
   end;

 procedure act(var tarcCnstStar1:tarcCnstStar;var tarcCnstSeg1:tarcCnstSeg);
 var
 archivotxt :text;
 nomtxt,nombinC,nombinS:string;
 nocons, nest:str30;
 d,r,c,z,nor,ptoor,ptodes: byte;
 cox,coy: word;
 tregCnstStar1:tregCnstStar;
 tregCnstSeg1:tregCnstSeg;
  begin
   writeln('Archivos a actualizar a partir de un archivo.txt');
   writeln('Ingrese nombre (Cnstlact) del archivo de texto: ');
   readln(nomtxt);
   write ('Ingrese nombre del archivo binario Constelacion :');
   readln(nombinC);
   write ('Ingrese nombre del archivo binario Segmentos :');
   readln(nombinS);
   assign(archivotxt,nomtxt+'.txt');
   reset(archivotxt);
   assign(tarcCnstStar1,nombinC+'.dat');
   reset(tarcCnstStar1);
   assign(tarcCnstSeg1,nombinS+'.dat');
   reset(tarcCnstSeg1);
   seek(tarcCnstStar1,filesize(tarcCnstStar1));
   seek(tarcCnstSeg1,filesize(tarcCnstSeg1));
      while not eoln(archivotxt) do
       begin
        c:=0;
        z:=0;
        readln(archivotxt,nocons);
        tregCnstStar1.nomCnstlcn:=nocons;
        write(tarcCnstStar1,tregCnstStar1);
        writeln(nocons);
        delay(500);
        repeat
           tregCnstStar1.cjtoStar[c].coordX:=cox;
           tregCnstStar1.cjtoStar[c].coordY:=coy;
           tregCnstStar1.cjtoStar[c].nroOrden:=nor;
           tregCnstStar1.cjtoStar[c].nomStar:=nest;
           write(tarcCnstStar1,tregCnstStar1);
           until nest=' *';
         repeat
           begin
            z:=z+1;
            readln(archivotxt,ptoor,ptodes);
            tregCnstSeg1.cjtoSeg[z].ptoOrigen:=ptoor;
            tregCnstSeg1.cjtoSeg[z].ptoDestino:=ptodes;
            write(ptoor); write(' '); writeln(ptodes);
            write(tarcCnstSeg1,tregCnstSeg1);
           end
          until ((ptoor=0) and (ptodes=0))
        end;
      close(tarcCnstSeg1);
      close(tarcCnstStar1);
      close(archivotxt);
  end;

 procedure cop(var tarcCnstStar1:tarcCnstStar;var tarcCnstSeg1:tarcCnstSeg);
  var
  archivotxt :text;
  nomtxt,nombinC,nombinS: string;
  nocons, nest:str30;
  nor,d,r,c,z,ptoor,ptodes: byte;
  cox,coy: word;
  tregCnstStar1:tregCnstStar;
  tregCnstSeg1:tregCnstSeg;
  begin
   nomtxt:=' ';nombinC:=' ';nombinS:=' ';nocons:= ' ';nest:= ' ';{inicializacion}
   cox:=0; coy:=0; d:=0;r:=0;c:=0;z:=0;nor:=0;ptoor:=0;ptodes:=0;

   writeln('Archivos a Crear a partir de un archivo.txt');
   writeln('Ingrese nombre (Cnstlcns) del archivo de texto nombre.txt: ');
   readln(nomtxt);
   assign(archivotxt,nomtxt+'.txt');
   reset(archivotxt);
   write ('Ingrese nombre del archivo binario Constelacion :');
   readln(nombinC);
   write ('Ingrese nombre del archivo binario Segmentos :');
   readln(nombinS);
   assign(archivotxt,nomtxt+'.txt');
   reset(archivotxt);
   assign(tarcCnstStar1,nombinC+'.dat');
   rewrite(tarcCnstStar1);
   reset(tarcCnstStar1);
   assign(tarcCnstSeg1,nombinS+'.dat');
   rewrite(tarcCnstSeg1);
   reset(tarcCnstSeg1);
    inicializar(tarcCnstStar1,tarcCnstSeg1 );
      while not eoln(archivotxt) do
       begin
        c:=0;
        z:=0;
        readln(archivotxt,nocons);
        tregCnstStar1.nomCnstlcn:=nocons;
        write(tarcCnstStar1,tregCnstStar1);
        writeln(nocons);
        delay(500);
        repeat
           tregCnstStar1.cjtoStar[c].coordX:=cox;
           tregCnstStar1.cjtoStar[c].coordY:=coy;
           tregCnstStar1.cjtoStar[c].nroOrden:=nor;
           tregCnstStar1.cjtoStar[c].nomStar:=nest;
           write(tarcCnstStar1,tregCnstStar1);
         until nest=' *';
         repeat
           begin
            z:=z+1;
            readln(archivotxt,ptoor,ptodes);
            tregCnstSeg1.cjtoSeg[z].ptoOrigen:=ptoor;
            tregCnstSeg1.cjtoSeg[z].ptoDestino:=ptodes;
            write(ptoor); write(' '); writeln(ptodes);
            write(tarcCnstSeg1,tregCnstSeg1);
           end
          until ((ptoor=0) and (ptodes=0))
        end;
      close(tarcCnstSeg1);
      close(tarcCnstStar1);
      close(archivotxt);
   end;

 procedure crea(var tarcCnstStar1:tarcCnstStar;var tarcCnstSeg1:tarcCnstSeg);
 var
  CnstStar : tarcCnstStar;
  rCnstStar: tregCnstStar;
  CnstSeg  : tarcCnstSeg;
  rCnstSeg : tregCnstSeg;
  NomStar: string;
  NomSeg: string;
  archivo1,archivotxt :text;
  nomtxt,nombinC,nombinS: string;
  nocons, nest:str30;
  i,j,opc,d,r,c,z,nor,ptoor,ptodes: byte;
  cox,coy: word;
  tregCnstStar1:tregCnstStar;
  tregCnstSeg1:tregCnstSeg;
  begin
   writeln('Archivo.txt a Crear a partir de archivos');
   writeln('selecione una opcion valida');
   writeln('1. Archivos binarios Constelacion y Estrella ');
   writeln('2. Archivo de texto  Cnstlcns.txt');
   readln(opc);
	if opc = 1 then
         begin
	  writeln('Ingrese nombre del archivo de texto a crear: ');
	  readln(nomtxt);
	  write ('Ingrese nombre del archivo binario Constelacion :');
	  readln(nombinC);
	  write ('Ingrese nombre del archivo binario Segmentos :');
	  readln(nombinS);
          {El Nuevo Txt a creado a traves de los dos binarios}
	  assign(archivotxt,nomtxt+'.txt');
	  rewrite(archivotxt);
	  {El Archivo Binarios Constelaciones/Estrellas a leer}
	  assign(tarcCnstStar1,nombinC+'.dat');
	  reset(tarcCnstStar1);
	  {El Archivo Binarios Segmento a leer}
	  assign(tarcCnstSeg1,nombinS+'.dat');
	  reset(tarcCnstSeg1);
	  {Carga el Nuevo Txt con el contenido de los Binarios}
	  while not eof(tarcCnstSeg1) do
	   begin
	    i:=0;
	    j:=0;
	    c:=0;
	    z:=0;
	    {Carga en el Nuevo Txt con el 1º Binario la Constelacion}
	    write (archivotxt,'Constelaciòn: ');
	    nocons:= tregCnstStar1.nomCnstlcn;
	    writeln(archivotxt,nocons);
           {Carga en el Nuevo Txt con el 1º Binario las Estrellas}
	     repeat
	       if i = 0 then
	        begin
		c:=c+1;
		i:=1;
		writeln(archivotxt,'Nro.Orden   Nombre de la Estrella   Coord X   Coord Y');
		cox:=tregCnstStar1.cjtoStar[c].coordX;
		coy:=tregCnstStar1.cjtoStar[c].coordY;
		nor:=tregCnstStar1.cjtoStar[c].nroOrden;
		nest:=tregCnstStar1.cjtoStar[c].nomStar;
		writeln(archivotxt,cox,coy:17,nor:26,nest:10);
		end
	       else
	        begin
		c:=c+1;
		cox:=tregCnstStar1.cjtoStar[c].coordX;
		coy:=tregCnstStar1.cjtoStar[c].coordY;
		nor:=tregCnstStar1.cjtoStar[c].nroOrden;
		nest:=tregCnstStar1.cjtoStar[c].nomStar;
		writeln(archivotxt,cox,coy:17,nor:26,nest:10);
	       end;
	        until (nest=' *');
        	{Carga en el Nuevo Txt con el 2º Binario los Segmento}
		repeat
		  if j = 0 then
		   begin
		    j:=1;
		    z:=z+1;
		    writeln(archivotxt,'Pto.Origen    Pto. Destino');
		    ptoor:=tregCnstSeg1.cjtoSeg[z].ptoOrigen;
		    ptodes:=tregCnstSeg1.cjtoSeg[z].ptoDestino;
		    writeln(archivotxt,ptoor,ptodes:14);
		   end
		  else
		   begin
		   z:=z+1;
		   ptoor:=tregCnstSeg1.cjtoSeg[z].ptoOrigen;
		   ptodes:=tregCnstSeg1.cjtoSeg[z].ptoDestino;
		   writeln(archivotxt,ptoor,ptodes:14);
		   end;
		until ((ptoor=0) and (ptodes=0));
	      end;
	      close(tarcCnstSeg1);
	      close(tarcCnstStar1);
	      close(archivotxt);
	     end
	   else
	    begin
	    write('ingrese el nombre del archivo de texto a crear:');
	    readln(nomtxt);
	    {El Antiguo Txt a leer}
	    assign(archivotxt,'Cnstlcns.txt');
	    reset(archivotxt);
	    {El Nuevo Txt a creado a traves del Antiguo Txt}
	    assign(archivo1,nomtxt+'.txt');
	    rewrite(archivo1);
	    {Carga el Nuevo Txt con el contenido del Antiguo Txt}
	    while not eoln(archivotxt) do
	     begin
	      i:=0;
	      j:=0;
	      {Carga el Nuevo Txt con la Constelacion del Antiguo Txt}
	      readln(archivotxt,nocons);
	      write (archivo1,'Constelaciòn: ');
	      writeln(archivo1,nocons);
	      {Carga en el Nuevo Txt con las Estrellas del Antiguo Txt}
	      repeat
	        if i = 0 then
		 begin
		  i:=1;
		  writeln(archivo1,'Nro.Orden   Nombre de la Estrella        Coord X   Coord Y');
		  readln(archivotxt,cox,coy,nor,nest);
		  writeln(archivo1,nor,nest:17,cox:26,coy:10);
		 end
		else
		 begin
		  readln(archivotxt,cox,coy,nor,nest);
		  writeln(archivo1,nor,nest:17,cox:26,coy:10);
		 end;
	      until (nest=' *');
	      {Carga en el Nuevo Txt con los Segmento del Antiguo Txt}
	      repeat
                if j = 0 then
		 begin
		  j:=1;
		  writeln(archivo1,'Pto.Origen    Pto.Destino');
		  readln(archivotxt,ptoor,ptodes);
		  writeln(archivo1,ptoor,ptodes:14);
		 end
		else
		 begin
		  readln(archivotxt,ptoor,ptodes);
		  writeln(archivo1,ptoor,ptodes:14);
		 end;
	      until ((ptoor=0) and (ptodes=0));
	    end;
	    close(archivo1);
	    close(archivotxt);
	 end;
  end;

 procedure AbrirBinStar(var Archivo:tarcCnstStar; modoApertura:char);
 begin
  modoApertura:=' ';
  if (modoApertura = 'w') then {POR FALTA DE TIEMPO NO SE PUDO DESARROLLAR}
  if (modoApertura = 'r') then
  if (modoApertura = 'e') then
 end;

 procedure AbrirBinSeg(var Archivo:tarcCnstSeg; modoApertura:char);
 begin
  modoApertura:=' ';
  if (modoApertura = 'w') then {POR FALTA DE TIEMPO NO SE PUDO DESARROLLAR}
  if (modoApertura = 'r') then
  if (modoApertura = 'e') then
 end;

 procedure AbrirText(var Archivo: text; modoApertura: char);
 begin
  modoApertura:=' ';
  if (modoApertura = 'w') then {POR FALTA DE TIEMPO NO SE PUDO DESARROLLAR}
  if (modoApertura = 'r') then
 end;

 function FileExistBin(var nomLog: tarcCnstStar): boolean;
 begin                        {POR FALTA DE TIEMPO NO SE PUDO DESARROLLAR}
  {$I-}
  reset(nomLog);
  {$I+}
  FileExistBin:=ioresult = 0;
 end;

 function FileExistTxt(var nomLog:text): boolean;
 begin                        {POR FALTA DE TIEMPO NO SE PUDO DESARROLLAR}
  {$I-}
  reset(nomLog);
  {$I+}
  FileExistTxt:=ioresult = 0;
 end;
procedure opcion;
 var
 opc: byte;
 Cons: tarcCnstStar;
 Seg: tarcCnstSeg;
 begin
 repeat
   ventana;
   opc:=menuprin;
   if(opc =1) then   cop(Cons,Seg);
   if(opc =2) then   act(Cons,Seg);
   if(opc =3) then   crea(Cons,Seg);
   if(opc =4) then    writeln('Fin del programa');
 until (opc = 4)
end;

Begin
 ClrScr;
 opcion;
 delay(1000);
end.
   { comentarios
   var
archivo,archivo1: text;
nom: string;
num: byte;
   nom:='w';
 opc:=menuprin;
 writeln('',opc);
 ventana;}
   {nom:= ' ';
  write('ingrese el nombre del archivo de texto:');
  readln(nom);
    assign(archivo,nom);
    reset(archivo);
    readln(archivo,nom,num);
    assign(archivo1,'2.txt');
    rewrite(archivo1);
    write(archivo1,nom,num);
    close(archivo1);}
 {append(archivo1);
 writeln(archivo1,'segundo');
 close(archivo1);}
Program Constelaciones;

Uses
      Crt, Dos;


Const
CANT_STAR = 25;          {declaracion de constante Cant_Star}

Type                     {tipo de datos para la constalaciones y estrellas}
str20= string[20];
str30= string[30];

{Define los Vectores}
tregStar= record                {registro vector de estrella}
          nomStar: str20;
          coordX,
          coordY: word;
          nroOrden: byte;
          end;

tregSeg=  record                {registro vector de segmentos}
          ptoOrigen,
          ptoDestino: byte;
          end;


{Define la longitud de los Vectores}
tvStar = array [1..cant_star]  of tregStar;   {arreglo de estrellas max 25}
tvSeg  = array [1..cant_star]  of tregSeg;    {arreglo de segmentos max 25}

{Define los Tipos de registros de los Binarios}

tregCnstStar= record                      {Registro const Estrella/ Reg Bin}
               nomCnstlcn: str30;
               cjtoStar  : tvStar;
              end;

tregCnstSeg = record                      {Registro const Estrella/ Reg Bin}
               nomCnsSeg: str30;
               cjtoSeg  : tvSeg;
              end;

{Define los tipos de registros de los Binarios}
tarcCnstStar = file of tregCnstStar;
tarcCnstSeg  = file of tregCnstSeg;
{variables globales}
var
archivotxt,archivoV:text;
archivoC: tarcCnstStar;
con: tregCnstStar;
archivoS: tarcCnstSeg;
seg: tregCnstSeg;

procedure inicializar(var CnStar:tarcCnstStar;var CnSeg:tarcCnstSeg;finS:byte;finC:byte);
 var
  rCnStar:tregCnstStar;
  rCnSeg:tregCnstSeg;
  i,c:byte;
  begin
 writeln(finS,' ',finC);
 writeln;
  c:=0; i:=0;
  for i:=0 to finC do begin
     repeat
     begin
    writeln('INICIALIZACION: sumador: ',c);
    delay(1000);
    rCnStar.cjtoStar[c].coordX:=0;
    rCnStar.cjtoStar[c].coordY:=0;
    rCnStar.cjtoStar[c].nroOrden:=0;
    rCnStar.cjtoStar[c].nomStar:='*';

    write(' ',rCnStar.cjtoStar[c].coordX);
    write(' ',rCnStar.cjtoStar[c].coordY);
    write(' ',rCnStar.cjtoStar[c].nroOrden);
    writeln(' ',rCnStar.cjtoStar[c].nomStar);
    c:=c+1;
    end;
    until c = 25;
    rCnStar.nomCnstlcn:='*';  writeln('nomb Constela',rCnStar.nomCnstlcn);
    write(CnStar,rCnStar);
    write(CnSeg,rCnSeg);
    delay(1000);
  end;
   c:=0;
  for i:=0 to finS do begin
     repeat        begin

    rCnSeg.cjtoSeg[c].ptoOrigen:=0;
    rCnSeg.cjtoSeg[c].ptoDestino:=0;

    write(' ', rCnSeg.cjtoSeg[c].ptoOrigen,'  ');
    writeln(' ', rCnSeg.cjtoSeg[c].ptoDestino,'  sumador:  ',c);
     c:=c+1;
     delay(1000)
    end;
    until c = 25;
    rCnSeg.nomCnsSeg:='*';   writeln('nomb Segmen',rCnSeg.nomCnsSeg);
    delay(1000);
  end;
  writeln('fin INICIALIZACION ');writeln;
  DELAY(1000)
 end;

Function FileExistBin(var nomLog:tarcCnstStar): Boolean;
     begin
         {$I-}
         reset(nomLog);
         {$I+}
         fileExistBin:= ioresult=0;
     end;{FileExistBin}

Function FileExistBinS(var nomLog:tarcCnstSeg): Boolean;
     begin
         {$I-}
         reset(nomLog);
         {$I+}
         fileExistBins:= ioresult=0;
     end;{FileExistBinS}


Function FileExistText(var nomLog: text): Boolean;
     begin
         {$I-}
         reset(nomLog);
         {$I+}
         fileExistText:= ioresult=0;
     end;{FileExiystText}

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

   procedure AbrirBinStar(var Archivo:tarcCnstStar; modoApertura:char;var archivotxt:Text);
 var
                 {DECLARACION DE VARIABLES}
 i,c: byte;
 Con:tregCnstStar;
 begin
     c:=0;
  i:=0;
  if (modoApertura = 'w') then begin

  readln(archivotxt,Con.nomCnstlcn);
   repeat
            {LECTURA DE ARCHIVO Y PASE A VARIABLE}

readln(archivotxt,Con.cjtoStar[c].coordX,Con.cjtoStar[c].coordY,Con.cjtoStar[c].nroOrden,Con.cjtoStar[c].nomStar);

	       if i = 0 then
	        begin
		i:=1;
writeln('Constelaciòn: ',Con.nomCnstlcn);
writeln('Nro.Orden   Nombre de la Estrella   Coord X   Coord Y sumador :',c);
write(Con.cjtoStar[c].nroOrden,'     ',Con.cjtoStar[c].nomStar);
writeln('    ',Con.cjtoStar[c].coordX,'    ',Con.cjtoStar[c].coordY);
                delay(1000);
		end
	       else
	        begin
write(Con.cjtoStar[c].nroOrden,'     ',Con.cjtoStar[c].nomStar);
writeln('    ',Con.cjtoStar[c].coordX,'    ',Con.cjtoStar[c].coordY,'  sum:',c);
delay(1000);
	       end;

               c:=c+1;
        until Con.cjtoStar[c].nroOrden = 0;
                             {ESCRITURA DE ARCHIVO}

     write(Archivo,Con);
  end;

  if (modoApertura = 'r') then begin
   repeat
	       if i = 0 then
	        begin
		i:=1;
writeln('Constelaciòn: ',Con.nomCnstlcn);
writeln('Nro.Orden   Nombre de la Estrella   Coord X   Coord Y sumador :',c);
write(Con.cjtoStar[c].nroOrden,'     ',Con.cjtoStar[c].nomStar);
writeln('    ',Con.cjtoStar[c].coordX,'    ',Con.cjtoStar[c].coordY);
                delay(1000);
		end
	       else
	        begin
write(Con.cjtoStar[c].nroOrden,'     ',Con.cjtoStar[c].nomStar);
writeln('    ',Con.cjtoStar[c].coordX,'    ',Con.cjtoStar[c].coordY,'  sum:',c);
delay(1000);
	       end;

               c:=c+1;
        until Con.cjtoStar[c].nroOrden = 0;
         writeln('fin muestra Con');
  writeln;

  end;
  if (modoApertura = 'e') then begin


  end;
 end;

 procedure AbrirBinSeg(var Archivo:tarcCnstSeg; modoApertura:char;var archivotxt:Text);
 var                   {DECLARACION DE VARIABLES}
 j,c: byte;
 Seg:tregCnstSeg;
 begin
 c:=0;
 j:=0;
  if (modoApertura = 'w') then begin
  repeat
            {LECTURA DE ARCHIVO Y PASE A VARIABLE}

   readln(archivotxt,Seg.cjtoSeg[c].ptoOrigen,Seg.cjtoSeg[c].ptoDestino);

    if j = 0 then
		   begin
		    j:=1;

  writeln('Pto.Origen    Pto. Destino sumador: ',c);
  writeln(Seg.cjtoSeg[c].ptoOrigen,Seg.cjtoSeg[c].ptoDestino:14);
                   delay(1000);
		   end
		  else
		   begin
  writeln(Seg.cjtoSeg[c].ptoOrigen,Seg.cjtoSeg[c].ptoDestino:14);
  delay(1000);
		   end;
                   c:=c+1;
 until ((Seg.cjtoSeg[c].ptoOrigen=0) and (Seg.cjtoSeg[c].ptoDestino=0));
                             {ESCRITURA DE ARCHIVO}
     write(Archivo,Seg);
  end;


  if (modoApertura = 'r') then begin
       writeln('Pto.Origen    Pto. Destino sumador: ',c);
  repeat  	 begin

  writeln(Seg.cjtoSeg[c].ptoOrigen,Seg.cjtoSeg[c].ptoDestino:14,' sumador:',c);
  delay(1000);
   c:=c+1;
		   end;

 until ((Seg.cjtoSeg[c].ptoOrigen=0) and (Seg.cjtoSeg[c].ptoDestino=0));
   writeln('fin muestra Seg');
  writeln;
  end;

  if (modoApertura = 'e') then begin

  end;

 end;

 procedure AbrirText(var Archivo: text; modoApertura: char);
 begin
  modoApertura:=' ';
  if (modoApertura = 'w') then  begin
  end;
  if (modoApertura = 'r') then begin
  end;
 end;

 function CnsArchS (var Archivo:tarcCnstSeg):Boolean;
 var
 Confirmacion: Char;
 Begin

    If FileExistBinS(Archivo)= true  then
        Begin
              writeln (#10,'>>> El archivo existe <<<',#10);
              repeat
                  write ('¨Desea reemplazarlo? s=> si || n=> no : ');
                  readln(Confirmacion);
                  Confirmacion:= upcase(Confirmacion);
              Until (Confirmacion='S') or (Confirmacion='N');

              if Confirmacion ='S' then  begin
                   CnsArchS:=true;
                   end
              else
                 CnsArchS:=false;
               {volver al menu?}
        end
    else
        begin
         writeln(#10);
         writeln ('>>> ­ATENCION!: El nombre del Archivo ingresado no existe <<<');
         repeat
                  writeln;
                  write ('¨Desea crearlo? s=> si || n=> no : ');
                  readln (Confirmacion);
                  Confirmacion:= upcase(Confirmacion);

         Until (Confirmacion='S') or (Confirmacion='N');

         if Confirmacion ='S' then
            begin
             CnsArchS:=true;
            end
         else
            begin
              CnsArchS:=false;
              {volver al menu?}
            end
        end;

End;{ConsArch}

{Consistencia Binarios 2 }
function CnsArchC (var Archivo:tarcCnstStar):Boolean;
 var
 Confirmacion: Char;
 Begin
    If FileExistBin(Archivo)=true  then
        Begin
              writeln (#10,'>>> El archivo existe <<<',#10);
              repeat
                  write ('¨Desea reemplazarlo? s=> si || n=> no : ');
                  readln(Confirmacion);
                  Confirmacion:= upcase(Confirmacion);
              Until (Confirmacion='S') or (Confirmacion='N');

              if Confirmacion ='S' then
                 begin
                    CnsArchC:=true;
                 end
              else
                 CnsArchC:=false;
               {volver al menu?}
        end
    else
        begin

         writeln(#10);
         writeln ('>>> ­ATENCION!: El nombre del Archivo ingresado no existe <<<');
         repeat
                  writeln;
                  write ('¨Desea crearlo? s=> si || n=> no : ');
                  readln (Confirmacion);
                  Confirmacion:= upcase(Confirmacion);

         Until (Confirmacion='S') or (Confirmacion='N');

         if Confirmacion ='S' then
            begin
              CnsArchC:=true;
            end
         else
            begin
              CnsArchC:=false;
              {volver al menu?}
            end
        end;

End;{ConsArchS}
{ {PROCEDIMIENTO ACTUALIZA ARCHIVOS BINARIOS, lo modifico una vez terminada la opcion grabar}
procedure act(var tarcCnstStar1:tarcCnstStar;var tarcCnstSeg1:tarcCnstSeg);
 var
 archivotxt :text;                {DECLARACION DE VARIABLES}
 nomtxt,nombinC,nombinS: string;
 nocons, nest:str30;
 d,r,c,z,nor,ptoor,ptodes: byte;
 cox,coy: word;
 tregCnstStar1:tregCnstStar;
 tregCnstSeg1:tregCnstSeg;
 begin                           {INICIO DEL PROCEDIMIENTO}
   writeln('Archivos a actualizar a partir de un archivo.txt'); {LECTURA DE NOMBRES DE ARCHIVOS}
   writeln('Ingrese nombre(Cnstlact) del archivo de texto: ');
   readln(nomtxt);
   assign(archivotxt,nomtxt+'.txt');      {ASIGNA EL ARCHIVO FISICO A UNA VARIABLE }

   if( FileExistText(archivotxt) = true) then    {CONSISTENCIA DE ARCHIVO TXT}
   begin
    reset(archivotxt);        {ABRE EL ARCHIVO}
    write ('Ingrese nombre del archivo binario Constelacion :');
   readln(nombinC);
    assign(tarcCnstStar1,nombinC+'.dat');
    If (FileExistBin(tarcCnstStar1)= true)   then   {CONSISTENCIA DE ARCHIVO BINARIO CONSTELACION}
    begin
     reset(tarcCnstStar1);
     write ('Ingrese nombre del archivo binario Segmentos :');
     readln(nombinS);
     assign(tarcCnstSeg1,nombinS+'.dat');
     If (FileExistBinS(tarcCnstSeg1)= true)  then  {CONSISTENCIA DE ARCHIVO BINARIO SEGMENTOS}
     begin
      reset(tarcCnstSeg1);
      seek(tarcCnstStar1,filesize(tarcCnstStar1)); {POSICIONAMIENTO DE PUNTERO}
      seek(tarcCnstSeg1,filesize(tarcCnstSeg1));   {EN FIN DE ARCHIVO}
      while not eoln(archivotxt) do         {CICLO CONDICIONAL 0 - X}
       begin
        c:=0;
        z:=0;
        readln(archivotxt,nocons);       {LECTURA DE ARCHIVO Y PASE A VARIABLE}
        tregCnstStar1.nomCnstlcn:=nocons;
        write(tarcCnstStar1,tregCnstStar1); {ESCRITURA DE ARCHIVO}
        repeat
          c:=c+1;
           readln(archivotxt,cox,coy,nor,nest);  {LECTURA DE ARCHIVO Y PASE A VARIABLE}
           tregCnstStar1.cjtoStar[c].coordX:=cox;
           tregCnstStar1.cjtoStar[c].coordY:=coy;
           tregCnstStar1.cjtoStar[c].nroOrden:=nor;
           tregCnstStar1.cjtoStar[c].nomStar:=nest;
           write(tarcCnstStar1,tregCnstStar1);     {ESCRITURA DE ARCHIVO}
        until nest=' *';
          repeat
           begin
            z:=z+1;
            readln(archivotxt,ptoor,ptodes);      {LECTURA DE ARCHIVO Y PASE A VARIABLE}
            tregCnstSeg1.cjtoSeg[z].ptoOrigen:=ptoor;
            tregCnstSeg1.cjtoSeg[z].ptoDestino:=ptodes;
            write(tarcCnstSeg1,tregCnstSeg1);        {ESCRITURA DE ARCHIVO}
           end
          until ((ptoor=0) and (ptodes=0))
        end;
      close(tarcCnstSeg1);      {CIERRE DE ARCHIVOS }
      close(tarcCnstStar1);
      close(archivotxt);
      writeln('Archivos actualizados');
      end
     else
      begin
      writeln('no existe el archivo segmentos'); {NO EXISTE ARCHIVO}
      delay(1000);
      exit;                                      {SALIDA}
      end;
     end
    else
     begin
      writeln('no existe el archivo constelacion');
      delay(1000);
      exit;
     end;
    end
   else
    begin
     writeln('El archivo no existe');
     delay(1000);
     exit;
    end;
  end;
     { PROCEDIMIENTO QUE CREA BINARIO  }
 procedure gra(var archivoC:tarcCnstStar;var archivoS:tarcCnstSeg);
  var
  archivotxt :text;   nomtxt,nombinC,nombinS: string;
  mAp:char;
  finC,finS:byte;
  Con:tregCnstStar;
  Seg:tregCnstSeg;
  {FALTA AGREGAR EL USO DE PROCEDIMIENTO AbrirText}
  begin
   nomtxt:=' ';nombinC:=' ';nombinS:=' ';
    mAp:=' ';
   writeln('Archivos a Crear a partir de un archivo.txt');
    write ('Ingrese nombre del archivo binario Constelacion :');
   readln(nombinC);
    assign(archivoC,nombinC+'.dat');
    If (CnsArchC(archivoC)= true)   then begin  {CONSISTENCIA DE ARCHIVO BINARIO CONSTELACION}

     rewrite(archivoC);
     reset(archivoC);

     write ('Ingrese nombre del archivo binario Segmentos :');
     readln(nombinS);
     assign(archivoS,nombinS+'.dat');
     If (CnsArchS(archivoS)= true)  then  begin {CONSISTENCIA DE ARCHIVO BINARIO SEGMENTOS}

      rewrite(archivoS);
      reset(archivoS);

      writeln('Ingrese nombre(Cnstlcns)del archivo de texto nombre.txt: ');
      readln(nomtxt);
      assign(archivotxt,nomtxt+'.txt');      {ASIGNA EL ARCHIVO FISICO A UNA VARIABLE }

      inicializar(archivoC,archivoS,filesize(archivoS),filesize(archivoC));
      close(archivoC);
      close(archivoS);
      reset(archivoC);
      reset(archivoS);
      mAp:='r';
       AbrirBinStar(archivoC,mAp,archivotxt);  {no estan funcinando bien}
       AbrirBinSeg(archivoS,mAp,archivotxt);
               {rebizar si lo hace bien}

      exit;
      mAp:='w';                               {OPCION DE GRABAR}
      if( FileExistText(archivotxt) = true) then   begin  {CONSISTENCIA DE ARCHIVO TXT}

      reset(archivotxt);        {ABRE EL ARCHIVO}
      while not eoln(archivotxt) do   begin  {CICLO CONDICIONAL 0 - X}

        readln(archivotxt,Con.nomCnstlcn);  {LECTURA DE ARCHIVO Y PASE A VARIABLE}

        AbrirBinStar(archivoC,mAp,archivotxt);  {no estan funcinando bien}
        AbrirBinSeg(archivoS,mAp,archivotxt);   {variable nomCnsSeg nunca recibe}
                                                {ningun valor, hay que igualarla a nomCnstlcn}
       end;
       close(archivoC);      {CIERRE DE ARCHIVOS }
       close(archivoS);
       close(archivotxt);
       writeln('Archivos creados');
       delay(1000);
      end
     else
      begin
      writeln('no existe el archivo de texto'); {NO EXISTE ARCHIVO}
      delay(1000);
      exit;                                      {SALIDA}
      end;
     end
    else
     begin
      writeln('no se creo el archivo segmentos');
      delay(1000);
      exit;
     end;
    end
   else
    begin
     writeln('no se creo el archivo constelacion');
     delay(1000);
     exit;
    end;
end;
procedure leer1(var archivoC:tarcCnstStar;var archivoS:tarcCnstSeg);
 var

  Con: tregCnstStar;
  Seg : tregCnstSeg;
  NomStar: string;
  NomSeg: string;
  archivo1,archivotxt :text;
  nomtxt,nombinC,nombinS: string;
  nocons, nest:str30;
  i,j,opc,d,r,c,z,nor,ptoor,ptodes: byte;
  cox,coy: word;
  archivoV: text;

  begin
   writeln('Archivo.txt a Crear a partir de archivos');
   writeln('selecione una opcion valida');
   writeln('1. Archivos binarios Constelacion y Estrella ');
   writeln('2. Archivo de texto  Cnstlcns.txt');
   readln(opc);
   writeln('Ingrese nombre del archivo de texto a crear: ');
   readln(nomtxt);
   assign(archivotxt,nomtxt+'.txt');
   rewrite(archivotxt);


	if opc = 1 then         begin

	  write ('Ingrese nombre del archivo binario Constelacion :');
	  readln(nombinC);
	  write ('Ingrese nombre del archivo binario Segmentos :');
	  readln(nombinS);


	  {El Archivo Binarios Constelaciones/Estrellas a leer}
	  assign(archivoC,nombinC+'.dat');
	  reset(archivoC);
	  {El Archivo Binarios Segmento a leer}
	  assign(archivoS,nombinS+'.dat');
	  reset(archivoS);
	  {Carga el Nuevo Txt con el contenido de los Binarios}
	  while not eof(archivoS) do
	   begin
	    i:=0;
	    j:=0;
	    c:=0;
	    z:=0;
	    {Carga en el Nuevo Txt con el 1º Binario la Constelacion}

	    write ('Constelaciòn: ');
	    writeln(Con.nomCnstlcn);
           {Carga en el Nuevo Txt con el 1º Binario las Estrellas}
	     repeat
	       if i = 0 then
	        begin
		c:=c+1;
		i:=1;

writeln('Nro.Orden   Nombre de la Estrella   Coord X   Coord Y');
write(Con.nomCnstlcn,Con.cjtoStar[c].coordY:17);
writeln(Con.cjtoStar[c].nroOrden:26,Con.cjtoStar[c].nomStar:10);
                delay(1000);
		end
	       else
	        begin
		c:=c+1;

write(Con.cjtoStar[c].coordX,Con.cjtoStar[c].coordY:17);
writeln(Con.cjtoStar[c].nroOrden:26,Con.cjtoStar[c].nomStar:10);
delay(1000);
	       end;
	        until (nest=' *');

        	{Carga en el Nuevo Txt con el 2º Binario los Segmento}
		repeat
		  if j = 0 then
		   begin
		    j:=1;
		    z:=z+1;
		    writeln('Pto.Origen    Pto. Destino');
writeln(Seg.cjtoSeg[z].ptoOrigen,Seg.cjtoSeg[z].ptoDestino:14);
                   delay(1000);
		   end
		  else
		   begin
		   z:=z+1;

writeln(Seg.cjtoSeg[z].ptoOrigen,Seg.cjtoSeg[z].ptoDestino:14);
delay(1000);
		   end;
		until ((Seg.cjtoSeg[z].ptoOrigen=0) and (Seg.cjtoSeg[z].ptoDestino=0));

	      end;
	      close(archivoS);
	      close(archivoC);
	      close(archivotxt);
	     end
	   else
	    begin

	    {El Antiguo Txt a leer}
	    assign(archivoV,'Cnstlcns.txt');
	    reset(archivoV);

	    {Carga el Nuevo Txt con el contenido del Antiguo Txt}
	    while not eoln(archivoV) do
	     begin
	      i:=0;
	      j:=0;
	      {Carga el Nuevo Txt con la Constelacion del Antiguo Txt}
	      readln(archivoV,nocons);
	      write (archivotxt,'Constelaciòn: ');
	      writeln(archivotxt,nocons);
	      {Carga en el Nuevo Txt con las Estrellas del Antiguo Txt}
	      repeat
	        if i = 0 then
		 begin
		  i:=1;
		  writeln(archivotxt,'Nro.Orden   Nombre de la Estrella        Coord X   Coord Y');
		  readln(archivoV,cox,coy,nor,nest);
		  writeln(archivotxt,nor,nest:17,cox:26,coy:10);
		 end
		else
		 begin
		  readln(archivoV,cox,coy,nor,nest);
		  writeln(archivotxt,nor,nest:17,cox:26,coy:10);
		 end;
	      until (nest=' *');
	      {Carga en el Nuevo Txt con los Segmento del Antiguo Txt}
	      repeat
                if j = 0 then
		 begin
		  j:=1;
		  writeln(archivotxt,'Pto.Origen    Pto.Destino');
		  readln(archivoV,ptoor,ptodes);
		  writeln(archivotxt,ptoor,ptodes:14);
		 end
		else
		 begin
		  readln(archivoV,ptoor,ptodes);
		  writeln(archivotxt,ptoor,ptodes:14);
		 end;
	      until ((ptoor=0) and (ptodes=0));
	    end;
	    close(archivotxt);
	    close(archivoV);
	 end;
end;

 procedure opcion;
 var
 opc: byte;
 Cons: tarcCnstStar;
 Seg: tarcCnstSeg;
 begin
 repeat
   ClrScr;
  {ventana;  }
   opc:=menuprin;
   if(opc =1) then begin
     gra(Cons,Seg);
     end;
   if(opc =2) then begin
    act(Cons,Seg);
    end;
   if(opc =3) then   begin
    leer(Cons,Seg);
   end;
   if(opc =4) then   begin
   writeln('Fin del programa');
   end;
 until (opc = 4);
end;

Begin
 ClrScr;
 opcion;
 delay(1500);
end.



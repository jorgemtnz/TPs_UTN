{TP# 1  Asignatura: Algoritmos y Estructura de Datos
         Curso K1071   A寸:2010
         Integrante: Mart》ez Jorge


Programa que partiendo de un archivo txt que contiene una serie de
constelaciones; se copian a dos archivos binarios donde un binario
almacena la informacion relacionada con las estrellas y el otro con los
segmentos de dichas estrellas. Mas tarde son actualizados a partir de
un  archivo txt. Para posteriormente imprimir en ListCnst.Txt los dos
binarios ya actualizados.}

Program pases;
Uses
      Crt, Dos;
Const
CANT_STAR = 25;          {Declaracion de constante Cant_Star}

Type                     {Tipo de datos para constelaciones y estrellas}
str20= string[20];
str30= string[30];

tregStar= record                {Registro de estrella}
          nomStar: str20;
          coordX,
          coordY: word;
          nroOrden: byte;
          end;

tregSeg=  record                {Registro de segmento}
          ptoOrigen,
          ptoDestino: byte;
          end;


{Define la longitud de los Vectores}
tvStar = array [1..cant_star]  of tregStar;{Vector de registro estrellas}
tvSeg  = array [1..cant_star]  of tregSeg; {Vector de registro segmentos}

{Define los tipos de registros de los archivos binarios}

tregCnstStar= record {Registro archivo constelacion de estrellas}
               nomCnstlcn: str30;
               cjtoStar  : tvStar;
              end;

tregCnstSeg = record {Registro archivo constelacion de segmentos}
               nomCnsSeg: str30;
               cjtoSeg  : tvSeg;
              end;

{Define los tipos de registros de los binarios}
tarcCnstStar = file of tregCnstStar;
tarcCnstSeg  = file of tregCnstSeg;

{Comprueba existencia de archivo binario constelacion de estrellas}
Function FileExistBin(var nomLog:tarcCnstStar): Boolean;
begin
  {$I-}
  reset(nomLog);
  {$I+}
  fileExistBin:= ioresult=0;
end;
{Comprueba existencia de archivo binario constelacion de segmentos}
Function FileExistBinS(var nomLog:tarcCnstSeg): Boolean;
begin
  {$I-}
  reset(nomLog);
  {$I+}
  fileExistBins:= ioresult=0;
end;

{Comprueba existencia de archivo de texto}
Function FileExistText(var nomLog: text): Boolean;
begin
  {$I-}
  reset(nomLog);
  {$I+}
  fileExistText:= ioresult=0;
end;

{Menu principal}
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

{Prepara el archivo constelacion estrella segun opcion}
procedure AbrirBinStar(var Archivo:tarcCnstStar; modoApertura:char);
var
  Con:tregCnstStar;

begin
  case modoApertura of
    'w': begin
           reset(Archivo);
         end;
    'r': begin
           reset(Archivo);
         end;
    'e': begin
           reset(Archivo);
           seek(Archivo,filesize(Archivo));
         end;
  end;
end;

{Prepara el archivo constelacion segmentos segun opcion}
procedure AbrirBinSeg(var Archivo:tarcCnstSeg; modoApertura:char);
var
  Seg:tregCnstSeg;

begin
   case modoApertura of
    'w': begin
           reset(Archivo);
         end;
    'r': begin
           reset(Archivo);
         end;
    'e': begin
           reset(Archivo);
           seek(Archivo,filesize(Archivo));
         end;
  end;
end;

{Prepara el archivo de texto segun opcion}
procedure AbrirText(var Archivo: text; modoApertura: char);
begin
  case modoApertura of
    'r': begin
           reset(Archivo);
         end;
    'w': begin
           append(Archivo);
         end;
  end;
end;

{realiza consistencia de archivo constelacion estrella,opcion 1}
function CnsArchS (var Archivo:tarcCnstSeg):Boolean;
var
  Confirmacion: Char;

Begin
  If FileExistBinS(Archivo)= true  then   Begin
    writeln (#10,' El archivo existe ',#10);
    repeat
      writeln('求esea reemplazarlo? ');
      writeln(' s => si ');
      writeln(' n => no ');
      readln(Confirmacion);
      Confirmacion:= upcase(Confirmacion);
    Until (Confirmacion='S') or (Confirmacion='N');

    if Confirmacion ='S' then  begin
      CnsArchS:=true;
    end
    else
      CnsArchS:=false;
  end
  else
    begin
      writeln(#10);
      writeln ('El Archivo no existe');
      repeat
        writeln;
        writeln('求esea crearlo? ');
        writeln(' s => si ');
        writeln(' n => no ');
        readln (Confirmacion);
        Confirmacion:= upcase(Confirmacion);
      Until (Confirmacion='S') or (Confirmacion='N');

      if Confirmacion ='S' then   begin
        CnsArchS:=true;
      end
      else
        CnsArchS:=false;
  end;
End;

{realiza consistencia de archivo constelacion segmento,opcion 1}
function CnsArchC (var Archivo:tarcCnstStar):Boolean;
var
  Confirmacion: Char;
Begin
  If FileExistBin(Archivo)=true  then   Begin
    writeln (#10,' El archivo existe ',#10);
    repeat
      writeln('求esea reemplazarlo? ');
      writeln(' s => si ');
      writeln(' n => no ');
      readln(Confirmacion);
      Confirmacion:= upcase(Confirmacion);
    Until (Confirmacion='S') or (Confirmacion='N');

    if Confirmacion ='S' then  begin
      CnsArchC:=true;
    end
    else
      CnsArchC:=false;
  end
  else
    begin
      writeln(#10);
      writeln ('El Archivo no existe');
      repeat
        writeln;
        writeln('求esea crearlo? ');
        writeln(' s => si ');
        writeln(' n => no ');
        readln (Confirmacion);
        Confirmacion:= upcase(Confirmacion);
      Until (Confirmacion='S') or (Confirmacion='N');

      if Confirmacion ='S' then   begin
        CnsArchC:=true;
      end
      else
        CnsArchC:=false;
    end;
end;
{Procedimiento que copia de txt a binarios}
procedure copiar(var archivoC:tarcCnstStar;var archivoS:tarcCnstSeg;var archTxt:text);
var
  i,j:byte;
  Con:tregCnstStar;
  Seg:tregCnstSeg;
  car:char;

begin
  i:=1;
  while not eof(archTxt) do begin
    i:=1;
    readln(archTxt,Con.nomCnstlcn);
    Seg.nomCnsSeg:=Con.nomCnstlcn;
    repeat      begin
      read(archTxt,Con.cjtoStar[i].coordX,Con.cjtoStar[i].coordY);
      readln(archTxt,Con.cjtoStar[i].nroOrden,car,Con.cjtoStar[i].nomStar);
      i:=i+1;
      end;
    until ((Con.cjtoStar[i-1].nroOrden =0)or (i = 26));
      write(archivoC,Con);
      j:=1;
    repeat  begin
      readln(archTxt,Seg.cjtoSeg[j].ptoOrigen,Seg.cjtoSeg[j].ptoDestino);
      j:=j+1;
      end;
    until ((Seg.cjtoSeg[j-1].ptoOrigen =0) and(Seg.cjtoSeg[j-1].ptoDestino =0));
    write(archivoS,Seg);
  end;
end;

{corresponde a la opcion 1,crear binarios}
procedure grabar(var archivoC:tarcCnstStar;var archivoS:tarcCnstSeg);
var
  archTxt :text;
  nomtxt,nombinC,nombinS: string;
  mAp,car:char;
  Con:tregCnstStar;
  Seg:tregCnstSeg;
  i,j:byte;

begin
  nomtxt:=' ';nombinC:=' ';nombinS:=' ';
  mAp:=' ';
  writeln('Archivos a Crear a partir de un archivo.txt');
  write ('Ingrese nombre del archivo binario Constelacion :');
  readln(nombinC);
  assign(archivoC,nombinC+'.dat');

  If (CnsArchC(archivoC)= true) then begin
    rewrite(archivoC);
    close(archivoC);
    mAp:='w';
    AbrirBinStar(archivoC,mAp);
    write ('Ingrese nombre del archivo binario Segmentos :');
    readln(nombinS);
    assign(archivoS,nombinS+'.dat');

    If (CnsArchS(archivoS)= true) then begin
      rewrite(archivoS);
      close(archivoS);
      AbrirBinSeg(archivoS,mAp);
      writeln('Ingrese nombre(Cnstlcns)del archivo de texto nombre.txt: ');
      readln(nomtxt);
      assign(archTxt,nomtxt+'.txt');

      if( FileExistText(archTxt)= true) then begin
        mAp:='r';
        AbrirText(archTxt,mAp);
        copiar(archivoC,archivoS,archTxt);
        close(archivoC);
        close(archivoS);
        close(archTxt);
        writeln('Archivos creados');
        delay(1000);
      end
      else
        begin
          writeln('no existe el archivo de texto');
          delay(1000);
          exit;
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

{corresponde a la opcion 2,actualizar binarios}
procedure actualizar(var ArchivoC :tarcCnstStar;var ArchivoS:tarcCnstSeg);
var
  archTxt :text;
  nomtxt,nombinC,nombinS: string;
  mAp,car:char;
  Con:tregCnstStar;
  Seg:tregCnstSeg;
  i,j:byte;

begin
  nomtxt:=' ';nombinC:=' ';nombinS:=' ';
  mAp:=' ';
  writeln('Archivos a actualizar a partir de un archivo.txt');
  write ('Ingrese nombre del archivo binario Constelacion :');
  readln(nombinC);
  assign(archivoC,nombinC+'.dat');

  If (FileExistBin(archivoC)= true) then begin
    mAp:='e';
    AbrirBinStar(archivoC,mAp);
    write ('Ingrese nombre del archivo binario Segmentos :');
    readln(nombinS);
    assign(archivoS,nombinS+'.dat');

    If (FileExistBinS(archivoS)= true) then begin
      AbrirBinSeg(archivoS,mAp);
      writeln('Ingrese nombre(Cnstlact)del archivo de texto nombre.txt: ');
      readln(nomtxt);
      assign(archTxt,nomtxt+'.txt');

      if( FileExistText(archTxt) = true) then begin
        mAp:='r';
        AbrirText(archTxt,mAp);
        seek(archivoC,filesize(ArchivoC)); {POSICIONAMIENTO DE PUNTERO}
        seek(archivoS,filesize(ArchivoS));
        copiar(archivoC,archivoS,archTxt);
        close(archivoC);
        close(archivoS);
        close(archTxt);
        writeln('Archivos actualizados');
        delay(1000);
      end
      else
        begin
          writeln('no existe el archivo de texto');
          delay(1000);
          exit;
        end;
    end
    else
      begin
        writeln('no se actualizo el archivo segmentos');
        delay(1000);
        exit;
      end;
  end
  else
    begin
      writeln('no se actualizo el archivo constelacion');
      delay(1000);
      exit;
    end;
end;

{corresponde a la opcion 3,pasar binariosa txt y pasar txt a txt}
procedure leer(var archivoC:tarcCnstStar;var archivoS:tarcCnstSeg);
var
  Con: tregCnstStar;
  Seg : tregCnstSeg;
  archTxt,archV :text;
  mAp:char;
  nor,opc,i,j,ptoor,ptodes:byte;
  nest,ncons,nomtxt,nombinS,nombinC:str30;
  cox,coy:word;

begin
  repeat
    writeln('Archivo.txt a Crear a partir de archivos');
    writeln('selecione una opcion valida');
    writeln('1. Archivos binarios Constelacion y Estrella ');
    writeln('2. Archivo de texto  Cnstlcns.txt');
    readln(opc);
  until ((opc>0) and (opc<3));
  assign(archTxt,'ListCnst.txt');
  rewrite(archTxt);
  close(archTxt);
  mAp:='w';
  AbrirText(archTxt,mAp);

  if opc = 1 then         begin
    write('Ingrese nombre del archivo binario Constelacion :');
    readln(nombinC);
    assign(archivoC,nombinC+'.dat');

    if (FileExistBin(archivoC)= true)   then   begin
      AbrirBinStar(archivoC,mAp);
      write ('Ingrese nombre del archivo binario Segmentos :');
      readln(nombinS);
      assign(archivoS,nombinS+'.dat');

      if (FileExistBinS(archivoS)= true)  then   begin
        AbrirBinSeg(archivoS,mAp);

        i:=1;
        j:=1;
        while not eof(archivoC) do  begin
          read(archivoC,Con);
          writeln(archTxt,'Constelacion:',Con.nomCnstlcn:30);
          i:=1;
          writeln(archTxt,'Nro.Orden      Nombre de la Estrella     Coord X   Coord Y');
          while not ((Con.cjtoStar[i].nroOrden =0)or (i = 26))  do begin
            write(archTxt,Con.cjtoStar[i].nroOrden:5,Con.cjtoStar[i].nomStar:30);
            writeln(archTxt,Con.cjtoStar[i].coordX:10,Con.cjtoStar[i].coordY:12);
            i:=i+1;
          end;
          read(archivoS,Seg);
          j:=1;
          writeln(archTxt,'Pto.Origen    Pto.Destino');
          while not((Seg.cjtoSeg[j].ptoOrigen=0)and(Seg.cjtoSeg[j].ptoDestino=0)or(j=26)) do
            begin
              writeln(archTxt,Seg.cjtoSeg[j].ptoOrigen:5,Seg.cjtoSeg[j].ptoDestino:10);
              j:=j+1;
          end;
          writeln(archTxt,'           ');
        end;
        close(archTxt);
        close(archivoC);
        close(archivoS);
      end
      else  begin
        writeln('no existe binario Segmentos'); delay(500); exit;
      end
    end
    else begin
      writeln('no existe binario Constelacion'); delay(500); exit;
    end
  end
  else    begin
    cox:=0;
    coy:=0;
    assign(archV,'Cnstlcns.txt');
    mAp:='r';
    AbrirText(archV,mAp);
    mAp:='w';
    AbrirText(archTxt,mAp);
    while not eof(archV) do  begin
      readln(archV,ncons);
      writeln(archTxt,'Constelacion:',ncons:30);
      writeln(archTxt,'Nro.Orden      Nombre de la Estrella     Coord X   Coord Y');
      readln(archV,cox,coy,nor,nest);
      i:=1;
      while not ((nor=0)or (i = 26))  do begin
        writeln(archTxt,nor:5,nest:30,cox:10,coy:12);
        readln(archV,cox,coy,nor,nest);
        i:=i+1;
      end;
      read(archV,ptoor,ptodes);
      j:=1;
      writeln(archTxt,'Pto.Origen    Pto.Destino');
      while not((ptoor=0) and(ptodes=0)or(j =26)) do begin
        writeln(archTxt,ptoor:5,ptodes:10);
        j:=j+1;
        readln(archV,ptoor,ptodes);
      end;
      writeln(archTxt,'           ');
    end;
    close(archTxt);
    close(archV);
  end;
end;
{ejecuta una accion segun resultado del menu principal}
procedure opcion;
var
  opc: byte;
  CnstStar: tarcCnstStar;
  CnstSeg: tarcCnstSeg;

begin
  repeat
    ClrScr;
    opc:=menuprin;
    case opc of
      1:grabar(CnstStar,CnstSeg);
      2:actualizar(CnstStar,CnstSeg);
      3:leer(CnstStar,CnstSeg);
      4:writeln('Fin del programa');
    end;
  until (opc = 4);
end;

{programa principal}
Begin
  ClrScr;
  opcion;
  delay(500);
end.
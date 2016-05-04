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

{ var
 CnstStar: tarcCnstStar;
 rCnstStar: tregCnstStar;
 CnstSeg: tarcCnstSeg;
 rCnstSeg: tregCnstSeg;}

 function menuprin:byte;
  var
  op : byte;
  begin
   repeat
    gotoxy(21,20); writeln('Elija una opcion');
    gotoxy(21,11); writeln(' 1.   Crear archivos Binarios de Constelaciones ');
    gotoxy(21,13); writeln(' 2.   Agregar Nueva Constelacion ');
    gotoxy(21,15); writeln(' 3.   Consultar Archivo de Constelaciones ');
    gotoxy(21,17); writeln(' 4.   Salir de la Aplicacion  ');
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
    gotoXY (x1,y2+1);
    write ('È');
    for l:=(x1+1) to (x2-1) do
     write ('Í');
     write ('¼');
     gotoXY (x1+1,y1+1);
   end;
 { FALTAN
 procedure AbrirBinStar
 procedure AbrirTex
 procedure selec (que llama a menuprin, y segun el resultado,
 en el case que tiene se hace uno u otra opcion, llamando
 a los procedimientos necesarios
 }
 procedure act(var tarcCnstStar1:tarcCnstStar;var tarcCnstSeg1:tarcCnstSeg);
 var
 archivotxt :text;
 nomtxt,nombinC,nombinS: string;
 nocons, nest:str30;
 d,r,c,z,nor,ptoor,ptodes: byte;
 cox,coy: word;
 tregCnstStar1:tregCnstStar;
 tregCnstSeg1:tregCnstSeg;
  begin
   writeln('Archivos a actualizar a partir de un archivo.txt');
   writeln('Ingrese nombre del archivo de texto: ');
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
         { begin
           c:=c+1;
           {writeln(cox);writeln(coy);writeln(nor);writeln(nest);write('  ');
          } delay(100);
           readln(archivotxt,cox,coy,nor,nest);

write(cox:2);write('  ');write(coy:2);write('  ');
write(nor:2);write(' ');write(nest);writeln;

           tregCnstStar1.cjtoStar[c].coordX:=cox;
           tregCnstStar1.cjtoStar[c].coordY:=coy;
           tregCnstStar1.cjtoStar[c].nroOrden:=nor;
           tregCnstStar1.cjtoStar[c].nomStar:=nest;
           write(tarcCnstStar1,tregCnstStar1);
         { end }
           until nest=' *';
         { until ((cox=0) and (coy=0) and (nor=0) and (nest='*'));}
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
   nomtxt:=' ';nombinC:=' ';nombinS:=' ';nocons:= ' ';nest:= ' ';
   cox:=0; coy:=0; d:=0;r:=0;c:=0;z:=0;nor:=0;ptoor:=0;ptodes:=0;

   writeln('Archivos a Crear a partir de un archivo.txt');
   writeln('Ingrese nombre del archivo de texto nombre.txt: ');
   readln(nomtxt);
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
      {assign(archivotxt,'Cnstlcns.txt');
   reset(archivotxt);
   assign(tarcCnstStar1,'C.dat');
   rewrite(tarcCnstStar1);
   reset(tarcCnstStar1);
   assign(tarcCnstSeg1,'S.dat');
   rewrite(tarcCnstSeg1);
   reset(tarcCnstSeg1);}
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
         { begin
           c:=c+1;
           {writeln(cox);writeln(coy);writeln(nor);writeln(nest);write('  ');
          } delay(100);
           readln(archivotxt,cox,coy,nor,nest);

write(cox:2);write('  ');write(coy:2);write('  ');
write(nor:2);write(' ');write(nest);writeln;

           tregCnstStar1.cjtoStar[c].coordX:=cox;
           tregCnstStar1.cjtoStar[c].coordY:=coy;
           tregCnstStar1.cjtoStar[c].nroOrden:=nor;
           tregCnstStar1.cjtoStar[c].nomStar:=nest;
           write(tarcCnstStar1,tregCnstStar1);
         { end }
           until nest=' *';
         { until ((cox=0) and (coy=0) and (nor=0) and (nest='*'));}
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
  archivo1,archivotxt :text;
  nomtxt,nombinC,nombinS: string;
  nocons, nest:str30;
  p,opc,d,r,c,z,nor,ptoor,ptodes: byte;
  cox,coy: word;
  tregCnstStar1:tregCnstStar;
  tregCnstSeg1:tregCnstSeg;

  begin
  p:=0;
   writeln('Archivo.txt a Crear a partir de archivos');
   writeln('selecione una opcion valida');
   writeln('1. Archivos binarios Constelacion y Estrella ');
   writeln('2. Archivo de texto  Cnstlcns.txt');
   readln(opc);
   if opc = 1 then
   begin
    writeln('Ingrese nombre del archivo de texto: ');
    readln(nomtxt);
    write ('Ingrese nombre del archivo binario Constelacion :');
    readln(nombinC);
    write ('Ingrese nombre del archivo binario Segmentos :');
    readln(nombinS);
    assign(archivotxt,nomtxt+'.txt');
    rewrite(archivotxt);
   { reset(archivotxt);}
    assign(tarcCnstStar1,nombinC+'.dat');
    reset(tarcCnstStar1);
    assign(tarcCnstSeg1,nombinS+'.dat');
    reset(tarcCnstSeg1);


      { for p:=1 to 3 do
       begin
       p:= p +1;}

        c:=0;
        z:=0;
        nocons:= tregCnstStar1.nomCnstlcn;
        write(nocons); delay(1000);
        writeln(archivotxt,nocons);
      {  repeat
          begin}
           c:=c+1;
           {readln(tarcCnstStar,tregCnstStar);}
           cox:=tregCnstStar1.cjtoStar[c].coordX;
           coy:=tregCnstStar1.cjtoStar[c].coordY;
           nor:=tregCnstStar1.cjtoStar[c].nroOrden;
           nest:=tregCnstStar1.cjtoStar[c].nomStar;
           write(cox);write(' ');write(coy); write(' ');write(nor);
           write(' ');writeln(nest);delay(2000);
           writeln(archivotxt,cox,coy,nor,nest);
        {  end;
          until nest=' *';
          repeat
           begin
            z:=z+1;
           { read(tarcCnstSeg,tregCnstSeg)  }
            ptoor:=tregCnstSeg1.cjtoSeg[z].ptoOrigen;
            ptodes:=tregCnstSeg1.cjtoSeg[z].ptoDestino;
            writeln(archivotxt,ptoor,ptodes);
          { end;
          until ((ptoor=0) and (ptodes=0));
         end;}
      close(tarcCnstSeg1);
      close(tarcCnstStar1);
      close(archivotxt);
      end
    else
     begin
      write('ingrese el nombre del archivo de texto a crear:');
      readln(nomtxt);
      assign(archivotxt,'Cnstlcns.txt');
      reset(archivotxt);
      assign(archivo1,nomtxt+'.txt');
      rewrite(archivo1);
      while not eoln(archivotxt) do
       begin
        c:=0;
        z:=0;
        readln(archivotxt,nocons);
        write(archivo1,nocons);
        repeat
          begin
           readln(archivotxt,cox,coy,nor,nest);
           write(archivo1,cox);
           write(archivo1,coy);
           write(archivo1,nor);
           writeln(archivo1,nest);
          end
          until nest=' *';
          repeat
           begin
            readln(archivotxt,ptoor,ptodes);
            write(archivo1,ptoor);
            writeln(archivo1,ptodes);
           end;
          until ((ptoor=0) and (ptodes=0));
         end;
       close(archivo1);
       close(archivotxt);
      end;

   end;

 procedure opcion;
 var
 opc: byte;
 {Cons : tarcConstStar;
 RCons: tregCnstStar;
 Seg:tarcCnstSeg;
 RSeg:tregConstSeg; }
 Cons: tarcCnstStar;
 {rCnstStar: tregCnstStar; }
 Seg: tarcCnstSeg;
 {rCnstSeg: tregCnstSeg;}
 begin
 {repeat}
  opc:=menuprin;
  case opc of

    1: cop(Cons,Seg);
    2: act(Cons,Seg);
    3: crea(Cons,Seg);
    else
    writeln(' Fin del Programa');
    {assign(Cons);
    assign(Seg);
    close(Cons);
    close(Seg);  }
   {until (opc = 4);}
 end;
end;
 {procedure inicializar(var tCnStar:tarcCnstStar;var tCnSeg:tarcCnstSeg);
 var
  rtCnStar:tregCnstStar;
  rtCnSeg:tregCnstSeg;
  c:byte;
 begin
 c:=0;
  for c:= 1 to 25 do
   begin
    rtCnStar.cjtoStar[c].coordX:=0;
    rtCnStar.cjtoStar[c].coordY:=0;
    rtCnStar.cjtoStar[c].nroOrden:=0;
    rtCnStar.cjtoStar[c].nomStar:=0;
    rtCnSeg.cjtoSeg[z].ptoOrigen:=0;
    rtCnSeg.cjtoSeg[z].ptoDestino:=0;
    tCnStar.nomCnstlcn:=' ';
    tCnSeg.nomCnsSeg:=' ';
   end;

 end; }

Begin
 ClrScr;
 opcion;
 delay(1500);
end.
   { comentarios
   var
archivo,archivo1: text;
nom: string;
num: byte;
   nom:='w';
 opc:=menuprin;
 writeln('            ',opc);
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
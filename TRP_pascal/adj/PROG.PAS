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

 var
 CnstStar: tarcCnstStar;
 rCnstStar: tregCnstStar;
 CnstSeg: tarcCnstSeg;
 rCnstSeg: tregCnstSeg;

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
    write ('�');
    for i:=(x1+1) to (x2-1) do
     write ('�');
     writeln ('�');
    for j:=(y1+1) to y2 do
     begin
      gotoXY (x2,j);
      writeln ('�');
     end;
    for k:=(y1+1) to y2 do
     begin
      gotoXY (x1,k);
      writeln ('�');
     end;
    gotoXY (x1,y2+1);
    write ('�');
    for l:=(x1+1) to (x2-1) do
     write ('�');
     write ('�');
     gotoXY (x1+1,y1+1);
   end;

 procedure act;
 var
 archivotxt :text;
 nomtxt,nombinC,nombinS: string;
 nocons, nest:str30;
 d,r,c,z,nor,ptoor,ptodes: byte;
 cox,coy: word;
  begin
   writeln('Archivos a actualizar a partir de un archivo.txt');
   writeln('Ingrese nombre del archivo de texto: ');
   readln(nomtxt);
   write ('Ingrese nombre del archivo binario Constelacion :');
   readln(nombinC);
   write ('Ingrese nombre del archivo binario Segmentos :');
   readln(nombinS);
   assign(archivotxt,'nomtxt.txt');
   reset(archivotxt);
   assign(tarcCnstStar,'nombinC.dat');
   reset(tarcCnstStar);
   assign(tarcCnstSeg,'nombinS.dat');
   reset(tarcCnstSeg);
   seek(tarcCnstStar,filesize(tarcCnstStar));
   seek(tarcCnstSeg,filesize(tarcCnstSeg));
      while not eoln(archivotxt) do
       begin
        c:=0;
        z:=0;
        readln(archivotxt,nocons);
        tarcCnstStar.nomCnstlcn:=nocons;
        repeat
          begin
           c:=c+1;
           readln(archivotxt,cox,coy,nor,nest);
           tregCnstStar.cjtoStar[c].coordX:=cox;
           tregCnstStar.cjtoStar[c].coordY:=coy;
           tregCnstStar.cjtoStar[c].nroOrden:=nor;
           tregCnstStar.cjtoStar[c].nomStar:=nest;
           write(tarcCnstStar,tregCnstStar);
          end;
          until ((cox=0) and (coy=0) and (nor=0) and (nest='*'));
          repeat
           begin
            z:=z+1;
            readln(archivotxt,ptoor,ptodes);
            tregCnstSeg.cjtoSeg[z].ptoOrigen:=ptoor;
            tregCnstSeg.cjtoSeg[z].ptoDestino:=ptodes;
            write(tarcCnstSeg,tregCnstSeg);
           end;
          until ((ptoor=0) and (ptodes=0));
         end;
      close(tarcCnstSeg);
      close(tarcCnstStar);
      close(archivotxt);
    end;
    {else;}
  end;

 procedure cop;
  var
  archivotxt :text;
  nomtxt,nombinC,nombinS: string;
  nocons, nest:str30;
  d,r,c,z,nor,ptoor,ptodes: byte;
  cox,coy: word;
  begin
   writeln('Archivos a Crear a partir de un archivo.txt');
   writeln('Ingrese nombre del archivo de texto: ');
   readln(nomtxt);
   write ('Ingrese nombre del archivo binario Constelacion :');
   readln(nombinC);
   write ('Ingrese nombre del archivo binario Segmentos :');
   readln(nombinS);
   assign(archivotxt,'nomtxt.txt');
   reset(archivotxt);
   assign(tarcCnstStar,'nombinC.dat');
   reset(tarcCnstStar);
   assign(tarcCnstSeg,'nombinS.dat');
   reset(tarcCnstSeg);
      while not eoln(archivotxt) do
       begin
        c:=0;
        z:=0;
        readln(archivotxt,nocons);
        tarcCnstStar.nomCnstlcn:=nocons;
        repeat
          begin
           c:=c+1;
           readln(archivotxt,cox,coy,nor,nest);
           tregCnstStar.cjtoStar[c].coordX:=cox;
           tregCnstStar.cjtoStar[c].coordY:=coy;
           tregCnstStar.cjtoStar[c].nroOrden:=nor;
           tregCnstStar.cjtoStar[c].nomStar:=nest;
           write(tarcCnstStar,tregCnstStar);
          end;
          until ((cox=0) and (coy=0) and (nor=0) and (nest='*'));
          repeat
           begin
            z:=z+1;
            readln(archivotxt,ptoor,ptodes);
            tregCnstSeg.cjtoSeg[z].ptoOrigen:=ptoor;
            tregCnstSeg.cjtoSeg[z].ptoDestino:=ptodes;
            write(tarcCnstSeg,tregCnstSeg);
           end;
          until ((ptoor=0) and (ptodes=0));
         end;
      close(tarcCnstSeg);
      close(tarcCnstStar);
      close(archivotxt);
    end;
  end;

 procedure crea;
  var
  archivotxt :text;
  nomtxt,nombinC,nombinS: string;
  nocons, nest:str30;
  opc,d,r,c,z,nor,ptoor,ptodes: byte;
  cox,coy: word;
  begin
   writeln('Archivo.txt a Crear a partir de un archivos');
   writeln('selecione una opcion valida');
   writeln('1. Archivos binarios Constelacion y Estrella ');
   writeln('2. Archivo de texto  Cnstlcns.txt');
   readln(opc);
   if opc:=1 then
   begin
    writeln('Ingrese nombre del archivo de texto: ');
    readln(nomtxt);
    write ('Ingrese nombre del archivo binario Constelacion :');
    readln(nombinC);
    write ('Ingrese nombre del archivo binario Segmentos :');
    readln(nombinS);
    assign(archivotxt,'nomtxt.txt');
    reset(archivotxt);
    assign(tarcCnstStar,'nombinC.dat');
    reset(tarcCnstStar);
    assign(tarcCnstSeg,'nombinS.dat');
    reset(tarcCnstSeg);
      while not eoln(archivotxt) do
       begin
        c:=0;
        z:=0;
        nocons:= tarcCnstStar.nomCnstlcn;
        writeln(archivotxt,nocons);
        repeat
          begin
           c:=c+1;
           {readln(tarcCnstStar,tregCnstStar);}
           cox:=tregCnstStar.cjtoStar[c].coordX;
           coy:=tregCnstStar.cjtoStar[c].coordY;
           nor:=tregCnstStar.cjtoStar[c].nroOrden;
           nest:=tregCnstStar.cjtoStar[c].nomStar;
           writeln(archivotxt,cox,coy,nor,nest);
          end;
          until ((cox=0) and (coy=0) and (nor=0) and (nest='*'));
          repeat
           begin
            z:=z+1;
            readln(archivotxt,ptoor,ptodes);
            tregCnstSeg.cjtoSeg[z].ptoOrigen:=ptoor;
            tregCnstSeg.cjtoSeg[z].ptoDestino:=ptodes;
            write(tarcCnstSeg,tregCnstSeg);
           end;
          until ((ptoor=0) and (ptodes=0));
         end;
      close(tarcCnstSeg);
      close(tarcCnstStar);
      close(archivotxt);
    end;
  end;

 {procedure opcion3;
 var
 selec1:byte;
   nombretxt: str30;
   nombrebin: str30;
   texto:string;
   archivotxt:text;
   d,r,x,y:byte;
   pvezimp_tvstar,pvezimp_tvseg:boolean;
  begin
   Writeln;
   write ('Ingrese origen para convertir a archivo.txt : ');
   writeln;
   writeln;
   writeln ('(1) Archivo de Texto');
   Writeln;
   writeln ('(2) Archivo archivo binario');
   readln (selec1);
   clrscr;
   if selec1=1 then
    begin
     repeat
      write ('Ingrese nombre del archivo de texto: ');
      read(nombretxt);
      clrscr;
      assign(archivotxt,'nombretxt.txt');
      reset(archivotxt);
      while (not eoln(archivotxt)) do
       begin
        readln(archivotxt,texto);
        writeln(texto);
       end;
      close(archivotxt);
      readkey;
    end
   else
    begin
     repeat
      write ('Ingrese el nombre del archivo binario: ');
      readln(nombrebin);
      clrscr;
      assign(Cnstlcns,'nombrebin.dat');
      reset(Cnstlcns);
      while (not eof(Cnstlcns)) do
       begin
        clrscr;
        d:=1;
        r:=1;
        pvezimp_tvstar:=true;
        pvezimp_tvseg:=true;
        read(Cnstlcns,rCnstlcn);
        write('Constelacion: ');
        writeln(rCnstlcn.nomcnstlcn);
        repeat
         if pvezimp_tvstar then
         begin
          gotoXY(1,d+1);write('Nro. Orden');
          gotoXY(15,d+1);write('Nombre de la estrella');
          gotoXY(40,d+1);write('Coord X');
          gotoXY(50,d+1);write('Coord Y');
          pvezimp_tvstar:=false;
         end
         else;
         gotoXY(1,d+2);writeln(rCnstlcn.cjtostar[d].numord);
         gotoXY(15,d+2);writeln(rCnstlcn.cjtostar[d].nomstar);
         gotoXY(40,d+2);writeln(rCnstlcn.cjtostar[d].coordX);
         gotoXY(50,d+2);writeln(rCnstlcn.cjtostar[d].coordY);
         d:=d+1
        until(rCnstlcn.cjtostar[d].numord=0)and(rCnstlcn.cjtostar[d].nomstar='*') and (rCnstlcn.cjtostar[d].coordX=0) and (rCnstlcn.cjtostar[d].coordY=0);
       repeat
        if pvezimp_tvseg then
         begin
          gotoXY(0,d+2);write('Segmentos');
          gotoXY(2,d+4);write('Pto. Origen');
          gotoXY(15,d+4);write('Pto. Destino');
          pvezimp_tvseg:=false;
         end
        else;
         gotoXY(1,(d+4)+r);writeln(rCnstlcn.cjtoseg[r].ptoOrigen);
         gotoXY(15,(d+4)+r);writeln(rCnstlcn.cjtoseg[r].ptoDestino);
         r:=r+1
       until(rCnstlcn.cjtoseg[r].ptoOrigen=0)and(rCnstlcn.cjtoseg[r].ptoDestino=0);
       readkey
      end;
      readkey;
     end;
end;}

     { begin
  write('ingrese el nombre del archivo de texto:'
    assign(archivo,'.txt');
    reset(archivo);
    readln(archivo,nom,num);
    assign(archivo1,'2.txt');
    rewrite(archivo1);
    write(archivo1,nom,num);
    close(archivo1);
    end;}
Begin
 ClrScr;
 {nom:='w';
 opc:=menuprin;
 writeln('            ',opc);
 ventana;}
 {append(archivo1);
 writeln(archivo1,'segundo');
 close(archivo1);}

 delay(1500);
end.

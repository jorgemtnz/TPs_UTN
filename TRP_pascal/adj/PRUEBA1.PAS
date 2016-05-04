Program pases;
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
var
archivotxt1, archivotxt: Text;
archivoC:tarcCnstStar;
archivoS:tarcCnstSeg;
Con:tregCnstStar;
Seg:tregCnstSeg;
caracter:char;
num1,i,j:byte;
Begin
 ClrScr;
 caracter:=' ';

 assign(archivoC,'2.dat');
 rewrite(archivoC);
 assign(archivoS,'3.dat');
 rewrite(archivoS);

 writeln;
 assign(archivotxt,'Cnstlcns.txt');
 reset(archivotxt);
 i:=0;

 while not eof(archivotxt) do begin
  i:=0;
  readln(archivotxt,Con.nomCnstlcn);
  Seg.nomCnsSeg:=Con.nomCnstlcn;
  writeln('constelacion:i',i,Con.nomCnstlcn:50);

  repeat      begin
   readln(archivotxt,Con.cjtoStar[i].coordX,Con.cjtoStar[i].coordY,Con.cjtoStar[i].nroOrden,caracter,Con.cjtoStar[i].nomStar);
   writeln('i ',i,Con.cjtoStar[i].coordX:5,Con.cjtoStar[i].coordY:10,Con.cjtoStar[i].nroOrden:13,Con.cjtoStar[i].nomStar:30);
   i:=i+1;
   writeln(' i  ',i);
   delay(500)
   end;
  until ((Con.cjtoStar[i-1].nroOrden =0)or (i = 25));

   writeln('total de estrellas: ',i-1);
   write(archivoC,Con);
   j:=0;

  repeat  begin
   writeln(' j:  ',j);
   readln(archivotxt,Seg.cjtoSeg[j].ptoOrigen,Seg.cjtoSeg[j].ptoDestino);
   writeln(Seg.cjtoSeg[j].ptoOrigen:5,Seg.cjtoSeg[j].ptoDestino:10);
   delay(500);
   j:=j+1;
   if j=26 then exit;
   end;
  until ((Seg.cjtoSeg[j-1].ptoOrigen =0) and(Seg.cjtoSeg[j-1].ptoDestino =0));

   writeln('total de segmentos por linea: ',j-1);
   write(archivoS,Seg);


 end;
 writeln('los valores estan en la variable registro ');

 close(archivoC);
 close(archivoS);
 reset(archivoC);
 reset(archivoS);
 writeln;writeln;writeln;
 delay(500);

assign(archivotxt1,'2.txt');
rewrite(archivotxt1);
close(archivotxt1);
append(archivotxt1);
num1:=0;
i:=0;
j:=0;
writeln;
writeln('COPIA DE ARCHIVOS BINARIOS A TXT');
writeln(' tama¤o de registro archivoC estrellas ',filesize(archivoC));
writeln(' tama¤o de registro archivoS segmentos ',filesize(archivoS));

while ((not eof(archivoC)) or(num1 = 50)) do  begin
 read(archivoC,Con);
 writeln(archivotxt1,'contelacion:',Con.nomCnstlcn:50);
 writeln('Constelacion:',Con.nomCnstlcn:50);
 i:=0;

 while not ((Con.cjtoStar[i].nroOrden =0)or (i = 25))  do begin
  writeln('i: ',i);
writeln(archivotxt1,Con.cjtoStar[i].coordX:5,Con.cjtoStar[i].coordY:10,Con.cjtoStar[i].nroOrden:13,Con.cjtoStar[i].nomStar:30);
  writeln(' valores de C pasados al txt :',i);
writeln(Con.cjtoStar[i].coordX:5,Con.cjtoStar[i].coordY:10,Con.cjtoStar[i].nroOrden:13,Con.cjtoStar[i].nomStar:20);
  delay(500);
  i:=i+1;
  {read(archivoC,Con); }
 end;

 read(archivoS,Seg);
 j:=0;
 while not ((Seg.cjtoSeg[j].ptoOrigen =0) and(Seg.cjtoSeg[j].ptoDestino =0)) do begin
  writeln('j  ',j);
  writeln(archivotxt1,Seg.cjtoSeg[j].ptoOrigen:5,Seg.cjtoSeg[j].ptoDestino:10);
  writeln('valores pasados al txt de S');
  writeln(Seg.cjtoSeg[j].ptoOrigen:5,Seg.cjtoSeg[j].ptoDestino:10);
  j:=j+1;
  delay(500);
  {read(archivoS,Seg); }
 end;

num1:= num1 +1;
end;

close(archivotxt1);
close(archivoC);
close(archivoS);

delay(2000);
end.
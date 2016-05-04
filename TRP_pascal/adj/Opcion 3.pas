program astar;
uses crt,sysUtils;
const
     cant_star=25;
type
    str20=string[20];
    str30=string[30];
    tregstar=record
                  nomstar:str20;
                  coordx,
                  coordy:word;
                  numord:byte;
                  end;
    tvstar=array [1..cant_star] of tregstar;
    tregseg=record
                ptoorigen,
                ptodestino:byte;
                end;
    tvseg=array [1..cant_star] of tregseg;
    tregcnstlcn=record
                      nomcnstlcn:str30;
                      cjtostar:tvstar;
                      cjtoseg:tvseg;
                      end;
    tarccnstlcn= file of tregcnstlcn;
var
   selec1:byte;
   nombretxt: str30;
   nombrebin: str30;
   texto:string;
   archivotxt:text;
   Cnstlcns:tarcCnstlcn;
   rCnstlcn:tregcnstlcn;
   d,r,x,y:byte;
   pvezimp_tvstar,pvezimp_tvseg:boolean;


begin
     writeln ('(1) Archivo de Texto');
     Writeln;
     writeln ('(2) Archivo archivo binario');
     Writeln;
     Writeln;
     Writeln;
     write ('Ingrese que tipo de archivo desea visualizar: ');
     readln (selec1);
     clrscr;
     if selec1=1 then
                 begin
                      repeat
                            write ('Ingrese el nombre del archivo de texto: ');
                            read(nombretxt);
                            clrscr;
                      until (fileexists('c:\'+nombretxt+'.txt'));
                 assign(archivotxt,'c:\'+nombretxt+'.txt');
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
                          until (fileexists('c:\'+nombrebin+'.dat'));
                     assign(Cnstlcns,'c:\'+nombrebin+'.dat');
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
                                                                                   gotoXY(1,d+1);
                                                                                   write('Nro. Orden');
                                                                                   gotoXY(15,d+1);
                                                                                   write('Nombre de la estrella');
                                                                                   gotoXY(40,d+1);
                                                                                   write('Coord X');
                                                                                   gotoXY(50,d+1);
                                                                                   write('Coord Y');
                                                                                   pvezimp_tvstar:=false;
                                                                              end
                                                                               else;
                                                      gotoXY(1,d+2);
                                                      writeln(rCnstlcn.cjtostar[d].numord);
                                                      gotoXY(15,d+2);
                                                      writeln(rCnstlcn.cjtostar[d].nomstar);
                                                      gotoXY(40,d+2);
                                                      writeln(rCnstlcn.cjtostar[d].coordX);
                                                      gotoXY(50,d+2);
                                                      writeln(rCnstlcn.cjtostar[d].coordY);
                                                      d:=d+1
                                                      until (rCnstlcn.cjtostar[d].numord=0) and (rCnstlcn.cjtostar[d].nomstar='*') and (rCnstlcn.cjtostar[d].coordX=0) and (rCnstlcn.cjtostar[d].coordY=0);

                                                      repeat
                                                            if pvezimp_tvseg then
                                                                              begin
                                                                                   gotoXY(0,d+2);
                                                                                   write('Segmentos');
                                                                                   gotoXY(2,d+4);
                                                                                   write('Pto. Origen');
                                                                                   gotoXY(15,d+4);
                                                                                   write('Pto. Destino');
                                                                                   pvezimp_tvseg:=false;
                                                                              end
                                                                               else;
                                                      gotoXY(1,(d+4)+r);
                                                      writeln(rCnstlcn.cjtoseg[r].ptoOrigen);
                                                      gotoXY(15,(d+4)+r);
                                                      writeln(rCnstlcn.cjtoseg[r].ptoDestino);
                                                      r:=r+1
                                                      until (rCnstlcn.cjtoseg[r].ptoOrigen=0) and (rCnstlcn.cjtoseg[r].ptoDestino=0);
                                                      readkey

                                                 end;
                                                 readkey;
                      end;
end.

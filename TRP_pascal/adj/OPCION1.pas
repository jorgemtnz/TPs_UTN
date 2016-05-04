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
   d,r,c,z:byte;
   pvezimp_tvstar,pvezimp_tvseg:boolean;
   nocons:str30;
   cox,coy:word;
   nor:byte;
   nest:str20;
   ptoor,ptodes:byte;
   opcion1:char;


begin
     write ('Ingrese el nombre del archivo binario que decea crear: ');
     readln (nombrebin);
     if fileexists('c:\'+nombrebin+'.dat') then
                                           begin
                                                write('El archivo ya existe, desea reemplazarlo? S/N : ');
                                                readln(opcion1);
                                                if (opcion1='S') or (opcion1='s') then
                                                                                  begin
                                                                                       write('Ingrese el nombre del archivo de texto que contiene el conjunto de constelaciones: ');
                                                                                       readln(nombretxt);
                                                                                       if fileexists('c:\'+nombretxt+'.txt') then
                                                                                                                             begin
                                                                                                                                  assign(cnstlcns,'c:\'+nombrebin+'.dat');
                                                                                                                                  rewrite(cnstlcns);
                                                                                                                             end
                                                                                                                             else;
                                                                                   end
                                                                                   else;
                                           end
                                           else
                                               begin
                                                    write('El archivo no existe, desea crearlo? S/N : ');
                                                    readln(opcion1);
                                                    if (opcion1='S') or (opcion1='s') then
                                                                                  begin
                                                                                       write('Ingrese el nombre del archivo de texto que contiene el conjunto de constelaciones: ');
                                                                                       readln(nombretxt);
                                                                                       if fileexists('c:\'+nombretxt+'.txt') then
                                                                                                                             begin
                                                                                                                                  assign(cnstlcns,'c:\'+nombrebin+'.dat');
                                                                                                                                  rewrite(cnstlcns);
                                                                                                                             end
                                                                                                                             else;
                                                                                   end
                                                                                   else;
                                               end;
end.

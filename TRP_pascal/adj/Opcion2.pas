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


begin
repeat
      write ('Ingrese el nombre del archivo de texto: ');
      readln(nombretxt);
until (fileexists('c:\'+nombretxt+'.txt') or (nombretxt='0'));
if nombretxt<>'0' then
   begin
        repeat
              write ('Ingrese el nombre del archivo de binario que decea actualizar: ');
              readln(nombrebin);
        until (fileexists('c:\'+nombrebin+'.dat') or (nombrebin='0'));
   end
   else;
if nombretxt<>'0' then
   begin
        assign(archivotxt,'c:\'+nombretxt+'.txt');
        reset(archivotxt);
        assign(cnstlcns,'c:\'+nombrebin+'.dat');
        reset(cnstlcns);
        seek(cnstlcns,filesize(cnstlcns));
        while not eoln(archivotxt) do
                                begin
                                     c:=0;
                                     z:=0;
                                     readln(archivotxt,nocons);
                                     //read(cnstlcns,rCnstlcn);//
                                     rCnstlcn.nomcnstlcn:=nocons;
                                     //write(cnstlcns,rCnstlcn);//
                                repeat
                                      begin
                                      c:=c+1;
                                      readln(archivotxt,cox,coy,nor,nest);
                                      rCnstlcn.cjtostar[c].coordX:=cox;
                                      rCnstlcn.cjtostar[c].coordY:=coy;
                                      rCnstlcn.cjtostar[c].numord:=nor;
                                      rCnstlcn.cjtostar[c].nomstar:=nest;
                                      write(cnstlcns,rCnstlcn);
                                      end;
                                until ((cox=0) and (coy=0) and (nor=0) and (nest='*'));
                                repeat
                                      begin
                                      z:=z+1;
                                      readln(archivotxt,ptoor,ptodes);
                                      rCnstlcn.cjtoseg[z].ptoorigen:=ptoor;
                                      rCnstlcn.cjtoseg[z].ptodestino:=ptodes;
                                      //write(cnstlcns,rCnstlcn);//
                                      end;
                                until ((ptoor=0) and (ptodes=0));
                                //write(cnstlcns,rCnstlcn);//

                                end;


      close(cnstlcns);
      close(archivotxt);
end
else;




end.

Program pases;
uses
crt;
type
treg = record
 nom:string;
 num: word;
 end;
 arch =  file of treg;
var
archivotxt1, archivotxt: Text;
archivo1,archivo2: arch;
rBin:treg;
caracter:char;
num1,i:byte;
Begin
ClrScr;
 caracter:=' ';

assign(archivo1,'2.dat');
rewrite(archivo1);
{rBin.nom:='*'; rBin.num:=0;
write(archivo1,rBin);
close(archivo1);
reset(archivo1);}
writeln;
assign(archivotxt,'1.txt');
reset(archivotxt);
 i:=0;
writeln(' nombre:[ ',rBin.nom,']  valores antes de leer del txt num:',rBin.num);

while not eof(archivotxt) do begin

readln(archivotxt,rBin.num,rBin.nom);
write(archivo1,rBin);
 writeln(' nombre:[ ',rBin.nom,'] valores leidos del txt num:',rBin.num);
 delay(1000);
{ num1:=filepos(archivo1)+1;
   seek(archivo1,num1); }
   i:=i+1;
 end;
writeln('los valores estan en la variable registro ');

close(archivo1);
reset(archivo1);
writeln;writeln;writeln;
 delay(5000);

assign(archivotxt1,'2.txt');
rewrite(archivotxt1);
close(archivotxt1);
append(archivotxt1);
num1:=0;
while not eof(archivo1) do  begin
read(archivo1,rBin);
writeln(archivotxt1,rBin.nom:10,rBin.num:20);
writeln(' nombre:[ ',rBin.nom,']  valores pasados al txt num:',rBin.num);
delay(1000);
num1:= num1 +1;
               end;
close(archivotxt1);
close(archivo1);

delay(2000);
end.
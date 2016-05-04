program astar;
uses crt;
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
        begin
                write ('Í');
        end;
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
        begin
                write ('Í');
        end;
write ('¼');
gotoXY (x1+1,y1+1);

end;

procedure principal (var n:byte);

begin
gotoXY (21,11);
        writeln ('(1) Crear archivo binario de constelaciones');
        gotoXY (21,13);
        writeln ('(2) Agregar nueva constelacion');
        gotoXY (21,15);
        writeln ('(3) Consultar arcivo de constelaciones');
        gotoXY (21,17);
        writeln ('(4) Salir de la aplicacion');
        gotoXY (21,24);
        write ('Elija una opcion: ');
        read (n);
        clrscr;
end;

procedure titulo;
begin
clrscr;
        gotoXY (40,5);
        write ('A-Star');
end;
procedure menu(n:byte);
        begin
                if n=1 then begin
                        clrscr;
                        writeln ('Crear archivo binario');
                        readkey;
                        end
                        else
                if n=2 then begin
                        clrscr;
                        writeln ('Agregar constelaciones');
                        readkey;
                        end
                        else
                if n=3 then begin
                        clrscr;
                        writeln ('Consultar archivo de constelaciones');
                        readkey;
                        end
                        else
                if n=4 then begin
                        clrscr;
                        end
                else
                end;

var
        cnstlcns:tarccnstlcn;
        rcnstlcn:tregcnstlcn;
        n:byte;
begin

        repeat
        titulo;
        ventana ;
        principal (n);
        menu (n);
        until (n=1) or (n=2) or (n=3) or (n=4);
END.

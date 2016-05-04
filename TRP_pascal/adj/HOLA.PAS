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
 rCnstSeg: tregCnstSeg;opc: byte;
  procedure menuprin(var op:byte);
 begin
  repeat
  writeln('Seleccione una opcion');
  writeln(' 1.   Crear archivos Binarios de Constelaciones ');
  writeln(' 2.   Agregar Nueva Constelacion ');
  writeln(' 3.   Consultar Archivo de Constelaciones ');
  writeln(' 4.   Salir de la Aplicacion  ');
  readln(op)
  until op<>4;
 end;
begin

 menuprin(opc);
end.

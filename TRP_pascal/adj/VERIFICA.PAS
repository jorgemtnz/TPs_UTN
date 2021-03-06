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
begin
ClrScr;
assign(archivoC,'5.dat');
assign(archivoS,'6.dat');
reset(archivoC);
reset(archivoS);
writeln('tama�o archivoC ',filesize(archivoC));
writeln('tama�o archivoS ',filesize(archivoS));
delay(5000);
end.
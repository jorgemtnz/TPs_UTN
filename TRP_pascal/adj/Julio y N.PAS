
{Programa Principal: INICIO}
Program Constelaciones;

Uses
      Crt, Dos;


Const
CANT_STAR = 25;                 {declaracion de constante Cant_Star}

Type                            {tipo de datos para la constalaciones y estrellas}
STR20= string[20];
STR30= string[30];

{Define los Vecores}
tregStar= record                {registro vecotr de estrella}
          nomStar: str20;
          coordX,
          coordY: word;
          nroOrden: byte;
          end;

tregSeg=  record                {registro vector de segmentos}
          ptoOrigen,
          ptoDestino: Byte;
          end;


{Define la longitud de los Vecores}
tvStar = array [1..cant_star]  of tregStar;   {arreglo de estrellas max 25}
tvSeg  = array [1..cant_star]  of tregSeg;    {arreglo de segmentos max 25}

{Define los Tipos de registros de los Binarios}
tregCnstStar= record                            {Registro const Estrella/ Reg Bin}
               nomCnstlen: str30;
               cjtoStar  : tvStar;
              end;

tregCnstSeg = record                            {Registro const Estrella/ Reg Bin}
               nomCnsSeg: str30;
               cjtoSeg  : tvSeg;
              end;


{Define los tipos de registros de los Binarios}
tarcCnstStar = file of tregCnstStar;
tarcCnstSeg  = file of tregCnstSeg;
{Define el tipo de registros de los Binarios}
ArchText= Text;


Function FileExistBin(var nomLog:tarcCnstStar): Boolean;
     begin
         {$I-}
         reset(nomLog);
         {$I+}
         fileExistBin:= ioresult=0;
     end;{FileExistBin}

Function FileExistBinS(var nomLog:tarcCnstSeg): Boolean;
     begin
         {$I-}
         reset(nomLog);
         {$I+}
         fileExistBins:= ioresult=0;
     end;{FileExistBinS}


Function FileExistText(var nomLog: text): Boolean;
     begin
         {$I-}
         reset(nomLog);
         {$I+}
         fileExistText:= ioresult=0;
     end;{FileExiystText}
{Programa Principal: INICIO}
	 
	 
	 
	 


{Consistencia Binarios 1 }	 
procedure ConsArch (var Archivo:tarcCnstSeg);
 var
 Confirmacion: Char;
 sl: boolean;
 NombArch: STR30;
 ArchTxt :text;
 
 Begin
    sl:=false;
    If FileExistBinS(Archivo)  then
        Begin
              writeln (#10,'>>> El archivo existe <<<',#10);
              repeat
                  write ('求esea reemplazarlo? s=> si || n=> no : ');
                  readln(Confirmacion);
                  Confirmacion:= upcase(Confirmacion);
              Until (Confirmacion='S') or (Confirmacion='N');

              if Confirmacion ='S' then
                 begin
                   repeat
                    writeln;
                    writeln ('Ingrese nombre del archivo de texto que ');
                    write   ('contiene el Conjunto de Constelaciones: ');
                    readln(NombArch);
                    Assign(ArchTxt,NombArch+'.txt');


                    if FileExistText(ArchTxt) then
                      Begin
                       rewrite(Archivo);
                       writeln;
                       writeln('>>> El archivo se ha modificado correctamente <<<');
                       sl:=true;
                      end
                    else
                      Begin
                       writeln;
                       writeln('>>> El nombre del archivo de texto no existe <<<');
                       writeln('>>>            Ingreselo nuevamente          <<<');
                      end
                   until sl=true;
                 end
              else
                 writeln(#10,'>>> El archivo no se ha reemplazado <<<')
               {volver al menu?}
        end
    else
        begin
         writeln(#10);
         writeln ('>>> 耍TENCION!: El nombre del Archivo ingresado no existe <<<');
         repeat
                  writeln;
                  write ('求esea crearlo? s=> si || n=> no : ');
                  readln (Confirmacion);
                  Confirmacion:= upcase(Confirmacion);

         Until (Confirmacion='S') or (Confirmacion='N');

         if Confirmacion ='S' then
            begin
              rewrite(Archivo);
              writeln(#10,'>>> El archivo se ha creado correctamente <<<');
            end
         else
            begin
              writeln;
              writeln('>>> El archivo no se ha creado <<<');
              {volver al menu?}
            end
        end;

End;{ConsArch}

{Consistencia Binarios 2 }
procedure ConsArchS (var Archivo:tarcCnstStar);
 var
 Confirmacion: Char;
 sl: boolean;
 NombArch: STR30;
 ArchTxt :text;

 Begin
    sl:=false;
    If FileExistBin(Archivo)  then
        Begin
              writeln (#10,'>>> El archivo existe <<<',#10);
              repeat
                  write ('求esea reemplazarlo? s=> si || n=> no : ');
                  readln(Confirmacion);
                  Confirmacion:= upcase(Confirmacion);
              Until (Confirmacion='S') or (Confirmacion='N');

              if Confirmacion ='S' then
                 begin
                   repeat
                    writeln;
                    writeln ('Ingrese nombre del archivo de texto que ');
                    write   ('contiene el Conjunto de Constelaciones: ');
                    readln(NombArch);
                    Assign(ArchTxt,NombArch+'.txt');


                    if FileExistText(ArchTxt) then
                      Begin
                       rewrite(Archivo);
                       writeln;
                       writeln('>>> El archivo se ha modificado correctamente <<<');
                       sl:=true;
                      end
                    else
                      Begin
                       writeln;
                       writeln('>>> El nombre del archivo de texto no existe <<<');
                       writeln('>>>            Ingreselo nuevamente          <<<');
                      end
                   until sl=true;
                 end
              else
                 writeln(#10,'>>> El archivo no se ha reemplazado <<<')
               {volver al menu?}
        end
    else
        begin
         writeln(#10);
         writeln ('>>> 耍TENCION!: El nombre del Archivo ingresado no existe <<<');
         repeat
                  writeln;
                  write ('求esea crearlo? s=> si || n=> no : ');
                  readln (Confirmacion);
                  Confirmacion:= upcase(Confirmacion);

         Until (Confirmacion='S') or (Confirmacion='N');

         if Confirmacion ='S' then
            begin
              rewrite(Archivo);
              writeln(#10,'>>> El archivo se ha creado correctamente <<<');
            end
         else
            begin
              writeln;
              writeln('>>> El archivo no se ha creado <<<');
              {volver al menu?}
            end
        end;

End;{ConsArchS}


{Definicion de variables PROGRAMA PRINCIPAL }
var                                            
   CnstStar : tarcCnstStar;
   rCnstStar: tregCnstStar;
   CnstSeg  : tarcCnstSeg;
   rCnstSeg : tregCnstSeg;
   NomStar: string;
   NomSeg: string;



Begin
clrscr;

write ('Ingrese nombre del archivo de estrellas : ');
readln(NomStar);
Assign(CnstStar,nomStar+'.dat');
ConsArchS(cnstStar);


writeln ('Ingrese nombre del archivo de segmentos: ');
readln(NomSeg);
Assign(CnstSeg,nomSeg+'.dat');
ConsArch(CnstSeg);



readln
End.
{Fin Opcion 1}
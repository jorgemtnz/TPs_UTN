{- Jorge Martínez Rodríguez 1408318 K2071  -}

type Nombre = String
type NivelOrientacion = Double
type NivelEmocion = Double
type NivelCultura = Double
type Orientacion = Double
type Emocion = Double
type Cultura = Double
type Atraccion = (Persona -> Persona)
type Persona = (Nombre, NivelOrientacion, NivelEmocion, NivelCultura)
type Parque = (Nombre,[Atraccion])
type Refresco = (Nombre, Orientacion, Emocion, Cultura)
type Criterio = Persona -> Double

isabel::Persona
isabel = ("isabel", 10, 4, 4)
mario::Persona
mario = ("mario", 9, 3, 2)
teresa::Persona
teresa = ("teresa", 8, 4, 3)


personas:: [Persona]
personas = [isabel, mario, teresa]


parques:: [Parque]
parques = [("diversiones",  [montañaRusa 100, caidaLibre 60]),("tematico",  [mundoMaya, cine4D]) ]


{-1-}
atracciones::[Parque] -> [Atraccion]
atracciones parques = foldr ((++).snd) [] parques


{-2-}
montañaRusa::Double -> Atraccion
montañaRusa velocidad (nombre,nivelOrientacion,emocion,cultura) = (nombre,nivelOrientacion-0.15*velocidad,emocion,cultura)
{- disminucion de la persona en un 15% de la velocidad-}

caidaLibre::Double -> Atraccion 
caidaLibre  altura  (nombre,orientacion,nivelEmocion,cultura) = (nombre,orientacion,nivelEmocion+0.20*altura,cultura)


mundoMaya::Atraccion 
mundoMaya (nombre,orientacion,nivelEmocion,nivelCultura) = (nombre,orientacion,nivelEmocion+0.20*nivelEmocion,nivelCultura+0.10*nivelCultura)


cine4D:: Atraccion
cine4D persona = persona


{-3-}
refresco:: Persona -> Persona
refresco (nombre, orientacion, emocion, cultura) = (nombre, 2*orientacion, emocion, cultura)


hidratar::Atraccion -> Persona -> Persona                                          --funcion principal
hidratar atraccion persona = (refresco.atraccion) persona


{-4-}
comparaOrientacion::Double -> Persona ->Bool
comparaOrientacion n (_, orientacion, _, _) = orientacion > n 


entregaLista::Int -> Double -> [Persona] -> [Persona]  
entregaLista m n = take m.filter (comparaOrientacion n. mundoMaya.hidratar (montañaRusa 10))

daNombre:: Persona -> Nombre
daNombre (nombre,_,_,_) = nombre

fueronaMontañaRusayMundoMaya:: Int -> Double -> [Persona] -> [Nombre]               --funcion principal
fueronaMontañaRusayMundoMaya m n personas= map daNombre (entregaLista m n personas)

{-5 -}
paseoAtracciones::Persona -> [Atraccion]->Persona                                   --funcion principal
paseoAtracciones persona lasAtracciones= foldl (\ persona atraccion -> hidratar atraccion persona)  persona lasAtracciones

{-6  -}

esCreciente::Persona -> Criterio ->[Atraccion]->  Bool                              --funcion principal
esCreciente _ _ []  = True
esCreciente persona criterio (cab:cola)  = (criterio persona < (criterio.cab)persona)&&(esCreciente ( cab persona) criterio cola  )

lasuma::Persona -> Double
lasuma (_, orientacion,_, cultura) = orientacion +cultura

niveldeemocion::Persona -> Double
niveldeemocion (_, _, emocion, _)  =   emocion

inversaCultura::Persona -> Double
inversaCultura (_, _, _, cultura)  =   1/cultura 



{-
7) esCreciente:: (Ord b )=> a -> [a -> a]-> (a -> b) -> Bool 
esCreciente isabel niveldeemocion [caidaLibre 60, mundoMaya] 
True
 8)
 lista infinita punto 4
 La funcion fueronaMontañaRusayMundoMaya hace un take m de los m elementos de la lista infinita luego evalua las demas funciones 
 como pasaPorAtracciones de afuera hacia adentro, por lo que no importa que sea infinita la lista, gracias a la evaluación diferida, o sea solo se evalua la expresión que se necesita.
 lista infinita punto 5
Si tomo una lista infinita en algún lugar y le aplico un pliegue por la derecha, en algún momento alcanzaré el inicio de la lista. 
Si embargo, si tomo una lista infinita en algún punto y le aplico un pliegue por la izquierda nunca alcanzaré el final. Sin embargo 
como no hago un take  a esta función  entonces da igual haber usado uno u otro, por lo que en este caso no llegara nunca al final de la lista.
 lista infinita punto 6
 Si la lista de atracciones es infinita, mientras sea creciente no terminará nunca de ejecutarse la funcion esCreciente, osea mientras el criterio 
 que recibe a la persona antes y despues de pasar por una atraccion devuelva True. Sin embargo cuando no  lo sea y de falso automaticamente terminará 
 ya que en esCreciente hay  un && logico  y al haber un falso el resultado final es falso y se corta.
-}

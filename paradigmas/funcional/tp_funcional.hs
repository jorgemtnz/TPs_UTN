type Torta = (Sabor, Harina, Huevos, Calorias)
type Sabor = (Ingrediente, Cantidad)
type Ingrediente = String
type Harina = Integer
type Huevos = Integer
type Calorias = Integer
type Cantidad = Integer
type Decoración = (Ingrediente, Cantidad, Calorias)

cVocal :: [Char] ->Bool
cVocal (x:xs) = elem x ['a','e','i','o','u'] 

esTradicional :: (([Char],Int),Int,Int,Int) -> Bool
esTradicional ((xs,a),b,c,d) 
	| xs == "chocolate" && a >= 2*b = True
	|  xs == "frutilla" && c == 3 = True
	|  xs == "mascarpone" && (a >= 300 && a <=500 ) = True
  	| otherwise = False	
	
esExotica :: (([Char],Int),Int,Int,Int) -> Bool
esExotica ((xs,a),b,c,d)
	| not (esTradicional dato) &&( length xs >3 && cVocal xs ) = True
	| otherwise = False	
	where dato = ((xs,a),b,c,d)
-- si empieza con vocal obligatoriamente no sera Tradicional	
cHarina :: Int -> Int -> Bool
cHarina a b = a < b

-- lo hice suponiendo que al entrar los datos la pregunta es si la primera torta es la mas sana

esMasSana::(([Char],Int),Int,Int,Int) -> (([Char],Int),Int,Int,Int) -> Bool
esMasSana ((xs,a),b,c,d) ((ys,r),o,t,v) 
	| d < v = True
	| d == v && cHarina b o = True
	| otherwise   = False	
	
decorar::(([Char],Int),Int,Int,Int) -> ([Char],Int,Int)-> (([Char],Int),Int,Int,Int)
decorar ((xs,a),b,c,d)	(ys,r,o) =(((xs ++ ['-'] ++ ys),a+r),b,c,d+o)

{-4b. En el paradigma imperativo tendría que escribir muchas mas líneas de código, porque tendria 
que ocuparme de especificar como debe hacer las cosas la máquina, cosa que no me preocupa en el paradigma funcional.
Aquí solo debo decirle lo que quiero que haga con los elementos de la tupla y nada mas. Ni siquiera es obligatorio
escribir la declaracion de tipo de la funcion ya que el Haskell tiene la propiedad de inferencia de tipos
asi que se da cuenta de que tipo se trata cuando lo use. Estas caracteristicas permiten que el codigo sea mas legible, claro
y facil de leer.  -}	
	



	
		
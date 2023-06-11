# Predstaveni
Vazeny pane predsedo, vazena komise, dovolte mi,
abych vam predstavil vysledky svoji diplomove prace.
Vedouci byl pan dekan Jirina, predseda teto komise,
a oponentem pan profesor Surynek.

Prace se zabyvala rozmistenim obrazu
na stene pomoci evolucnich vypocetnich technik.

# Cíle práce
Zajem a do urcite miry fascinace tim, jak funguje evoluce a **prirozeny vyber** v prirode
Kdy staci aby existovalo prostredi, a po uplynuti urcite doby se mu organismy prizpusobi.
Najdou optimalni strategii, ktera jim dovoli
v prostredi prezit a branit se predatorum.

# Formulace problému
Rozmisteni obrazu nema jasne dane nejlepsi reseni.
Muze se stat, ze prijde jeden znalec umemi a rozmisteni ohodnoti jako spatne,
a pak prijde druhy znalec umeni a to same rozmisteni ohodnoti jako dobre.
Proto jsem se snazil do formulace problemu zahrnout co nejvice volnosti.
To jsem modelovat objektivni funkce, kterou vidite dole.
Ta ma tri casti.

Prvni cast je vzajemny vztah nebo afinita mezi jednotlivymi obrazy.
... popsat obrazek

Druha cast je vztah obrazu a steny.
To jsem se snazil modelovat pomoci funkce pi, ktera ohodnoti rozmisteni obrazu vzhledem
k casti steny, na ktere jsou umistene.
Napriklad si muzeme predstavit, ze prava stena je lepe osvetlena.
V tom pripade funkce pi bude preferovat tuto cast steny.

Posledni cast je penalizace za spatne umisteni.
To je umisteni obrazu tak, ze se navzajem prekryvaji,
nebo svoji casti presanuji prostor pro umistovani.

# Genetický algoritmus – princip fungování
V uvodu jsem zminoval prirozeny vyber.
Jeden z algoritmu, ktery se jim inspiruje, je prave GA.
Ten jsem take pouzil pro reseni problemu rozmisteni obrazu na stene.
GA muzeme klasifikovat jako randomizovany iterativni algoritmus.
Jeho kostru muzete videt na obrazku.
... rychle popsat obrazek
Stezejsi casti GA jsou zde napsane.
Dulezite casti ktere ktere jsem pouzil v moji praci vam ted popisu.

# Genetický algoritmus – kódování jedince
Nejdulezitejsi cast je skoro vzdy kodovani jedince.
Kodovani je bud prima, nebo v mem pripade neprima reprezentace reseni.
Tedy nejakeho rozmisteni obrazu.
... popsat omezeni

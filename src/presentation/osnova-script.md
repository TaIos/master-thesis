# Predstaveni
Vazeny pane predsedo, vazena komise, dovolte mi,
abych vam predstavil vysledky svoji diplomove prace.
Vedouci byl pan dekan Jirina,
a oponentem pan profesor Surynek.

Prace se zabyvala optimalizaci rozmistenim obrazu
pomoci evolucnich vypocetnich technik.

# Cíle práce
Stezejni casti bylo navrzeni evolucni techniky.
To byla zaroven i moji motivace, proc si tema vybrat.
Zajima me totiz a do urcite miry i fascinuje to, jak v prirode funguje prirozeny vyber,
kterym jsou evolucni techniky inspirovany.
Staci aby existovalo prostredi, a po uplynuti urcite doby se mu organismy prizpusobi.
Najdou optimalni strategii, ktera jim dovoli
v prostredi prezit a branit se predatorum.

# Formulace problému
Rozmisteni obrazu nema jasne dane nejlepsi reseni.
Muze se stat, ze prijde jeden znalec umemi a rozmisteni ohodnoti jako spatne,
a pak prijde druhy znalec umeni a to same rozmisteni ohodnoti jako dobre.
Proto jsem se snazil do formulace problemu zahrnout co nejvice volnosti.
To jsem se snazil modelovat objektivni funkci, kterou vidite zde.
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

# Genetický algoritmus – kódování jedince
Nejdulezitejsi cast je skoro vzdy kodovani jedince.
Kodovani je bud prima, nebo v mem pripade neprima reprezentace reseni.
Tedy nejakeho rozmisteni obrazu.
... popsat omezeni

# Měření
Nejzajimavejsi zjisteni z whitebox faze testovani genetickeho algoritmu bylo,
ze elitismus je dulezity pro zajisteni dobrych vysledku.
Na grafu je videt, ze bez jeho pouziti jsou dosazene vysledky vyrazne horsi.
Duvodem je pravdepodobne destruktivni dopad krizeni, kdy pro dva vzdalene jedince vznikne jedinec horsi.
Elitismus se tomu vyhne a primo prekopiruje nejlepsi jedince do dalsi populace.

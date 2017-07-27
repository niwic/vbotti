## M��rittely
Tarkoituksena olisi tehd� Javassa Vindinium botti 3 op laajuisena ty�n�. Botti perustuisi p��asiallisesti A* algoritmiin. Algoritmi k�ytett�isiin l�yt�mm�n lyhyimm�n reitin ei pelaajan omistuksessa olevaan kultakaivokseen. Algoritmin polun painoja voisi s��t�� sit� mukaan, miten hyv� reitti on kyseess�. Esimerkiksi reitti jossa on vastustaja voisi olla painavampi, kun sellainen mink� polulla ei ole vastustajaa.

Lis�n� voisi my�s laatia tilakoneen, joka ohjaa botin toimintaa esim. kun se ei omista mit��n kaivoja, tai kun se omistaa kaikki kaivot. Tiloja voisi my�s olla perustuen botin el�m�pisteisiin.

A* algoritmia k�ytet��n koska se on nopea tapa l�yt�� reitti pisteiden v�lill�. Algoritmin painoilla voidaan my�s s��t�� reitti� pelimekaniikan mukaan. Algoritmin sy�tteen� voisi olla parametrit jolla hienos��det��n reitti-valintaa, esim. mink� painoinen vastustajan l�pi menev�n reitti on.

Kentt� yll�pidet��n jonkin sortin matriisina josta vierus-solmut selvi�v�t nopeasti, ja A* algoritmin apuna k�ytet��n todenn�k�isesti minimikekoa.

Aikavaativuutta dominoi todenn�k�isesti A* algoritmin toteutus. Tilavaativuus on ehk� O(Kent�n koko).

L�hteet:
- A* osa TIRA kurssin luentomateriaalista
- http://vindinium.org/
## Määrittely
Tarkoituksena olisi tehdä Javassa Vindinium botti 3 op laajuisena työnä. Botti perustuisi pääasiallisesti A* algoritmiin. Algoritmi käytettäisiin löytämmän lyhyimmän reitin ei pelaajan omistuksessa olevaan kultakaivokseen. Algoritmin polun painoja voisi säätää sitä mukaan, miten hyvä reitti on kyseessä. Esimerkiksi reitti jossa on vastustaja voisi olla painavampi, kun sellainen minkä polulla ei ole vastustajaa.

Lisänä voisi myös laatia tilakoneen, joka ohjaa botin toimintaa esim. kun se ei omista mitään kaivoja, tai kun se omistaa kaikki kaivot. Tiloja voisi myös olla perustuen botin elämäpisteisiin.

A* algoritmia käytetään koska se on nopea tapa löytää reitti pisteiden välillä. Algoritmin painoilla voidaan myös säätää reittiä pelimekaniikan mukaan. Algoritmin syötteenä voisi olla parametrit jolla hienosäädetään reitti-valintaa, esim. minkä painoinen vastustajan läpi menevän reitti on.

Kenttä ylläpidetään jonkin sortin matriisina josta vierus-solmut selviävät nopeasti, ja A* algoritmin apuna käytetään todennäköisesti minimikekoa.

Aikavaativuutta dominoi todennäköisesti A* algoritmin toteutus. Tilavaativuus on ehkä O(Kentän koko).

Lähteet:
- A* osa TIRA kurssin luentomateriaalista
- http://vindinium.org/
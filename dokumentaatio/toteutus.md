## Toteutus

Ohjelmassa on pääpiirteiltään kaksi osaa. Ensimmäinen osa on tarvittavat kirjastot millä voi simuloida pelin etenemistä. Kirjastolla pystyy siirtämään pelaajan, josta palautuksena saa kopion pelikentästä siirron jälkeisellä tilanteella. Kirjastossa on pyritty kopioimaan niin vähän kun mahdollista. Käytännössä kopioidaan pelaajat, ja kultakaivokset. Aikavaativuus on siis O(H+G), missä H on pelaajat, ja G on kultakaivokset.

Toinen osa on itse alpha-beta algoritmiin perustuva botti. Botti toimii branch-and-bound perjaatteella. Se etenee siirto kerrallaan alas pelipuuta. Algoritmi palaa ylös jos pääsee pelin loppuun, maksimisyvyyteen, tai alpha-betan mukaiseen tilanteeseen jossa todetaan, että tilannetta ei tarvitse simuloida syvemmälle. Alpha-beta algoritmin aikavaativuus pahimmassa tapauksessa on O(S^D), S on siirtojen lukumäärä ja D on syvyys pelipuussa. Käytännössä se pitäisi toimia jonkin verran nopeammin, koska karsitaan pois turhia alipuita. Tilavaativuus tulee enimmäkseen siitä, että jokaisesta pelitilanteen muutoksesta luodaan kopio, jotta voidaan helposti siirtyä takaisin edelliseen tilanteeseen. Tämän johdosta muistissa on enimmäkseen pelin loppuun asti, eli puun yksi kokonainen haara kerrallan, eli O(D).

Käytössä olevat itse-toteutetut aputietorakenteet ovat ArrayList, Queue ja InsertionSort. Lisäksi botti käyttää leveyssuntaista hakua kiinnostavien kohteiden etäisyyden mittaamiseen.

Bottia pyritään optimoimaan muutamalla eri tavalla. Ensinnäkin alpha-beta karsiminen karsii pois tarkistettavia solmuja, jos parempi vaihtoehto on jo löydetty. Jotta alpha-beta toimisi paremmin, pyritään myös arvaamaan parhaan siirron. Paras siirto arvataan olevan se, joka johtaa pelaajan ei-hallitsemaan kultakaivokseen. Jos botilla on liian vähän life-pisteitä valtaakseen kultakaivoksen, arvataan sen sijaan, että paras siirto on kohti tavernaa. Mahdollisia siirtoja karsitaan myös sen perusteella, että ei edetä pelipuussa alas siirtoja jotka ovata sama kun, että pysyttäisiin paikoillaan.

Yhden peli-vuoron aikavaativuus koostuu pelikentän parsimistesta (R), reitinhausta (P) ja alpha-beta algoritmin suorituksesta (S^D). Alpha-beta algoritmin suorituksen aikavaativuus koostu siirtojen generoinnista (ml. niiden järjestäminen) (S^2) ja pelikentän mutatoinnista. Pelikentän mutatoinnin aikavaativuus sen sijaan koostuu pelaajien kopioinnista (H) ja kultakaivosten kopioinnista (G). Botin kokonaisaikavaativuus voisi siis sanoa olevan O(R + P + S^D*S^2*(H+G)) = O(R + P + S^(D+2)*(H+G)) = O(S^(D+2)*(H+G)).

Algoritmin toimintaa kuvaa seuraava taulukko, jossa näkyy miten monta solmua on jouduttu tarkistamaan riippuen miten hyvin ollaan arvattu oikea siirto.

![Arvaus vs. Solmut](tarkistettutsolmut.png)
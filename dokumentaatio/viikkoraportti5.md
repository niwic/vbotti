## Viikkoraportti 5

Käytetty aika:
- 5h: Mahdollisten siirtojen järjestäminen BFS algoritmilla
- 5h: Omia tietorakenteita & testejä niille

Yksi tapa virittää alpha-beta algoritmi, on yrittää arvata mikä paras siirto voisi olla, ja kokeilla sitä ensin. Yritin tehdä tätä järjestämällä siirrot niin, että kokeillaan ensin se joka johtaa lähimpään kultakaivokseen. Tämä paransi bottia merkittävästi lopullisen siirron valinnassa. Haku on toteutettu BFS hakuna, koska "kaarilla" ei ole painoja. Haetaan kerran etäisyyden jokaisesta pelikentän vapaasta paikasta jokaiseen kultakaivokseen. Lähde: https://chessprogramming.wikispaces.com/Alpha-Beta.

Botti etenee nyt määrätietoisesti kohti lähintä kultakaivosta, varoen ettei muut pelaajat sitä tapa. Se myös tappaa pelaajan jos se pystyy ja kohdepelaajalla on kaivoksia. Nyt kuitenkin vaikuttaa siltä, että se ei koskaan pidä arvokkaan käydä tavernassa, vaikka hp on niin alhainen, ettei se pysty valtaamaan kaivosta. Pitäisi siis vielä virittää botti niin, että se myös tajuaa käydä tavernassa vaikka se lyhyellä tähtäimellä merkitseekin kahden kullan menettämistä.

On myös tarkoitus vielä siistiä AlphaBetaBot luokka lisätä toiminnallisuudelle puuttuvat testit. Mahdolliset checkstyle virheet pitäisi myös vielä tarkistaa. Dokumentaation suhteen olisi tarkoitus lisätä vielä puuttuvia javadoc kommenttejä paikoittain, sekä viimeistellä toteutusdokumentaatio viimeisimmillä muutoksilla & lisätä käyttöohje.
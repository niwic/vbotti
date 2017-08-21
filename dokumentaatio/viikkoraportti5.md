## Viikkoraportti 5

5h: Mahdollisten siirtojen järjestäminen BFS algoritmilla
5h: Omia tietorakenteita & testejä niille

Yksi tapa virittää alpha-beta algoritmi, on yrittää arvata mikä paras siirto voisi olla, ja kokeilla sitä ensin. Yritin tehdä tätä järjestämällä siiroot niin, että kokeillaan ensin se joka johtaa lähimpään kultakaivokseen. Tämä paransi bottia merkittävästi lopullisen siirron valinnassa. Se kuitenkin hidasti alpha-beta algoritmia merkittävästi, joten muutin sen niin, että se tehdään vaan ensimmäisellä tasolla. Haku on toteutettu BFS hakuna, koska "kaarilla" ei ole painoja, ja pitää vain löytää lähin ei pelaajan omistuksessa oleva kaivo. Lähde: https://chessprogramming.wikispaces.com/Alpha-Beta.

Botti menee nyt määrätietoisesti kohti lähintä kultakaivosta, varoen ettei muut pelaaja sitä tapa. Se myös tappaa pelaajan jos se pystyy ja kohdepelaajalla on kaivoksia. Nyt kuitenkin vaikuttaa siltä, että se ei koskaa pidä arvokkaan käydä tavernassa, vaikka hp on niin alhainen, ettei se pysty valtaamaan kaivosta. Pitäisi siis vielä virittää botti niin, että se myös tajuaa käydä tavernassa vaikka se lyhyellä tähtäimellä menettääkin kaksi kultaa.
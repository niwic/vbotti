## M‰‰rittely
Tarkoituksena olisi tehd‰ Javassa Vindinium botti 3 op laajuisena tyˆn‰.

Tarkoitus olisi tehd‰ alpha-beta algoritmiin perustuvan botin. Botti toimisi niin, ett‰ pelaajan vuoron alussa l‰hdet‰‰n analysoimaan kaikki mahdolliset siirrot puuna ja mihin tulokseen ne loppujen lopuksi johtaa. Tilanteen analysointi pit‰‰ ehk‰ lopettaa ennen kun p‰‰st‰‰n lopputilanteeseen, koska mahdollisia mutaatioita on viisi (liikumiset + paikalla pysyminen) per vuoro. Vuoroja voi olla muutamasta sadasta yli tuhanteen.

Joka pelitilanteen mutaatiossa arvioidaan botin pisteet vastaan parhaan toisen botin pisteit‰. T‰m‰n erotuksen perusteella olisi tarkoitus muodostaa "alpha" ja "beta" arvot, jotka rajaavat mitk‰ pelitilanteen haarat kannattaa l‰hte‰ kokeilemaan. T‰m‰ tekee algoritmista branch-and-bound tyyppisen.

Alpha-beta algoritmi perustuu min-max algoritmiin jolla analysoidaan pelin kaikkien mahdollisten siirtojen tulos. Alpha-beta algoritmissa yritet‰‰n kuitenkin rajata mitk‰ haarat l‰hdet‰‰n analysoimaan. Pahimmassa tapauksessa joudutaan kuitenkin ehk‰ analysoimaan kaikki.

Kentt‰ yll‰pidet‰‰n jonkin matriisina josta vierus-solmut selvi‰v‰t nopeasti.

L‰hteet:
- https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning
- http://web.cs.ucla.edu/~rosen/161/notes/alphabeta.html
- https://www.cs.cornell.edu/courses/cs312/2002sp/lectures/rec21.htm
- http://vindinium.org/
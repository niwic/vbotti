## Käyttöohje

Botti suoritetaan ajamalla Main luokka. Luokalle pitää antaa vindinium pelaajan avaimen. Avaimen saa luotua itselleen osoitteesta http://vindinium.org/register. Ensimmäisen siirron yhteydessä tulostetaan osoite pelin visualisointiin, jossa voi seurata botin toimintaa.

Avaimen lisäksi botille voi antaa toisena parametrina "COMPETITION", jolloin botti osallistuu oikeaan peliin toisia botteja vastaan. Jos toista parametria ei anneta, osallistuu botti kokeilupeliin random-botteja vastaan.

Esim:

```
java -jar vbotti-1.0-SNAPSHOT.jar <avain>
```

tai

```
java -jar vbotti-1.0-SNAPSHOT.jar <avain> COMPETITION
```

Github repon release välilehdestä voi ladata itselleen käännetyn jar tiedoston.
package fi.niwic.vbotti;

import com.brianstempin.vindiniumclient.Main.VindiniumUrl;
import com.brianstempin.vindiniumclient.bot.simple.SimpleBot;
import com.brianstempin.vindiniumclient.bot.simple.SimpleBotRunner;
import com.brianstempin.vindiniumclient.dto.ApiKey;
import com.google.api.client.http.GenericUrl;
import fi.niwic.vbotti.bot.AlphaBetaBot;
import java.security.InvalidParameterException;

/**
 * Main luokka joka käynnistää botin.
 */
public class Main {

    /**
     * Käynnistää botin suorituksen.
     * 
     * Ensimmäisenä argumenttina pitää antaa vindinium avaimen jonka saa
     * osoitteesta http://vindinium.org/register.
     * 
     * Toisena argumenttina voi antaa "COMPETITION", jolloin botti osallistuu
     * oikeaan peliin. Muuten botti osallistuu harjoituspelin random-botteja
     * vastaan.
     * 
     * @param args avain ja mahdollisesti "COMPETITION"
     * @throws Exception mikä vaan poikkeus
     */
    public static void main(String[] args) throws Exception {
        
        if (args.length < 1) {
            throw new InvalidParameterException("You must provide a key");
        }
        
        String key = args[0];
        String arena = "";
        
        if (args.length > 1) {
            arena = args[1];
        }
        
        final GenericUrl gameUrl = getGameUrl(arena);
        final ApiKey apiKey = getApiKey(key);
        
        SimpleBot bot = new AlphaBetaBot();
        SimpleBotRunner runner = new SimpleBotRunner(apiKey, gameUrl, bot);
        runner.call();
    }
    
    private static GenericUrl getGameUrl(String arena) {
        if ("COMPETITION".equals(arena)) {
            return VindiniumUrl.getCompetitionUrl();
        } else {
            return VindiniumUrl.getTrainingUrl();
        }
    }
    
    private static ApiKey getApiKey(String key) {
        return new ApiKey(key);
    }
    
}

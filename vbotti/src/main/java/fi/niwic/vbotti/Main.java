package fi.niwic.vbotti;

import com.brianstempin.vindiniumclient.Main.VindiniumUrl;
import com.brianstempin.vindiniumclient.bot.simple.SimpleBot;
import com.brianstempin.vindiniumclient.bot.simple.SimpleBotRunner;
import com.brianstempin.vindiniumclient.dto.ApiKey;
import com.google.api.client.http.GenericUrl;
import fi.niwic.vbotti.bot.AStarBot;
import java.security.InvalidParameterException;

public class Main {

    public static void main(String[] args) throws Exception {
        
        if (args.length < 1) throw new InvalidParameterException("You must provide a key");
        
        String key = args[0];
        String arena = "";
        
        if (args.length > 1) {
            arena = args[1];
        }
        
        final GenericUrl gameUrl = getGameUrl(arena);
        final ApiKey apiKey = getApiKey(key);
        
        SimpleBot bot = new AStarBot();
        SimpleBotRunner runner = new SimpleBotRunner(apiKey, gameUrl, bot);
        runner.call();
    }
    
    public static GenericUrl getGameUrl(String arena) {
        if ("COMPETITION".equals(arena))
            return VindiniumUrl.getCompetitionUrl();
        else
            return VindiniumUrl.getTrainingUrl();
    }
    
    public static ApiKey getApiKey(String key) {
        return new ApiKey(key);
    }
    
}

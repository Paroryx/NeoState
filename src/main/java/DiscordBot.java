import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;

public class DiscordBot {
    private final String TOKEN;
    public DiscordBot(String token){
        this.TOKEN = token;
    }

    public static boolean checkToken(String token) {
        try {
            JDA api = JDABuilder.createDefault(token).build().awaitReady();
            return true;
        } catch (InvalidTokenException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Discord Bot Logic
}
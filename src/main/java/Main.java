import java.io.*;
public class Main {
    private static final File USERDATA = new File("src/main/java/accounts.json");
    private static final UserManager userManager = new UserManager(USERDATA);

    public static void main(String[] args) {
        //TODO Selfbot geht mit JDA nicht, muss ich selbst implementieren

        // Setup UI
        ConsoleUI.init();

        userManager.resetUsers();

        userManager.loadUser();
        System.out.println(EncryptionHelper.decryptToken(userManager.getUser().getToken()));


        try {
            userManager.addUser();
            userManager.deleteUser();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //make sure token is functioning
        userManager.validateToken();

        System.out.println("Your token is fine alr letsgoo");

        DiscordBot discord = new DiscordBot(EncryptionHelper.decryptToken(userManager.getUser().getToken()));
        //TODO selfbot soon??
        ConsoleUI.exit();
    }
}

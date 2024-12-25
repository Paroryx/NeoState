import org.fusesource.jansi.AnsiConsole;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ConsoleUI {
    // console interactions and menus

    public ConsoleUI(){
        AnsiConsole.systemInstall(); //initializing Jansi to use in Outputstream

    }

    public void drawLogo(){
        try {
            Scanner scr = new Scanner(new FileInputStream("src/main/java/logo.txt"));
            while(scr.hasNext()){
                System.out.println(scr.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void exit(){
        //TODO make it work :,)
        AnsiConsole.systemUninstall(); //uninstall Jansi from Outputstream
    }


}

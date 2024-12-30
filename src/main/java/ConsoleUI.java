import com.google.gson.Gson;
import org.fusesource.jansi.AnsiConsole;

import java.io.*;
import java.util.Scanner;

public class ConsoleUI {
    // console interactions and menus
    static Scanner uScr;
    public static void init(){
        AnsiConsole.systemInstall(); //initializing Jansi to use in Outputstream
        uScr = new Scanner(System.in);

        drawLogo();
    }

    public static void drawLogo(){
        try {
            Scanner scr = new Scanner(new FileInputStream("src/main/java/logo.txt"));
            while(scr.hasNext()){
                System.out.println(scr.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void printUsers(File f) throws IOException {
        System.out.println(getUsers(f));
    }
    private static Users getUsers(File f) throws IOException {
        Users users;
        String json = "";
        BufferedReader br = new BufferedReader(new FileReader(f));
        String temp = br.readLine();
        while(temp!=null){
            json+= temp;
            temp = br.readLine();
        }
        Gson gson = new Gson();
        users = gson.fromJson(json,Users.class);
        return users;
    }
    public static void exit(){
        //TODO make it work :,)
        AnsiConsole.systemUninstall(); //uninstall Jansi from Outputstream
    }


    public static User newUser() {
        User user = new User();
        System.out.println("Add a new Account:");
        System.out.print("Username: ");
        uScr = new Scanner(System.in);
        user.setUsername(uScr.nextLine().strip());
        System.out.print("Your Token: ");
        user.setToken(EncryptionHelper.encryptToken(uScr.nextLine().strip()));
        user.setPresence(null);
        user.setDate(System.currentTimeMillis());
        return user;
    }

    public static int selectUser(File f, Users users) throws IOException {
        printUsers(f);
        int index;
        do {
            System.out.print("select (userid)\t");
            index = uScr.nextInt();
        }while(index < 0 || index > users.getUsers().length-1 );
        System.out.println("selected %s".formatted(users.getUsers()[index].getUsername()));

        return index;
    }

    public static void readToken(Users users, int userid) {
        System.out.printf("There is something wrong with your token!\nPlease add the token of %s again\n",users.getUsers()[userid].getUsername());
        System.out.print("Your token: ");
        uScr = new Scanner(System.in);
        String token = EncryptionHelper.encryptToken(uScr.nextLine().strip());
        users.getUsers()[userid].setToken(token);
    }
}

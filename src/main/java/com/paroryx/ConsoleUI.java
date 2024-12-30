package com.paroryx;

import com.google.gson.Gson;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.*;
import java.util.Scanner;

public class ConsoleUI {
    // console interactions and menus
    static Scanner uScr;
    public static void init(){
        AnsiConsole.systemInstall(); //initializing Jansi to use in Outputstream
        AnsiConsole.out().println(Ansi.ansi().fg(Ansi.Color.CYAN).bold().a("Welcome to NeoState!").reset());

        uScr = new Scanner(System.in);

        drawLogo();
    }

    public static void drawLogo(){
        try {
            Scanner scr = new Scanner(ConsoleUI.class.getClassLoader().getResourceAsStream("logo.txt"));
            while(scr.hasNext()){
                AnsiConsole.out.println(Ansi.ansi().fg(Ansi.Color.YELLOW).bg(Ansi.Color.BLACK).a(scr.nextLine()).reset());
            }
        } catch (Exception e) {
            AnsiConsole.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("Error loading logo!").reset());
            throw new RuntimeException(e);
        }
    }
    public static void printUsers(File f) throws IOException {
        AnsiConsole.out.println(Ansi.ansi().fg(Ansi.Color.GREEN).bold().a("User List:").reset());
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
        AnsiConsole.out.println(Ansi.ansi().fg(Ansi.Color.RED).bold().a("Exiting...").reset());
        AnsiConsole.systemUninstall(); //uninstall Jansi from Outputstream
    }


    public static User newUser() {
        User user = new User();
        System.out.println(Ansi.ansi().fg(Ansi.Color.GREEN).bold().a("Add a new Account:").reset());
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
            System.out.print(Ansi.ansi().fg(Ansi.Color.CYAN).a("Select user by ID: ").reset());
            index = uScr.nextInt();
        }while(index < 0 || index > users.getUsers().length-1 );
        System.out.println(Ansi.ansi().fg(Ansi.Color.GREEN).a("Selected: ").reset()+users.getUsers()[index].getUsername());
        return index;
    }

    public static void readToken(Users users, int userid) {
        AnsiConsole.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("There is something wrong with your token!").reset()); // Fehler in Rot
        AnsiConsole.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("Please add the token of %s again\n".formatted(users.getUsers()[userid].getUsername())).reset());
        System.out.print("Your token: ");
        uScr = new Scanner(System.in);
        String token = EncryptionHelper.encryptToken(uScr.nextLine().strip());
        users.getUsers()[userid].setToken(token);
    }
}

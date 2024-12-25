import com.google.gson.Gson;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static final File F = new File("src/main/java/accounts.json");
    private static Scanner uScr = new Scanner(System.in);
    private static Users users = new Users();
    private static int userid;

    public static void main(String[] args) {
        //TODO Selfbot geht mit JDA nicht, muss ich selbst implementieren

        //set up UI
        ConsoleUI console = new ConsoleUI();

        console.drawLogo();

        resetUsers();

        if(!F.isFile() || F.length() == 0){
            try {
                F.createNewFile();
                addUser(F);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            userid = selectUser();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

/*        try {
            addUser(f);
            deleteUser(f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

        //make sure token is functioning
        while(!DiscordBot.checkToken(decryptToken(users.getUsers()[userid].getToken()))){
            System.out.printf("There is something wrong with your token!\nPlease add the token of %s again",users.getUsers()[userid].getUsername());
            System.out.print("Your token: ");
            uScr = new Scanner(System.in);
            String token = encryptToken(uScr.nextLine().strip());
            users.getUsers()[userid].setToken(token);

            try {
                saveToJson(users,F);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Your token is fine alr letsgoo");
        DiscordBot discord = new DiscordBot(decryptToken(users.getUsers()[userid].getToken()));
        //TODO selfbot soon??
    }

    private static int selectUser() throws IOException {
        showUsers();
        int index;
        do {
            System.out.print("select (userid)\t");
            index = uScr.nextInt();
        }while(index < 0 || index > users.getUsers().length-1 );
        System.out.println("selected %s".formatted(users.getUsers()[index].getUsername()));

        return index;
    }

    private static void showUsers() throws IOException {
        String json = "";
        BufferedReader br = new BufferedReader(new FileReader(F));
        String temp = br.readLine();
        while(temp!=null){
            json+= temp;
            temp = br.readLine();
        }
        Gson gson = new Gson();
        users = gson.fromJson(json,Users.class);

        System.out.println(users);
    }

    private static void deleteUser(File f) throws IOException {
        int i;
        System.out.println("Select Account to delete:");
        if(users.getUsers().length > 1) {
            i = selectUser();
        }else{
            resetUsers();
            addUser(f);
            return;
        }
        User deletedUser = users.getUsers()[i];

        User[] temp = new User[users.getUsers().length-1];
        User[] temp1 = Arrays.copyOfRange(users.getUsers(),0,i);
        User[] temp2 = Arrays.copyOfRange(users.getUsers(),i+1,users.getUsers().length);
        int k = 0;
        for (int j = 0; j < temp.length; j++) {
            if(j< temp1.length) {
                temp[j] = temp1[j];
            }else{
                temp[j] = temp2[k];
                k++;
            }
        }
        users.setUsers(temp);

        saveToJson(users,f);

        System.out.printf("succesfully removed %s",deletedUser.getUsername());
        if(i==userid){
            System.out.println("Select Account to use");
            userid = selectUser();
        }
    }

    private static void saveToJson(Users users, File f) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(users);

        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        writer.write(json);
        writer.close();
    }

    private static void addUser(File f) throws IOException {
        System.out.println("Add a new Account:");
        User user = new User();
        System.out.print("Username: ");
        uScr = new Scanner(System.in);
        user.setUsername(uScr.nextLine().strip());
        System.out.print("Your Token: ");
        user.setToken(encryptToken(uScr.nextLine().strip()));
        user.setPresence(null);
        user.setDate(System.currentTimeMillis());


        if(users.getUsers()!=null){
            User[] temp = Arrays.copyOf(users.getUsers(),users.getUsers().length+1);
            temp[temp.length-1] = user;
            users.setUsers(temp);
        }else{
            users.setUsers(new User[]{user});
        }

        saveToJson(users,f);

        System.out.printf("Succesfully added %s",user.getUsername());
    }

    private static String encryptToken(String token) {
        return token; //encrypt token here - TODO
    }
    private static String decryptToken(String token){
        return token; //decrypt token here - TODO
    }
    private static void resetUsers(){
        if(F.delete()){
            System.out.printf("succesfully deleted %s",F.getName());
        }else{
            System.out.printf("something went wrong, please delete %s manually",F.getName());
        }
    }
}

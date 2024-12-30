import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class UserManager {
    private final File FILE;
    private Users users;
    private int userid;
    public UserManager(File f) {
        this.FILE = f;
        this.users = new Users();
    }
    public User getUser(){
        return users.getUsers()[userid];
    }

    public void resetUsers(){
        if(FILE.delete()){
            System.out.printf("successfully deleted %s\n",FILE.getName());
        }else{
            System.out.printf("something went wrong, please delete %s manually\n",FILE.getName());
        }
    }

    public void loadUser() {

        try{
            if(FILE.createNewFile()){
                addUser();
            }
        }catch(IOException e){
            throw new RuntimeException(e);
        }

        try {
            users = JsonHelper.readUsersFromJson(FILE);
            userid = ConsoleUI.selectUser(FILE,users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void addUser() throws IOException {
        User user = ConsoleUI.newUser();

        if(users.getUsers()!=null){
            User[] temp = Arrays.copyOf(users.getUsers(),users.getUsers().length+1);
            temp[temp.length-1] = user;
            users.setUsers(temp);
        }else{
            users.setUsers(new User[]{user});
        }

        JsonHelper.saveUsersToJson(users,FILE);

        System.out.printf("Successfully added %s\n",user.getUsername());
    }
    public void deleteUser() throws IOException {
        int i;
        System.out.println("Select Account to delete:");
        if(users.getUsers().length > 1) {
            i = ConsoleUI.selectUser(FILE,users);
        }else{
            resetUsers();
            addUser();
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

        JsonHelper.saveUsersToJson(users,FILE);

        System.out.printf("successfully removed %s\n",deletedUser.getUsername());
        if(i==userid){
            System.out.println("Select Account to use");
            userid = ConsoleUI.selectUser(FILE,users);
        }
    }

    public void validateToken() {
        while(!DiscordBot.checkToken(EncryptionHelper.decryptToken(users.getUsers()[userid].getToken()))){
            ConsoleUI.readToken(users, userid);

            try {
                JsonHelper.saveUsersToJson(users,FILE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

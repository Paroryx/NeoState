import com.google.gson.Gson;

import java.io.*;

public class JsonHelper {
    public static void saveUsersToJson(Users users, File f) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(users);

        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        writer.write(json);
        writer.close();
    }
    public static Users readUsersFromJson(File f) throws IOException {
        StringBuilder json = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(f));
        String temp = br.readLine();

        while(temp!=null){
            json.append(temp);
            temp = br.readLine();
        }

        Gson gson = new Gson();
        return gson.fromJson(json.toString(),Users.class);
    }
}

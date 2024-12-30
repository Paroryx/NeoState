public class Users {
    private User[] users;

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Loaded %d Accounts:\n".formatted(users.length));
        for (int i = 0; i < users.length; i++) {
            sb.append("%d. %s\n".formatted(i,users[i]));
        }
        return sb.toString();
    }
}

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String login;
    private String password;
    private String name;
    private double money;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return login.equals(user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.money = 0;
    }

    public User(String login, String password, String name, double money) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.money = money;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getMoneyString() {
        return "You have: " + this.money + "$";
    }
    public double getMoney() {
        return this.money;
    }

    public void changeLogin(String login) {
        this.login = login;
    }

    public boolean changePassword(UserInterface userInterface) {
        boolean result = false;
        String oldPassword = userInterface.getString("Input your password: ");
        if(oldPassword.equals(this.password)){
            String newPassword1 = userInterface.getString("Input new password: ");
            String newPassword2 = userInterface.getString("Repeat new password: ");
            if (newPassword1.equals(newPassword2)){
                this.password = password;
                result = true;
            } else {
                userInterface.getString("Mismatch");
            }
        } else {
            userInterface.getString("Incorrect!");
        }
        return result;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addMoney(double money) {
        this.money += money;
    }

    public boolean spendMoney(double money) {
        boolean result = money >= this.money;
        if (result) {
            this.money += money;
        }
        return result;
    }
}

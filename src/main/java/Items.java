import java.io.Serializable;

public class Items implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private double price;
    private User owner;

    //Нужен только для упрощения вызова getListFromFile
    public Items(String name) {
        this.name = name;
    }

    public Items(String name, User owner) {
        this.name = name;
        this.owner = owner;
        this.price = 0.01;
    }

    public Items(String name, double price, User owner) {
        this.name = name;
        this.price = price;
        this.owner = owner;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return name + ", price=" + price + ", owner=" + owner;
    }
}

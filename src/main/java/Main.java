import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        //get image
        File imageFile = new File("src\\main\\resources\\userImage.png");
        BufferedImage image = null;
        try {
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DatabaseProxy databaseProxy = new DatabaseProxy();
        Databaseable databaseable = databaseProxy.instanceDatabaseProxy(new Database());
        try {
            databaseable.connectToDatabase();
            databaseable.setUser("Vasya", "Pupsik", image);
            databaseable.closeConnectToDatabase();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

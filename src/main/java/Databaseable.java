import java.sql.SQLException;

public interface Databaseable {
    void connectToDatabase() throws SQLException, ClassNotFoundException;
    void closeConnectToDatabase() throws SQLException ;

    @SaveImage(indexOfParametr = 2, path = "src\\main\\resources\\")
    <T> void setUser(String name, String surname, T photo)throws SQLException;
}

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
    private Connection connection = null;

    public void connectToDatabase() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/playtika?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "root");
    }

    @SaveImage(indexOfParametr = 2, path = "src\\main\\resources\\")
    public <T> void setUser(String name, String surname, T photo) throws SQLException {
        String query = " insert into users (name, surname, photo)"
                + " values (?, ?, ?)";

        if (photo.getClass().getName().contains("java.lang.String")) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, (String) photo);

            preparedStatement.execute();
        }
    }

    public void closeConnectToDatabase() throws SQLException {
        connection.close();
    }
}

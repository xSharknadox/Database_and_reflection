import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.util.UUID;

public class DatabaseProxy {

    public Databaseable instanceDatabaseProxy(Database database) {
        return (Databaseable) Proxy.newProxyInstance(Database.class.getClassLoader(), Database.class.getInterfaces(),(proxy, method, args) -> {
            if(method.isAnnotationPresent(SaveImage.class)) {
                //get annotation
                Annotation annotation = method.getDeclaredAnnotation(SaveImage.class);

                //random file name
                String path = ((SaveImage) annotation).path() + UUID.randomUUID().toString() + ".png";

                //write file in disk
                File outputJpeg = new File(path);
                ImageIO.write((BufferedImage) args[((SaveImage) annotation).indexOfParametr()], "png", outputJpeg);

                //change BufferedImage parameter on String with path of image on disk
                args[((SaveImage) annotation).indexOfParametr()] = path;

                method.invoke(database, args);
            } else {
                return method.invoke(database, args);
            }
            return null;
        });
    }
}

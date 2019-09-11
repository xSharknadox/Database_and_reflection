import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.annotation.Annotation;
import java.util.UUID;

public class DatabaseEnhancer {
    public Database instanceDatabaseProxy(Database database) {
        MethodInterceptor methodInterceptor = (o, method, objects, methodProxy) -> {
            if (method.isAnnotationPresent(SaveImage.class)) {
                //get annotation
                Annotation annotation = method.getDeclaredAnnotation(SaveImage.class);

                //random file name
                String path = ((SaveImage) annotation).path() + UUID.randomUUID().toString() + ".png";

                //write file in disk
                File outputJpeg = new File(path);
                ImageIO.write((BufferedImage) objects[((SaveImage) annotation).indexOfParametr()], "png", outputJpeg);

                //change BufferedImage parameter on String with path of image on disk
                objects[((SaveImage) annotation).indexOfParametr()] = path;

                method.invoke(database, objects);
            } else {
                return method.invoke(database, objects);
            }
            return null;
        };
        return (Database) Enhancer.create(Database.class, methodInterceptor);
    }
}

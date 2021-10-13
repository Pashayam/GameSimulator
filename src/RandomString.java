import java.util.Random;
import java.util.stream.Collectors;

public class RandomString {
    static  String str = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
    public static String getString() {
        return new Random().ints(((long) new Random().nextInt(3) + 3), 0, str.length())
                .mapToObj(str::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
    }
}

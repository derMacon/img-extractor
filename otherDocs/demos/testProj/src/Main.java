import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("hi");
        List<String> lst = new LinkedList<>();
        lst.add(0, "hi");
        System.out.println(lst.remove(0));
        System.out.println(lst.remove(0));
    }
}

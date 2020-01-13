import java.io.File;

public class Main {

    public static void main(String[] args) {
        File f1 = new File("./test1.txt");
        File f2 = new File("./test2.txt");

        System.out.println(fstFileNewer(f1, f2));
    }


    private static boolean fstFileNewer(File f1, File f2) {
        long d1 = f1.lastModified();
        long d2 = f2.lastModified();

        return d1 == d2 || d1 > d2;
    }


}

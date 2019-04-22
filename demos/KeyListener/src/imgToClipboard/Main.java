package imgToClipboard;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

import javax.swing.ImageIcon;

public class Main {

    public static void main(String[] args) {

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        ImageIcon icon = new ImageIcon("res/b.png", "");

        ClipboardImage clipboardImage = new ClipboardImage(icon.getImage());
        clipboard.setContents(clipboardImage, clipboardImage);

    }

}

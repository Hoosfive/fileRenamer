import java.awt.*;

public class main {
    public static void main(String[] args) {
        mainWin win = new mainWin();
        win.setTitle("FilesRenamer");
        win.setMinimumSize(new Dimension(550,400));
        win.setResizable(false);
        win.setLocationRelativeTo(null);
        win.setVisible(true);
        win.pack();
    }
}

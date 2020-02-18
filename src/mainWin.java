import javax.swing.*;
import java.io.File;
import java.util.Vector;

public class mainWin  extends JFrame{
    private JTextField filesPath;
    private JButton choosePath;
    private JTextField newExtension;
    private JButton startRenaming;
    private JList renamedList;
    private JPanel panel;
    private JLabel isDone;
    private JButton cancel;
    private File file;
    private Vector<String> oldNames = new Vector<>(), newNames = new Vector<>(),listData = new Vector<>();
    private File[] filesList;


    mainWin()
    {
        this.getContentPane().add(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        choosePath.addActionListener(e -> {
            isDone.setText("");
            JFileChooser fileopen = new JFileChooser();
            fileopen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileopen.setDialogTitle("Выберите папку");
            int ret = fileopen.showDialog(null, "Выбор папки");
            if (ret == JFileChooser.APPROVE_OPTION) {
                file = fileopen.getSelectedFile();
                filesPath.setText(file.getPath());
                listData.clear();
                renamedList.removeAll();
            }
        });
        startRenaming.addActionListener(e -> getFilesList());
        cancel.addActionListener(e -> cancelRenaming());
    }

    private void cancelRenaming()
    {
        filesList = new File(filesPath.getText()).listFiles();
        if (filesList != null & !listData.isEmpty()) {
            int counter = 0 ;
            listData.clear();
            for (File file : filesList) {
                file.renameTo(new File(filesPath.getText(), oldNames.get(counter)));
                listData.addElement(newNames.get(counter)+ " → " + oldNames.get(counter) );
                counter++;
            }
            renamedList.setListData(listData);
            isDone.setText("Отмена выполнена!");
        }
        else {
            isDone.setText("Отменять нечего...");
        }
    }
    private void getFilesList()
    {
        oldNames.clear();
        newNames.clear();
        listData.clear();
        filesList = new File(filesPath.getText()).listFiles();
        File file2;
        if (filesList != null) {
            for (File file : filesList) {
                if (file.isFile()) {
                    if (file.getName().contains("."))
                        file2 = new File(file.getParent(), file.getName().substring(0,file.getName().indexOf(".")+1) + newExtension.getText());
                    else file2 = new File(file.getParent(), file.getName() + "." + newExtension.getText());
                    while (file2.exists())
                    {
                        file2 = new File(file2.getParent(), file2.getName().substring(0,file2.getName().indexOf(".")) + "— копия." + newExtension.getText() );
                    }
                    oldNames.addElement(file.getName());
                    file.renameTo(file2);
                    newNames.addElement(file2.getName());
                    listData.addElement(oldNames.lastElement() + " → " + newNames.lastElement());
                }
            }
            renamedList.setListData(listData);
            isDone.setText("Переименование выполнено!");
        }
        else isDone.setText("В папке нет файлов...");
    }
}

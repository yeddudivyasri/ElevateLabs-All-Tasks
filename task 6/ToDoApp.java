import javax.swing.*;
import java.awt.*;

public class ToDoApp extends JFrame {

    public ToDoApp() {
        setTitle("To Do App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextField taskField = new JTextField(20);
        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");

        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);

        JPanel panel = new JPanel();
        panel.add(taskField);
        panel.add(addButton);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(list), BorderLayout.CENTER);
        add(deleteButton, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            String task = taskField.getText();
            if (!task.isEmpty()) {
                model.addElement(task);
                taskField.setText("");
            }
        });

        deleteButton.addActionListener(e -> {
            int index = list.getSelectedIndex();
            if (index != -1) {
                model.remove(index);
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new ToDoApp();
    }
}
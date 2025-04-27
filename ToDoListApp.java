import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class ToDoListApp extends JFrame {
    private ArrayList<Task> tasks;
    private DefaultListModel<String> listModel;
    private JList<String> taskList;

    private JTextField nameField, dueDateField;
    private JComboBox<String> categoryComboBox;
    private JButton addButton, deleteButton, completeButton, saveButton, loadButton;

    public ToDoListApp() {
        setTitle("To-Do List with Categories");
        setSize(500, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tasks = new ArrayList<>();
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);

        // Input Fields
        nameField = new JTextField(15);
        dueDateField = new JTextField(15);
        categoryComboBox = new JComboBox<>(new String[] {"Work", "Personal", "Miscellaneous"});

        addButton = new JButton("Add Task");
        deleteButton = new JButton("Delete Task");
        completeButton = new JButton("Mark as Completed");
        saveButton = new JButton("Save to File");
        loadButton = new JButton("Load from File");

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.add(new JLabel("Task Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Due Date (yyyy-mm-dd):"));
        inputPanel.add(dueDateField);
        inputPanel.add(new JLabel("Category:"));
        inputPanel.add(categoryComboBox);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);

        JPanel filePanel = new JPanel();
        filePanel.add(saveButton);
        filePanel.add(loadButton);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(taskList), BorderLayout.CENTER);
        mainPanel.add(filePanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Button Events
        addButton.addActionListener(e -> addTask());
        deleteButton.addActionListener(e -> deleteTask());
        completeButton.addActionListener(e -> completeTask());
        saveButton.addActionListener(e -> saveToFile());
        loadButton.addActionListener(e -> loadFromFile());
    }

    private void addTask() {
        String name = nameField.getText().trim();
        String category = categoryComboBox.getSelectedItem().toString();
        String dueDate = dueDateField.getText().trim();

        if (name.isEmpty() || dueDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        Task task = new Task(name, category, dueDate);
        tasks.add(task);
        listModel.addElement(task.display());

        nameField.setText("");
        dueDateField.setText("");
    }

    private void deleteTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            tasks.remove(selectedIndex);
            listModel.remove(selectedIndex);
        }
    }

    private void completeTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            Task task = tasks.get(selectedIndex);
            task.setCompleted(true);
            listModel.set(selectedIndex, task.display());
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt"))) {
            for (Task task : tasks) {
                writer.write(task.toString());
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Data saved to tasks.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        tasks.clear();
        listModel.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("tasks.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = Task.fromString(line);
                if (task != null) {
                    tasks.add(task);
                    listModel.addElement(task.display());
                }
            }
            JOptionPane.showMessageDialog(this, "Data loaded from tasks.txt");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "No saved file found.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ToDoListApp().setVisible(true));
    }
}

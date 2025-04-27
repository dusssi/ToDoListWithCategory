public class Task {
    private String name;
    private String category;
    private String dueDate;
    private boolean isCompleted;

    public Task(String name, String category, String dueDate) {
        this.name = name;
        this.category = category;
        this.dueDate = dueDate;
        this.isCompleted = false;
    }

    public String toString() {
        return name + "," + category + "," + dueDate + "," + (isCompleted ? "Completed" : "Pending");
    }

    public static Task fromString(String data) {
        String[] parts = data.split(",");
        if (parts.length == 4) {
            Task task = new Task(parts[0], parts[1], parts[2]);
            task.setCompleted(parts[3].equals("Completed"));
            return task;
        }
        return null;
    }

    public String display() {
        return String.format("Name: %s | Category: %s | Due Date: %s | Status: %s", 
            name, category, dueDate, (isCompleted ? "Completed" : "Pending"));
    }

    public String getCategory() {
        return category;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}

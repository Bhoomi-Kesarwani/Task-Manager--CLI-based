import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final UserService userService = new UserService();
    private static final TaskService taskService = new TaskService();

    public static void main(String[] args) {
        System.out.println("=== Task Manager CLI ===");
        while (true) {
            System.out.println("\n1) Register  2) Login  3) Exit");
            System.out.print("Choice: ");
            String ch = sc.nextLine();
            switch (ch) {
                case "1": register(); break;
                case "2":
                    int userId = login();
                    if (userId != -1) userMenu(userId);
                    else System.out.println("Invalid login.");
                    break;
                case "3": System.out.println("Bye!"); return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void register() {
        System.out.print("Username: ");
        String u = sc.nextLine();
        System.out.print("Password: ");
        String p = sc.nextLine();
        if (userService.register(u, p)) System.out.println("Registered successfully.");
        else System.out.println("Registration failed.");
    }

    private static int login() {
        System.out.print("Username: ");
        String u = sc.nextLine();
        System.out.print("Password: ");
        String p = sc.nextLine();
        return userService.login(u, p);
    }

    private static void userMenu(int userId) {
        while (true) {
            System.out.println("\n--- Task Menu ---");
            System.out.println("1) Add Task  2) Update Task  3) Delete Task");
            System.out.println("4) Mark Complete  5) View All Tasks");
            System.out.println("6) Search by Category/Priority  7) Logout");
            System.out.print("Choice: ");
            String ch = sc.nextLine();
            switch (ch) {
                case "1": addTask(userId); break;
                case "2": updateTask(userId); break;
                case "3": deleteTask(userId); break;
                case "4": markComplete(userId); break;
                case "5": viewAll(userId); break;
                case "6": search(userId); break;
                case "7": return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void addTask(int userId) {
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Description: ");
        String desc = sc.nextLine();
        System.out.print("Category: ");
        String cat = sc.nextLine();
        System.out.print("Priority (High/Medium/Low): ");
        String pr = sc.nextLine();
        System.out.println(taskService.addTask(userId, title, desc, cat, pr) ? "Task added." : "Failed.");
    }

    private static void updateTask(int userId) {
        System.out.print("Task ID: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("New Title: ");
        String title = sc.nextLine();
        System.out.print("New Description: ");
        String desc = sc.nextLine();
        System.out.print("New Category: ");
        String cat = sc.nextLine();
        System.out.print("New Priority: ");
        String pr = sc.nextLine();
        System.out.print("Status (Pending/Completed): ");
        String st = sc.nextLine();
        System.out.println(taskService.updateTask(id, userId, title, desc, cat, pr, st) ? "Updated." : "Failed.");
    }

    private static void deleteTask(int userId) {
        System.out.print("Task ID: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.println(taskService.deleteTask(id, userId) ? "Deleted." : "Failed.");
    }

    private static void markComplete(int userId) {
        System.out.print("Task ID: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.println(taskService.markComplete(id, userId) ? "Completed." : "Failed.");
    }

    private static void viewAll(int userId) {
        List<String> tasks = taskService.listTasks(userId);
        if (tasks.isEmpty()) System.out.println("No tasks found.");
        else tasks.forEach(System.out::println);
    }

    private static void search(int userId) {
        System.out.print("Category: ");
        String cat = sc.nextLine();
        System.out.print("Priority: ");
        String pr = sc.nextLine();
        List<String> tasks = taskService.searchByCategoryOrPriority(userId, cat, pr);
        if (tasks.isEmpty()) System.out.println("No matching tasks.");
        else tasks.forEach(System.out::println);
    }
}

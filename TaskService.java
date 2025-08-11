import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskService {

    public boolean addTask(int userId, String title, String description, String category, String priority) {
        String sql = "INSERT INTO tasks (user_id, title, description, category, priority) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setString(4, category);
            ps.setString(5, priority);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Add task failed: " + e.getMessage());
            return false;
        }
    }

    public boolean updateTask(int taskId, int userId, String title, String description, String category, String priority, String status) {
        String sql = "UPDATE tasks SET title = ?, description = ?, category = ?, priority = ?, status = ? WHERE id = ? AND user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, category);
            ps.setString(4, priority);
            ps.setString(5, status);
            ps.setInt(6, taskId);
            ps.setInt(7, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Update task failed: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteTask(int taskId, int userId) {
        String sql = "DELETE FROM tasks WHERE id = ? AND user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, taskId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Delete task failed: " + e.getMessage());
            return false;
        }
    }

    public boolean markComplete(int taskId, int userId) {
        String sql = "UPDATE tasks SET status = 'Completed' WHERE id = ? AND user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, taskId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Mark complete failed: " + e.getMessage());
            return false;
        }
    }

    public List<String> listTasks(int userId) {
        String sql = "SELECT id, title, category, priority, status FROM tasks WHERE user_id = ?";
        List<String> tasks = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tasks.add(String.format("[%d] %s | %s | %s | %s",
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("category"),
                            rs.getString("priority"),
                            rs.getString("status")));
                }
            }
        } catch (SQLException e) {
            System.err.println("List tasks failed: " + e.getMessage());
        }
        return tasks;
    }

    public List<String> searchByCategoryOrPriority(int userId, String category, String priority) {
        String sql = "SELECT id, title, status FROM tasks WHERE user_id = ? AND (category = ? OR priority = ?)";
        List<String> tasks = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, category);
            ps.setString(3, priority);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tasks.add(String.format("[%d] %s - %s",
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("status")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Search failed: " + e.getMessage());
        }
        return tasks;
    }
}

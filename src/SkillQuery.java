import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SkillQuery {

    public static void main(String[] args) {

        String roleName = "Java Full Stack Developer";

        String sql = """
                SELECT s.skill_name, rs.importance
                FROM role_skills rs
                JOIN roles r ON rs.role_id = r.id
                JOIN skills s ON rs.skill_id = s.id
                WHERE r.role_name = ?
                ORDER BY rs.importance DESC
                """;

        try {
            Connection connection = DBConnection.getConnection();

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, roleName);

            ResultSet result = statement.executeQuery();

            System.out.println("Required skills for: " + roleName);
            System.out.println("--------------------------------");

            while (result.next()) {
    String skill = result.getString("skill_name");
    int importance = result.getInt("importance");

    String priority;

    if (importance == 5) {
        priority = "HIGH";
    } else if (importance == 4) {
        priority = "MEDIUM";
    } else {
        priority = "LOW";
    }

    System.out.println(
            skill + " - Importance: " + importance + " - Priority: " + priority
    );
}

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

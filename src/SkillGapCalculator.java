import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SkillGapCalculator {

   public static AnalysisResult analyze(String roleName, List<String> currentSkills) {
        // Skills required for the selected job role
        

        // Skills currently known by the user
        

List<String> requiredSkills = new ArrayList<>();
List<Integer> skillImportance = new ArrayList<>();
List<String> matchedSkillNames = new ArrayList<>();
List<String> missingSkills = new ArrayList<>();
List<String> priorities = new ArrayList<>();

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

    while (result.next()) {
        requiredSkills.add(result.getString("skill_name"));
        skillImportance.add(result.getInt("importance"));
    }

    connection.close();

} catch (Exception e) {
    e.printStackTrace();
}

        int matchedSkills = 0;

System.out.println("Matched Skills:");
System.out.println("----------------");

for (String skill : requiredSkills) {

    if (currentSkills.contains(skill)) {
        System.out.println("✓ " + skill);
        matchedSkills++;
        matchedSkillNames.add(skill);
    }
}

System.out.println();

System.out.println("Missing Skills:");
System.out.println("----------------");

for (int i = 0; i < requiredSkills.size(); i++) {

    String skill = requiredSkills.get(i);
    int importance = skillImportance.get(i);

    if (!currentSkills.contains(skill)) {

    String priority;

    if (importance == 5) {
        priority = "HIGH";
    } else if (importance == 4) {
        priority = "MEDIUM";
    } else {
        priority = "LOW";
    }

    System.out.println("✗ " + skill + " - Priority: " + priority);

    missingSkills.add(skill);
    priorities.add(priority);
}
}

System.out.println();
        double coverage =
                ((double) matchedSkills / requiredSkills.size()) * 100;

        System.out.println();
        System.out.println("Skill Coverage: " + coverage + "%");
        return new AnalysisResult(
        roleName,
        coverage,
        matchedSkillNames,
        missingSkills,
        priorities
);
}
}
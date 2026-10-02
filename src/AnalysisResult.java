import java.util.List;

public class AnalysisResult {

    String roleName;
    double coverage;
    List<String> matchedSkills;
    List<String> missingSkills;
    List<String> priorities;

    public AnalysisResult(
            String roleName,
            double coverage,
            List<String> matchedSkills,
            List<String> missingSkills,
            List<String> priorities) {

        this.roleName = roleName;
        this.coverage = coverage;
        this.matchedSkills = matchedSkills;
        this.missingSkills = missingSkills;
        this.priorities = priorities;
    }
}
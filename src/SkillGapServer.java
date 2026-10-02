import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SkillGapServer {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8081), 0
        );

        server.createContext("/", SkillGapServer::handleHome);
        server.createContext("/analyze", SkillGapServer::handleAnalyze);

        server.createContext("/style.css", exchange -> {

            String css = Files.readString(
                    Path.of("../web/style.css")
            );

            byte[] cssBytes = css.getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders().set(
                    "Content-Type",
                    "text/css; charset=UTF-8"
            );

            exchange.sendResponseHeaders(
                    200,
                    cssBytes.length
            );

            OutputStream output = exchange.getResponseBody();

            output.write(cssBytes);
            output.close();
        });

        server.start();

        System.out.println("SkillGap Analyzer running at:");
        System.out.println("http://localhost:8081");
    }

    private static void handleHome(HttpExchange exchange) throws IOException {

        String html = Files.readString(
                Path.of("../web/index.html")
        );

        byte[] htmlBytes = html.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set(
                "Content-Type",
                "text/html; charset=UTF-8"
        );

        exchange.sendResponseHeaders(
                200,
                htmlBytes.length
        );

        OutputStream output = exchange.getResponseBody();

        output.write(htmlBytes);
        output.close();
    }

    private static void handleAnalyze(HttpExchange exchange) throws IOException {

        String query = exchange.getRequestURI().getQuery();

        System.out.println("Received from browser:");
        System.out.println(query);

        String[] parameters = query.split("&");

        for (String parameter : parameters) {
            System.out.println(parameter);
        }

        String roleName = "";

        List<String> currentSkills = new ArrayList<>();

        for (String parameter : parameters) {

            String[] parts = parameter.split("=");

            if (parts[0].equals("role")) {
                roleName = parts[1].replace("+", " ");
            }

            if (parts[0].equals("skill")) {
                currentSkills.add(
                        parts[1].replace("+", " ")
                );
            }
        }

        System.out.println("Role: " + roleName);
        System.out.println("Current Skills: " + currentSkills);

        AnalysisResult result =
                SkillGapCalculator.analyze(
                        roleName,
                        currentSkills
                );

        StringBuilder response = new StringBuilder();

        response.append("""
                <html>
                <head>
                    <title>SkillGap Result</title>
                    <link rel="stylesheet" href="/style.css">
                </head>

                <body>

                <div class="container">

                <div class="card">

                <h1>SkillGap Analysis</h1>

                <h2>Target Role:&nbsp;""");

        response.append(result.roleName);

        response.append("""
                </h2>

                <div class="coverage-section">

                    <div class="coverage-text">
                        Skill Coverage: """);

        response.append(
                String.format("%.2f", result.coverage)
        );

        response.append("""
                        %
                    </div>

                    <div class="progress-bar">

                        <div class="progress-fill"
                             style="width:""");

        response.append(result.coverage);

        response.append("%;\"></div>");

        response.append("""
                    </div>

                </div>

                <div class="section">

                    <h2>Matched Skills</h2>

                    <ul>
                """);

        for (String skill : result.matchedSkills) {

            response.append("<li>&#10003; ")
                    .append(skill)
                    .append("</li>");
        }

        response.append("""
                    </ul>

                </div>

                <div class="section">

                    <h2>Missing Skills</h2>

                    <ul>
                """);

        for (int i = 0; i < result.missingSkills.size(); i++) {

            String skill =
                    result.missingSkills.get(i);

            String priority =
                    result.priorities.get(i);

            response.append("<li>&#10007; ")
                    .append(skill)
                    .append(" - <span class=\"")
                    .append(priority.toLowerCase())
                    .append("\">")
                    .append(priority)
                    .append("</span></li>");
        }

        response.append("""
                    </ul>

                </div>

                <div class="section">

                    <h2>Recommended Learning Order</h2>

                    <ol>
                """);

        for (int i = 0; i < result.missingSkills.size(); i++) {

            String skill =
                    result.missingSkills.get(i);

            String priority =
                    result.priorities.get(i);

            response.append("<li>")
                    .append(skill)
                    .append(" - <span class=\"")
                    .append(priority.toLowerCase())
                    .append("\">")
                    .append(priority)
                    .append("</span></li>");
        }

        response.append("""
                    </ol>

                </div>

                </div>

                </div>

                </body>
                </html>
                """);

        String responseText =
                response.toString();

        responseText =
                responseText.replace(
                        "Target Role:&nbsp;",
                        "Target Role:&nbsp;"
                );

        exchange.getResponseHeaders().set(
                "Content-Type",
                "text/html; charset=UTF-8"
        );

        byte[] responseBytes =
                responseText.getBytes(
                        StandardCharsets.UTF_8
                );

        exchange.sendResponseHeaders(
                200,
                responseBytes.length
        );

        OutputStream output =
                exchange.getResponseBody();

        output.write(responseBytes);
        output.close();
    }
}
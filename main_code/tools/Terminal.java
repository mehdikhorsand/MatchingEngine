package tools;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import main.Settings;

public class Terminal {

    static ProcessBuilder processBuilder = new ProcessBuilder();

    public static String run_command_with_output(String command) {
        processBuilder.command("sh", "-c", command);
        StringBuilder output = new StringBuilder();
        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            int exitVal = process.waitFor();
            if (exitVal != 0) {
                System.out.println(ConsoleColors.RED + "\n# " + command + ConsoleColors.RESET);
                BufferedReader error_reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                while((line = error_reader.readLine()) != null)
                    System.out.println(ConsoleColors.RED + line + ConsoleColors.RESET);
            }
            else {
                System.out.println(ConsoleColors.GREEN + "\n# " + command + ConsoleColors.RESET);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(ConsoleColors.RED + "Error running command: " + command + ConsoleColors.RESET);
            e.printStackTrace();
        }
        return String.valueOf(output);
    }

    public static void run_command(String command) {
        System.out.println(run_command_with_output(command));
    }

    public static void copy(String source_address, String destination_address) {
        run_command("mkdir -p " + destination_address + " && cp -r " + source_address + " " + destination_address);
    }

    public static void mkdir(String path) {
        run_command("mkdir -p " + path);
    }

    public static void rm(String path) {
        run_command("rm -rf " + path);
    }

    public static void run_jacoco(String mode) {
        System.out.println(ConsoleColors.BLUE_BOLD + "Running Jacoco. (mode:" + mode + ")" + ConsoleColors.RESET);
        run_command("/usr/lib/jvm/java-11-openjdk/bin/java " +
                "-Dmaven.multiModuleProjectDirectory=/home/mehdi/IdeaProjects/MatchingEngine " +
                "-Djansi.passthrough=true -Dmaven.home=/usr/share/idea/plugins/maven/lib/maven3 " +
                "-Dclassworlds.conf=/usr/share/idea/plugins/maven/lib/maven3/bin/m2.conf " +
                "-Dmaven.ext.class.path=/usr/share/idea/plugins/maven/lib/maven-event-listener.jar " +
                "-javaagent:/usr/share/idea/lib/idea_rt.jar=37225:/usr/share/idea/bin -Dfile.encoding=UTF-8 " +
                "-classpath /usr/share/idea/plugins/maven/lib/maven3/boot/plexus-classworlds-2.6.0.jar:" +
                "/usr/share/idea/plugins/maven/lib/maven3/boot/plexus-classworlds.license " +
                "org.codehaus.classworlds.Launcher -Didea.version=2023.1.4 " + mode);
    }

    public static void run_pitest() {
        System.out.println(ConsoleColors.BLUE_BOLD + "Running PiTest." + ConsoleColors.RESET);
        run_command("/usr/lib/jvm/java-11-openjdk/bin/java " +
                "-javaagent:/usr/share/idea/lib/idea_rt.jar=44945:/usr/share/idea/bin " +
                "-Dfile.encoding=UTF-8 " +
                "-classpath /home/mehdi/.local/share/JetBrains/IdeaIC2023.1/pit-idea-plugin/lib/" +
                "pitest-junit5-plugin-1.1.2.jar:/home/mehdi/.local/share/JetBrains/IdeaIC2023.1/" +
                "pit-idea-plugin/lib/commons-text-1.10.0.jar:/home/mehdi/.local/share/JetBrains/" +
                "IdeaIC2023.1/pit-idea-plugin/lib/commons-lang3-3.12.0.jar:/home/mehdi/.local/share/" +
                "JetBrains/IdeaIC2023.1/pit-idea-plugin/lib/pitest-entry-1.11.6.jar:/home/mehdi/.local/" +
                "share/JetBrains/IdeaIC2023.1/pit-idea-plugin/lib/pitest-command-line-1.11.6.jar:" +
                "/home/mehdi/.local/share/JetBrains/IdeaIC2023.1/pit-idea-plugin/lib/pitest-1.11.6.jar:" +
                "/home/mehdi/IdeaProjects/MatchingEngine/target/test-classes:" +
                "/home/mehdi/IdeaProjects/MatchingEngine/target/classes:" +
                "/home/mehdi/.m2/repository/org/junit/jupiter/junit-jupiter-engine/5.5.2/junit-jupiter-engine-5.5.2.jar:" +
                "/home/mehdi/.m2/repository/org/apiguardian/apiguardian-api/1.1.0/apiguardian-api-1.1.0.jar:" +
                "/home/mehdi/.m2/repository/org/junit/platform/junit-platform-engine/1.5.2/junit-platform-engine-1.5.2.jar:" +
                "/home/mehdi/.m2/repository/org/opentest4j/opentest4j/1.2.0/opentest4j-1.2.0.jar:/home/mehdi/.m2/repository/org/" +
                "junit/platform/junit-platform-commons/1.5.2/junit-platform-commons-1.5.2.jar:" +
                "/home/mehdi/.m2/repository/org/junit/jupiter/junit-jupiter-api/5.5.2/junit-jupiter-api-5.5.2.jar:" +
                "/home/mehdi/.m2/repository/junit/junit/4.12/junit-4.12.jar:" +
                "/home/mehdi/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar:" +
                "/home/mehdi/.m2/repository/org/jetbrains/annotations/24.0.1/annotations-24.0.1.jar " +
                "org.pitest.mutationtest.commandline.MutationCoverageReport " +
                "--reportDir /home/mehdi/IdeaProjects/MatchingEngine/target/report " +
                "--sourceDirs /home/mehdi/IdeaProjects/MatchingEngine/src/main/java " +
                "--targetClasses matchingEngine.Broker,matchingEngine.Shareholder,matchingEngine.MatchingEngine," +
                "matchingEngine.Environment,matchingEngine.Trade,matchingEngine.Order,matchingEngine.OrderBook " +
                "--targetTests TestCaseRunner --outputFormats XML,HTML");
    }

    public static void mv(String source_address, String destination_address) {
        run_command("mv " + source_address + " " + destination_address);
    }

    public static String cat(String file_path) {
        return run_command_with_output("cat " + file_path);
    }

    public static String run_oracle(String input_file_path) {
        return run_command_with_output("." + Settings.oracle + "/GetTCTraces --trades " + input_file_path);
    }

    public static void run_oracle(String input_file_path, String output_file_path) {
        run_command("." + Settings.oracle + "/GetTCTraces --trades " + input_file_path + " > " + output_file_path);
    }

    public static void browse(String file_paths) {
        if(!(file_paths.trim().equals("")))
            Terminal.run_command("google-chrome-stable " + file_paths);
    }

    public static void change_directory(String path) {
        processBuilder.directory(new File(path));
    }
}

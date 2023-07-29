import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Terminal {

    static ProcessBuilder processBuilder = new ProcessBuilder();

    public static String run_command_with_output(String command){
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
            if (exitVal != 0)
                System.out.println("Command: " + command + "\nfailure!");
        } catch (IOException | InterruptedException e) {
            System.out.println("Error running command: " + command);
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

    public static void run_maven_project(String mode) {
        System.out.println("Running maven project. (mode:" + mode + ")");
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

    public static void mv(String source_address, String destination_address) {
        run_command("mv " + source_address + " " + destination_address);
    }

    public static String cat(String file_path) {
        return run_command_with_output("cat " + file_path);
    }

    public static String run_oracle(String input_file_path) {
        return run_command_with_output("./" + Settings.oracle + "/GetTCTraces --trades " + input_file_path);
    }

    public static void run_oracle(String input_file_path, String output_file_path) {
        run_command("./" + Settings.oracle + "/GetTCTraces --trades " + input_file_path + " > " + output_file_path);
    }

    public static void browse(String file_paths) {
        Terminal.run_command("google-chrome-stable " + file_paths);
    }

    public static void change_directory(String path) {
        processBuilder.directory(new File(path));
    }
}

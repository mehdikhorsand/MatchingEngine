import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Terminal {

    static ProcessBuilder processBuilder = new ProcessBuilder();

    public static String run_command_with_output(String command, boolean print_in_console){
        if (print_in_console)
            System.out.println("Command: " + command);
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
            if(print_in_console)
                if (exitVal == 0)
                    System.out.println("success!");
                else
                    System.out.println("failure!");
        } catch (IOException | InterruptedException e) {
            System.out.println("Error running command: " + command);
            e.printStackTrace();
        }
        return String.valueOf(output);
    }

    public static void run_command(String command) {
        System.out.println(run_command_with_output(command, false));
    }

    public static void change_directory(String path) {
        processBuilder.directory(new File(path));
    }
}

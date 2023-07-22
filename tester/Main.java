public class Main extends Thread{
    public void run() {
        get_haskell_testcase_output();
    }

    public static void main(String[] args) {
        Main thread = new Main();
        thread.start();
        get_java_testcase_output();
        if(!thread.isAlive())
            EvaluateOutputs.evaluation("TCs/RT/tc-java-res.txt", "TCs/RT/tc-hs-res.txt");
    }

    public static void get_java_testcase_output() {
        new TCRunner("TCs/RT/tc.txt", "TCs/RT/tc-java-res.txt");
    }

    public static void get_haskell_testcase_output() {
        Terminal.run_command(
                "oracle/GetTCTraces --trades TCs/RT/tc.txt > TCs/RT/tc-hs-res.txt");
    }
}

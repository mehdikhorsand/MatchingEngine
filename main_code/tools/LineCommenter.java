//package tools;
//
//import source.TCRunner;
//
//public class LineCommenter {
//    private static void rebuild_src() {
//        Terminal.run_command("javac -d target/test-classes src/test/java/*.java");
//        Terminal.run_command("javac -d target/classes src/main/java/matchingEngine/*.java main_code/*/*.java main_code/*/*/*.java");
//    }
//
//    public static void comment_lines(String[] target_lines) {
//        commenter(target_lines, true);
//        rebuild_src();
//    }
//
//    public static void uncomment_lines(String[] target_lines) {
//        commenter(target_lines, false);
//        rebuild_src();
//    }
//
//    private static void commenter(String[] target_lines, boolean comment) {
//        String target_prefix =      (comment)? "":"//";
//        String replacement_prefix = (comment)? "//":"";
//        for(Class src_class : TCRunner.get_src_classes()) {
//            String class_path = "src/main/java/" + src_class.getName().replace(".", "/") + ".java";
//            String class_content = Terminal.cat(class_path);
//            for(String template : target_lines) {
//                class_content = class_content.replace(target_prefix + template, replacement_prefix + template);
//            }
//            Terminal.run_command_with_output("echo '" + class_content + "' > " + class_path);
//        }
//    }
//}

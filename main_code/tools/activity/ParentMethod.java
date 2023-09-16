package tools.activity;

public class ParentMethod {
    String class_path;
    String class_name;
    public String method_name;

    public ParentMethod(StackTraceElement element) {
        assert element.getFileName() != null;
        this.class_path = element.getClassName();
//        this.class_name = element.getFileName().replace(".java", "");
        String[] spelt_class_path = class_path.split("\\.");
        this.class_name = spelt_class_path[spelt_class_path.length-1];
        this.method_name = element.getMethodName();
    }

    @Override
    public String toString() {
        return class_name + "." + method_name;
    }

    @Override
    public boolean equals(Object obj) {
        try{
            ParentMethod another = (ParentMethod) obj;
            return this.class_path.equals(another.class_path) && this.method_name.equals(another.method_name);
        }
        catch (Exception e) {
            return false;
        }
    }
}

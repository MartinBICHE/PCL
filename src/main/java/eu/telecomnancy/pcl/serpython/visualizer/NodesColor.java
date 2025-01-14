package eu.telecomnancy.pcl.serpython.visualizer;

public class NodesColor {
    public static String IfElseForColor(String nodeName) {
        return "style " + nodeName + " fill:#ffcccc,stroke:#ff0000,color:#000000";
    }

    public static String OperatorColor(String nodeName) {
        return "style " + nodeName + " fill:#ccccff,stroke:#0000ff,color:#000000";
    }

    public static String StringColor(String nodeName) {
        return "style " + nodeName + " fill:#ccffcc,stroke:#00ff00,color:#000000";
    }

    public static String FunctionColor(String nodeName) {
        return "style " + nodeName + " fill:#ffffcc,stroke:#ffff00,color:#000000";
    }
}

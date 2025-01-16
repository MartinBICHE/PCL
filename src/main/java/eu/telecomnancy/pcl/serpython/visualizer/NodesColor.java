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

    public static String NumberColor(String nodeName) {
        return "style " + nodeName + " fill:#e0e0e0,stroke:#000000,color:#000000";
    }

    public static String IdentifierColor(String nodeName) {
        return "style " + nodeName + " fill:#ffffff,stroke:#000000,color:#000000";
    }

    public static String NoneTrueFalseColor(String nodeName) {
        return "style " + nodeName + " fill:#ffefcc,stroke:#ff5f00,color:#000000";
    }
}

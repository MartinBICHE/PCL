package eu.telecomnancy.pcl.serpython.ast;

import java.util.ArrayList;

public class Function extends AbstractNode {
    private Identifier name;
    private ArrayList<Identifier> arguments;
    private Block instruBlock;

    public Function(Identifier name, ArrayList<Identifier> arguments, Block  instruBlock){
        this.name = name;
        this.arguments= arguments;
        this.instruBlock = instruBlock;
    }

    public Identifier getName(){
        return name;
    }

    public ArrayList<Identifier> getArguments(){
        return arguments;
    }

    public Block getInstructions(){
        return this.instruBlock;
    }
}

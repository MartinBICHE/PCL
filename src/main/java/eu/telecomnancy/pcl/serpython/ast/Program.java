package eu.telecomnancy.pcl.serpython.ast;

import java.util.ArrayList;

public class Program {
    private ArrayList<Function> functions;
    private Block block;

    public Program(ArrayList<Function> functions,Block block){
        this.block = block;
        this.functions=functions;
    }
    
    public ArrayList<Function> getFunctions(){
        return this.functions;
    }

    public Block getBlock(){
        return block;
    }
}

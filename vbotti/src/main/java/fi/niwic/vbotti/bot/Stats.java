package fi.niwic.vbotti.bot;

public class Stats {

    private final int treeNodes;
    private int nodesChecked;
    
    public Stats(int treeNodes) {
        this.treeNodes = treeNodes;
        nodesChecked = 0;
    }
    
    public int getTreeNodes() {
        return treeNodes;
    }
    
    public void increaseNodesChecked() {
        nodesChecked++;
    }
    
    public int getNodesChecked() {
        return nodesChecked;
    }
    
}

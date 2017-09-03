package fi.niwic.vbotti.bot;

/**
 * Luokka jolla tallenetaa botin suorituksen statistiikkaa.
 */
public class Stats {

    private final int treeNodes;
    private int nodesChecked;
    
    /**
     * Luo uuden instanssin, ja alustaa tarkistettujen solmujen määrän nollaan.
     * 
     * @param treeNodes puun solmujen kokonaismäärä
     */
    public Stats(int treeNodes) {
        this.treeNodes = treeNodes;
        nodesChecked = 0;
    }
    
    /**
     * Palauttaa puun solmujen kokonaismäärän.
     * 
     * @return solmujen kokonais määrä
     */
    public int getTreeNodes() {
        return treeNodes;
    }
    
    /**
     * Lisää tarkistettujen solmujen määrän yhdellä.
     */
    public void increaseNodesChecked() {
        nodesChecked++;
    }
    
    /**
     * Palauttaa tarkistettujen solmujen määrän.
     * 
     * @return tarkistetut solmut
     */
    public int getNodesChecked() {
        return nodesChecked;
    }
    
}

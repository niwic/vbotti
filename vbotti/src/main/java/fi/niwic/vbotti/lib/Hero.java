package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;
import fi.niwic.util.ArrayList;

/**
 * Pelaajaa kuvaava luokka.
 */
public class Hero extends HasPosition implements Tile  {

    private int id;
    private int life;
    private int mines;
    private int gold;
    
    private GameState.Position respawnPosition;

    /**
     * Luo uuden instanssin herosta peliklientin hero instanssista.
     * 
     * @param hero hero
     */
    public Hero(GameState.Hero hero) {
        this(hero.getId(), hero.getLife(), hero.getMineCount(), hero.getGold(), hero.getPos(), hero.getSpawnPos());
    }
    
    /**
     * Luo uuden instanssi herosta annettuilla parametreillä.
     * 
     * @param id heron id
     * @param life heron life määrä
     * @param mines heron kultakaivosten määrä
     * @param gold heron kullan määrä
     * @param position heron paikka pelikentällä
     * @param respawnPosition heron respawn paikka pelikentällä
     */
    public Hero(int id, int life, int mines, int gold, GameState.Position position, GameState.Position respawnPosition) {
        this.id = id;
        this.life = life;
        this.mines = mines;
        this.gold = gold;
        this.position = position;
        this.respawnPosition = respawnPosition;
    }

    /**
     * Palauttaa heron idn.
     * 
     * @return heron id
     */
    public int getId() {
        return id;
    }
    
    /**
     * Palauttaa heron respawn paikan.
     * 
     * @return respawn paikka
     */
    public GameState.Position getRespawnPos() {
        return respawnPosition;
    }

    /**
     * Palauttaa heron kultakaivosten lukumäärän.
     * 
     * @return kultakaivosten lukumäärä
     */
    public int getMineCount() {
        return this.mines;
    }
    
    /**
     * Asettaa heron kultakavosten lukumäärän.
     * 
     * @param count kultakaivosten lukumäärä
     */
    public void setMineCount(int count) {
        this.mines = count;
    }
    
    /**
     * Palauttaa heron kullan määrän.
     * 
     * @return kullan määrä
     */
    public int getGold() {
        return gold;
    }
    
    /**
     * Asettaa heron kullan määrän.
     * 
     * @param gold kullan määrä
     */
    public void setGold(int gold) {
        this.gold = gold;
    }
    
    /**
     * Palauttaa heron life määrän.
     * 
     * @return life määrä
     */
    public int getLife() {
        return life;
    }
    
    /**
     * Asettaa heron lifen määrä.
     * 
     * Heron life ei voi olla yli 100.
     * 
     * @param life lifen määrä
     */
    public void setLife(int life) {
        if (life > 99) {
            this.life = 100;
        } else {
            this.life = life;
        }
    }
    
    /**
     * Onko pelaaja kuollut?
     * 
     * @return onko vai ei
     */
    public boolean isDead() {
        return (life < 1);
    }
    
    /**
     * Palauttaa kopion herosta.
     * 
     * @return kopio
     */
    public Hero copy() {
        return new Hero(this.id, this.life, this.mines, this.gold, this.position, this.respawnPosition);
    }
    
    /**
     * Palauttaa kopion herosta uudella sijainnilla.
     * 
     * @param position uusi sijainti
     * @return kopio uudella sijainilla
     */
    public Hero copy(GameState.Position position) {
        return new Hero(this.id, this.life, this.mines, this.gold, position, this.respawnPosition);
    }
    
    /**
     * Suorittaa respawnlogiikan heron osalta.
     * 
     * Siirtää pelaajan respawan paikkaan &amp; asettaa life sataan.
     */
    public void respawn() {
        this.position = respawnPosition;
        this.life = 100;
    }
    
    /**
     * Suorittaa kuolemislogiikan heron osalta
     * 
     * Asetetaan kaivosten lukumäärä nollaksi, ja poistetaan omistajuus
     * kaivoksista.
     * 
     * @param mines kaikki kultakaivoksen
     */
    public void die(ArrayList<GoldMine> mines) {
        die(mines, 0);
    }
    
    /**
     * Suorittaa kuolemislogiikan heron osalta, jos vastustaja tappaa.
     * 
     * Asetetaan heron kaivosten lukumäärän nollaan, ja siirretään kaivosten
     * omistus vastustajalle.
     * 
     * @param mines kaikki kultakaivokset
     * @param killedBy vastustaja joka tappo heron
     */
    public void die(ArrayList<GoldMine> mines, Hero killedBy) {
        killedBy.setMineCount(killedBy.getMineCount() + this.mines);
        die(mines, killedBy.getId());
    }
    
    private void die(ArrayList<GoldMine> mines, int newOwner) {
        this.life = 0;
        this.mines = 0;
        for (GoldMine mine : mines) {
            if (mine.isOwnedBy(id)) {
                mine.setOwner(newOwner);
            }
        }
    }
    
    @Override
    public void onMoveInto(State state, Hero hero) {
        /* nothing */
    }
    
    @Override
    public boolean isMovePossible() {
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        sb.append("@(");
        sb.append(position.getX());
        sb.append("/");
        sb.append(position.getY());
        sb.append(") life: ");
        sb.append(life);
        sb.append(" gold: ");
        sb.append(gold);
        sb.append(" mines: ");
        sb.append(mines);
        
        return sb.toString();
    }
        
}

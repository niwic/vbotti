package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;
import java.util.List;

public class Hero extends HasPosition implements Tile  {

    private int id;
    private int life;
    private int mines;
    private int gold;
    
    private GameState.Position respawnPosition;

    public Hero(GameState.Hero hero) {
        this(hero.getId(), hero.getLife(), hero.getMineCount(), hero.getGold(), hero.getPos(), hero.getSpawnPos());
    }
    
    public Hero(int id, int life, int mines, int gold, GameState.Position position, GameState.Position respawnPosition) {
        this.id = id;
        this.life = life;
        this.mines = mines;
        this.gold = gold;
        this.position = position;
        this.respawnPosition = respawnPosition;
    }

    public int getId() {
        return id;
    }
    
    public GameState.Position getRespawnPos() {
        return respawnPosition;
    }

    public int getMineCount() {
        return this.mines;
    }
    
    public void setMineCount(int count) {
        this.mines = count;
    }
    
    public int getGold() {
        return gold;
    }
    
    public void setGold(int gold) {
        this.gold = gold;
    }
    
    public int getLife() {
        return life;
    }
    
    public void setLife(int life) {
        if (life > 99) {
            this.life = 100;
        } else {
            this.life = life;
        }
    }
    
    public boolean isDead() {
        return (life < 1);
    }
    
    public Hero copy() {
        return new Hero(this.id, this.life, this.mines, this.gold, this.position, this.respawnPosition);
    }
    
    public Hero copy(GameState.Position position) {
        return new Hero(this.id, this.life, this.mines, this.gold, position, this.respawnPosition);
    }
    
    public void respawn() {
        this.position = respawnPosition;
        this.life = 100;
    }
    
    public void die(List<GoldMine> mines) {
        die(mines, 0);
    }
    
    public void die(List<GoldMine> mines, Hero killedBy) {
        killedBy.setMineCount(killedBy.getMineCount() + this.mines);
        die(mines, killedBy.getId());
    }
    
    private void die(List<GoldMine> mines, int newOwner) {
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
        return "@" + id;
    }
        
}

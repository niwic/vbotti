package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;

public class Hero extends HasPosition implements Tile  {

    private int id;
    private int life;
    private int mines;
    private int gold;

    public Hero(GameState.Hero hero) {
        this(hero.getId(), hero.getLife(), hero.getMineCount(), hero.getGold(), hero.getPos());
    }
    
    public Hero(int id, int life, int mines, int gold, GameState.Position position) {
        this.id = id;
        this.life = life;
        this.mines = mines;
        this.gold = gold;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public GameState.Position getPos() {
        return position;
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
        if (life > 100) {
            this.life = 100;
        } else {
            this.life = life;
        }
    }
    
    public boolean isDead() {
        return (life < 1);
    }
    
    public Hero copy() {
        return new Hero(this.id, this.life, this.mines, this.gold, this.position);
    }
    
    public Hero copy(GameState.Position position) {
        return new Hero(this.id, this.life, this.mines, this.gold, position);
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

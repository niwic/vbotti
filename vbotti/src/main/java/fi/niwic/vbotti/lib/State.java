package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;

/**
 * Ote pelitilanteesta mistä pystyy hakemaan myös pelitilanteen mutaatioita.
 * 
 * @author nic
 */
public class State {

    Board board;
    Hero me;
    Hero[] heroes;
    
    public State(GameState gameState) {
        setHeroes(gameState);
        this.me = new Hero(gameState.getHero());
        this.board = new Board(gameState.getGame().getBoard());
    }
    
    private State(Board board, Hero[] heroes, Hero me) {
        this.board = board;
        this.heroes = heroes;
        this.me = me;
    }
    
    private void setHeroes(GameState gs) {
        this.heroes = new Hero[gs.getGame().getHeroes().size() + 1];
        for (GameState.Hero hero : gs.getGame().getHeroes()) {
            this.heroes[hero.getId()] = new Hero(hero);
        }
    }
    
    /**
     * Palauttaa tässä paikassa olevan ruudun.
     * 
     * @param position paikka joka tarkistetaan
     * @return pakassa oleva ruutu
     */
    private Tile getTile(GameState.Position position) {
        Tile boardTile = board.getTile(position);
        if (boardTile instanceof Free) {
            int hero = getHero(position);
            if (hero > -1) return heroes[hero];
            else return boardTile;
        } else {
            return boardTile;
        }
    }
    
    /**
     * Palauttaa hero idn jos sellainen löytyy annetusta positiosta
     * @param position positio joka tarkistetaan
     * @return hero id, tai -1 jos ei löydy
     */
    private int getHero(GameState.Position position) {
        for (int i = 0; i < heroes.length; i++) {
            if (isHero(position, heroes[i])) return i;
        }
        
        return -1;
    }
    
    /**
     * Onko tässä paikassa tietty pelaaja?
     * 
     * @param position Paikka
     * @return kyllä/ei
     */
    private boolean isHero(GameState.Position position, Hero hero) {
        return hero.getPosition().getX() == position.getX()
                && hero.getPosition().getY() == position.getY();
    }
    
    /**
     * Onko tässä paikassa pelaaja?
     * 
     * @param position Paikka
     * @return kyllä/ei
     */
    public boolean isHero(GameState.Position position) {
        return (getTile(position) instanceof Hero);
    }
    
    /**
     * Palauttaa mutatoidun pelitilanteen, missä kyseinen hero on siirretty
     * pyydettyyn paikkaan. Ei muuta tätä pelitilannetta.
     * 
     * @param hero hero joka siirretään
     * @param position paikka johon hero siirretään
     * @return uusi pelitilanne missä hero siirretty
     */
    public State move(GameState.Hero hero, GameState.Position position) {
        if (!board.isMovePossible(position)) return this;
        
        Tile target = getTile(position);
        
        if (!target.isMovePossible()) {
            position = heroes[hero.getId()].getPosition();
        }
        
        Hero[] mutatedHeroList = new Hero[heroes.length];
        for (int i = 0; i < heroes.length; i++) {
            if (heroes[i].getId() == hero.getId()) {
                mutatedHeroList[i] = heroes[i].copy(position);
            } else {
                mutatedHeroList[i] = heroes[i].copy();
            }
        }
        
        Hero mutatedMe = mutatedHeroList[hero.getId()];
        
        State newState = new State(board.copy(), mutatedHeroList, mutatedMe);
        target = newState.getTile(position);
        target.onMoveInto(newState, heroes[hero.getId()]);
        newState.evaluateEndOfTurn(heroes[hero.getId()]);
        
        return newState;
    }

    private void evaluateEndOfTurn(Hero hero) {
        fight(hero);
        doTheMining(hero);
        drink(hero);
        respawnDeadPeople();
    }
    
    private void fight(Hero hero) {
        for (Hero opponent : heroes) {
            if (hero.getId() != opponent.getId()) {
                if (Math.abs(hero.getPosition().getX() - opponent.getPosition().getX()) == 1 
                    && Math.abs(hero.getPosition().getY() - opponent.getPosition().getY()) == 1) {
                    fight(hero, opponent);
                }
            }
        }
    }
    
    private void fight(Hero hero, Hero opponent) {
        opponent.setLife(opponent.getLife() - 20);
        if (opponent.isDead()) {
            int mines = opponent.getMineCount();
            hero.setMineCount(hero.getMineCount() + mines);
            opponent.setMineCount(0);
            for (GoldMine mine : board.getMines()) {
                if (mine.isOwnedBy(opponent.getId())) {
                    mine.setOwner(hero.getId());
                }
            }
        }
    }
    
    private void doTheMining(Hero hero) {
        hero.setGold(hero.getGold() + hero.getMineCount());
    }
    
    private void drink(Hero hero) {
        if (hero.getLife() > 1) {
            hero.setLife(hero.getLife() - 1);
        }
    }
    
    private void respawnDeadPeople() {
        int killed = 0;
        for (Hero hero : heroes) {
            if (hero.isDead()) {
                GameState.Position respawnPos = hero.getRespawnPos();
                Tile respawnPosTile = getTile(respawnPos);
                if (respawnPosTile instanceof Hero) {
                    ((Hero) respawnPosTile).die();
                    killed++;
                }
                hero.respawn(board.getMines());
            }
        }
        
        if (killed > 0) {
            respawnDeadPeople();
        }
    }
    
}

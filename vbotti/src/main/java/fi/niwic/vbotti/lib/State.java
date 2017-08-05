package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class State {

    Board board;
    GameState.Hero me;
    GameState.Hero[] heroes;
    
    public State(GameState gameState) {
        setHeroes(gameState);
        this.me = gameState.getHero();
        this.board = new Board(gameState.getGame().getBoard());
    }
    
    private State(Board board, GameState.Hero[] heroes, GameState.Hero me) {
        this.board = board;
        this.heroes = heroes;
        this.me = me;
    }
    
    private void setHeroes(GameState gs) {
        this.heroes = new GameState.Hero[gs.getGame().getHeroes().size()];
        for (GameState.Hero hero : gs.getGame().getHeroes()) {
            this.heroes[hero.getId()] = hero;
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
            if (hero > -1) return new Hero(heroes[hero]);
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
     * Keroo voidaanko siirtyä tähän paikkaan.
     * 
     * @param position Paikka johon voisi siirtyä
     * @return kyllä/ei
     */
    public boolean isMovePossible(GameState.Position position) {
        if (!board.isMovePossible(position)) return false;
        return getTile(position).isMovePossible();
    }
    
    /**
     * Onko tässä paikassa tietty pelaaja?
     * 
     * @param position Paikka
     * @return kyllä/ei
     */
    private boolean isHero(GameState.Position position, GameState.Hero hero) {
        return hero.getPos().getX() == position.getX()
                && hero.getPos().getY() == position.getY();
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
        if (!isMovePossible(position)) return this;
        
        GameState.Hero[] mutatedHeroList = new GameState.Hero[heroes.length];
        for (int i = 0; i < heroes.length; i++) {
            if (heroes[i].getId() == hero.getId()) {
                mutatedHeroList[i] = createHero(heroes[i], position);
            } else {
                mutatedHeroList[i] = createHero(heroes[i]);
            }
        }
        
        GameState.Hero mutatedMe = mutatedHeroList[hero.getId()];
        
        return new State(board, mutatedHeroList, mutatedMe);
    }
    
    private GameState.Hero createHero(GameState.Hero hero) {
        return new GameState.Hero(
                hero.getId(),
                hero.getName(),
                hero.getUserId(),
                hero.getElo(),
                hero.getPos(),
                hero.getLife(),
                hero.getGold(),
                hero.getMineCount(),
                hero.getSpawnPos(),
                hero.isCrashed()
        );
    }
    
    private GameState.Hero createHero(GameState.Hero hero, GameState.Position position) {
        return new GameState.Hero(
                hero.getId(),
                hero.getName(),
                hero.getUserId(),
                hero.getElo(),
                position,
                hero.getLife(),
                hero.getGold(),
                hero.getMineCount(),
                hero.getSpawnPos(),
                hero.isCrashed()
        );
    }
    
    /**
     * Evaluoi uuden pelitianteen siirron jälkeen.
     * 
     * Päivittää hpt, kullan määrän ja kaivosten omistuksen.
     * 
     * @param hero Hero kenen vuoro on
     */
    public void evaluateTurn(GameState.Hero hero) {
        throw new NotImplementedException();
    }
    
}

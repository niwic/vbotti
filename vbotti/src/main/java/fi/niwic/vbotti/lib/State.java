package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;
import java.util.List;

/**
 * Ote pelitilanteesta mistä pystyy hakemaan myös pelitilanteen mutaatioita.
 * 
 * @author nic
 */
public class State {

    private int turn;
    private int maxTurns;
    private Board board;
    private Hero me;
    private Hero[] heroes;
    
    public State(GameState gameState) {
        setHeroes(gameState);
        this.turn = gameState.getGame().getTurn();
        this.me = new Hero(gameState.getHero());
        this.board = new Board(gameState.getGame().getBoard());
        this.maxTurns = gameState.getGame().getMaxTurns();
    }
    
    private State(Board board, Hero[] heroes, Hero me, int turn, int maxTurns) {
        this.board = board;
        this.heroes = heroes;
        this.me = me;
        this.turn = turn;
        this.maxTurns = maxTurns;
    }
    
    private void setHeroes(GameState gs) {
        this.heroes = new Hero[gs.getGame().getHeroes().size() + 1];
        for (GameState.Hero hero : gs.getGame().getHeroes()) {
            this.heroes[hero.getId()] = new Hero(hero);
        }
    }
    
    /**
     * Palauttaa kaikki pelissä olevat herot.
     * 
     * @return kaikki herot
     */
    public Hero[] getHeroes() {
        return heroes;
    }
    
    /**
     * Palauttaa pelaajan hero instanssin.
     * 
     * @return hero instanssi
     */
    public Hero getMe() {
        return me;
    }
    
    /**
     * Palauttaa kaikki kentän kultakaivokset.
     * 
     * @return kentänt kultakaivokset
     */
    public List<GoldMine> getMines() {
        return board.getMines();
    }
    
    /**
     * Palauttaa menossa olevan vuoron numeron.
     * 
     * @return vuoron numero
     */
    public int getTurn() {
        return turn;
    }
    
    /**
     * Onko peli loppu?
     * 
     * @return onko peli loppu?
     */
    public boolean isFinished() {
        return turn >= maxTurns;
    }
    
    /**
     * Palauttaa pelikentän.
     * 
     * @return pelikenttä
     */
    public Board getBoard() {
        return board;
    }
    
    /**
     * Kertoo pelin tuloksen.
     * 
     * Pelin tulos on (oma kulta määrä) - (parhaan vastustajan kultamäärä)
     * 
     * @return pelin tulos
     */
    public int getResult() {
        int bestOpponent = 0;
        for (int i = 1; i < heroes.length; i++) {
            if (i != me.getId() && heroes[i].getGold() > bestOpponent) {
                bestOpponent = heroes[i].getGold();
            }
        }
        
        return me.getGold() - bestOpponent;
    }
    
    /**
     * Palauttaa tässä paikassa olevan ruudun.
     * 
     * @param position paikka joka tarkistetaan
     * @return paikassa oleva ruutu
     */
    private Tile getTile(GameState.Position position) {
        Tile boardTile = board.getTile(position);
        if (boardTile instanceof Free) {
            int hero = getHero(position);
            if (hero > 0) return heroes[hero];
            else return boardTile;
        } else {
            return boardTile;
        }
    }
    
    /**
     * Palauttaa hero idn jos sellainen löytyy annetusta positiosta
     * @param position positio joka tarkistetaan
     * @return hero id, tai 0 jos ei löydy
     */
    private int getHero(GameState.Position position) {
        for (int i = 1; i < heroes.length; i++) {
            if (isHero(position, heroes[i])) return i;
        }
        
        return 0;
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
     * Palauttaa mutatoidun pelitilanteen, missä kyseinen hero on siirretty
     * pyydettyyn paikkaan. Ei muuta tätä pelitilannetta.
     * 
     * @param heroId heron id joka siirretään
     * @param move suunta minne siirretään
     * @return uusi pelitilanne missä hero siirretty
     */
    public State move(int heroId, Move move) {
        
        Hero hero = heroes[heroId];
        GameState.Position moveToPosition = move.from(hero.getPosition());
        Tile target = getTile(moveToPosition);
        
        GameState.Position nextPosition = moveToPosition;
        if (!board.isMovePossible(moveToPosition) || !target.isMovePossible()) {
            nextPosition = hero.getPosition();
        }
        
        Hero[] mutatedHeroList = new Hero[heroes.length];
        for (int i = 1; i < heroes.length; i++) {
            if (heroes[i].getId() == hero.getId()) {
                mutatedHeroList[i] = heroes[i].copy(nextPosition);
            } else {
                mutatedHeroList[i] = heroes[i].copy();
            }
        }
        
        Hero mutatedMe = mutatedHeroList[me.getId()];
        
        int nextTurn = turn + 1;
        State newState = new State(board.copy(), mutatedHeroList, mutatedMe, nextTurn, maxTurns);
        target = newState.getTile(moveToPosition);
        target.onMoveInto(newState, mutatedHeroList[heroId]);
        newState.evaluateEndOfTurn(mutatedHeroList[heroId]);
        
        return newState;
    }

    private void evaluateEndOfTurn(Hero hero) {
        fight(hero);
        doTheMining(hero);
        thirst(hero);
        respawnDeadPeople();
    }
    
    private void fight(Hero hero) {
        for (int i = 1; i < heroes.length; i++) {
            Hero opponent = heroes[i];
            if (hero.getId() != opponent.getId()) {
                if (Move.isOneMoveAway(hero.getPosition(), opponent.getPosition())) {
                    fight(hero, opponent);
                }
            }
        }
    }
    
    private void fight(Hero hero, Hero opponent) {
        opponent.setLife(opponent.getLife() - 20);
        if (opponent.isDead()) {
            opponent.die(board.getMines(), hero);
        }
    }
    
    private void doTheMining(Hero hero) {
        hero.setGold(hero.getGold() + hero.getMineCount());
    }
    
    private void thirst(Hero hero) {
        if (hero.getLife() > 1) {
            hero.setLife(hero.getLife() - 1);
        }
    }
    
    private void respawnDeadPeople() {
        int killed = 0;
        for (int i = 1; i < heroes.length; i++) {
            Hero hero = heroes[i];
            if (hero.isDead()) {
                GameState.Position respawnPos = hero.getRespawnPos();
                Tile respawnPosTile = getTile(respawnPos);
                if (respawnPosTile instanceof Hero &&
                        respawnPos != hero.getPosition()) {
                    ((Hero) respawnPosTile).die(board.getMines());
                    killed++;
                }
                hero.respawn();
            }
        }
        
        if (killed > 0) {
            respawnDeadPeople();
        }
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Turn: ");
        sb.append(turn);
        sb.append("/");
        sb.append(maxTurns);
        sb.append(" result: ");
        sb.append(getResult());
        sb.append(System.lineSeparator());
        
        for (int i = 1; i < heroes.length; i++) {
            sb.append(heroes[i].toString());
            sb.append(System.lineSeparator());
        }
        
        return sb.toString();
    }
    
}

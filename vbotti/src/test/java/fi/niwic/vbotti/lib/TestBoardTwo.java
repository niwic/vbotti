package fi.niwic.vbotti.lib;

import com.brianstempin.vindiniumclient.dto.GameState;

public class TestBoardTwo {

    private static int size = 28;
    private static String tiles = 
              "        ##    @1                        @4    ##        "
            + "            ##  ##    ############    ##  ##            "
            + "                ##      ########      ##                "
            + "    $-$-          $-################$-          $-$-    "
            + "  ##          ##          ####          ##          ##  "
            + "        $-            $-        $-            $-        "
            + "  ##        ##  ##                    ##  ##        ##  "
            + "  ##                                                ##  "
            + "    ##  ##              ##    ##              ##  ##    "
            + "$-######            ##$-########$-##            ######$-"
            + "            ##            $-$-            ##            "
            + "      ##                                        ##      "
            + "##  ####                                        ####  ##"
            + "[]        ##  ##          $-$-          ##  ##        []"
            + "[]        ##  ##          $-$-          ##  ##        []"
            + "##  ####                                        ####  ##"
            + "      ##                                        ##      "
            + "            ##            $-$-            ##            "
            + "$-######            ##$-########$-##            ######$-"
            + "    ##  ##              ##    ##              ##  ##    "
            + "  ##                                                ##  "
            + "  ##        ##  ##                    ##  ##        ##  "
            + "        $-            $-        $-            $-        "
            + "  ##          ##          ####          ##          ##  "
            + "    $-$-          $-################$-          $-$-    "
            + "                ##      ########      ##                "
            + "            ##  ##    ############    ##  ##            "
            + "        ##    @2                        @3    ##        ";
 
    
    public static GameState.Board getBoard() {
        return new GameState.Board(tiles, size);
    }
    
}
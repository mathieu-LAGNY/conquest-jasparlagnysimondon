package fr.umontpellier.iut.conquest;

import fr.umontpellier.iut.conquest.strategies.Minmax;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MinMaxTest {

    private Board board;
    private Pawn[][] field;
    Game game;
    private Minmax minMax;
    private Player aiPlayer;
    private Player player2;

    /**
     * Helper functions
     */

    void setup_game() {
        game = new Game(board, minMax, null, null,  null);
        aiPlayer = game.getPlayers()[0];
        player2 = game.getPlayers()[1];
    }

    void create_board_of_size_5() {
        field =  new Pawn[5][5];
        board = new Board(field);
        setup_game();
    }

    /**
     * Tests
     */

    @Test
    void move_should_be_valid_for_depth_1() {
        minMax = new Minmax(1);
        board = new Board(3);
        setup_game();
        board.initField(aiPlayer, player2);
        Move move = minMax.getMove(board, aiPlayer);

        assertTrue(board.isValid(move, aiPlayer));
    }

    @Test
    void move_should_be_valid_for_depth_2() {
        minMax = new Minmax(2);
        board = new Board(5);
        setup_game();
        board.initField(aiPlayer, player2);
        Move move = minMax.getMove(board, aiPlayer);

        assertTrue(board.isValid(move, aiPlayer));
    }

    @Test
    void move_should_be_the_best_possible_for_depth_1() {
        minMax = new Minmax(1);
        board = new Board(3);
        setup_game();
        board.initField(aiPlayer, player2);
        Move move = minMax.getMove(board, aiPlayer);

        assertEquals(1, move.getRow2());
        assertEquals(1, move.getColumn2());
    }

    @Test
    void move_should_be_the_best_possible_for_new_board_of_size_5_and_AI_level_2() {
        minMax = new Minmax(2);
        board = new Board(5);
        setup_game();
        /*
         * __0_1_2_3_4
         * 0|X _ _ _ O
         * 1|_ _ _ _ _
         * 2|_ _ _ _ _
         * 3|_ _ _ _ _
         * 4|O _ _ _ X
         */
        board.initField(aiPlayer, player2);
        Move move = minMax.getMove(board, aiPlayer);
        /*
         * __0_1_2_3_4
         * 0|X X _ _ O
         * 1|_ _ _ _ _
         * 2|_ _ _ _ _
         * 3|_ _ _ _ _
         * 4|O _ _ _ X
         */

        assertEquals(0, move.getRow1());
        assertEquals(0, move.getColumn1());
        assertEquals(0, move.getRow2());
        assertEquals(1, move.getColumn2());
    }

    @Test
    void move_should_be_the_best_for_board_of_size_5_after_1_turn_and_AI_level_2() {
        minMax = new Minmax(2);

        create_board_of_size_5();

        field[0][0] = new Pawn(aiPlayer);
        field[0][1] = new Pawn(aiPlayer);
        field[4][4] = new Pawn(aiPlayer);
        field[0][3] = new Pawn(player2);
        field[0][4] = new Pawn(player2);
        field[4][0] = new Pawn(player2);
        /*
         * __0_1_2_3_4
         * 0|X X _ O O
         * 1|_ _ _ _ _
         * 2|_ _ _ _ _
         * 3|_ _ _ _ _
         * 4|O _ _ _ X
         */

        Move move = minMax.getMove(board, aiPlayer);
        /*
         * __0_1_2_3_4
         * 0|X _ _ X X
         * 1|_ _ _ X _
         * 2|_ _ _ _ _
         * 3|_ _ _ _ _
         * 4|O _ _ _ X
         */

        assertEquals(0, move.getRow1());
        assertEquals(1, move.getColumn1());
        assertEquals(1, move.getRow2());
        assertEquals(3, move.getColumn2());
    }

    @Test
    void move_should_be_the_best_for_board_of_size_5_after_1_turn_and_AI_level_3() {
        minMax = new Minmax(3);

        create_board_of_size_5();

        field[0][0] = new Pawn(aiPlayer);
        field[0][1] = new Pawn(aiPlayer);
        field[4][4] = new Pawn(aiPlayer);
        field[0][3] = new Pawn(player2);
        field[0][4] = new Pawn(player2);
        field[4][0] = new Pawn(player2);
        /*
         * __0_1_2_3_4
         * 0|X X _ O O
         * 1|_ _ _ _ _
         * 2|_ _ _ _ _
         * 3|_ _ _ _ _
         * 4|O _ _ _ X
         */

        Move move = minMax.getMove(board, aiPlayer);
        /*
         * __0_1_2_3_4
         * 0|X _ _ X X
         * 1|_ _ _ X _
         * 2|_ _ _ _ _
         * 3|_ _ _ _ _
         * 4|O _ _ _ X
         */

        assertEquals(0, move.getRow1());
        assertEquals(1, move.getColumn1());
        assertEquals(1, move.getRow2());
        assertEquals(3, move.getColumn2());
    }

    @Test
    void player_should_still_move_if_opponent_has_no_moves_left() {

        minMax = new Minmax(4);

        create_board_of_size_5();

        field[0][0] = new Pawn(player2);
        field[0][1] = new Pawn(player2);
        field[0][2] = new Pawn(aiPlayer);
        field[0][3] = new Pawn(aiPlayer);
        field[0][4] = new Pawn(aiPlayer);
        field[1][0] = new Pawn(player2);
        field[1][1] = new Pawn(player2);
        field[1][2] = new Pawn(aiPlayer);
        field[1][3] = new Pawn(aiPlayer);
        field[1][4] = new Pawn(aiPlayer);
        field[2][0] = new Pawn(player2);
        field[2][1] = new Pawn(player2);
        field[2][2] = new Pawn(aiPlayer);
        field[2][3] = new Pawn(aiPlayer);
        field[2][4] = new Pawn(aiPlayer);
        field[3][0] = new Pawn(player2);
        field[3][1] = new Pawn(player2);
        field[3][2] = new Pawn(aiPlayer);
        field[3][3] = new Pawn(aiPlayer);
        field[3][4] = null;
        field[4][0] = new Pawn(player2);
        field[4][1] = new Pawn(player2);
        field[4][2] = new Pawn(aiPlayer);
        field[4][3] = new Pawn(aiPlayer);
        field[4][4] = new Pawn(aiPlayer);
        /*
         * __0_1_2_3_4
         * 0|O O X O O
         * 1|O O X X X
         * 2|O O X X X
         * 3|O O X X _
         * 4|X O X X X
         */

        Move move = minMax.getMove(board, aiPlayer);

        assertEquals(3, move.getRow2());
        assertEquals(4, move.getColumn2());
    }

    @Test
    void aiPLayer_should_move_from_3_2_to_4_3(){
       /*
        * __0_1_2_3_4
        * 0|_ X X X O
        * 1|X X X X O
        * 2|X X X X X
        * 3|X O X X X
        * 4|O O O _ O
        */

        minMax = new Minmax(4);

        create_board_of_size_5();

        field[0][0] = null;
        field[0][1] = new Pawn(aiPlayer);
        field[0][2] = new Pawn(aiPlayer);
        field[0][3] = new Pawn(aiPlayer);
        field[0][4] = new Pawn(player2);
        field[1][0] = new Pawn(aiPlayer);
        field[1][1] = new Pawn(aiPlayer);
        field[1][2] = new Pawn(aiPlayer);
        field[1][3] = new Pawn(aiPlayer);
        field[1][4] = new Pawn(player2);
        field[2][0] = new Pawn(aiPlayer);
        field[2][1] = new Pawn(aiPlayer);
        field[2][2] = new Pawn(aiPlayer);
        field[2][3] = new Pawn(aiPlayer);
        field[2][4] = new Pawn(aiPlayer);
        field[3][0] = new Pawn(aiPlayer);
        field[3][1] = new Pawn(player2);
        field[3][2] = new Pawn(aiPlayer);
        field[3][3] = new Pawn(aiPlayer);
        field[3][4] = new Pawn(aiPlayer);
        field[4][0] = new Pawn(player2);
        field[4][1] = new Pawn(player2);
        field[4][2] = new Pawn(player2);
        field[4][3] = null;
        field[4][4] = new Pawn(player2);

        Move move = minMax.getMove(board, aiPlayer);

        assertEquals(3, move.getRow1());
        assertEquals(2, move.getColumn1());
        assertEquals(4, move.getRow2());
        assertEquals(3, move.getColumn2());
    }
}
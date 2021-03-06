package fr.umontpellier.iut.conquest.strategies;

import fr.umontpellier.iut.conquest.Board;
import fr.umontpellier.iut.conquest.Move;
import fr.umontpellier.iut.conquest.Player;

import java.util.List;

public class Minmax implements Strategy {

    private final int level;
    private Move bestMove;

    /**
     * Constructeur
     * @param level le niveau de difficulté de l'IA
     */
    public Minmax(int level) {
        this.level = level;
        bestMove = null;
    }

    /**
     * Utilise minimax pour calculer le meilleur coup possible en fonction du niveau / profondeur et du plateau
     * @param board le plateau de jeu dans l'état donné
     * @param player le joueur dont on détermine les coups (IA)
     * @return le meilleur move en fonction de la difficulté et du board donné
     */
    @Override
    public Move getMove(Board board, Player player) {
        Board boardCopy = board.getBoardCopy();
        minimax(boardCopy, player, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, level, true);
        return bestMove;
    }

    /**
     *Retourne le score le plus élevé au prochain tour en fonctions des déplacements disponibles et du nombre de coups
     *calculés à l'avance
     *
     * @param board le plateau de jeu en l'état actuel
     * @param player le joueur pour lequel on calcule les coups possibles
     * @param alpha la meilleure valeur pour le "maximizingPlayer"
     * @param beta la meilleure valeur pour le "minimizingPlayer"
     * @param depth la profondeur de l'arbre = le nombre de coups prévus à l'avance
     * @param maximizingPlayer boolean déterminant si l'on cherche le score maximum ou le score minimum
     * @return le meilleur score pour {@code player}
     */
    public double minimax(Board board, Player player, double alpha, double beta, int depth, boolean maximizingPlayer) {
        Player otherPlayer = player.getGame().getOtherPlayer(player);
        List<Move> playerMovesList = board.getValidMoves(player);
        List<Move> otherPlayerMovesList = board.getValidMoves(otherPlayer);

        if (depth == 0 || player.getGame().isFinished()) {
            return board.getNbPawns(player) - board.getNbPawns(otherPlayer);
        }
        if (maximizingPlayer) {
            if(playerMovesList.isEmpty()) {
                return board.getNbPawns(player) - board.getNbPawns(otherPlayer);
            }
            double maxEval = Double.NEGATIVE_INFINITY;
            for (Move move : playerMovesList) {
                Board currentBoard = board.getBoardCopy();
                currentBoard.movePawn(move);
                double eval = minimax(currentBoard, player, alpha, beta, depth-1, false);
                if (eval > maxEval && depth == level) {
                    setBestMove(move);
                }
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                   break;
                }
            }
            return maxEval;
        } else {
            if(otherPlayerMovesList.isEmpty()) {
                return board.getNbPawns(player) - board.getNbPawns(otherPlayer);
            }
            double minEval = Double.POSITIVE_INFINITY;
            for (Move move : otherPlayerMovesList) {
                Board currentBoard = board.getBoardCopy();
                currentBoard.movePawn(move);
                double eval = minimax(currentBoard, player, alpha, beta,depth-1, true);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }
    }

    /**
     * Setter permettant de changer la valeur du meilleur move
     * @param move un move valide sélectionné par minimax
     */
    private void setBestMove(Move move) {
        bestMove = move;
    }
}

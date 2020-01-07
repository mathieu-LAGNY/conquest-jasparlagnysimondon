package fr.umontpellier.iut.conquest;

import java.util.List;

/**
 * Modélise un plateau.
 */
public class Board {
    /**
     * Tableau des pions.
     */
    private Pawn[][] field;

    /**
     * Constructeur.
     *
     * @param size : la taille du plateau.
     */
    public Board(int size) {
        field = new Pawn[size][size];
    }

    /**
     * Constructeur pour Test.
     *
     * @param field : plateau prédéfini.
     */
    public Board(Pawn[][] field) {
        this.field = field;
    }

    /**
     * Les méthodes suivantes sont utilisées pour les tests automatiques. Il ne faut pas les utiliser.
     */
    public Pawn[][] getField() {
        return field;
    }

    /**
     * Retourne la taille du plateau.
     */
    public int getSize() {
        return field.length;
    }

    /**
     * Affiche le plateau.
     */
    public String toString() {
        int size = field.length;
        StringBuilder b = new StringBuilder();
        for (int r = -1; r < size; r++) {
            for (int c = -1; c < size; c++) {
                if (r == -1 && c == -1) {
                    b.append("_");
                } else if (r == -1) {
                    b.append("_").append(c);
                } else if (c == -1) {
                    b.append(r).append("|");
                } else if (field[r][c] == null) {
                    b.append("_ ");
                } else if (field[r][c].getPlayer().getColor() == 1) {
                    b.append("X ");
                } else {
                    b.append("O ");
                }
            }
            b.append("\n");
        }
        b.append("---");
        return b.toString();
    }

    /**
     * Initialise le plateau avec les pions de départ.
     * Rappel :
     * - player1 commence le jeu avec un pion en haut à gauche (0,0) et un pion en bas à droite.
     * - player2 commence le jeu avec un pion en haut à droite et un pion en bas à gauche.
     */
    public void initField(Player player1, Player player2) {
        int size = getSize();
        field[0][0] = new Pawn(player1);
        field[size-1][size-1] = new Pawn(player1);
        field[0][size-1] = new Pawn(player2);
        field[size-1][0] = new Pawn(player2);
    }

    /**
     * Vérifie si un coup est valide.
     * Rappel :
     * - Les coordonnées du coup doivent être dans le plateau.
     * - Le pion bougé doit appartenir au joueur.
     * - La case d'arrivée doit être libre.
     * - La distance entre la case d'arrivée est au plus 2.
     */
    public boolean isValid(Move move, Player player) {
        int start_row = move.getRow1();
        int start_column = move.getColumn1();
        int end_row = move.getRow2();
        int end_column = move.getColumn2();

        int size = getSize();

        if (end_row < 0 || end_row >= size || end_column < 0 || end_column >= size) {
            return false;
        } else if (start_row < 0 || start_row >= size || start_column < 0 || start_column >= size) {
            return false;
        } else if(field[start_row][start_column] == null) {
            return false;
        } else if (! field[start_row][start_column].getPlayer().equals(player)) {
            return false;
        } else if (Math.abs(end_row - start_row) > 2 | Math.abs(end_column - start_column) > 2) {
            return false;
        } else if (field[end_row][end_column] != null) {
            return false;
        }
        return true;
    }

    /**
     * Déplace un pion.
     *
     * @param move : un coup valide.
     *             Rappel :
     *             - Si le pion se déplace à distance 1 alors il se duplique pour remplir la case d'arrivée et la case de départ.
     *             - Si le pion se déplace à distance 2 alors il ne se duplique pas : la case de départ est maintenant vide et la case d'arrivée remplie.
     *             - Dans tous les cas, une fois que le pion est déplacé, tous les pions se trouvant dans les cases adjacentes à sa case d'arrivée prennent sa couleur.
     */
    public void movePawn(Move move) {
        int col1 = move.getColumn1();
        int row1 = move.getRow1();
        int col2 = move.getColumn2();
        int row2 = move.getRow2();
        Player player = field[row1][col1].getPlayer();
        if (Math.abs(col1-col2)==2 || Math.abs(row1-row2)==2) {
            field[row1][col1] = null;
        }
        field[row2][col2] = new Pawn(player);
        for (int i=row2-1; i<=row2+1; i++) {
            for (int j=col2-1; j<=col2+1; j++) {
                if (0<=i && i<getSize() && 0<=j && j<getSize()) {
                    if (field[i][j] != null && !field[i][j].getPlayer().equals(player)) {
                        field[i][j] = new Pawn(player);
                    }
                }
            }
        }
    }

    /**
     * Retourne la liste de tous les coups valides de player.
     * S'il n'y a de coup valide, retourne une liste vide.
     */
    public List<Move> getValidMoves(Player player) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Retourne la liste de tous les pions d'un joueur.
     */
    public int getNbPawns(Player player) {
        int nbPawns = 0;
        for (Pawn[] row: field) {
            for(Pawn pawn : row) {
                if(pawn != null && pawn.getPlayer().equals(player)) {
                    nbPawns++;
                }
            }
        }
        return nbPawns;
    }
}

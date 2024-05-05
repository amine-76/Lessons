public class Morpion {
    private char[][] grille; // La grille de morpion
    private char joueurCourant; // Le joueur actuel (X ou O)

    // Constructeur pour initialiser la grille de morpion
    public Morpion() {
        grille = new char[3][3]; // Une grille de 3x3
        initialiserGrille();
        joueurCourant = 'X'; // Commencez par le joueur X
    }

    // Méthode pour initialiser la grille avec des cases vides
    private void initialiserGrille() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grille[i][j] = ' ';
            }
        }
    }

    // Méthode pour effectuer un mouvement sur la grille
    public boolean jouerCoup(int ligne, int colonne) {
        if (grille[ligne][colonne] == ' ') {
            grille[ligne][colonne] = joueurCourant;
            joueurCourant = (joueurCourant == 'X') ? 'O' : 'X'; // Changer de joueur
            return true; // Le coup est valide
        } else {
            return false; // La case est déjà occupée
        }
    }

    // Méthode pour vérifier s'il y a un gagnant
    public char verifierGagnant() {
        // Vérifier les lignes
        for (int i = 0; i < 3; i++) {
            if (grille[i][0] == grille[i][1] && grille[i][1] == grille[i][2] && grille[i][0] != ' ') {
                return grille[i][0];
            }
        }
        // Vérifier les colonnes
        for (int j = 0; j < 3; j++) {
            if (grille[0][j] == grille[1][j] && grille[1][j] == grille[2][j] && grille[0][j] != ' ') {
                return grille[0][j];
            }
        }
        // Vérifier les diagonales
        if ((grille[0][0] == grille[1][1] && grille[1][1] == grille[2][2] && grille[0][0] != ' ') ||
                (grille[0][2] == grille[1][1] && grille[1][1] == grille[2][0] && grille[0][2] != ' ')) {
            return grille[1][1];
        }
        // Aucun gagnant trouvé
        return ' ';
    }

    // Méthode pour vérifier s'il y a un match nul
    public boolean verifierMatchNul() {
        // Vérifier si la grille est remplie et qu'il n'y a pas de gagnant
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grille[i][j] == ' ') {
                    return false; // Il reste des cases vides, le jeu n'est pas un match nul
                }
            }
        }
        // La grille est remplie et il n'y a pas de gagnant, donc c'est un match nul
        return true;
    }

    // Méthode pour obtenir l'état actuel de la grille
    public char[][] getGrille() {
        return grille;
    }

    // Méthode pour obtenir le joueur actuel
    public char getJoueurCourant() {
        return joueurCourant;
    }
}

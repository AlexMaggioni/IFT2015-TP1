import java.util.*;

/**
 * Classe représentant une requête contenant une grille et une liste de mots.
 */
public class Query {
    private final int M;
    private final int N;
    private final char[][] grid;
    private final List<String> words;
    private static final int[] DX = {-1, 0, 1, -1, 0, 1, -1, 0, 1};
    private static final int[] DY = {-1, -1, -1, 0, 0, 0, 1, 1, 1};

    /**
     * Constructeur pour initialiser une requête.
     *
     * @param M      Nombre de lignes de la grille.
     * @param N      Nombre de colonnes de la grille.
     * @param grid   Grille de lettres.
     * @param words  Liste de mots à chercher.
     */
    public Query(int M, int N, char[][] grid, List<String> words) {
        this.M = M;
        this.N = N;
        this.grid = grid;
        // Copie et tri des mots
        this.words = new ArrayList<>(words);
        Collections.sort(this.words);
    }

    /**
     * Méthode pour obtenir la liste des mots.
     *
     * @return La liste des mots.
     */
    public List<String> getWords() {
        return words;
    }

    /**
     * Trouve les chemins dans la grille pour un mot donné.
     *
     * @param word Le mot à chercher.
     * @return Une liste de chemins où le mot est trouvé.
     */
    public List<String> findWordPaths(String word) {
        char[] wordChars = word.toCharArray();
        List<String> paths = new ArrayList<>();
        // Parcourir chaque cellule de la grille
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                // Si la première lettre du mot est trouvée
                if (grid[i][j] == wordChars[0]) {
                    StringBuilder path = new StringBuilder();
                    dfs(i, j, 0, wordChars, path, paths);
                }
            }
        }
        Collections.sort(paths);
        return paths;
    }

    /**
     * Fonction récursive pour explorer la grille à la recherche d'un mot.
     *
     * @param x         Coordonnée x actuelle.
     * @param y         Coordonnée y actuelle.
     * @param index     Index actuel dans le mot.
     * @param wordChars Caractères du mot.
     * @param path      Chemin actuel.
     * @param paths     Liste des chemins trouvés.
     */
    private void dfs(int x, int y, int index, char[] wordChars, StringBuilder path, List<String> paths) {
        // Vérifier les limites de la grille
        if (x < 0 || x >= M || y < 0 || y >= N) return;
    
        char currentChar = grid[x][y];
        // Vérifier si le caractère correspond
        if (currentChar != wordChars[index]) return;
    
        // Mise à jour du chemin
        int pathLength = path.length();
        path.append("(").append(x).append(",").append(y).append(")");
    
        // Si le dernier caractère du mot est atteint
        if (index == wordChars.length - 1) {
            paths.add(path.toString());  
        } else {
            path.append("->");
            // Explorer les voisins
            for (int i = 0; i < 9; i++) {
                dfs(x + DX[i], y + DY[i], index + 1, wordChars, path, paths);
            }
        }
    
        // Réinitialiser le chemin pour l'itération suivante
        path.setLength(pathLength);
    }
}

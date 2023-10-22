import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe principale permettant de lire et de traiter les requêtes à partir d'un fichier donné.
 */
public class Main {
    // Liste pour stocker les requêtes
    private final List<Query> queries = new ArrayList<>();

    /**
     * Constructeur permettant d'initialiser les requêtes à partir d'un fichier.
     *
     * @param filename Le nom du fichier à lire.
     */
    public Main(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null && !line.trim().isEmpty()) {
                // Découpage de la ligne en parties
                String[] parts = line.split(" ");
                int M = Integer.parseInt(parts[0]);
                int N = Integer.parseInt(parts[1]);
                // Initialisation de la grille
                char[][] grid = new char[M][N];
                for (int i = 0; i < M; i++) {
                    grid[i] = br.readLine().replace(" ", "").toCharArray();
                }
                // Récupération des mots
                List<String> words = List.of(br.readLine().split(" "));
                queries.add(new Query(M, N, grid, words));
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier: " + e.getMessage());
        }
    }

    /**
     * Méthode pour obtenir la liste des requêtes.
     *
     * @return La liste des requêtes.
     */
    public List<Query> getQueries() {
        return queries;
    }

    /**
     * Point d'entrée principal du programme.
     *
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {   
        if (args.length < 1) {
            System.out.println("Veuillez fournir le nom du fichier d'entrée.");
            return;
        }
        Main reader = new Main(args[0]);
        int queryNumber = 1;
        StringBuilder output = new StringBuilder();
        // Traitement des requêtes
        for (Query query : reader.getQueries()) {
            output.append("Query ").append(queryNumber).append(":\n");
            for (String word : query.getWords()) {
                for (String path : query.findWordPaths(word)) {
                    output.append(word).append(" ").append(path).append("\n");
                }
            }
            queryNumber++;
        }        
        System.out.print(output);
    }   
}

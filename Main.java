import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private final List<Query> queries = new ArrayList<>();

    public Main(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null && !line.trim().isEmpty()) {
                String[] parts = line.split(" ");
                int M = Integer.parseInt(parts[0]);
                int N = Integer.parseInt(parts[1]);
                char[][] grid = new char[M][N];
                for (int i = 0; i < M; i++) {
                    grid[i] = br.readLine().replace(" ", "").toCharArray();  // Reverted the change here
                }
                List<String> words = List.of(br.readLine().split(" "));
                queries.add(new Query(M, N, grid, words));
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    public List<Query> getQueries() {
        return queries;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide the input filename.");
            return;
        }
        Main reader = new Main(args[0]);
        int queryNumber = 1;
        StringBuilder output = new StringBuilder();
        for (Query query : reader.getQueries()) {
            output.append("Query ").append(queryNumber).append("\n");
            for (String word : query.getWords()) {
                for (String path : query.findWordPaths(word)) {  // This is now a String
                    output.append(word).append(" ").append(path).append("\n");
                }
            }
            queryNumber++;
        }        
        System.out.print(output);
    }
}
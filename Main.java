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

        long startTime = System.currentTimeMillis();

        if (args.length < 1) {
            System.out.println("Please provide the input filename.");
            return;
        }
        Main reader = new Main(args[0]);
        int queryNumber = 1;
        StringBuilder output = new StringBuilder();
        for (Query query : reader.getQueries()) {
            output.append("Query ").append(queryNumber).append(":\n");
            for (String word : query.getWords()) {
                for (List<int[]> path : query.findWordPaths(word)) {
                    output.append(word).append(" ");
                    for (int i = 0; i < path.size(); i++) {
                        output.append("(").append(path.get(i)[0]).append(",").append(path.get(i)[1]).append(")");
                        if (i < path.size() - 1) {
                            output.append("->");
                        }
                    }
                    output.append("\n");
                }
            }
            queryNumber++;
        }
        System.out.println(output);

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("\nExecution time: " + executionTime + " milliseconds");
    }
}
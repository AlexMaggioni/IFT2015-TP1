import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    private static int N, M;
    private static char[][] grid;
    private static String[] words;
    private static Map<String, List<String>> wordDictionary = new HashMap<>();
    private static int[] dx = {-1, -1, -1,  0,  0,  0,  1,  1,  1};
    private static int[] dy = {-1,  0,  1,  1,  0,  -1,  1,  0, -1};

    private static List<Query> readInput(String filename) throws IOException {
        List<Query> queries = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] dimensions = line.split(" ");
                int N = Integer.parseInt(dimensions[0]);
                int M = Integer.parseInt(dimensions[1]);
    
                char[][] grid = new char[N][M];
                for (int i = 0; i < N; i++) {
                    line = br.readLine();
                    for (int j = 0; j < M; j++) {
                        grid[i][j] = line.charAt(2 * j); // every other character is a space
                    }
                }
                String[] words = br.readLine().split(" ");
    
                queries.add(new Query(N, M, grid, words));
            }
        }
    
        return queries;
    }    

    private static String formatPath(StringBuilder path) {
        String str = path.toString();
        if (str.endsWith("->")) {
            return str.substring(0, str.length() - 2);
        }
        return str;
    }

    private static void search() {
        Trie trie = new Trie(words);
        Trie.TrieNode root = trie.getRoot();

        Set<Character> startingChars = root.children.keySet();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (startingChars.contains(grid[i][j])) {
                    dfs(i, j, root.children.get(grid[i][j]), new StringBuilder());
                }
            }
        }        
    }

    private static void dfs(int i, int j, Trie.TrieNode node, StringBuilder path) {
        if (i < 0 || i >= N || j < 0 || j >= M) return;
    
        path.append("(" + i + "," + j + ")->");
    
        if (node.isWord) {
            wordDictionary.putIfAbsent(node.word, new ArrayList<>());
            wordDictionary.get(node.word).add(formatPath(path));
        }
    
        int originalLength = path.length();
        for (int d = 0; d < 9; d++) {
            int x = i + dx[d];
            int y = j + dy[d];
    
            if (x >= 0 && x < N && y >= 0 && y < M && node.children.containsKey(grid[x][y])) {
                dfs(x, y, node.children.get(grid[x][y]), path);
                path.setLength(originalLength); // Reset the path
            }
        }
    }    

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();  // Start recording the time
    
        List<Query> queries = readInput(args[0]);
    
        for (int queryNum = 1; queryNum <= queries.size(); queryNum++) {
            Query query = queries.get(queryNum - 1);
            
            N = query.N;
            M = query.M;
            grid = query.grid;
            words = query.words;
    
            wordDictionary.clear();  // Clear previous query results
            search();
            
            System.out.println("Query " + queryNum + ":");
            
            // Sorting
            List<String> sortedOutput = new ArrayList<>();
            for (Map.Entry<String, List<String>> entry : wordDictionary.entrySet()) {
                for (String path : entry.getValue()) {
                    sortedOutput.add(entry.getKey() + " " + path);
                }
            }
            Collections.sort(sortedOutput);
    
            for (String line : sortedOutput) {
                System.out.println(line);
            }
        }
    
        long endTime = System.currentTimeMillis();
    
        System.out.println("Execution time: " + (endTime - startTime) + " milliseconds");
    }    
    
}
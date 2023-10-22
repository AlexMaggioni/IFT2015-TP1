import java.util.*;

public class Query {
    private final int M;
    private final int N;
    private final char[][] grid;
    private final List<String> words;
    private static final int[] DX = {-1, 0, 1, -1, 0, 1, -1, 0, 1};
    private static final int[] DY = {-1, -1, -1, 0, 0, 0, 1, 1, 1};

    public Query(int M, int N, char[][] grid, List<String> words) {
        this.M = M;
        this.N = N;
        this.grid = grid;
        this.words = new ArrayList<>(words);
        Collections.sort(this.words);
    }

    public List<String> getWords() {
        return words;
    }

    public List<String> findWordPaths(String word) {
        char[] wordChars = word.toCharArray();
        List<String> paths = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j] == wordChars[0]) {
                    StringBuilder path = new StringBuilder();
                    dfs(i, j, 0, wordChars, path, paths);
                }
            }
        }
        Collections.sort(paths);  // Sorting the paths
        return paths;
    }
    

    private void dfs(int x, int y, int index, char[] wordChars, StringBuilder path, List<String> paths) {
        if (x < 0 || x >= M || y < 0 || y >= N) return;
    
        char currentChar = grid[x][y];
        if (currentChar != wordChars[index]) return;
    
        int pathLength = path.length();
        path.append("(").append(x).append(",").append(y).append(")");
    
        if (index == wordChars.length - 1) {
            paths.add(path.toString());  
        } else {
            path.append("->");
            for (int i = 0; i < 9; i++) {
                dfs(x + DX[i], y + DY[i], index + 1, wordChars, path, paths);
            }
        }
    
        path.setLength(pathLength); // Revert the path to its previous state
    }
    
}
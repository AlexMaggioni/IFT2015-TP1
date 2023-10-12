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

    public List<List<int[]>> findWordPaths(String word) {
        char[] wordChars = word.toCharArray();
        List<List<int[]>> paths = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j] == wordChars[0]) {
                    List<int[]> path = new ArrayList<>();
                    dfs(i, j, 0, wordChars, path, paths);
                }
            }
        }
        return paths;
    }

    private void dfs(int x, int y, int index, char[] wordChars, List<int[]> path, List<List<int[]>> paths) {
        if (x < 0 || x >= M || y < 0 || y >= N) return;
        
        char currentChar = grid[x][y];
        if (currentChar != wordChars[index]) return;
        
        path.add(new int[]{x, y});
        
        if (index == wordChars.length - 1) {
            paths.add(new ArrayList<>(path));
        } else {
            for (int i = 0; i < 9; i++) {
                dfs(x + DX[i], y + DY[i], index + 1, wordChars, path, paths);
            }
        }
        
        path.remove(path.size() - 1);
    }
}
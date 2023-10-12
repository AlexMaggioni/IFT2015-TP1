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
        this.words = words;
    }

    public List<String> getWords() {
        List<String> sortedWords = new ArrayList<>(words);
        Collections.sort(sortedWords);
        return sortedWords;
    }

    public List<List<int[]>> findWordPaths(String word) {
        char[] wordChars = word.toCharArray();
        List<List<int[]>> paths = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j] == wordChars[0]) {
                    dfs(i, j, 0, wordChars, new ArrayList<>(), paths);
                }
            }
        }
        return paths;
    }

    private void dfs(int x, int y, int index, char[] wordChars, List<int[]> path, List<List<int[]>> paths) {
        if (x < 0 || x >= M || y < 0 || y >= N || grid[x][y] != wordChars[index]) {
            return;
        }
        if (index == wordChars.length - 1) {
            path.add(new int[]{x, y});
            paths.add(new ArrayList<>(path));
            path.remove(path.size() - 1);
            return;
        }
        path.add(new int[]{x, y});
        for (int i = 0; i < 9; i++) {
            dfs(x + DX[i], y + DY[i], index + 1, wordChars, path, paths);
        }
        path.remove(path.size() - 1);
    }
}
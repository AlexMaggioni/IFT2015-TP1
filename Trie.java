import java.util.HashMap;
import java.util.Map;

public class Trie {

    public static class TrieNode {
        Map<Character, TrieNode> children;
        boolean isWord;
        String word;

        public TrieNode() {
            children = new HashMap<>();
            this.isWord = false;
            this.word = null;
        }
    }

    private final TrieNode root = new TrieNode();

    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
        }
        node.isWord = true;
        node.word = word;
    }

    public Trie(String[] words) {
        for (String word : words) {
            this.insert(word);
        }
    }

    public TrieNode getRoot() {
        return this.root;
    }
}
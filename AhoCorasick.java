import java.util.*;

public class AhoCorasick {
    static class Node {
        Node[] children = new Node[26];
        Node fail;
        List<String> output = new ArrayList<>();
    }
    Node root = new Node();
    public void insert(String pattern) {
        Node curr = root;
        for (char ch : pattern.toCharArray()) {
            int idx = ch - 'a';
            if (curr.children[idx] == null) {
                curr.children[idx] = new Node();
            }
            curr = curr.children[idx];
        }
        curr.output.add(pattern);
    }
    public void buildFailureLinks() {
        Queue<Node> q = new LinkedList<>();
        root.fail = root;
        for (int i = 0; i < 26; i++) {
            if (root.children[i] != null) {
                root.children[i].fail = root;
                q.offer(root.children[i]);
            }
        }
        while (!q.isEmpty()) {
            Node curr = q.poll();
            for (int i = 0; i < 26; i++) {
                Node child = curr.children[i];
                if (child == null) continue;
                Node fallback = curr.fail;
                while (fallback != root && fallback.children[i] == null) {
                    fallback = fallback.fail;
                }
                if (fallback.children[i] != null) {
                    child.fail = fallback.children[i];
                } else {
                    child.fail = root;
                }
                child.output.addAll(child.fail.output);
                q.offer(child);
            }
        }
    }
    public List<String> search(String text) {
        List<String> result = new ArrayList<>();
        Node curr = root;
        for (char ch : text.toCharArray()) {
            int idx = ch - 'a';
            while (curr != root && curr.children[idx] == null) {
                curr = curr.fail;
            }
            if (curr.children[idx] != null) {
                curr = curr.children[idx];
            }
            result.addAll(curr.output);
        }
        return result;
    }
    public static void main(String[] args) {
        AhoCorasick ac = new AhoCorasick();
        ac.insert("he");
        ac.insert("she");
        ac.insert("his");
        ac.insert("hers");
        ac.buildFailureLinks();
        System.out.println(ac.search("ahishers"));
    }
}
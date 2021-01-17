package leetcode.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

class AlienDictionary {

    public static void main(String[] args) {
        System.out.println("main");
    }

    interface GraphNode<V> {
        List<GraphNode<V>> children();
    }

    class AlienLetter implements GraphNode<AlienLetter> {
        Set<AlienLetter> prior = new HashSet<>();
        Character letter;
        Set<AlienLetter> next = new HashSet<>();

        AlienLetter(Character letter) {
            this.letter = letter;
        }

        boolean addNext(AlienLetter nextLetter) {
            if (letter.equals(nextLetter.letter) || prior.contains(nextLetter)) {
                return false;
            }
            next.add(nextLetter);
            return true;
        }

        boolean addPrior(AlienLetter priorLetter) {
            if (letter.equals(priorLetter.letter) || next.contains(priorLetter)) {
                return false;
            }
            prior.add(priorLetter);
            return true;
        }

        @Override
        public List<GraphNode<AlienLetter>> children() {
            return new ArrayList<>(next);
        }
    }

    public String alienOrder(String[] words) {
        Map<Character, AlienLetter> letterDict = new HashMap<>();

        if(words == null || words.length == 0) {
            return "";
        } else if (words.length == 1) {
            return String.valueOf(words[0].charAt(0));
        } else {
            for (int i = 1; i < words.length; i++) {
                char[] orderedPair = firstDiff(0, words[i - 1], words[i]);
                if (orderedPair != null) {
                    Character leftChar = Character.valueOf(orderedPair[0]);
                    Character rightChar = Character.valueOf(orderedPair[1]);

                    AlienLetter leftLetter = letterDict.getOrDefault(leftChar, new AlienLetter(leftChar));
                    AlienLetter rightLetter = letterDict.getOrDefault(rightChar, new AlienLetter(rightChar));
                    letterDict.put(leftChar, leftLetter);
                    letterDict.put(rightChar, rightLetter);

                    if (leftLetter.addNext(rightLetter) == false) {
                        return "";
                    }
                    if (rightLetter.addPrior(leftLetter) == false) {
                        return "";
                    }
                }

                //always include first letter because we learnt something about it.
                //e.g. [[z],[z]] or [[ab],[adc]]
                Character firstChar = Character.valueOf(words[i].charAt(0));
                AlienLetter leftLetter = letterDict.getOrDefault(firstChar, new AlienLetter(firstChar));
                letterDict.put(firstChar, leftLetter);
            }

            if (letterDict.isEmpty()) {
                return "";
            }

            //perform a topological sort
            Set<GraphNode<AlienLetter>> discovered = new HashSet<>();
            LinkedList<GraphNode<AlienLetter>> topoOrdered = new LinkedList<>();
            for(AlienLetter root : letterDict.values()) {
                dfs(root, discovered, topoOrdered);
            }

            StringBuilder builder = new StringBuilder();
            for(GraphNode<AlienLetter> letter : topoOrdered) {
                builder.append(((AlienLetter) letter).letter.charValue());
            }
            return builder.toString();
        }
    }

    public char[] firstDiff(int index, String left, String right) {
        if(left.length() == index || right.length() == index) {
            return null;
        } else if (left.charAt(index) != right.charAt(index)) {
            char[] orderedPair = new char[2];
            orderedPair[0] = left.charAt(index);
            orderedPair[1] = right.charAt(index);
            return orderedPair;
        } else {
            return firstDiff(index+1, left, right);
        }
    }


    public static <V> void dfs(GraphNode<V> root, Set<GraphNode<V>> discovered, LinkedList<GraphNode<V>> topoOrdered) {
        if(root == null || discovered.contains(root)) {
            return;
        }

        discovered.add(root);
        for (GraphNode<V> v : root.children()) {
            dfs(v, discovered, topoOrdered);
        }
        topoOrdered.addFirst(root);
    }

    private Map<Character, List<Character>> reverseAdjList = new HashMap<>();
    private Map<Character, Boolean> seen = new HashMap<>();
    private StringBuilder output = new StringBuilder();

    //leetcode optimal solution
    public String alienOrderOptimal(String[] words) {

        // Step 0: Put all unique letters into reverseAdjList as keys.
        for (String word : words) {
            for (char c : word.toCharArray()) {
                reverseAdjList.putIfAbsent(c, new ArrayList<>());
            }
        }

        // Step 1: Find all edges and add reverse edges to reverseAdjList.
        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            // Check that word2 is not a prefix of word1.
            if (word1.length() > word2.length() && word1.startsWith(word2)) {
                return "";
            }
            // Find the first non match and insert the corresponding relation.
            for (int j = 0; j < Math.min(word1.length(), word2.length()); j++) {
                if (word1.charAt(j) != word2.charAt(j)) {
                    reverseAdjList.get(word2.charAt(j)).add(word1.charAt(j));
                    break;
                }
            }
        }

        // Step 2: DFS to build up the output list.
        for (Character c : reverseAdjList.keySet()) {
            boolean result = dfs(c);
            if (!result) return "";
        }


        if (output.length() < reverseAdjList.size()) {
            return "";
        }
        return output.toString();
    }

    // Return true iff no cycles detected.
    private boolean dfs(Character c) {
        if (seen.containsKey(c)) {
            return seen.get(c); // If this node was grey (false), a cycle was detected.
        }
        seen.put(c, false);
        for (Character next : reverseAdjList.get(c)) {
            boolean result = dfs(next);
            if (!result) return false;
        }
        seen.put(c, true);
        output.append(c);
        return true;
    }
}
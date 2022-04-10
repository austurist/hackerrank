package swapnodes;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'swapNodes' function below.
     *
     * The function is expected to return a 2D_INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. 2D_INTEGER_ARRAY indexes
     *  2. INTEGER_ARRAY queries
     */

    static class Pair {
        Integer left;
        Integer right;

        private Integer convert(Integer index) {
            return index == -1 ? null : index;
        }

        Pair(List<Integer> indexes) {
            this(indexes.get(0), indexes.get(1));
        }

        Pair(Integer left, Integer right) {
            this.left = convert(left);
            this.right = convert(right);
        }
    }


    public static List<List<Integer>> swapNodes(List<List<Integer>> indexes, List<Integer> queries) {

        Map<Integer, Pair> tree = new HashMap<>();

        // build tree
        AtomicInteger count = new AtomicInteger(1);
        indexes.forEach(idx_at_idx -> {
            Pair pair = new Pair(idx_at_idx);
            tree.put(count.getAndIncrement(), pair);
        });

        int depth = indexes.size();
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0 ; i < queries.size() ; i++) {

            List<Integer> subqueries = queries.subList(0, i+1);

            List<Boolean> isSwappedAtDepth = IntStream.range(1, depth+1)
                    .mapToLong(d -> {

                        return subqueries
                                .stream()
                                .filter(q -> d % q == 0)
                                .count();

                    })
                    .mapToObj(l -> l % 2 == 1)
                    .collect(toList());

            List<Integer> walkAfterSwaps = walk(tree, 1, 0, isSwappedAtDepth);
            result.add(walkAfterSwaps);
        }

        return result;
    }

    private static List<Integer> walk(Map<Integer, Pair> tree, int node, int depth, List<Boolean> isSwappedAtDepth) {
        List<Integer> result = new ArrayList<>();
        Pair current = tree.get(node);

        Integer left = current.left;
        Integer right = current.right;
        Boolean swapped = isSwappedAtDepth.get(depth);

        if (swapped) {
            left = current.right;
            right = current.left;
        }

        if (left != null) { result.addAll(walk(tree, left, depth+1, isSwappedAtDepth)); }
        result.add(node);
        if (right != null) { result.addAll(walk(tree, right, depth+1, isSwappedAtDepth)); }

        return result;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Integer>> indexes = new ArrayList<>();

        IntStream.range(0, n).forEach(i -> {
            try {
                indexes.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Integer::parseInt)
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int queriesCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> queries = IntStream.range(0, queriesCount).mapToObj(i -> {
                    try {
                        return bufferedReader.readLine().replaceAll("\\s+$", "");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(toList());

        List<List<Integer>> result = Result.swapNodes(indexes, queries);

        result.stream()
                .map(
                        r -> r.stream()
                                .map(Object::toString)
                                .collect(joining(" "))
                )
                .map(r -> r + "\n")
                .collect(toList())
                .forEach(e -> {
                    try {
                        bufferedWriter.write(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

        bufferedReader.close();
        bufferedWriter.close();
    }
}

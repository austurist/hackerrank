package specialsub;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution {

    private static class Pair {
        Integer left;
        Integer right;

        Pair(Integer left, Integer right) {
            this.left = left;
            this.right = right;
        }

        public Integer getLeft() {
            return left;
        }

        public Integer getRight() {
            return right;
        }

        Integer getKey() {
            return getLeft();
        }

        Integer getValue() {
            return getRight();
        }
    }

    private static class SubStrings {
        private Map<Integer, List<Integer>> positionsOfChars;
        private Map<Integer, List<Pair>> streaks;

        SubStrings(String str) {
            Iterator<Integer> idx = Stream.iterate(0, i -> i + 1).iterator();
            positionsOfChars = str.chars()
                    .boxed()
                    .map(i -> new Pair(i, idx.next()))
                    .collect(Collectors.groupingBy(
                            Pair::getKey,
                            Collectors.mapping(
                                    Pair::getValue,
                                    Collectors.toList()
                            )
                    ));

            computeAllStreaks();

            streaks
                    .values()
                    .forEach(this::computePalindromes);
        }

        public int count() {
            return streaks
                    .values()
                    .stream()
                    .map(List::size)
                    .reduce(0, Integer::sum);
        }

        private void computeAllStreaks() {
            streaks = positionsOfChars
                    .entrySet()
                    .stream()
                    .collect(
                            Collectors.toMap(
                                    Map.Entry::getKey,
                                    il -> computeStreaks(il.getValue())
                            )
                    );
        }

        private List<Pair> computeStreaks(List<Integer> positions) {
            List<Pair> result = new ArrayList<>();

            if (positions.size() == 0) {
                return result;
            }

            Collections.sort(positions);
            Integer begin = positions.get(0);
            Integer end = positions.get(0);
            for (Integer pos : positions) {
                if (pos != end + 1) {
                    // new streak
                    begin = pos;
                }
                end = pos;
                result.add(new Pair(begin, end+1));
            }

            return result;
        }

        private void computePalindromes(List<Pair> streaks) {
            Map<Integer, List<Pair>> begins = streaks
                    .stream()
                    .collect(Collectors.groupingBy(Pair::getLeft));

            Map<Integer, List<Pair>> ends = streaks
                    .stream()
                    .collect(Collectors.groupingBy(Pair::getRight));

            // find palindromes
            List<Pair> palindromes = new ArrayList<>();
            ends
                    .keySet()
                    .forEach(end -> {
                        List<Pair> firsthalves = ends.get(end);
                        List<Pair> secondhalves = begins.get(end+1);
                        if (secondhalves != null) {
                            // pair same sizes
                            Map<Integer, List<Pair>> firsthalvesbysize = firsthalves
                                    .stream()
                                    .collect(Collectors.groupingBy(pr -> pr.getRight() - pr.getLeft()));

                            Map<Integer, List<Pair>> secondhalvesbysize = secondhalves
                                    .stream()
                                    .collect(Collectors.groupingBy(pr -> pr.getRight() - pr.getLeft()));

                            firsthalvesbysize
                                    .keySet()
                                    .stream()
                                    .forEach( sz -> {
                                        if (secondhalvesbysize.get(sz) != null) {
                                            if (firsthalvesbysize.get(sz).size() != 1 || secondhalvesbysize.get(sz).size() != 1) {
                                                throw new RuntimeException("wtf");
                                            }
                                            Pair fst = firsthalvesbysize.get(sz).get(0);
                                            Pair snd = secondhalvesbysize.get(sz).get(0);
                                            palindromes.add(new Pair(fst.getLeft(), snd.getRight()));
                                        }
                                    });
                        }
                    });
            streaks.addAll(palindromes);

        }
    }

    // Complete the substrCount function below.
    static long substrCount(int n, String s) {
        SubStrings ss = new SubStrings(s);

        return ss.count();
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        String s = scanner.nextLine();

        long result = substrCount(n, s);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}

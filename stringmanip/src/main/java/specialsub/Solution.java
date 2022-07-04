package specialsub;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.*;

public class Solution {

    private static class Pair <L, R>   {
        L left;
        R right;

        Pair(L left, R right) {
            this.left = left;
            this.right = right;
        }

        public L getLeft() {
            return left;
        }

        public R getRight() {
            return right;
        }

        public L getKey() {
            return getLeft();
        }

        public R getValue() {
            return getRight();
        }
    }

    static class SubStrings {

        List<Pair<Integer, Integer>> characters = new ArrayList<>();

        public SubStrings(String s) {
            Integer currentCh = null;
            Integer streak = 0;
            for (Iterator<Integer> it = s.chars().boxed().iterator(); it.hasNext(); ) {
                Integer ch = it.next();

                if (!Objects.equals(ch, currentCh)) {
                    // new streak begins

                    // save old
                    if (currentCh != null)
                        characters.add(new Pair<>(currentCh, streak));

                    currentCh = ch;
                    streak = 1;
                } else {
                    // streak continues
                    streak++;
                }
            }
            // save last streak
            characters.add(new Pair<>(currentCh, streak));
        }

        public Integer count() {
            // count streaks
            Integer noOfStreaks = characters
                    .stream()
                    .map(Pair::getRight)
                    .map(i -> i * (i + 1) / 2)
                    .reduce(0, Integer::sum);

            Integer noOfPalindromes = 0;
            for (Iterator<Integer> it = IntStream.range(1, characters.size() - 1).boxed().iterator(); it.hasNext(); ) {
                Integer idx = it.next();

                if (
                        characters.get(idx-1).getKey().equals(characters.get(idx+1).getKey())
                        && characters.get(idx).getRight() == 1
                ) {
                    noOfPalindromes += Math.min(characters.get(idx-1).getRight(), characters.get(idx+1).getRight());
                }
            }

            return noOfStreaks + noOfPalindromes;
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

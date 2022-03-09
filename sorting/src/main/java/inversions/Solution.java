package inversions;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    public static long merge(List<Integer> arr, List<Integer> left, List<Integer> right) {
        int i = 0, j = 0;
        long count = 0;
        while (i < left.size() || j < right.size()) {
            if (i == left.size()) {
                arr.set(i+j, right.get(j));
                j++;
            } else if (j == right.size()) {
                arr.set(i+j, left.get(i));
                i++;
            } else if (left.get(i) <= right.get(j)) {
                arr.set(i+j, left.get(i));
                i++;
            } else {
                arr.set(i+j, right.get(j));
                count += left.size()-i;
                j++;
            }
        }
        return count;
    }

    public static long countInversions(List<Integer> arr) {
        if (arr.size() < 2)
            return 0;

        int m = (arr.size() + 1) / 2;
        List<Integer> left = new ArrayList<>(arr.subList(0, m));
        List<Integer>  right = new ArrayList<>(arr.subList(m, arr.size()));

        return countInversions(left) + countInversions(right) + merge(arr, left, right);
    }


}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, t).forEach(tItr -> {
            try {
                int n = Integer.parseInt(bufferedReader.readLine().trim());

                List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList());

                long result = Result.countInversions(arr);

                bufferedWriter.write(String.valueOf(result));
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}

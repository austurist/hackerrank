package triplets;

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

public class Solution {


    static List<Integer> getOccurences(Map<Long, List<Integer>> indicesmap, Long e) {
        List<Integer> result = Collections.emptyList();
        if (indicesmap.containsKey(e)) {
            result = indicesmap.get(e);
        }

        return result;
    }

    // Complete the countTriplets function below.
    static long countTriplets(List<Long> arr, long r) {
        long count = 0;

        // store indices in map
        Stream<Integer> numbers = IntStream.iterate(0, num -> num + 1).boxed();
        Iterator<Integer> iterator = numbers.iterator();

        Map<Long, List<Integer>> indicesmap = arr
                .stream()
                .sequential()
                .map(elt -> new AbstractMap.SimpleEntry<Long, Integer>(elt, iterator.next()))
                .collect(Collectors.groupingBy(
                        AbstractMap.SimpleEntry::getKey,
                        Collectors.mapping(AbstractMap.SimpleEntry::getValue, toList())
                ));

        List<Long> uniqueelements = arr.stream().sorted().distinct().collect(toList());
        for (Long e1 : uniqueelements) {
            Long e2 = e1 * r;
            Long e3 = e1 * r * r;

            if (uniqueelements.contains(e2) && uniqueelements.contains(e3)) {
                List<Integer> e1elts = getOccurences(indicesmap, e1);
                List<Integer> e2elts = getOccurences(indicesmap, e2);
                List<Integer> e3elts = getOccurences(indicesmap, e3);

                for (Integer j : e2elts) {
                    long n1 = 0;
                    for (Integer e1i : e1elts) {
                        if (e1i < j)
                            n1++;
                        else
                            break;
                    }

                    long n3 = 0;
                    for (Integer e3k : e3elts) {
                        if (j < e3k)
                            n3++;
                        else
                            continue;
                    }

                    count += n1*n3;
                }
            }
        }

        return count;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] nr = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(nr[0]);

        long r = Long.parseLong(nr[1]);

        List<Long> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Long::parseLong)
                .collect(toList());

        long ans = countTriplets(arr, r);

        bufferedWriter.write(String.valueOf(ans));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

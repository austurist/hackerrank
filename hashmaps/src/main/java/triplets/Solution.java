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


    static long nCk(long N, long K) {
        long nCk = 1L;
        for (int k = 0; k < K; k++) {
            nCk = nCk * (N-k) / (k+1);
        }

        return nCk;
    }

    // Complete the countTriplets function below.
    static long countTriplets(List<Long> arr, long r) {

        Map<Long, Long> elements = new HashMap<>();
        arr
            .stream()
            .forEach(elt -> elements.merge(elt, 1L, Long::sum));


        List<Long> dselts = arr.stream().sorted().distinct().collect(Collectors.toList());
        long count = 0L;
        for (Long e : dselts) {
            Map<Long, AbstractMap.SimpleEntry<Long, Long>> combinations = new HashMap<>();
            long e1 = e;
            long n1 = elements.getOrDefault(e1, 0L);
            combinations.merge(e1, new AbstractMap.SimpleEntry<>(n1, 1L),
                    (ent1, ent2) -> new AbstractMap.SimpleEntry<>(ent1.getKey(), ent1.getValue()+ent2.getValue()));

            long e2 = e * r;
            long n2 = elements.getOrDefault(e2, 0L);
            combinations.merge(e2, new AbstractMap.SimpleEntry<>(n2, 1L),
                    (ent1, ent2) -> new AbstractMap.SimpleEntry<>(ent1.getKey(), ent1.getValue()+ent2.getValue()));

            long e3 = e * r * r;
            long n3 = elements.getOrDefault(e3, 0L);
            combinations.merge(e3, new AbstractMap.SimpleEntry<>(n3, 1L),
                    (ent1, ent2) -> new AbstractMap.SimpleEntry<>(ent1.getKey(), ent1.getValue()+ent2.getValue()));

            if (n1*n2*n3 == 0) {
                continue;
            }

            count += combinations
                    .entrySet()
                    .stream()
                    .map(nkEntry -> {
                        return nCk(nkEntry.getValue().getKey(), nkEntry.getValue().getValue());
                    })
                    .reduce(1L, Math::multiplyExact);

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

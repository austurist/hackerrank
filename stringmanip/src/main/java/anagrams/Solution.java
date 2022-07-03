package anagrams;

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

    private static class AnaDesc {
        private SortedMap<Integer, Long> desc;

        public AnaDesc(String a) {
            desc = a.chars().boxed().collect(Collectors.groupingBy(
               Function.identity(),
               TreeMap::new,
               Collectors.counting()
            ));
        }

        public Long diff(AnaDesc other) {
            var allLetters = new HashSet<>(desc.keySet());
            allLetters.addAll(other.desc.keySet());
            return allLetters
                    .stream()
                    .map(lt -> Math.abs(desc.getOrDefault(lt, 0L) - other.desc.getOrDefault(lt, 0L)))
                    .reduce(0L, Long::sum);
        }
    }

    public static int makeAnagram(String a, String b) {
        AnaDesc ada = new AnaDesc(a);
        AnaDesc adb = new AnaDesc(b);
        // Write your code here
        return ada.diff(adb).intValue();
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String a = bufferedReader.readLine();

        String b = bufferedReader.readLine();

        int res = Result.makeAnagram(a, b);

        bufferedWriter.write(String.valueOf(res));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

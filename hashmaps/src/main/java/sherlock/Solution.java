package sherlock;

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

    /*
     * Complete the 'sherlockAndAnagrams' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts STRING s as parameter.
     */


    public static  List<char[]> getSubStrings(String s) {
        List<char[]> subarrs = new ArrayList<>();
        for (int i = 0; i < s.length() ; ++i)
            for (int j = i+1; j <= s.length(); ++j) {
                String substring = s.substring(i, j);
                char[] subarr = substring.toCharArray();
                Arrays.sort(subarr);
                subarrs.add(subarr);
        }

        return subarrs;
    }

    public static int sherlockAndAnagrams(String s) {
        // Write your code here
        List<char[]> subarrays = getSubStrings(s);
        String subs = getSubStrings(s)
                .stream()
                .map(Arrays::toString)
                .collect(Collectors.joining(", "));
        //System.out.printf("subs: %s%n", subs);

        int anagrams = 0;
        for (int i = 0; i < subarrays.size() ; ++i) {
            for (int j = i+1 ; j < subarrays.size(); ++j) {
                if (Arrays.equals(subarrays.get(i), subarrays.get(j))) {
                    anagrams++;
                }
            }
        }

        return anagrams;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, q).forEach(qItr -> {
            try {
                String s = bufferedReader.readLine();

                int result = Result.sherlockAndAnagrams(s);

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

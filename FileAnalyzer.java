import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.Paths;
import java.util.*;

class FileAnalyzer {

    public static void main(String[] args) {
        String fileContent = "";
        TreeMap<String, Integer> sortedWordMap = new TreeMap<String, Integer>();

        if (args.length > 0){
            File file = new File(args[0]);
            fileContent = fileToString(file.getAbsolutePath());

            // Sorted by value in descending order. Words are keys, appearance count are values.
            sortedWordMap = getSortedWords(fileContent);

            printWordCount(fileContent);
            printTop10Words(sortedWordMap);
            printLastLineWithMostUsedWord(fileContent, sortedWordMap.firstEntry().getKey());
        }

        else {
            System.out.println("Include file as first argument in command line");
        }
    }

    public static void printWordCount (String fileContent){
        fileContent.trim(); // if file contains only spaces
        String words [] = fileContent.split("\\s+"); // split at spaces
        System.out.println("Word count in file:\n" + words.length + "\n");
    } 

    public static void printTop10Words(TreeMap <String, Integer> sortedMap){
        System.out.println("Top 10 frequently used words with appearance count:");
        int entriesToPrint = 10;
        for (SortedMap.Entry<String, Integer> entry: sortedMap.entrySet()){
            if (entriesToPrint > 0){
                System.out.println(entry.getKey() + " " + entry.getValue());
                entriesToPrint--;
            }
            else {
                System.out.println();
                return;
            }
        }
    }

    // Prints the last line in the file that contains the most commonly used word 
    public static void printLastLineWithMostUsedWord(String fileContent, String mostCommonWord){
        String sentences [] = fileContent.split("!+|\\.+|[\\r?\\n]+"); // split at ! . endl
        List<String> sentenceList;

        for (int i = 0; i < sentences.length; i++){
            sentences[i] = sentences[i].trim();
        }

        sentenceList = Arrays.asList(sentences);
        Collections.reverse(sentenceList); // sentences towards end of file are examined first

        // for every sentence in the file, create a word set
        for (String sentence : sentenceList){
            if (sentence.length() > 0){
                String words [] = sentence.split("\\s|,|:|\""); // split at spaces , : "

                // standardize keys for word set
                for (int i = 0; i < words.length; i++){
                    String word = words[i];
                    word = word.toLowerCase();
                    words[i] = word;
                }

                Set<String> wordSet = new HashSet<>(Arrays.asList(words));

                if (wordSet.contains(mostCommonWord)){
                    System.out.println("Last line containing most frequently used word:\n" + sentence);
                    return;
                }
            }
        }
    }

    // Returns a sortedMap sorted by descending values. Words as keys, appearance count as values.
    private static TreeMap<String, Integer> getSortedWords (String fileContent){
        HashMap<String, Integer> wordsMap = new HashMap<String, Integer>();

        String words [] = fileContent.split("\\.|,|:|\"|\\s+|[\\r?\\n]+"); // split at . , : spaces endl "

        for (String word : words){
            if (word.length() > 0){ // eliminate empty elements from two sequential delimiters
                word = word.toLowerCase();
                if (!wordsMap.containsKey(word)){
                    wordsMap.put(word,1);
                }
                else {
                    wordsMap.put(word, wordsMap.get(word) + 1);
                }
            }
        }

        TreeMap<String, Integer> sortedMap = sortMapByValue(wordsMap);

        return sortedMap;
    }

    // create a TreeMap that sorts in descending order by appearance count
    private static TreeMap<String, Integer> sortMapByValue(HashMap<String, Integer> map){
        Comparator<String> comparator = new ValueComparator(map);
        TreeMap<String, Integer> result = new TreeMap<String, Integer>(comparator);
        result.putAll(map);
        return result;
    }

    // returns a string of the content in the file
    private static String fileToString(String filePath){
        String fileContent = "";
        try {
            fileContent = new String (Files.readAllBytes(Paths.get(filePath)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }
}

// ValueComparator establishes descending by value order for map creation
// https://www.programcreek.com/2013/03/java-sort-map-by-value/
class ValueComparator implements Comparator<String>{
    HashMap<String, Integer> map = new HashMap<String, Integer>();
 
    public ValueComparator(HashMap<String, Integer> map){
        this.map.putAll(map);
    }
 
    @Override
    public int compare(String s1, String s2) {
        if (map.get(s1) >= map.get(s2)){
            return -1;
        } else {
            return 1;
        }   
    }
}
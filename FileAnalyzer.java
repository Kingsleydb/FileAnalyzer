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

            fileContent = readAllBytesJava7(file.getAbsolutePath());

            printWordCount(fileContent);
            sortedWordMap = getSortedWords(fileContent);
            printTop10(sortedWordMap);
            // printLastLineWithMCW(fileContent, sortedWordMap);
        }

        else {
            System.out.println("Include file as first argument in command line");
        }
    }

    public static void printWordCount (String fileContent){
        String words [] = fileContent.split("\\s+|[\\r?\\n]+");
        System.out.println("Total number of words in file: " + words.length);
    } 

    private static TreeMap<String, Integer> getSortedWords (String fileContent){
        HashMap<String, Integer> wordsMap = new HashMap<String, Integer>();

        String words [] = fileContent.split("\\.|,|:|\"|\\s+|[\\r?\\n]+");
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

    public static void printTop10(TreeMap <String, Integer> sortedMap){
        System.out.println("Top 10 frequently used words with appearance count:");
        int entriesToPrint = 10;
        for (SortedMap.Entry<String, Integer> entry: sortedMap.entrySet()){
            if (entriesToPrint > 0){
                System.out.println(entry.getKey() + " " + entry.getValue());
                entriesToPrint--;
            }
            else {
                break;
            }
        }
    }

    private static TreeMap<String, Integer> sortMapByValue(HashMap<String, Integer> map){
        Comparator<String> comparator = new ValueComparator(map);
        TreeMap<String, Integer> result = new TreeMap<String, Integer>(comparator);
        result.putAll(map);
        return result;
    }

    private static String readAllBytesJava7(String filePath){
        String fileContent = "";
        try {
            fileContent = new String ( Files.readAllBytes( Paths.get(filePath) ) );
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }
}

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
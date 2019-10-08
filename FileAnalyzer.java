import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.Paths;
import java.util.*;

class FileAnalyzer {
    public static void main(String[] args) {
        String content = "";
        int wordCount = 0;
        String freqWords = "";

        if (args.length > 0){
            File file = new File(args[0]);        

            content = readAllBytesJava7(file.getAbsolutePath());
            // wordCount = getWordCount(content);
            freqWords = getFreqWords(content);

            // System.out.println("Word count is: " + wordCount);
        }
        else {
            System.out.println("No file detected");
        }
    }

    public static int getWordCount (String content){
        String words [] = content.split("\\s+|[\\r?\\n]+");
        for (String word : words){
            System.out.println( "--->" + word);
        }
        return words.length;
    } 

    public static String getFreqWords (String content){
        Hashtable<String, Integer> wordsDict = new Hashtable<String, Integer>();
        String words [] = content.split("\\.|,|:|\"|\\s+|[\\r?\\n]+");
        for (String word : words){
            // eliminate empty entries
            if (word != ""){
                word = word.toLowerCase();
                // first entry into dictionary
                if (!wordsDict.containsKey(word)){
                    wordsDict.put(word,1);
                }
                else {
                    wordsDict.put(word, wordsDict.get(word) + 1);
                }
            }
        }
        
        return "";
    }

    // todo
    // public static String getLineWithFreqWord (String content){

    // }

    // https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
    private static String readAllBytesJava7(String filePath){
        String content = "";
        try
        {
            content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return content;
    }
}
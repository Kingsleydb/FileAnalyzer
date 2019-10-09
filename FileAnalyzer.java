import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.Paths;
import java.util.*;

class FileAnalyzer {

    private String mostUsedWord = "";

    public static void main(String[] args) {
        String content = "";
        int wordCount = 0;
        String freqWords = "";

        if (args.length > 0){
            File file = new File(args[0]);        

            content = readAllBytesJava7(file.getAbsolutePath());
            wordCount = getWordCount(content);
            freqWords = getFreqWords(content);

        }
        else {
            System.out.println("Include file as first argument in command line");
        }
    }

    public static int getWordCount (String content){
        String words [] = content.split("\\s+|[\\r?\\n]+");
        return words.length;
    } 

    public static String getFreqWords (String content){
        Hashtable<String, Integer> wordsDict = new Hashtable<String, Integer>();
        String words [] = content.split("\\.|,|:|\"|\\s+|[\\r?\\n]+");
        for (String word : words){
            // eliminate empty elements from two sequential delimiters
            if (word.length() > 0){
                word = word.toLowerCase();
                if (!wordsDict.containsKey(word)){
                    wordsDict.put(word,1);
                }
                else {
                    wordsDict.put(word, wordsDict.get(word) + 1);
                }
            }
            // TODO: SORT DATA BY VALUE, PRINT TOP 10
            // TODO: REMEMBER TO SET MOST USED WORD
        }
        return "";
    }

    // todo
    // public static String getLineWithFreqWord (String content){
        
    // }

    private String getMostUsedWord(){
        return mostUsedWord;
    }

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
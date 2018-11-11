package edu.sdsu.cs;

import edu.sdsu.cs.datastructures.BalancedMap;
import edu.sdsu.cs.datastructures.IMap;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class App{

    private List<String> tokens;

    public App(){
        this.tokens = new ArrayList<>();
    }

    private static class Bigram<E, F> implements Comparable<Bigram<E,F>> {

        private E grouping1;
        private F grouping2;

        public Bigram(E one, F two){
             grouping1 = one;
             grouping2 = two;
        }

        public String toString(){
            return grouping1.toString() + " " + grouping2.toString();
        }

        public String getFirstWord(){
            return grouping1.toString();
        }

        public String getSecondWord(){
            if(grouping2.getClass() == String.class)
                return grouping2.toString();
            else {
                return ((Bigram) grouping2).getFirstWord();
            }
        }

        public String getThirdWord(){
            if(grouping2.getClass() == Bigram.class)
                return ((Bigram) grouping2).getSecondWord();
            else
                return null;
        }

        @Override
        public int compareTo(Bigram<E, F> o) {
            if(o.toString().equals(this.toString()))
                return 0;
            else if(o.toString().compareTo(this.toString()) > 0)
                return 1;
            else
                return -1;
        }
    }

    public static void main( String[] args ) throws IOException {

        App app = new App();

        if(args.length != 2){
            System.out.println("Invalid # of arguments. Two parameters needed."
                    + "\nFor first parameter, " +
                    "provide valid text file. For second parameter, provide " +
                    "valid output text file path");
            return;
        }
        try {
            File input = new File(args[0]);
            app.readFile(Paths.get(input.getAbsolutePath()));
        }catch(Exception e){
            System.out.println("Invalid input file");
        }
        try {
            File output = new File(args[1]);
            /** For Bigram Output **/
            app.writeFile(
                    output.getAbsolutePath(),
                    app.printBigram(
                            app.generateBigramMap(
                                    app.convertTokenListToBigramList()
                            )
                    ),
                    app.printTrigrams(
                            app.generateTrigramMap(
                                    app.convertTokenListToTrigramList()
                            )
                    )
            );
        } catch(FileNotFoundException e){
            System.out.println("Invalid output file");
        }
    }

    public List<String> printBigram(IMap<Bigram<String, String>, Integer> bmap){
        List<String> toPrint = new ArrayList<>();
        toPrint.add("Bigrams\n");
        toPrint.add(String.format("%-10s %-15s %-15s \n---------------------" +
                "-----------------" +
                "", "Count", "First Word", "Second Word"));
        for(Bigram e : bmap.keyset()){
            toPrint.add(String.format(
                    "%-10d %-15s %-15s",
                    bmap.getValue(e),
                    e.getFirstWord(),
                    e.getSecondWord()));
        }
        return toPrint;
    }

    public IMap<Bigram<String, String>, Integer>
    generateBigramMap(List<Bigram<String, String>> bigramList){
        IMap<Bigram<String, String>, Integer> bigramMap = new BalancedMap<>();
        for(Bigram e : bigramList){
            if(bigramMap.contains(e)){
                int newCount = bigramMap.getValue(e) + 1;
                Bigram copy = e;
                bigramMap.delete(e);
                bigramMap.add(copy, newCount);
            }
            else {
                bigramMap.add(e, 1);
            }
        }
        return bigramMap;
    }

    public List<String>
    printTrigrams(IMap<Bigram<String, Bigram<String, String>>, Integer> tmap){
        List<String> trigrams = new ArrayList<>();
        trigrams.add("\nTrigrams\n");
        trigrams.add(String.format("%-10s %-15s %-15s %-15s " +
                "\n-----------------------------------------" +
                "------------", "Count", "First Word", "Second Word",
                "Third Word"));
        for(Bigram e : tmap.keyset()){
            trigrams.add(String.format(
                    "%-10d %-15s %-15s %-15s",
                    tmap.getValue(e),
                    e.getFirstWord(),
                    e.getSecondWord(),
                    e.getThirdWord()));
        }
        return trigrams;
    }

    public IMap<Bigram<String, Bigram<String, String>>, Integer>
    generateTrigramMap(List<Bigram<String, Bigram<String, String>>> trigramList)
    {
        IMap<Bigram<String, Bigram<String, String>>, Integer> trigramMap =
                new BalancedMap<>();
        for(Bigram e : trigramList){
            if(trigramMap.contains(e)){
                int newCount = trigramMap.getValue(e) + 1;
                Bigram copy = e;
                trigramMap.delete(e);
                trigramMap.add(copy, newCount);
            }
            else{
                trigramMap.add(e, 1);
            }
        }
        return trigramMap;
    }

    public List<Bigram<String, Bigram<String, String>>>
    convertTokenListToTrigramList(){
        Iterator<String> iter = this.tokens.iterator();
        Iterator<String> iter2 = this.tokens.iterator();
        Iterator<String> iter3 = this.tokens.iterator();
        List<Bigram<String, Bigram<String, String>>> trigrams =
                new ArrayList<>();
        iter.next();
        iter.next();
        iter2.next();
        while(iter.hasNext() && iter2.hasNext() && iter3.hasNext()){
            trigrams.add(
                    new Bigram<>(iter3.next(),
                            new Bigram<>(iter2.next(),
                                    iter.next())));
        }
        return trigrams;
    }

    public List<Bigram<String, String>> convertTokenListToBigramList(){
        Iterator<String> iter = this.tokens.iterator();
        Iterator<String> iter2 = this.tokens.iterator();
        List<Bigram<String, String>> bigrams = new ArrayList<>();
        iter.next();
        while(iter.hasNext() && iter2.hasNext()){
            bigrams.add(new Bigram<>(iter2.next(), iter.next()));
        }
        return bigrams;
    }

    public List<String> readFile(Path path){
        List<String> lines = null;
        Scanner tokenizer;
        try {
            lines = Files.readAllLines(path, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String line : lines){
            tokenizer = new Scanner(line);
            while(tokenizer.hasNext()){
                this.tokens.add(tokenizer.next());
            }
        }
        return this.tokens;
    }

    public void writeFile(String path,
                          List<String> bigrams,
                          List<String> trigrams)
            throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        for(String e : bigrams){
            writer.write(e);
            writer.write("\n");
        }
        for(String e : trigrams){
            writer.write(e);
            writer.write("\n");
        }
        writer.close();
    }

}

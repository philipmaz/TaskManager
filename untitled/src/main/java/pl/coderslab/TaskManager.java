package pl.coderslab;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import pl.coderslab.ConsoleColors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class TaskManager {

    static final String filename="tasks.csv";
    static String[][] tasks;


    public static void main(String[] args){
        tasks=convertToArray(filename);
        Scanner scanner=new Scanner(System.in);
        printOptions();

        while(scanner.hasNextLine()){
            String input=scanner.nextLine();
            input=input.toLowerCase().trim();
            switch (input) {
                case "list":
                    printLines(tasks);
                    break;
                case "add":
                    addTask();
                    break;
                case "remove":
                    removeTask(tasks, getTheNumber());
                    System.out.println("Value was successfully deleted.");
                    break;
                case "exit":
                    saveTabToFile(filename, tasks);
                    System.out.println(ConsoleColors.RED + "Bye, bye.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please select a correct option.");
            }
            System.out.println();
            printOptions();
            }

        }

    public static void printOptions() {
        System.out.println(ConsoleColors.BLUE + "PLease select an option:"+ ConsoleColors.RESET);
        System.out.println("add");
        System.out.println("remove");
        System.out.println("list");
        System.out.println("exit");
        }

    private static void addTask(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task description");
        String description = scanner.nextLine();
        System.out.println("Please add task due date");
        String dueDate = scanner.nextLine();
        System.out.println("Is your task important: true/false");
        String isImportant = scanner.nextLine();

        tasks =  Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length-1] = new String[3];
        tasks[tasks.length-1][0] = description;
        tasks[tasks.length-1][1] = dueDate;
        tasks[tasks.length-1][2] = isImportant;
    }
    public static boolean isNumberGreaterEqualZero(String input) {
        if (NumberUtils.isNumber(input)) {
            return Integer.parseInt(input) >= 0;
        }
        return false;

    }
    public static int getTheNumber() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select number to remove.");
        String n = scanner.nextLine();
        while (!isNumberGreaterEqualZero(n)) {
            System.out.println("Incorrect argument passed. Please give number greater or equal 0");
            scanner.nextLine();
        }
        return Integer.parseInt(n);

    }
    private static void removeTask(String[][] tab, int index) {
        try {
            if (index < tab.length) {
                tasks = ArrayUtils.remove(tab, index);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Element not exist in tab");
        }
    }

    public static void saveTabToFile(String filename, String[][] tab) {
        Path dir = Paths.get(filename);
        String[] lines = new String[tasks.length];
        for (int i = 0; i < tab.length; i++) {
            lines[i] = String.join(",", tab[i]);
        }
        try {
            Files.write(dir, Arrays.asList(lines));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void printLines(String[][] tab){
        for (int i = 0; i < tab.length; i++) {
            System.out.print(i+":");
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print(tab[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static String[][] convertToArray(String filename){
        // read and convert file to Array in right format
        Path path = Paths.get(filename);
        String[][] tab=null;
        if(!Files.exists(path)){
            System.out.println("File doesnt exist.");
            System.exit(0);
        }
        try{
            List<String> strings=Files.readAllLines(path);
            tab=new String[strings.size()][strings.get(0).split(",").length];
            for (int i = 0; i < strings.size() ; i++) {
                String[] split=strings.get(i).split(",");
                for (int j = 0; j < split.length; j++) {
                    tab[i][j]=split[j];
                }

            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return tab;
    }
}

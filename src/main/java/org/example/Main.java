package org.example;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Main {
    public static Database_Handler handler;
    public static void addFile(String name, String path, String extension, String size){
        String qu = "INSERT INTO MEMBER VALUES (" +
                "'" + name + "'," +
                "'" + path + "'," +
                "'" + extension + "'," +
                "'" + size + "')";
        handler.execAction(qu);
    }

    public static void retrieveFileInfo(String table) {
        String qu = "SELECT * FROM " + table;
        ResultSet resultSet = handler.execQuery(qu);
        try{
            while (resultSet.next()){
                String name = resultSet.getString("NAME");
                String path = resultSet.getString("PATH");
                String extension = resultSet.getString("EXTENSION");
                String size = resultSet.getString("SIZE");
                System.out.println("File Name: " + name + "\tPath: " + path + "\tExtension: " + extension + "\tFile Size: " + size);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("To creat a table, enter 1. To add a file to the table, enter 2. To display the table, enter 3.");
        String userInput = scanner.nextLine();
        if (userInput.equals("1")){
            handler = new Database_Handler();
            System.out.println("Input the name of the table");
            handler.tableName = scanner.nextLine();
        }else if(userInput.equals("2")){
            System.out.println("Input the file directory");
            String directory = scanner.nextLine();
            File inputFile = new File(directory);

            for(File file : inputFile.listFiles()){
                String name = file.getName();
                String path = file.getPath();
                long size = file.length();
                String extension = "";
                for(int i = 0; i < name.length() ; i++){
                    if(name.charAt(i) == '.'){
                        extension = name.substring(i+1);
                    }
                }
            }

        }else if(userInput.equals("3")) {
            handler = Database_Handler.getHandler();
            try {
                handler.displayTable();
            } catch (Exception throwables) {
                throw new RuntimeException(throwables);
            }
        }


    }
}
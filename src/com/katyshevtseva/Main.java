package com.katyshevtseva;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        File file = new File("D://", "file.txt");
        File source = new File("D://", "source.txt");
        StringBuilder text = new StringBuilder();


        try (FileReader reader = new FileReader(source); FileWriter writer = new FileWriter(file)) {

            int c;
            while ((c = reader.read()) != -1) {
                text.append((char) c);
            }

            String result = text.toString().replaceAll("[\\d]{1,2}:[\\d]{1,2}", "")
                    .replaceAll("\\n|\\r\\n", " ")
                    .replaceAll("\\.", ".\n");


            writer.write(result);
            writer.flush();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

package com.example.todolist;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileHelper {
    //This is the file name that i want to save onto the device memory, So i defined it at the beginning
    public static final String FILENAME ="listinfo.dat";

    //i'll create a method to write the data to this file.
    public static void writeData(ArrayList<String> item, Context context)
    {
        //code to save a list in this method and i'll use final output stream class,
        //so this class is used to write data into a file
        //we will create an Object from this class
        try {
            //we can open and write the  data to file after we need to close the file.
            FileOutputStream fos = context.openFileOutput(FILENAME,Context.MODE_PRIVATE);
            ObjectOutputStream oas = new ObjectOutputStream(fos);
            oas.writeObject(item);
            oas.close();

        } catch (FileNotFoundException e) {
           e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Method to read this file
    public static ArrayList<String> readData(Context context)
    {
        ArrayList<String> itemList = null;

        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            ObjectInput ois = new ObjectInputStream(fis);
            //type casting
            itemList = (ArrayList<String>) ois.readObject();
        } catch (FileNotFoundException e) {
            //it is necessary for the very first opening of the app
            itemList = new ArrayList<>();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
         return itemList;
    }
}

package com.example.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText item;
    Button add;
    ListView listView;
    ArrayList<String> itemList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    item =findViewById(R.id.editText);
    add = findViewById(R.id.button);
    listView =findViewById(R.id.list);

        //Need ArrayList, so this will hold my items array list string
        //Need an adapter to connect the array list and list view
        //So, now if I add items from array list to list view using the adapter directly,

        //this will read the data and then send it to the ArrayList if there is any data
        itemList = FileHelper.readData(this);

        //send the Data using the array adapter
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1,itemList);

        //now i need to assign this adapter to the listView
        listView.setAdapter(arrayAdapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Take an item from the editText and assign it to a string container
                String itemName = item.getText().toString();

                //Add this item to the ArrayList
                itemList.add(itemName);
                item.setText("");
                FileHelper.writeData(itemList,getApplicationContext());

                //Need to notify the adapter to get these changes
                arrayAdapter.notifyDataSetChanged();

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Delete");
                alert.setMessage("Do you want to delete this item from the list");
                alert.setCancelable(false);

                //define the negative button and add a clickListener

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                //define the positive button and add a clickListener to this button as well
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //First, if user click yes it will delete from the arrayList
                        itemList.remove(position);
                        //Second, list of you need to renew, we can do this, by notifying the adapter
                        arrayAdapter.notifyDataSetChanged();
                        //Third, i need to write this new array list to the file, Because
                        //the application is closed ,we don't want to lose the data
                        FileHelper.writeData(itemList,getApplicationContext());
                    }
                });

                //And lastly, we will need to finish the alert dialogue,
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });

    }
}
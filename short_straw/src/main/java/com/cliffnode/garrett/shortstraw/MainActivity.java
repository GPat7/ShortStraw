package com.cliffnode.garrett.shortstraw;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> choiceList = new ArrayList<>();
    Random randomGenerator = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup for the button
        Button pickButton = (Button) findViewById(R.id.pick_button);
        pickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choiceList.size() > 0) {
                    //Find the index of our next choice
                    int choice = randomGenerator.nextInt(choiceList.size());

                    showChoice(choice);
                }
            }
        });

        maxRange = 100;

        Button randomButton = (Button) findViewById(R.id.random);
        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Random Number(between 1-" + maxRange);

                String message;
                // Set up the buttons
                if (maxRange > 0) {
                    message = Integer.toString(randomGenerator.nextInt(maxRange) + 1);
                }
                else{
                    message = "Max number must be greater than 0";
                }

                builder.setMessage(message);

                builder.show();
            }
        });

        //Set up for the list view of choices
        ListView choiceListView = (ListView) findViewById(R.id.choices);
        ArrayAdapter<String> choiceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, choiceList);
        choiceListView.setAdapter(choiceAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    private void showChoice(int choice) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set up the buttons
        builder.setMessage("Your Choice is " + choiceList.get(choice) + "!!!!!!!!!!!");

        builder.show();
    }

    private void addItem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Type the item to add");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        choiceList.add(input.getText().toString());
                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private int maxRange;

    private void setRandomRange() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set Max Random Value");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            maxRange = Integer.parseInt(input.getText().toString());
                        } catch (NumberFormatException e) {
                            makeToast("Number could not be read");
                        }

                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void makeToast(String text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_item:
                addItem();
                return true;
            case R.id.set_range:
                setRandomRange();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

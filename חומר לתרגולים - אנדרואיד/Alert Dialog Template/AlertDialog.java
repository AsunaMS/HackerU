package com.example.notesexample.models;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class AlertDialog extends android.app.AlertDialog.Builder {


    public AlertDialog(Context context) {
        super(context);
        this.setIcon(android.R.drawable.ic_dialog_alert)
                // set the title
                .setTitle("Are you sure to Exit")
                // set the message
                .setMessage("Exiting will call Bla bla")
                // set the positive button
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context.getApplicationContext(), "Bla Bla", Toast.LENGTH_LONG).show();
                    }
                })
                //set negative button
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what should happen when negative button is clicked
                        Toast.makeText(context.getApplicationContext(), "Nothing Happened", Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }
}


package com.notur.dailyactivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText txtKtg, txt_usia, txtDesc, txt_status;
    TextView txt_hasil;
    String ktg, dsc;
    FloatingActionButton fab,fab2;
    ListView lv;
    List<String> data = new ArrayList<>();
    String getvalue = "";
    String user = "noy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txt_hasil   = findViewById(R.id.txt_hasil);
        lv = findViewById(R.id.list);

        lv.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, data));


        fab = findViewById(R.id.fab);
        fab2 = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //txt_hasil.setText(null);
                DialogForm();
            }
        });

        for (int i = 0; i < data.size(); i++) {
            data.add((String)data.get(i));
        }


        Log.e("test", String.valueOf(data));

        //final String finalGetvalue = getvalue;
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL,new String[]{"ed@wikasita.co.id"});
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"soetiyono@wikasita.co.id"});

                email.putExtra(Intent.EXTRA_SUBJECT, "[Daily Activity]"+user);
                email.putExtra(Intent.EXTRA_TEXT, "test...test");

                //need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });
    }

    // untuk mengosongi edittext
    private void kosong(){
        txtKtg.setText(null);
        txtDesc.setText(null);
    }

    private void DialogForm() {
        dialog = new AlertDialog.Builder(MainActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Form Biodata");

        txtKtg = dialogView.findViewById(R.id.txt_ktg);
        txtDesc = dialogView.findViewById(R.id.txt_desc);

        //kosong();

        dialog.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                ktg = txtKtg.getText().toString();
                dsc = txtDesc.getText().toString();

                txt_hasil.setText("Katagori : " + ktg + "\n" + "Keterangan : " + dsc);
                data.add("Katagori : " + ktg + "\n" + "Keterangan : " +dsc);
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

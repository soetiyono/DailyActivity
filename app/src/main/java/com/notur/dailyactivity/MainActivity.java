package com.notur.dailyactivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.notur.dailyactivity.db.DataHelper;
import com.notur.dailyactivity.db.DbHelper;

public class MainActivity extends AppCompatActivity {

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText txtKtg, txtDesc;
    TextView txt_hasil;
    String ktg, dsc;
    FloatingActionButton fab,fab2;
    String user = "noy";
    DbHelper dbHelper;
    String[] daftar;
    DataHelper helper;
    String log1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DbHelper(this);
        helper = new DataHelper(this);

        txt_hasil   = findViewById(R.id.txt_hasil);


        fab = findViewById(R.id.fab);
        fab2 = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogForm();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL,new String[]{"ed@wikasita.co.id"});
                email.putExtra(Intent.EXTRA_SUBJECT, "[Daily Activity]"+user);
                email.putExtra(Intent.EXTRA_TEXT, log1);

                //need this to prompts email client only
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });

        getData();
        fileExport();
    }

    @SuppressLint("InflateParams")
    private void DialogForm() {
        dialog = new AlertDialog.Builder(MainActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Insert your Activity");

        txtKtg = dialogView.findViewById(R.id.txt_ktg);
        txtDesc = dialogView.findViewById(R.id.txt_desc);

        dialog.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                ktg = txtKtg.getText().toString();
                dsc = txtDesc.getText().toString();

                addData();
                finish();
                startActivity(getIntent());
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

    public void addData(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("insert into data( katagori, keterangan ) values ('" +
                ktg+ "','"+
                dsc+ "')");
        Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
    }

    public void getData(){
        final SQLiteDatabase ReadData = dbHelper.getReadableDatabase();
        @SuppressLint("Recycle") final Cursor cursor = ReadData.rawQuery("SELECT * FROM data order by id desc", null);
        daftar = new String[cursor.getCount()];
        cursor.moveToLast();

        for (int count = 0; count < cursor.getCount();count++){
            cursor.moveToPosition(count);
            daftar[count] = (cursor.getString(1)+"\n"+cursor.getString(2)+"\n"+cursor.getString(3));
        }

        final ListView listView = findViewById(R.id.list);
        ArrayAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, daftar);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //helper.removeData(i+1);
                Log.e("posisi", String.valueOf(i+1));
                return true;
            }
        });

    }

    private void fileExport() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM data WHERE id >= 1 AND id <= 10",null);
        StringBuilder result = new StringBuilder();
        String log = "store: "+result.toString();
        Log.e("footbe", log);
        while (cursor.moveToNext()){
            result.append(cursor.getString(cursor.getColumnIndex("katagori"))).append(";").append(cursor.getString(cursor.getColumnIndex("keterangan"))).append("\r");
            result.append("\n");
        }

        log1 = "store1: "+result.toString();
        Log.e("footbe1", log1);

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

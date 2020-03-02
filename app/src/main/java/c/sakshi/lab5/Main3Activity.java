package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main3Activity extends AppCompatActivity {
    int idnote = -1;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        et = (EditText) findViewById(R.id.noteField);
        Intent intent = getIntent();
        idnote = intent.getIntExtra("noteid", -1);
        if (idnote != -1) {
            Note note = Main2Activity.notes.get(idnote);
            String noteContent = note.getContent();
            et.setText(noteContent);
        }
    }

    public void saveMethod(View view) {
        String content = et.getText().toString();

        Context context = getApplicationContext();
        SQLiteDatabase sqlite = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqlite);
        SharedPreferences sp = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        String user = sp.getString("username", "");
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (idnote != -1) {
            title = "NOTE_" + (idnote + 1);
            dbHelper.updateNote(title, date, content, user);
        } else {
            title = "NOTE_" + (Main2Activity.notes.size() + 1);
            dbHelper.saveNotes(user, title, content, date);
        }

        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}

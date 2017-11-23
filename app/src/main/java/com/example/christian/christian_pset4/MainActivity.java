package com.example.christian.christian_pset4;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.EditText;

import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TodoDatabase theTodoDatabase;
    private Button delButton;
    private EditText editText;
    private ListView listView;
    private TodoAdaptor theAdapter;

    long global_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        editText.setHint("Add a todo!");
        delButton = findViewById(R.id.delButton);
        listView = findViewById(R.id.listView);
        listView.setLongClickable(true);

        theTodoDatabase = TodoDatabase.getInstance(this);

        delButton.setVisibility(View.INVISIBLE);

        listView.setOnItemLongClickListener(new ClickSomeLong());
        listView.setOnItemClickListener(new ClickSome());

        updateData();

        theTodoDatabase = TodoDatabase.getInstance(getApplicationContext());

    }

    public void addTodo(String todo) {
        boolean putInDatabase = theTodoDatabase.insert(todo);
        if (putInDatabase) {
            Toast.makeText(this, "Successfully inserted that todo!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "This did not work!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateData() {
        Cursor todo = theTodoDatabase.getTodos();
        ArrayList<String> todoArray = new ArrayList<String>();
        while (todo.moveToNext()) {
            todoArray.add(todo.getString(1));
        }

        theAdapter = new TodoAdaptor(this, todo);

        listView.setAdapter(theAdapter);

    }

    public void buttonClicked(View view) {
        String newTodo = editText.getText().toString();
        if (editText.length() != 0) {
            addTodo(newTodo);
            editText.setText("");
            updateData();
        } else {
            Toast.makeText(this, "Please fill in a todo!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteButtonClicked(View view) {
        theTodoDatabase.deleteThis(global_id);
        updateData();
        delButton.setVisibility(View.INVISIBLE);
    }


    private class ClickSomeLong implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            global_id = id;
            delButton.setVisibility(View.VISIBLE);
            editText.setHint("Add a todo!");
            return true;
        }
    }

    private class ClickSome implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            theTodoDatabase.checkboxState(id);
            delButton.setVisibility(View.INVISIBLE);
            updateData();

        }
    }
}

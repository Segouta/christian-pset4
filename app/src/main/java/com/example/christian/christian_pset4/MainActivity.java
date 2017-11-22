package com.example.christian.christian_pset4;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.CheckBox;
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

        updateData();

        theTodoDatabase = TodoDatabase.getInstance(getApplicationContext());

    }

    public void addTodo(String todo) {
        boolean putInDatabase = theTodoDatabase.insert(todo);
        if (putInDatabase) {
            Toast.makeText(this, "Successfully inserted dat ting", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Dat ting went wrong man", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateData() {
        Cursor todo = theTodoDatabase.getTodos();
        ArrayList<String> todoArray = new ArrayList<String>();
        while(todo.moveToNext()) {
            todoArray.add(todo.getString(1));
        }

        theAdapter = new TodoAdaptor(this, todo);
//        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, todoArray);

        listView.setAdapter(theAdapter);
//        listView.setAdapter(adapter);
    }

    public void buttonClicked(View view) {
        String newTodo = editText.getText().toString();
        if (editText.length() != 0) {
            addTodo(newTodo);
            editText.setText("");
            updateData();
        }
        else {
            Toast.makeText(this, "text field was empty oen", Toast.LENGTH_SHORT).show();
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
            System.out.println(id);
            delButton.setVisibility(View.VISIBLE);
            editText.setHint("Add a todo!");
//            deleteString = parent.getItemAtPosition(position).toString();
            return true;
        }
    }

    public void onListItemClick(View view) {

        Toast.makeText(this, "you clicked somehere", Toast.LENGTH_SHORT).show();


        if (view != null) {
            CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkBox);
            checkBox.setChecked(!checkBox.isChecked());
        }
    }
}

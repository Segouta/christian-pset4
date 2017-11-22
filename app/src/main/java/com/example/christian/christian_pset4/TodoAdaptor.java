package com.example.christian.christian_pset4;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class TodoAdaptor extends ResourceCursorAdapter {

    public TodoAdaptor(Context context, Cursor cursor) {
        super(context, R.layout.row_todo, cursor, 0);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {


        TextView entryText = view.findViewById(R.id.entryText);
        CheckBox completed = view.findViewById(R.id.checkBox);

        Integer title_id = cursor.getColumnIndex("title");
        Integer completed_id = cursor.getColumnIndex("completed");
        Integer id = cursor.getColumnIndex("_id");


        String title_val = cursor.getString(title_id);
        Integer completed_val = cursor.getInt(completed_id);
        Long id_val = cursor.getLong(id);

        entryText.setText(title_val);
        entryText.setTag(id_val);

        if (completed_val == 0){
            completed.setChecked(false);
        } else {
            completed.setChecked(true);
        }
    }
}
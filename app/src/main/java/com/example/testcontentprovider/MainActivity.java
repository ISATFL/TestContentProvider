package com.example.testcontentprovider;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button select=findViewById(R.id.select);
        final Button delete=findViewById(R.id.delete);
        Button update=findViewById(R.id.update);
        Button add=findViewById(R.id.add);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final View select_view= LayoutInflater.from(MainActivity.this).inflate(R.layout.select_view,null);
                AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("查询单词");
                dialog.setView(select_view);
                final EditText word=select_view.findViewById(R.id.s_word);

                final TextView result=findViewById(R.id.result);
                String sel_word=word.getText().toString();
                dialog.setPositiveButton("查询", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Uri uri=Uri.parse("content://com.example.thewordbooks.provider/words");
                        Cursor cursor=getContentResolver().query(uri,null,"word=?",new String[]{word.getText().toString()},null);
                        if(cursor!=null){
                            while (cursor.moveToNext()){
                                String word=cursor.getString(cursor.getColumnIndex("meaning"));
                                result.setText(word);
                            }
                            cursor.close();
                        }


                    }
                });
                dialog.setNegativeButton("取消",null);
                dialog.show();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View delete_view= LayoutInflater.from(MainActivity.this).inflate(R.layout.delete_view,null);
                AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("删除单词");
                dialog.setView(delete_view);
                final EditText word=delete_view.findViewById(R.id.d_word);
                dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri uri=Uri.parse("content://com.example.thewordbooks.provider/words");
                        getContentResolver().delete(uri,"word=?",new String[]{word.getText().toString()});
                        Toast.makeText(MainActivity.this,"已删除",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setNegativeButton("取消",null);
                dialog.show();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View add_view= LayoutInflater.from(MainActivity.this).inflate(R.layout.add_view,null);
                AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("添加单词");
                dialog.setView(add_view);
                final EditText word=add_view.findViewById(R.id.a_word);
                final EditText meaning=add_view.findViewById(R.id.a_meaning);
                dialog.setPositiveButton("添加", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri uri=Uri.parse("content://com.example.thewordbooks.provider/words");
                        ContentValues values=new ContentValues();
                        values.put("word",word.getText().toString());
                        values.put("meaning",meaning.getText().toString());
                        getContentResolver().insert(uri,values);
                        Toast.makeText(MainActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setNegativeButton("取消",null);
                dialog.show();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View update_view= LayoutInflater.from(MainActivity.this).inflate(R.layout.update_view,null);
                AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("修改单词");
                dialog.setView(update_view);
                final EditText word=update_view.findViewById(R.id.u_word);
                final EditText meaning=update_view.findViewById(R.id.u_meaning);
                dialog.setPositiveButton("修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri uri=Uri.parse("content://com.example.thewordbooks.provider/words");
                        ContentValues values=new ContentValues();
                        values.put("word",word.getText().toString());
                        values.put("meaning",meaning.getText().toString());
                        getContentResolver().update(uri,values,null,null);
                        Toast.makeText(MainActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setNegativeButton("取消",null);
                dialog.show();
            }
        });
    }
}

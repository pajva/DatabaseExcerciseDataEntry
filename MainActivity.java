package com.example.databaseexcercisedataentry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editId,editName,editSalary,editAddress;
    Button btnAddData,btnViewAll,btnUpdate,btnDelete,btnBetween,btnMax,btnOrderBy,btnGroupBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
        editId=findViewById(R.id.et1);
        editName=findViewById(R.id.et2);
        editAddress=findViewById(R.id.et3);
        editSalary=findViewById(R.id.et4);
        btnAddData=findViewById(R.id.bt1);
        btnViewAll=findViewById(R.id.bt2);
        btnUpdate=findViewById(R.id.bt3);
        btnDelete=findViewById(R.id.bt4);
        btnBetween=findViewById(R.id.bt5);
        btnMax=findViewById(R.id.bt6);
        btnOrderBy=findViewById(R.id.bt7);
        btnGroupBy=findViewById(R.id.bt8);
        addData();
        viewAllData();
        updateData();
        deleteData();
        betweenData();
        maxData();
        orderData();
        groupData();
    }

    private void groupData() {
        btnGroupBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = myDb.groupSalary();
                StringBuffer buffer = new StringBuffer();
                while(cursor.moveToNext()) {
                    buffer.append("Id :" + cursor.getString(0) + "\n");
                    buffer.append("Name :" + cursor.getString(1) + "\n");
                    buffer.append("Address :" + cursor.getString(2) + "\n");
                    buffer.append("Salary :" + cursor.getString(3) + "\n");
                }
                showMessage("Data",buffer.toString());
            }
        });
    }

    private void orderData() {
        btnOrderBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = myDb.orderSalary();
                StringBuffer buffer = new StringBuffer();
                while(cursor.moveToNext()) {
                    buffer.append("Id :" + cursor.getString(0) + "\n");
                    buffer.append("Name :" + cursor.getString(1) + "\n");
                    buffer.append("Address :" + cursor.getString(2) + "\n");
                    buffer.append("Salary :" + cursor.getString(3) + "\n");
                }
                showMessage("Data",buffer.toString());
            }
        });
    }

    private void maxData() {
        btnMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = myDb.maxSalary();
                if (cursor.getCount()==0){
                    showMessage("Maximum Salary","No members");
                }else {StringBuffer buffer = new StringBuffer();
                    while(cursor.moveToNext()) {
                        buffer.append("Id :" + cursor.getString(0) + "\n");
                        buffer.append("Name :" + cursor.getString(1) + "\n");
                        buffer.append("Address :" + cursor.getString(2) + "\n");
                        buffer.append("Salary :" + cursor.getString(3) + "\n");
                    }
                    showMessage("Data",buffer.toString());

                }
            }
        });
    }

    private void betweenData() {
        btnBetween.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = myDb.betweenSalary();
                if(cursor.getCount()==0)
                {
                    showMessage("Salary between 2000 & 3000", "No One");
                }else{
                    StringBuffer buffer = new StringBuffer();
                    while(cursor.moveToNext()) {
                        buffer.append("Id :" + cursor.getString(0) + "\n");
                        buffer.append("Name :" + cursor.getString(1) + "\n");
                        buffer.append("Address :" + cursor.getString(2) + "\n");
                        buffer.append("Salary :" + cursor.getString(3) + "\n");
                    }
                    showMessage("Data",buffer.toString());
                }

            }
        });
    }

    private void addData() {
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted=myDb.insertData(editName.getText().toString(),editSalary.getText().toString(),editAddress.getText().toString());
                if(isInserted = true)
                    Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data not inserted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewAllData() {
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if(res.getCount() == 0)
                {
                    showMessage("Error","Nothing found");
                    return;
                }
                else{
                    StringBuffer buffer = new StringBuffer();
                    while(res.moveToNext()) {
                        buffer.append("Id :" + res.getString(0) + "\n");
                        buffer.append("Name :" + res.getString(1) + "\n");
                        buffer.append("Address :" + res.getString(2) + "\n");
                        buffer.append("Salary :" + res.getString(3) + "\n");
                    }
                    showMessage("Data",buffer.toString());
                }

            }
        });
    }

    private void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateData() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDb.updateData(editId.getText().toString(),editName.getText().toString(),editSalary.getText().toString(),editAddress.getText().toString());
                if(isUpdate == true)
                    Toast.makeText(MainActivity.this, "Data updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data not updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteData() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleteRows = myDb.deleteData(editId.getText().toString());
                if(deleteRows > 0)
                    Toast.makeText(MainActivity.this, "Data deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data not deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        return false;
    }
}
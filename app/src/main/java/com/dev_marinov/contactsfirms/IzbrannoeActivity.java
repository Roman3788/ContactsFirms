package com.dev_marinov.contactsfirms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class IzbrannoeActivity extends AppCompatActivity {

    ImageView imageView_back;
    ConstraintLayout container_list_contact_izbrannoe;
    SharedPreferences sharedPreferences_izbrannoe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izbrannoe);

        Window window = getWindow();
        Drawable drawable_gradient = getResources().getDrawable(R.drawable.gradient);
        window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        window.setNavigationBarColor(getResources().getColor(android.R.color.black));
        window.setBackgroundDrawable(drawable_gradient);

        container_list_contact_izbrannoe = findViewById(R.id.container_list_contact_izbrannoe);

        imageView_back = findViewById(R.id.imageView_back);
        imageView_back.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View v) {
            onBackPressed();
        }
    });

    }
    @Override
    protected void onStart() {
        super.onStart();
        updateLIstContact_izbrannoe();
    }

    public void updateLIstContact_izbrannoe() {
        String count_user_star = loadSettingString("count_contact", "0"); // метод возвращает значение по его ключу
        int c_u_s = Integer.parseInt(count_user_star);
        Log.e("IZB_ACT ","int c_u_s" + c_u_s);

        container_list_contact_izbrannoe.removeAllViews(); // удаляет всех детей



        int z = 0;
        for (int i = 1; i <= c_u_s; i++) {

            String value = loadSettingString("user"+ i, "0");
            Log.e("IZB_ACT ","final String value " + value);
            String[] arValue = value.split(";"); // парсинг данных в созданный массив

            String surname = arValue[0];
            String name = arValue[1];
            String otchestvo = arValue[2];
            String phone = arValue[3];
            String check_izbrannoe = arValue[4];
            Log.e("IZB_ACT ","final String check_izbrannoe =" + check_izbrannoe);

            if (check_izbrannoe.equals("1")) {
                z++;
                Log.e("name",name);
                    ConstraintLayout constraintLayoutIzbrannoe = new ConstraintLayout(this);
                    ConstraintLayout.LayoutParams layoutParams_1 = new ConstraintLayout.LayoutParams
                            (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    constraintLayoutIzbrannoe.setBackgroundColor(getResources().getColor(R.color.colorE6E6E6));
                    constraintLayoutIzbrannoe.setPadding(10,20,10,10);

                    layoutParams_1.topMargin = 2; // расстояние между контейнерами с textviews
                    layoutParams_1.topToBottom = (10 + z -1);
                    constraintLayoutIzbrannoe.setLayoutParams(layoutParams_1);
                    //constraintLayoutIzbrannoe.setBackgroundColor(getResources().getColor(R.color.color_izbrannoe));


                  //  Log.e("--IZB_ACT--", "layoutParams_1.topToBottom =" + (layoutParams_1.topToBottom = (10 + i - 1)));
                    constraintLayoutIzbrannoe.setId(10 + z);
                  //  Log.e("--IZB_ACT--", "constraintLayoutIzbrannoe.setId " + (10 + i));

//ФИО
                    TextView newTextView_fio = new TextView(this);
                    newTextView_fio.setText(String.format("%s %s %s", surname, name, otchestvo));
                    Log.e("++++++++IZB_ACT++++++++", "surname, name, otchestvo " + surname + name + otchestvo);
                    ConstraintLayout.LayoutParams layoutParams_2 = new ConstraintLayout.LayoutParams
                            (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams_2.rightMargin = 70;
                    layoutParams_2.leftMargin = 10;
                    newTextView_fio.setLayoutParams(layoutParams_2);


                    newTextView_fio.setId(500000 + z);
                    constraintLayoutIzbrannoe.addView(newTextView_fio);

// телефон
                    TextView newTextView_phone = new TextView(this);
                    newTextView_phone.setText(phone);
                    ConstraintLayout.LayoutParams layoutParams_3 = new ConstraintLayout.LayoutParams
                            (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams_3.leftMargin = 10;
                    layoutParams_3.topToBottom = (500000 + z);
                    newTextView_phone.setLayoutParams(layoutParams_3);
                    constraintLayoutIzbrannoe.addView(newTextView_phone);


                    container_list_contact_izbrannoe.addView(constraintLayoutIzbrannoe);

            }
        }
    }


    // считывает файл
    public String loadSettingString(String key,String default_value)
    {
        // List_contact - имя файла, MODE_MULTI_PROCESS - доступ для всех процессов
        sharedPreferences_izbrannoe = getSharedPreferences("List_contact", MODE_MULTI_PROCESS);
        return sharedPreferences_izbrannoe.getString(key, default_value);
    }



}

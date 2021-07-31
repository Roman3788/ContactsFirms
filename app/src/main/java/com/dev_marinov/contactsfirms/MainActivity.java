package com.dev_marinov.contactsfirms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.time.format.TextStyle;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textView_allUsers;
    TextView textView_favoriteUsers;

    Button bt_add_contact;
    //Button imageView_izbrannoe;
    ImageView imageView_izbrannoe_menu;
    ConstraintLayout container_list_contact;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        Drawable drawable_background = getResources().getDrawable(R.drawable.gradient);
        window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        window.setNavigationBarColor(getResources().getColor(android.R.color.black));
        window.setBackgroundDrawable(drawable_background);




        textView_allUsers = findViewById(R.id.textView_allUsers);
        textView_favoriteUsers = findViewById(R.id.textView_favoriteUsers);

        container_list_contact = findViewById(R.id.container_list_contact);

        // переход в EditActivity
        bt_add_contact = findViewById(R.id.bt_add_contact);
        bt_add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), EditActivity.class);
                intent.putExtra("count_contact", getCount_contact());  // передаем общее кол-во пользователей в списке в виде ключа и значения
                intent.putExtra("action", "add");  // передаем параметр действие (могут быть add или edit) в виде ключа и значения
                startActivity(intent); // добавить в стек activity
            }
        });

    // переход в IzbrannoeActivity
   // imageView_izbrannoe = findViewById(R.id.imageView_izbrannoe);
        imageView_izbrannoe_menu = findViewById(R.id.imageView_izbrannoe_menu);
        imageView_izbrannoe_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), IzbrannoeActivity.class);
                intent.putExtra("count_contact", getCount_contact());
                Log.e("/////MAIN_ACT//////","main_act передает в main_izb " + getCount_contact());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        updateListContact();
    }

    public void updateListContact() {
        String count_user = loadSettingString("count_contact", "0"); // метод возвращает значение по его ключу
        int c = Integer.parseInt(count_user);
        Log.e("MAIN_ACT_update();","int c = " + c);

        int temp = 0; // переменная для хранения значения счетчика избранного

        textView_allUsers.setText(count_user); // вывод сколько всего users в списке
        textView_favoriteUsers.setText(String.valueOf(0)); // вывод нуля для пустого списка

        container_list_contact.removeAllViews(); // удаляет всех детей



        for (int i = 1; i <= c; i++  ) {

            final String value = loadSettingString("user"+ i, "0");
            String[] arValue = value.split(";"); // парсинг данных в созданный массив

            String surname = arValue[0];
            String name = arValue[1];
            String otchestvo = arValue[2];
            String phone = arValue[3];
            final String check_izbrannoe = arValue[4];

            Log.e("MAIN_ACT ","final String check_izbrannoe" + check_izbrannoe);

            ConstraintLayout.LayoutParams layoutParams_1 = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ConstraintLayout newContenerUser = new ConstraintLayout(this);
            newContenerUser.setPadding(10,10,10,10);
            layoutParams_1.topMargin = 1;
            layoutParams_1.topToBottom = (1000 + i - 1);
            newContenerUser.setLayoutParams(layoutParams_1);
            newContenerUser.setBackgroundColor(getResources().getColor(R.color.colorE6E6E6));
            newContenerUser.setId(1000 + i);

//ИМЯ ФАМИЛИЯ
            TextView newTextView_fio = new TextView(this);
            ConstraintLayout.LayoutParams layoutParams_2 = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams_2.leftMargin = 10;
            layoutParams_2.rightMargin = 120;
            newTextView_fio.setId(500000 + i);
            newTextView_fio.setText(String.format("%s %s", surname, name));
            newTextView_fio.setTextSize(15);
            newTextView_fio.setTypeface(null,Typeface.BOLD);
            newTextView_fio.setLayoutParams(layoutParams_2);
            newContenerUser.addView(newTextView_fio);


////ОТЧЕСТВО
            TextView newTextView_otchestvo = new TextView(this);
            newTextView_otchestvo.setId(2342384 + i);
            newTextView_otchestvo.setText(String.format("%s", otchestvo));
            ConstraintLayout.LayoutParams layoutParams_2_1 = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams_2_1.topToBottom = newTextView_fio.getId();
            layoutParams_2_1.leftMargin = 10;
            newTextView_otchestvo.setTextSize(15);
            newTextView_otchestvo.setLayoutParams(layoutParams_2_1);
            newContenerUser.addView(newTextView_otchestvo);


// телефон
            TextView newTextView_phone = new TextView(this);
            newTextView_phone.setText(phone);
            ConstraintLayout.LayoutParams layoutParams_3 = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams_3.leftMargin = 10;
            layoutParams_3.topToBottom = (500000 + i);
            layoutParams_3.topToBottom = newTextView_otchestvo.getId();
            layoutParams_3.topMargin = 5;
            newTextView_phone.setLayoutParams(layoutParams_3);
            newContenerUser.addView(newTextView_phone);

// избранное
            final ImageView new_imageView_star = new ImageView(this);
            ConstraintLayout.LayoutParams layoutParams_4 = new ConstraintLayout.LayoutParams(40,40);
            layoutParams_4.rightToRight = 0;
            layoutParams_4.rightMargin = 90;
            layoutParams_4.topToTop = 0;
            layoutParams_4.topMargin = 10;
            layoutParams_4.leftToRight = newTextView_fio.getId();
            new_imageView_star.setLayoutParams(layoutParams_4);

// УСТАНОВКА СЕРОЙ ИКОНКИ
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                if (check_izbrannoe.equals("0"))
                {
                    new_imageView_star.setBackground(getResources().getDrawable(android.R.drawable.star_off));
                    Log.e("MAIN_ACT в if сработал ", " 0 " + check_izbrannoe);
                }
                else
                {
                    new_imageView_star.setBackground(getResources().getDrawable(R.drawable.star_fav));
                    Log.e("MAIN_ACT else сработал", " 1 " + check_izbrannoe);
                }
            }
            else // показ imageview для старых версий android
            {
                if (check_izbrannoe.equals("0")) {
                    new_imageView_star.setBackgroundResource(android.R.drawable.star_off);
                }
                else
                {
                    new_imageView_star.setBackgroundResource(R.drawable.star_fav);
                }
            }

            // обработчик смены картинки для избранного
            final int i_star_on_off = i;
            Log.e("MAIN_ACT_i_star_on_off", "user " + i_star_on_off);

            new_imageView_star.setOnClickListener(new View.OnClickListener() {
            private boolean bool = true;
                public void onClick(View v) {
                    if (bool) {
                            new_imageView_star.setImageResource(R.drawable.star_fav);
                            final String value = loadSettingString("user" + i_star_on_off, "0");
                        Log.e("MAIN_ACT ", "final String value " + value);
                            String[] arValue = value.split(";"); // парсинг данных в созданный массив
                        Log.e("MAIN_ACT клик ", " " + bool);
                            String surname = arValue[0];
                            String name = arValue[1];
                            String otchestvo = arValue[2];
                            String phone = arValue[3];
                            String check_izbrannoe = arValue[4];

                            if (check_izbrannoe.equals("0")) {
                                check_izbrannoe = "1";

                                Toast toast = Toast.makeText(getApplicationContext(), "User " + i_star_on_off + " помещен в избранное", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                            }
                            else {
                                check_izbrannoe = "0";
                                Toast toast = Toast.makeText(getApplicationContext(), "User " + i_star_on_off + " удален из избранное", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }

                            String user = surname + ";" + name + ";" + otchestvo + ";" + phone + ";" + check_izbrannoe;
                            saveSettingString("user" + i_star_on_off, user);
                            Log.e("MAIN_ACTsaveSettingStri", "i_star_on_off " + i_star_on_off + "user " + user);

                            updateListContact(); //Пересоздаёт список контактов
                    }
                    else {
                        bool = true;  //// ЗДЕСЬ НАВЕРНО ДОЛЖЕН БЫТЬ FALSE
                        new_imageView_star.setImageResource(android.R.drawable.star_off);
                        Toast toast = Toast.makeText(getApplicationContext(), "User " + i_star_on_off + " удален из избранное", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        Log.e("ТУТ  клик 3", " " + bool);
                    }
                }
            });
            newContenerUser.addView(new_imageView_star);

//Редактировать
            ImageView new_imageView_edit = new ImageView(this);
            ConstraintLayout.LayoutParams layoutParams_5 = new ConstraintLayout.LayoutParams(32,32);
            layoutParams_5.rightToRight = 0;
            layoutParams_5.rightMargin = 50;
            layoutParams_5.topToTop = 0;
            layoutParams_5.topMargin = 15;
            layoutParams_5.leftToRight = new_imageView_star.getId();
            new_imageView_edit.setLayoutParams(layoutParams_5);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                new_imageView_edit.setBackground(getResources().getDrawable(R.drawable.edit));
            }
            else
            {
                new_imageView_edit.setBackgroundResource(R.drawable.edit);
            }

              //Вешаем обработчик клика на кнопку редактировать
            String param = String.valueOf(i) + ";" + surname + ";" + name + ";" + otchestvo + ";" + phone+";" + check_izbrannoe;
            Log.e("String param = ", "" + param);

// СЧЕТЧИК ИЗБРАННОЕ (вывод кол-ва избранных в MainActivity)
            int count_contact_izbrannoe = Integer.parseInt(check_izbrannoe);
            if (count_contact_izbrannoe == 1) {
                temp += count_contact_izbrannoe;
            }
            if (count_contact_izbrannoe == 0) {
                temp -= count_contact_izbrannoe;
            }
            textView_favoriteUsers.setText(String.valueOf(temp)); // передача значения счетчика count_contact_izbrannoe

            new_imageView_edit.setTag(param);
            new_imageView_edit.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   String  param = v.getTag().toString();
                   String[] arParam = param.split(";");
                   int number = Integer.valueOf(arParam[0]);

                   Log.e("MAIN_ACT_NUMBER ", " " + number);

                   String surname = arParam[1];
                   String name = arParam[2];
                   String otchestvo = arParam[3];
                   String phone = arParam[4];
                   String check_izbrannoe = arParam[5];

                   Intent intent = new Intent(getBaseContext(), EditActivity.class);
                   intent.putExtra("count_contact", number);  // передаем общее кол-во пользователей в списке в виде ключа и значения
                   intent.putExtra("action", "edit");  // передаем параметр действие (могут быть add или edit) в виде ключа и значения
                   intent.putExtra("surname", surname);  // передаем параметр действие (могут быть add или edit) в виде ключа и значения
                   intent.putExtra("name", name);  // передаем параметр действие (могут быть add или edit) в виде ключа и значения
                   intent.putExtra("otchestvo", otchestvo);  // передаем параметр действие (могут быть add или edit) в виде ключа и значения
                   intent.putExtra("phone", phone);  // передаем параметр действие (могут быть add или edit) в виде ключа и значения
                   intent.putExtra("check_izbrannoe", check_izbrannoe);  // передаем параметр действие (могут быть add или edit) в виде ключа и значения

                   startActivity(intent); // добавить в стек activity
               }
            });

            newContenerUser.addView(new_imageView_edit);
            container_list_contact.addView(newContenerUser);

            //Удалить
            ImageView new_imageView_delete = new ImageView(this);
            ConstraintLayout.LayoutParams layoutParams_6 = new ConstraintLayout.LayoutParams(32,32);
            layoutParams_6.rightToRight = 0;
            layoutParams_6.rightMargin = 10;
            layoutParams_6.topMargin = 15;
            layoutParams_6.topToTop = 0;
            layoutParams_6.leftToRight = new_imageView_edit.getId();
            new_imageView_delete.setLayoutParams(layoutParams_6);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                new_imageView_delete.setBackground(getResources().getDrawable(R.drawable.delete));
            }
            else
            {
                new_imageView_delete.setBackgroundResource(R.drawable.delete);
            }

            final String number_user_delete = String.valueOf(i);
            new_imageView_delete.setTag(number_user_delete);
            new_imageView_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String number_user_delete = v.getTag().toString();
                    deleteSettingString("user" + number_user_delete);

                    updateListContact();
                }
            });
            newContenerUser.addView(new_imageView_delete);
        }
    }

    public int getCount_contact()
    {
      return container_list_contact.getChildCount();  // возращает кол-во детей всех
    }

    // считывает файл
    public String loadSettingString(String key,String default_value)
    {
        // List_contact - имя файла, MODE_MULTI_PROCESS - доступ для всех процессов
        sharedPreferences = getSharedPreferences("List_contact", MODE_MULTI_PROCESS);
        return sharedPreferences.getString(key, default_value);
    }

    public void saveSettingString(String key, String value) {
        // List_contact - имя файла куда будут сохраняться данные, MODE_MULTI_PROCESS - доступ для всех процессов
        sharedPreferences = getSharedPreferences("List_contact", MODE_MULTI_PROCESS);
        SharedPreferences.Editor ed = sharedPreferences.edit(); // edit() - редактирование файлов
        ed.putString(key, value); // добавляем ключ и его значение

        if (ed.commit()) // сохранить файл
        {
            //успешно записано данные в файл
        }
        else
        {
            //ошибка при записи
            Toast.makeText(this, "Write error", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteSettingString(String key)
    {
        String count_user = loadSettingString("count_contact", "0"); // метод возвращает значение по его ключу
        int c = Integer.parseInt(count_user);

        ArrayList<String> myAr = new ArrayList<>();

        for (int i = 1; i <= c; i++  ) {
            if (!key.equals("user" + i)) //Не добавляем в новый массив пользователя которого мы удаляем в
            {
                final String value = loadSettingString("user" + i, "0"); //не нужное осталяем, добавляем во временный массив
                myAr.add(value);
            }
        }
        //Удаляем все из файла
        sharedPreferences = getSharedPreferences("List_contact", MODE_MULTI_PROCESS);
        sharedPreferences.edit().clear().apply();

        if (myAr.size()  > 0) //Если есть хотябы 1 или более пользователей, то создайем новый файл list_contact с новыми ключами
        {
            saveSettingString("count_contact", String.valueOf(myAr.size()));
            for (int i = 1; i <= myAr.size();i++) {
                saveSettingString("user"+(i), myAr.get(i-1));
            }
        }


    }
}

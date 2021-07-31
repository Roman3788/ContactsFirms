package com.dev_marinov.contactsfirms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class EditActivity extends AppCompatActivity {

    ImageView bt_back;
    Button bt_save;
    SharedPreferences pref;

    EditText edit_surname;
    EditText edit_name;
    EditText edit_otchestvo;
    EditText edit_phone;
    String check_izbrannoe;

    TextView textView_error_surname;
    TextView textView_error_name;

    int count_contact; //Общее количество уже добавленных контактов
    String action; //Может иметь значение add или edit
    TextView textView_h1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        // bungle класс необходим для временного хранения данных в процессе выполнения. Это отличный выбор при передаче данных между активностями.
        // Это способ для сохранения данных при смене ориентации экрана.

        Window window = getWindow();
        Drawable drawable_gradient = getResources().getDrawable(R.drawable.gradient);
        window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        window.setNavigationBarColor(getResources().getColor(android.R.color.black));
        window.setBackgroundDrawable(drawable_gradient);


        count_contact = getIntent().getExtras().getInt("count_contact",0);  // получаем значение от MainActivity (получаем кол-во пользователей в списке)
        Log.e("EDIT_ACT_oncreate(); ", "count_contact " + count_contact);

        action = getIntent().getExtras().getString("action"); // получаем значение от MainActivity (получаем кол-во пользователей в списке)
        String surname = getIntent().getExtras().getString("surname");
        String name = getIntent().getExtras().getString("name");
        String otchestvo = getIntent().getExtras().getString("otchestvo");
        String phone = getIntent().getExtras().getString("phone");
        check_izbrannoe = getIntent().getExtras().getString("check_izbrannoe");


        edit_surname = findViewById(R.id.edit_surname);
        edit_name = findViewById(R.id.edit_name);
        edit_otchestvo = findViewById(R.id.edit_otchestvo);
        edit_phone = findViewById(R.id.edit_phone);
        textView_h1 = findViewById(R.id.textView_h1);
        bt_save = findViewById(R.id.bt_save);

        textView_error_surname = findViewById(R.id.textView_error_surname);
        textView_error_name = findViewById(R.id.textView_error_name);

        if (action.equals("add"))
        {
            textView_h1.setText("Новый пользователь");
            edit_surname.setText("");
            edit_name.setText("");
            edit_otchestvo.setText("");
            edit_phone.setText("");
            bt_save.setText("Добавить");
        }
        else
        {
            textView_h1.setText("Редактирование пользователя");
            edit_surname.setText(surname);
            edit_name.setText(name);
            edit_otchestvo.setText(otchestvo);
            edit_phone.setText(phone);
            bt_save.setText("Сохранить");
        }

        //По кнопке назад
        bt_back = findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();// вернуться назад. Удаляет из стека последнюю activity по очереди
            }
        });

 //По кнопке Добавить пользоателя или Редактировать пользователя
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int error;
                // ПОЛУЧНИЕ ФАМИЛИИ
                // метод getText.toString - получение строки из поля. Использовать String.volueOf нельзя - это перевод значение в строку
                String surname = edit_surname.getText().toString();
                surname = surname.trim(); // trim(); удаляет пробелы
                if (surname.getBytes().length == 0) {
                    textView_error_surname.setVisibility(View.VISIBLE); // textView_error_surname ссобщение если фамилия не введена
                    error = 1;
                }
                else {
                surname = surname.substring(0, 1).toUpperCase() + surname.substring(1); // делает заглавную букву полученной строки
                error = 0;
                }
                // ПОЛУЧЕНИЕ ИМЕНИ
                String name = edit_name.getText().toString();
                name = name.trim();
                if (name.getBytes().length == 0) {
                    textView_error_name.setVisibility(View.VISIBLE);
                    error = 1;
                }
                else {
                    name = name.substring(0, 1).toUpperCase() + name.substring(1);
                }
                // ПОЛУЧЕНИЕ ОТЧЕСТВА
                String otchestvo = edit_otchestvo.getText().toString();
                if (!otchestvo.equals("")) { // если не равно пустому значению (если user имеет отчество)
                    otchestvo = otchestvo.substring(0, 1).toUpperCase() + otchestvo.substring(1);
                }
                if (otchestvo.equals("")) { // если равно пустому значению (если у user нет отчества)
                    otchestvo = "не имеет отчества";
                }
                // ПОЛУЧЕНИЕ ТЕЛЕФОНА
                String phone = edit_phone.getText().toString();
                if (!phone.equals("")) {
                }
                if (phone.equals("")) { // если у user нет номера телефона
                    phone = "не имеет номера";
                }

                    // Добавление нового пользователя
                if (error == 0) {
                    if (action.equals("add")) {
                        Log.e("EDIT_ACT_if(act.eq(add)", "count_contact ДО " + count_contact);
                        count_contact++;
                        Log.e("EDIT_ACT_if(act.eq(add)", "count_contact ПОСЛЕ " + count_contact);
                        saveSettingString("count_contact", String.valueOf(count_contact));

                        String user = surname + ";" + name + ";" + otchestvo + ";" + phone + ";" + "0";
                        saveSettingString("user" + count_contact, user);
                        Toast.makeText(getApplicationContext(), "User " + String.valueOf(count_contact) +
                                " успешно сохранен в файл", Toast.LENGTH_SHORT).show();
                        finish(); // EditActivity закроется после нажатия кн. ДОБАВИТЬ и после finish
                    }
                    // редактирование пользователя
                    else {
                        String user = surname + ";" + name + ";" + otchestvo + ";" + phone + ";" + check_izbrannoe;
                        Log.e("EDIT_ACT_else edit", "check_izbrannoe " + check_izbrannoe);
                        saveSettingString("user" + count_contact, user);
                        Log.e("EDIT_ACTsaveSettString ", "count_contact " + count_contact);
                        finish();  // EditActivity закроется после нажатия кн. СОХРАНИТЬ и после finish
                    }
                }
            }
        });

    }
// СНАЧАЛА ТУТ СОЗДЕТСЯ ФАЙЛ SharedPreferences "List_contact" в который записывается все остальное
    public void saveSettingString(String key, String value) {
        // List_contact - имя файла куда будут сохраняться данные, MODE_MULTI_PROCESS - доступ для всех процессов
        pref = getSharedPreferences("List_contact", MODE_MULTI_PROCESS);
        SharedPreferences.Editor ed = pref.edit(); // edit() - редактирование файлов
        ed.putString(key, value); // добавляем ключ и его значение
        if (ed.commit()) // сохранить файл
        {
            //успешно записано данные в файл
          }
        else
        {
            //ошибка при записи
        }
    }


}

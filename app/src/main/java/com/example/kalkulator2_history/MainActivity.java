package com.example.kalkulator2_history;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText edAngka1, edAngka2;
    Button btnTambah, btnKurang, btnBagi, btnKali;
    String value_operator;
    Float value_hasil;

    private List<Nilai> numberModelList;
    private ViewAdapter adapter;
    private RecyclerView recyclerView;
    ListView listView;

    TextView txtA1, txtA2, txtOPR, txtHAS;

    SharedPreferences preferences;

    public static final String KEY_ANGKA1 = ("angka1");
    public static final String KEY_ANGKA2 = ("angka2");
    public static final String KEY_OPERATOR = ("operator");
    public static final String KEY_HASIL = ("hasil");

    public static final String NAME_PREF = ("userInfo");
    public static final Integer MODE_PREF = Integer.valueOf(("0"));
    public static final String LIST_KEY = ("list_key");;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();

        edAngka1 = findViewById(R.id.edAngka1);
        edAngka2 = findViewById(R.id.edAngka2);

        txtA1 = findViewById(R.id.txtAngka1);
        txtA2 = findViewById(R.id.txtAngka2);
        txtOPR = findViewById(R.id.txtOperasi);
        txtHAS = findViewById(R.id.txtHasil);

        btnTambah = findViewById(R.id.btnTambah);
        btnKurang = findViewById(R.id.btnKurang);
        btnBagi = findViewById(R.id.btnBagi);
        btnKali = findViewById(R.id.btnKali);

        recyclerView = findViewById(R.id.rv);
        preferences = getSharedPreferences(NAME_PREF, MODE_PREF);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new ViewAdapter(this, numberModelList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edAngka1.getText().toString().trim().length() > 0 || edAngka2.getText().toString().trim().length() > 0) {
                    String operator = "+";

                    Float A1 = Float.valueOf(edAngka1.getText().toString());
                    Float A2 = Float.valueOf(edAngka2.getText().toString());
                    Float hasil = A1 + A2;

                    Nilai numberModel = new Nilai(A1, A2, operator, hasil);
                    numberModelList.add(numberModel);

                    config.writeListInPref(getApplicationContext(), numberModelList);
                    adapter.setNumberModelList(numberModelList);

                    saveData();
                }else {
                    Toast.makeText(getApplicationContext(), "Inputan Tidak Sesuai", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnKurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edAngka1.getText().toString().trim().length() > 0 || edAngka2.getText().toString().trim().length() > 0 ) {
                    String operator = "-";

                    Float A1 = Float.valueOf(edAngka1.getText().toString());
                    Float A2 = Float.valueOf(edAngka2.getText().toString());
                    Float hasil = A1 - A2;

                    Nilai numberModel = new Nilai(A1, A2, operator, hasil);
                    numberModelList.add(numberModel);

                    config.writeListInPref(getApplicationContext(), numberModelList);
                    adapter.setNumberModelList(numberModelList);

                    saveData();
                }else {
                    Toast.makeText(getApplicationContext(), "Inputan Tidak Sesuai", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnKali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edAngka1.getText().toString().trim().length() > 0 || edAngka2.getText().toString().trim().length() > 0 ) {
                    String operator = "*";

                    Float A1 = Float.valueOf(edAngka1.getText().toString());
                    Float A2 = Float.valueOf(edAngka2.getText().toString());
                    Float hasil = A1 * A2;

                    Nilai nilaiAngka = new Nilai(A1, A2, operator, hasil);
                    numberModelList.add(nilaiAngka);

                    config.writeListInPref(getApplicationContext(), numberModelList);
                    adapter.setNumberModelList(numberModelList);

                    saveData();
                }else {
                    Toast.makeText(getApplicationContext(), "Inputan Tidak Sesuai", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edAngka1.getText().toString().trim().length() > 0 || edAngka2.getText().toString().trim().length() > 0 ) {
                    String operator = "/";

                    Float A1 = Float.valueOf(edAngka1.getText().toString());
                    Float A2 = Float.valueOf(edAngka2.getText().toString());
                    Float hasil = A1 / A2;

                    Nilai nilaiAngka = new Nilai(A1, A2, operator, hasil);
                    numberModelList.add(nilaiAngka);

                    config.writeListInPref(getApplicationContext(), numberModelList);
                    adapter.setNumberModelList(numberModelList);

                    saveData();
                }else {
                    Toast.makeText(getApplicationContext(), "Inputan Tidak Sesuai", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deleteData(){
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().remove(LIST_KEY).apply();
    }

    private void saveData(){
        SharedPreferences preferences = getSharedPreferences(NAME_PREF, MODE_PREF);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(numberModelList);
        editor.putString(LIST_KEY, json);
        editor.apply();
    }

    private void loadData(){
        SharedPreferences preferences = getSharedPreferences(NAME_PREF, MODE_PREF);
        Gson gson = new Gson();
        String json = preferences.getString(LIST_KEY, null);
        Type type = new TypeToken<ArrayList<Nilai>>() {}.getType();
        numberModelList = gson.fromJson(json, type);

        if (numberModelList == null) {
            numberModelList = new ArrayList<>();
        }
    }


}
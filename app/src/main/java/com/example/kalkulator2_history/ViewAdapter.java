package com.example.kalkulator2_history;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.appsearch.SearchResult;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {
    public ArrayList<Float> al_angka_1 = new ArrayList<>();
    public ArrayList<Float> al_angka_2 = new ArrayList<>();
    public ArrayList<String> al_operasi = new ArrayList<>();
    public ArrayList<Float> al_hasil = new ArrayList<>();
    public static final String LIST_KEY = ("list_key");;
    public static final String NAME_PREF = ("userInfo");
    public static final Integer MODE_PREF = Integer.valueOf(("0"));

    SharedPreferences preferences;

    private Context context;
    private List<Nilai> numberModelList;


    public ViewAdapter(ArrayList<Float> al_angka_1, ArrayList<Float> al_angka_2, ArrayList<String> al_operasi, ArrayList<Float> al_hasil, Context context) {
        this.al_angka_1 = al_angka_1;
        this.al_angka_2 = al_angka_2;
        this.al_operasi = al_operasi;
        this.al_hasil = al_hasil;
        this.context = context;
    }


    public ViewAdapter(Context context, List<Nilai> numberModelList) {
        this.context = context;
        this.numberModelList = numberModelList;
    }

    public void setNumberModelList(List<Nilai> numberModelList){
        this.numberModelList = numberModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.angka1.setText(String.valueOf(numberModelList.get(position).getAngka1()));
        holder.angka2.setText(String.valueOf(numberModelList.get(position).getAngka2()));
        holder.operator.setText(numberModelList.get(position).getOperator());
        holder.hasil.setText(String.valueOf(numberModelList.get(position).getHasil()));
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setTitle("DELETE")
                        .setMessage("Yakin Ingin Menghapus Data?")
                        .setPositiveButton("IYA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                removeData(position);
                                saveData();
                                loadData();
                            }
                        }).setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(context, MainActivity.class);
                                context.startActivity(intent);
                            }
                        }).show();
                return false;
            }
        });

    }

    private void saveData(){
        SharedPreferences preferences = context.getSharedPreferences(NAME_PREF, MODE_PREF);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(numberModelList);
        editor.putString(LIST_KEY, json);
        editor.apply();
    }

    private void loadData(){
        SharedPreferences preferences = context.getSharedPreferences(NAME_PREF, MODE_PREF);
        Gson gson = new Gson();
        String json = preferences.getString(LIST_KEY, null);
        Type type = new TypeToken<ArrayList<Nilai>>() {}.getType();
        numberModelList = gson.fromJson(json, type);

        if (numberModelList == null) {
            numberModelList = new ArrayList<>();
        }
    }

    public void removeData(int position){
        numberModelList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, numberModelList.size());
    }

    @Override
    public int getItemCount() {
        return numberModelList != null ? numberModelList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView angka1, angka2, operator, hasil;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            angka1 = itemView.findViewById(R.id.txtAngka1);
            angka2 = itemView.findViewById(R.id.txtAngka2);
            operator = itemView.findViewById(R.id.txtOperasi);
            hasil = itemView.findViewById(R.id.txtHasil);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}

package com.example.openweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.example.openweather.adapters.CityListAdapter;
import com.example.openweather.model.City;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Toolbar customToolbar;
    private RecyclerView recyclerView;
    private CityListAdapter adapter;
    List<City> cityFromJson;
    TextView emptySearchResult;
    private City city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        customToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(customToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        emptySearchResult = findViewById(R.id.empty_search_result);


        recyclerView = findViewById(R.id.city_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Type collectionType = new TypeToken<List<City>>(){}.getType();
        cityFromJson = (List<City>) new Gson().fromJson(parseJSONData(), collectionType);

        sortList();

        adapter.setCityList(cityFromJson);

        recyclerView.setAdapter(adapter);

        SearchView searchView = findViewById(R.id.search_view);
        SearchManager searchManager =(SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {

                    adapter.getFilter().filter(newText);
                    if(adapter.getItemCount()<=0){
                        recyclerView.setVisibility(View.GONE);
                        emptySearchResult.setVisibility(View.VISIBLE);
                    }else{
                        recyclerView.setVisibility(View.VISIBLE);
                        emptySearchResult
                                .setVisibility(View.GONE);
                    }

                return  false;
            }
        });

    }


    private void sortList(){
        Collections.sort(cityFromJson, new Comparator<City>() {
            @Override
            public int compare(City a, City b) {
                return a.getName().compareTo(b.getName());
            }
        });
        adapter = new CityListAdapter(cityFromJson, this);
        adapter.notifyDataSetChanged();
        adapter.setCityList(cityFromJson);
    }
    public String parseJSONData() {
        String JSONString = null;
        try {

            InputStream inputStream = getAssets().open("eg_city_list.json");

            int sizeOfJSONFile = inputStream.available();

            byte[] bytes = new byte[sizeOfJSONFile];

            inputStream.read(bytes);

            inputStream.close();

            JSONString = new String(bytes, StandardCharsets.UTF_8);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONString;
    }


}
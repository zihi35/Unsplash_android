package com.example.flowers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.flowers.api.ApiUtilities;
import com.example.flowers.model.SearchModel;
import com.example.flowers.model.imageModel;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Homectivity extends AppCompatActivity {
    private RecyclerView reciclerview;
    private ArrayList<imageModel> list;
    private GridLayoutManager manager;
    private imageAdapter adapter;
    private int page = 1;
    private ProgressDialog dialog;
    private int pagesSize= 30;
    private boolean isLoading;
    private boolean isLastPage;
    static Logger log = Logger.getLogger("MyLog");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homectivity);

        reciclerview = findViewById(R.id.recyclerview);
        list = new ArrayList<>();
        adapter = new imageAdapter(this, list);
        manager = new GridLayoutManager(this, 3);
        reciclerview.setLayoutManager(manager);
        reciclerview.setHasFixedSize(true);
        reciclerview.setAdapter(adapter);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();


        getData();

        reciclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItem = manager.getChildCount();
                int totalItem = manager.getItemCount();
                int firstVisibleItemPos = manager.findFirstCompletelyVisibleItemPosition();

                if(!isLoading && !isLastPage){
                    if((visibleItem+firstVisibleItemPos >= totalItem)
                    && firstVisibleItemPos >= 0 && totalItem >= pagesSize){
                        page ++;
                        getData();
                    }
                }
            }

        });
    }
    private void getData() {
        isLoading = true;
        ApiUtilities.getApiInterface().getImages(page, 30)
                .enqueue(new Callback<List<imageModel>>() {
                    @Override
                    public void onResponse(Call<List<imageModel>> call, Response<List<imageModel>> response) {
                        if (response.body() != null){
                            list.addAll(response.body());
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                        isLoading= false;
                        dialog.dismiss();

                        if (list.size() > 0){
                            isLastPage = list.size() < pagesSize;
                        }else isLastPage = true;
                    }

                    @Override
                    public void onFailure(Call<List<imageModel>> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(Homectivity.this, "Error:" +t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchview = (SearchView) search.getActionView();
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.show();
//                searchData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
}

//    private void searchData(String query) {
//        ApiUtilities.getApiInterface().seachImages(query)
//                .enqueue(new Callback<List<SearchModel>>() {
//                    @Override
//                    public void onResponse(Call<List<SearchModel>> call, Response<List<SearchModel>> response) {
//                        list.clear();
//                        list.addAll(response.body().getResults());
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<SearchModel>> call, Throwable t) {
//
//                    }
//                });
//
//    }

}


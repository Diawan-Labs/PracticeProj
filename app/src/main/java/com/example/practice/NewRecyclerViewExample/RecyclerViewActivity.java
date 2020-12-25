package com.example.practice.NewRecyclerViewExample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.practice.IntentExamples.MainActivity;
import com.example.practice.NewRecyclerViewExample.model.ListOfNumbers;
import com.example.practice.NewRecyclerViewExample.model.Number;
import com.example.practice.NewRecyclerViewExample.util.NumberToWordsConverter;
import com.example.practice.R;
import com.example.practice.databinding.ActivityRecyclerViewBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class RecyclerViewActivity extends AppCompatActivity {

    private static final String TAG = "RecyclerViewActivity";
    ActivityRecyclerViewBinding activityRecyclerViewBinding;
    MyCustomRecyclerViewAdapter myCustomRecyclerViewAdapter;
    List<Number> numberArrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_recycler_view);
        activityRecyclerViewBinding=ActivityRecyclerViewBinding.inflate(getLayoutInflater());
        setContentView(activityRecyclerViewBinding.getRoot());

        InputStream inputStream=getResources().openRawResource(R.raw.numbers);
        Scanner sc=new Scanner(inputStream);

        StringBuilder stringBuilder=new StringBuilder();
        while (sc.hasNext())
        {
           stringBuilder.append(sc.nextLine());
        }
        ListOfNumbers listOfNumbers = new Gson().fromJson(stringBuilder.toString(),ListOfNumbers.class);
        numberArrayList=listOfNumbers.getNumbers();

        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        activityRecyclerViewBinding.recycleViewNew.addItemDecoration(dividerItemDecoration);
        activityRecyclerViewBinding.swipeRefreshLayout.setOnRefreshListener(()->
        {
           int startIndex=numberArrayList.stream().max(Comparator.comparingInt(Number::getId)).get().getId()+1;
           //Log.d(TAG,"startIndex::"+startIndex);
           for(int i=startIndex;i<=startIndex+3;i++)
               numberArrayList.add(new Number(i, NumberToWordsConverter.convert(i)));
            myCustomRecyclerViewAdapter.notifyDataSetChanged();
            activityRecyclerViewBinding.swipeRefreshLayout.setRefreshing(false);
        });
        ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN|ItemTouchHelper.START|ItemTouchHelper.END,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition=viewHolder.getAdapterPosition();
                int toPosition=target.getAdapterPosition();
                Log.d(TAG,"Inside onMove::fromPosition="+fromPosition+"\ttoPosition="+toPosition);
                Collections.swap(numberArrayList,fromPosition,toPosition);
                recyclerView.getAdapter().notifyItemMoved(fromPosition,toPosition);
                return false;
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(RecyclerViewActivity.this, R.color.exo_gray_ripple))
                        .addActionIcon(R.drawable.exo_ic_chevron_left)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position=viewHolder.getAdapterPosition();
                Number number=numberArrayList.get(position);
                switch (direction)
                {
                    case ItemTouchHelper.LEFT:
                        numberArrayList.remove(position);
                        myCustomRecyclerViewAdapter.notifyItemRemoved(position);
                        View.OnClickListener onClickListener= v -> {
                            numberArrayList.add(position,number);
                            myCustomRecyclerViewAdapter.notifyItemInserted(position);
                        };
                        Snackbar.make(activityRecyclerViewBinding.recycleViewNew,"item Removed", Snackbar.LENGTH_LONG)
                                .setAction("undo",onClickListener)
                                .show();
                        break;
                }

            }
        };
        myCustomRecyclerViewAdapter=new MyCustomRecyclerViewAdapter(numberArrayList,this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(activityRecyclerViewBinding.recycleViewNew);
        activityRecyclerViewBinding.recycleViewNew.setAdapter(myCustomRecyclerViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem menuItem=menu.findItem(R.id.searchList);
        SearchView searchView= (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               myCustomRecyclerViewAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
import android.graphics.Canvas;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private MyListAdapter mAdapter;
    SwipeController swipeController = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        setMyListAdapter();
        setupRecyclerView();
    }

    private void setMyListAdapter() {
        List<Books> booksList = new ArrayList<>();
        try {
            InputStreamReader is = new InputStreamReader(getAssets().open("books.csv"));

            BufferedReader reader = new BufferedReader(is);
            reader.readLine();
            String line;
            String[] st;
            while ((line = reader.readLine()) != null) {
                st = line.split(",");
                Books book = new Books();
                book.setMaintitle(st[1]);
                book.setSubtitle(st[2]);
                booksList.add(book);
            }
        } catch (IOException e) {

        }

        mAdapter = new MyListAdapter(booksList);
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
//                // TODO Auto-generated method stub
//                if(position == 0) {
//                    //code specific to first list item
//                    Toast.makeText(getApplicationContext(),"Place Your First Option Code",Toast.LENGTH_SHORT).show();
//                }
//
//                else if(position == 1) {
//                    //code specific to 2nd list item
//                    Toast.makeText(getApplicationContext(),"Place Your Second Option Code",Toast.LENGTH_SHORT).show();
//                }
//
//                else if(position == 2) {
//
//                    Toast.makeText(getApplicationContext(),"Place Your Third Option Code",Toast.LENGTH_SHORT).show();
//                }
//                else if(position == 3) {
//
//                    Toast.makeText(getApplicationContext(),"Place Your Forth Option Code",Toast.LENGTH_SHORT).show();
//                }
//                else if(position == 4) {
//
//                    Toast.makeText(getApplicationContext(),"Place Your Fifth Option Code",Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
//        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);
        swipeController = new SwipeController(new SwipeControllerActions() {
//            @Override
//            public void onLeftClicked(int position){
//                mAdapter.booksList.remove(position);
//                mAdapter.notifyItemRemoved(position);
//                mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
//            }
            @Override
            public void onRightClicked(int position) {
                mAdapter.booksList.remove(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
            }
        });
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

}

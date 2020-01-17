package com.example.lbs;

import android.app.Activity;

//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//public class MyListAdapter extends ArrayAdapter<String> {
//
//    private final Activity context;
//    private final String[] maintitle;
//    private final String[] subtitle;
//    private final Integer[] imgid;
//
//    public MyListAdapter(Activity context, String[] maintitle,String[] subtitle, Integer[] imgid) {
//        super(context, R.layout.mylist, maintitle);
//         TODO Auto-generated constructor stub
//
//        this.context=context;
//        this.maintitle=maintitle;
//        this.subtitle=subtitle;
//        this.imgid=imgid;
//
//    }
//
//    public View getView(int position,View view,ViewGroup parent) {
//        LayoutInflater inflater=context.getLayoutInflater();
//        View rowView=inflater.inflate(R.layout.mylist, null,true);
//
//        TextView titleText = (TextView) rowView.findViewById(R.id.title);
//        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
//        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);
//
//        titleText.setText(maintitle[position]);
//        imageView.setImageResource(imgid[position]);
//        subtitleText.setText(subtitle[position]);
//
//        return rowView;
//
//    };
//}

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.BookViewHolder> {
    public List<Books> booksList;

    public class BookViewHolder extends RecyclerView.ViewHolder {
        private TextView maintitle, subtitle;


        public BookViewHolder(View view) {
            super(view);
            maintitle = (TextView) view.findViewById(R.id.title);
            subtitle = (TextView) view.findViewById(R.id.subtitle);
        }
    }

    public MyListAdapter(List<Books> booksList) {
        this.booksList = booksList;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mylist, parent, false);

        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Books book = booksList.get(position);
        holder.maintitle.setText(book.getMaintitle());
        holder.subtitle.setText(book.getSubtitle());
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }
}

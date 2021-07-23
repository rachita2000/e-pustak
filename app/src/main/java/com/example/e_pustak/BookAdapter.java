package com.example.e_pustak;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private Context mcontext;
   private ArrayList<Books> books=new ArrayList<>();
    private BooKInterfaceAdapater listener;
    public BookAdapter(Context mcontext ,BooKInterfaceAdapater listener) {
        this.listener=listener;
        this.mcontext = mcontext;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.activity_books,parent,false);
        BookViewHolder viewHolder=new BookViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(books.get(viewHolder.getAdapterPosition()));
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BookAdapter.BookViewHolder holder, int position) {
        Books book=books.get(position);
        holder.title.setText(book.getTitle());
        for(int i=0;i<book.getAuthors().size() ;i++)
        holder.authors.setText(book.getAuthors().get(i));
        holder.publisher.setText(book.getPublisher());
        holder.price.setText(book.getPrice());
        Glide.with(holder.itemView.getContext()).load(book.thumbnail).into(holder.bImage);

    }

    public void updateBook(ArrayList<Books> updated)
    {
        books.clear();
        books.addAll(updated);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return books == null ? 0 : books.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder{
        TextView title,authors,publisher,price;
        ImageView bImage;
        public BookViewHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.booktitle);
            authors=itemView.findViewById(R.id.bookauthor);
            publisher=itemView.findViewById(R.id.bookpublisher);
            price=itemView.findViewById(R.id.bookprice);
            bImage=itemView.findViewById(R.id.bookimage);
        }
    }
}
interface BooKInterfaceAdapater{
    void onItemClicked(Books book);
}

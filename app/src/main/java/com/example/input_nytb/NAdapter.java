package com.example.input_nytb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NAdapter extends RecyclerView.Adapter<NAdapter.ViewHolder> {

    Context context;
    ArrayList<nytb> list;

    ItemClick itemClick;

    public NAdapter(Context context, ArrayList<nytb> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NAdapter.ViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.bait.setText(list.get(position).getBait());
        holder.koor.setText(list.get(position).getKoor());
        holder.nada.setText(list.get(position).getNada());
        holder.itemView.setOnClickListener(view -> itemClick.OnClick(list.get(position)));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, bait, koor, nada;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.list_item_title);
            bait = itemView.findViewById(R.id.bait);
            koor = itemView.findViewById(R.id.kor);
            nada = itemView.findViewById(R.id.nada);

        }
    }

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public interface ItemClick{
        void OnClick(nytb nytb);
    }
}

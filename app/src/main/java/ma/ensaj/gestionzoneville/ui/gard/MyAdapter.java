package ma.ensaj.gestionzoneville.ui.gard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import ma.ensaj.gestionzoneville.R;

import java.util.List;

import ma.ensaj.gestionzoneville.beans.PharmacieGard;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<PharmacieGard> listItems;
    private Context context;
    private ProgressDialog dialog;


    public MyAdapter(List<PharmacieGard> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView id;
        public TextView uid;
        public TextView name;
        public TextView address;
        public TextView phone;
        public TextView genre;
        public ImageView imageView;
        public CardView card_view;
        public ViewHolder(View itemView ) {
            super(itemView);

            uid = (TextView) itemView.findViewById(R.id.textView5);
            name = (TextView) itemView.findViewById(R.id.textView3);
            address = (TextView) itemView.findViewById(R.id.textView6);
            phone = (TextView) itemView.findViewById(R.id.textView7);
            imageView=itemView.findViewById(R.id.imgph);


        }


    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item2, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final PharmacieGard listItem = listItems.get(position);

        holder.uid.setText("Date Debut :"+listItem.getDate_debut());
        holder.name.setText(listItem.getPharmacie().get(0).getName());
        holder.address.setText("Date Fin :"+listItem.getDate_fin());
        holder.phone.setText("Gard Type :"+listItem.getGard().get(0).getType());
        System.out.println(listItem.getPharmacie().get(0).getPhoto().split("://")[0]+"s://"+listItem.getPharmacie().get(0).getPhoto().split("://")[1]);
        Glide.with(context).load(listItem.getPharmacie().get(0).getPhoto().split("://")[0]+"s://"+listItem.getPharmacie().get(0).getPhoto().split("://")[1]).into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}
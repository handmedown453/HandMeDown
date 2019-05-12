package android.project.handmedown;

import android.content.Context;
import android.content.Intent;
import android.project.handmedown.Fooddata.Food_data;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RequestdataAdpter extends RecyclerView.Adapter<RequestdataAdpter.ViewHolder>  {

    List<Food_data> mdata;
    Context mcontext;


    public RequestdataAdpter(Context mcontext, List<Food_data> mdata) {


        this.mdata = mdata;
        this.mcontext = mcontext;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recyclerview, parent, false);
       ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestdataAdpter.ViewHolder holder, int position) {
        holder.textView1.setText(mdata.get(position).getTitle());
        holder.textView2.setText(mdata.get(position).getTime());
        holder.textView3.setText(mdata.get(position).getRemaingdays());
        holder.setimage(mdata.get(position).getFilepath());


    }

    @Override
    public int getItemCount() {
            return mdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView textView1, textView2, textView3;
        Button button1, button2;
        ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.request_image);
            textView1 = itemView.findViewById(R.id.Requestusername);
            textView2 = itemView.findViewById(R.id.requesteddistance);
            button1 = itemView.findViewById(R.id.request_accept);
            button2 = itemView.findViewById(R.id.request_Declined);

        }
        public void setimage(String filepath) {
            Picasso.get().load(filepath).fit().into(image);

        }
    }
}

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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class
FooddataAdapter extends RecyclerView.Adapter<FooddataAdapter.ViewHolder> {
    List<Food_data> mdata;
    Context mcontext;

    public FooddataAdapter(Context mcontext, List<Food_data> mdata) {


        this.mdata = mdata;
        this.mcontext = mcontext;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recyclerview, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.textView1.setText(mdata.get(position).getTitle());
        holder.textView2.setText(mdata.get(position).getTime());
        holder.textView3.setText(mdata.get(position).getRemaingdays());
        holder.setimage(mdata.get(position).getFilepath());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(mcontext,ThirdActivity.class);
                i.putExtra("id",mdata.get(position).getId());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mcontext.startActivity(i);

            }
        });



    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView textView1, textView2, textView3;
        ConstraintLayout constraintLayout;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            image = itemView.findViewById(R.id.Home_image);
            textView1 = itemView.findViewById(R.id.Home_title);
            textView2 = itemView.findViewById(R.id.home_best_days);
            textView3 = itemView.findViewById(R.id.home_pickuptime);
            constraintLayout = itemView.findViewById(R.id.Home_constrain);


        }

        public void setimage(String filepath) {
             Picasso.get().load(filepath).fit().into(image);

        }
    }



}

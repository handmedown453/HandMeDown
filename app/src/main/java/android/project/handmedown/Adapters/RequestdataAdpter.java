package android.project.handmedown.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.project.handmedown.Fooddata.ARActivity;
import android.project.handmedown.Fooddata.Food_data;
import android.project.handmedown.R;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RequestdataAdpter extends RecyclerView.Adapter<RequestdataAdpter.ViewHolder> {

    List<Food_data> mdata;
    Context mcontext;
    private String  user, id;


    public RequestdataAdpter(Context mcontext, List<Food_data> mdata) {


        this.mdata = mdata;
        this.mcontext = mcontext;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_recyclerview, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RequestdataAdpter.ViewHolder holder, final int position) {

        holder.setimage(mdata.get(position).getFilepath());
        id = mdata.get(position).getId();
        user = mdata.get(position).getReqstuser();


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mcontext, ARActivity.class);
                i.putExtra("id", mdata.get(position).getId());
                i.putExtra("user", mdata.get(position).getReqstuser());

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView textView1, textView2, textView3;
        Button button1, button2;

        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.request_image);

            linearLayout = itemView.findViewById(R.id.requestid);


        }

        public void setimage(String filepath) {
            Picasso.get().load(filepath).fit().into(image);

        }


    }
}

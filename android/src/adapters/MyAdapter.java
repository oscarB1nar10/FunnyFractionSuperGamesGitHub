package adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.funnyfractions.game.R;

import java.util.ArrayList;

import Util.SumaTutorialInformation;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    //vars
    private ArrayList<SumaTutorialInformation> sumaTutorialInformationsList;
    private Context context;
    public SharedPreferences sharedPreferences;
    //widget
    public MyAdapter(ArrayList<SumaTutorialInformation> sumaTutorialInformationsList, Context context){
        this.sumaTutorialInformationsList = sumaTutorialInformationsList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        sharedPreferences = context.getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_to_tutorials,parent,false);
        return new MyViewHolder(view);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (position == 0) {
            if(sharedPreferences.getString("idioma","Spanish").equals("Spanish")){
                holder.mRelativeLayout.setBackgroundResource(R.drawable.tutorials_cover_page);
            }
            else {
                holder.mRelativeLayout.setBackgroundResource(R.drawable.tutorials_cover_page_english);
            }
            holder.title.setText("");
            holder.information.setText("");
            holder.ImageView.setVisibility(View.INVISIBLE);
            holder.optionalImg.setVisibility(View.INVISIBLE);

        } else {
            holder.mRelativeLayout.setBackgroundResource(R.drawable.sheet);
            holder.title.setText(sumaTutorialInformationsList.get(position).getTitle());
            holder.information.setText(sumaTutorialInformationsList.get(position).getInformation());
            holder.ImageView.setVisibility(View.VISIBLE);
            holder.optionalImg.setVisibility(View.VISIBLE);
            holder.ImageView.setImageResource(sumaTutorialInformationsList.get(position).getMainImage());
            holder.optionalImg.setImageResource(sumaTutorialInformationsList.get(position).getOptionalImg());
        }
    }

    @Override
    public int getItemCount() {
        return (sumaTutorialInformationsList.size());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, information;
        ImageView ImageView;
        ImageView optionalImg;
        RelativeLayout mRelativeLayout;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.txv_title);
            information = view.findViewById(R.id.txv_information);
            ImageView = view.findViewById(R.id.img_example1);
            mRelativeLayout = view.findViewById(R.id.cover_page);
            optionalImg = view.findViewById(R.id.optionalImg);
        }
    }
}

package adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.funnyfractions.game.R;

import java.util.ArrayList;

import Util.SumaTutorialInformation;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<SumaTutorialInformation> sumaTutorialInformationsList;

    public MyAdapter(ArrayList<SumaTutorialInformation> sumaTutorialInformationsList){
        this.sumaTutorialInformationsList = sumaTutorialInformationsList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_to_tutorials,parent,false);

        return new MyViewHolder(view);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.title.setText(sumaTutorialInformationsList.get(position).getTitle());
            holder.information.setText(sumaTutorialInformationsList.get(position).getInformation());
            holder.circleImageView.setImageResource(sumaTutorialInformationsList.get(position).getMainImage());
    }

    @Override
    public int getItemCount() {
        return (sumaTutorialInformationsList.size());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, information;
        CircleImageView circleImageView;
        ImageView optionalImg1, optionalImg2, optionalImg3;

        public MyViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.txv_title);
            information = view.findViewById(R.id.txv_information);
            circleImageView = view.findViewById(R.id.img_example1);
            optionalImg1 = view.findViewById(R.id.img_optional1);
            optionalImg2 = view.findViewById(R.id.img_optional2);
            optionalImg3 = view.findViewById(R.id.img_optional3);

        }
    }
}

package shaolizhi.leanclouddemo.ui.main;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVObject;
import com.bumptech.glide.Glide;

import java.util.List;

import shaolizhi.leanclouddemo.R;

/**
 * 由邵励治于2018/2/4创造.
 */

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {

    private LayoutInflater layoutInflater;
    private Activity activity;
    private List<AVObject> listData;

    MainRecyclerAdapter(Context context, List<AVObject> listData) {
        layoutInflater = LayoutInflater.from(context);
        activity = (Activity) context;
        this.listData = listData;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(layoutInflater.inflate(R.layout.item_list_main, parent, false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        holder.mTitle.setText((CharSequence) listData.get(position).get("title"));
        holder.mPrice.setText(listData.get(position).get("price") == null ? "￥" : "￥ " + listData.get(position).get("price"));
        holder.mName.setText(listData.get(position).getAVUser("owner") == null ? "" : listData.get(position).getAVUser("owner").getUsername());
        Glide.with(activity).load(listData.get(position).getAVFile("image") == null ? "www" : listData.get(position).getAVFile("image").getUrl()).into(holder.mPicture);
        holder.mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, "FUCK", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mPrice;
        private TextView mTitle;
        private CardView mItem;
        private ImageView mPicture;

        MainViewHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.name_item_main);
            mTitle = itemView.findViewById(R.id.title_item_main);
            mPrice = itemView.findViewById(R.id.price_item_main);
            mPicture = itemView.findViewById(R.id.picture_item_main);
            mItem = itemView.findViewById(R.id.item_main);
        }
    }
}

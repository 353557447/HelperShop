package com.shuiwangzhijia.shuidian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuiwangzhijia.shuidian.R;
import com.shuiwangzhijia.shuidian.bean.CityBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 选择城市
 * created by wangsuli on 2018/9/27.
 */
public class SelectCityAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private List<CityBean> data;
    private OnRecyclerViewItemClickListener itemClickListener;

    public SelectCityAdapter(Context context) {
        mContext = context;
        data = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View header = LayoutInflater.from(mContext).inflate(R.layout.item_city_header, parent, false);
            return new PinnedHolder(header);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_city, parent, false);
            return new CityHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CityBean item = getItem(position);
        int type = getItemViewType(position);
        if (type == 1) {
            if (holder instanceof PinnedHolder) {
                ((PinnedHolder) holder).headTitle.setText(item.getLetter());
            }
        } else {
            if (holder instanceof CityHolder) {
                ((CityHolder) holder).itemView.setTag(position);
                ((CityHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemClickListener.onItemClick((int) view.getTag());
                    }
                });
                ((CityHolder) holder).city.setText(item.getCity());
            }
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        itemClickListener = listener;
    }


    public interface OnRecyclerViewItemClickListener {
        void onItemClick(int position);
    }

    /**
     * 1表示字母 0表示城市
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        CityBean cityBean = data.get(position);
        return cityBean.getType();
    }

    public CityBean getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<CityBean> list) {
        data = list;
        notifyDataSetChanged();
    }

    public int getLetterPosition(String letter) {
        for (int i = 0; i < getItemCount(); i++) {
            CityBean item = getItem(i);
            if (item.getType() == 1 && item.getLetter().equals(letter)) {
                return i;
            }
        }
        return -1;
    }

    class CityHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.city)
        TextView city;

        public CityHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    class PinnedHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.headTitle)
        TextView headTitle;

        public PinnedHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

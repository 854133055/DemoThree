package com.mlmOK.demoThree.bottomWindow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.mlmOK.hotWheel.utils.ArrayUtils;
import com.mlmOK.demothree.R;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mml
 * @since 2018/7/8.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private Context mContext;
    private List<String> lists;

    private List<Integer> checkedItemArray;
    private int lastSelectedPosition = -1;
    private boolean isSingleSelected = true;
    private OnItemClickListener mOnItemClickListener = null;

    public RecycleAdapter(Context mContext, List<String> lists, boolean isSingleSelect) {
        this.mContext = mContext;
        this.isSingleSelected = isSingleSelect;
        this.checkedItemArray = new ArrayList<>();
        this.lists = ArrayUtils.asList("1111111111", "2222222222", "1111111111", "2222222222", "3333333333", "4444444444");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_bottom_sheet_item, parent, false);
        return new BaseViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (isSingleSelected) {
            ((BaseViewHolder) holder).checkBox.setVisibility(View.GONE);
            ((BaseViewHolder) holder).radioButton.setVisibility(View.VISIBLE);
            ((BaseViewHolder) holder).radioButton.setClickable(false);
            ((BaseViewHolder) holder).radioButton.setChecked(lastSelectedPosition == position);
            ((BaseViewHolder)holder).parentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = position;
                    notifyDataSetChanged();
                    ((BaseViewHolder) holder).radioButton.setChecked(true);
                }
            });
        } else {
            ((BaseViewHolder) holder).radioButton.setVisibility(View.GONE);
            ((BaseViewHolder) holder).checkBox.setVisibility(View.VISIBLE);
            ((BaseViewHolder) holder).checkBox.setClickable(false);
            ((BaseViewHolder)holder).parentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked = ((BaseViewHolder) holder).checkBox.isChecked();
                    if (!isChecked) {
                        ((BaseViewHolder) holder).checkBox.setChecked(true);
                        checkedItemArray.add(position);
                    }
                    if (isChecked) {
                        ((BaseViewHolder) holder).checkBox.setChecked(false);
                        checkedItemArray.remove(Integer.valueOf(position));
                    }

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


    public interface OnItemClickListener {
        //点击sendMsg
        void onRecycleViewItemClick();
    }

    public void setOnItemClickListener(RecycleAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {

        private View parentView;
        private CheckBox checkBox;
        private RadioButton radioButton;

        public BaseViewHolder(View itemView) {
            super(itemView);
            parentView = itemView;
            checkBox = itemView.findViewById(R.id.pub_ochatsdk_bottom_window_item_checkbox);
            radioButton = itemView.findViewById(R.id.pub_ochatsdk_bottom_window_item_radiobutton);
        }
    }

    public List<Integer> getCheckedItemArray() {
        return checkedItemArray;
    }
}

package com.example.mainproj.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mainproj.R;
import com.example.mainproj.vo.CustomMemberVO;
import com.google.android.material.transition.platform.Hold;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private Activity activity;
    private List<CustomMemberVO> memberVOList;
    private CustomHolder holder;

    public class CustomHolder{
        private ImageView iv_custom_profile;
        private TextView tv_custom_item_name;
        private TextView tv_custom_item_age;
    }

    public CustomAdapter(Activity activity, List<CustomMemberVO> memberVOList){
        this.activity = activity;
        this.memberVOList = memberVOList;
    }

    @Override
    public int getCount() {
        return memberVOList.size();
    }

    @Override
    public Object getItem(int i) {
        return memberVOList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(activity).inflate(R.layout.item_custom_list,viewGroup,false);

            holder = new CustomHolder();
            holder.iv_custom_profile = view.findViewById(R.id.iv_custom_profile);
            holder.tv_custom_item_name = view.findViewById(R.id.tv_custom_item_name);
            holder.tv_custom_item_age = view.findViewById(R.id.tv_custom_item_age);
            view.setTag(holder);
        }else{
            holder = (CustomHolder) view.getTag();
        }

        holder.tv_custom_item_name.setText(memberVOList.get(i).getName());
        holder.tv_custom_item_age.setText(memberVOList.get(i).getAge());
        if(i%2 == 1){
            holder.iv_custom_profile.setImageResource(R.drawable.icon_woman_profile);
        }
        return view;
    }

    public void addItem(CustomMemberVO customMemberVO){
        memberVOList.add(customMemberVO);
        notifyDataSetChanged();
    }
}

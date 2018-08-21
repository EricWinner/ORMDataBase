package com.example.edwardadmin.ormdatabase.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.edwardadmin.ormdatabase.R;
import com.example.edwardadmin.ormdatabase.cache.CacheUtil;
import com.example.edwardadmin.ormdatabase.entity.PersonInfo;
import com.example.edwardadmin.ormdatabase.entity.PersonToken;
import com.example.edwardadmin.ormdatabase.util.TimeConvertUtils;
import com.j256.ormlite.dao.ForeignCollection;

import java.util.ArrayList;

public class PersonAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<PersonInfo> personInfoArrayList;

    public PersonAdapter(Context context, ArrayList<PersonInfo> personInfoArrayList) {
        this.mContext = context;
        this.personInfoArrayList = personInfoArrayList;
    }

    @Override
    public int getCount() {
        return personInfoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return personInfoArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.person_item_layout, null, true);
            viewHolder.personName = convertView.findViewById(R.id.person_name);
            viewHolder.personSex = convertView.findViewById(R.id.person_sex);
            viewHolder.personAge = convertView.findViewById(R.id.person_age);
            viewHolder.personHeight = convertView.findViewById(R.id.person_height);
            viewHolder.personNative = convertView.findViewById(R.id.person_native);
            viewHolder.personNumber = convertView.findViewById(R.id.person_number);
            viewHolder.personTime = convertView.findViewById(R.id.person_time);
            viewHolder.personView = convertView.findViewById(R.id.person_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PersonInfo personInfo = personInfoArrayList.get(position);
        viewHolder.personName.setText(personInfo.getPersonName());
        viewHolder.personSex.setText(personInfo.getPersonSex());
        viewHolder.personAge.setText(personInfo.getPersonAge());
        viewHolder.personHeight.setText(personInfo.getPersonHeight());
        viewHolder.personNative.setText(personInfo.getPersonNative());
        viewHolder.personNumber.setText(personInfo.getPersonNumber());

        ForeignCollection<PersonToken> personTokens = personInfo.personTokens;
        if (personTokens != null) {
            StringBuilder builder = new StringBuilder();
            for (PersonToken personToken : personTokens) {
                long time = personToken.getDataToken();
                String token = TimeConvertUtils.formatDate4(time);
                builder.append(token + ",");
            }
            viewHolder.personTime.setText(builder.toString());
        }

        //add new person view
        String personImage = personInfo.getPersonImage();
        String personNumber = personInfo.getPersonNumber();
        if (personImage != null) {
            CacheUtil.getInstance().displayImage(Uri.parse(personImage), viewHolder.personView, personNumber);
        } else {
            viewHolder.personView.setImageResource(R.drawable.head);
        }
        return convertView;
    }

    class ViewHolder {
        TextView personName;
        TextView personSex;
        TextView personAge;
        TextView personHeight;
        TextView personNative;
        TextView personNumber;
        TextView personTime;
        ImageView personView;
    }
}

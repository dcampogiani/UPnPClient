package com.danielecampogiani.upnpclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.koushikdutta.ion.Ion;

import java.util.List;

/**
 * Created by danielecampogiani on 04/06/14.
 */
public class PictureAdapter extends ArrayAdapter<Picture> {

    private int resource;


    public PictureAdapter(Context context, int resource, List<Picture> objects) {
        super(context, resource, objects);
        this.resource=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout picturesView;

        Picture current = getItem(position);

        if (convertView==null){
            picturesView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li = (LayoutInflater)getContext().getSystemService(inflater);
            li.inflate(resource,picturesView,true);

        }

        else {
            picturesView = (LinearLayout) convertView;
        }

        ImageView imageView = (ImageView)picturesView.findViewById(R.id.imageView);

        Ion.with(imageView).load(current.getUrl());

        return picturesView;
    }
}

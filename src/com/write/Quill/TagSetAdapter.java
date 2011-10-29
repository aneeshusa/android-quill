package com.write.Quill;

import com.write.Quill.TagManager.Tag;
import com.write.Quill.TagManager.TagSet;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TagSetAdapter extends ArrayAdapter {
	
	private static final String TAG = "TagSetAdapter";
	
	private TagSet tags;
	private Context context;
	private static final int HIGHLIGHT = Color.YELLOW;

	public TagSetAdapter(Context mContext, TagSet active_tags) {
		super(mContext, R.layout.tag_item, 
					active_tags.allTags());
		tags = active_tags;
		context = mContext;
		
	}

	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv;
        if (convertView == null) {
            tv = (TextView) LayoutInflater.from(context).inflate(
                    R.layout.tag_item, parent, false);
        } else {
            tv = (TextView) convertView;
        }
        Tag t = tags.allTags().get(position);
        tv.setText(t.name);
        if (tags.contains(t)) {
        	tv.setShadowLayer(20, 0, 0, HIGHLIGHT);
        	tv.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
        	tv.setShadowLayer(0, 0, 0, HIGHLIGHT);
        	tv.setTypeface(Typeface.DEFAULT);
	
        }
        return tv;
    }
	
}


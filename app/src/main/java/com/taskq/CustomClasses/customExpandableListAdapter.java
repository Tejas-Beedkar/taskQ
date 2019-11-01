package com.taskq.CustomClasses;

import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.taskq.DataBase.dBaseArchitecture_What;
import com.taskq.R;

public class customExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;
    private HashMap<String, List<String>> expandableListIDs;
    private HashMap<String, List<String>> expandableListTagCnt;
    private int                           maxProgressBar;

    public customExpandableListAdapter(Context context,
                                       List<String> expandableListTitle,
                                       HashMap<String, List<String>> expandableListDetail,
                                       HashMap<String, List<String>> expandableListIDs,
                                       HashMap<String, List<String>> expandableListTagCnt,
                                       int maxProgressBar   ) {
        this.context                = context;
        this.expandableListTitle    = expandableListTitle;
        this.expandableListDetail   = expandableListDetail;
        this.expandableListIDs      = expandableListIDs;
        this.expandableListTagCnt   = expandableListTagCnt;
        this.maxProgressBar         = maxProgressBar;

    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    public Object getChild_SQL_ID(int listPosition, int expandedListPosition) {
        return this.expandableListIDs.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        final String expandedListID   = (String) getChild_SQL_ID(listPosition, expandedListPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listview_countentries_sub, null);
        }

        //Populate the task description
        TextView expandedListTextView = (TextView) convertView .findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);

        //Populate the task description ID
        TextView expandedListIDView = (TextView) convertView .findViewById(R.id.expandedListItemID);
        expandedListIDView.setText(expandedListID);



        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        String listTitle = (String) getGroup(listPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listview_countentries, null);
        }

        //Set the Title
        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.search_category);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);

        //Set the Title progress Bar
        ProgressBar progress = (ProgressBar) convertView.findViewById(R.id.search_progressBar);
        progress.setMax(maxProgressBar);
        List<String> strProgress = this.expandableListTagCnt.get(listTitle);
        int intProgress = Integer.parseInt(strProgress.get(0));
        progress.setProgress(intProgress);
        progress.setScaleY(3f);

        //Set the count
        TextView listTitleCount = (TextView) convertView.findViewById(R.id.search_count);
        listTitleCount.setText(strProgress.get(0));

        //Set color based on name. Overdue is orange and Today is Green
        //ToDo - this needs to access the string resource file
        if(listTitle.equals("Overdue")){
            //ToDo - this needs to access the colorOverdue resource
            //convertView.setBackgroundColor(Color.parseColor("#FFE6A191"));
            listTitleTextView.setTextColor(Color.parseColor("#FF5722"));
        }
        //ToDo - this needs to access the string resource file
        if(listTitle.equals("Due Today")){
            //ToDo - this needs to access the colorToday resource
            //convertView.setBackgroundColor(Color.parseColor("#7627E632"));
            listTitleTextView.setTextColor(Color.parseColor("#16B91E"));
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}


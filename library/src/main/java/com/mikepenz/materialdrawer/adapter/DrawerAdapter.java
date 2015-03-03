package com.mikepenz.materialdrawer.adapter;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.materialdrawer.model.ExpListDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DrawerAdapter extends BaseDrawerAdapter {
    private ArrayList<IDrawerItem> mDrawerItems;
    private LayoutInflater mInflater;
    private List<String> mTypeMapper;
    public DrawerAdapter(Activity activity) {
        this.mInflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        update(null);
    }
    public DrawerAdapter(Activity activity, ArrayList<IDrawerItem> drawerItems) {
        this.mInflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        update(drawerItems);
    }
    public void update(ArrayList<IDrawerItem> drawerItems) {
        this.mDrawerItems = drawerItems;
        if (this.mDrawerItems == null) {
            mDrawerItems = new ArrayList<>();
        }
        mapTypes();
    }
    public void add(IDrawerItem... drawerItems) {
        if (this.mDrawerItems == null) {
            mDrawerItems = new ArrayList<>();
        }
        if (drawerItems != null) {
            Collections.addAll(this.mDrawerItems, drawerItems);
        }
        mapTypes();
    }
    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    public boolean isEnabled(int position) {
        if (mDrawerItems != null && mDrawerItems.size() > position) {
            return mDrawerItems.get(position).isEnabled();
        } else {
            return false;
        }
    }
    @Override
    public int getGroupCount() {
        if (mDrawerItems == null) {
            return 0;
        }
        return mDrawerItems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(getDrawerItems().get(groupPosition) instanceof ExpListDrawerItem) {
            return ((ExpListDrawerItem)getDrawerItems().get(groupPosition))
                    .getChildCount();
        }
        return 0;
    }

    @Override
    public Object getGroup(int position) {
        if (mDrawerItems != null && mDrawerItems.size() > position) {
            return mDrawerItems.get(position);
        } else {
            return null;
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if(getDrawerItems().get(groupPosition) instanceof ExpListDrawerItem) {
            return ((ExpListDrawerItem)getDrawerItems().get(groupPosition))
                    .getChildAt(childPosition);
        }
        return null;
    }

    @Override
    public long getGroupId(int position) {
        return position;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        if(getDrawerItems().get(groupPosition) instanceof ExpListDrawerItem) {
            return groupPosition*100+childPosition;
        }
        return 0;
    }

    private Drawable getRotateDrawable(final Drawable d, final float angle) {
        final Drawable[] arD = { d };
        return new LayerDrawable(arD) {
            @Override
            public void draw(final Canvas canvas) {
                canvas.save();
                canvas.rotate(angle, d.getBounds().width() / 2, d.getBounds().height() / 2);
                super.draw(canvas);
                canvas.restore();
            }
        };
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public ArrayList<IDrawerItem> getDrawerItems() {
        return mDrawerItems;
    }
    @Override
    public void setDrawerItems(ArrayList<IDrawerItem> drawerItems) {
        this.mDrawerItems = drawerItems;
    }
    @Override
    public List<String> getTypeMapper() {
        return mTypeMapper;
    }
    @Override
    public void setTypeMapper(List<String> typeMapper) {
        this.mTypeMapper = typeMapper;
    }
    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
        IDrawerItem item = (IDrawerItem) getGroup(groupPosition);
        if(item instanceof ExpListDrawerItem) {
            ExpListDrawerItem expItem = (ExpListDrawerItem) item;
            if(expItem.getIcon() != null) {
                Drawable rotated = getRotateDrawable(expItem.getIcon(), 180.0f);
                expItem.setIcon(rotated);
            }
        }
    }
    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
        IDrawerItem item = (IDrawerItem) getGroup(groupPosition);
        if(item instanceof ExpListDrawerItem) {
            ExpListDrawerItem expItem = (ExpListDrawerItem) item;
            if(expItem.getIcon() != null) {
                Drawable rotated = getRotateDrawable(expItem.getIcon(), 180.0f);
                expItem.setIcon(rotated);
            }
        }

    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        IDrawerItem item = (IDrawerItem) getGroup(groupPosition);
        return item.convertView(mInflater, convertView, parent);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
         IDrawerItem item = (IDrawerItem) ((ExpListDrawerItem) getGroup(groupPosition)).getChildAt(childPosition);
         return item.convertView(mInflater, convertView, parent);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
package com.mlmOK.hotWheel.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.mlmOK.hotWheel.utils.ArrayUtils;
import com.mlmOK.hotWheel.utils.CompatUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * @author molei.li
 * @since 2018/3/1.
 */

public abstract class HArrayAdapter<T> extends BaseAdapter implements Filterable {
    protected List<T> mObjects;
    protected final Object mLock = new Object();
    private boolean mNotifyOnChange = true;
    protected Context mContext;
    protected ArrayList<T> mOriginalValues;
    private HArrayAdapter<T>.ArrayFilter mFilter;
    protected LayoutInflater mInflater;

    public HArrayAdapter(Context context) {
        this.init(context, new ArrayList());
    }

    public HArrayAdapter(Context context, T[] objects) {
        this.init(context, ArrayUtils.asList(objects));
    }

    public HArrayAdapter(Context context, T[] objects, boolean readOnly) {
        this.init(context, (List)(readOnly?ArrayUtils.asReadOnlyList(objects):ArrayUtils.asList(objects)));
    }

    public HArrayAdapter(Context context, List<T> objects) {
        this.init(context, objects);
    }

    protected View setIdToTag(View view, int id) {
        View v = null;
        if(CompatUtil.hasHoneycomb()) {
            view.setTag(id, v = view.findViewById(id));
        } else {
            Object tag = view.getTag();
            if(!SparseArray.class.isInstance(tag)) {
                tag = new SparseArray();
                view.setTag(tag);
            }

            ((SparseArray)tag).put(id, v = view.findViewById(id));
            view.setTag(tag);
        }

        return v;
    }

    protected View getViewFromTag(View view, int id) {
        if(CompatUtil.hasHoneycomb()) {
            return (View)view.getTag(id);
        } else {
            Object tag = view.getTag();
            return !SparseArray.class.isInstance(tag)?null:(View)((SparseArray)tag).get(id);
        }
    }

    public void add(T object) {
        if(this.mOriginalValues != null) {
            Object var2 = this.mLock;
            synchronized(this.mLock) {
                this.mOriginalValues.add(object);
                if(this.mNotifyOnChange) {
                    this.notifyDataSetChanged();
                }
            }
        } else {
            this.mObjects.add(object);
            if(this.mNotifyOnChange) {
                this.notifyDataSetChanged();
            }
        }

    }

    public void addAll(int index, List<T> notes) {
        if(notes != null) {
            if(this.mOriginalValues != null) {
                Object var3 = this.mLock;
                synchronized(this.mLock) {
                    this.mOriginalValues.addAll(index, notes);
                    if(this.mNotifyOnChange) {
                        this.notifyDataSetChanged();
                    }
                }
            } else {
                this.mObjects.addAll(index, notes);
                if(this.mNotifyOnChange) {
                    this.notifyDataSetChanged();
                }
            }

        }
    }

    public void addAll(int index, T[] notes) {
        this.addAll(index, ArrayUtils.asReadOnlyList(notes));
    }

    public void addAll(List<T> notes) {
        if(notes != null) {
            if(this.mOriginalValues != null) {
                Object var2 = this.mLock;
                synchronized(this.mLock) {
                    this.mOriginalValues.addAll(notes);
                    if(this.mNotifyOnChange) {
                        this.notifyDataSetChanged();
                    }
                }
            } else {
                this.mObjects.addAll(notes);
                if(this.mNotifyOnChange) {
                    this.notifyDataSetChanged();
                }
            }

        }
    }

    public void addAll(T[] notes) {
        this.addAll(ArrayUtils.asReadOnlyList(notes));
    }

    public void insert(T object, int index) {
        if(this.mOriginalValues != null) {
            Object var3 = this.mLock;
            synchronized(this.mLock) {
                this.mOriginalValues.add(index, object);
                if(this.mNotifyOnChange) {
                    this.notifyDataSetChanged();
                }
            }
        } else {
            this.mObjects.add(index, object);
            if(this.mNotifyOnChange) {
                this.notifyDataSetChanged();
            }
        }

    }

    public void remove(T object) {
        if(this.mOriginalValues != null) {
            Object var2 = this.mLock;
            synchronized(this.mLock) {
                this.mOriginalValues.remove(object);
            }
        } else {
            this.mObjects.remove(object);
        }

        if(this.mNotifyOnChange) {
            this.notifyDataSetChanged();
        }

    }

    public void clear() {
        if(this.mOriginalValues != null) {
            Object var1 = this.mLock;
            synchronized(this.mLock) {
                this.mOriginalValues.clear();
            }
        } else {
            this.mObjects.clear();
        }

        if(this.mNotifyOnChange) {
            this.notifyDataSetChanged();
        }

    }

    public void sort(Comparator<? super T> comparator) {
        Collections.sort(this.mObjects, comparator);
        if(this.mNotifyOnChange) {
            this.notifyDataSetChanged();
        }

    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        this.mNotifyOnChange = true;
    }

    public void setNotifyOnChange(boolean notifyOnChange) {
        this.mNotifyOnChange = notifyOnChange;
    }

    private void init(Context context, List<T> objects) {
        this.mContext = context;
        this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mObjects = objects;
        if(this.mObjects == null) {
            throw new NullPointerException("list must not be null");
        }
    }

    public View inflate(int resouse, ViewGroup root) {
        return this.mInflater.inflate(resouse, root, false);
    }

    public Context getContext() {
        return this.mContext;
    }

    public int getCount() {
        return this.mObjects.size();
    }

    public T getItem(int position) {
        return this.mObjects.get(position);
    }

    public int getPosition(T item) {
        return this.mObjects.indexOf(item);
    }

    public long getItemId(int position) {
        return (long)position;
    }

    public Filter getFilter() {
        if(this.mFilter == null) {
            this.mFilter = new HArrayAdapter.ArrayFilter();
        }

        return this.mFilter;
    }

    public int getItemViewType(int position) {
        return 0;
    }

    public int getViewTypeCount() {
        return 1;
    }

    private class ArrayFilter extends Filter {
        private ArrayFilter() {
        }

        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            Object var3;
            if(HArrayAdapter.this.mOriginalValues == null) {
                var3 = HArrayAdapter.this.mLock;
                synchronized(HArrayAdapter.this.mLock) {
                    HArrayAdapter.this.mOriginalValues = new ArrayList(HArrayAdapter.this.mObjects);
                }
            }

            ArrayList values;
            if(prefix != null && prefix.length() != 0) {
                String prefixString = prefix.toString().toLowerCase(Locale.US);
                values = HArrayAdapter.this.mOriginalValues;
                int count = values.size();
                ArrayList<T> newValues = new ArrayList(count);

                for(int i = 0; i < count; ++i) {
                    T value = (T) values.get(i);
                    String valueText = value.toString().toLowerCase(Locale.US);
                    if(valueText.startsWith(prefixString)) {
                        newValues.add(value);
                    } else {
                        String[] words = valueText.split(" ");
                        int wordCount = words.length;

                        for(int k = 0; k < wordCount; ++k) {
                            if(words[k].startsWith(prefixString)) {
                                newValues.add(value);
                                break;
                            }
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            } else {
                var3 = HArrayAdapter.this.mLock;
                synchronized(HArrayAdapter.this.mLock) {
                    values = new ArrayList(HArrayAdapter.this.mOriginalValues);
                    results.values = values;
                    results.count = values.size();
                }
            }

            return results;
        }

        protected void publishResults(CharSequence constraint, FilterResults results) {
            HArrayAdapter.this.mObjects = (List)results.values;
            if(results.count > 0) {
                HArrayAdapter.this.notifyDataSetChanged();
            } else {
                HArrayAdapter.this.notifyDataSetInvalidated();
            }

        }
    }
}

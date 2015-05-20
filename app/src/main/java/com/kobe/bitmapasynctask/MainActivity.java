package com.kobe.bitmapasynctask;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kobe.library.AsyncDrawable;
import com.kobe.library.BitmapAsyncTask;


public class MainActivity extends Activity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter(this);
        adapter.addAll(ImageData.imageData);
        listView.setAdapter(adapter);
    }


    class MyAdapter extends ArrayAdapter<String> {

        public MyAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder;
            if (convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.layout_list_item, parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) view.findViewById(R.id.image);
                holder.text = (TextView) view.findViewById(R.id.text);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            String url = getItem(position);
            boolean isNeedCreateTask = true;
            Drawable imageDrawable = holder.image.getDrawable();
            if (imageDrawable != null) {
                /**
                 * 若drawable为AsyncDrawable，那么说明task还未执行完成，因为如果执行完成，drawable就是BitmapDrawable类型
                 * 若task是正确的task并且未完成，那么这次不创建异步任务，就让上一次的任务继续执行
                 * 若task是不正确的，那么取消掉这个task，重新创建task去获取图片
                 */
                if (imageDrawable instanceof AsyncDrawable) {
                    AsyncDrawable drawable = (AsyncDrawable) holder.image.getDrawable();
                    BitmapAsyncTask task = drawable.getTask();
                    if (task != null) {
                        if (!task.getData().equals(url))
                            task.cancel(true);
                        else
                            isNeedCreateTask = false;
                    }
                }
            }
            if(isNeedCreateTask) {
                BitmapAsyncTask task = new BitmapAsyncTask(holder.image, url);
                AsyncDrawable drawable = new AsyncDrawable(task);
                holder.image.setImageDrawable(drawable);
                task.execute();
            }

            return view;
        }
    }

    class ViewHolder {
        ImageView image;
        TextView text;
    }

}

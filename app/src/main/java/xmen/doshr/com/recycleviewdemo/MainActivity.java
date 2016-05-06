package xmen.doshr.com.recycleviewdemo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import xmen.doshr.com.recycleviewdemo.adapter.RecyclerAdapter;

public class MainActivity extends Activity
{
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter; //适配器
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initCrotrol();
    }

    //初始化
    @TargetApi(Build.VERSION_CODES.M)
    private void initCrotrol()
    {
        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);

        //管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        //recyclerView.setHasFixedSize(true);
        adapter = new RecyclerAdapter(getDataSource(), this);
        recyclerView.setAdapter(adapter);

        list.add(3,"0000000");
        adapter.notifyItemInserted(3);
        //adapter.notifyItemChanged(3);

        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                Toast.makeText(MainActivity.this, list.get(position), Toast.LENGTH_SHORT).show();
            }
        });


        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP, ItemTouchHelper.RIGHT)
        {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
            {
                int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
                int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
                if (fromPosition < toPosition)
                {
                    //分别把中间所有的item的位置重新交换
                    for (int i = fromPosition; i < toPosition; i++)
                    {
                        Collections.swap(list, i, i + 1);
                    }
                } else
                {
                    for (int i = fromPosition; i > toPosition; i--)
                    {
                        Collections.swap(list, i, i - 1);
                    }
                }
                adapter.notifyItemMoved(fromPosition, toPosition);
                //返回true表示执行拖动
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
            {
                int position = viewHolder.getAdapterPosition();
                list.remove(position);
                adapter.notifyItemRemoved(position);
            }

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive)
            {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE)
                {
                    //滑动时改变Item的透明度
                    final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
                    viewHolder.itemView.setAlpha(alpha);
                    viewHolder.itemView.setTranslationX(dX);
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    //获取数据源
    private List<String> getDataSource()
    {
        for (int i = 0; i < 35; i++)
        {
            list.add("RecyclerView的item = " + i);
        }
        return list;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}

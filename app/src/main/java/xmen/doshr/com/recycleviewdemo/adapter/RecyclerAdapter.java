package xmen.doshr.com.recycleviewdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import xmen.doshr.com.recycleviewdemo.R;

/**
 * Created by wesley on 2016/1/14.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder>
{
    private List<String> list;
    private Context context;
    private LayoutInflater inflater;
    private final static int TYPE_0 = 0;
    private final static int TYPE_1 = 1;
    private OnItemClickListener listener;

    public RecyclerAdapter(List<String> list, Context context)
    {
        this.list = list;
        this.context = context;
        if (context != null)
        {
            inflater = LayoutInflater.from(context);
        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }


    @Override
    public int getItemViewType(int position)
    {
        switch (position)
        {
        case 0:
            return TYPE_0;

        default:
            return TYPE_1;
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (inflater == null)
        {
            return null;
        }
        View view;
        BaseViewHolder viewHolder = null;
        switch (viewType)
        {
        case TYPE_0:
            view = inflater.inflate(R.layout.item_image, null);
            viewHolder = new ImageViewHolder(view);
            break;

        case TYPE_1:
            view = inflater.inflate(R.layout.item, null);
            viewHolder = new TextViewHolder(view);
            break;

        default:
            break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position)
    {
        //取值
        int type = getItemViewType(position);
        switch (type)
        {
        case TYPE_0:
            ((ImageViewHolder)holder).imageView.setImageResource(R.mipmap.ic_launcher);
            break;

        case 1:
            String value = list.get(position);
            ((TextViewHolder)holder).textContent.setText(value);
            ((TextViewHolder)holder).textContent.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    //int layoutPosition = holder.getLayoutPosition();
                    //listener.onItemClick(holder.textContent, layoutPosition);

                    Toast.makeText(context, list.get(position), Toast.LENGTH_SHORT).show();
                }
            });
            break;

        default:
            break;
        }

    }

    @Override
    public int getItemCount()
    {
        if (list != null)
        {
            return list.size();
        }
        return 0;
    }


    //内部类
    final static class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textContent;
        private ImageView imageView;

        public ViewHolder(View itemView)
        {
            super(itemView);
            int tag = Integer.parseInt(itemView.getTag().toString());
            switch (tag)
            {
            case TYPE_0:
                imageView = (ImageView) itemView.findViewById(R.id.image_view);
                break;

            case TYPE_1:
                textContent = (TextView) itemView.findViewById(R.id.valeu);
                break;

            default:
                break;
            }
        }
    }


    final class ImageViewHolder extends BaseViewHolder
    {
        private ImageView imageView;
        public ImageViewHolder(View itemView)
        {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
        }
    }


    final class TextViewHolder extends BaseViewHolder
    {
        private TextView textContent;
        public TextViewHolder(View itemView)
        {
            super(itemView);
            textContent = (TextView) itemView.findViewById(R.id.valeu);
        }
    }
}

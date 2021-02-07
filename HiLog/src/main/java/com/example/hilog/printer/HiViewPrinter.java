package com.example.hilog.printer;

import android.app.Activity;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hilog.HiLogConfig;
import com.example.hilog.HiLogType;
import com.example.hilog.R;
import com.example.hilog.domin.HiLogMo;
import com.example.hilog.printer.helper.HiViewPrinterHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2021/2/7
 * Author: huozhenpeng
 */
public class HiViewPrinter implements HiLogPrinter{
    private RecyclerView mRecyclerView;
    private LogAdapter mLogAdapter;
    private HiViewPrinterHelper hiViewPrinterHelper;
    public HiViewPrinter(Activity activity) {
        FrameLayout rootView = activity.findViewById(android.R.id.content);
        mRecyclerView = new RecyclerView(activity);
        mLogAdapter = new LogAdapter(activity);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mLogAdapter);
        hiViewPrinterHelper = new HiViewPrinterHelper(rootView, mRecyclerView);
    }
    public HiViewPrinterHelper getHiViewPrinterHelper() {
        return hiViewPrinterHelper;
    }
    @Override
    public void print(@NonNull HiLogConfig config, int level, String tag, @NonNull String msg) {
        if (mLogAdapter != null) {
            mLogAdapter.addLog(new HiLogMo(System.currentTimeMillis(), level, tag, msg));
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    private static class LogAdapter extends RecyclerView.Adapter<LogViewHolder> {
        public LogAdapter(Context context) {
            this.context = context;
        }
        public void addLog(HiLogMo hiLogMo) {
            data.add(0, hiLogMo);
            notifyItemInserted(0);
        }
        private Context context;
        private List<HiLogMo> data = new ArrayList<>();
        @NonNull
        @Override
        public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_log, parent, false);
            LogViewHolder logViewHolder = new LogViewHolder(view);
            return logViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
            HiLogMo logMo = data.get(position);
            holder.tv_tag.setText(logMo.tag);
            holder.tv_log.setText(logMo.getFlattenedLog());
            holder.tv_log.setTextColor(getHighlightColor(logMo.level));

        }

        @Override
        public int getItemCount() {
            return data.size();
        }
        private int getHighlightColor(int logLevel) {
            int highLight;
            switch (logLevel) {
                case HiLogType.A:
                case HiLogType.V:
                    highLight = 0xffbbbbbb;
                    break;
                case HiLogType.D:
                    highLight = 0xffffffff;
                    break;
                case HiLogType.I:
                    highLight = 0xff6a8759;
                    break;
                case HiLogType.W:
                    highLight = 0xffbbb529;
                    break;
                case HiLogType.E:
                    highLight = 0xffff6b68;
                    break;
                default:
                    highLight = 0xffffff00;
                    break;
            }
            return highLight;
        }
    }

    private static class LogViewHolder extends RecyclerView.ViewHolder {
        TextView tv_log;
        TextView tv_tag;
        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_log = itemView.findViewById(R.id.message);
            tv_tag = itemView.findViewById(R.id.tag);
        }
    }
}

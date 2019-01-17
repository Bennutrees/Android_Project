package com.example.benjamin.gallery_project.ViewModels;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.benjamin.gallery_project.R;

import java.text.DateFormat;
import java.util.Calendar;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {


    static class  EventViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView begin;
        private final TextView end;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.event_title);
            begin = itemView.findViewById(R.id.event_begin);
            end = itemView.findViewById(R.id.event_end);
        }
    }

    private final LayoutInflater mInflater;
    private Cursor data;

    private ContentResolver cr;
    private Context mContext;

    public EventAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        cr = context.getContentResolver();
        mContext = context;
    }

    @NonNull
    @Override
    public EventAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)  {
        View itemView = mInflater.inflate(R.layout.event_item, parent, false);
        return new EventViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.EventViewHolder holder, int position) {
        if (data == null || data.getCount() == 0) {
            holder.title.setText("No Event");
            holder.begin.setText("");
            holder.end.setText("");
        } else {
            // this one is relative
            //data.move(position);
            // this one is absolute
            data.moveToPosition(position);
            holder.title.setText(data.getString(data.getColumnIndex(CalendarContract.Events.TITLE)));

            long startT = data.getLong(data.getColumnIndex(CalendarContract.Events.DTSTART));
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(startT);
            DateFormat df = DateFormat.getDateInstance();

            holder.begin.setText(df.format(c.getTime()));

            long endT =data.getLong(data.getColumnIndex(CalendarContract.Events.DTEND));
            c.setTimeInMillis(endT);

            holder.end.setText(df.format(c.getTime()));
        }
    }

    private static String[] PROJECTION = new String[] {CalendarContract.Events._ID, CalendarContract.Events.TITLE,
            CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND};



    public void readEvents(long time) {
        if (ContextCompat.checkSelfPermission(mContext,  Manifest.permission.READ_CALENDAR)  == PackageManager.PERMISSION_GRANTED) {

            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(time);

            long begin = c.getTimeInMillis();

            c.add(Calendar.DATE,1);

            long end = c.getTimeInMillis();
            // first arg end
            // second args begin
            String select = CalendarContract.Events.DTSTART+"<="+end+" AND "+begin+"<"+CalendarContract.Events.DTEND;
            data = cr.query(CalendarContract.Events.CONTENT_URI, PROJECTION, select, null, null);
            this.notifyDataSetChanged();
        }
    }

    public void readEvents(int year, int month, int dayOfMonth) {

        Calendar c = Calendar.getInstance();
        c.set(year,month,dayOfMonth);
        readEvents(c.getTimeInMillis());

    }

    @Override
    public int getItemCount() {
        if (data==null) return 1;
        return data.getCount();
    }
}

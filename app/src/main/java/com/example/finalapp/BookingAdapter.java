package com.example.finalapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import models.Book;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    private List<Book> bookings;
    private OnCancelClickListener onCancelClickListener;

    public interface OnCancelClickListener {
        void onCancelClick(Book book);
    }

    public void setBookings(List<Book> bookings) {
        this.bookings = bookings;
        notifyDataSetChanged();
    }
    public void setOnCancelClickListener(OnCancelClickListener listener) {
        this.onCancelClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book booking = bookings.get(position);
        holder.bind(booking);
    }

    @Override
    public int getItemCount() {
        return bookings != null ? bookings.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView serviceTextView;
        private TextView dateTextView;
        private TextView timeTextView;
        private Button cancelButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceTextView = itemView.findViewById(R.id.serviceTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            cancelButton = itemView.findViewById(R.id.cancelButton);

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onCancelClickListener != null) {
                        onCancelClickListener.onCancelClick(bookings.get(position));
                    }
                }
            });
        }

        public void bind(Book booking) {
            serviceTextView.setText("Service: " + booking.getService());
            dateTextView.setText("Date: " + booking.getDate());
            timeTextView.setText("Time: " + booking.getTime());
        }
    }
}

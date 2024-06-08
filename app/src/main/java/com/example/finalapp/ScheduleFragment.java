package com.example.finalapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.example.finalapp.database.AppDatabase;

import java.util.List;

import models.Book;

public class ScheduleFragment extends Fragment {

    private static final String ARG_SELECTED_SERVICE = "selected_service";
    private String selectedService;
    private String selectedDate;
    private String selectedTime;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    public static ScheduleFragment newInstance(String selectedService) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SELECTED_SERVICE, selectedService);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedService = getArguments().getString(ARG_SELECTED_SERVICE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CalendarView calendarView = view.findViewById(R.id.calendar_view);
        Spinner spinnerHours = view.findViewById(R.id.spinner_hours);
        Button btnSubmit = view.findViewById(R.id.btnSubmit);

        //init spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.hours_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHours.setAdapter(adapter);

        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
        });

        spinnerHours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTime = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //nothing
            }
        });

        btnSubmit.setOnClickListener(v -> {
            if (selectedDate != null && selectedTime != null) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.setDate(selectedDate);
                    mainActivity.setTime(selectedTime);

                    AppDatabase db = AppDatabase.getInstance(getContext());

                    //check if hour is available
                    new Thread(() -> {
                        if (!isHourAvailable(db, selectedDate, selectedTime)) {
                            getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "This hour is already taken. Please select another one.", Toast.LENGTH_SHORT).show());
                            return;
                        }

                        Book book = new Book(mainActivity.getUserName(), mainActivity.getUserPhone(),
                                selectedService, selectedDate, selectedTime);

                        //insert book into db
                        insertBook(db, book);
                        getActivity().runOnUiThread(() -> {
                            Toast.makeText(getContext(), "Booking saved!", Toast.LENGTH_SHORT).show();

                            //move to ProfileFragment
                            ProfileFragment profileFragment = new ProfileFragment();
                            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, profileFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        });
                    }).start();
                }
            } else {
                Toast.makeText(getContext(), "Please select date and time", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void insertBook(AppDatabase db, Book book) {
        new Thread(() -> {
            db.bookDao().insert(book);
        }).start();
    }

    private boolean isHourAvailable(AppDatabase db, String date, String time) {
        List<Book> bookings = db.bookDao().getBooksByDateAndTime(date, time);
        return bookings.isEmpty();
    }

}

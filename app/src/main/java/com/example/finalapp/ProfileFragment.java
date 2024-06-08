package com.example.finalapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalapp.database.AppDatabase;
import com.example.finalapp.database.BookDao;

import java.util.List;

import models.Book;

public class ProfileFragment extends Fragment {

    private TextView profileNameTextView;
    private RecyclerView bookingsRecyclerView;
    private BookingAdapter bookingsAdapter;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //Init views
        profileNameTextView = view.findViewById(R.id.profileName);
        bookingsRecyclerView = view.findViewById(R.id.recyclerView);
        Button logoutButton = view.findViewById(R.id.logoutButton);

        //set up RecyclerView
        bookingsAdapter = new BookingAdapter();
        bookingsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bookingsRecyclerView.setAdapter(bookingsAdapter);

        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            String userName = mainActivity.getUserName();
            String userPhone = mainActivity.getUserPhone();

            profileNameTextView.setText(userName != null ? userName : "Please log in for profile details");

            AppDatabase db = AppDatabase.getInstance(getContext());
            BookDao bookDao = db.bookDao();

            bookingsAdapter.setOnCancelClickListener(new BookingAdapter.OnCancelClickListener() {
                @Override
                public void onCancelClick(Book book) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            bookDao.delete(book);
                            //refresh bookings list
                            List<Book> updatedBookings = "Admin".equals(userName) ? bookDao.getAllBooks() : bookDao.getBooksByUser(userName, userPhone);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    bookingsAdapter.setBookings(updatedBookings);
                                }
                            });
                        }
                    }).start();
                }
            });
            if ("Admin".equals(userName)) {
                //fetch all bookings if the user is Admin
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<Book> allBookings = bookDao.getAllBooks();
                        //Update RecyclerView
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bookingsAdapter.setBookings(allBookings);
                            }
                        });
                    }
                }).start();
            } else {
                //fetch client  haircuts
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<Book> userBookings = bookDao.getBooksByUser(userName, userPhone);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bookingsAdapter.setBookings(userBookings);
                            }
                        });
                    }
                }).start();
            }
        }

        //Handle logout
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity != null) {
                    //redirect to Home and clear user details
                    mainActivity.clearUserDetails();

                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.container, HomeFragment.newInstance(null, null))
                            .commit();
                }
            }
        });

        return view;
    }

}

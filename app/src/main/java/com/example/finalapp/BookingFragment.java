package com.example.finalapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class BookingFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_NAME = "name";

    private String mParam1;
    private String mParam2;
    private String mName;

    public BookingFragment() {
        // Required empty public constructor
    }

    public static BookingFragment newInstance(String param1, String param2, String name) {
        BookingFragment fragment = new BookingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mName = getArguments().getString(ARG_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        TextView greetingTextView = view.findViewById(R.id.greetingTextView);
        greetingTextView.setText("Hello, " + mName);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardView hairShavingCutsCard = view.findViewById(R.id.button_hair_shaving_cuts);
        CardView hairShavingBreadStylingCard = view.findViewById(R.id.button_hair_shaving_bread_styling);
        CardView hairWashingCard = view.findViewById(R.id.button_hair_washing);
        CardView breadStyleCard = view.findViewById(R.id.button_bread_style);

        hairShavingCutsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToScheduleFragment("Haircut without Beard");
            }
        });

        hairShavingBreadStylingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToScheduleFragment("Haircut with Beard");
            }
        });

        hairWashingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToScheduleFragment("Hair Washing");
            }
        });

        breadStyleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToScheduleFragment("Beard Trimming");
            }
        });
    }

    //Move to ScheduleFragment
    private void moveToScheduleFragment(String service) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        ScheduleFragment fragment = ScheduleFragment.newInstance(service);
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
package ru.mirea.komaristaya.mireaproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.mirea.komaristaya.mireaproject.databinding.FragmentFirebaseBinding;
import ru.mirea.komaristaya.mireaproject.databinding.FragmentProfileBinding;


public class FirebaseFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private FragmentFirebaseBinding binding;

    public FirebaseFragment() {
        // Required empty public constructor
    }


    public static FirebaseFragment newInstance(String param1, String param2) {
        FirebaseFragment fragment = new FirebaseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFirebaseBinding.inflate(inflater,container,false);
        View v = binding.getRoot();

        Intent intent = new Intent(getContext(), FirebaseActivity.class);
        startActivity(intent);

        return v;
    }
}
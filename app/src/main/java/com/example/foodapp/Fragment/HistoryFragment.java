package com.example.foodapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodapp.R;
import com.example.foodapp.adapter.BuyAgainAdapter;
import com.example.foodapp.databinding.FragmentHistoryBinding;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    private BuyAgainAdapter buyAgainAdapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment using View Binding
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize data
        ArrayList<String> buyAgainFoodName = new ArrayList<>();
        buyAgainFoodName.add("Food 1");
        buyAgainFoodName.add("Food 2");
        buyAgainFoodName.add("Food 3");

        ArrayList<String> buyAgainFoodPrice = new ArrayList<>();
        buyAgainFoodPrice.add("$10");
        buyAgainFoodPrice.add("$20");
        buyAgainFoodPrice.add("$30");

        ArrayList<Integer> buyAgainFoodImage = new ArrayList<>();
        buyAgainFoodImage.add(R.drawable.menu1);
        buyAgainFoodImage.add(R.drawable.menu2);
        buyAgainFoodImage.add(R.drawable.menu3);

        // Initialize and set adapter
        buyAgainAdapter = new BuyAgainAdapter(buyAgainFoodName, buyAgainFoodPrice, buyAgainFoodImage);
        binding.BuyAgainRecyclerView.setAdapter(buyAgainAdapter);

        // Set layout manager
        binding.BuyAgainRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Clear binding to avoid memory leaks
    }
}
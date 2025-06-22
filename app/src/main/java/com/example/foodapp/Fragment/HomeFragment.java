package com.example.foodapp.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.foodapp.MenuBottomSheetFragment;
import com.example.foodapp.R;
import com.example.foodapp.adapter.PopularAdapter;
import com.example.foodapp.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // You can initialize data here if needed
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.textViewSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuBottomSheetFragment bottomSheetDialog = new MenuBottomSheetFragment();
                bottomSheetDialog.show(getParentFragmentManager(), "Test");
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up image slider
        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.banner1, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.banner2, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.banner3, ScaleTypes.FIT));

        ImageSlider imageSlider = binding.imageSlider;
        imageSlider.setImageList(imageList, ScaleTypes.FIT); // chỉ cần gọi 1 lần

        imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void doubleClick(int position) {
                // Not implemented
            }

            @Override
            public void onItemSelected(int position) {
                String itemMessage = "Selected Image " + (position + 1);
                Toast.makeText(requireContext(), itemMessage, Toast.LENGTH_SHORT).show();
            }
        });

        List<String> foodName = Arrays.asList("Burger", "Sandwich", "Indomie");
        List<String> price = Arrays.asList("10,000 VND", "20,000 VND", "50,000 VND");
        List<Integer> popularFoodImages = Arrays.asList(R.drawable.menu1, R.drawable.menu2, R.drawable.menu3);
        // Thiết lập RecyclerView
        PopularAdapter adapter = new PopularAdapter(foodName, price, popularFoodImages);
        binding.PopulerRecycleView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.PopulerRecycleView.setAdapter(adapter);
    }
}

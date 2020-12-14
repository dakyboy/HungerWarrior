package com.dakiiii.hungerwarrior.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dakiiii.hungerwarrior.R;
import com.dakiiii.hungerwarrior.adapter.OrderItemsAdapter;
import com.dakiiii.hungerwarrior.model.OrderItem;
import com.dakiiii.hungerwarrior.ui.viewmodel.OrderItemViewModel;

import java.util.List;

public class NewOrdersFragment extends Fragment {
    private OrderItemViewModel eOrderItemViewModel;
    private OrderItemsAdapter eAdapter;

    public NewOrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
        eOrderItemViewModel.getOrderItems();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_new_orders, container, false);

        RecyclerView recyclerViewNewOrders = view.findViewById(R.id.recyclerview_new_orders);
        recyclerViewNewOrders.setLayoutManager(new LinearLayoutManager(container.getContext()));
        eAdapter = new OrderItemsAdapter();
        recyclerViewNewOrders.setAdapter(eAdapter);
        eOrderItemViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getActivity().getApplication()).create(OrderItemViewModel.class);
        eOrderItemViewModel.getLiveDataPendingOrderItems()
                .observe(this, new Observer<List<OrderItem>>() {
            @Override
            public void onChanged(List<OrderItem> orderItems) {
            eAdapter.setOrderItems(orderItems);
            }
        });
        return view;
    }

}

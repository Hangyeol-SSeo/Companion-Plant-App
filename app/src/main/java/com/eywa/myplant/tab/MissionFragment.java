package com.eywa.myplant.tab;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eywa.myplant.R;
import com.eywa.myplant.tab.placeholder.MissionHolderContent;

import java.util.ArrayList;
import java.util.List;

public class MissionFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mission_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.mission_recycler_list);
        Context context = view.getContext();
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        List<MissionHolderContent.MissionHolderItem> items = new ArrayList<>();
        items.add(new MissionHolderContent.MissionHolderItem("스킨답서스", "물주기", "💦", "10", 0.1f));
        items.add(new MissionHolderContent.MissionHolderItem("로즈마리", "공유하기", "🎈", "20", 0.2f));
        items.add(new MissionHolderContent.MissionHolderItem("바질", "햇빛에 3시간 두기", "☀️", "30", 0.3f));

        // Create the adapter with the list of items
        MissionRecyclerViewAdapter adapter = new MissionRecyclerViewAdapter(items);

        recyclerView.setAdapter(adapter);
        return view;
    }
}
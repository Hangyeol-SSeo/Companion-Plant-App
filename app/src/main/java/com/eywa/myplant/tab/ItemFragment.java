package com.eywa.myplant.tab;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eywa.myplant.DialogAddPlant;
import com.eywa.myplant.R;
import com.eywa.myplant.tab.placeholder.PlaceholderContent;
import com.eywa.myplant.data.model.LoggedInUser;

import java.util.UUID;

/*
HttpCallback httpCallback = new HttpCallback() {
    @Override
    public void onSuccess(String idValue) {
        // idValue를 여기서 사용하실 수 있습니다.
    }

    @Override
    public void onFailure(String errorMessage) {
        // 에러 메시지를 여기서 사용하실 수 있습니다.
    }
};

String url = "http://localhost:3000/newuser?username="+$;
Executor executor = Executors.newSingleThreadExecutor();
executor.execute(new PostRequestForId(url, httpCallback));
 */
public class ItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment newInstance(int columnCount) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

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
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Find the RecyclerView directly using findViewById
        RecyclerView recyclerView = view.findViewById(R.id.item_recycler_list);
        Context context = view.getContext();
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        recyclerView.setAdapter(new MyItemRecyclerViewAdapter(PlaceholderContent.ITEMS));

        // + 버튼 클릭시 Dialog 띄우기
        Button addButton = view.findViewById(R.id.item_recycler_add);
        addButton.setOnClickListener(v -> {
            DialogAddPlant dialog = new DialogAddPlant((nickname, realname) -> {
                String id = UUID.randomUUID().toString(); // generate unique ID
                PlaceholderContent.PlaceholderItem newItem = new PlaceholderContent.PlaceholderItem(id, fetchDisplayNameFromPreferences(), nickname, realname);
                PlaceholderContent.addItem(newItem);
                recyclerView.getAdapter().notifyDataSetChanged();
            });
            dialog.show(getChildFragmentManager(), "addPlantDialog");
        });

        return view;
    }

    private String fetchDisplayNameFromPreferences() {
        Context context = getActivity();
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userName", ""); // Default value is an empty string
    }

}
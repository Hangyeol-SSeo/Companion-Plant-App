package com.eywa.myplant.tab;

import static com.eywa.myplant.Global.PREFERENCES_NAME;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eywa.myplant.DialogAddPlant;
import com.eywa.myplant.HttpCallback;
import com.eywa.myplant.PostRequestForId;
import com.eywa.myplant.R;
import com.eywa.myplant.data.DatabaseHelper;
import com.eywa.myplant.tab.adapter.MyItemRecyclerViewAdapter;
import com.eywa.myplant.tab.placeholder.PlaceholderContent;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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

    HttpCallback httpCallback = new HttpCallback() {
        @Override
        public void onSuccess(String message, String idValue) {
            Log.d("postResponse", message + " " + idValue);
            // sharedPreferences를 사용하면 값이 계속 덮어씌워지는 문제 발생
        }

        @Override
        public void onFailure(String errorMessage) {
            Log.e("postResponse", errorMessage);
        }
    };

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
        String userId = fetchFromPreferences("userId");
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        List<PlaceholderContent.PlaceholderItem> items = dbHelper.getAllPlantsByUserId(userId);

        MyItemRecyclerViewAdapter adapter = new MyItemRecyclerViewAdapter(items);
        recyclerView.setAdapter(adapter);

        // + 버튼 클릭시 Dialog 띄우기
        Button addButton = view.findViewById(R.id.item_recycler_add);
        addButton.setOnClickListener(v -> {
            DialogAddPlant dialog = new DialogAddPlant((nickname, realname, plantImageUri) -> {
                String id = UUID.randomUUID().toString(); // generate unique ID

                // Sending UUID to the server
                String url = "/newplant?plantId="+id + "&plantname="+nickname + "&userId="+userId;
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(new PostRequestForId(url, httpCallback));

                PlaceholderContent.PlaceholderItem newItem = new PlaceholderContent.PlaceholderItem(id, userId, nickname, realname, plantImageUri);

                // Save data to local SQLite database
                dbHelper.addPlant(newItem);
                adapter.addItem(newItem);

                PlaceholderContent.addItem(newItem);
                recyclerView.getAdapter().notifyDataSetChanged();
            });
            dialog.show(getChildFragmentManager(), "addPlantDialog");
        });

        // - 버튼 클릭시 원하는 식물 삭제
        Button delButton = view.findViewById(R.id.item_recycler_del);
        delButton.setOnClickListener(v -> {
            if (adapter.isSelectionMode()) {
                // Delete selected items
                List<PlaceholderContent.PlaceholderItem> selectedItems = adapter.getSelectedItems();
                for (PlaceholderContent.PlaceholderItem item : selectedItems) {
                    // Delete item from server
                    String url = "/rmplant?plantId="+item.id;
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(new PostRequestForId(url, httpCallback));

                    // Delete item from local database
                    dbHelper.deletePlant(item);

                    // Delete item from RecyclerView
                    PlaceholderContent.removeItem(item);
                    adapter.removeItem(item);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
                adapter.setSelectionMode(false);
                delButton.setText("삭제");
            } else {
                adapter.setSelectionMode(true);
                delButton.setText("확인");
            }
        });

        return view;
    }

    private String fetchFromPreferences(String key) {
        Context context = getActivity();
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, ""); // Default value is an empty string
    }
}
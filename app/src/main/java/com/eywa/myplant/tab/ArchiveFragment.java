package com.eywa.myplant.tab;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.eywa.myplant.MainActivity;
import com.eywa.myplant.R;
import com.eywa.myplant.tab.adapter.ArchiveCategoryRecyclerViewAdapter;
import com.eywa.myplant.tab.adapter.ArchiveRecyclerViewAdapter;
import com.eywa.myplant.tab.placeholder.ArchiveCategoryHolderContent;
import com.eywa.myplant.tab.placeholder.ArchiveHolderContent;

import java.util.ArrayList;
import java.util.List;

public class ArchiveFragment extends Fragment implements OnBackPressedListener {
    private WebView myWebView;
    private SearchView searchView;
    private RecyclerView archiveCategoryRecyclerView;
    private ArchiveCategoryRecyclerViewAdapter archiveCategoryRecyclerViewAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_archive_list, container, false);

        myWebView = (WebView) view.findViewById(R.id.archive_webview);
        searchView = view.findViewById(R.id.archive_search);
        archiveCategoryRecyclerView = view.findViewById(R.id.archive_list);
        archiveCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl("https://nongsaro.go.kr/portal/ps/psz/psza/contentSub.ps?menuId=PS00376&cntntsNo=12976&totalSearchYn=Y");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search submit
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //plantAdapter.getFilter().filter(newText);
                return false;
            }
        });

        // Assume getArchiveCategories() returns a list of ArchiveCategoryHolderContent
        archiveCategoryRecyclerViewAdapter = createArchiveInstance();
        archiveCategoryRecyclerView.setAdapter(archiveCategoryRecyclerViewAdapter);

        return view;
    }

    @Override
    public void onBackPressed() {
        if (myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MainActivity) context).setOnBackPressedListener(this);
    }

    public ArchiveCategoryRecyclerViewAdapter createArchiveInstance() {
        // 기초 도감 item instance 생성
        ArchiveHolderContent archiveHolderContent = new ArchiveHolderContent(Uri.parse("https://example.com/image.jpg"), Uri.parse("https://example.com/page.html"), "식물이름1", "종1", "학명1");

        List<ArchiveHolderContent> archiveHolderContentList = new ArrayList<>();
        archiveHolderContentList.add(archiveHolderContent);  // 기초 도감 item을 Recyclerview에 추가
        ArchiveRecyclerViewAdapter recyclerViewAdapter = new ArchiveRecyclerViewAdapter(archiveHolderContentList);

        // category item 추가
        String categoryName = "ㄱ";
        ArchiveCategoryHolderContent archiveCategoryHolderContent = new ArchiveCategoryHolderContent(categoryName, recyclerViewAdapter);

        List<ArchiveCategoryHolderContent> categoryHolderContentList = new ArrayList<>();
        categoryHolderContentList.add(archiveCategoryHolderContent);  // category item을 Recyclerview에 추가

        ArchiveCategoryRecyclerViewAdapter archiveCategoryRecyclerViewAdapter = new ArchiveCategoryRecyclerViewAdapter(categoryHolderContentList);
        return archiveCategoryRecyclerViewAdapter;
    }
}
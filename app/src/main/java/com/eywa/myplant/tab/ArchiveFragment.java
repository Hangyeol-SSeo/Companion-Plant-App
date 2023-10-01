package com.eywa.myplant.tab;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.eywa.myplant.MainActivity;
import com.eywa.myplant.R;
import com.eywa.myplant.tab.adapter.ArchiveCategoryRecyclerViewAdapter;
import com.eywa.myplant.tab.adapter.ArchiveRecyclerViewAdapter;
import com.eywa.myplant.tab.placeholder.ArchiveCategoryHolderContent;
import com.eywa.myplant.tab.placeholder.ArchiveHolderContent;

import java.util.ArrayList;
import java.util.List;

public class ArchiveFragment extends Fragment implements OnBackPressedListener {
    public static WebView webView;
    public static LinearLayout linearLayout;
    private SearchView searchView;
    private RecyclerView archiveCategoryRecyclerView;
    private ArchiveCategoryRecyclerViewAdapter archiveCategoryRecyclerViewAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_archive_list, container, false);

        webView = (WebView) view.findViewById(R.id.archive_webview);
        linearLayout = view.findViewById(R.id.archive_container);
        searchView = view.findViewById(R.id.archive_search);
        archiveCategoryRecyclerView = view.findViewById(R.id.archive_list);
        archiveCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search submit
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                archiveCategoryRecyclerViewAdapter.filter(newText);
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
        if (webView.canGoBack()) {
            webView.goBack();
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
        List<ArchiveHolderContent> archiveHolderContentList = new ArrayList<>();
        archiveHolderContentList.add(new ArchiveHolderContent(Uri.parse("https://nongsaro.go.kr/cms_contents/301/12976_MF_ATTACH_01.jpg"), Uri.parse("https://nongsaro.go.kr/portal/ps/psz/psza/contentSub.ps?menuId=PS00376&cntntsNo=12976&totalSearchYn=Y"), "식물이름1", "종1", "학명1"));  // 기초 도감 item을 Recyclerview에 추가
        ArchiveRecyclerViewAdapter recyclerViewAdapter = new ArchiveRecyclerViewAdapter(archiveHolderContentList);

        // category item 추가
        List<ArchiveCategoryHolderContent> categoryHolderContentList = new ArrayList<>();
        categoryHolderContentList.add(new ArchiveCategoryHolderContent("ㄱ", recyclerViewAdapter));  // category item을 Recyclerview에 추가

        ArchiveCategoryRecyclerViewAdapter archiveCategoryRecyclerViewAdapter = new ArchiveCategoryRecyclerViewAdapter(categoryHolderContentList);
        return archiveCategoryRecyclerViewAdapter;
    }
}
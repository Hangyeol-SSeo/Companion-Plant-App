package com.eywa.myplant.tab;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ArchiveFragment extends Fragment implements OnBackPressedListener {
    public static WebView webView;
    public static LinearLayout linearLayout;
    private SearchView searchView;
    private RecyclerView archiveCategoryRecyclerView;
    private ArchiveCategoryRecyclerViewAdapter archiveCategoryRecyclerViewAdapter;

    private final Executor executor = Executors.newSingleThreadExecutor(); // 백그라운드 작업을 위한 Executor
    private final Handler handler = new Handler(Looper.getMainLooper()); // 메인 스레드에서 UI 업데이트를 위한 Handler



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

        executor.execute(new CreateArchiveInstanceRunnable());

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

    private class CreateArchiveInstanceRunnable implements Runnable {
        @Override
        public void run() {
            final ArchiveCategoryRecyclerViewAdapter archiveCategoryRecyclerViewAdapter = createArchiveInstance();

            // 메인 스레드에서 RecyclerView에 Adapter를 설정
            handler.post(new Runnable() {
                @Override
                public void run() {
                    archiveCategoryRecyclerView.setAdapter(archiveCategoryRecyclerViewAdapter);
                }
            });
        }
    }

    public ArchiveCategoryRecyclerViewAdapter createArchiveInstance() {
        // 기초 도감 item instance 생성
        // 가
        List<ArchiveHolderContent> archiveHolderContentList1 = new ArrayList<>(); // 기초 도감 item을 Recyclerview에 추가
        archiveHolderContentList1.add(new ArchiveHolderContent(Uri.parse("https://www.nongsaro.go.kr/cms_contents/301/15831_MF_REPR_ATTACH_01.jpg"), Uri.parse("https://www.nongsaro.go.kr/portal/ps/psz/psza/contentSub.ps;jsessionid=mVwOCiVxmY2YXTJtc1547ZsGsGM9OcMnUawn1naa1X1o9nllU8raxbz1eSRN0wBW.nongsaro-web_servlet_engine1?menuId=PS00376&cntntsNo=15831&totalSearchYn=Y"), "고무나무", "뽕나무과", "Ficus benghalensis", 16, 28, 40, 70, 3, 4, 1));
        ArchiveRecyclerViewAdapter recyclerViewAdapter1 = new ArchiveRecyclerViewAdapter(archiveHolderContentList1);

        // 사
        List<ArchiveHolderContent> archiveHolderContentList7 = new ArrayList<>(); // 기초 도감 item을 Recyclerview에 추가
        archiveHolderContentList7.add(new ArchiveHolderContent(Uri.parse("https://nongsaro.go.kr/cms_contents/301/12976_MF_ATTACH_01.jpg"), Uri.parse("https://nongsaro.go.kr/portal/ps/psz/psza/contentSub.ps?menuId=PS00376&cntntsNo=12976&totalSearchYn=Y"), "스킨답서스", "천남성과", "Epipremnum aureum 'Lime'", 21, 25, 40, 70, 2, 4, 1));
        archiveHolderContentList7.add(new ArchiveHolderContent(Uri.parse("https://www.nongsaro.go.kr/cms_contents/1122/204869_MF_BIMG_01.jpg"), Uri.parse("https://www.nongsaro.go.kr/portal/ps/psz/psza/contentMain.ps?menuId=PS04099&gnbIndex=CUSTOMINFO&directUrl=http%3A%2F%2Fwww.nongsaro.go.kr%2Fportal%2Fps%2Fpsz%2Fpsza%2FcontentMain.ps%3FmenuId%3DPS04099&sServiceCd=1410&lServiceCd=1006&mServiceCd=1045&contentId=15183&pageUnit=6"), "산세베리아 하니", "용설란과", "Sansevieria trifasciata 'Hahnii'", 18, 27, 40, 70, 2, 4, 15));
        archiveHolderContentList7.add(new ArchiveHolderContent(Uri.parse("https://www.nongsaro.go.kr/cms_contents/301/19717_MF_REPR_ATTACH_01.jpg"), Uri.parse("https://www.nongsaro.go.kr/portal/ps/psz/psza/contentSub.ps;jsessionid=b11ckNmOQAJkfRjXMm1C3xMFL1MV6pVCN5WTo0CVochQWS9nUeGdFhlAdi9rmVVl.nongsaro-web_servlet_engine1?menuId=PS00376&cntntsNo=19717&totalSearchYn=Y"), "스파티필룸", "천남성과", "Spathiphyllum wallisii", 18, 32, 40, 70, 2, 3, 3));
        ArchiveRecyclerViewAdapter recyclerViewAdapter7 = new ArchiveRecyclerViewAdapter(archiveHolderContentList7);

        // 아
        List<ArchiveHolderContent> archiveHolderContentList8 = new ArrayList<>(); // 기초 도감 item을 Recyclerview에 추가
        archiveHolderContentList8.add(new ArchiveHolderContent(Uri.parse("https://img1.daumcdn.net/thumb/R1280x0/?fname=http://t1.daumcdn.net/brunch/service/user/cg7o/image/gezw1ErBCRBHLIVHHxJ6-HDUfWg.jpg"), Uri.parse("https://fuleaf.com/plants/detail/5ecb98b8612274031566b2d3"), "안스리움", "천남성과", "Anthurium andraeanum", 18, 25, 40, 70, 2, 3, 3));
        archiveHolderContentList8.add(new ArchiveHolderContent(Uri.parse("https://www.nongsaro.go.kr/cms_contents/301/18658_MF_REPR_ATTACH_01.jpg"), Uri.parse("https://www.nongsaro.go.kr/portal/ps/psz/psza/contentSub.ps?menuId=PS00376&cntntsNo=18658&totalSearchYn=Y"), "알로카시아", "천남성과", "Alocasia amazonica", 21, 25, 40, 70, 2, 3, 3));
        ArchiveRecyclerViewAdapter recyclerViewAdapter8 = new ArchiveRecyclerViewAdapter(archiveHolderContentList8);

        // 자
        List<ArchiveHolderContent> archiveHolderContentList9 = new ArrayList<>(); // 기초 도감 item을 Recyclerview에 추가
        archiveHolderContentList9.add(new ArchiveHolderContent(Uri.parse("https://www.nongsaro.go.kr/cms_contents/301/16447_MF_REPR_ATTACH_01.jpg"), Uri.parse("https://www.nongsaro.go.kr/portal/ps/psz/psza/contentMain.ps?menuId=PS00376&pageIndex=1&pageSize=10&pageUnit=10&cntntsNo=&detailSrch=&sDtlSrchText=%E3%85%88&sType=sCntntsSj&sText=&sDtlSrchType=sDtlSrchsCntntsSj&sDtlSrchLight=&sDtlSrchGrwhstle=&sDtlSrchLefcolr=&sDtlSrchLefmrk=&sDtlSrchFlclr=&sDtlSrchFmldecolr=&sDtlSrchIgnSeason=&sDtlSrchWinterLwet=&priceType=&sDtlSrchPriceType=&sDtlSrchWaterCycle="), "접란", "백합과", "Chlorophytum comosum", 16, 28, 40, 70, 2, 4, 3));
        ArchiveRecyclerViewAdapter recyclerViewAdapter9 = new ArchiveRecyclerViewAdapter(archiveHolderContentList9);

        // 타
        List<ArchiveHolderContent> archiveHolderContentList12 = new ArrayList<>(); // 기초 도감 item을 Recyclerview에 추가
        archiveHolderContentList12.add(new ArchiveHolderContent(Uri.parse("https://www.nongsaro.go.kr/cms_contents/301/13002_MF_REPR_ATTACH_01.jpg"), Uri.parse("https://www.nongsaro.go.kr/portal/ps/psz/psza/contentSub.ps?menuId=PS00376&pageIndex=1&pageSize=10&pageUnit=6&cntntsNo=13002&detailSrch=&sDtlSrchText=%E3%85%8C&sType=sCntntsSj&sText=&sDtlSrchType=sDtlSrchsCntntsSj&sDtlSrchLight=&sDtlSrchGrwhstle=&sDtlSrchLefcolr=&sDtlSrchLefmrk=&sDtlSrchFlclr=&sDtlSrchFmldecolr=&sDtlSrchIgnSeason=&sDtlSrchWinterLwet=&priceType=big&sDtlSrchPriceType=&sDtlSrchWaterCycle="), "테이블야자", "야자과", "Chamaedorea elegans", 21, 25, 40, 70, 2, 4, 3));
        ArchiveRecyclerViewAdapter recyclerViewAdapter12 = new ArchiveRecyclerViewAdapter(archiveHolderContentList12);

        // 하
        List<ArchiveHolderContent> archiveHolderContentList14 = new ArrayList<>(); // 기초 도감 item을 Recyclerview에 추가
        archiveHolderContentList14.add(new ArchiveHolderContent(Uri.parse("https://www.nongsaro.go.kr/cms_contents/301/12986_MF_REPR_ATTACH_01.jpg"), Uri.parse("https://www.nongsaro.go.kr/portal/ps/psz/psza/contentSub.ps?menuId=PS00376&cntntsNo=12986&totalSearchYn=Y"), "호야", "박주가리과", "Hoya carnosa", 16, 27, 40, 70, 6, 12, 15));
        archiveHolderContentList14.add(new ArchiveHolderContent(Uri.parse("https://www.nongsaro.go.kr/cms_contents/301/19453_MF_REPR_ATTACH_01.jpg"), Uri.parse("https://www.nongsaro.go.kr/portal/ps/psz/psza/contentSub.ps?menuId=PS00376&cntntsNo=19453&totalSearchYn=Y"), "후마타 고사리", "고사리과", "Humata tyermannii", 16, 20, 70, 100, 2, 4, 3));
        ArchiveRecyclerViewAdapter recyclerViewAdapter14 = new ArchiveRecyclerViewAdapter(archiveHolderContentList14);

        // category item 추가
        List<ArchiveCategoryHolderContent> categoryHolderContentList = new ArrayList<>(); // category item을 Recyclerview에 추가
        categoryHolderContentList.add(new ArchiveCategoryHolderContent("ㄱ", recyclerViewAdapter1));
        categoryHolderContentList.add(new ArchiveCategoryHolderContent("ㅅ", recyclerViewAdapter7));
        categoryHolderContentList.add(new ArchiveCategoryHolderContent("ㅇ", recyclerViewAdapter8));
        categoryHolderContentList.add(new ArchiveCategoryHolderContent("ㅈ", recyclerViewAdapter9));
        categoryHolderContentList.add(new ArchiveCategoryHolderContent("ㅌ", recyclerViewAdapter12));
        categoryHolderContentList.add(new ArchiveCategoryHolderContent("ㅎ", recyclerViewAdapter14));

        ArchiveCategoryRecyclerViewAdapter archiveCategoryRecyclerViewAdapter = new ArchiveCategoryRecyclerViewAdapter(categoryHolderContentList);
        return archiveCategoryRecyclerViewAdapter;
    }

}
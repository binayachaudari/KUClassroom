package com.example.binaya.kuclassroom.NoticeTab;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.binaya.kuclassroom.R;

/**
 * Created by Binaya on 6/3/17.
 */

public class SeminarTalks extends Fragment {
    private static final String TAG = "SeminarTalks";
    Button search;
    EditText editText;
    TextView txt;
    public WebView webView;
    String url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.seminar_talks,container,false);

        search = (Button) view.findViewById(R.id.button);
        editText = (EditText) view.findViewById(R.id.editText);
//        final SwipeRefreshLayout swipeToRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        final TextView txt = (TextView) view.findViewById(R.id.txtInfo);
        webView = (WebView) view.findViewById(R.id.webView);
        webView.setWebViewClient(new SeminarTalks.MyBrowser());  //set the WebViewClient of your WebView to the custom subclass that you created in your code.
        txt.setText("Seminar and Talks!");


        search.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            //Called when a view has been clicked.
            public void onClick(View v) {
                url = webView.getUrl();
                webView.findAllAsync(editText.getText().toString()); //findAllAsync->Finds all instances on the page and highlights them, asynchronously
                txt.setText("You Searched For " + editText.getText().toString());
            }
        });

        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            //Returns True if the listener has consumed the event, false otherwise.
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK){
                    if(webView.canGoBack()){
                        webView.goBack();
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                return false;
            }
        });

//        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                webView.reload();
//                swipeToRefresh.setRefreshing();
//            }
//        });


        url = "http://www.ku.edu.np/news/index.php?op=Default&postCategoryId=4&blogId=1";
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);

        // Webview Performance Improvement
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true); //Enables storage of cookie
        webView.getSettings().setEnableSmoothTransition(true);

        txt.setText("Notices & Announcements");

        return view;
    }

    public class MyBrowser extends WebViewClient {
        @Override
        //Give the host application a chance to take over the control when a new url is about to be loaded in the current WebView.
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url)
        {
            view.loadUrl("javascript:(function() { " +
                    "document.getElementById('Menu').style.display=\"none\"; " +
                    "document.getElementById('Content').style.width=\"99%\";" +
                    "document.getElementById('Bottommenu').style.display=\"none\"; " +
                    "document.getElementById('Bottom').style.display=\"none\"; " +
                    "document.getElementById('Title').style.display=\"none\"; " +
                    "document.getElementById('Subtitle').style.display=\"none\"; " +
                    "document.getElementsByTagName('h2')[0].style.display=\"none\"; " +
                    "document.getElementsByTagName('img')[0].style.width=\"100%\";" +
                    "var elems = document.getElementsByClassName('footer');for(var i = 0; i != elems.length; ++i){elems[i].style.display = \"none\"; }" +
                    "})()");
        }
    }
}

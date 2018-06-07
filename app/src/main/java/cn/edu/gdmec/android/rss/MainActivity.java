package cn.edu.gdmec.android.rss;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    public final String RSS_URL = "http://ent.qq.com/movie/rss_movie.xml";
    public final String tag = "RSSReader";
    private RssFeed feed = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
        .detectDiskReads()
        .detectDiskWrites()
        .detectNetwork()
        .penaltyLog()
        .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
        .detectLeakedSqlLiteObjects()
        .detectLeakedClosableObjects()
        .penaltyLog()
        .penaltyDeath()
        .build());
        try{
            feed = new RssFeed_SAXParser().getFeed(RSS_URL);

        }catch (Exception e){
            e.printStackTrace();
        }
        showListView();
    }

    private void showListView() {
        ListView itemList = (ListView) this.findViewById(R.id.listView1);
        if (feed == null){
            setTitle("访问的Rss无效");
            return;
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,feed.getAllItems(),android.R.layout.simple_list_item_2,
                new String[]{RssItem.TITLE,RssItem.PUBDATE},new int[]{
                android.R.id.text1,android.R.id.text2
        });
        itemList.setAdapter(simpleAdapter);
        itemList.setOnItemClickListener(this);
        itemList.setSelection(0);
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(this,ShowDescriptionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title",feed.getItem(position).getTitle());
        bundle.putString("description",feed.getItem(position).getDescription());
        bundle.putString("link",feed.getItem(position).getLink());
        bundle.putString("pubdate",feed.getItem(position).getPubdate());
        intent.putExtra("android.intent.extra.rssItem",bundle);
        startActivityForResult(intent,0);

    }
}

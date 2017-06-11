package neu.cn.mymaterialtest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private FruitAdapter fruitAdapter;
    private GirlAdapter girlAdapter;
    private List<Fruit> fruitLists = new ArrayList<>();
    private List<BeautifulGirl> girlLists = new ArrayList<>();

    private Fruit[] fruits = {new Fruit(R.drawable.apple, "Apple"), new Fruit(R.drawable.banana, "Banana"),
            new Fruit(R.drawable.orange, "Orange"), new Fruit(R.drawable.watermelon, "Watermelon"),
            new Fruit(R.drawable.pear, "Pear"), new Fruit(R.drawable.grape, "Grape"),
            new Fruit(R.drawable.pineapple, "Pineapple"), new Fruit(R.drawable.strawberry, "Strawberry"),
            new Fruit(R.drawable.cherry, "Cherry"), new Fruit(R.drawable.mango, "Mango")};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFruitLists();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //refreshFruits();
                new GetData().execute("http://gank.io/api/data/福利/10/3");
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        fruitAdapter = new FruitAdapter(fruitLists);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        new GetData().execute("http://gank.io/api/data/福利/10/2");
        //mRecyclerView.setAdapter(fruitAdapter);
        final NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "FloatingActionButton clicked", Toast.LENGTH_SHORT).show();
                Snackbar.make(v, "Data deleted", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "FloatingActionButton clicked", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                Toast.makeText(this, "you clicked backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "you clicked delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "you clicked settings", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 初始化fruitlists
     */
    private void initFruitLists() {
        fruitLists.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitLists.add(fruits[index]);
        }
    }

    private class GetData extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //设置swipeRefreshLayout为刷新状态
            mSwipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... params) {
            return MyOkhttp.get(params[0]);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (!TextUtils.isEmpty(result)) {

                JSONObject jsonObject;
                Gson gson = new Gson();
                String jsonData = null;

                try {
                    jsonObject = new JSONObject(result);
                    jsonData = jsonObject.getString("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (girlLists == null || girlLists.size() == 0) {
                    girlLists = gson.fromJson(jsonData, new TypeToken<List<BeautifulGirl>>() {
                    }.getType());
                } else {
                    List<BeautifulGirl> girls = gson.fromJson(jsonData, new TypeToken<List<BeautifulGirl>>() {
                    }.getType());
                    girlLists.addAll(girls);
                }

                if (girlAdapter == null) {
                    girlAdapter = new GirlAdapter(girlLists);
                    mRecyclerView.setAdapter(girlAdapter);
                } else {
                    girlAdapter.notifyDataSetChanged();
                }
            }
            //停止swipeRefreshLayout加载动画
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruitLists();
                        fruitAdapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}

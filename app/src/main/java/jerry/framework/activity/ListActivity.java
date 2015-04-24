package jerry.framework.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import jerry.framework.R;
import jerry.framework.adapter.BaseRecyclerViewAdapter;

public class ListActivity extends BaseActivity {

    RecyclerView rv;

    List<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_list);
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void iniViews() {
        rv = (RecyclerView) findViewById(R.id.my_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));

        datas = new ArrayList<>();
        datas.add("1");
        rv.setAdapter(new BaseRecyclerViewAdapter(this, datas));
    }

    @Override
    protected void iniEvent() {

    }
}

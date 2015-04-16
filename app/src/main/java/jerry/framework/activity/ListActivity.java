package jerry.framework.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import jerry.framework.R;
import jerry.framework.adapter.BaseRecyclerViewAdapter;

@EActivity(R.layout.activity_list)
public class ListActivity extends BaseActivity {

    @ViewById(R.id.my_recycler_view)
    RecyclerView rv;

   List<String> datas;

    @AfterViews
    void ini() {
        rv.setLayoutManager(new LinearLayoutManager(this));

        datas = new ArrayList<>();
        datas.add("1");
        rv.setAdapter(new BaseRecyclerViewAdapter(this, datas));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


}

package murmur.pagefolddemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import murmur.pagefoldlayout.PageFoldTransformer;


public class MainActivity extends AppCompatActivity {
    private static final int[] mColor = {
            Color.rgb(153, 204, 255),
            Color.GREEN,
            Color.rgb(255, 204, 153),
            Color.YELLOW,
            Color.CYAN
    };
    private static final String mListItem[] = {
            "yousyoku no nekoya",
            "new game!!",
            "sakura quest",
            "re:talker",
            "mumimumi",
            "kobayasi",
            "to-ru",
            "kanna",
            "Under The Bridge",
            "riku",
            "nino",
            "Pko"
    };

    private static final int NUM_PAGES = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myAdapter);
        mViewPager.setPageTransformer(true, new PageFoldTransformer());
    }




    public static class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public Fragment getItem(int position) {
            return ArrayListFragment.newInstance(position);
        }
    }




    public static class ArrayListFragment extends ListFragment {
        int mNum;

        /**
         * Create a new instance of CountingFragment, providing "num"
         * as an argument.
         */
        static ArrayListFragment newInstance(int num) {
            ArrayListFragment f = new ArrayListFragment();

            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);

            return f;
        }

        /**
         * When creating, retrieve this instance's number from its arguments.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        }

        /**
         * The Fragment's UI is just a simple text view showing its
         * instance number.
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_layout, container, false);
            View holder = v.findViewById(R.id.con);
            holder.setBackgroundColor(mColor[mNum]);
            holder = v.findViewById(R.id.text);
            String tmp = "Fragment #" + mNum;
            ((TextView)holder).setText(tmp);
            v.setTag(mNum);
            return v;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            setListAdapter(new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_list_item_1, mListItem));
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Log.i("kanna", mNum + " page Item clicked: " + id);
        }
    }
}

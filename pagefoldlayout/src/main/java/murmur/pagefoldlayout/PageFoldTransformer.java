package murmur.pagefoldlayout;

import android.support.v4.view.ViewPager;
import android.view.View;

public class PageFoldTransformer implements ViewPager.PageTransformer{
    @Override
    public void transformPage(View view, float position) {
        int width = view.getWidth();
        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setTranslationX(0);

        }
        else if (position <= 0) { // [-1,0]
            //Log.d("kanna",view.getTag().toString() + " position " + position);
            if(position != -1) {
                ((PageFoldLayout) view).updatePoint(position);
                view.setTranslationX(-position * width);
            }
            else{
                view.setTranslationX(0);
            }
        }
        else if (position <= 1) { // (0,1]
            //Log.d("kanna",view.getTag().toString()+ " position " + position);
            if(position != 1) {
                ((PageFoldLayout)view).updatePoint(position);
                view.setTranslationX(-position * width);
            }
            else {
                view.setTranslationX(0);
            }
        }
        else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setTranslationX(0);
        }
    }
}

PageFoldLayout
======

Create a page-fold animation for ViewPager scrolling.

Based on: http://blog.csdn.net/aigestudio/article/details/42686907

![image](https://github.com/murmurmuk/PageFoldLayout/blob/master/demo.gif)

How to use:
=======

Wrap original layout in PageFoldLayout

```xml
<murmur.pagefoldlayout.PageFoldLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_height="match_parent"
    android:layout_width="match_parent">


    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:id="@+id/con"
        >
        <TextView android:id="@+id/text"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center_vertical|center_horizontal"
            />
        <ListView android:id="@android:id/list"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:drawSelectorOnTop="false"/>
    </LinearLayout>


</murmur.pagefoldlayout.PageFoldLayout>
```

set PageFoldTransformer as the page transformer.

```java
    ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
    MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager());
    mViewPager.setAdapter(myAdapter);
    mViewPager.setPageTransformer(true, new PageFoldTransformer());
```


Dependency
======

add remote maven url

```groovy
	repositories {
	    maven {
	        url "https://jitpack.io"
	    }
	}
```

then add a library dependency

```groovy
	dependencies {
	    compile 'com.github.murmurmuk:PageFoldLayout:1.0'
	}
```


package cn.com.cml.dbl;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import cn.com.cml.dbl.ui.CircleIndicatorView;
import cn.com.cml.dbl.util.PrefUtil_;
import cn.com.cml.dbl.view.Introduce1Fragment_;
import cn.com.cml.dbl.view.Introduce2Fragment_;
import cn.com.cml.dbl.view.Introduce3Fragment_;

@EActivity(R.layout.activity_introduce)
public class IntroduceActivity extends FragmentActivity {

	@ViewById(R.id.container)
	ViewPager pager;

	@ViewById(R.id.circle_first)
	CircleIndicatorView firstCircleView;

	@ViewById(R.id.circle_second)
	CircleIndicatorView secondCircleView;

	@ViewById(R.id.circle_third)
	CircleIndicatorView thirdCircleView;

	int selectedViewIndex;

	private List<CircleIndicatorView> circleViews;

	@AfterViews
	public void afterViews() {

		circleViews = new ArrayList<CircleIndicatorView>(3);

		circleViews.add(firstCircleView);
		circleViews.add(secondCircleView);
		circleViews.add(thirdCircleView);

		pager.setOffscreenPageLimit(0);

		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				circleViews.get(position).setSelected(true);

				circleViews.get(selectedViewIndex).setSelected(false);

				selectedViewIndex = position;
			}

			@Override
			public void onPageScrolled(int position, float arg1, int percent) {
			}

			@Override
			public void onPageScrollStateChanged(int position) {
			}
		});

		List<Fragment> fragments = new ArrayList<Fragment>();

		fragments.add(Introduce1Fragment_.builder().build());
		fragments.add(Introduce2Fragment_.builder().build());
		fragments.add(Introduce3Fragment_.builder().build());

		pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),
				fragments));

		circleViews.get(selectedViewIndex).setSelected(true);

	}

	private class MyPagerAdapter extends FragmentPagerAdapter {

		private List<Fragment> fragments;

		public MyPagerAdapter(FragmentManager manager, List<Fragment> fragments) {
			super(manager);
			this.fragments = fragments;
		}

		@Override
		public int getCount() {
			return this.fragments.size();
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

	}

}

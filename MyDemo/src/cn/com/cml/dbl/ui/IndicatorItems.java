package cn.com.cml.dbl.ui;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import cn.com.cml.dbl.R;

@EViewGroup(R.layout.view_indicator_items)
public class IndicatorItems extends LinearLayout implements
		OnCheckedChangeListener {

	@ViewById(R.id.indicator_icon)
	TextView leftIcon;
	private String leftIconText;

	@ViewById(R.id.indicator_text)
	TextView messageView;
	private String messageText;

	@ViewById(R.id.indicator_indicator)
	TextView rightIcon;
	private String rightIconText;

	@ViewById(R.id.indicator_switch)
	Switch switchView;

	@ViewById(R.id.indicator_desc)
	TextView indicatorDescView;

	private boolean switchChecked;
	private String indicatorDescCheckedText;
	private String indicatorDescUnCheckedText;

	private OnCheckedChangeListener onChangeListener;

	private float textSize;

	private int indicatorType = 0;

	public static final int TYPE_COMMON = 1;
	public static final int TYPE_SWITCH = 2;
	private static final String TAG = null;

	public IndicatorItems(Context context) {
		super(context);
	}

	public IndicatorItems(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initConfig(context, attrs);
	}

	public IndicatorItems(Context context, AttributeSet attrs) {
		super(context, attrs);
		initConfig(context, attrs);
	}

	private void initConfig(Context context, AttributeSet attrs) {

		TypedArray types = context.obtainStyledAttributes(attrs,
				R.styleable.IndicatorItems);

		indicatorType = types.getInt(R.styleable.IndicatorItems_arrow_type,
				TYPE_COMMON);

		leftIconText = types.getString(R.styleable.IndicatorItems_left_icon);
		rightIconText = types.getString(R.styleable.IndicatorItems_right_icon);
		messageText = types.getString(R.styleable.IndicatorItems_show_text);
		textSize = types.getFloat(R.styleable.IndicatorItems_text_size, 20);

		if (indicatorType == TYPE_SWITCH) {
			indicatorDescCheckedText = types
					.getString(R.styleable.IndicatorItems_switch_checked_text);
			indicatorDescUnCheckedText = types
					.getString(R.styleable.IndicatorItems_switch_unchecked_text);
			switchChecked = types.getBoolean(
					R.styleable.IndicatorItems_switch_checked, false);
		}

		types.recycle();
	}

	@AfterViews
	protected void afterViews() {

		leftIcon.setText(leftIconText);
		messageView.setText(messageText);

		leftIcon.setTextSize(textSize);
		messageView.setTextSize(textSize);

		switch (indicatorType) {

		case TYPE_COMMON:
			rightIcon.setText(rightIconText);
			rightIcon.setTextSize(textSize);
			break;

		case TYPE_SWITCH:
			switchView.setVisibility(VISIBLE);
			rightIcon.setVisibility(GONE);
			indicatorDescView.setVisibility(VISIBLE);
			switchView.setChecked(switchChecked);

			if (switchChecked) {
				setSwitchDesc(indicatorDescCheckedText);
			} else {
				setSwitchDesc(indicatorDescUnCheckedText);
			}

			switchView.setOnCheckedChangeListener(this);
			break;

		}
	}

	public void setSwitchChecked(boolean checked) {
		switchView.setChecked(checked);
	}

	public void setOnChangeListener(OnCheckedChangeListener onChangeListener) {
		this.onChangeListener = onChangeListener;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		
		if (isChecked) {
			setSwitchDesc(indicatorDescCheckedText);
		} else {
			setSwitchDesc(indicatorDescUnCheckedText);
		}

		if (null != onChangeListener) {
			onChangeListener.onCheckedChanged(buttonView, isChecked);
		}

	}

	private void setSwitchDesc(String text) {
		if (text == null) {
			indicatorDescView.setVisibility(GONE);
		} else {
			indicatorDescView.setVisibility(VISIBLE);
			indicatorDescView.setText(text);
		}
	}
}

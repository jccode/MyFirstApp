package com.example.myfirstapp.bitmap;

import com.example.myfirstapp.R;
import com.example.myfirstapp.util.ImageFetcher;
import com.example.myfirstapp.util.ImageWorker;
import com.example.myfirstapp.util.Utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageDetailFragment extends Fragment {
	
	private static final String IMAGE_DATA_EXTRA = "extra_image_data";
	private String mImageUrl;
	private ImageView mImageView;
	private ImageFetcher mImageFetcher;

	public static Fragment newInstance(String imageUrl) {
		final ImageDetailFragment f = new ImageDetailFragment();
		
		final Bundle args = new Bundle();
		args.putString(IMAGE_DATA_EXTRA, imageUrl);
		f.setArguments(args);
		
		return f;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mImageUrl = getArguments() != null ? getArguments().getString(IMAGE_DATA_EXTRA) : null;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_image_detail, container, false);
		mImageView = (ImageView) view.findViewById(R.id.imageView);
		mImageView.setDrawingCacheEnabled(true);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		// Use the parent activity to load the image asynchronously into the ImageView (so a single
         // cache can be used over all pages in the ViewPager
		if(ImageDetailActivity.class.isInstance(getActivity())) {
			mImageFetcher = ((ImageDetailActivity) getActivity()).getmImageFetcher();
			mImageFetcher.loadImage(mImageUrl, mImageView);
		}
		
		// Pass clicks on the ImageView to the parent activity to handle
		if(OnClickListener.class.isInstance(getActivity()) && Utils.hasHoneycomb()) {
			mImageView.setOnClickListener((OnClickListener)getActivity());
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(mImageView != null) {
			ImageWorker.cancelWork(mImageView);
			mImageView.setImageDrawable(null);
		}
	}
}

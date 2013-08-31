package com.example.myfirstapp.bitmap;

import com.example.myfirstapp.BuildConfig;
import com.example.myfirstapp.R;
import com.example.myfirstapp.provider.Images;
import com.example.myfirstapp.util.ImageCache.ImageCacheParams;
import com.example.myfirstapp.util.ImageFetcher;
import com.example.myfirstapp.util.RecyclingBitmapDrawable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class BitmapFragment extends Fragment {
	private static final String TAG = "BitmapFragment";
	private static final String IMAGE_CACHE_DIR = "thumbs";
	
	private int mImageThumbSize;
	private int mImageThumbSpacing;
	private ImageAdapter mAdapter;
	private ImageFetcher mImageFetcher;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		
		mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
		mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);
		
		ImageCacheParams cacheParams = new ImageCacheParams(getActivity(), IMAGE_CACHE_DIR);
		cacheParams.setMemCacheSizePercent(.25f);
		
		mImageFetcher = new ImageFetcher(getActivity(), mImageThumbSize);
		mImageFetcher.setLoadingImage(R.drawable.empty_photo);
		mImageFetcher.addImageCache(getActivity().getSupportFragmentManager(), cacheParams);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_bitmap, container, false);
		final GridView gridview = (GridView) view.findViewById(R.id.gridView);
		mAdapter = new ImageAdapter(getActivity());
		gridview.setAdapter(mAdapter);
		gridview.setOnItemClickListener(new ItemClickListener());
		
		gridview.setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// Pause fetcher to ensure smoother scrolling when flinging
				if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
					mImageFetcher.setPauseWork(true);
				} else {
					mImageFetcher.setPauseWork(false);
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				
			}
			
		});
		
		// This listener is used to get the final width of the GridView and then calculate the
        // number of columns and the width of each column. The width of each column is variable
        // as the GridView has stretchMode=columnWidth. The column width is used to set the height
        // of each view so we get nice square thumbnails.
		gridview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				if(mAdapter.getNumColumns() == 0) {
					final int numColumns = (int) Math.floor(
							gridview.getWidth() / (mImageThumbSize + mImageThumbSpacing));
					if(numColumns > 0) {
						final int columnWidth = 
								(gridview.getWidth() / numColumns) - mImageThumbSpacing;
						mAdapter.setNumColumns(numColumns);
						mAdapter.setItemHeight(columnWidth);
						if(BuildConfig.DEBUG) {
							Log.d(TAG, "onCreateView - numColumns set to " + numColumns);
						}
					}
				}
			}
		});
		
		return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_bitmap, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.clear_cache:
			mImageFetcher.clearCache();
			Toast.makeText(getActivity(), R.string.clear_cache_complete_toast,
					Toast.LENGTH_SHORT).show();
			return true;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mImageFetcher.setExitTasksEarly(false);
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onPause() {
		super.onPause();
		mImageFetcher.setPauseWork(false);
		mImageFetcher.setExitTasksEarly(true);
		mImageFetcher.flushCache();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mImageFetcher.closeCache();
	}

	private class ImageAdapter extends BaseAdapter {
		
		private final Context mContext;
		private GridView.LayoutParams mImageViewLayoutParams;
		private int mNumColumns = 0;
		private int mItemHeight = 0;
		
		public ImageAdapter(Context context) {
			super();
			mContext = context;
			mImageViewLayoutParams = new GridView.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			
		}

		@Override
		public int getCount() {
			return Images.imageThumbUrls.length;
		}

		@Override
		public Object getItem(int position) {
			return Images.imageThumbUrls[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ImageView imageView;
			if(convertView == null) { // if it's not recycled, instantiate and initialize 
				imageView = new RecyclingImageView(mContext);
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setLayoutParams(mImageViewLayoutParams);
			} else {  // Otherwise re-use the converted view
				imageView = (ImageView) convertView;
			}
			
			// Check the height matches our calculated column width
			if(imageView.getLayoutParams().height != mItemHeight) {
				imageView.setLayoutParams(mImageViewLayoutParams);
			}
			
			
			mImageFetcher.loadImage(Images.imageThumbUrls[position], imageView);
			return imageView;
		}
		
		
		public void setItemHeight(int height) {
			if(height == mItemHeight) {
				return ;
			}
			mItemHeight = height;
			mImageViewLayoutParams = new GridView.LayoutParams(LayoutParams.MATCH_PARENT, mItemHeight);
			mImageFetcher.setImageSize(height);
			notifyDataSetChanged();
		}
		
		public int getNumColumns() {
			return mNumColumns;
		}
		
		public void setNumColumns(int numColumns) {
			mNumColumns = numColumns;
		}
	}
	
	private class ItemClickListener implements AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			final Intent i = new Intent(getActivity(), ImageDetailActivity.class);
			i.putExtra(ImageDetailActivity.EXTRA_IMAGE, (int)id);
			
			startActivity(i);
		}
		
	}
}

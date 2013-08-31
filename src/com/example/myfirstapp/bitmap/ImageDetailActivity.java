package com.example.myfirstapp.bitmap;

import java.io.IOException;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

import com.example.myfirstapp.R;
import com.example.myfirstapp.provider.Images;
import com.example.myfirstapp.util.ImageCache;
import com.example.myfirstapp.util.ImageFetcher;
import com.example.myfirstapp.util.Utils;

public class ImageDetailActivity extends ActionBarActivity implements OnClickListener {
	
	private static final int ACTIVITY_SELECT_IMAGE = 1;
	public static final String EXTRA_IMAGE = "extra_image";
	private static final String IMAGE_CACHE_DIR = "images";
	private ViewPager mPager;
	private ImagePagerAdapter mAdapter;
	private ImageFetcher mImageFetcher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_detail);
		
		mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), Images.imageUrls.length);
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		mPager.setOffscreenPageLimit(2);
		
		final int extraCurrentItem = getIntent().getIntExtra(EXTRA_IMAGE, -1);
		if(extraCurrentItem != -1) {
			mPager.setCurrentItem(extraCurrentItem);
		}
		
		// fetch screen width & height
		DisplayMetrics displayMetrics = getDisplayMetrics();
		final int height = displayMetrics.heightPixels;
		final int width = displayMetrics.widthPixels;
		final int longest = (height > width ? height : width);
		
		mImageFetcher = new ImageFetcher(this, longest);
		
		ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams(this, IMAGE_CACHE_DIR);
		cacheParams.setMemCacheSizePercent(.25f);
		mImageFetcher.addImageCache(getSupportFragmentManager(), cacheParams);
		mImageFetcher.setImageFadeIn(false);
		
		// set up activity to go full screen
		getWindow().addFlags(LayoutParams.FLAG_FULLSCREEN);
		
		// Enable some additional newer visibility and ActionBar features to create a more
		// immersive photo viewing experience
		if(Utils.hasHoneycomb()) {
			final ActionBar actionBar = getSupportActionBar();
			
			// Hide title text and set home as up
			actionBar.setDisplayShowTitleEnabled(false);
			actionBar.setDisplayHomeAsUpEnabled(true);
			
			mPager.setOnSystemUiVisibilityChangeListener(
					new View.OnSystemUiVisibilityChangeListener() {
						@Override
						public void onSystemUiVisibilityChange(int visibility) {
							if((visibility & View.SYSTEM_UI_FLAG_LOW_PROFILE) != 0) {
								actionBar.hide();
							} else {
								actionBar.show();
							}
						}
					});
			
			// Start low profile mode and hide ActionBar
			mPager.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
			actionBar.hide();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				return true;
			case R.id.action_set_wallpaper:
				final WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
				
				DisplayMetrics displayMetrics = getDisplayMetrics();
				final int height = displayMetrics.heightPixels;
				final int width = displayMetrics.widthPixels;
				
				View imageView = (ImageView) findViewById(R.id.imageView);
				try {
					Bitmap toSet = Bitmap.createScaledBitmap(imageView.getDrawingCache(), width, height, false);
					wallpaperManager.setBitmap(toSet);
					finish();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				/*
				Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
				*/
				
				/*
				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				photoPickerIntent.setType("image/*");
				startActivityForResult(photoPickerIntent, ACTIVITY_SELECT_IMAGE);
				*/
				
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mImageFetcher.setExitTasksEarly(false);
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
	

	public ImageFetcher getmImageFetcher() {
		return mImageFetcher;
	}

	public void setmImageFetcher(ImageFetcher mImageFetcher) {
		this.mImageFetcher = mImageFetcher;
	}


	private class ImagePagerAdapter extends FragmentStatePagerAdapter {
		
		private final int mSize;

		public ImagePagerAdapter(FragmentManager fm, int size) {
			super(fm);
			mSize = size;
		}

		@Override
		public Fragment getItem(int position) {
			return ImageDetailFragment.newInstance(Images.imageUrls[position]);
		}

		@Override
		public int getCount() {
			return mSize;
		}
	}


	@Override
	public void onClick(View v) {
		final int visibility = mPager.getSystemUiVisibility();
		if((visibility & View.SYSTEM_UI_FLAG_LOW_PROFILE) != 0) {
			mPager.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
		} else {
			mPager.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
		}
	}
	
	/**
	 * fetch screen size
	 * @return
	 */
	private DisplayMetrics getDisplayMetrics() {
		final DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		return displayMetrics;
	}
	
	
	/**
	 * create an intent for image crop. 
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == ACTIVITY_SELECT_IMAGE && resultCode == RESULT_OK) {
//			final Bundle extras = data.getExtras();
			Uri photoUri = data.getData();
			if (photoUri != null) {
                Intent intent = new Intent("com.android.camera.action.CROP");
                //intent.setClassName("com.android.camera", "com.android.camera.CropImage");

                intent.setData(photoUri);
                intent.putExtra("outputX", 96);
                intent.putExtra("outputY", 96);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);            
                startActivityForResult(intent, 1);
			}
		}
	}
}

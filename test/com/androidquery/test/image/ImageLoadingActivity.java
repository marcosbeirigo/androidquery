package com.androidquery.test.image;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.R;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.androidquery.test.RunSourceActivity;
import com.androidquery.util.AQUtility;

public class ImageLoadingActivity extends RunSourceActivity {

	private String type;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		
		aq.id(R.id.result).gone();
		
		type = getIntent().getStringExtra("type");
		
		
		if("image_access_file".equals(type) || "image_access_memory".equals(type)){
			image_simple();
		}else if("image_file".equals(type) || "image_file_custom".equals(type)){
			//image_down();			
			load("http://farm6.static.flickr.com/5035/5802797131_a729dac808_b.jpg");
		}else if("image_preload".equals(type)){
			image_prepreload();
		}else if("image_ratio".equals(type)){
			aq.id(R.id.image).width(250);
		}
			
		
			
	}
	
	@Override
	protected void runSource(){
		AQUtility.debug(type);
		AQUtility.invokeHandler(this, type, false, null);
	}
	
	public void image_simple(){
		
		String url = "http://www.vikispot.com/z/images/vikispot/android-w.png";
		aq.id(R.id.image).image(url);
		
	}
	
	public void image_cache(){
		
		boolean memCache = false;
		boolean fileCache = true;

		aq.id(R.id.image).image("http://www.vikispot.com/z/images/vikispot/android-w.png", memCache, fileCache);
	}
	
	public void image_down(){
		
		String imageUrl = "http://farm6.static.flickr.com/5035/5802797131_a729dac808_b.jpg";            
		aq.id(R.id.image).image(imageUrl, true, true, 200, 0);

	}
	
	public void image_fallback(){
		
		String imageUrl = "http://www.vikispot.com/z/images/vikispot/xyz.png";
		aq.id(R.id.image).image(imageUrl, true, true, 0, R.drawable.image_missing);
		
	}
	
	public void image_prepreload(){
		
		String small = "http://farm6.static.flickr.com/5035/5802797131_a729dac808_s.jpg";		
		load(small);
		
		aq.id(R.id.image).width(250).height(250).image(0).visible();
		
	}
	
	private void load(String url){
		aq.id(R.id.hidden).image(url);
	}
	
	public void image_preload(){
		
		String thumbnail = "http://farm6.static.flickr.com/5035/5802797131_a729dac808_s.jpg";	
		Bitmap preset = aq.getCachedImage(thumbnail);
		
		String imageUrl = "http://farm6.static.flickr.com/5035/5802797131_a729dac808_b.jpg";		
		aq.id(R.id.image).image(imageUrl, false, false, 0, 0, preset, AQuery.FADE_IN, AQuery.RATIO_PRESERVE);
		
	}

	public void image_animation(){
	
		String imageUrl = "http://www.vikispot.com/z/images/vikispot/android-w.png";					
		aq.id(R.id.image).image(imageUrl, true, true, 0, 0, null, AQuery.FADE_IN);
		
	}
	
	
	public void image_animation2(){
		
		String imageUrl = "http://www.vikispot.com/z/images/vikispot/android-w.png";		
		aq.id(R.id.image).image(imageUrl, true, true, 0, 0, null, R.anim.slide_in_left);
		
	}
	
	public void image_ratio(){
		
		
		
		String imageUrl = "http://farm3.static.flickr.com/2199/2218403922_062bc3bcf2.jpg";	
		aq.id(R.id.image).image(imageUrl, true, true, 0, 0, null, AQuery.FADE_IN, AQuery.RATIO_PRESERVE);
	
	}
	
	public void image_file(){
		
		String imageUrl = "http://farm6.static.flickr.com/5035/5802797131_a729dac808_b.jpg";
		File file = aq.getCachedFile(imageUrl);
		
		if(file != null){
			aq.id(R.id.image).image(file, 300);
		}
		
	}
	
	public void image_custom(){
		
		String imageUrl = "http://www.vikispot.com/z/images/vikispot/android-w.png";
		final int tint = 0x77AA0000;

		aq.id(R.id.image).visible().image(imageUrl, true, true, 0, 0, new BitmapAjaxCallback(){

	        @Override
	        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status){
	           
                iv.setImageBitmap(bm);
                iv.setColorFilter(tint, PorterDuff.Mode.SRC_ATOP);
	                
	        }
		        
		});
	}
	
	public void image_file_custom(){
		
		String imageUrl = "http://farm6.static.flickr.com/5035/5802797131_a729dac808_b.jpg";
		File file = aq.getCachedFile(imageUrl);
		final int tint = 0x77AA0000;
		
		if(file != null){
			
			aq.id(R.id.image).visible().image(file, true, 300, new BitmapAjaxCallback(){

		        @Override
		        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status){
		           
	                iv.setImageBitmap(bm);
	                iv.setColorFilter(tint, PorterDuff.Mode.SRC_ATOP);
		                
		        }
			        
			});
			
		}
		
	}
	
	
	public void image_dup(){
		
		String imageUrl = "http://www.vikispot.com/z/images/vikispot/android-w.png";
		aq.id(R.id.image).image(imageUrl, false, false);

		//no network fetch for 2nd request, image will be shown when first request is completed
		aq.id(R.id.image2).image(imageUrl, false, false);
		
	}
	
	public void image_advance(){
		
		aq.id(R.id.image).width(LayoutParams.FILL_PARENT);
		
		String imageUrl = "http://farm3.static.flickr.com/2199/2218403922_062bc3bcf2.jpg";	
	
		BitmapAjaxCallback cb = new BitmapAjaxCallback();
		cb.url(imageUrl).animation(AQuery.FADE_IN).ratio(1.0f);
		
		aq.id(R.id.image).image(cb);
		
	}
	
	public void image_access_file(){
		
		String imageUrl = "http://www.vikispot.com/z/images/vikispot/android-w.png";
		File file = aq.getCachedFile(imageUrl);
		
		if(file != null){
			showResult("File:" + file + " Length:" + file.length());
		}
		
	}
	
	public void image_access_memory(){
		
		String imageUrl = "http://www.vikispot.com/z/images/vikispot/android-w.png";			
		Bitmap bm = aq.getCachedImage(imageUrl);
		
		if(bm != null){
			showResult("Dimension:" + bm.getWidth() + "x" + bm.getHeight());
		}
		
	}
	
	
}

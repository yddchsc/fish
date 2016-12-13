package com.fish.object;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import com.fish.R;

public class BulletGoods extends GameGoods{
	public BulletGoods(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initBitmap() {
		// TODO Auto-generated method stub
		bmp = BitmapFactory.decodeResource(resources, R.drawable.bullet_goods);
		object_width = bmp.getWidth();			
		object_height = bmp.getHeight();	
	}
}


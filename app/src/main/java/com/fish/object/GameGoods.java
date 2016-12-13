package com.fish.object;

import java.util.Random;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import com.fish.constant.ConstantUtil;

public class GameGoods extends GameObject{
	protected Bitmap bmp;								
	private int direction;
	public GameGoods(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
		this.speed = 10;
		Random ran = new Random();
		direction = ran.nextInt(2) + 3;
		initBitmap();
	}

	@Override
	public void initial(int arg0,float arg1,float arg2){
		isAlive = true;
		object_x = screen_width/2 - object_width/2;
		object_y = -object_height * (arg0*2 + 1);
	}

	@Override
	protected void initBitmap() {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		if(isAlive){
			canvas.save();
			canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
			canvas.drawBitmap(bmp, object_x, object_y,paint);
			canvas.restore();
			logic();
		}
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		if(!bmp.isRecycled()){
			bmp.recycle();
		}
	}

	@Override
	public void logic() {
		Random ran = new Random();
		if(direction == ConstantUtil.DIR_LEFT_UP){
			object_x -= ran.nextInt(3) + speed;
			object_y -= ran.nextInt(3) + speed;	
			if(object_x <= 0 || object_y <= 0){
				if(object_x <= 0)
					object_x = 0;
				else
					object_y = 0;
				int dir = 0;
				do{
					dir = ran.nextInt(4)+1;
				}
				while(dir == direction);
				direction = dir;
				this.speed = 10 + ran.nextInt(5);
			}
		}
		else if(direction == ConstantUtil.DIR_RIGHT_UP){
			object_x += ran.nextInt(3) + speed;
			object_y -= ran.nextInt(3) + speed;	
			if(object_x >= screen_width - object_width || object_y <= 0){
				if(object_x >= screen_width - object_width)
					object_x = screen_width - object_width;
				else
					object_y = 0;
				int dir = 0;
				do{
					dir = ran.nextInt(4)+1;
				}
				while(dir == direction);
				direction = dir;
				this.speed = 10 + ran.nextInt(5);
			}
		}
		else if(direction == ConstantUtil.DIR_LEFT_DOWN){
			object_x -= ran.nextInt(3) + speed;
			object_y += ran.nextInt(3) + speed;	
			if(object_x <= 0 || object_y >= screen_height - object_height){
				if(object_x <= 0)
					object_x = 0;
				else
					object_y = screen_height - object_height;
				int dir = 0;
				do{
					dir = ran.nextInt(4)+1;
				}
				while(dir == direction);
				direction = dir;
				this.speed = 10 + ran.nextInt(5);
			}
		}
		else if(direction == ConstantUtil.DIR_RIGHT_DOWN){
			object_x += ran.nextInt(3) + speed;
			object_y += ran.nextInt(3) + speed;	
			if(object_x >= screen_width - object_width || object_y >= screen_height - object_height){
				if(object_x >= screen_width - object_width)
					object_x = screen_width - object_width;
				else
					object_y = screen_height - object_height;
				int dir = 0;
				do{
					dir = ran.nextInt(4)+1;
				}
				while(dir == direction);
				direction = dir;
				this.speed = 10 + ran.nextInt(5);
			}
		}
	}
	@Override
	public boolean isCollide(GameObject obj) {
		if (object_x <= obj.getObject_x()
				&& object_x + object_width <= obj.getObject_x()) {
			return false;
		}
		else if (obj.getObject_x() <= object_x
				&& obj.getObject_x() + obj.getObject_width() <= object_x) {
			return false;
		}
		else if (object_y <= obj.getObject_y()
				&& object_y + object_height <= obj.getObject_y()) {
			return false;
		}
		else if (obj.getObject_y() <= object_y
				&& obj.getObject_y() + obj.getObject_height() <= object_y) {
			return false;
		}
		isAlive = false;
		return true;
	}
}


package com.fish.view;

import java.util.ArrayList;
import java.util.List;

import com.fish.constant.ConstantUtil;
import com.fish.factory.GameObjectFactory;
import com.fish.R;
import com.fish.object.BigFish;
import com.fish.object.BossFish;
import com.fish.object.BulletGoods;
import com.fish.object.EnemyFish;
import com.fish.object.GameObject;
import com.fish.object.MiddleFish;
import com.fish.object.MissileGoods;
import com.fish.object.MyFish;
import com.fish.object.SmallFish;
import com.fish.sounds.GameSoundPool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class MainView extends BaseView{
	private int missileCount;
	private int middlePlaneScore;
	private int bigPlaneScore;
	private int bossPlaneScore;
	private int missileScore;
	private int bulletScore;
	private int sumScore;
	private int speedTime;
	private float bg_y;
	private float bg_y2;
	private float play_bt_w;
	private float play_bt_h;	 
	private float missile_bt_y;		 	
	private boolean isPlay;
	private boolean isTouchPlane;
	private Bitmap background;
	private Bitmap background2;
	private Bitmap playButton;
	private Bitmap missile_bt;
	private MyFish myFish;
	private BossFish bossPlane;
	private List<EnemyFish> enemyPlanes;
	private MissileGoods missileGoods;
	private BulletGoods bulletGoods;
	private GameObjectFactory factory;
	public MainView(Context context,GameSoundPool sounds) {
		super(context,sounds);
		// TODO Auto-generated constructor stub
		isPlay = true;
		speedTime = 1;
		factory = new GameObjectFactory();
		enemyPlanes = new ArrayList<EnemyFish>();
		myFish = (MyFish) factory.createMyPlane(getResources());
		myFish.setMainView(this);
		for(int i = 0; i < SmallFish.sumCount; i++){
			SmallFish smallPlane = (SmallFish) factory.createSmallPlane(getResources());
			enemyPlanes.add(smallPlane);
		}
		for(int i = 0; i < MiddleFish.sumCount; i++){
			MiddleFish middlePlane = (MiddleFish) factory.createMiddlePlane(getResources());
			enemyPlanes.add(middlePlane);
		}
		for(int i = 0; i < BigFish.sumCount; i++){
			BigFish bigPlane = (BigFish) factory.createBigPlane(getResources());
			enemyPlanes.add(bigPlane);
		}
		bossPlane = (BossFish)factory.createBossPlane(getResources());
		bossPlane.setMyPlane(myFish);
		enemyPlanes.add(bossPlane);
		missileGoods = (MissileGoods) factory.createMissileGoods(getResources());
		bulletGoods = (BulletGoods) factory.createBulletGoods(getResources());
		thread = new Thread(this);	
	}
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		super.surfaceChanged(arg0, arg1, arg2, arg3);
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		super.surfaceCreated(arg0);
		initBitmap();
		for(GameObject obj:enemyPlanes){			
			obj.setScreenWH(screen_width,screen_height);
		}
		missileGoods.setScreenWH(screen_width, screen_height);
		bulletGoods.setScreenWH(screen_width, screen_height);
		myFish.setScreenWH(screen_width,screen_height);
		myFish.setAlive(true);
		if(thread.isAlive()){
			thread.start();
		}
		else{
			thread = new Thread(this);
			thread.start();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		super.surfaceDestroyed(arg0);
		release();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_UP){
			isTouchPlane = false;
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_DOWN){
			float x = event.getX();
			float y = event.getY();
			if(x > 10 && x < 10 + play_bt_w && y > 10 && y < 10 + play_bt_h){
				if(isPlay){
					isPlay = false;
				}		
				else{
					isPlay = true;	
					synchronized(thread){
						thread.notify();
					}
				}
				return true;
			}

			else if(x > myFish.getObject_x() && x < myFish.getObject_x() + myFish.getObject_width()
					&& y > myFish.getObject_y() && y < myFish.getObject_y() + myFish.getObject_height()){
				if(isPlay){
					isTouchPlane = true;
				}
				return true;
			}

			else if(x > 10 && x < 10 + missile_bt.getWidth() 
					&& y > missile_bt_y && y < missile_bt_y + missile_bt.getHeight()){
				if(missileCount > 0){
					missileCount--;
					sounds.playSound(5, 0);
					for(EnemyFish pobj:enemyPlanes){
						if(pobj.isCanCollide()){
							pobj.attacked(100);
							if(pobj.isExplosion()){
								addGameScore(pobj.getScore());
							}			
						}
					}	
				}
				return true;
			}
		}

		else if(event.getAction() == MotionEvent.ACTION_MOVE && event.getPointerCount() == 1){
			if(isTouchPlane){
				float x = event.getX();
				float y = event.getY();
				if(x > myFish.getMiddle_x() + 20){
					if(myFish.getMiddle_x() + myFish.getSpeed() <= screen_width){
						myFish.setMiddle_x(myFish.getMiddle_x() + myFish.getSpeed());
					}					
				}
				else if(x < myFish.getMiddle_x() - 20){
					if(myFish.getMiddle_x() - myFish.getSpeed() >= 0){
						myFish.setMiddle_x(myFish.getMiddle_x() - myFish.getSpeed());
					}				
				}
				if(y > myFish.getMiddle_y() + 20){
					if(myFish.getMiddle_y() + myFish.getSpeed() <= screen_height){
						myFish.setMiddle_y(myFish.getMiddle_y() + myFish.getSpeed());
					}		
				}
				else if(y < myFish.getMiddle_y() - 20){
					if(myFish.getMiddle_y() - myFish.getSpeed() >= 0){
						myFish.setMiddle_y(myFish.getMiddle_y() - myFish.getSpeed());
					}
				}
				return true;
			}	
		}
		return false;
	}

	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
		playButton = BitmapFactory.decodeResource(getResources(),R.drawable.play);
		background = BitmapFactory.decodeResource(getResources(), R.drawable.bg_01);
		background2 = BitmapFactory.decodeResource(getResources(), R.drawable.bg_02);
		missile_bt = BitmapFactory.decodeResource(getResources(), R.drawable.missile_bt);
		scalex = screen_width / background.getWidth();
		scaley = screen_height / background.getHeight();
		play_bt_w = playButton.getWidth();
		play_bt_h = playButton.getHeight()/2;
		bg_y = 0;
		bg_y2 = bg_y - screen_height;
		missile_bt_y = screen_height - 10 - missile_bt.getHeight();
	}

	public void initObject(){
		for(EnemyFish obj:enemyPlanes){
			if(obj instanceof SmallFish){
				if(!obj.isAlive()){
					obj.initial(speedTime,0,0);
					break;
				}	
			}
			else if(obj instanceof MiddleFish){
				if(middlePlaneScore > 10000){
					if(!obj.isAlive()){
						obj.initial(speedTime,0,0);
						break;
					}	
				}
			}
			else if(obj instanceof BigFish){
				if(bigPlaneScore >= 25000){
					if(!obj.isAlive()){
						obj.initial(speedTime,0,0);
						break;
					}	
				}
			}
			else{
				if(bossPlaneScore >= 80000){
					if(!obj.isAlive()){
						obj.initial(0,0,0);
						break;
					}
				}
			}
		}
		if(missileScore >= 30000){
			if(!missileGoods.isAlive()){
				missileGoods.initial(0,0,0);
				missileScore = 0;
			}
		}
		if(bulletScore >= 20000){
			if(!bulletGoods.isAlive()){
				bulletGoods.initial(0,0,0);
				bulletScore = 0;
			}
		}
		if(bossPlane.isAlive())
			bossPlane.initButtle();
		myFish.isBulletOverTime();
		myFish.initButtle();
		if(sumScore >= speedTime*100000 && speedTime < 6){
			speedTime++;	
		}
	}
	@Override
	public void release() {
		// TODO Auto-generated method stub
		for(GameObject obj:enemyPlanes){			
			obj.release();
		}
		myFish.release();
		bulletGoods.release();
		missileGoods.release();
		if(!playButton.isRecycled()){
			playButton.recycle();
		}
		if(!background.isRecycled()){
			background.recycle();
		}
		if(!background2.isRecycled()){
			background2.recycle();
		}
		if(!missile_bt.isRecycled()){
			missile_bt.recycle();
		}
	}

	@Override
	public void drawSelf() {
		// TODO Auto-generated method stub
		try {
			canvas = sfh.lockCanvas();
			canvas.drawColor(Color.BLACK);
			canvas.save();
			canvas.scale(scalex, scaley, 0, 0);
			canvas.drawBitmap(background, 0, bg_y, paint);
			canvas.drawBitmap(background2, 0, bg_y2, paint);
			canvas.restore();
			canvas.save();
			canvas.clipRect(10, 10, 10 + play_bt_w,10 + play_bt_h);
			if(isPlay){
				canvas.drawBitmap(playButton, 10, 10, paint);			 
			}
			else{
				canvas.drawBitmap(playButton, 10, 10 - play_bt_h, paint);
			}
			canvas.restore();
			if(missileCount > 0){
				paint.setTextSize(40);
				paint.setColor(Color.BLACK);
				canvas.drawBitmap(missile_bt, 10,missile_bt_y, paint);
				canvas.drawText("X "+String.valueOf(missileCount), 20 + missile_bt.getWidth(), screen_height - 25, paint);
			}
			if(missileGoods.isAlive()){
				if(missileGoods.isCollide(myFish)){
					missileGoods.setAlive(false);
					missileCount++;
					sounds.playSound(6, 0);
				}
				else
					missileGoods.drawSelf(canvas);
			}
			if(bulletGoods.isAlive()){
				if(bulletGoods.isCollide(myFish)){
					bulletGoods.setAlive(false);
					if(!myFish.isChangeBullet()){
						myFish.setChangeBullet(true);
						myFish.changeButtle();
						myFish.setStartTime(System.currentTimeMillis());
					}
					else{
						myFish.setStartTime(System.currentTimeMillis());
					}
					sounds.playSound(6, 0);
				}
				else
					bulletGoods.drawSelf(canvas);
			}
			for(EnemyFish obj:enemyPlanes){
				if(obj.isAlive()){
					obj.drawSelf(canvas);
					if(obj.isCanCollide() && myFish.isAlive()){
						if(obj.isCollide(myFish)){
							myFish.setAlive(false);
						}
					}
				}	
			}
			if(!myFish.isAlive()){
				threadFlag = false;
				sounds.playSound(4, 0);
			}
			myFish.drawSelf(canvas);
			myFish.shoot(canvas,enemyPlanes);
			sounds.playSound(1, 0);
			if(missileCount > 0){
				paint.setTextSize(40);
				paint.setColor(Color.BLACK);
				canvas.drawBitmap(missile_bt, 10,missile_bt_y, paint);
				canvas.drawText("X "+String.valueOf(missileCount), 20 + missile_bt.getWidth(), screen_height - 25, paint);
			}
			paint.setTextSize(30);
			paint.setColor(Color.rgb(235, 161, 1));
			canvas.drawText("总分:"+String.valueOf(sumScore), 30 + play_bt_w, 40, paint);
			canvas.drawText("时间: "+String.valueOf(speedTime), screen_width - 150, 40, paint);
		} catch (Exception err) {
			err.printStackTrace();
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}

	public void viewLogic(){
		if(bg_y > bg_y2){
			bg_y += 10;											
			bg_y2 = bg_y - background.getHeight();
		}
		else{
			bg_y2 += 10;											
			bg_y = bg_y2 - background.getHeight();
		}
		if(bg_y >= background.getHeight()){
			bg_y = bg_y2 - background.getHeight();
		}
		else if(bg_y2 >= background.getHeight()){
			bg_y2 = bg_y - background.getHeight();
		}
	}

	public void addGameScore(int score){
		middlePlaneScore += score;
		bigPlaneScore += score;
		bossPlaneScore += score;
		missileScore += score;
		bulletScore += score;
		sumScore += score;
	}

	public void playSound(int key){
		sounds.playSound(key, 0);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (threadFlag) {	
			long startTime = System.currentTimeMillis();
			initObject();
			drawSelf();	
			viewLogic();
			long endTime = System.currentTimeMillis();	
			if(!isPlay){
				synchronized (thread) {  
				    try {  
				    	thread.wait();  
				    } catch (InterruptedException e) {  
				        e.printStackTrace();  
				    }  
				}  		
			}	
			try {
				if (endTime - startTime < 100)
					Thread.sleep(100 - (endTime - startTime));
			} catch (InterruptedException err) {
				err.printStackTrace();
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Message message = new Message();   
		message.what = 	ConstantUtil.TO_END_VIEW;
		message.arg1 = Integer.valueOf(sumScore);
		mainActivity.getHandler().sendMessage(message);
	}
}

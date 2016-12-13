package com.fish.interfaces;

import java.util.List;

import com.fish.object.EnemyFish;

import android.graphics.Canvas;

public interface IMyFish {
	public float getMiddle_x();
	public void setMiddle_x(float middle_x);
	public float getMiddle_y();
	public void setMiddle_y(float middle_y);
	public boolean isChangeBullet();
	public void setChangeBullet(boolean isChangeBullet);
	public void shoot(Canvas canvas, List<EnemyFish> planes);
	public void initButtle();
	public void changeButtle();
}

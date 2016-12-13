package com.fish.factory;

import android.content.res.Resources;

import com.fish.object.BigFish;
import com.fish.object.BossBullet;
import com.fish.object.BossFish;
import com.fish.object.MyBullet;
import com.fish.object.BulletGoods;
import com.fish.object.GameObject;
import com.fish.object.MiddleFish;
import com.fish.object.MissileGoods;
import com.fish.object.MyBullet2;
import com.fish.object.MyFish;
import com.fish.object.SmallFish;

public class GameObjectFactory {
	public GameObject createSmallPlane(Resources resources){
		return new SmallFish(resources);
	}
	public GameObject createMiddlePlane(Resources resources){
		return new MiddleFish(resources);
	}
	public GameObject createBigPlane(Resources resources){
		return new BigFish(resources);
	}
	public GameObject createBossPlane(Resources resources){
		return new BossFish(resources);
	}
	public GameObject createMyPlane(Resources resources){
		return new MyFish(resources);
	}
	public GameObject createMyBullet(Resources resources){
		return new MyBullet(resources);
	}
	public GameObject createMyBullet2(Resources resources){
		return new MyBullet2(resources);
	}
	public GameObject createBossBullet(Resources resources){
		return new BossBullet(resources);
	}
	public GameObject createMissileGoods(Resources resources){
		return new MissileGoods(resources);
	}
	public GameObject createBulletGoods(Resources resources){
		return new BulletGoods(resources);
	}
}

package com.fish.object;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
/*��Ϸ������*/
abstract public class GameObject {
	protected int currentFrame; 	// ��ǰ����֡
	protected int speed; 			// ������ٶ�
	protected float object_x; 		// ����ĺ�����
	protected float object_y;		// �����������
	protected float object_width; 	// ����Ŀ��
	protected float object_height; 	// ����ĸ߶�
	protected float screen_width; 	// ��Ļ�Ŀ��
	protected float screen_height;  // ��Ļ�ĸ߶�
	protected boolean isAlive;		// �ж��Ƿ���
	protected Paint paint; 			// ���ʶ���
	protected Resources resources;  // ��Դ��
	// ���캯��
	public GameObject(Resources resources) {
		this.resources = resources;
		this.paint = new Paint();
	}
	// ������Ļ��Ⱥ͸߶�
	public void setScreenWH(float screen_width, float screen_height) {
		this.screen_width = screen_width;
		this.screen_height = screen_height;
	}
	// ��ʼ������//�����ֱ�Ϊ:�ٶ����ӵı���,x��������,y��������,
	public void initial(int arg0,float arg1,float arg2){}
	// ��ʼ��ͼƬ��Դ��
	protected abstract void initBitmap();
	// ����Ļ�ͼ����
	public abstract void drawSelf(Canvas canvas);
	// �ͷ���Դ�ķ���
	public abstract void release();
	// �����ײ�ķ���
	public boolean isCollide(GameObject obj) {	
		return true;
	}
	// ������߼�����
	public void logic(){}
	//getter��setter����
	public int getSpeed(){
		return speed;
	}
	public void setSpeed(int speed){
		this.speed = speed;
	}
	public float getObject_x() {
		return object_x;
	}
	public void setObject_x(float object_x) {
		this.object_x = object_x;
	}
	public float getObject_y() {
		return object_y;
	}
	public void setObject_y(float object_y) {
		this.object_y = object_y;
	}
	public float getObject_width() {
		return object_width;
	}
	public void setObject_width(float object_width) {
		this.object_width = object_width;
	}
	public float getObject_height() {
		return object_height;
	}
	public void setObject_height(float object_height) {
		this.object_height = object_height;
	}
	public boolean isAlive() {
		return isAlive;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
}

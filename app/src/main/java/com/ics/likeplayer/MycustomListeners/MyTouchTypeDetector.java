package com.ics.likeplayer.MycustomListeners;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.core.view.GestureDetectorCompat;




public class MyTouchTypeDetector {
    public static final int SCROLL_DIR_UP = 1;
    public static final int SCROLL_DIR_RIGHT = 2;
    public static final int SCROLL_DIR_DOWN = 3;
    public static final int SCROLL_DIR_LEFT = 4;
    public static final int SWIPE_DIR_UP = 5;
    public static final int SWIPE_DIR_RIGHT = 6;
    public static final int SWIPE_DIR_DOWN = 7;
    public static final int SWIPE_DIR_LEFT = 8;
    final MyTouchTypeDetector.a a = new MyTouchTypeDetector.a();
    final GestureDetectorCompat b;
    final MyTouchTypeDetector.TouchTypListener c;

    public MyTouchTypeDetector(Context context, MyTouchTypeDetector.TouchTypListener touchTypListener) {
        this.b = new GestureDetectorCompat(context, this.a);
        this.c = touchTypListener;
    }

    public interface TouchTypListener {
        void onDoubleTap();

        void onLongPress();

        void onScroll(int var1);

        void onSingleTap();

        void onSwipe(int var1);

        void onThreeFingerSingleTap();

        void onTwoFingerSingleTap();
    }

    class a extends GestureDetector.SimpleOnGestureListener {
        a() {
        }

        public final boolean onDoubleTap(MotionEvent e) {
            MyTouchTypeDetector.this.c.onDoubleTap();
            return super.onDoubleTap(e);
        }

        public final boolean onFling(MotionEvent startevent, MotionEvent finishevent, float velocityX, float velocityY) {
            float var5 = finishevent.getX() - startevent.getX();
            float startevent1 = finishevent.getY() - startevent.getY();
            if (Math.abs(var5) > Math.abs(startevent1)) {
                if (Math.abs(var5) > 120.0F && Math.abs(velocityX) > 200.0F) {
                    if (var5 > 0.0F) {
                        MyTouchTypeDetector.this.c.onSwipe(6);
                    } else {
                        MyTouchTypeDetector.this.c.onSwipe(8);
                    }
                }
            } else if (Math.abs(startevent1) > 120.0F && Math.abs(velocityY) > 200.0F) {
                if (startevent1 > 0.0F) {
                    MyTouchTypeDetector.this.c.onSwipe(7);
                } else {
                    MyTouchTypeDetector.this.c.onSwipe(5);
                }
            }

            return false;
        }

        public final void onLongPress(MotionEvent e) {
            MyTouchTypeDetector.this.c.onLongPress();
            super.onLongPress(e);
        }

        public final boolean onScroll(MotionEvent startevent, MotionEvent finishevent, float distanceX, float distanceY) {
            float var5 = finishevent.getX() - startevent.getX();
            float var6 = finishevent.getY() - startevent.getY();
            if (Math.abs(var5) > Math.abs(var6)) {
                if (Math.abs(var5) > 120.0F) {
                    if (var5 > 0.0F) {
                        MyTouchTypeDetector.this.c.onScroll(2);
                    } else {
                        MyTouchTypeDetector.this.c.onScroll(4);
                    }
                }
            } else if (Math.abs(var6) > 120.0F) {
                if (var6 > 0.0F) {
                    MyTouchTypeDetector.this.c.onScroll(3);
                } else {
                    MyTouchTypeDetector.this.c.onScroll(1);
                }
            }

            return super.onScroll(startevent, finishevent, distanceX, distanceY);
        }

        public final boolean onSingleTapConfirmed(MotionEvent e) {
            MyTouchTypeDetector.this.c.onSingleTap();
            return super.onSingleTapConfirmed(e);
        }
    }
}
  
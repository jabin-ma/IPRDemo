package com.jabin.ipr;

import android.app.Service;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.Surface;

public class RenderService extends Service {


    Handler handler = new RenderHandler();

    static class RenderHandler extends Handler {
        Surface surface;
        static final int WHAT_INIT=1<<1;
        static final int WHAT_TEST=1<<2;
        public RenderHandler() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d("mjp",msg.toString());
            switch (msg.what)
            {
                case WHAT_INIT:
                    surface = (Surface) msg.obj;
                    Message tmp=Message.obtain(msg);
                    tmp.what = WHAT_TEST;
                    sendMessage(tmp);
                    break;
                case WHAT_TEST:
                    Canvas canvas = surface.lockCanvas(null);
                    canvas.drawColor(Color.GREEN);
                    Paint paint = new Paint();
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setColor(Color.WHITE);
                    paint.setStrokeWidth(10);
                    canvas.drawRect(new Rect(0, 0, msg.arg1, msg.arg2), paint);
                    surface.unlockCanvasAndPost(canvas);
                    if(msg.arg1 ==0 || msg.arg2 == 0) {
                        break;
                    }
                    sendMessageDelayed(Message.obtain(this,WHAT_TEST,msg.arg1-1,msg.arg2-1),50);
                    break;
                default:
            }
        }

    }

    public RenderService() {
    }



    @Override
    public IBinder onBind(Intent intent) {
        return new Stub().asBinder();
    }

    class Stub extends IRenderService.Stub {

        @Override
        public void onSurfaceTextureAvailable(Surface surface, int width, int height) throws RemoteException {
            Log.d("mjp", "IPR - onSurfaceTextureAvailable begin");
            Message.obtain(handler, RenderHandler.WHAT_INIT, width, height, surface).sendToTarget();
            Log.d("mjp", "IPR - onSurfaceTextureAvailable end");
        }

        @Override
        public void onSurfaceTextureSizeChanged(Surface surface, int width, int height) throws RemoteException {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(Surface surface) throws RemoteException {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(Surface surface) throws RemoteException {

        }
    }
}
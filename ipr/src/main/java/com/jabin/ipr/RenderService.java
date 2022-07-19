package com.jabin.ipr;

import android.app.Service;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Surface;

public class RenderService extends Service {
    public RenderService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new Stub().asBinder();
    }

    class Stub extends IRenderService.Stub{

        @Override
        public void onSurfaceTextureAvailable(Surface surface, int width, int height) throws RemoteException {
            Log.d("mjp","IPR - onSurfaceTextureAvailable begin");
            Canvas canvas = surface.lockCanvas(new Rect(0,0,width,height));
            canvas.drawColor(Color.RED);
            Paint paint= new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(100);
            canvas.drawText("I'm Service ",50,50,paint);
            surface.unlockCanvasAndPost(canvas);
            Log.d("mjp","IPR - onSurfaceTextureAvailable end");
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
package com.jabin.ipr.client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.SurfaceTexture;
import android.os.Bundle;

import com.jabin.ipr.IRenderService;
import com.jabin.ipr.client.databinding.ActivityMainBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;


public class MainActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener, View.OnClickListener {

    private ActivityMainBinding binding;
    private Surface surface;
    private IRenderService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.textureView.setSurfaceTextureListener(this);
        binding.connect.setOnClickListener(this);
    }

    @Override
    public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surfaceTexture, int width, int height) {
        Log.d("mjp","onSurfaceTextureAvailable");
        try {
            service.onSurfaceTextureAvailable(new Surface(surfaceTexture),width,height);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {
        Log.d("mjp","onSurfaceTextureSizeChanged");
    }

    @Override
    public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surfaceTexture) {
        Log.d("mjp","onSurfaceTextureDestroyed");
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surfaceTexture) {
        Log.d("mjp","onSurfaceTextureUpdated");
    }

    @Override
    public void onClick(View view) {
           switch (view.getId()){
        case R.id.connect:
             Intent intent = new Intent();
             intent.setComponent(new ComponentName("com.jabin.ipr.service","com.jabin.ipr.RenderService"));
             bindService(intent, new ServiceConnection() {
                 @Override
                 public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                     binding.textureView.setVisibility(View.VISIBLE);
                     service = IRenderService.Stub.asInterface(iBinder);
                 }

                 @Override
                 public void onServiceDisconnected(ComponentName componentName) {
                     Log.d("mjp","onServiceDisconnected");
                     binding.textureView.setVisibility(View.GONE);
                 }
             }, BIND_AUTO_CREATE);
            break;
               default: break;
           }
    }
}
// IRenderService.aidl
package com.jabin.ipr;



interface IRenderService {

    void onSurfaceTextureAvailable( in Surface surface, in int width, in  int height);
    void onSurfaceTextureSizeChanged( in Surface surface,  in int width,  in int height);
    boolean onSurfaceTextureDestroyed( in Surface surface);
    void onSurfaceTextureUpdated( in Surface surface);
}
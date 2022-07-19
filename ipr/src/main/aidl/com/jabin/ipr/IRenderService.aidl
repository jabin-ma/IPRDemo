// IRenderService.aidl
package com.jabin.ipr;



interface IRenderService {


             void onSurfaceTextureAvailable( in Surface surface, in int width, in  int height);

                 /**
                  * Invoked when the {@link SurfaceTexture}'s buffers size changed.
                  *
                  * @param surface The surface returned by
                  *                {@link android.view.TextureView#getSurfaceTexture()}
                  * @param width The new width of the surface
                  * @param height The new height of the surface
                  */
                 void onSurfaceTextureSizeChanged( in Surface surface,  in int width,  in int height);

                 /**
                  * Invoked when the specified {@link SurfaceTexture} is about to be destroyed.
                  * If returns true, no rendering should happen inside the surface texture after this method
                  * is invoked. If returns false, the client needs to call {@link SurfaceTexture#release()}.
                  * Most applications should return true.
                  *
                  * @param surface The surface about to be destroyed
                  */
                 boolean onSurfaceTextureDestroyed( in Surface surface);

                 /**
                  * Invoked when the specified {@link SurfaceTexture} is updated through
                  * {@link SurfaceTexture#updateTexImage()}.
                  *
                  * @param surface The surface just updated
                  */
                 void onSurfaceTextureUpdated( in Surface surface);
}
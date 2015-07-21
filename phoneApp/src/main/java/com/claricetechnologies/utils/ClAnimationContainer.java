package com.claricetechnologies.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.widget.ImageView;

import java.lang.ref.SoftReference;

public class ClAnimationContainer {
    // single instance procedures
    private static ClAnimationContainer mInstance;
    public int FPS; // animation FPS
    // animation progress indicator frames

    private ClAnimationContainer() {
    }

    public static ClAnimationContainer getInstance() {
        if (mInstance == null)
            mInstance = new ClAnimationContainer();
        return mInstance;
    }

    /**
     * AnimationPlayer. Plays animation frames sequence in loop
     */
    public class FrameSequenceAnimation {
        private int[] mFrames; // animation frames
        private int mIndex; // current frame
        private boolean mIsProgressDialogAnim;
        private boolean mShouldRun; // true if the animation should continue
        // running. Used to stop the animation
        private boolean mIsRunning; // true if the animation currently running.
        // prevents starting the animation twice
        private SoftReference<ImageView> mSoftReferenceImageView; // Used to
        // prevent holding ImageView when it should be dead.
        private Handler mHandler;
        private int mDelayMillis;
        // private OnAnimationStoppedListener mOnAnimationStoppedListener;

        private Bitmap mBitmap = null;
        private BitmapFactory.Options mBitmapOptions;

        public FrameSequenceAnimation(ImageView imageView, int[] frames,
                                      boolean isProgressDialogAnimation) {
            mHandler = new Handler();
            mFrames = frames;
            mIndex = -1;
            mSoftReferenceImageView = new SoftReference<ImageView>(imageView);
            mShouldRun = false;
            mIsRunning = false;
            mIsProgressDialogAnim = isProgressDialogAnimation;
            mDelayMillis = 1000 / FPS;

            imageView.setImageResource(mFrames[0]);

            // use in place bitmap to save GC work (when animation images are
            // the same size & type)
            if (Build.VERSION.SDK_INT >= 11) {
                Bitmap bmp = ((BitmapDrawable) imageView.getDrawable())
                        .getBitmap();
                int width = bmp.getWidth();
                int height = bmp.getHeight();
                Bitmap.Config config = bmp.getConfig();
                mBitmap = Bitmap.createBitmap(width, height, config);
                mBitmapOptions = new BitmapFactory.Options();
                // setup bitmap reuse options.
                mBitmapOptions.inBitmap = mBitmap;
                mBitmapOptions.inMutable = true;
                mBitmapOptions.inSampleSize = 1;
            }
        }

        private int getNext() {
            mIndex++;
            if (mIndex >= mFrames.length) {
//                if (mFrames.length == mProgressIndicatorFrames.length
//                        || mIsProgressDialogAnim) {
//                    mIndex = 0;
//                } else {
//                    mIndex = mFrames.length - 1;
//                }
            }
            return mFrames[mIndex];
        }

        /**
         * Starts the animation
         */
        public synchronized void start() {
            mShouldRun = true;
            mIndex = -1;
            if (mIsRunning)
                return;

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    ImageView imageView = mSoftReferenceImageView.get();
                    if (!mShouldRun || imageView == null) {
                        mIsRunning = false;

                        return;
                    }
                    mIsRunning = true;
                    mHandler.postDelayed(this, mDelayMillis);
                    if (imageView.isShown()) {
                        int imageRes = getNext();
                        if (mBitmap != null) { // so Build.VERSION.SDK_INT >= 11
                            Bitmap bitmap = null;
                            try {
                                bitmap = BitmapFactory.decodeResource(
                                        imageView.getResources(), imageRes,
                                        mBitmapOptions);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (bitmap != null) {
                                imageView.setImageBitmap(bitmap);
                            } else {
                                imageView.setImageResource(imageRes);
                                mBitmap.recycle();
                                mBitmap = null;
                            }
                        } else {
                            imageView.setImageResource(imageRes);
                        }
                    }
                }
            };
            mHandler.post(runnable);
        }

        /**
         * Stops the animation
         */
        public synchronized void stop() {
            mShouldRun = false;
        }
    }
}

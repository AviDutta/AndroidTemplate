package com.claricetechnologies.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;

import com.claricetechnologies.R;

import java.io.InputStream;

public class ClGifAnimationView extends View {
    public Movie mMovie;
    public long movieStart;

    public ClGifAnimationView(Context context) {
        super(context);
        initializeView();
    }

    public ClGifAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();
    }

    public ClGifAnimationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeView();
    }

    private void initializeView() {
        InputStream is = getContext().getResources().openRawResource(R.raw.splash_screen);
        mMovie = Movie.decodeStream(is);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        super.onDraw(canvas);
        long now = android.os.SystemClock.uptimeMillis();
        if (movieStart == 0) {
            movieStart = now;
        }
        if (mMovie != null) {
            int relTime = (int) ((now - movieStart) % mMovie.duration());
            mMovie.setTime(relTime);
            mMovie.draw(canvas, getWidth() / 4, getHeight() / 6);
            this.invalidate();
        }
    }
}

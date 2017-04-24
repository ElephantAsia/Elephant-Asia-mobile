package fr.elephantasia.elephantasia.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import fr.elephantasia.elephantasia.R;

public class RoundedImageView extends AppCompatImageView {

    private float radius;
    private RectF rect;
    private Path clipPath;

    public RoundedImageView(Context context) {
        super(context);
        init();
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(context, attrs);

    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundedImageView);
            if (a.hasValue(R.styleable.RoundedImageView_radius)) {
                radius = a.getDimension(R.styleable.RoundedImageView_radius, 0.0f);
            }
            a.recycle();
        }
        init();
    }

    private void init() {
        clipPath = new Path();
        rect = new RectF();
    }

    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        rect.set(0, 0, this.getWidth(), this.getHeight());
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }
}

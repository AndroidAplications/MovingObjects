package androidaplications.pablolaiz.movingobjects;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class TouchActivity extends Activity {

    private ViewGroup mainLayout;
    private ImageView image;

    private int xDelta;
    private int yDelta;

    private boolean updateCoord = true;
    private int _x;
    private int _y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
        mainLayout = (RelativeLayout) findViewById(R.id.main);
        image = (ImageView) findViewById(R.id.image);

        image.setOnTouchListener(onTouchListener());
    }

    private OnTouchListener onTouchListener() {
        return new OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();
                if (updateCoord){
                    _x = x;
                    _y = y;
                    updateCoord = !updateCoord;
                }
                /*
                Log.i("onTouchListener", "Coordinates: ");
                Log.i("onTouchListener", "x: " + x);
                Log.i("onTouchListener", "y: " + y);
                */
                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        //Log.i("ACTION_DOWN", "In ACTION_DOWN");
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();

                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        //Log.i("ACTION_DOWN", "xDelta: " + xDelta);
                        //Log.i("ACTION_DOWN", "yDelta: " + yDelta);
                        break;

                    case MotionEvent.ACTION_UP:
                        //Log.i("ACTION_UP", "In ACTION_UP");
                        Toast.makeText(TouchActivity.this,
                                "Everybody love Pablo!", Toast.LENGTH_SHORT)
                                .show();

                        mainLayout = (RelativeLayout) findViewById(R.id.main);
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        params.leftMargin = _x;
                        params.topMargin = _y;
                        ImageView myImage = new ImageView(getApplicationContext());
                        myImage.setImageResource(R.mipmap.ic_launcher);
                        myImage.setOnTouchListener(onTouchListener());
                        mainLayout.addView(myImage, params);

                        updateCoord = !updateCoord;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        //Log.i("ACTION_MOVE", "In ACTION_MOVE");
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);

                        /**/
                        break;
                }
                mainLayout.invalidate();
                return true;
            }
        };
    }
}

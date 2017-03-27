package student.sdu.hearingscreening.activities;

import android.content.Intent;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import student.sdu.hearingscreening.R;

public class HowToActivity extends AppCompatActivity {
    private float x1;
    private float x2;
    private static final int DISTANCE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);

        ImageButton btn = (ImageButton) findViewById(R.id.ib_swipe_right);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent mainIntent = new Intent(getApplicationContext(), HowTo2Activity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                HowToActivity.this.startActivity(mainIntent);
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out2);
                HowToActivity.this.finish();
            }
        });

        btn = (ImageButton) findViewById(R.id.ib_swipe_left);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent mainIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                HowToActivity.this.startActivity(mainIntent);
                overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in2);
                HowToActivity.this.finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent mainIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        HowToActivity.this.startActivity(mainIntent);
        overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in);
        HowToActivity.this.finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;

                if (Math.abs(deltaX) > DISTANCE)
                {
                    // Left to Right swipe action
                    if (x2 > x1)
                    {
                        Intent mainIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        HowToActivity.this.startActivity(mainIntent);
                        overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in2);
                        HowToActivity.this.finish();
                    }

                    // Right to left swipe action
                    else
                    {
                        Intent mainIntent = new Intent(getApplicationContext(), HowTo2Activity.class);
                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        HowToActivity.this.startActivity(mainIntent);
                        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out2);
                        HowToActivity.this.finish();
                    }

                }
                else
                {
                    // consider as something else - a screen tap for example
                }
                break;
        }
        return super.onTouchEvent(event);
    }

}

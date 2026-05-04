package ma.ensa.labthreadsasynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.labthreadsasynctask.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private TextView txtStatus;
    private ProgressBar progressBar;
    private ImageView img;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtStatus = findViewById(R.id.txtStatus);
        progressBar = findViewById(R.id.progressBar);
        img = findViewById(R.id.img);

        findViewById(R.id.btnToast).setOnClickListener(v ->
                Toast.makeText(this, R.string.btn_test_toast, Toast.LENGTH_SHORT).show());

        findViewById(R.id.btnLoadThread).setOnClickListener(v -> loadImage());
        findViewById(R.id.btnCalcAsync).setOnClickListener(v -> startCalculation());
    }

    private void loadImage() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        txtStatus.setText(R.string.status_loading_thread);

        new Thread(() -> {
            try {
                Thread.sleep(2000);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                mainHandler.post(() -> {
                    img.setImageBitmap(bitmap);
                    progressBar.setVisibility(View.INVISIBLE);
                    txtStatus.setText(R.string.status_image_loaded);
                });
            } catch (InterruptedException e) {
                Log.e("Lab", "Erreur thread", e);
            }
        }).start();
    }

    private void startCalculation() {
        txtStatus.setText(R.string.status_calc_running);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(false);

        executorService.execute(() -> {
            long res = 0;
            for (int i = 1; i <= 100; i++) {
                for (int k = 0; k < 500000; k++) res += (i * k) % 7;
                int p = i;
                mainHandler.post(() -> progressBar.setProgress(p));
            }
            long finalRes = res;
            mainHandler.post(() -> {
                progressBar.setVisibility(View.INVISIBLE);
                txtStatus.setText(getString(R.string.status_calc_result, finalRes));
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdownNow();
    }
}
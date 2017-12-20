package edu.swarthmore.cs.cs71.shelved.shelved;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScannerActivity extends Activity implements ZBarScannerView.ResultHandler {
    private static final String TAG = "ScannerActivity";
    private ZBarScannerView mScannerView;
    private static String ISBN;

    @Override
    public void onCreate(Bundle state) {
        Log.d(TAG, "inside onCreate for scanner activity");
        super.onCreate(state);
        setContentView(R.layout.activity_scanner);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA}, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mScannerView != null) {
            mScannerView.setResultHandler(this);
            mScannerView.startCamera();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mScannerView != null) {
            mScannerView.stopCamera();
            mScannerView.stopCameraPreview();
            mScannerView = null;
        }
    }

    @Override
    public void handleResult(Result rawResult) {
        Toast.makeText(this, "Contents = " + rawResult.getContents() +
                ", Format = " + rawResult.getBarcodeFormat().getName(), Toast.LENGTH_SHORT).show();
        ISBN = rawResult.getContents();
        //https://developer.android.com/training/basics/intents/result.html
        //https://stackoverflow.com/questions/14785806/android-how-to-make-an-activity-return-results-to-the-activity-which-calls-it
        setResult(RESULT_OK, getIntent());

        Log.d(TAG, "past setting isbn");
        Log.d(TAG, ISBN);

        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //mScannerView.resumeCameraPreview(ScannerActivity.this);
                mScannerView.stopCamera();
                finish();
            }
        }, 2000);

    }

    public static String getISBN() {
        return ISBN;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            ViewGroup contentFrame = (ViewGroup) findViewById(R.id.scannerLayout);
            mScannerView = new ZBarScannerView(this);
            contentFrame.addView(mScannerView);
            mScannerView.setResultHandler(this);
            mScannerView.startCamera();
        } else {
            Toast.makeText(this, "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
        }
    }
}

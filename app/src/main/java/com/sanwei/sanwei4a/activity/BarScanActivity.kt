package com.sanwei.sanwei4a.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.ViewGroup
import android.view.WindowManager
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.jetbrains.anko.find
import com.sanwei.sanwei4a.R
import org.jetbrains.anko.toast

class BarScanActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private lateinit var mScannerView: ZXingScannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_scan)
        //不弹出软键盘
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        val contentFrame = find<ViewGroup>(R.id.z_frame_scan)
        mScannerView = ZXingScannerView(this)
        contentFrame.addView(mScannerView)
    }

    override fun handleResult(rawResult: Result?) {
        Log.d("TAG", "Contents = " + rawResult!!.text +
                ", Format = " + rawResult.barcodeFormat.toString())
        if (rawResult.barcodeFormat == BarcodeFormat.EAN_13) {
            val intent = Intent()
            intent.putExtra("isbn", rawResult.text)
            intent.putExtra("format", rawResult.barcodeFormat.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        } else {
            toast("您所扫描的不是图书ISBN号")
            Handler().postDelayed({
                mScannerView.resumeCameraPreview(this@BarScanActivity)
            }, 2000)
        }
    }

    override fun onResume() {
        super.onResume()
        mScannerView.setResultHandler(this)
        mScannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()
    }

}

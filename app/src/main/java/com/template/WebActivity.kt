package com.template

import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.*
import com.template.databinding.ActivityWebBinding

class WebActivity : Activity() {


    private lateinit var binding: ActivityWebBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView.apply {
            loadUrl(intent.getStringExtra("url").toString())
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    CookieManager.getInstance().flush()
                    val cookie = CookieManager.getInstance().getCookie(url)
                    CookieManager.getInstance().setCookie(url, cookie)

                }
                override fun onPageFinished(view: WebView?, url: String?) {
                    CookieManager.getInstance().flush()
                    CookieManager.getInstance().getCookie(url)


                }
            }
            settings.javaScriptEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.domStorageEnabled = true
            settings.allowContentAccess = true
            settings.allowFileAccess = true
            CookieManager.getInstance().setAcceptCookie(true)
            settings.mediaPlaybackRequiresUserGesture = false
            webChromeClient = WebChromeClient()

        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {

        if (binding.webView.canGoForward()) {
            binding.webView.goForward()
            Log.d("Go", "GoBackForward")
        } else if (binding.webView.canGoBack()) {
           binding.webView.goBack()
            Log.d("Go", "okGoBack")
        } else{
             Log.d("Go", "noGoBack")

             }

    }
}

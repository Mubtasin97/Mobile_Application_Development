package com.example.learningportalapp

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.webkit.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var etUrl: EditText
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        etUrl = findViewById(R.id.etUrl)
        progressBar = findViewById(R.id.progressBar)

        setupWebView()
        setupButtons()
        loadHomePage()
    }

    private fun setupWebView() {
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = MyWebViewClient()
        webView.webChromeClient = MyWebChromeClient()
    }

    private fun setupButtons() {
        findViewById<Button>(R.id.btnBack).setOnClickListener {
            if (webView.canGoBack()) webView.goBack() else Toast.makeText(this, "No more history", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnForward).setOnClickListener {
            if (webView.canGoForward()) webView.goForward()
        }

        findViewById<Button>(R.id.btnRefresh).setOnClickListener { webView.reload() }

        findViewById<Button>(R.id.btnHome).setOnClickListener { loadHomePage() }

        findViewById<Button>(R.id.btnGo).setOnClickListener { loadUrl() }

        etUrl.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loadUrl()
                true
            } else false
        }

        findViewById<Button>(R.id.btnGoogle).setOnClickListener { webView.loadUrl("https://www.google.com") }
        findViewById<Button>(R.id.btnYouTube).setOnClickListener { webView.loadUrl("https://www.youtube.com") }
        findViewById<Button>(R.id.btnWikipedia).setOnClickListener { webView.loadUrl("https://www.wikipedia.org") }
        findViewById<Button>(R.id.btnKhan).setOnClickListener { webView.loadUrl("https://www.khanacademy.org") }
        findViewById<Button>(R.id.btnUniversity).setOnClickListener { webView.loadUrl("https://www.google.com") }
    }

    private fun loadHomePage() {
        webView.loadUrl("https://www.google.com")
    }

    private fun loadUrl() {
        var url = etUrl.text.toString().trim()
        if (url.isEmpty()) url = "https://www.google.com"
        if (!url.startsWith("http")) url = "https://$url"
        webView.loadUrl(url)
    }

    private inner class MyWebViewClient : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
            progressBar.visibility = android.view.View.VISIBLE
            etUrl.setText(url)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            progressBar.visibility = android.view.View.GONE
        }

        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            webView.loadUrl("file:///android_asset/offline.html")
        }
    }

    private inner class MyWebChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            progressBar.progress = newProgress
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) webView.goBack() else super.onBackPressed()
    }
}
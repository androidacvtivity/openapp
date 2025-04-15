package com.bhuvaneshw.pdfviewer

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream

class PdfJsViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView = WebView(this)
        setContentView(webView)

        val pdfFileName = intent.getStringExtra("pdf_file") ?: "Codul_de_etică.pdf"

        // 1. Copie fișierul PDF din assets în cacheDir
        val pdfFile = File(cacheDir, pdfFileName)
        if (!pdfFile.exists()) {
            assets.open(pdfFileName).use { input ->
                FileOutputStream(pdfFile).use { output ->
                    input.copyTo(output)
                }
            }
        }

        // 2. Construiește URL pentru viewer.html
        val localPdfPath = "file://${pdfFile.absolutePath}"
        val viewerUrl = "file:///android_asset/pdfjs/web/viewer.html?file=$localPdfPath"

        // 3. Setări WebView
        val settings: WebSettings = webView.settings
        settings.javaScriptEnabled = true
        settings.allowFileAccess = true
        settings.domStorageEnabled = true
        settings.allowFileAccessFromFileURLs = true
        settings.allowUniversalAccessFromFileURLs = true

        webView.loadUrl(viewerUrl)
    }
}

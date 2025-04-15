package com.bhuvaneshw.pdfviewer

import android.R
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.webkit.URLUtil
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bhuvaneshw.pdfviewer.PdfJsViewerActivity
import com.bhuvaneshw.pdfviewer.databinding.ActivityMainBinding
import com.bhuvaneshw.pdfviewer.databinding.UrlDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {

    private lateinit var view: ActivityMainBinding
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()





        pref = getSharedPreferences("pref", MODE_PRIVATE)

        view = ActivityMainBinding.inflate(layoutInflater)
        setContentView(view.root)




    //Asa se deschide interfata dar nu este continut
        view.fromAssetPdfjs.setOnClickListener {
            startActivity(
                Intent(this, PdfJsViewerActivity::class.java).apply {
                    putExtra("pdf_file", "Codul_de_etică.pdf")
                }
            )
        }

//Daca se declara asa  R.id.fromAssetDoc - nu vede
        view.fromAssetDoc.setOnClickListener {
            openDocFromAssets("Regulament.doc") // sau .docx
        }

//aici se deschide - este unul si acelas fisier
        view.fromAsset.setOnClickListener {
            startActivity(
                Intent(this, getViewerActivityClass()).apply {
                    putExtra("fileName", "Codul de etică")
                    putExtra("fileSize", 271804L)
                    putExtra("filePath", "asset://Codul_de_etică.pdf")
                }
            )
        }

        view.fromUrl.setOnClickListener {
            startActivity(
                Intent(this, getViewerActivityClass()).apply {
                    putExtra("fileName", "Regulament financiar 2024")
                    putExtra("fileSize", 271804L)
                    putExtra("filePath", "asset://Regulament_Directia_management financiar_2024.pdf")
                }
            )
        }

        val openLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            result.data?.data?.let { uri ->
                startActivity(
                    Intent(this, getViewerActivityClass()).apply {
                        putExtra("fileUri", uri.toString())
                    }
                )
            }
        }
        view.fromStorage.setOnClickListener {
            openLauncher.launch(
                Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "application/pdf"
                }
            )
        }

//        view.fromUrl.setOnClickListener {
//            promptUrl { url ->
//                startActivity(
//                    Intent(this, getViewerActivityClass()).apply {
//                        putExtra("fileUrl", url)
//                    }
//                )
//            }
        }


    private fun openDocFromAssets(fileName: String) {
        val inputStream = assets.open(fileName)
        val file = File(cacheDir, fileName)
        FileOutputStream(file).use { output ->
            inputStream.copyTo(output)
        }

        val uri = FileProvider.getUriForFile(
            this,
            "${packageName}.fileprovider",
            file
        )

        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, "application/msword")
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Nicio aplicație disponibilă pentru fișiere DOC", Toast.LENGTH_SHORT).show()
        }
    }


    private fun getViewerActivityClass(): Class<*> {
        val useCompose = pref.getBoolean("use_compose", false)

        return if (useCompose) ComposePdfViewerActivity::class.java
        else PdfViewerActivity::class.java
    }

    private fun promptUrl(callback: (String) -> Unit) {
        val view = UrlDialogBinding.inflate(layoutInflater)

        MaterialAlertDialogBuilder(this)
            .setTitle("Enter Pdf Url")
            .setView(view.root)
            .setPositiveButton("Load") { dialog, _ ->
                dialog.dismiss()
                val url = view.field.text.toString()
                if (URLUtil.isValidUrl(url)) callback(url)
                else toast("Enter valid url!")
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        if (intent.action == Intent.ACTION_VIEW && intent.data != null) {
            startActivity(
                Intent(this, getViewerActivityClass()).apply {
                    putExtra("fileUri", intent.data.toString())
                }
            )
        }
    }
}

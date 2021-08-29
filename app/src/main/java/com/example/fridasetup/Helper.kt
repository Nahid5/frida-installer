package com.example.fridasetup

import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.net.URL


class Helper {
    fun download(link: String, path: String) {
        val thread = Thread {
            try {
                URL(link).openStream().use { input ->
                    FileOutputStream(File(path)).use { output ->
                        input.copyTo(output)
                    }
                }
            } catch (ex: Exception) {
                Log.e("ERROR DOWNLOADING:", ex.toString())
                ex.printStackTrace();
            }
        }
        thread.start();
    }
}
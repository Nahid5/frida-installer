package com.example.fridasetup

import android.os.Build
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.util.*

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

    fun download2 () {

    }

    fun downloadFrida() {
        val phoneVersion = Arrays.toString(Build.SUPPORTED_ABIS)
        val arch = System.getProperty("os.arch")
        download("https://api.github.com/repos/frida/frida/releases/latest", "/sdcard/Download/FRIDA_TMP.txt")
        //Log.e("BUILD", System.getProperty("os.arch"))
        //Log.e("BUILD", Arrays.toString(Build.SUPPORTED_ABIS))
        //Log.e("BUILD", Arrays.toString(Build.SUPPORTED_32_BIT_ABIS))
        //Log.e("BUILD", Arrays.toString(Build.SUPPORTED_64_BIT_ABIS))
        val rh = RootHelper()
        if ("arm" in phoneVersion) {
            if("64" in arch) {
                rh.runAsRoot2("cat /sdcard/Download/FRIDA_TMP.txt | grep browser_download_url | grep server | grep android | grep arm64 | cut -d '\"' -f 4 > /sdcard/Download/FRIDA_DL.txt")
                val loc = File("/sdcard/Download/FRIDA_DL.txt").bufferedReader().readLines().toString()
                Log.i("FRIDA LOCATION:", loc)
                download(loc, "/sdcard/Download/frida..xz")
            }
        }
        //download("https://api.github.com/repos/frida/frida/releases/latest", "/sdcard/Download/FRIDA_TMP.txt")
        //rh.runAsRoot2("cat /sdcard/Download/FRIDA_TMP.txt | grep browser_download_url ")
        /*
        val link = "https://github.com/frida/frida/releases"
        val path = "/sdcard/Downloads"

        val thread = Thread {
            try {
                URL(link).openStream().use { input ->
                    FileOutputStream(File(path)).use { output ->
                        //input.copyTo(output)
                        val reader = BufferedReader(input.reader())
                        var content: String
                        content = reader.readText()
                        reader.close()
                        //Log.e("OUTS:", content)
                        rh.runAsRoot2("ls")
                    }
                }
            } catch (ex: Exception) {
                Log.e("ERROR DOWNLOADING:", ex.toString())
                ex.printStackTrace();
            }
        }
        thread.start();
         */
    }
}
package com.example.fridasetup

import android.os.Build
import android.util.Log
import java.io.*

class RootHelper {
    fun runAsRoot2(cmds: String) {
        /*
        * cmds: String of commands to be executed as root
        *
        * Executes command and prints to logcat
         */
        var line: String
        var process = Runtime.getRuntime().exec("su")
        var stdin: OutputStream = process.outputStream
        var stderr: InputStream = process.errorStream
        var stdout: InputStream = process.inputStream

        // Add new line to cut off the command
        var cmdToExecute = cmds + "\n"
        stdin.write(cmdToExecute.toByteArray())
        stdin.write("exit\n".toByteArray())     // Exit the shell window
        stdin.flush()
        stdin.close()

        /****************************** Print stdout ***************************/
        var br = BufferedReader(InputStreamReader(stdout))
        try {
            line = br.readLine()
            while (line != null) {
                //println("[Output] " + line)
                Log.i("STDOUT: ",line)
                line = br.readLine()
            }
        } catch (ex: Exception) {
            Log.i("CONSOLE ERR: ",ex.toString())
        }
        br.close()

        /****************************** Print stderr ***************************/
        br = BufferedReader(InputStreamReader(stderr))
        try {
            line = br.readLine()
            while (line != null) {
                Log.i("STDOUT: ",line)
                line = br.readLine()
            }
        } catch (ex: Exception) {
            Log.i("CONSOLE ERR: ",ex.toString())
        }
        br.close()

        process.waitFor()
        process.destroy()
    }

    fun runAsRoot(cmds: Array<String>) {
        /*
        * cmds: Array of commands to be executed as root
        *
        * Just executes the command, and no output to anything
         */
        val p = Runtime.getRuntime().exec("su")
        val os = DataOutputStream(p.outputStream)
        for (tmpCmd in cmds) {
            os.writeBytes(
                """
                $tmpCmd
                """.trimIndent()
            )
        }
        os.writeBytes("exit\n")
        os.flush()
    }

    fun isDeviceRooted(): Boolean {
        //return checkRootMethod1() || checkRootMethod2() || checkRootMethod3()
        return checkRootMethod3()
    }

    private fun checkRootMethod1(): Boolean {
        val buildTags = Build.TAGS
        return buildTags != null && buildTags.contains("test-keys")
    }

    private fun checkRootMethod2(): Boolean {
        val paths = arrayOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su",
            "/su/bin/su"
        )
        for (path in paths) {
            if (File(path).exists()) return true
        }
        return false
    }

    private fun checkRootMethod3(): Boolean {
        var process: Process? = null
        return try {
            process = Runtime.getRuntime().exec( "su")
            val ina = BufferedReader(InputStreamReader(process.inputStream))
            if (ina.readLine() != null) return true else return false
        } catch (t: Throwable) {
            return false
        } finally {
            if (process != null) process.destroy();
        }
    }
}
package com.example.fridasetup

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
}
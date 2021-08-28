package com.example.fridasetup

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.io.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val cmds: Array<String> = arrayOf("touch /sdcard/Download/and_std.txt\n")
        //runAsRoot(cmds)
        /***************** Star Server **********************/
        val startServerButton: Button = findViewById(R.id.startServerButton)
        startServerButton.setOnClickListener {
            //val toast = Toast.makeText(this, "Dice Rolled!", Toast.LENGTH_SHORT)
            //toast.show()
            var cmd: String = "pwd"
            runAsRoot2(cmd)
            cmd = "ls"
            runAsRoot2(cmd)
        }
        /***************** Stop Server **********************/
        /***************** Restart Server Server **********************/
        /***************** Download Latest Server **********************/
        val downloadServerButton: Button = findViewById(R.id.downloadServerButton)
        downloadServerButton.setOnClickListener {
            //val toast = Toast.makeText(this, "Dice Rolled!", Toast.LENGTH_SHORT)
            //toast.show()
            val cmd: String = "ls -la /"
            runAsRoot2(cmd)
        }
    }

    fun runAsRoot2(cmds: String) {
        /*
        * cmds: String of commands to be executed as root
        *
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
package com.example.fridasetup

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rootExec = RootHelper()
        val helperFunc = Helper()

        // Storing data into SharedPreferences
        var sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        // Creating an Editor object to edit(write to the file)
        var myEdit = sharedPreferences.edit()
        // Storing the key and its value as the data fetched from edittext
        myEdit.putString("name", "Test Name")
        myEdit.putInt("age", 12)
        // Once the changes have been made,
        // we need to commit to apply those changes made,
        // otherwise, it will throw an error
        myEdit.commit()


        //val cmds: Array<String> = arrayOf("touch /sdcard/Download/and_std.txt\n")
        //runAsRoot(cmds)
        /***************** Star Server **********************/
        val startServerButton: Button = findViewById(R.id.startServerButton)
        startServerButton.setOnClickListener {
            //val toast = Toast.makeText(this, "Dice Rolled!", Toast.LENGTH_SHORT)
            //toast.show()
            var cmd: String = "pwd"
            rootExec.runAsRoot2(cmd)
            //cmd = "touch /sdcard/Download/andhthasd.txt; touch /sdcard/Download/andhthasd2.txt"
            //rootExec.runAsRoot2(cmd)
        }
        /***************** Stop Server **********************/
        /***************** Restart Server Server **********************/
        /***************** Download Latest Server **********************/
        val downloadServerButton: Button = findViewById(R.id.downloadServerButton)
        downloadServerButton.setOnClickListener {
            //val toast = Toast.makeText(this, "Dice Rolled!", Toast.LENGTH_SHORT)
            //toast.show()
            helperFunc.downloadFrida()
            //val cmd: String = "cd /data/local/tmp/; touch a.txt"
            //rootExec.runAsRoot2(cmd)
        }
    }

}
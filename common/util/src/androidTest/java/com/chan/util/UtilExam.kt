package com.chan.util

import androidx.appcompat.app.AppCompatActivity

class UtilExam : AppCompatActivity() {

    private val register = register {
        println("Activity Result....")
    }

    private val registerResult = registerResult(
        1000,
    ) {
        println("Activity Result....")
    }

    fun registerExam() {
        register.launch(
            createIntent(
                "com.chan.main.ui.MainActivity"
            )
        )
    }

    fun registerResultExam() {
        registerResult.launch(
            createIntent(
                "com.chan.main.ui.MainActivity",
                "storeId" to "1001"
            )
        )
    }

    fun startActivityExam() {
        startActivity(
            "com.chan.main.ui.MainActivity",
            "storeId" to "1001"
        )
    }
}
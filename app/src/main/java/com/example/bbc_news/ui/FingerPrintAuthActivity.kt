package com.example.bbc_news.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.bbc_news.R

class FingerPrintAuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finger_print_auth)

        checkBiometricSupportedOrEnrolled()

    }

    /**
     * check either fingerprint enrolled or not,
     * start Authentication dialog if its  enrolled otherwise start MainActivity
     */
    private fun checkBiometricSupportedOrEnrolled(){
        try {
            val biometricManager = BiometricManager.from(this)
            when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG )) {
                BiometricManager.BIOMETRIC_SUCCESS ->{
                    Log.d("MY_APP_TAG", "App can authenticate using biometrics.")
                    showBiometricPrompt()
                }
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE,
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE,
                BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED,
                BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED,
                BiometricManager.BIOMETRIC_STATUS_UNKNOWN,
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    Log.e("MY_APP_TAG", "Biometric features not enrolled")
                    startMainActivity()

                }
            }
        }catch (e: Exception){
            Toast.makeText(this@FingerPrintAuthActivity, "unable to authenticate!", Toast.LENGTH_SHORT
            ).show()
            e.printStackTrace()
        }
    }

    /**
     *this method will display instruction to use fingerprint sensor for authentication
     */
    private fun showBiometricPrompt() {
        try {
            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Authenticate with your fingerprint")
                .setSubtitle("Place your finger on the sensor")
                .setNegativeButtonText("Cancel")
                .build()

            val biometricPrompt = BiometricPrompt(
                this,
                ContextCompat.getMainExecutor(this),
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        // Fingerprint authentication successful
                        Log.v("finger auth", "successfull")
                        startMainActivity()


                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        Toast.makeText(
                            this@FingerPrintAuthActivity,
                            "Authentication error: $errString",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Toast.makeText(
                            this@FingerPrintAuthActivity,
                            "Fingerprint authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )

            biometricPrompt.authenticate(promptInfo)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    /**
     * MainActivity will calll in case of successful authentication
     * Finish the current activity to prevent going back to it with the back button
     */
    private fun startMainActivity() {
        val intent = Intent(this, MainActivity ::class.java)
        startActivity(intent)
        finish()
    }

    }

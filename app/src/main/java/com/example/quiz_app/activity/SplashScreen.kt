package com.example.quiz_app.activity

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import com.example.quiz_app.R

class SplashScreen : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        progressBar = findViewById(R.id.progressBar)

        // Start the progress bar animation
        startProgressBarAnimation()

        // Simulate a long-running task (network call, data loading, etc.)
        // Here, we are using a Handler to delay the transition to the next activity
        Handler().postDelayed({
            navigateToNextActivity()
        }, SPLASH_DELAY)
    }

    private fun startProgressBarAnimation() {
        val progressAnimation = ObjectAnimator.ofInt(progressBar, "progress", 0, 100)
        progressAnimation.duration = SPLASH_DELAY
        progressAnimation.interpolator = DecelerateInterpolator()
        progressAnimation.start()
    }

    private fun navigateToNextActivity() {
        val intent = Intent(this, AppIntroActivity::class.java)
        startActivity(intent)
        finish() // Close the splash activity so it's not accessible from the back stack
    }

    companion object {
        private const val SPLASH_DELAY = 2000L // Splash screen delay in milliseconds (3 seconds)
    }
}
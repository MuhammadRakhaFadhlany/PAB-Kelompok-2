package id.ac.stmi.pabkel2
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
class SplashActivity : AppCompatActivity() {
 override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); setContentView(R.layout.activity_splash)
 findViewById<Button>(R.id.btnStart).setOnClickListener { startActivity(Intent(this,LoginActivity::class.java)); finish() }
 }
}

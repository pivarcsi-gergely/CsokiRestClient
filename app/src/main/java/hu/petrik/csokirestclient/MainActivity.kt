package hu.petrik.csokirestclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import hu.petrik.csokirestclient.databinding.ActivityMainBinding
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.adatok.movementMethod = ScrollingMovementMethod.getInstance()
        var requestTask = RequestTask()
        requestTask.execute()
        /*try {
            binding.adatok.text = RequestHandler.get("https://retoolapi.dev/GIl55L/Csokoladek").toString()
        }
        catch (e: IOException) {
            e.stackTrace
        }*/

    }

}
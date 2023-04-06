package com.zebdul.flashlight

import android.content.Context
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.zebdul.flashlight.databinding.ActivityMainBinding
import java.security.Permission

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var torchState:Boolean = false
    private lateinit var cameraManager:CameraManager
    private var camId : String="0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        camId = cameraManager.cameraIdList[0]

        Dexter.withContext(this).withPermission(android.Manifest.permission.CAMERA).withListener(object:PermissionListener{
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                turnOnFlashLIght()
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                Toast.makeText(this@MainActivity, "Please Grant Permission",
                    Toast.LENGTH_SHORT).show()
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: PermissionRequest?,
                p1: PermissionToken?
            ) {
                p1?.continuePermissionRequest()
            }

        }).check()

        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        camId = cameraManager.cameraIdList[0]





    }

    private fun turnOnFlashLIght() {
       binding.imgTorch.setOnClickListener {
           torchState = when(torchState){
               false->{
                   cameraManager.setTorchMode(camId, true)
                   binding.imgTorch.setImageResource(R.drawable.flashlight_on)
                   true
               }
               true -> {
                   cameraManager.setTorchMode(camId, false)
                   binding.imgTorch.setImageResource(R.drawable.flashlight_off)
                   false
               }
           }
       }
    }
}
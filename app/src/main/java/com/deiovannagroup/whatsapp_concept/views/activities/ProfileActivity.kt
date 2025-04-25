package com.deiovannagroup.whatsapp_concept.views.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.deiovannagroup.whatsapp_concept.R
import com.deiovannagroup.whatsapp_concept.databinding.ActivityProfileBinding
import com.deiovannagroup.whatsapp_concept.utils.showMessage

class ProfileActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val hasCamera = permissions[Manifest.permission.CAMERA] == true
        val hasGallery = permissions[getGalleryPermission()] == true

        if (!hasCamera || !hasGallery) {
            showMessage("Permissions not granted")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEdgeToEdgeLayout()
        initToolbar()
        requestPermissionsIfNecessary()
    }

    private fun requestPermissionsIfNecessary() {
        val requiredPermissions = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requiredPermissions.add(Manifest.permission.CAMERA)
        }

        val galleryPermission = getGalleryPermission()
        if (ContextCompat.checkSelfPermission(this, galleryPermission)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requiredPermissions.add(galleryPermission)
        }

        if (requiredPermissions.isNotEmpty()) {
            permissionLauncher.launch(requiredPermissions.toTypedArray())
        }
    }

    private fun getGalleryPermission(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
    }

    private fun initToolbar() {
        val toolbar = binding.includeToolbarProfile.tbMain
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(R.string.edit_profile)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setEdgeToEdgeLayout() {
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }
    }
}

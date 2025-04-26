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
import androidx.lifecycle.ViewModelProvider
import com.deiovannagroup.whatsapp_concept.R
import com.deiovannagroup.whatsapp_concept.databinding.ActivityProfileBinding
import com.deiovannagroup.whatsapp_concept.utils.showMessage
import com.deiovannagroup.whatsapp_concept.viewmodels.ProfileViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    private val profileViewModel by lazy {
        ViewModelProvider(this)[ProfileViewModel::class.java]
    }

    private var hasCameraPermission = false
    private var hasGalleryPermission = false

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasCameraPermission = permissions[Manifest.permission.CAMERA] == true
        hasGalleryPermission = permissions[getGalleryPermission()] == true
    }

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            binding.imageProfile.setImageURI(uri)
            profileViewModel.uploadImage(uri)
        } else {
            showMessage(getString(R.string.image_not_selected))
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEdgeToEdgeLayout()
        initToolbar()
        requestPermissionsIfNecessary()
        initListeners()
        addObservers()
    }

    override fun onStart() {
        super.onStart()
        profileViewModel.getProfileData()
    }

    override fun onDestroy() {
        profileViewModel.uploadImageResult.removeObservers(this)
        profileViewModel.updateProfileResult.removeObservers(this)
        profileViewModel.profileData.removeObservers(this)
        super.onDestroy()
    }

    private fun addObservers() {
        profileViewModel.uploadImageResult.observe(this) { result ->
            result.onSuccess { uri ->
                val data = mapOf(
                    "photo" to uri.toString()
                )
                profileViewModel.updateProfile(data)
                showMessage(getString(R.string.image_uploaded))
            }

            result.onFailure { error ->
                showMessage("${getString(R.string.error_uploading_image)}: ${error.message}")
            }
        }

        profileViewModel.updateProfileResult.observe(this) { result ->
            result.onSuccess {
                showMessage(getString(R.string.profile_updated))
            }

            result.onFailure { error ->
                showMessage("${getString(R.string.error_updating_profile)}: ${error.message}")
            }
        }

        profileViewModel.profileData.observe(this) { result ->
            result.onSuccess { profile ->

                binding.editProfileName.setText(profile.name)

                if (profile.photo.isNotEmpty()) {
                    Picasso
                        .get()
                        .load(profile.photo)
                        .into(binding.imageProfile)
                }
            }

            result.onFailure { error ->
                showMessage("${getString(R.string.error_getting_profile)}: ${error.message}")
            }
        }
    }


    private fun initListeners() {
        binding.fabGallerySelector.setOnClickListener {
            if (hasGalleryPermission) {
                galleryLauncher.launch("image/*")
            } else {
                showMessage(getString(R.string.requesting_gallery_permission))
                requestPermissionsIfNecessary()
                permissionLauncher.launch(arrayOf(getGalleryPermission()))
            }
        }

        binding.btnUpdate.setOnClickListener {
            val name = binding.editProfileName.text.toString()

            if (name.isNotEmpty()) {
                val data = mapOf(
                    "name" to name,
                )

                profileViewModel.updateProfile(data)
            } else {
                showMessage(getString(R.string.empty_name))
            }
        }
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

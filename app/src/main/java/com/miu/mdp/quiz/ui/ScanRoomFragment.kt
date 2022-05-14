package com.miu.mdp.quiz.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.miu.mdp.quiz.R
import com.miu.mdp.quiz.databinding.FragmentScanRoomBinding

class ScanRoomFragment : BaseFragment<FragmentScanRoomBinding>() {

    private var scannedRoomData: Uri? = null

    private val videoResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                scannedRoomData = result.data?.data
                binding.videoView.setVideoURI(scannedRoomData)
                binding.videoView.start()
                binding.beginQuiz.isVisible = scannedRoomData != null
            }
        }
    private val videoPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                videoResultLauncher.launch(Intent(MediaStore.ACTION_VIDEO_CAPTURE))
            }
        }
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentScanRoomBinding
        get() = FragmentScanRoomBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle("Scan room")

        with(binding) {
            val mediaController = MediaController(requireContext())
            videoView.setMediaController(mediaController)
            mediaController.setAnchorView(scanRoomButton)

            scanRoomButton.setOnClickListener {
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                    -> {
                        videoResultLauncher.launch(Intent(MediaStore.ACTION_VIDEO_CAPTURE))
                    }
                    else -> {
                        videoPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }
            }
            beginQuiz.setOnClickListener {
                if (scannedRoomData == null) toast("You have to scan the room first")
                else {
                    dialog("""
                        The quiz is out of 5 questions.
                        Each questions is 1 point.
                        Each time you skip a question, you loss 1 point.
                        Enjoy!
                    """.trimIndent(), "Quiz Rules") {
                        findNavController().navigate(ScanRoomFragmentDirections.actionScanRoomFragmentToQuestionsFragment())
                    }

                }
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                viewModel?.logout()
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

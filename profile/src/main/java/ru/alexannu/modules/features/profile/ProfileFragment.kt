package ru.alexannu.modules.features.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.alexannu.modules.features.api.MainService
import ru.alexannu.modules.features.profile.databinding.FragmentProfileBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()
    private val binding: FragmentProfileBinding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentProfileBinding.inflate(layoutInflater)
    }
    private var uploadFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.progress
                .collect{ loadData ->
                    binding.progressBar.max = loadData.first.toInt()
                    binding.progressBar.progress = loadData.second.toInt()
                }
        }
        val getContent =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                uploadFile = File.createTempFile("tmp_image",".png", requireContext().cacheDir).apply {
                    createNewFile()
                    deleteOnExit()
                }
                if (uri != null) {
                    val parcelFileDescriptor = requireContext().contentResolver.openFileDescriptor(uri, "r", null)
                    val inputStream = FileInputStream(parcelFileDescriptor?.fileDescriptor)
                    val outputStream = FileOutputStream(uploadFile)
                    inputStream.copyTo(outputStream)
                    parcelFileDescriptor?.close()
                    binding.fileName.text = uploadFile?.name
                }


            }

        binding.selectFileButton.setOnClickListener {
            getContent.launch("image/*")
        }
        binding.sendFileButton.setOnClickListener {
            if (uploadFile != null){
                viewModel.uploadFile(uploadFile!!, MainService())
            }
        }

    }
}
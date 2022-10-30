package ru.alexannu.modules.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import ru.alexannu.modules.features.api.MainService
import ru.alexannu.modules.features.api.ProgressRequestBody
import ru.alexannu.modules.features.api.UploadCallback
import java.io.File

class ProfileViewModel() : ViewModel(), UploadCallback {


    private val _progress = MutableStateFlow<Pair<Long, Long>>(Pair(0, 0))
    val progress: StateFlow<Pair<Long, Long>> = _progress.asStateFlow()


    fun uploadFile(file: File, mainService: MainService) {
        viewModelScope.launch {
            val requestBody = ProgressRequestBody(
                file = file,
                callback = this@ProfileViewModel
            )
            val multipartData = MultipartBody.Part.create(requestBody)
            mainService.uploadFile(multipartData)
        }
    }

    override fun onProgressUpdate(totalSize: Long, uploaded: Long) {
        _progress.value = Pair(totalSize, uploaded)
    }
}
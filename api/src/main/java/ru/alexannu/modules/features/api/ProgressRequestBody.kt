package ru.alexannu.modules.features.api

import android.net.Uri
import android.os.Handler
import android.os.Looper
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream


class ProgressRequestBody(
    private val file: File,
    private val contentType: String = "image",
    private val callback: UploadCallback
): RequestBody() {

    override fun contentType() = "$contentType/*".toMediaType()

    override fun contentLength() = file.length()

    override fun writeTo(sink: BufferedSink) {
        val length = file.length()
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        val fileInputStream = FileInputStream(file)
        var uploaded = 0L

        fileInputStream.use { inputStream ->
            var read:Int
            while (inputStream.read(buffer).also { read = it } != -1) {
                uploaded += read.toLong()
                callback.onProgressUpdate(length, uploaded)
                sink.write(buffer, 0, read)
            }
        }
    }


    companion object{
        const val DEFAULT_BUFFER_SIZE = 1024
    }
}

interface UploadCallback{
    fun onProgressUpdate(totalSize:Long, uploaded:Long)

}
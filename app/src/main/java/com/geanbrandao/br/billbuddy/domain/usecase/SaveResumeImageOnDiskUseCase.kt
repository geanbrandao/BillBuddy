package com.geanbrandao.br.billbuddy.domain.usecase

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.core.annotation.Factory
import java.io.File
import kotlin.coroutines.resume

@Factory
class SaveResumeImageOnDiskUseCase(
    private val context: Context,
) {

    operator fun invoke(bitmap: Bitmap, billName: String) = flow<Uri> {
        val fileName = buildString {
            append(billName.replace(" ", "_"))
            append("_")
            append("racha_conta")
            append("${System.currentTimeMillis()}")
            append(".png")
        }

        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            fileName
        )

        file.writeBitmap(bitmap, Bitmap.CompressFormat.PNG, 100)

        scanFilePath(context, file.path)?.let { scannedUri ->
            emit(scannedUri)
        } ?: throw Exception("File $fileName could not be scanned")
    }

    private suspend fun scanFilePath(context: Context, filePath: String): Uri? {
        return suspendCancellableCoroutine { continuation ->
            MediaScannerConnection.scanFile(
                context,
                arrayOf(filePath),
                arrayOf("image/png")
            ) { _, scannedUri ->
                if (scannedUri == null) {
                    continuation.cancel(Exception("File $filePath could not be scanned"))
                } else {
                    continuation.resume(scannedUri)
                }
            }
        }
    }

    private fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
        outputStream().use { out ->
            bitmap.compress(format, quality, out)
            out.flush()
        }
    }
}
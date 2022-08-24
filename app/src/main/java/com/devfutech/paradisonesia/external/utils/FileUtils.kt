package com.devfutech.paradisonesia.external.utils

import android.R
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.DatabaseUtils
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.navigation.NavController
import com.devfutech.paradisonesia.BuildConfig.DEBUG
import timber.log.Timber
import java.io.File
import java.io.FileFilter
import java.text.DecimalFormat


/**
 * Created by devfutech on 9/14/2021.
 */
object FileUtils {
    const val MIME_TYPE_AUDIO = "audio/*"
    const val MIME_TYPE_TEXT = "text/*"
    const val MIME_TYPE_IMAGE = "image/*"
    const val MIME_TYPE_VIDEO = "video/*"
    const val MIME_TYPE_APP = "application/*"
    const val HIDDEN_PREFIX = "."

    fun getExtension(uri: String?): String? {
        if (uri == null) {
            return null
        }
        val dot = uri.lastIndexOf(".")
        return if (dot >= 0) {
            uri.substring(dot)
        } else {
            // No extension.
            ""
        }
    }

    fun isLocal(url: String?): Boolean {
        return (url != null) && !url.startsWith("http://") && !url.startsWith("https://")
    }

    private fun isMediaUri(uri: Uri?): Boolean {
        return "media".equals(uri?.authority, ignoreCase = true)
    }


    fun getUri(file: File?): Uri? {
        return if (file != null) {
            Uri.fromFile(file)
        } else null
    }

    fun getPathWithoutFilename(file: File?): File? {
        if (file != null) {
            return if (file.isDirectory) {
                file
            } else {
                val filename: String = file.name
                val filepath: String = file.absolutePath

                var pathwithoutname = filepath.substring(
                    0,
                    filepath.length - filename.length
                )
                if (pathwithoutname.endsWith("/")) {
                    pathwithoutname = pathwithoutname.substring(0, pathwithoutname.length - 1)
                }
                File(pathwithoutname)
            }
        }
        return null
    }

    private fun getMimeType(file: File): String? {
        val extension = getExtension(file.name)
        return if (extension?.isNotEmpty() == true) MimeTypeMap.getSingleton().getMimeTypeFromExtension(
            extension.substring(1)
        ) else "application/octet-stream"
    }

    private fun getMimeType(context: Context, uri: Uri): String? {
        val file = File(getPath(context, uri).toString())
        return getMimeType(file)
    }

    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    private fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }

    private fun getDataColumn(
        context: Context, uri: Uri?, selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(
            column
        )
        try {
            cursor = context.contentResolver.query(
                uri!!, projection, selection, selectionArgs,
                null
            )
            if (cursor != null && cursor.moveToFirst()) {
                if (DEBUG) DatabaseUtils.dumpCursor(cursor)
                val columnIndex: Int = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(columnIndex)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    fun getPath(context: Context, uri: Uri): String? {
        if (DEBUG) Timber.d(" File -",
            "Authority: " + uri.authority.toString() +
                    ", Fragment: " + uri.fragment.toString() +
                    ", Port: " + uri.port.toString() +
                    ", Query: " + uri.query.toString() +
                    ", Scheme: " + uri.scheme.toString() +
                    ", Host: " + uri.host.toString() +
                    ", Segments: " + uri.pathSegments.toString()
        )
        val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // LocalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageState()
                        .toString() + "/" + split[1]
                }
            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                if (id != null && id.startsWith("raw:")) {
                    return id.substring(4)
                }
                val contentUriPrefixesToTry = arrayOf(
                    "content://downloads/public_downloads",
                    "content://downloads/my_downloads",
                    "content://downloads/all_downloads"
                )
                for (contentUriPrefix in contentUriPrefixesToTry) {
                    val contentUri = ContentUris.withAppendedId(
                        Uri.parse(contentUriPrefix),
                        java.lang.Long.valueOf(id)
                    )
                    val path = getDataColumn(context, contentUri, null, null)
                    if (path != null) {
                        return path
                    }
                }
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null
                if (("image" == type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if (("video" == type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if (("audio" == type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(
                    split[1]
                )
                return getDataColumn(context, contentUri, selection, selectionArgs)
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(
                context,
                uri,
                null,
                null
            )
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

    fun getFile(context: Context, uri: Uri?): File? {
        if (uri != null) {
            val path = getPath(context, uri)
            if (path != null && isLocal(path)) {
                return File(path)
            }
        }
        return null
    }

    fun getReadableFileSize(size: Int): String {
        val bytesInKilobytes = 1024
        val dec = DecimalFormat("###.#")
        val kilobiytes = " KB"
        val megabytes = " MB"
        val gigabytes = " GB"
        var fileSize = 0f
        var suffix = kilobiytes
        if (size > bytesInKilobytes) {
            fileSize = (size / bytesInKilobytes).toFloat()
            if (fileSize > bytesInKilobytes) {
                fileSize /= bytesInKilobytes
                if (fileSize > bytesInKilobytes) {
                    fileSize /= bytesInKilobytes
                    suffix = gigabytes
                } else {
                    suffix = megabytes
                }
            }
        }
        return (dec.format(fileSize).toString() + suffix)
    }

    fun getThumbnail(context: Context, file: File): Bitmap? {
        return getThumbnail(context, getUri(file), getMimeType(file))
    }

    fun getThumbnail(context: Context, uri: Uri): Bitmap? {
        return getThumbnail(context, uri, getMimeType(context, uri))
    }

    fun getThumbnail(context: Context, uri: Uri?, mimeType: String?): Bitmap? {
        if (DEBUG) Timber.d("Attempting to get thumbnail")
        if (!isMediaUri(uri)) {
            Timber.e("You can only retrieve thumbnails for images and videos.")
            return null
        }
        var bm: Bitmap? = null
        if (uri != null) {
            val resolver: ContentResolver = context.contentResolver
            var cursor: Cursor? = null
            try {
                cursor = resolver.query(uri, null, null, null, null)
                if (cursor?.moveToFirst() == true) {
                    val id: Int = cursor.getInt(0)
                    if (DEBUG) Timber.d( "Got thumb ID: $id")
                    if (mimeType!!.contains("video")) {
                        bm = MediaStore.Video.Thumbnails.getThumbnail(
                            resolver,
                            id.toLong(),
                            MediaStore.Video.Thumbnails.MINI_KIND,
                            null
                        )
                    } else if (mimeType.contains(MIME_TYPE_IMAGE)) {
                        bm = MediaStore.Images.Thumbnails.getThumbnail(
                            resolver,
                            id.toLong(),
                            MediaStore.Images.Thumbnails.MINI_KIND,
                            null
                        )
                    }
                }
            } catch (e: Exception) {
                if (DEBUG) Timber.e("getThumbnail", e)
            } finally {
                cursor?.close()
            }
        }
        return bm
    }

    var sComparator: Comparator<File> = Comparator<File> { f1, f2 ->
        f1.name.lowercase().compareTo(
            f2.name.lowercase()
        )
    }

    var sFileFilter: FileFilter = FileFilter { file ->
        val fileName: String = file.name
        file.isFile && !fileName.startsWith(HIDDEN_PREFIX)
    }

    var sDirFilter: FileFilter = FileFilter { file ->
        val fileName: String = file.name
        file.isDirectory && !fileName.startsWith(HIDDEN_PREFIX)
    }

    fun createGetContentIntent(): Intent {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        return intent
    }

    fun ltrim(str: String): String {
        val toDrop = "https://paradisonesia-assets.coretanstudio.com/"
        return "https://paradisonesia-assets.coretanstudio.com/"+str.replace(toDrop.toRegex(), "")
    }

    fun NavController.safeNavigate(direction: Int) {
        currentDestination?.getAction(direction)?.run {
            navigate(direction)
        }
    }

    fun simpleSpinnerAdapter(context: Context, items: Array<String>) : ArrayAdapter<String>? {
        return ArrayAdapter(context,
            R.layout.simple_spinner_dropdown_item,
            items)
    }

    fun DatePicker.getDate(): String {
        /*
        val calendar = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Calendar.getInstance()
        } else {
            TODO("VERSION.SDK_INT < N")
           // java.util.Calendar.getInstance()
        }
        calendar.set(year, month, dayOfMonth)


        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        val formatedDate: String = sdf.format(calendar.time)
        return sdf.parse(formatedDate)
         */

        val str_day = checkDigit(dayOfMonth)
        val str_month = checkDigit(month)
        val str_year = year.toString()

        return str_day + "-" + str_month + "-" + str_year
    }

    fun checkDigit(number: Int): String? {
        return if (number <= 9) "0$number" else number.toString()
    }
}
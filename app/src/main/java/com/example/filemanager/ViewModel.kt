package com.example.filemanager

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.io.File

class MyViewModel : ViewModel() {
    val currentDir = mutableStateOf(Environment.getExternalStorageDirectory())
    val files = mutableStateOf<List<File>>(currentDir.value.listFiles()?.toList() ?: listOf())
    val selectedFile = mutableStateOf<File?>(null)
    val fileToCopy = mutableStateOf<File?>(null)
    val isPermissionGranted = mutableStateOf(false)
    val copyStatus = mutableStateOf<Resource>(Resource.NotStarted)

    fun onClick(file: File){
        if (file.isDirectory){
            currentDir.value = file
            files.value = currentDir.value.listFiles()?.toList() ?: listOf()
        }else{
            selectedFile.value = file
        }
    }
    fun onBack (){
        val path = currentDir.value.path.dropLastWhile { it != '/' }
        currentDir.value = File(path)
        files.value = currentDir.value.listFiles()?.toList() ?: listOf()
    }

    private fun copyFile(fileToCopy: File, pasteDirectory: File): Flow<Resource>{
        return flow {
            CopyOperation.copyFile(fileToCopy,pasteDirectory){ status ->
                emit(Resource.Running(status))
                Log.d("Status",status.toString())
            }
            emit(Resource.Success("${fileToCopy.name} copied Successfully"))
        }
    }
    fun pasteFile(){
        viewModelScope.launch {
            copyFile(fileToCopy.value!!,currentDir.value)
                .flowOn(Dispatchers.IO)
                .collect{ status ->
                    copyStatus.value = status
                }
            files.value = currentDir.value.listFiles()?.toList() ?: listOf()
        }

    }
    fun setCopyFile(){
        fileToCopy.value = selectedFile.value
    }
    fun deleteFile(){
        selectedFile.value?.delete()
        files.value = emptyList()
        files.value = currentDir.value.listFiles()?.toList() ?: listOf()
    }
    fun openFile(context: Context,file: File){
        val uri: Uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri,context.contentResolver.getType(uri))
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        if (intent.resolveActivity(context.packageManager) != null){
            context.startActivity(Intent.createChooser(intent,"Open File With"))
        }else{
            println("File Did Not Open")
        }
    }
}
//You Can Catch Me From The Links Below
//Fiverr Link: https://www.fiverr.com/tonyappdevelopr
//LinkedIn Link: https://www.linkedin.com/in/ahsan-tony-b8ba94325
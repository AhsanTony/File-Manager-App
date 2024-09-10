package com.example.filemanager

import android.content.ClipData.Item
import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.io.File

@Composable
fun FileViewer(modifier: Modifier = Modifier,context: Context, viewModel: MyViewModel) {
    viewModel.isPermissionGranted.value = checkPermission(context)
    LazyColumn {
        item {
            Spacer(modifier = Modifier.height(140.dp))
        }
        when(val status = viewModel.copyStatus.value){
            Resource.NotStarted -> {}
            is Resource.Running -> {
                item {
                    Column {
                        LinearProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp),
                            progress = status.status?.progress ?: 0f,
                            color = Color.Green
                        )
                    }
                }
            }
            is Resource.Success -> {}
        }
        when(viewModel.isPermissionGranted.value){
            true ->{
                items(viewModel.files.value){file ->
                    val mod = if (file == viewModel.selectedFile.value && file != viewModel.fileToCopy.value){
                        Modifier.border(2.dp, color =  Color.Red)
                    } else if (file == viewModel.selectedFile.value && file == viewModel.fileToCopy.value){
                        Modifier.border(2.dp, color =  Color.Blue)
                    }
                    else{
                        Modifier
                    }
                    ItemFile(file = file, modifier = mod, onClick = {viewModel.onClick(file = file)})
                }
            }
            false ->{
                item {
                   RequestPermission(vmdl = viewModel)
                }
            }
        }
    }
}

@Composable
fun ItemFile(modifier: Modifier = Modifier,file: File, onClick: () -> Unit) {
    val style = if (file.isDirectory){
        Icons.Default.Folder
    }else{
        Icons.Default.FileOpen
    }
    Button(onClick = onClick, modifier = modifier) {
        Row {
            Icon(imageVector = style, contentDescription = null)
            Text(text = file.name)
        }
    }
}
//You Can Catch Me From The Links Below
//Fiverr Link: https://www.fiverr.com/tonyappdevelopr
//LinkedIn Link: https://www.linkedin.com/in/ahsan-tony-b8ba94325
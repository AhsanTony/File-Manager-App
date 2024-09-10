package com.example.filemanager

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FileCopy
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BottomBar(
    fileOpen: () -> Unit,
    fileCopy: () -> Unit,
    filePaste: () -> Unit,
    fileDelete: () -> Unit
) {
    BottomAppBar {
        Button(onClick = {fileOpen()}) {
            Icon(imageVector = Icons.Default.FileOpen, contentDescription = "Open")
        }
        Button(onClick = {fileCopy()}) {
            Icon(imageVector = Icons.Default.FileCopy, contentDescription = "Copy")
        }
        Button(onClick = {filePaste()}) {
            Icon(imageVector = Icons.Default.ContentPaste, contentDescription = "Paste")
        }
        Button(onClick = {fileDelete()}) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
        }
    }
}
//You Can Catch Me From The Links Below
//Fiverr Link: https://www.fiverr.com/tonyappdevelopr
//LinkedIn Link: https://www.linkedin.com/in/ahsan-tony-b8ba94325
package com.example.filemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.filemanager.ui.theme.FileManagerTheme

class MainActivity : ComponentActivity() {
    val vm : MyViewModel by viewModels()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            FileManagerTheme {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Scaffold(
                            modifier = Modifier.fillMaxSize(),
                            topBar = {
                                TopAppBar(
                                    title = { Text(text = vm.currentDir.value.path.removeRange(0,17))},
                                    navigationIcon = {
                                        Button(onClick = {vm.onBack()}) {
                                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                                        }
                                    }
                                )
                            },
                            bottomBar = {
                                BottomBar(
                                    fileOpen = { vm.openFile(context = context, file = vm.selectedFile.value!!) },
                                    fileCopy = { vm.setCopyFile() },
                                    filePaste = { vm.pasteFile() },
                                    fileDelete = { vm.deleteFile() }
                                )
                            }
                        ) { innerpadding ->
                            Spacer(modifier = Modifier
                                .height(40.dp)
                                .padding(innerpadding))
                            FileViewer(context =context , viewModel =vm )
                        }
                    }
                }
            }
        }
    }
}
//You Can Catch Me From The Links Below
//Fiverr Link: https://www.fiverr.com/tonyappdevelopr
//LinkedIn Link: https://www.linkedin.com/in/ahsan-tony-b8ba94325
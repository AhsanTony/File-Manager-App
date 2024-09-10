package com.example.filemanager

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermission(vmdl: MyViewModel) {
    val context = LocalContext.current
    val permission = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    )
    val showPermission = remember {
        mutableStateOf(!checkPermission(context))
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            showPermission.value = !checkPermission(context)
            vmdl.isPermissionGranted.value = checkPermission(context)
        }
    )
    if (showPermission.value){
        Button(onClick = {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                try {
                    val intent = Intent()
                    intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                    val uri = Uri.fromParts("package",context.packageName,null)
                    intent.data = uri
                    launcher.launch(intent)
                }catch (e: Exception){
                    val intent = Intent()
                    intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                    launcher.launch(intent)
                }
            }else{
                permission.launchMultiplePermissionRequest()
            }
        }) {
            Text(text = "Grant Permission", fontSize = 30.sp)
        }
    }
}


fun checkPermission(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        Environment.isExternalStorageManager()
    } else {
        val read = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val write = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        read == PackageManager.PERMISSION_GRANTED && write == PackageManager.PERMISSION_GRANTED
    }
}
//You Can Catch Me From The Links Below
//Fiverr Link: https://www.fiverr.com/tonyappdevelopr
//LinkedIn Link: https://www.linkedin.com/in/ahsan-tony-b8ba94325
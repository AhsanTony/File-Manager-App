package com.example.filemanager

import java.io.File

object CopyOperation {

    suspend fun copyFile(fileToCopy: File, pasteDirectory: File, status: suspend (Status)-> Unit){
        val input = fileToCopy.inputStream()
        val file = if(pasteDirectory.isDirectory){
            File(pasteDirectory.absoluteFile, fileToCopy.name)
        }else{
            pasteDirectory
        }
        if (file.exists()) file.delete()

        val output = file.outputStream()

        input.use {
            var copied = 0
            var speed = 0
            val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
            var bytes = it.read(buffer)
            var beforeTime = System.currentTimeMillis()
            while (bytes > -1){
                output.write(buffer,0,bytes)
                bytes = it.read(buffer)
                copied += bytes
                if (System.currentTimeMillis() - beforeTime > 1000){
                    status(
                        Status(
                            progress = copied.toFloat() / fileToCopy.length().toFloat(),
                            speed = speed / 1024f
                        )
                    )
                    beforeTime = System.currentTimeMillis()
                    speed = 0
                }else{
                    speed += bytes
                }
            }
            status(
                Status(
                    progress = copied.toFloat() / fileToCopy.length().toFloat(),
                    speed = speed / 1024f
                )
            )
            it.close()
        }
    }
}
//You Can Catch Me From The Links Below
//Fiverr Link: https://www.fiverr.com/tonyappdevelopr
//LinkedIn Link: https://www.linkedin.com/in/ahsan-tony-b8ba94325
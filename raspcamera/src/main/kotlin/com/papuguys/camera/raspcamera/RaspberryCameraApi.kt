package com.papuguys.camera.raspcamera

import uk.co.caprica.picam.PicamNativeLibrary.installTempLibrary

class RaspberryCameraApi {
    companion object NativeLibInstaller {
        fun install() {
            installTempLibrary()
        }
    }
}
package com.papuguys.camera.raspcamera

import uk.co.caprica.picam.Camera
import uk.co.caprica.picam.CameraConfiguration
import uk.co.caprica.picam.PicamNativeLibrary.*
import uk.co.caprica.picam.CameraConfiguration.cameraConfiguration
import uk.co.caprica.picam.FilePictureCaptureHandler
import uk.co.caprica.picam.enums.AutomaticWhiteBalanceMode

import uk.co.caprica.picam.enums.Encoding
import uk.co.caprica.picam.enums.ExposureMeteringMode
import uk.co.caprica.picam.enums.ExposureMode
import java.io.File


fun main(args: Array<String>) {

    installLibrary("/home/pi/camera_api/")

    val configuration = CameraConfiguration.cameraConfiguration()
        .width(1920)
        .height(1080)
        .automaticWhiteBalance(AutomaticWhiteBalanceMode.AUTO)
        .exposureMode(ExposureMode.AUTO)
        .exposureMeteringMode(ExposureMeteringMode.MATRIX)
        .encoding(Encoding.JPEG)
        .quality(85)
    val camera: Camera = Camera(configuration)
    with(camera) {
        takePicture(FilePictureCaptureHandler(File("picam" + System.currentTimeMillis() + ".jpg")));
    };
}
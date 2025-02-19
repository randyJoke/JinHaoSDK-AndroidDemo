package com.jinhao.jinhaosdk_androiddemo

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jinhao.jinhaosdk.aid.jinhao.JinHaoAccessory
import com.jinhao.jinhaosdk.aid.jinhao.JinHaoAccessoryListener
import com.jinhao.jinhaosdk.aid.jinhao.data.JinHaoDsp
import com.jinhao.jinhaosdk.aid.jinhao.service.JinHaoRequest
import com.jinhao.jinhaosdk.shared.accessory.Accessory
import com.jinhao.jinhaosdk.shared.accessory.AccessoryService
import java.util.function.Consumer
import kotlin.math.roundToInt

class DetailActivity : ComponentActivity(), JinHaoAccessoryListener {

    private var tag = DetailActivity::class.simpleName

    private var device: JinHaoAccessory? = null

    private var connectedState = mutableStateOf<Boolean>(false)

    private var volumeState = mutableStateOf<Float>(0F)

    private var programState = mutableStateOf<Float>(0F)

    private var noiseState = mutableStateOf<Float>(-1F)

    private var mpoState = mutableStateOf<Float>(-1F)

    private var directionState = mutableStateOf<Float>(-1F)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val itemId = intent.getStringExtra("address") // 获取传递的数据
        device = DataHolder.accessoryManager?.deviceForDeviceId(itemId) as JinHaoAccessory
        configureUI()
    }

    override fun onStop() {
        super.onStop()
        if (device?.isConnected == true) {
            device?.disconnect(true)
        }
    }

    private fun configureUI() {
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp), // 子项间距
                horizontalAlignment = Alignment.CenterHorizontally // 水平对齐方式
            ) {
                Text(text = "${device?.deviceName}")
                Button(
                    enabled = !connectedState.value,
                    onClick = {
                        /**
                         * After discovering a device, call `connect` to establish a connection.
                         * This is required for subsequent data read and write operations.
                         */
                        val connect = device?.connect(this@DetailActivity)
                    }) {
                    Text("connect")
                }
                Button(
                    enabled = connectedState.value,
                    onClick = {
                        val connect = device?.disconnect(false)
                    }) {
                    Text("disconnect")
                }

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Text(text = "Volume")
                    Slider(value = this@DetailActivity.volumeState.value,
                        onValueChange = {
                            volumeState.value = it
                            /**
                             * Sets the device's volume level.
                             * Once the volume is set, the updated value can be retrieved in the `deviceDidUpdateValue` callback.
                             */
                            device?.excute(JinHaoRequest.controlVolume(it.roundToInt(), programState.value.roundToInt()), Consumer {
                                
                            })},
                        valueRange = 0f..10f,
                        steps = 9
                    )
                }

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Text(text = "Mode")
                    Slider(value = this@DetailActivity.programState.value,
                        onValueChange = { program ->
                            programState.value = program
                            /**
                             * Sets the Program mode for the hearing device.
                             * The program mode determines how the device processes sound based on different listening environments.
                             *
                             * Available Program modes:
                             * - 0: Normal       → Standard mode for everyday listening.
                             * - 1: Music        → Optimized for richer sound quality when listening to music.
                             * - 2: Outdoor      → Reduces wind noise and enhances speech clarity in outdoor environments.
                             * - 3: Restaurant   → Focuses on speech and reduces background noise in noisy environments.
                             */
                            device?.excute(JinHaoRequest.controlProgram(program.roundToInt()), Consumer { result ->
                                if (result.isSuccess && device != null) {

                                }
                            })},
                        valueRange = 0f..3f,
                        steps = 2
                    )
                }

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Text(text = "Noise")
                    Slider(value = this@DetailActivity.noiseState.value,
                        onValueChange = {
                            noiseState.value = it
                            /**
                             * Sets the noise reduction mode for the hearing device.
                             * The process involves retrieving the device's DSP, modifying the NOISE mode,
                             * and then writing the updated DSP data back to the device.
                             *
                             * Available noise reduction levels:
                             * - OFF: Noise reduction disabled
                             * - WEAK: Low-level noise reduction
                             * - MEDIUM: Medium-level noise reduction
                             * - STRONG: High-level noise reduction
                             */
                            val dsp = device?.dsp?.copy()
                            when {
                                it.roundToInt() == 0 -> {
                                    dsp?.noise = JinHaoDsp.NOISE.OFF
                                }
                                it.roundToInt() == 1 -> {
                                    dsp?.noise = JinHaoDsp.NOISE.WEAK
                                }
                                it.roundToInt() == 2 -> {
                                    dsp?.noise = JinHaoDsp.NOISE.MEDIUM
                                }
                                it.roundToInt() == 3 -> {
                                    dsp?.noise = JinHaoDsp.NOISE.STRONG
                                }
                            }
                            dsp.let {
                                device?.excute(JinHaoRequest.writeDsp(it, programState.value.roundToInt(), true), Consumer {

                                })
                            }
                        },
                        valueRange = 0f..3f,
                        steps = 2
                    )
                }

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Text(text = "MPO")
                    Slider(value = this@DetailActivity.mpoState.value,
                        onValueChange = {
                            mpoState.value = it
                            /**
                             * Sets the Maximum Power Output (MPO) mode for the hearing device.
                             * The process involves retrieving the device's DSP, modifying the MPO mode,
                             * and then writing the updated DSP data back to the device.
                             *
                             * Available MPO levels:
                             * - OFF: MPO disabled (no output limitation)
                             * - LOW: Low-level output limitation
                             * - MEDIUM: Medium-level output limitation
                             * - HIGH: High-level output limitation
                             */
                            val dsp = device?.dsp?.copy()
                            when {
                                it.roundToInt() == 0 -> {
                                    dsp?.mpo = JinHaoDsp.MPO.OFF
                                }
                                it.roundToInt() == 1 -> {
                                    dsp?.mpo = JinHaoDsp.MPO.LOW
                                }
                                it.roundToInt() == 2 -> {
                                    dsp?.mpo = JinHaoDsp.MPO.MEDIUM
                                }
                                it.roundToInt() == 3 -> {
                                    dsp?.mpo = JinHaoDsp.MPO.HIGH
                                }
                            }
                            dsp.let {
                                device?.excute(JinHaoRequest.writeDsp(it, programState.value.roundToInt(), true), Consumer {

                                })
                            }
                        },
                        valueRange = 0f..3f,
                        steps = 2
                    )
                }

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Text(text = "Direction")
                    Slider(value = this@DetailActivity.directionState.value,
                        onValueChange = {
                            directionState.value = it
                            /**
                             * Sets the direction mode for the hearing device.
                             * The directional mode adjusts how the device captures sound based on the current listening environment.
                             *
                             * Available Directional modes:
                             * - NORMAL:   Standard microphone direction, capturing sound equally from all directions.
                             * - TV:       Optimized for listening to television, focusing on sound from the front.
                             * - MEETING:  Optimized for group settings, enhancing voices from the front while reducing noise.
                             * - FACE:     Focused on capturing sound from a specific person or a face-to-face conversation.
                             */
                            val dsp = device?.dsp?.copy()
                            when {
                                it.roundToInt() == 0 -> {
                                    dsp?.direction = JinHaoDsp.DIRECTION.NORMAL
                                }
                                it.roundToInt() == 1 -> {
                                    dsp?.direction = JinHaoDsp.DIRECTION.TV
                                }
                                it.roundToInt() == 2 -> {
                                    dsp?.direction = JinHaoDsp.DIRECTION.METTING
                                }
                                it.roundToInt() == 3 -> {
                                    dsp?.direction = JinHaoDsp.DIRECTION.FACE
                                }
                            }
                            dsp.let {
                                device?.excute(JinHaoRequest.writeDsp(it, programState.value.roundToInt(), true), Consumer {

                                })
                            }
                        },
                        valueRange = 0f..3f,
                        steps = 2
                    )
                }
            }
        }
    }

    @Composable
    fun GlobalLoading(isLoading: Boolean, content: @Composable () -> Unit) {
        Box(modifier = Modifier.fillMaxSize()) {
            // 全局菊花加载进度条
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f)), // 半透明背景
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(50.dp),
                        color = Color.White,
                        strokeWidth = 4.dp
                    )
                }
            }
        }
    }

    /**
     *  *********************************** JinHaoAccessoryListener ********************************
     */

    /**
     * Callback triggered when the device's state changes.
     */
    override fun deviceDidUpdate(device: Accessory?, state: Int) {

    }

    /**
     *  Callback when the device successfully connects
     */
    override fun deviceDidConnect(device: Accessory?) {
        runOnUiThread {
            connectedState.value = true
            Toast.makeText(this, "connected", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     *  Callback when the device disconnects
     */
    override fun deviceDidDisconnect(device: Accessory?) {
        runOnUiThread {
            connectedState.value = false
            Toast.makeText(this, "disconnected", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Callback when the device fails to connect
     */
    override fun deviceDidFailToConnect(device: Accessory?, errorCode: Int) {
        runOnUiThread {
            connectedState.value = false
            Toast.makeText(this, "connection failed", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * This callback is called when the device has discovered its services.
     * At this point, you can retrieve device information such as the current Program, Volume,
     * or DSP data for each Program.
     */
    override fun deviceDidDiscoverServices(device: Accessory?, services: MutableList<AccessoryService>?) {
        if (device is JinHaoAccessory) {
            device.excute(JinHaoRequest.readProgramVolume(), Consumer {
                if (it.isError) {
                    Log.w(tag, "Failed to read program volume")
                } else {
                    Log.w(tag, "Successfully read program volume")
                }
            })
            device.excute(JinHaoRequest.readDsp(0), Consumer {
                Log.w(tag, "Finished reading program 0 DSP file")
            })
            device.excute(JinHaoRequest.readDsp(1), Consumer {
                Log.w(tag, "Finished reading program 1 DSP file")
            })
            device.excute(JinHaoRequest.readDsp(2), Consumer {
                Log.w(tag, "Finished reading program 2 DSP file")
            })
            device.excute(JinHaoRequest.readDsp(3), Consumer {
                Log.w(tag, "Finished reading program 3 DSP file")
            })
        }
    }

    /**
     * Callback triggered when the device's battery level changes.
     */
    override fun deviceBatteryChanged(device: JinHaoAccessory?, bat: Int) {

    }

    /**
     * This callback is called when the device's data value is updated,
     * such as when data changes or when data is read from the device.
     * You can access the current device data values in this method.
     * To retrieve the corresponding values from the device, you need to first issue a read command,
     * as shown in the `deviceDidDiscoverServices` method where `device.excute` performs a read operation.
     */
    override fun deviceDidUpdateValue(device: JinHaoAccessory?) {
        runOnUiThread {
            if (device is JinHaoAccessory) {
                device.dsp?.let {
                    when {
                        it.noise == JinHaoDsp.NOISE.OFF -> {
                            noiseState.value = 0F
                        }
                        it.noise == JinHaoDsp.NOISE.WEAK -> {
                            noiseState.value = 1F
                        }
                        it.noise == JinHaoDsp.NOISE.MEDIUM -> {
                            noiseState.value = 2F
                        }
                        it.noise == JinHaoDsp.NOISE.STRONG -> {
                            noiseState.value = 3F
                        }
                    }
                    when {
                        it.direction == JinHaoDsp.DIRECTION.NORMAL -> {
                            directionState.value = 0F
                        }
                        it.direction == JinHaoDsp.DIRECTION.TV -> {
                            directionState.value = 1F
                        }
                        it.direction == JinHaoDsp.DIRECTION.METTING -> {
                            directionState.value = 2F
                        }
                        it.direction == JinHaoDsp.DIRECTION.FACE -> {
                            directionState.value = 3F
                        }
                    }

                    when {
                        it.mpo == JinHaoDsp.MPO.OFF -> {
                            mpoState.value = 0F
                        }
                        it.mpo == JinHaoDsp.MPO.LOW -> {
                            mpoState.value = 1F
                        }
                        it.mpo == JinHaoDsp.MPO.MEDIUM -> {
                            mpoState.value = 2F
                        }
                        it.mpo == JinHaoDsp.MPO.HIGH -> {
                            mpoState.value = 3F
                        }
                    }
                }
                device.let {
                    volumeState.value = it.volume.toFloat()
                }
                device.let {
                    programState.value = it.program.toFloat()
                }
            }
        }
    }

    /**
     * Changing the program mode on the hearing aid
     */
    override fun didChangedProgramByAid(device: JinHaoAccessory?, previous: Int, current: Int) {
        programState.value = current.toFloat()
        device?.excute(JinHaoRequest.readDsp(current), null)
    }

    /**
     * Changing the volume on the hearing aid
     */
    override fun didChangedVolumeByAid(device: JinHaoAccessory?, previous: Int, current: Int) {
        volumeState.value = current.toFloat()
    }

}



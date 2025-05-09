package com.jinhao.jinhaosdk_androiddemo

import android.os.Bundle
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.jinhao.jinhaosdk.aid.jinhao.JinHaoA16Accessory
import com.jinhao.jinhaosdk.aid.jinhao.JinHaoAccessory
import com.jinhao.jinhaosdk.aid.jinhao.JinHaoAccessoryListener
import com.jinhao.jinhaosdk.aid.jinhao.data.JinHaoA16Dsp
import com.jinhao.jinhaosdk.aid.jinhao.data.JinHaoA4Dsp
import com.jinhao.jinhaosdk.aid.jinhao.data.JinHaoDsp
import com.jinhao.jinhaosdk.aid.jinhao.data.JinHaoProfileType
import com.jinhao.jinhaosdk.aid.jinhao.data.JinHaoProgram
import com.jinhao.jinhaosdk.aid.jinhao.service.JinHaoRequest
import com.jinhao.jinhaosdk.shared.accessory.Accessory
import com.jinhao.jinhaosdk.shared.accessory.AccessoryService
import com.jinhao.jinhaosdk_androiddemo.DataHolder
import kotlinx.coroutines.*
import java.util.function.Consumer
import kotlin.math.max
import kotlin.math.roundToInt


class DetailActivity : ComponentActivity(), JinHaoAccessoryListener {

    private var tag = DetailActivity::class.simpleName

    private var device: JinHaoAccessory? = null

    private var connectedState = mutableStateOf<Boolean>(false)

    private var volumeState = mutableStateOf<Float>(0F)
    private var minVolumeState = mutableStateOf<Float>(0F)
    private var maxVolumeState = mutableStateOf<Float>(10F)

    private var programState = mutableStateOf<Float>(0F)

    private var noiseState = mutableStateOf<Float>(-1F)

    private var mpoState = mutableStateOf<Float>(-1F)

    private var directionState = mutableStateOf<Float>(-1F)

    private val frequencies = listOf(250, 500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 5000, 6000, 7000)

    private var eqState = frequencies.associateWith { mutableStateOf(0f) }

    private var minEQValueState = mutableStateOf<Float>(value = 0f)

    private var maxEQValueState = mutableStateOf<Float>(value = 15f)

    private var numberOfProgramState = mutableStateOf<Int>(value = 3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val itemId = intent.getStringExtra("address")
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
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
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
                        valueRange = minVolumeState.value..maxVolumeState.value,
                        steps = (maxVolumeState.value - 1).toInt()
                    )
                }

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Text(text = "Program")
                    Slider(value = this@DetailActivity.programState.value,
                        onValueChange = { program ->
                            programState.value = program
                            /**
                             * Sets the Program for the hearing device.
                             * The program determines how the device processes sound based on different listening environments.
                             *
                             */
                            device?.excute(JinHaoRequest.controlProgram(program.roundToInt()), Consumer { result ->
                                if (result.isSuccess && device != null) {
                                    Log.w(tag, "current program is ${program}, current scene mode is ${device!!.scenesOfProgram.get(device!!.program)}")
                                }
                            })},
                        valueRange = 0f..(numberOfProgramState.value).toFloat(),
                        steps = max(0, numberOfProgramState.value-1)
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
                            dsp?.let {
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
                            if (dsp is JinHaoA4Dsp) {
                                mpoState.value = it
                                when {
                                    it.roundToInt() == 0 -> {
                                        dsp.mpo = JinHaoA4Dsp.MPO.OFF
                                    }

                                    it.roundToInt() == 1 -> {
                                        dsp.mpo = JinHaoA4Dsp.MPO.LOW
                                    }

                                    it.roundToInt() == 2 -> {
                                        dsp?.mpo = JinHaoA4Dsp.MPO.MEDIUM
                                    }

                                    it.roundToInt() == 3 -> {
                                        dsp?.mpo = JinHaoA4Dsp.MPO.HIGH
                                    }
                                }
                                dsp.let {
                                    device?.excute(
                                        JinHaoRequest.writeDsp(
                                            it,
                                            programState.value.roundToInt(),
                                            true
                                        ), Consumer {

                                        })
                                }
                            }


                            /**
                             *
                             */
                            if (dsp is JinHaoA16Dsp) {
                                mpoState.value = it
                                dsp.setMpoLevel(250, it.roundToInt())
                                dsp.setMpoLevel(500, it.roundToInt())
                                //...
                                //...
                                //...
                                dsp.setMpoLevel(7500, it.roundToInt())
                                dsp.let {
                                    device?.excute(
                                        JinHaoRequest.writeDsp(
                                            it,
                                            programState.value.roundToInt(),
                                            true
                                        ), Consumer {

                                        })
                                }
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
                            dsp?.let {
                                device?.excute(JinHaoRequest.writeDsp(it, programState.value.roundToInt(), true), Consumer {

                                })
                            }
                        },
                        valueRange = 0f..3f,
                        steps = 2
                    )
                }

                EqSliderUI()
            }
        }
    }

    @Composable
    fun EqSliderUI() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "EQ Settings", fontSize = 16.sp, fontWeight = FontWeight.Bold)

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(frequencies) { frequency ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "${frequency}Hz")

                        Slider(
                            value = eqState[frequency]?.value ?: 0f,
                            onValueChange = {
                                eqState[frequency]?.value = it
                                /**
                                 * Adjusts the EQ (Equalizer) values for different frequency bands.
                                 * The frequencies are as follows: 250Hz, 500Hz, 1000Hz, 1500Hz, 2000Hz, 2500Hz, 3000Hz, 3500Hz, 4000Hz, 5000Hz, 6000Hz, and 7000Hz.
                                 * For each frequency, the EQ values range from minEQValue to maxEQValue.
                                 * These values are stored as bytes (minEQValue to maxEQValue) for the DSP (Digital Signal Processor) settings.
                                 */
                                val dsp = device?.dsp?.copy()
                                dsp?.setEq(frequency, it.toInt())
                                dsp?.let {
                                    device?.excute(JinHaoRequest.writeDsp(it, programState.value.roundToInt(), true), Consumer {

                                    })
                                }
                            },
                            valueRange = minEQValueState.value..maxEQValueState.value,
                            steps = (maxEQValueState.value-1).toInt(),
                            modifier = Modifier.weight(1f)
                        )

                        Text(text = "${eqState[frequency]?.value?.toInt()}")
                    }
                }
            }
        }
    }


    @Composable
    fun GlobalLoading(isLoading: Boolean, content: @Composable () -> Unit) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f)),
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
            if (device is JinHaoA16Accessory) {
                device.requestSummaryDataLog {

                }
            }

            device.excute(JinHaoRequest.readProfile(JinHaoProfileType.PRODUCT_SKU), Consumer {
                if (it.isSuccess) {
                    minVolumeState.value = device.profile.minVolume.toFloat()
                    maxVolumeState.value = device.profile.maxVolume.toFloat()
                    Log.w(tag, "success to read sku: ${device.profile.skuCode}, minVolume is: ${device.profile.minVolume}, maxVolume is: ${device.profile.maxVolume}")
                }
            })


            device.excute(JinHaoRequest.readProfile(JinHaoProfileType.PRODUCT_PATTERN), Consumer {
                if (it.isSuccess) {
                    Log.w(tag, "success to read pattern: ${device.profile.patternCode}")
                }
            })


            device.excute(JinHaoRequest.readProgramVolume(), Consumer {
                if (it.isError) {
                    Log.w(tag, "Failed to read current program volume")
                } else {
                    Log.w(tag, "Successfully read current program volume")
                    device.excute(JinHaoRequest.readDsp(device.program), Consumer {
                        device.dsp?.let {
                            minEQValueState.value = it.minEqValue.toFloat()
                            maxEQValueState.value = it.maxEqValue.toFloat()
                        }
                        Log.w(tag, "Finished reading DSP file for program ${device.program} ")
                    })
                }
            })


            device.readNumberOfProgram(5000, Consumer {
                if (it.isError) {
                    Log.w(tag, "Failed to read number of program")
                } else {
                    numberOfProgramState.value = device.numberOfProgram
                    Log.w(tag, "Successfully read number of program is ${device.numberOfProgram + 1}")
                }
            })

            device.readScenesOfProgram(5000, Consumer {
                val programs = device.scenesOfProgram
                val type0 = programs.get(0)     //default is NORMAL
                val type1 = programs.get(1)     //default is RESTAURANT
                val type2 = programs.get(2)     //default is MUSIC
                val type3 = programs.get(3)     //default is OUTDOOR
                Log.w(tag, "Successfully read scenes of program is ${programs.size}")
            })

            device.excute(JinHaoRequest.readDsp(0), Consumer {
                val frequences = device.dsp.frequences
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

            //range is 0~3, total 4 program(0、1、2、3)
            device.writeNumberOfProgram(3, 5000, Consumer {
                if (it.isError) {
                    Log.w(tag, "Failed to write number of program")
                } else {
                    Log.w(tag, "Successfully write number of program")
                }
            })

            device.excute(JinHaoRequest.readBrEdr(), Consumer {
                if (it.isSuccess) {
                    Log.w(tag, "br edr enable: ${device.isBrEdr}")
                }
            })

            device.excute(JinHaoRequest.readBat(), Consumer {
                if (it.isSuccess) {
                    Log.w(tag, "bat is: ${device.bat}")
                }
            })

            this.readDataLog()
        }
    }

    /**
     * Callback triggered when the device's battery level changes.
     */
    override fun deviceBatteryChanged(device: JinHaoAccessory?, bat: Int) {
        Log.w(tag, "deviceBatteryChanged bat is: ${device?.bat}")
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
                    Log.w(tag, "The hearing aid supports the following channels (frequencies): ${it.frequences}")
                    minEQValueState.value = it.minEqValue.toFloat()
                    maxEQValueState.value = it.maxEqValue.toFloat()
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

                    if (it is JinHaoA16Dsp) {
                        Log.w(tag, "compresstion threshold level is ${it.getCompressRatioLevel(250)}  in 250Hz, value is ${it.getCompressThresholdValue(it.getCompressThresholdLevel(250))}")
                        Log.w(tag, "compresstion ratio level is ${it.getCompressRatioLevel(250)}  in 250Hz, value is ${it.getCompressRatioValue(it.getCompressRatioLevel(250))}")
                        Log.w(tag, "attack time level is ${it.attackTimeLevel}, value is ${it.getAttackTimeValue(it.attackTimeLevel)}")
                        Log.w(tag, "release time level is ${it.releaseTimeLevel}, value is ${it.getReleaseTimeValue(it.releaseTimeLevel)}")
                        Log.w(tag, "mpo level is ${it.getMpoLevel(250)}, value is ${it.getMpoValue(it.getMpoLevel(250))}")
                    }

                    //range of value is dsp.minEqValue to dsp.maxEqValue
                    eqState[250]?.value = it.getEq(250).toFloat()
                    eqState[500]?.value = it.getEq(500).toFloat()
                    eqState[1000]?.value = it.getEq(1000).toFloat()
                    eqState[1500]?.value = it.getEq(1500).toFloat()
                    eqState[2000]?.value = it.getEq(2000).toFloat()
                    eqState[2500]?.value = it.getEq(2500).toFloat()
                    eqState[3000]?.value = it.getEq(3000).toFloat()
                    eqState[3500]?.value = it.getEq(3500).toFloat()
                    eqState[4000]?.value = it.getEq(4000).toFloat()
                    eqState[5000]?.value = it.getEq(5000).toFloat()
                    eqState[6000]?.value = it.getEq(6000).toFloat()
                    eqState[7000]?.value = it.getEq(7000).toFloat()
                }
                device.let {
                    volumeState.value = it.volume.toFloat()
                    programState.value = it.program.toFloat()
                    numberOfProgramState.value = it.numberOfProgram
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



    private fun changeCompressionThreshold() {
        val dsp = device?.dsp?.copy()
        if (dsp is JinHaoA16Dsp) {
            //ct is 0 ~ 35
            dsp.setCompressThresholdLevel(250, 1)
            device?.program?.let {
                device!!.excute(JinHaoRequest.writeDsp(dsp, it, true), Consumer {

                })
            }
        }
    }

    private fun changeCompressionRatio() {
        val dsp = device?.dsp?.copy()
        if (dsp is JinHaoA16Dsp) {
            //compressRatioLevel is 0 ~ 31
            dsp.setCompressRatioLevel(250, 1)
            device?.program?.let {
                device!!.excute(JinHaoRequest.writeDsp(dsp, it, true), Consumer {

                })
            }
        }
    }

    private fun changeMPO() {
        val dsp = device?.dsp?.copy()
        if (dsp is JinHaoA16Dsp) {
            //mpo is 0 ~ 11
            dsp.setMpoLevel(250, 1)
            device?.program?.let {
                device!!.excute(JinHaoRequest.writeDsp(dsp, it, true), Consumer {

                })
            }
        }
    }

    private fun changeAttackTime() {
        val dsp = device?.dsp?.copy()
        if (dsp is JinHaoA16Dsp) {
            //attackTimeLevel is 0 ~ 3
            dsp.attackTimeLevel = 1
            device?.program?.let {
                device!!.excute(JinHaoRequest.writeDsp(dsp, it, true), Consumer {

                })
            }
        }
    }

    private fun changeReleaseTime() {
        val dsp = device?.dsp?.copy()
        if (dsp is JinHaoA16Dsp) {
            //releaseTimeLevel is 0 ~ 3
            dsp.releaseTimeLevel = 1
            device?.program?.let {
                device!!.excute(JinHaoRequest.writeDsp(dsp, it, true), Consumer {

                })
            }
        }
    }

    private fun readDataLog() {
        (device as? JinHaoA16Accessory)?.requestSummaryDataLog {
            if (it != null) {
                Log.w(tag, "success to read data log: ${it}")
            }
        }
    }

}


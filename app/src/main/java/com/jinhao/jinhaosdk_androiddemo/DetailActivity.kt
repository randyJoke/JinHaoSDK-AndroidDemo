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
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jinhao.jinhaosdk.aid.jinhao.JinHaoAccessory
import com.jinhao.jinhaosdk.aid.jinhao.JinHaoAccessoryListener
import com.jinhao.jinhaosdk.aid.jinhao.data.JinHaoDsp
import com.jinhao.jinhaosdk.aid.jinhao.data.JinHaoProfile
import com.jinhao.jinhaosdk.aid.jinhao.data.JinHaoProfileType
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

    private val frequencies = listOf(250, 500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 5000, 6000, 7000)

    private var eqState = frequencies.associateWith { mutableStateOf(0f) }

    private var minEQValueState = mutableStateOf<Float>(value = 0f)

    private var maxEQValueState = mutableStateOf<Float>(value = 15f)

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
                             * Sets the Program for the hearing device.
                             * The program determines how the device processes sound based on different listening environments.
                             *
                             */
                            device?.excute(JinHaoRequest.controlProgram(program.roundToInt()), Consumer { result ->
                                if (result.isSuccess && device != null) {
                                    Log.w(tag, "current program is ${program}, current scene mode is ${device!!.scenesOfProgram.get(device!!.program)}")
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
                            dsp?.let {
                                device?.excute(JinHaoRequest.writeDsp(it, programState.value.roundToInt(), true), Consumer {
                                    if (it.isSuccess) {

                                    }
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
                                 * For each frequency, the EQ values range from 0 to 15.
                                 * These values are stored as bytes (0 to 15) for the DSP (Digital Signal Processor) settings.
                                 */
                                val dsp = device?.dsp?.copy()
                                when (frequency) {
                                    250 -> dsp?.eq250 = it.toInt().toByte()
                                    500 -> dsp?.eq500 = it.toInt().toByte()
                                    1000 -> dsp?.eq1000 = it.toInt().toByte()
                                    1500 -> dsp?.eq1500 = it.toInt().toByte()
                                    2000 -> dsp?.eq2000 = it.toInt().toByte()
                                    2500 -> dsp?.eq2500 = it.toInt().toByte()
                                    3000 -> dsp?.eq3000 = it.toInt().toByte()
                                    3500 -> dsp?.eq3500 = it.toInt().toByte()
                                    4000 -> dsp?.eq4000 = it.toInt().toByte()
                                    5000 -> dsp?.eq5000 = it.toInt().toByte()
                                    6000 -> dsp?.eq6000 = it.toInt().toByte()
                                    7000 -> dsp?.eq7000 = it.toInt().toByte()
                                }
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
            device.excute(JinHaoRequest.readProgramVolume(), Consumer {
                if (it.isError) {
                    Log.w(tag, "Failed to read program volume")
                } else {
                    Log.w(tag, "Successfully read program volume")
                }
            })

            //We can access the total number of programs in the hearing aid, but no more than four.
            device.excute(JinHaoRequest.readNumberOfProgram(device.hearChip), Consumer {
                if (it.isError) {
                    Log.w(tag, "Failed to read number of program")

                    var numberOfPragram = device.numberOfProgram
                    device.excute(JinHaoRequest.readDsp(0), Consumer {
                        Log.w(tag, "Finished reading program 0 DSP file, scene is ${device.scenesOfProgram.get(0)}")
                    })
                    device.excute(JinHaoRequest.readDsp(numberOfPragram-1), Consumer {
                        Log.w(tag, "Finished reading program lastest DSP file, scene is ${device.scenesOfProgram.get(numberOfPragram-1)}")
                    })

                } else {
                    Log.w(tag, "Successfully read number of program is ${device.numberOfProgram}")
                }
            })

            //We can obtain the scene mode corresponding to each program, for example, the scene mode of program 0 is scenesOfProgram[0]
            device.excute(JinHaoRequest.readScenesOfProgram(), Consumer {
                val programs = device.scenesOfProgram    //JinHaoProgram
                Log.w(tag, "Successfully read scenes of program is ${programs.size}, current scene is ${device.scenesOfProgram.get(device.numberOfProgram)}")
            })

            //read battery
            device.excute(JinHaoRequest.readBat(), Consumer {
                if (it.isError) {
                    Log.w(tag, "Failed to read battery")
                } else {
                    Log.w(tag, "Successfully read battery ${device.bat}")
                }
            })

            //read current program and volume
            device.excute(JinHaoRequest.readProgramVolume(), Consumer {
                if (it.isError) {
                    Log.w(tag, "Failed to read program and volume")
                } else {
                    Log.w(tag, "Successfully read current program ${device.program}, volume is ${device.volume}")
                    device.excute(JinHaoRequest.readDsp(device.program), Consumer {
                        Log.w(tag, "current dsp for program ${device.program} is ${device.dsp}")
                    })
                }
            })

            //read sku for hearing aid
            device.excute(JinHaoRequest.readProfile(JinHaoProfileType.PRODUCT_SKU), Consumer {
                if (it.isSuccess) {
                    Log.w(tag, "sku code is ${device.profile.skuCode}")
                }
            })
            //read pattern for hearing aid
            device.excute(JinHaoRequest.readProfile(JinHaoProfileType.PRODUCT_PATTERN), Consumer {
                if (it.isSuccess) {
                    Log.w(tag, "pattern code is ${device.profile.patternCode}")
                }
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
                    //range of value is dsp.minEqValue to dsp.maxEqValue
                    eqState[250]?.value = it.eq250.toFloat()
                    eqState[500]?.value = it.eq500.toFloat()
                    eqState[1000]?.value = it.eq1000.toFloat()
                    eqState[1500]?.value = it.eq1500.toFloat()
                    eqState[2000]?.value = it.eq2000.toFloat()
                    eqState[2500]?.value = it.eq2500.toFloat()
                    eqState[3000]?.value = it.eq3000.toFloat()
                    eqState[3500]?.value = it.eq3500.toFloat()
                    eqState[4000]?.value = it.eq4000.toFloat()
                    eqState[5000]?.value = it.eq5000.toFloat()
                    eqState[6000]?.value = it.eq6000.toFloat()
                    eqState[7000]?.value = it.eq7000.toFloat()
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
        device?.excute(JinHaoRequest.readDsp(current), Consumer {
            if (it.isSuccess) {

            }
        })
    }

    /**
     * Changing the volume on the hearing aid
     */
    override fun didChangedVolumeByAid(device: JinHaoAccessory?, previous: Int, current: Int) {
        volumeState.value = current.toFloat()
        device?.dsp?.let {
            minEQValueState.value = it.minEqValue.toFloat()
            maxEQValueState.value = it.maxEqValue.toFloat()
        }
    }

}



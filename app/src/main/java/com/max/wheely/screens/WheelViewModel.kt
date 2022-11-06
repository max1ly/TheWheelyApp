package com.max.wheely.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.max.wheely.domain.Option
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class WheelState {
    object Idle : WheelState()
    object Spinning : WheelState()
    class Selected(val option: Option) : WheelState()
}

class WheelViewModel : ViewModel() {

    private val _options = MutableStateFlow<List<Option>>(emptyList())
    val options: StateFlow<List<Option>>
        get() = _options

    private val _optionsOnWheel = MutableStateFlow<List<OptionOnWheel>>(emptyList())
    val optionsOnWheel: StateFlow<List<OptionOnWheel>>
        get() = _optionsOnWheel

    private val _wheelState = MutableStateFlow<WheelState>(WheelState.Idle)
    val wheelState: StateFlow<WheelState>
        get() = _wheelState

    init {
        _options.value = listOf(
            Option("Option 1", false),
            Option("Option 2", false),
            Option("Option 3", false),
            Option("Option 4", false),
            Option("Option 5", false),
            Option("Option 6", false),
            Option("Option 7", false),
            Option("Option 8", false),
            Option("Option 9", false),
            Option("Option 10", false),
            Option("Option 11", false),
            Option("Option 12", false),
        )
    }

    fun onOptionSelected(option: Option) {
        val options = _options.value.toMutableList()
        val index = options.indexOf(option)
        if (index != -1) {
            val updatedOption = option.copy(selected = !option.selected)
            options[index] = updatedOption
            viewModelScope.launch {
                _options.emit(options)
            }
        }
    }

    fun optionsOnWheel() = viewModelScope.launch {
        val selected = _options.value.filter { it.selected }
        val size = selected.size
        val angleSpan = 360f / size
        val onWheel = mutableListOf<OptionOnWheel>()
        var angle = 0f
        for (option in selected) {
            onWheel.add(OptionOnWheel(option, angleSpan))
            angle += angleSpan
        }
        _optionsOnWheel.emit(onWheel)
    }

    fun onWheelSpin() = viewModelScope.launch {
        _wheelState.emit(WheelState.Spinning)
    }

    fun randomAngle(): Float {
        val minAngle = 360
        val maxAngle = 1080
        return (minAngle..maxAngle).shuffled().first().toFloat()
    }

    fun onWheelSelectedAngle(value: Float) = viewModelScope.launch {
        _wheelState.emit(WheelState.Idle)
        val rounds = (value / 360).toInt()
        val pointerAngle = if (rounds > 0) {
            value - (rounds * 360)
        } else {
            value
        }
        var currentAngleSpan = 0f
        var minAngle = 0f
        var maxAngle = 0f
        for (option in _optionsOnWheel.value) {
            minAngle = currentAngleSpan - option.angle / 2
            maxAngle = currentAngleSpan + option.angle / 2
            if (pointerAngle in minAngle..maxAngle) {
                _wheelState.emit(WheelState.Selected(option.option))
                break
            }
            currentAngleSpan += option.angle
        }
    }
}

package com.serverless.aircar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog
import com.serverless.aircar.databinding.FragmentTimeSetBinding
import java.text.SimpleDateFormat
import java.util.*


class TimeSetFragment : Fragment() {

    private lateinit var binding: FragmentTimeSetBinding
    private val now = System.currentTimeMillis()
    private val currentDate = Date(now)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimeSetBinding.inflate(inflater, container, false)
        context ?: return binding.root

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        initTime()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnComplete.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.lnRentTime.setOnClickListener {
            showDateTimePicker(binding.tvRentTime, "대여 시각")
        }

        binding.lnReturnTime.setOnClickListener {
            showDateTimePicker(binding.tvReturnTime, "반납 시각")
        }

        return binding.root
    }

    private fun initTime() {
        val calendar = Calendar.getInstance()
        calendar.time = currentDate
        binding.tvRentTime.text = getDate(currentDate, calendar)

        calendar.add(Calendar.HOUR, 2)
        binding.tvReturnTime.text = getDate(calendar.time, calendar)
    }

    private fun showDateTimePicker(textView: TextView, title: String) {
        val calendar = Calendar.getInstance()
        calendar.time = currentDate
        calendar.add(Calendar.DATE, 1)

        SingleDateAndTimePickerDialog.Builder(context)
            .mainColor(ContextCompat.getColor(requireContext(), R.color.orange))
            .minDateRange(currentDate)
            .title(title)
            .listener {
                textView.text = getDate(it, calendar)
            }.display()
    }

    private fun getDate(selectedDate: Date, calendar: Calendar): String {
        val dateFormat = SimpleDateFormat("MM-dd", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val moreDaysFormat = SimpleDateFormat("MM월 dd일 HH:mm", Locale.getDefault())

        return if (dateFormat.format(currentDate) == dateFormat.format(selectedDate)) {
            "오늘 ${timeFormat.format(selectedDate)}"
        } else if (dateFormat.format(calendar.time) == dateFormat.format(selectedDate)) {
            "내일 ${timeFormat.format(selectedDate)}"
        } else {
            moreDaysFormat.format(selectedDate)
        }
    }
}
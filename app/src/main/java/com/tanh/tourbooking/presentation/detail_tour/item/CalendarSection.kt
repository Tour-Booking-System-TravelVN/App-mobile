package com.tanh.tourbooking.presentation.detail_tour.item

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tanh.tourbooking.domain.model.TourUnitCalendar
import com.tanh.tourbooking.util.Month
import java.time.LocalDate

@Composable
fun CalendarSection(
    modifier: Modifier = Modifier,
    months: List<Pair<Int, Month>>,
    isDateChosen: (Int, Int) -> Unit
) {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp

    Log.d("CAl5", months.toString())

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("CN", "T2", "T3", "T4", "T5", "T6", "T7").forEach { day ->
                Text(
                    text = day,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }


        months.forEach { (firstDayOfWeek, month) ->
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = month.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            val totalCells = firstDayOfWeek + month.days.size
            val rows = (totalCells + 6) / 7

            var dayIndex = 0

            for (row in 0 until rows) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    for (col in 0 until 7) {
                        val cellIndex = row * 7 + col
                        if (cellIndex < firstDayOfWeek || dayIndex >= month.days.size) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                            )
                        } else {
                            val day = month.days[dayIndex]
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .padding(4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                val isEnabled = !((LocalDate.now().monthValue == month.month)
                                        && (day.date <= LocalDate.now().dayOfMonth))
                                Text(
                                    text = day.date.toString(),
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center,
                                    fontWeight = if(day.data) FontWeight.Bold else FontWeight.Normal,
                                    color = if (day.data) MaterialTheme.colorScheme.primary else Color.Black,
                                    modifier = Modifier.let {
                                        if(day.data) {
                                            it.clickable {
                                                isDateChosen(day.date, month.month)
                                            }
                                        } else Modifier
                                    }
                                )
                            }
                            dayIndex++
                        }
                    }
                }
            }
        }
    }
}

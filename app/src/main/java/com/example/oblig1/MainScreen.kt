package com.example.oblig1

import android.icu.util.Calendar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oblig1.ui.theme.Green50
import com.example.oblig1.ui.theme.Oblig1Theme
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    currentYear: Int,
    currentMonth: Int
) {
    val weekDays = listOf(
        " ", stringResource(id = R.string.monday), stringResource(R.string.tuesday),
        stringResource(R.string.wednesday), stringResource(R.string.thursday),
        stringResource(R.string.friday), stringResource(R.string.saturday),
        stringResource(R.string.sunday)
    )
    val showPassedDaysDialog = remember { mutableStateOf(false) }
    val amountOfWorkingDays = remember { mutableStateOf(0) }
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    calendar.set(Calendar.MONTH, currentMonth)
    calendar.set(Calendar.YEAR, currentYear)
    var currentWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR)

    val lastDayNumber = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    calendar.set(Calendar.DAY_OF_MONTH, 1)

    val dayOfWeek = getDayOfWeekNumber(calendar.get(Calendar.DAY_OF_WEEK))
    val currentMonthNameResource = stringResource(id = getCurrentMonthName(currentMonth))

    val numberOfDays = arrayOfNulls<Int>(40)
    numberOfDays[0] = currentWeekOfYear++
    var k = 1
    for (i in dayOfWeek..<dayOfWeek + lastDayNumber + 4) {
        if (i % 8 == 0) numberOfDays[i] = currentWeekOfYear++
        else numberOfDays[i] = k++
    }
    val numberOfWorkingDays = countWorkingDaysAmount(numberOfDays, lastDayNumber)


    if (showPassedDaysDialog.value) {
        AlertDialog(onDismissRequest = { showPassedDaysDialog.value = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = cardColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 20.dp
                ),
            ) {
                Text(
                    text = stringResource(
                        id = R.string.workdaysfrom1januar,
                        amountOfWorkingDays.value
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        //.background(Color.Red)
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                        .testTag("WorkingDaysText")
                )
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier.border(
                width = 0.5.dp,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        {
            Text(
                text = currentMonthNameResource,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .height(40.dp)
                    .background(MaterialTheme.colorScheme.secondary)
                    .wrapContentHeight(Alignment.CenterVertically)
                    .fillMaxWidth()
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Image(painter = painterResource(id = R.drawable.background), contentDescription = null)

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                LazyVerticalStaggeredGrid(
                    modifier = Modifier.fillMaxWidth(),
                    columns = StaggeredGridCells.Fixed(8),
                    horizontalArrangement = Arrangement.spacedBy(0.dp),

                    ) {
                    itemsIndexed(weekDays) { _, dayString ->
                        Box(
                            modifier = Modifier
                                .border(
                                    width = 0.5.dp,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                .background(Green50)
                        ) {
                            Text(
                                text = dayString,
                                fontSize = 18.sp,
                                color = if (dayString[0] == stringResource(id = R.string.sunday)[0]) Color.Red else Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp)
                                    .height(40.dp)
                                    .wrapContentHeight(Alignment.CenterVertically),
                                textAlign = TextAlign.Left

                            )
                        }
                    }
                }


                LazyVerticalStaggeredGrid(
                    modifier = Modifier.fillMaxWidth(),
                    columns = StaggeredGridCells.Fixed(8),
                    horizontalArrangement = Arrangement.spacedBy(0.dp),

                    ) {
                    itemsIndexed(numberOfDays) { index, dayNumberIndexed ->
                        Box(
                            modifier = Modifier
                                .border(
                                    width = 0.5.dp,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                .testTag(if (index == 17) "MyDateButton" else "")
                                .clickable {
                                    if (index % 8 == 0 || dayNumberIndexed == null) return@clickable
                                    amountOfWorkingDays.value =
                                        countAmountOfWorkingDaysSinceStartOfTheYear(
                                            month = currentMonth,
                                            dayOfMonth = dayNumberIndexed
                                        )
                                    showPassedDaysDialog.value = true
                                }

                        ) {
                            Text(
                                text = dayNumberIndexed?.toString() ?: "",
                                fontSize = if (index % 8 == 0) 14.sp else 18.sp,
                                color = if (index % 8 == 0) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary,
                                fontWeight = if (index % 8 == 0) FontWeight.Bold else FontWeight.Normal,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp)
                                    .height(40.dp)
                                    .wrapContentHeight(Alignment.CenterVertically),
                                textAlign = if (index % 8 == 0) TextAlign.Center else TextAlign.Left
                            )
                        }
                    }
                }


                Box(
                    modifier = Modifier.border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Text(
                        text = stringResource(R.string.workdays, numberOfWorkingDays),
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp,
                        modifier = Modifier
                            .height(60.dp)
                            .background(MaterialTheme.colorScheme.secondary)
                            .wrapContentHeight(Alignment.CenterVertically)
                            .fillMaxWidth()

                    )

                }

            }
        }
    }
}


fun countWorkingDaysAmount(numberOfDays: Array<Int?>, lastDayNumber: Int): Int {
    var amountOfWeekends = 0
    for (i in 6..39 step 8) {
        if (numberOfDays[i] != null) amountOfWeekends++
        if (numberOfDays[i + 1] != null) amountOfWeekends++
    }
    return lastDayNumber - amountOfWeekends
}


fun getDayOfWeekNumber(dayOfWeekCalendar: Int): Int {
    return when (dayOfWeekCalendar) {
        Calendar.SUNDAY -> 7
        else -> dayOfWeekCalendar - 1
    }

}

fun getCurrentMonthName(monthNumber: Int): Int {
    return when (monthNumber) {
        Calendar.JANUARY -> R.string.january
        Calendar.FEBRUARY -> R.string.february
        Calendar.MARCH -> R.string.march
        Calendar.APRIL -> R.string.april
        Calendar.MAY -> R.string.may
        Calendar.JUNE -> R.string.june
        Calendar.JULY -> R.string.july
        Calendar.AUGUST -> R.string.august
        Calendar.SEPTEMBER -> R.string.september
        Calendar.OCTOBER -> R.string.october
        Calendar.NOVEMBER -> R.string.november
        Calendar.DECEMBER -> R.string.december
        else -> R.string.january
    }
}

fun countAmountOfWorkingDaysSinceStartOfTheYear(month: Int, dayOfMonth: Int): Int {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    calendar.set(Calendar.MONTH, month)
    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
    val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
    val amountOfFullWeeks = dayOfYear / 7
    var holidays = amountOfFullWeeks * 2
    if (dayOfYear % 7 == 6) holidays++
    return dayOfYear - holidays
}


@Preview (showBackground = true)
@Composable
fun PreviewMainScreen() {
    val calendar = java.util.Calendar.getInstance()
    val currentMonth = calendar.get(java.util.Calendar.MONTH)
    val currentYear = calendar.get(java.util.Calendar.YEAR)
    Oblig1Theme {
        MainScreen(currentYear, currentMonth)
    }
}

@Preview
@Composable
fun PreviewDarkMainScreen() {
    val calendar = java.util.Calendar.getInstance()
    val currentMonth = calendar.get(java.util.Calendar.MONTH)
    val currentYear = calendar.get(java.util.Calendar.YEAR)
    Oblig1Theme(
        darkTheme = true
    ) {
       MainScreen(currentYear, currentMonth)
    }

}

fun countAmountOfDaysSinceStartOfYear(month: Int, dayOfMonth: Int): Long {
    var passedDataString = ""
    passedDataString = if (dayOfMonth > 9) passedDataString.plus(dayOfMonth.toString()).plus(".")
    else passedDataString.plus("0").plus(dayOfMonth.toString()).plus(".")
    passedDataString = if (month > 9) passedDataString.plus(month.toString()).plus(".")
    else passedDataString.plus("0").plus(month.toString()).plus(".")
    passedDataString = passedDataString.plus("2024 00:00:00")

    val dateFormat = "dd.MM.yyyy HH:mm:ss"
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    val currentDate = formatter.parse(passedDataString)
    val dateString = "01.01.2024 00:00:00"
    val januaryDate = formatter.parse(dateString)
    val diff: Long = currentDate!!.time - januaryDate!!.time
    return diff / 86_400_000 + 1
}

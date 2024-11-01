package com.example.oblig1

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.oblig1.ui.theme.Oblig1Theme
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainScreenUITest {

    @get: Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            Oblig1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(2024, 3)
                }
            }
        }
    }

    @Test
    fun checkIsDialogDisplayed() {
        composeTestRule.onNodeWithTag("MyDateButton").performClick()
        composeTestRule.onNodeWithTag("WorkingDaysText").assertIsDisplayed()
    }

    @Test
    fun checkIsAmountOfWorkingDaysCorrect() {
        composeTestRule.onNodeWithTag("MyDateButton").performClick()
        composeTestRule.onNodeWithTag("WorkingDaysText")
            .assertTextEquals("Antall arbeidsdager \nsiden 1. januar = 76")
    }
}
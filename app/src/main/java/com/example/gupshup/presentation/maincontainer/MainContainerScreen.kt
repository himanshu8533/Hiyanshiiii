package com.example.gupshup.presentation.maincontainer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.gupshup.presentation.bottomnavigation.BottomNavigation
import com.example.gupshup.presentation.callscreen.CallScreen
import com.example.gupshup.presentation.communitiesscreen.CommunitiesScreen
import com.example.gupshup.presentation.homescreen.HomeScreen
import com.example.gupshup.presentation.reelscreen.ReelScreen
import com.example.gupshup.presentation.updatescreen.UpdateScreen
import com.example.gupshup.presentation.viewmodels.BaseViewModel
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun MainContainerScreen(navController: NavHostController) {
    val pagerState = rememberPagerState(pageCount = { 5 })
    val scope = rememberCoroutineScope()
    val baseViewModel: BaseViewModel = hiltViewModel()

    Scaffold(
        bottomBar = {
            BottomNavigation(
                navHostController = navController,
                onClick = { index ->
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                selectedItem = pagerState.currentPage,
                unSelectedItem = Color.DarkGray
            )
        }
    ) { paddingValues ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Box(
                Modifier.graphicsLayer {
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue
                    
                    // Sliding animation with scale effect as requested
                    // Opaque (no transparency)
                    scaleX = 0.98f + (1f - 0.98f) * (1f - pageOffset.coerceIn(0f, 1f))
                    scaleY = 0.98f + (1f - 0.98f) * (1f - pageOffset.coerceIn(0f, 1f))
                }
            ) {
                when (page) {
                    0 -> HomeScreen(navController, baseViewModel, paddingValues)
                    1 -> UpdateScreen(navController, paddingValues)
                    2 -> ReelScreen(navController, paddingValues)
                    3 -> CommunitiesScreen(navController, paddingValues)
                    4 -> CallScreen(navController, paddingValues)
                }
            }
        }
    }
}

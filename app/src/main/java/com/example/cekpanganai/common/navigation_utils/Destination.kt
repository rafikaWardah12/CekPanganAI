package com.example.cekpanganai.common.navigation_utils

sealed class Destination(protected val route: String, vararg params: String) {
    val fullRoute: String = if (params.isEmpty()) route else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/{$it}") }
        builder.toString()
    }

    sealed class NoArgumentDestination(route: String) : Destination(route) {
        operator fun invoke(): String = route
    }

    object OnBoardingScreen : NoArgumentDestination(ONBOARDING_ROUTE)
    object FormProfileScreen : NoArgumentDestination(FORM_PROFILE_ROUTE)
    object BMIScoreScreen : NoArgumentDestination(BMI_SCORE_ROUTE)
    object DashboardScreen : NoArgumentDestination(DASHBOARD_ROUTE)
    object ProcessDetectScreen : NoArgumentDestination(PROCESS_DETECT_ROUTE)
    object ResultScreen : NoArgumentDestination(RESULT_ROUTE)
    object ProfileScreen : NoArgumentDestination(PROFILE_ROUTE)
    object HistoryScreen : NoArgumentDestination(HISTORY_ROUTE)
    object HistoryDetailScreen : Destination(HISTORY_DETAIL_ROUTE, "id") {
        const val ID = "id"
        operator fun invoke(id: String): String = route.appendParams(
            ID to id
        )

    }

    object DetectScreen : NoArgumentDestination(DETECT_ROUTE)
    object EditImageScreen : NoArgumentDestination(EDIT_IMAGE_ROUTE)
    object TestingScreen : NoArgumentDestination(TESTING_ROUTE)
    object TestLineChartScreen : NoArgumentDestination(TEST_LINE_CHART)

    companion object {
        const val ONBOARDING_ROUTE = "onBoarding"
        const val FORM_PROFILE_ROUTE = "formProfile"
        const val BMI_SCORE_ROUTE = "bmiScore"
        const val DASHBOARD_ROUTE = "dashboard"
        const val PROCESS_DETECT_ROUTE = "processDetect"
        const val RESULT_ROUTE = "result"
        const val PROFILE_ROUTE = "profile"
        const val HISTORY_ROUTE = "history"
        const val DETECT_ROUTE = "detect"
        const val HISTORY_DETAIL_ROUTE = "historyDetail"
        const val EDIT_IMAGE_ROUTE = "editImage"
        const val TESTING_ROUTE = "testing"
        const val TEST_LINE_CHART = "testingLine"
    }
}

internal fun String.appendParams(vararg params: Pair<String, Any?>): String {
    val builder = StringBuilder(this)

    params.forEach {
        it.second?.toString()?.let {
            builder.append("/$it")
        }
    }
    return builder.toString()
}
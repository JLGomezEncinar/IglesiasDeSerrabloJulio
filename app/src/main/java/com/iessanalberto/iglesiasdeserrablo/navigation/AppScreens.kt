package com.iessanalberto.iglesiasdeserrablo.navigation

sealed class AppScreens (val route: String, val title: String) {
    object MainScreen: AppScreens (route = "main_screen", title = "Lista iglesias")

    object  IglesiaScreen: AppScreens (route = "iglesia_screen", title = "Iglesia")

    object MapScreen: AppScreens (route = "map_screen", title = "Mapa")
    companion object {
        val listaMenu by lazy { listOf( MainScreen, MapScreen,)
        }
    }
}
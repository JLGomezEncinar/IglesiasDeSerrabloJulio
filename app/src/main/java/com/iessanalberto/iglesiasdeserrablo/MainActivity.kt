package com.iessanalberto.iglesiasdeserrablo


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.iessanalberto.iglesiasdeserrablo.navigation.AppNavigation
import com.iessanalberto.iglesiasdeserrablo.navigation.AppScreens
import com.iessanalberto.iglesiasdeserrablo.ui.theme.IglesiasDeSerrabloTheme
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import java.io.File

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        // --- CONFIGURACIÓN DE OSMDROID ---
        // 1. Cargar la configuración por defecto
        Configuration.getInstance().load(
            applicationContext,
            getSharedPreferences("osmdroid", MODE_PRIVATE)
        )
        // 2. Establecer el User Agent (usa el ID de tu paquete)
        Configuration.getInstance().userAgentValue = packageName

        // 3. Opcional pero recomendado: definir ruta de caché interna
        // para evitar problemas de permisos de escritura en Android 10+
        Configuration.getInstance().osmdroidTileCache = File(cacheDir, "osmdroid")
        // ---------------------------------
        enableEdgeToEdge()
        setContent {
            IglesiasDeSerrabloTheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet {
                            Text(
                                "Menú",
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.titleLarge
                            )
                            HorizontalDivider() // Opcional: una línea divisoria
                            NavigationDrawerItem(
                                label = { Text("Inicio") },
                                selected = false,
                                onClick = {
                                    scope.launch {
                                        navController.navigate(AppScreens.MainScreen.route){
                                            launchSingleTop = true
                                        }
                                        drawerState.close()
                                    }
                                    /* Navegar aquí */
                                }
                            )
                            NavigationDrawerItem(
                                label = { Text("Mapa") },
                                selected = false,
                                onClick = {
                                    scope.launch {
                                        navController.navigate(AppScreens.MapScreen.route){
                                            launchSingleTop = true
                                        }
                                        drawerState.close()
                                    }
                                    /* Navegar aquí */
                                }
                            )
                        }
                    }
                ) {
                    Scaffold(topBar = {
                        TopAppBar(
                            title = { Text("Mi app") },
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        scope.launch { drawerState.open() }
                                    }
                                ) {
                                    Icon(Icons.Default.Menu, contentDescription = "Abrir Menú")
                                }
                            }
                        )
                    }
                    )


                    { paddingValues ->
                        AppNavigation(
                            navController = navController,
                            modifier = Modifier.padding(paddingValues)

                        )
                    }
                }
            }
        }
    }
}


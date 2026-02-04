package com.iessanalberto.iglesiasdeserrablo.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.iessanalberto.iglesiasdeserrablo.data.listaPuntos
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun OSMMap(

    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    AndroidView(
        modifier = modifier,
        factory = {
            MapView(context).apply {

                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)

                val point = GeoPoint(42.473, -0.342)

                controller.setZoom(11.0)
                controller.setCenter(point)
                listaPuntos.forEach { punto ->
                    overlays.add(
                        Marker(this).apply {
                            position = GeoPoint(punto.latitud,punto.longitud)
                            title = punto.nombre
                        }

                    )
                }
            }
        }
    )
}

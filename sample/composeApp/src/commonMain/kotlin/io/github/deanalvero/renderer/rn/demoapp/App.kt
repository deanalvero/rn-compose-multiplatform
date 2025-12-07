package io.github.deanalvero.renderer.rn.demoapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import io.github.deanalvero.renderer.rn.demoapp.data.ComponentRepository
import io.github.deanalvero.renderer.rn.demoapp.screens.ComponentDetailScreen
import io.github.deanalvero.swiftycompose.cmp.demoapp.screens.ComponentListScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = ListRoute) {

            composable<ListRoute> {
                ComponentListScreen(
                    components = ComponentRepository.all,
                    onComponentSelected = { component ->
                        navController.navigate(DetailRoute(componentName = component.name))
                    }
                )
            }

            composable<DetailRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<DetailRoute>()
                val component = ComponentRepository.getByName(route.componentName)

                if (component != null) {
                    ComponentDetailScreen(
                        component = component,
                        onBack = { navController.popBackStack() }
                    )
                } else {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Component not found")
                    }
                }
            }
        }
    }
}

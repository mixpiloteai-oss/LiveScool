package com.livemachine.academy.ui.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Insights
import androidx.compose.material.icons.outlined.Route
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.livemachine.academy.ui.screens.challenges.ChallengesScreen
import com.livemachine.academy.ui.screens.exercises.ExercisesScreen
import com.livemachine.academy.ui.screens.glossary.GlossaryScreen
import com.livemachine.academy.ui.screens.home.HomeScreen
import com.livemachine.academy.ui.screens.lesson.LessonScreen
import com.livemachine.academy.ui.screens.machine.MachineScreen
import com.livemachine.academy.ui.screens.modules.ModuleDetailScreen
import com.livemachine.academy.ui.screens.modules.ModulesScreen
import com.livemachine.academy.ui.screens.path.PathScreen
import com.livemachine.academy.ui.screens.photo.PhotoScreen
import com.livemachine.academy.ui.screens.progress.ProgressScreen
import com.livemachine.academy.ui.screens.quiz.QuizScreen
import com.livemachine.academy.ui.screens.settings.SettingsScreen
import kotlin.reflect.KClass

private data class TopLevelDestination(
    val route: Any,
    val routeClass: KClass<*>,
    val label: String,
    val icon: ImageVector,
)

private val topLevelDestinations = listOf(
    TopLevelDestination(HomeRoute, HomeRoute::class, "Accueil", Icons.Outlined.Home),
    TopLevelDestination(PathRoute, PathRoute::class, "Parcours", Icons.Outlined.Route),
    TopLevelDestination(MachineRoute, MachineRoute::class, "Machine", Icons.Outlined.Tune),
    TopLevelDestination(ProgressRoute, ProgressRoute::class, "Progrès", Icons.Outlined.Insights),
    TopLevelDestination(GlossaryRoute, GlossaryRoute::class, "Glossaire", Icons.AutoMirrored.Outlined.MenuBook),
)

@Composable
fun LiveMachineAppRoot() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val destination = backStackEntry?.destination
    val showBottomBar = topLevelDestinations.any { top ->
        destination?.hierarchy?.any { it.hasRoute(top.routeClass) } == true
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        // La barre d'état est gérée par la barre du haut de chaque écran : on ne garde
        // ici que la barre de navigation système, pour éviter une double marge en haut.
        contentWindowInsets = WindowInsets.navigationBars,
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(containerColor = MaterialTheme.colorScheme.surfaceContainer) {
                    topLevelDestinations.forEach { top ->
                        val selected = destination?.hierarchy?.any { it.hasRoute(top.routeClass) } == true
                        NavigationBarItem(
                            selected = selected,
                            onClick = { navController.navigateTopLevel(top.route) },
                            icon = { Icon(top.icon, contentDescription = null) },
                            label = { Text(top.label) },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                            ),
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        AppNavHost(navController, Modifier.padding(innerPadding))
    }
}

private fun NavHostController.navigateTopLevel(route: Any) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
private fun AppNavHost(navController: NavHostController, modifier: Modifier) {
    val back: () -> Unit = { navController.popBackStack() }
    val openModule: (String) -> Unit = { navController.navigate(ModuleRoute(it)) }
    val openLesson: (String) -> Unit = { navController.navigate(LessonRoute(it)) }
    val openChallenges: () -> Unit = { navController.navigate(ChallengesRoute) }
    val openReview: () -> Unit = { navController.navigate(QuizRoute(QuizKind.REVIEW, "all")) }

    NavHost(navController = navController, startDestination = HomeRoute, modifier = modifier) {
        composable<HomeRoute> {
            HomeScreen(
                onOpenLesson = openLesson,
                onOpenPath = { navController.navigateTopLevel(PathRoute) },
                onOpenModules = { navController.navigate(ModulesRoute) },
                onOpenExercises = { navController.navigate(ExercisesRoute) },
                onOpenMachine = { navController.navigateTopLevel(MachineRoute) },
                onOpenSettings = { navController.navigate(SettingsRoute) },
                onOpenChallenges = openChallenges,
                onOpenReview = openReview,
            )
        }
        composable<PathRoute> {
            PathScreen(
                onOpenModule = openModule,
                onOpenModules = { navController.navigate(ModulesRoute) },
                onOpenSettings = { navController.navigate(SettingsRoute) },
            )
        }
        composable<MachineRoute> {
            MachineScreen(
                onOpenSettings = { navController.navigate(SettingsRoute) },
                onOpenPhoto = { navController.navigate(PhotoRoute) },
            )
        }
        composable<ModulesRoute> { ModulesScreen(onBack = back, onOpenModule = openModule) }
        composable<ModuleRoute> {
            ModuleDetailScreen(
                onBack = back,
                onOpenLesson = openLesson,
                onOpenFinalQuiz = { navController.navigate(QuizRoute(QuizKind.FINAL, it)) },
            )
        }
        composable<LessonRoute> {
            LessonScreen(
                onBack = back,
                onOpenQuiz = { navController.navigate(QuizRoute(QuizKind.LESSON, it)) },
            )
        }
        composable<QuizRoute> { QuizScreen(onBack = back) }
        composable<ExercisesRoute> { ExercisesScreen(onBack = back, onOpenLesson = openLesson) }
        composable<ChallengesRoute> { ChallengesScreen(onBack = back) }
        composable<PhotoRoute> { PhotoScreen(onBack = back) }
        composable<ProgressRoute> {
            ProgressScreen(
                onOpenModule = openModule,
                onOpenExercises = { navController.navigate(ExercisesRoute) },
                onOpenChallenges = openChallenges,
                onOpenReview = openReview,
                onOpenSettings = { navController.navigate(SettingsRoute) },
            )
        }
        composable<GlossaryRoute> {
            GlossaryScreen(onOpenSettings = { navController.navigate(SettingsRoute) })
        }
        composable<SettingsRoute> { SettingsScreen(onBack = back) }
    }
}

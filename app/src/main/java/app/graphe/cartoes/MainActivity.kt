package app.graphe.cartoes

import android.os.Bundle
import android.os.SystemClock
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.graphe.cartoes.ui.HomeScreen
import app.graphe.cartoes.ui.MemorizeScreen
import app.graphe.cartoes.ui.MemorizeViewModel
import app.graphe.cartoes.ui.MemorizeViewModelFactory
import app.graphe.cartoes.ui.QuizScreen
import app.graphe.cartoes.ui.QuizViewModel
import app.graphe.cartoes.ui.QuizViewModelFactory
import app.graphe.cartoes.ui.StudyScreen
import app.graphe.cartoes.ui.StudyViewModel
import app.graphe.cartoes.ui.StudyViewModelFactory
import app.graphe.cartoes.ui.theme.CartoesTheme

private enum class AppPlace {
    HOME,
    CARDS,
    QUIZ,
    MEMORIZE,
}

class MainActivity : ComponentActivity() {
    private val studyViewModel: StudyViewModel by viewModels {
        val app = application as CartoesApp
        StudyViewModelFactory(app.repository, app.practice)
    }
    private val quizViewModel: QuizViewModel by viewModels {
        val app = application as CartoesApp
        QuizViewModelFactory(app.repository, app.practice)
    }
    private val memorizeViewModel: MemorizeViewModel by viewModels {
        MemorizeViewModelFactory((application as CartoesApp).practice)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        val openedAt = SystemClock.elapsedRealtime()
        splash.setKeepOnScreenCondition { SystemClock.elapsedRealtime() - openedAt < 700 }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT),
        )
        setContent {
            CartoesTheme {
                var place by rememberSaveable { mutableStateOf(AppPlace.HOME) }
                when (place) {
                    AppPlace.HOME -> HomeScreen(
                        onFlashcards = { place = AppPlace.CARDS },
                        onQuiz = { place = AppPlace.QUIZ },
                        onMemorize = { place = AppPlace.MEMORIZE },
                    )
                    AppPlace.CARDS -> {
                        val state by studyViewModel.ui.collectAsStateWithLifecycle()
                        StudyScreen(
                            state = state,
                            viewModel = studyViewModel,
                            onHome = { place = AppPlace.HOME },
                        )
                    }
                    AppPlace.QUIZ -> QuizScreen(
                        viewModel = quizViewModel,
                        onHome = { place = AppPlace.HOME },
                    )
                    AppPlace.MEMORIZE -> MemorizeScreen(
                        viewModel = memorizeViewModel,
                        onHome = { place = AppPlace.HOME },
                    )
                }
            }
        }
    }
}
